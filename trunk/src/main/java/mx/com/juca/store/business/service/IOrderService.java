package mx.com.juca.store.business.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.exception.ApplicationException;

/**
 * Service Interface
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface IOrderService {

	/**
	 * 
	 * @param orderDTO
	 * @return	Boolean
	 */
	@WebMethod(exclude=true)
	public Boolean placeOrder(final OrderDTO orderDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param creditCardDTO
	 * @param orderDTO
	 * @return	String
	 */
	@WebMethod(exclude=true)
	public String processPayment(final CreditCardDTO creditCardDTO, final OrderDTO orderDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param customerDTO
	 * @return	List<OrderDTO>
	 */
	@WebMethod(operationName="retrieveOrders", action="retrieveOrders")
	public List<OrderDTO> getOrders(@WebParam(name="customer") final CustomerDTO customerDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param idOrder
	 * @return	List<ProductOrderDTO>
	 */
	@WebMethod(operationName="retrieveOrderDetails", action="retrieveOrderDetails")
	public List<ProductOrderDTO> getOrderDetails(@WebParam(name="orderNumber") final Integer idOrder) throws ApplicationException;	

	/**
	 * 
	 * @param creditCardDTO
	 * @param orderDTO
	 * @return	String
	 */
	@WebMethod(exclude=true)
	public String placeOrderAndPayment(final CreditCardDTO creditCardDTO, final OrderDTO orderDTO) throws ApplicationException;
}
