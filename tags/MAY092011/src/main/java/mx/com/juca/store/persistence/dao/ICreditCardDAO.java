package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.CreditCard;

/**
 * Data access object Interface (DAO) for domain model class CreditCard.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface ICreditCardDAO {
	public void save(CreditCard transientInstance);

	public void delete(CreditCard persistentInstance);

	public CreditCard findById(Integer id);

	public List<CreditCard> findByExample(CreditCard instance);

	public List<CreditCard> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(CreditCard instance);

	public void update(CreditCard instance);

}