package mx.com.juca.store.persistence.dao.impl;

import java.util.List;

import mx.com.juca.store.persistence.dao.IProductDAO;
import mx.com.juca.store.persistence.domain.Product;

import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Product.
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public class ProductDAOImpl implements IProductDAO {

	@Autowired
	@Qualifier("mySqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Product transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@Override
	public void delete(Product persistentInstance) {
		this.sessionFactory.getCurrentSession().delete(persistentInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Product findById(Integer id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Product.class);
		criteria.add(Restrictions.eq("idProduct", id));
		List<Product> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByExample(Product instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Product.class);
		Example example = Example.create(instance);
		example.enableLike(MatchMode.ANYWHERE);
		example.excludeZeroes();
		example.ignoreCase();
		criteria.add(example);
		
		if(instance.getBrand().getIdBrand()!=null){
			criteria.add(Restrictions.eq("brand.idBrand", instance.getBrand().getIdBrand()));
		}
		if(instance.getCategory().getIdCategory()!=null){
			criteria.add(Restrictions.eq("category.idCategory", instance.getCategory().getIdCategory()));
		}
		
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Product.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getRandomProducts(Integer max) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Product.class);
		criteria.add(Restrictions.gt("quantity", 0));
		criteria.setMaxResults(max);
		if(RandomUtils.JVM_RANDOM.nextBoolean()){
			criteria.addOrder(Order.asc("code"));	
		}else{		
			criteria.addOrder(Order.desc("code"));
		}
		if(RandomUtils.JVM_RANDOM.nextBoolean()){
			criteria.addOrder(Order.asc("name"));	
		}else{		
			criteria.addOrder(Order.desc("name"));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAll(Integer maxResults) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Product.class);
		if(maxResults!=null){
			criteria.setMaxResults(maxResults);
		}
		criteria.addOrder(Order.asc("quantity"));
		criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.asc("code"));
		return criteria.list();
	}

	@Override
	public void saveOrUpdate(Product instance) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
	}

	@Override
	public void update(Product instance) {
		this.sessionFactory.getCurrentSession().update(instance);
	}
}