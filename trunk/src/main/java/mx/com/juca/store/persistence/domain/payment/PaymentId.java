package mx.com.juca.store.persistence.domain.payment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 23, 2011
 */
@Embeddable
public class PaymentId implements Serializable {

	private static final long serialVersionUID = 4176341157636925851L;

	@Column(name = "id_order", nullable = false, updatable = false)
	private Integer idOrder;

	@Column(name = "id_customer", nullable = false, updatable = false)
	private Integer idCustomer;

	public PaymentId(){
		
	}
	
	/**
	 * 
	 * @param idCustomer
	 * @param idOrder
	 */
	public PaymentId(Integer idCustomer, Integer idOrder) {
		this.idCustomer = idCustomer;
		this.idOrder = idOrder;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result + ((idOrder == null) ? 0 : idOrder.hashCode());
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
		PaymentId other = (PaymentId) obj;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (idOrder == null) {
			if (other.idOrder != null)
				return false;
		} else if (!idOrder.equals(other.idOrder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentId [idOrder=").append(idOrder)
				.append(", idCustomer=").append(idCustomer).append("]");
		return builder.toString();
	}

}
