package mx.com.juca.store.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICustomerService;
import mx.com.juca.store.persistence.dao.ICreditCardDAO;
import mx.com.juca.store.persistence.dao.ICustomerDAO;
import mx.com.juca.store.persistence.domain.CreditCard;
import mx.com.juca.store.persistence.domain.Customer;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.util.Transfomer;

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
public class CustomerServiceImpl implements ICustomerService {
	
	private static final Logger log = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private ICustomerDAO customerDAO;
	
	@Autowired
	private ICreditCardDAO creditCardDAO;
	
	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public CustomerDTO doSignIn(String email, String password) throws ApplicationException {
		log.debug(email+", "+StringUtils.leftPad(StringUtils.right(password,2), password.length(), '*'));
		CustomerDTO result = null;
		try{
			Customer customer = this.customerDAO.findByEmailPassword(email, password);
			if(customer!=null){
				result = Transfomer.convertToDTO(customer); 
			}else{
				log.debug("Customer not found");
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
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean doSignUp(CustomerDTO customerDTO) throws ApplicationException {
		log.debug(customerDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.customerDAO.save(Transfomer.convertToDomain(customerDTO));
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
	public Boolean updateCustomerInformation(CustomerDTO customerDTO) throws ApplicationException {
		log.debug(customerDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.customerDAO.update(Transfomer.convertToDomain(customerDTO));
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
	public Boolean addUpdatePaymentInformation(CreditCardDTO creditCardDTO) throws ApplicationException {
		log.debug(creditCardDTO);
		Boolean result = Boolean.FALSE;
		try{
			if(creditCardDTO.getCustomerDTO()==null){
				throw new ApplicationException("Customer information is missing");
			}
			CreditCard creditCard = Transfomer.convertToDomain(creditCardDTO);
			this.creditCardDAO.saveOrUpdate(creditCard);
			creditCardDTO.setIdCreditCard(creditCard.getIdCreditCard());//Recover Id and set it into the DTO
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
	public Boolean updatePaymentInformation(CreditCardDTO creditCardDTO) throws ApplicationException {
		log.debug(creditCardDTO);
		Boolean result = Boolean.FALSE;
		try{
			if(creditCardDTO.getCustomerDTO()==null){
				throw new ApplicationException("Customer information is missing");
			}
			this.creditCardDAO.update(Transfomer.convertToDomain(creditCardDTO));
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
	public Boolean deletePaymentInformation(CreditCardDTO creditCardDTO) throws ApplicationException {
		log.debug(creditCardDTO);
		Boolean result = Boolean.FALSE;
		try{
			if(creditCardDTO.getCustomerDTO()==null){
				throw new ApplicationException("Customer information is missing");
			}
			this.creditCardDAO.delete(Transfomer.convertToDomain(creditCardDTO));
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
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<CreditCardDTO> getAllCreditCards(Integer idCustomer)
			throws ApplicationException {
		log.debug(idCustomer);
		List<CreditCardDTO> result = new ArrayList<CreditCardDTO>();
		try{
			List<CreditCard> list = this.creditCardDAO.findByProperty("customer.idCustomer", idCustomer);
			for(CreditCard creditCard : list){
				result.add(Transfomer.convertToDTO(creditCard));
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
	public CreditCardDTO getCreditCard(Integer idCreditCard)
			throws ApplicationException {
		log.debug(idCreditCard);
		CreditCardDTO result = null;
		try{
			CreditCard creditCard = this.creditCardDAO.findById(idCreditCard);
			if(creditCard!=null){
				result = Transfomer.convertToDTO(creditCard);
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

}
