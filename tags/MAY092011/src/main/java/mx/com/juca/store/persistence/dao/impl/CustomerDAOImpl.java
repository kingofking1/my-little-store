package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.ICustomerDAO;
import mx.com.juca.store.persistence.domain.Customer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Customer.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class CustomerDAOImpl implements ICustomerDAO {
	
	private static final Logger log = Logger.getLogger(CustomerDAOImpl.class);

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Customer transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(Customer persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}
	
	@Override
	public Customer findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Customer.class);
		criteria.add(Restrictions.eq("idCustomer", id));
		@SuppressWarnings("unchecked")
		List<Customer> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@Override
	public Customer findByEmailPassword(String email, String password) {
		log.debug(email+", "+StringUtils.leftPad(StringUtils.right(password,2), password.length(), '*'));
		log.debug(this.sessionFactory==null);
		Criteria criteria = this.sessionFactory.getCurrentSession()
		.createCriteria(Customer.class);
		criteria.add(Restrictions.and(Restrictions.eq("email", email),Restrictions.eq("password", password)));
		@SuppressWarnings("unchecked")
		List<Customer> items = criteria.list();
		log.debug(items);
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findByExample(Customer instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Customer.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Customer.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(Customer instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(Customer instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}