package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.IProductOrderDAO;
import mx.com.juca.store.persistence.domain.ProductOrder;
import mx.com.juca.store.persistence.domain.ProductOrderId;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class ProductOrder.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class ProductOrderDAOImpl implements IProductOrderDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(ProductOrder transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(ProductOrder persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductOrder findById(ProductOrderId id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(ProductOrder.class);
		criteria.add(Restrictions.eq("id.idProduct", id.getIdProduct()));
		criteria.add(Restrictions.eq("id.idOrder", id.getIdOrder()));
		List<ProductOrder> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrder> findByExample(ProductOrder instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(ProductOrder.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrder> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(ProductOrder.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(ProductOrder instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(ProductOrder instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}