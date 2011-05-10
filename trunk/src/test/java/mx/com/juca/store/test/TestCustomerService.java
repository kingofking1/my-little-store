package mx.com.juca.store.test;

import java.util.List;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.service.ICustomerService;
import mx.com.juca.store.util.DateUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
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
 * Test Cases for CustomerService
 * 
 * @author Juan Carlos Cruz
 * @since Feb 15, 2011
 */
public class TestCustomerService extends BaseTestCase {

	private static final Logger log = Logger
			.getLogger(TestCustomerService.class);

	@Autowired
	private ICustomerService customerService;
	
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
	public void testDoSignIn() {
		log.info("Starting test - testDoSignIn");
		CustomerDTO result = null;
		try {
			result = this.customerService.doSignIn("juca.cruz@gmail.com","1234");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(result);
		log.debug(result);
		log.info("End of test - testDoSignIn");
	}
	
	@Test
	@Transactional
	@Rollback
	@Repeat(2)
	public void testDoSignUp() {
		log.info("Starting test - testDoSignUp");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();
		//customerDTO.setCreationTs(new Date()); //Optional
		//customerDTO.setModificationTs(new Date());  //Optional
		customerDTO.setEmail(RandomStringUtils.randomAlphanumeric(20).toLowerCase()+"@"+RandomStringUtils.randomAlphabetic(17).toLowerCase()+".com");
		customerDTO.setFirstName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setLastName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setMiddleName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setPassword(RandomStringUtils.randomAlphanumeric(45).toUpperCase());
		try {
			result = this.customerService.doSignUp(customerDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.debug(result);
		log.info("End of test - testDoSignUp");
	}
	
	@Test
	@Transactional
	@Rollback
	@Repeat(1)
	public void testUpdateCustomerInformation() {
		log.info("Starting test - testUpdateCustomerInformation");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(2));
		customerDTO.setCreationTs(null); //It doesn't matter, because column is NOT UPDATABLE
		customerDTO.setModificationTs(null); //I have a default for this field, so I don't care
		customerDTO.setEmail(RandomStringUtils.randomAlphanumeric(20).toLowerCase()+"@"+RandomStringUtils.randomAlphabetic(17).toLowerCase()+".com");
		customerDTO.setFirstName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setLastName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setMiddleName(RandomStringUtils.randomAlphabetic(50).toUpperCase());
		customerDTO.setPassword(RandomStringUtils.randomAlphanumeric(45).toUpperCase());
		try {
			result = this.customerService.updateCustomerInformation(customerDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.debug(result);
		log.info("End of test - testUpdateCustomerInformation");
	}
	
	@Test
	@Transactional
	@Rollback
	@Repeat(1)
	public void testAddUpdatePaymentInformation() {
		log.info("Starting test - testAddUpdatePaymentInformation");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();		
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(2));
		
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setCustomerDTO(customerDTO);
		creditCardDTO.setExpirationDate(DateUtils.formatDate(new java.util.Date(), DateUtils.FORMAT_MMYYYY));
		creditCardDTO.setNumber(RandomStringUtils.randomNumeric(16));
		creditCardDTO.setSecurityCode(RandomStringUtils.randomNumeric(3));
		this.populateCreditCardType(creditCardDTO);	
		
		try {
			result = this.customerService.addUpdatePaymentInformation(creditCardDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.debug(result);
		log.info("End of test - testAddUpdatePaymentInformation");
	}

	@Test
	@Transactional
	@Rollback
	@Repeat(1)
	public void testDeletePaymentInformation() {
		log.info("Starting test - testDeletePaymentInformation");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();		
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(2));		
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setIdCreditCard(RandomUtils.JVM_RANDOM.nextInt(1));
		creditCardDTO.setCustomerDTO(customerDTO);
		try {
			result = this.customerService.deletePaymentInformation(creditCardDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.debug(result);
		log.info("End of test - testDeletePaymentInformation");
	}
	
	@Test
	@Transactional
	@Rollback
	@Repeat(1)
	public void testUpdatePaymentInformation() {
		log.info("Starting test - testUpdatePaymentInformation");
		Boolean result = false;
		CustomerDTO customerDTO = new CustomerDTO();		
		customerDTO.setIdCustomer(RandomUtils.JVM_RANDOM.nextInt(2));
		
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setIdCreditCard(RandomUtils.JVM_RANDOM.nextInt(1));
		creditCardDTO.setCustomerDTO(customerDTO);
		creditCardDTO.setExpirationDate(DateUtils.formatDate(new java.util.Date(), DateUtils.FORMAT_MMYYYY));
		creditCardDTO.setNumber(RandomStringUtils.randomNumeric(16));
		creditCardDTO.setSecurityCode(RandomStringUtils.randomNumeric(3));
		this.populateCreditCardType(creditCardDTO);
		
		try {
			result = this.customerService.updatePaymentInformation(creditCardDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.debug(result);
		log.info("End of test - testUpdatePaymentInformation");
	}	
	
	
	@Test
	public void testGetAllCreditCards() {
		log.info("Starting test - testGetAllCreditCards");
		List<CreditCardDTO> result = null;
		try {
			result = this.customerService.getAllCreditCards(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(result);
		log.debug(result);
		log.info("End of test - testGetAllCreditCards");
	}	
}