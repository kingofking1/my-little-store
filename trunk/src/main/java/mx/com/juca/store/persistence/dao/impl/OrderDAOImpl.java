package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.IOrderDAO;
import mx.com.juca.store.persistence.domain.Order;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Order.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class OrderDAOImpl implements IOrderDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Order transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(Order persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Order.class);
		criteria.add(Restrictions.eq("idOrder", id));
		List<Order> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findByExample(Order instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Order.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(org.hibernate.criterion.Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Order.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(Order instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(Order instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}