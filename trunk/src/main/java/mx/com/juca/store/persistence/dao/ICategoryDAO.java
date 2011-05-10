package mx.com.juca.store.persistence.dao;

import java.util.List;

import mx.com.juca.store.persistence.domain.Category;

/**
 * Data access object Interface (DAO) for domain model class Category.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface ICategoryDAO {
	public void save(Category transientInstance);

	public void delete(Category persistentInstance);

	public Category findById(Integer id);

	public List<Category> findByExample(Category instance);

	public List<Category> findByProperty(String propertyName, Object value);

	public void saveOrUpdate(Category instance);

	public void update(Category instance);

	public List<Category> getAll();

}