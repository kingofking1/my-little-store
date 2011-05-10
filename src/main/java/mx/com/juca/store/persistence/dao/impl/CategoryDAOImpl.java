package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.ICategoryDAO;
import mx.com.juca.store.persistence.domain.Category;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Category.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class CategoryDAOImpl implements ICategoryDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Category transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(Category persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Category findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Category.class);
		criteria.add(Restrictions.eq("idCategory", id));
		List<Category> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findByExample(Category instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Category.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Category.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		Criteria criteria = this.sessionFactory.getCurrentSession()
			.createCriteria(Category.class);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(Category instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(Category instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}