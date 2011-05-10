package mx.com.juca.store.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICustomerService;
import mx.com.juca.store.business.service.IOrderService;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.BaseCustomController;
import mx.com.juca.store.web.ShoppingCart;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 21, 2011
 */
@Controller
@RequestMapping("/checkOut/*")
public class CheckOutController extends BaseCustomController {
	
	private static final Logger log = Logger.getLogger(CheckOutController.class);
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ShoppingCartController shoppingCartController; //Reuse functionality from this Controller!
	
	@Autowired
	private ReCaptcha reCaptcha; 
	
	@RequestMapping(value="reviewOrder.do")
	public String reviewOrder(HttpServletRequest request, ModelMap modelMap){
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		log.debug("Review order - show shopping cart content");

		//Reuse existing code
		log.debug("Reuse existing code by calling shoppingCartController.viewShoppingCartContent(..)");
		this.shoppingCartController.viewShoppingCartContent(request, modelMap);
		
		log.debug("show review_order.jsp");
		return "checkout/review_order";
	}
	
	@RequestMapping(value="addPaymentInformation.do", method=RequestMethod.GET)
	public String showAddPaymentInfo(HttpServletRequest request, ModelMap modelMap, 
			@RequestParam(value="idCreditCard", required=false) final Integer idCreditCard){//Sometimes this param will be sometimes won't
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		
		log.debug("Show payment method screen to add payment information");
		List<CreditCardDTO> list = new ArrayList<CreditCardDTO>();
		CustomerDTO customerDTO = this.getUserAuthenticated(request);
		modelMap.put(GenericConstants.KEY_USER, customerDTO);
				
		try {
			list = this.customerService.getAllCreditCards(customerDTO.getIdCustomer());
			modelMap.put(GenericConstants.KEY_LIST_ITEMS, list);
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}		

		log.debug("Create new DTO to be used on form");
		log.debug("idCreditCard param="+idCreditCard);
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		if(idCreditCard!=null){
			try {
				creditCardDTO = this.customerService.getCreditCard(idCreditCard);
			} catch (ApplicationException ex) {
				log.error(ex);
			}
			if(creditCardDTO==null){//Credit Card not Found
				creditCardDTO = new CreditCardDTO();
			}
		}
		modelMap.put("CreditCardDTO", creditCardDTO);
		
		//Populate KEY_MAP_TYPE_CARDS
		Map<String, String> mapCardTypes = new LinkedHashMap<String, String>();
		mapCardTypes.put(GenericConstants.AMEX_DESC, GenericConstants.AMEX_DESC);
		mapCardTypes.put(GenericConstants.MASTERCARD_DESC, GenericConstants.MASTERCARD_DESC);
		mapCardTypes.put(GenericConstants.VISA_DESC, GenericConstants.VISA_DESC);
		mapCardTypes.put(GenericConstants.OTHER_CARD_DESC, GenericConstants.OTHER_CARD_DESC);
		modelMap.put(GenericConstants.KEY_MAP_TYPE_CARDS, mapCardTypes);
		
		log.debug("show payment_method.jsp");
		return "checkout/payment_method";
	}

	@RequestMapping(value="addPaymentInformation.do", method=RequestMethod.POST)
	public String addPaymentInfo(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("CreditCardDTO") CreditCardDTO paramCreditCardDTO,
			Object skipUpdateCreditCard){
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		Boolean result = false;
		log.debug("Add payment information "+paramCreditCardDTO+", skipUpdateCreditCard="+skipUpdateCreditCard);
		//Associate it with customer
		paramCreditCardDTO.setCustomerDTO(this.getUserAuthenticated(request));
		
		if(skipUpdateCreditCard==null){
			try {
				result = this.customerService.addUpdatePaymentInformation(paramCreditCardDTO);
			} catch (ApplicationException ex) {
				log.error(ex);
				modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
			}
			if(result){
				log.debug("Card added successfully");
			}else{
				log.warn("Error while adding payment method="+paramCreditCardDTO);
			}
		}
		
		//Get the Order - Reuse existing logic
		log.debug("Reuse existing code by calling reviewOrder(..)");
		this.reviewOrder(request, modelMap);
		modelMap.put("CreditCardDTO", paramCreditCardDTO);
		modelMap.put("CustomerDTO", this.getUserAuthenticated(request));
		
		/*
		show screen to confirm the Order (before processing payment)
		This is the last step, once pressing Submit Order, you're set!
		*/
		log.debug("show confirm_order.jsp");
		
		//Displays reCatcha		
		modelMap.put("reCaptcha", reCaptcha.createRecaptchaHtml(request.getParameter("error"), null));
		
		return "checkout/confirm_order"; 
	}
	
	@RequestMapping(value="submitOrderAndPayment.do", method=RequestMethod.POST)
	public String submitOrderWithPayment(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("idCreditCard") final Integer idCreditCard,
			@RequestParam("recaptcha_challenge_field") final String reCaptchaChallenge,
			@RequestParam("recaptcha_response_field") final String reCaptchaResponse){

		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		Integer idOrder = null;
		String confirmationNumber = null;
		
		//Handles reCaptcha
		ReCaptchaResponse response = reCaptcha.checkAnswer(request.getRemoteAddr(), reCaptchaChallenge, reCaptchaResponse);
		if(!response.isValid()){
			log.warn("reCaptcha Answer msg= "+response.getErrorMessage());
			CreditCardDTO creditCardDTO = null;
			try {
				creditCardDTO = this.customerService.getCreditCard(idCreditCard);
			} catch (ApplicationException ex) {
				log.error(ex);
			}
			modelMap.put("reCaptchaError",response.getErrorMessage());
			return addPaymentInfo(request, modelMap, creditCardDTO, Boolean.TRUE);
		}

		CreditCardDTO creditCardDTO = new CreditCardDTO();
		try {
			log.debug("Retrieve CreditCard "+idCreditCard);
			creditCardDTO = this.customerService.getCreditCard(idCreditCard);
			log.debug(creditCardDTO);
		} catch (ApplicationException ex) {
			log.error(ex);
		}
				
		log.debug("Prepare Order");
		OrderDTO orderDTO = this.generateOrderDTO(this.getUserAuthenticated(request), this.getUserAuthenticated(request).getShoppingCart().getProductDTOs());		
		try {
			log.debug("Submit order and Processing payment in one single Tx");
			//Place Order and payment
			confirmationNumber = this.orderService.placeOrderAndPayment(creditCardDTO, orderDTO);
			idOrder = orderDTO.getIdOrder();
		} catch (ApplicationException ex) {
			log.error("Error while processing your Order and Payment for "+this.getUserAuthenticated(request),ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		} catch (Exception ex) {
			log.error("Error while processing your Order and Payment for "+this.getUserAuthenticated(request),ex);
			modelMap.put(GenericConstants.KEY_FATAL, ex.getMessage()+". Cause: "+ex.getCause().getMessage());
			//Show confirmation order page
			//Get the Order - Reuse existing logic
			log.debug("Reuse existing code by calling reviewOrder(..)");
			this.reviewOrder(request, modelMap);
			modelMap.put("CreditCardDTO", creditCardDTO);
			modelMap.put("CustomerDTO", this.getUserAuthenticated(request));			
			log.debug("show confirm_order.jsp");
			return "checkout/confirm_order";			
		}
		
		modelMap.put("ORDER_NUMBER", idOrder);
		modelMap.put("CONFIRMATION_NUMBER", confirmationNumber);
		
		//Clean Shopping Cart
		this.getUserAuthenticated(request).setShoppingCart(new ShoppingCart());
		
		//Show confirmation page, it shows order number
		return "checkout/order_completed";
	}
	
	@RequestMapping(value="submitOrderThenPayment.do", method=RequestMethod.POST)
	public String submitOrderIn2Steps(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("idCreditCard") final Integer idCreditCard){
		
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		
		Integer idOrder = null;
		String confirmationNumber = null;
		Boolean orderSubmitted = false;
		
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		try {
			log.debug("Retrieve CreditCard "+idCreditCard);
			creditCardDTO = this.customerService.getCreditCard(idCreditCard);
			log.debug(creditCardDTO);
		} catch (ApplicationException ex) {
			log.error(ex);
		}
				
		log.debug("Prepare Order");
		OrderDTO orderDTO = this.generateOrderDTO(this.getUserAuthenticated(request), this.getUserAuthenticated(request).getShoppingCart().getProductDTOs());		
		try {
			log.debug("Submit order in one Tx");
			//Place Order 
			orderSubmitted = this.orderService.placeOrder(orderDTO);
			idOrder = orderDTO.getIdOrder();
		} catch (ApplicationException ex) {
			log.error("Error wile processing Order for "+this.getUserAuthenticated(request).getEmail(),ex);
			modelMap.put(GenericConstants.KEY_ERROR, "Error while processing your Order. ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}
		
		if(orderSubmitted){
			log.debug("Processing payment in another Tx");
			try {
				//Submit Payment
				confirmationNumber = this.orderService.processPayment(creditCardDTO, orderDTO);
			} catch (ApplicationException ex) {
				log.error("Error wile processing Payment for "+this.getUserAuthenticated(request).getEmail()+", Using "+creditCardDTO,ex);
				//TODO: Handle this error by using a Queue
				String errorMessage = (String)modelMap.get(GenericConstants.KEY_ERROR);
				modelMap.put(GenericConstants.KEY_ERROR, StringUtils.trim(errorMessage)+". Error while processing your payment, ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
			}		
		}else{
			log.error("Error wile processing Order for "+this.getUserAuthenticated(request));
		}
		
		modelMap.put("ORDER_NUMBER", idOrder);
		modelMap.put("CONFIRMATION_NUMBER", confirmationNumber);
		
		//Show confirmation page, it shows order number
		return "checkout/order_completed";
	}	
	
	/**
	 * Creates an OrderDTO to be processed
	 * 
	 * @return	OrderDTO
	 */
	private OrderDTO generateOrderDTO(final CustomerDTO customerDTO, final List<ProductDTO> products){
		Float totalCart = 0F;
		//We don't need Card due to payment is another process
		//Here we just place the order with details
		
		List<ProductOrderDTO> productOrders = new ArrayList<ProductOrderDTO>();		
		for(ProductDTO productDTO :products){
			ProductOrderDTO productOrderDTO = new ProductOrderDTO();
			productOrderDTO.setNumberItems(productDTO.getQuantity());
			productOrderDTO.setProductDTO(productDTO);
			productOrders.add(productOrderDTO);
			
			//Calculate Total in Cart
			totalCart = new Float(totalCart + (productDTO.getQuantity()*productDTO.getPrice()));			
		}
		
		OrderDTO orderDTO = new OrderDTO();		
		orderDTO.setCustomerDTO(customerDTO);
		orderDTO.setTax(0F);//TODO: No taxes for now
		orderDTO.setTotalSale(totalCart);
		orderDTO.setProductOrders(productOrders);
		
		return orderDTO;
	}
}