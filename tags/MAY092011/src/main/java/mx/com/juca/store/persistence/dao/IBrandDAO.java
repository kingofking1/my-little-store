package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.Brand;

/**
 * Data access object Interface (DAO) for domain model class Brand.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface IBrandDAO {
	public void save(Brand transientInstance);

	public void delete(Brand persistentInstance);

	public Brand findById(Integer id);

	public List<Brand> findByExample(Brand instance);

	public List<Brand> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(Brand instance);

	public void update(Brand instance);

	public List<Brand> getAll();

}