package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.ProductOrder;
import mx.com.juca.store.persistence.domain.ProductOrderId;

/**
 * Data access object Interface (DAO) for domain model class ProductOrder.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface IProductOrderDAO {
	public void save(ProductOrder transientInstance);

	public void delete(ProductOrder persistentInstance);

	public ProductOrder findById(ProductOrderId id);

	public List<ProductOrder> findByExample(ProductOrder instance);

	public List<ProductOrder> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(ProductOrder instance);

	public void update(ProductOrder instance);

}