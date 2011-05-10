package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.Order;

/**
 * Data access object Interface (DAO) for domain model class Order.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface IOrderDAO {
	public void save(Order transientInstance);

	public void delete(Order persistentInstance);

	public Order findById(Integer id);

	public List<Order> findByExample(Order instance);

	public List<Order> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(Order instance);

	public void update(Order instance);

}