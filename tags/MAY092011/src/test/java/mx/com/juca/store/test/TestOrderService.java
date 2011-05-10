package mx.com.juca.store.test;

import java.util.ArrayList;
import java.util.List;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.service.IOrderService;
import mx.com.juca.store.util.DateUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test Cases for OrderService
 * 
 * @author Juan Carlos Cruz
 * @since Feb 15, 2011
 */
public class TestOrderService extends BaseTestCase {
	private static final Logger log = Logger
			.getLogger(TestCustomerService.class);

	@Autowired
	private IOrderService orderService;

	@BeforeClass
	public static void executeBeforeClass() {
		log.info("Execute to inizitalize class with @BeforeClass");
	}

	@AfterClass
	public static void executeAfterClass() {
		log.info("Execute to finalize class with @AfterClass");
	}

	@After
	public void executeAfter() {
		log.info("This should be executed after each method with @After");
	}

	@Before
	public void executeBefore() {
		log.info("This should be executed before each method with @Before");
	}

	@Test
	public void testGetOrders() {
		log.info("Starting test - testGetOrders");
		List<OrderDTO> result = null;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(2));
		try {
			result = this.orderService.getOrders(customerDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(result);
		for(OrderDTO orderDTO : result){
			log.debug(orderDTO +": "+orderDTO.getProductOrders());
		}
		log.info("End of test - testGetOrders");
	}
	
	@Test
	@Rollback
	@Transactional
	@Repeat(2)
	public void testProcessPayment() {
		log.info("Starting test - testProcessPayment");
		String result = null;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(10));
		customerDTO.setEmail(RandomStringUtils.randomAlphanumeric(20).toLowerCase()+"@"+RandomStringUtils.randomAlphabetic(17).toLowerCase()+".com");
		
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setIdCreditCard(RandomUtils.JVM_RANDOM.nextInt(3));
		creditCardDTO.setCustomerDTO(customerDTO);
		creditCardDTO.setExpirationDate(DateUtils.formatDate(new java.util.Date(), DateUtils.FORMAT_MMYYYY));
		creditCardDTO.setNumber(RandomStringUtils.randomNumeric(16));
		creditCardDTO.setSecurityCode(RandomStringUtils.randomNumeric(3));
		this.populateCreditCardType(creditCardDTO);
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setIdOrder(RandomUtils.JVM_RANDOM.nextInt());
		orderDTO.setCustomerDTO(customerDTO);
		orderDTO.setTax(new Float(RandomUtils.JVM_RANDOM.nextInt(100)*(RandomUtils.JVM_RANDOM.nextInt(15)/100)));
		orderDTO.setTotalSale(new Float(RandomUtils.JVM_RANDOM.nextInt(1000)+"."+RandomUtils.JVM_RANDOM.nextInt(99)));
		
		try {
			result = this.orderService.processPayment(creditCardDTO, orderDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(result);
		log.info("End of test - testProcessPayment");
	}
	
	@Test
	@Rollback
	@Transactional
	@Repeat(1)
	public void testPlaceOrder() {
		log.info("Starting test - testPlaceOrder");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setIdCustomer(1);//(RandomUtils.JVM_RANDOM.nextInt(2));
		customerDTO.setEmail(RandomStringUtils.randomAlphanumeric(20).toLowerCase()+"@"+RandomStringUtils.randomAlphabetic(17).toLowerCase()+".com");
		
		//We don't need Card due to payment is another process
		//Here we just place the order with details
		
		List<ProductOrderDTO> productOrders = new ArrayList<ProductOrderDTO>();		
		for(int i=0; i<RandomUtils.JVM_RANDOM.nextInt(3);i++){
			ProductOrderDTO productOrderDTO = new ProductOrderDTO();
			Integer numberOfItems = RandomUtils.JVM_RANDOM.nextInt(3);		
			productOrderDTO.setNumberItems(numberOfItems.intValue()==0?1:numberOfItems);
			ProductDTO productDTO = new ProductDTO();
			productDTO.setIdProduct(RandomUtils.JVM_RANDOM.nextInt(6));
			productOrderDTO.setProductDTO(productDTO);
			productOrders.add(productOrderDTO);
		}
		
		OrderDTO orderDTO = new OrderDTO();		
		orderDTO.setCustomerDTO(customerDTO);
		orderDTO.setTax(new Float(RandomUtils.JVM_RANDOM.nextInt(100)*(RandomUtils.JVM_RANDOM.nextInt(15)/100F)));
		orderDTO.setTotalSale(new Float(RandomUtils.JVM_RANDOM.nextInt(1000)+"."+RandomUtils.JVM_RANDOM.nextInt(99)));
		orderDTO.setProductOrders(productOrders);
		try {
			result = this.orderService.placeOrder(orderDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testPlaceOrder");
	}	
	
	
	@Test
	public void testWebService(){
		log.info("Starting test - testWebService");
		List<OrderDTO> result = null;
		try{			
			JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
			factoryBean.setDataBinding(new AegisDatabinding());			
			factoryBean.getInInterceptors().add(new LoggingInInterceptor());
			factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
			factoryBean.setAddress("http://127.0.0.1:9080/little-store/services/OrderService");
			factoryBean.setServiceClass(IOrderService.class);
			IOrderService myOrderWebService = (IOrderService)factoryBean.create();
			
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setIdCustomer(1);
			
			result = myOrderWebService.getOrders(customerDTO);
		}catch(Exception ex){
			log.warn(ex);
		}	
		
		Assert.assertNotNull(result);
		
		for(OrderDTO orderDTO : result){
			log.debug(orderDTO +": "+orderDTO.getProductOrders());
		}
			
		log.info("End of test - testWebService");
	}
	
	@Test
	public void testWebService2(){
		log.info("Starting test - testWebService2");
		List<ProductOrderDTO> result = null;
		try{			
			JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
			factoryBean.setDataBinding(new AegisDatabinding());			
			factoryBean.getInInterceptors().add(new LoggingInInterceptor());
			factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
			factoryBean.setAddress("http://127.0.0.1:9080/little-store/services/OrderService");
			factoryBean.setServiceClass(IOrderService.class);
			IOrderService myOrderWebService = (IOrderService)factoryBean.create();
			
			result = myOrderWebService.getOrderDetails(12);
		}catch(Exception ex){
			log.warn(ex);
		}	
		
		Assert.assertNotNull(result);
		
		for(ProductOrderDTO productOrderDTO : result){
			log.debug(productOrderDTO);
		}
			
		log.info("End of test - testWebService2");
	}	
}