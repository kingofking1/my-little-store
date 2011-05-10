package mx.com.juca.store.test;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.util.GenericConstants;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test Cases for CatalogsService
 * 
 * @author Juan Carlos Cruz
 * @since Feb 15, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/conf/spring/daoApplicationContext.xml",
		"file:src/main/webapp/WEB-INF/conf/spring/serviceApplicationContext.xml" })
public abstract class BaseTestCase {

	static{
		DOMConfigurator.configure("src/main/webapp/WEB-INF/conf/log4j/log4j-config-minimal.xml");
	}
	
	/**
	 * Populate CreditCard Type for a given instance of CreditCardDTO
	 * 
	 * @param creditCardDTO
	 */
	protected void populateCreditCardType(CreditCardDTO creditCardDTO){
		switch (RandomUtils.JVM_RANDOM.nextInt(3)) {
		case 0://GenericConstants.VISA:		
			creditCardDTO.setType(GenericConstants.VISA_DESC);
			break;

		case 1://GenericConstants.AMEX:			
			creditCardDTO.setType(GenericConstants.AMEX_DESC);
			break;
			
		case 2://GenericConstants.MASTERCARD:
			creditCardDTO.setType(GenericConstants.MASTERCARD_DESC);
			break;
			
		default:
			creditCardDTO.setType(GenericConstants.OTHER_CARD_DESC);
			break;
		}
	}
}
