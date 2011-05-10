package mx.com.juca.store.persistence.dao.payment;

import java.util.List;

import mx.com.juca.store.persistence.domain.payment.Payment;
import mx.com.juca.store.persistence.domain.payment.PaymentId;

/**
 * Data access object Interface (DAO) for domain model class Payment.
 * 
 * @author Juan Carlos Cruz
 * @since February 23, 2011
 */
public interface IPaymentDAO {
	public void save(Payment transientInstance);

	public Payment findById(PaymentId id);

	public List<Payment> findByExample(Payment instance);

	public List<Payment> findByProperty(String propertyName, Object value);

	/**
	 * The only restriction is: both arrays must be the same size
	 * @param propertyName
	 * @param value
	 * @return	List<Payment>
	 */
	public List<Payment> findByProperties(String[] propertyName, Object[] value);
}