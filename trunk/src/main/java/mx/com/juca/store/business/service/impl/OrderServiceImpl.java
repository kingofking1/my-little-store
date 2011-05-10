package mx.com.juca.store.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.IOrderService;
import mx.com.juca.store.persistence.dao.IOrderDAO;
import mx.com.juca.store.persistence.dao.IProductDAO;
import mx.com.juca.store.persistence.dao.IProductOrderDAO;
import mx.com.juca.store.persistence.dao.payment.IPaymentDAO;
import mx.com.juca.store.persistence.domain.Order;
import mx.com.juca.store.persistence.domain.Product;
import mx.com.juca.store.persistence.domain.ProductOrder;
import mx.com.juca.store.persistence.domain.payment.Payment;
import mx.com.juca.store.persistence.domain.payment.PaymentId;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.util.Transfomer;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation
 * 
 * @author Juan Carlos Cruz
 * @since February 12, 2011
 */
@WebService(serviceName="OrderService") //, endpointInterface = "mx.com.juca.store.business.service.IOrderService", targetNamespace="http://juca-cruz-utilities.blogspot.com")
public class OrderServiceImpl implements IOrderService {

	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private IOrderDAO orderDAO;
	
	@Autowired
	private IProductOrderDAO productOrderDAO;
	
	@Autowired
	private IProductDAO productDAO;
	
	@Autowired
	private IPaymentDAO paymentDAO;
	
	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean placeOrder(OrderDTO orderDTO) throws ApplicationException {
		log.debug(orderDTO);
		log.info("Placing order for Customer="+orderDTO.getCustomerDTO().getIdCustomer());
		Boolean result = Boolean.FALSE;
		try{
			log.debug("Saving Order");
			Order order = Transfomer.convertToDomain(orderDTO);
			this.orderDAO.save(order);
			log.debug("Updating Stock for each product");
			for(ProductOrderDTO productOrderDTO : orderDTO.getProductOrders()){
				Product product = this.productDAO.findById(productOrderDTO.getProductDTO().getIdProduct());
				log.debug("Product "+product);
				log.debug("Items ordered="+productOrderDTO.getNumberItems());
				int newStock = product.getQuantity() - productOrderDTO.getNumberItems();				
				if (newStock>0 && newStock<5){
					log.warn("Need to refil stock for Product ("+product.getIdProduct()+", "+product.getName()+", "+product.getCode()+") ASAP");
					//TODO: Send notification
				}
				if (newStock==0){
					log.error("Need to refil stock for Product ("+product.getIdProduct()+", "+product.getName()+", "+product.getCode()+") NOW!");
					//TODO: Send notification
				}
				if (newStock<0){
					log.warn("This should not happen dude!");
					throw new ApplicationException("Stock for Product "+product.getIdProduct()+"/"+product.getName()+" is less than number of items ordered ("+productOrderDTO.getNumberItems()+"). Items in stock "+product.getQuantity());
				}
				log.debug("Updating stock with newStock= "+newStock);
				//Update Stock
				product.setQuantity(newStock);
				
				log.debug("Saving ProductOrder");
				productOrderDTO.setOrderDTO(Transfomer.convertToDTO(order)); // After saving we have an Id generated on Order
				ProductOrder productOrder = Transfomer.convertToDomain(productOrderDTO);
				this.productOrderDAO.save(productOrder);
			}			
			orderDTO.setIdOrder(order.getIdOrder());//Recover IdOrder
			log.info("Order successfull for Customer="+orderDTO.getCustomerDTO().getIdCustomer()+", OrderId="+orderDTO.getIdOrder());
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public String processPayment(CreditCardDTO creditCardDTO, OrderDTO orderDTO) throws ApplicationException {
		log.info("Processing payment for "+creditCardDTO+", OrderId="+orderDTO.getIdOrder()+", Total= "+orderDTO.getTotalSale()+orderDTO.getTax()+", Customer="+orderDTO.getCustomerDTO().getEmail());
		
		Payment payment = new Payment();
		payment.setTotalAmountTransaction(orderDTO.getTotalSale());
		payment.setCreationTs(new Date());
		PaymentId paymentId = new PaymentId(orderDTO.getCustomerDTO().getIdCustomer(), orderDTO.getIdOrder());
		payment.setId(paymentId);
		
		StringBuffer txNumber = new StringBuffer();
		txNumber.append(StringUtils.leftPad(paymentId.getIdOrder()+"", 9, '0'));
		txNumber.append(StringUtils.leftPad(paymentId.getIdCustomer()+"", 9, '0'));
		txNumber.append(StringUtils.center(StringUtils.right(creditCardDTO.getNumber(),4), 8, RandomStringUtils.randomAlphabetic(4)));
		txNumber.append(payment.getCreationTs().getTime());
		payment.setTransactionNumber(txNumber.toString());
		try{
			log.debug("Saving Payment in external DB");
			this.paymentDAO.save(payment);
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the Payments DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing payment process: "+exception.getMessage(), exception);
		}
		log.debug("Payment Tx Number="+txNumber);
		log.info("Payment successfull for "+creditCardDTO+", OrderId="+orderDTO.getIdOrder()+", Total= "+orderDTO.getTotalSale()+orderDTO.getTax()+", Customer="+orderDTO.getCustomerDTO().getEmail());
		return txNumber.toString();
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<OrderDTO> getOrders(CustomerDTO customerDTO) throws ApplicationException {
		log.debug(customerDTO);
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		try{
			List<Order> list = this.orderDAO.findByProperty("customer.idCustomer",customerDTO.getIdCustomer());
			log.debug("Items retrieved "+list.size());	
			for(Order order: list){
				result.add(Transfomer.convertToDTO(order));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<ProductOrderDTO> getOrderDetails(Integer idOrder)
			throws ApplicationException {
		log.debug(idOrder);
		List<ProductOrderDTO> result = new ArrayList<ProductOrderDTO>();
		try{
			List<ProductOrder> list = this.productOrderDAO.findByProperty("id.idOrder",idOrder);
			log.debug("Items retrieved "+list.size());	
			for(ProductOrder productOrder: list){
				result.add(Transfomer.convertToDTO(productOrder));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public String placeOrderAndPayment(CreditCardDTO creditCardDTO,
			OrderDTO orderDTO) throws ApplicationException {
		log.debug("Execute payment and order in the same Tx");
		String result = null;
		Boolean orderSuccessfull = false;
		//placing order
		orderSuccessfull = this.placeOrder(orderDTO);
		if(orderSuccessfull){
			//payment...
			result = this.processPayment(creditCardDTO, orderDTO);
		}
		return result;
	}

}
