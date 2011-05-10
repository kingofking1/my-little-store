package mx.com.juca.store.persistence.dao.impl.payment;

import java.util.List;

import mx.com.juca.store.persistence.dao.payment.IPaymentDAO;
import mx.com.juca.store.persistence.domain.payment.Payment;
import mx.com.juca.store.persistence.domain.payment.PaymentId;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Data access object Implementation (DAOImpl) for domain model class Payment.
 * 
 * @author Juan Carlos Cruz
 * @since February 23, 2011
 */
public class PaymentDAOImpl implements IPaymentDAO {

	@Autowired
	@Qualifier("hSqlSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Payment transientInstance) {
		this.sessionFactory.getCurrentSession().save(transientInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Payment findById(PaymentId id) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("id.idCustomer", id.getIdCustomer()));
		criteria.add(Restrictions.eq("id.idOrder", id.getIdOrder()));
		List<Payment> items = criteria.list();
		return items.isEmpty() ? null : items.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> findByExample(Payment instance) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Payment.class);
		Example example = Example.create(instance).ignoreCase();
		example.enableLike(MatchMode.START);
		example.excludeZeroes();
		criteria.add(example);
		criteria.addOrder(Order.desc("creationTs"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> findByProperty(String propertyName, Object value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Payment.class);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> findByProperties(String[] propertyName, Object[] value) {
		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Payment.class);
		for(int i=0;i<propertyName.length;i++){
			criteria.add(Restrictions.eq(propertyName[i], value[i]));
		}
		return criteria.list();
	}

}
