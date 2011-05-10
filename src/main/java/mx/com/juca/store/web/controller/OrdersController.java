package mx.com.juca.store.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.IOrderService;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.BaseCustomController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Mar 1, 2011
 */
@Controller
@RequestMapping(value = "/orders/*")
public class OrdersController extends BaseCustomController {

	private static final Logger log = Logger.getLogger(OrdersController.class);
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value = "viewMyOrders.htm")
	public String showOrders(HttpServletRequest request, ModelMap modelMap) {

		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}		
		log.debug("show orders for "+this.getUserAuthenticated(request).getEmail());
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		try {
			list = this.orderService.getOrders(this.getUserAuthenticated(request));
			modelMap.put(GenericConstants.KEY_LIST_ITEMS, list);
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}

		return "orders/display_orders";//show jsp
	}
	
	@RequestMapping(value = "showOrderDetails.do")
	public String showOrderDetails(HttpServletRequest request, ModelMap modelMap, 
			@RequestParam(value="idOrder", required=true, defaultValue="0") final Integer idOrderParam) {

		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}	
		
		modelMap.put("idOrder", idOrderParam);//
		
		log.debug("show order details");
		List<ProductOrderDTO> list = new ArrayList<ProductOrderDTO>();
		try {
			list = this.orderService.getOrderDetails(idOrderParam);
			modelMap.put(GenericConstants.KEY_LIST_ITEMS, list);
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}

		return "orders/order_details";//show jsp
	}	
}
