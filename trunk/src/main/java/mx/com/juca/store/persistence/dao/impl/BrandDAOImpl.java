package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.IBrandDAO;
import mx.com.juca.store.persistence.domain.Brand;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Brand.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class BrandDAOImpl implements IBrandDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Brand transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(Brand persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Brand findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Brand.class);
		criteria.add(Restrictions.eq("idBrand", id));
		List<Brand> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> findByExample(Brand instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Brand.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Brand.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(Brand instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(Brand instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getAll() {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Brand.class);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
	
	
}