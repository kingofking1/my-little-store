package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.ICreditCardDAO;
import mx.com.juca.store.persistence.domain.CreditCard;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class CreditCard.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class CreditCardDAOImpl implements ICreditCardDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(CreditCard transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(CreditCard persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CreditCard findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(CreditCard.class);
		criteria.add(Restrictions.eq("idCreditCard", id));
		List<CreditCard> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCard> findByExample(CreditCard instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(CreditCard.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCard> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(CreditCard.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(CreditCard instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(CreditCard instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}