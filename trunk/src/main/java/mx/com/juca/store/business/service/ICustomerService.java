package mx.com.juca.store.business.service;

import java.util.List;

import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.exception.ApplicationException;

/**
 * Service Interface
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface ICustomerService {

	/**
	 * Authenticate 
	 * 
	 * @param email
	 * @param password
	 * @return CustomerDTO
	 */
	public CustomerDTO doSignIn(final String email, final String password) throws ApplicationException;
	
	/**
	 * Register
	 * 
	 * @param customerDTO
	 * @return Boolean
	 */
	public Boolean doSignUp(final CustomerDTO customerDTO) throws ApplicationException;
	
	/**
	 * Update Customer
	 * 
	 * @param customerDTO
	 * @return Boolean
	 */
	public Boolean updateCustomerInformation(final CustomerDTO customerDTO) throws ApplicationException;

	/**
	 * Save or Update
	 * 
	 * @param creditCardDTO
	 * @return	Boolean
	 */
	public Boolean addUpdatePaymentInformation(CreditCardDTO creditCardDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param creditCardDTO
	 * @return	Boolean
	 */
	public Boolean updatePaymentInformation(final CreditCardDTO creditCardDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param creditCardDTO
	 * @return	Boolean
	 */
	public Boolean deletePaymentInformation(final CreditCardDTO creditCardDTO) throws ApplicationException;

	/**
	 * 
	 * @param idCustomer
	 * @return	List<CreditCardDTO>
	 * @throws ApplicationException
	 */
	public List<CreditCardDTO> getAllCreditCards(Integer idCustomer) throws ApplicationException;

	/**
	 * 
	 * @param idCreditCard
	 * @return CreditCardDTO
	 * @throws ApplicationException
	 */
	public CreditCardDTO getCreditCard(Integer idCreditCard) throws ApplicationException;
}
