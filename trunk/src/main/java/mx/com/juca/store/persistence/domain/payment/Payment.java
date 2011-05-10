package mx.com.juca.store.persistence.domain.payment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 23, 2011
 */
@Entity
@Table(name = "\"PAYMENTS\"")
public class Payment implements Serializable {

	private static final long serialVersionUID = 29461940044295039L;

	@EmbeddedId
	private PaymentId id;

	@Column(name = "total_amount_transaction", nullable = false, precision = 6, scale = 2)
	private Float totalAmountTransaction;

	@Column(name = "transaction_number", nullable = false, updatable = false, unique = true, length=50)
	private String transactionNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_ts", length = 19, updatable = false, insertable = true)
	private Date creationTs;

	public PaymentId getId() {
		return id;
	}

	public void setId(PaymentId id) {
		this.id = id;
	}

	public Float getTotalAmountTransaction() {
		return totalAmountTransaction;
	}

	public void setTotalAmountTransaction(Float totalAmountTransaction) {
		this.totalAmountTransaction = totalAmountTransaction;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Date getCreationTs() {
		return creationTs;
	}

	public void setCreationTs(Date creationTs) {
		this.creationTs = creationTs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((transactionNumber == null) ? 0 : transactionNumber
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (transactionNumber == null) {
			if (other.transactionNumber != null)
				return false;
		} else if (!transactionNumber.equals(other.transactionNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("Payment [id=%s, totalAmountTransaction=%s, transactionNumber=%s, creationTs=%s]",
						id, totalAmountTransaction, transactionNumber, creationTs);
	}

}
