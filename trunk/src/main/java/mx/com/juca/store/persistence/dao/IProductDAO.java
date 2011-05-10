package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.Product;

/**
 * Data access object Interface (DAO) for domain model class Product.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface IProductDAO {
	public void save(Product transientInstance);

	public void delete(Product persistentInstance);

	public Product findById(Integer id);

	public List<Product> findByExample(Product instance);

	public List<Product> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(Product instance);

	public void update(Product instance);

	public List<Product> getRandomProducts(Integer max);

	public List<Product> getAll(Integer maxResults);

}