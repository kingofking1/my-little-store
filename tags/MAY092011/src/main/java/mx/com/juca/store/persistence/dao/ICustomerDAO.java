package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.Customer;

/**
 * Data access object Interface (DAO) for domain model class Customer.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface ICustomerDAO {
	public void save(Customer transientInstance);

	public void delete(Customer persistentInstance);

	public Customer findById(Integer id);
	
	public Customer findByEmailPassword(String email, String password);

	public List<Customer> findByExample(Customer instance);

	public List<Customer> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(Customer instance);

	public void update(Customer instance);

}