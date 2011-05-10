package mx.com.juca.store.business.dto.payment;

import java.io.Serializable;
import java.util.Date;

public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = -4900400851877818079L;

	private Integer idOrder;

	private Integer idCustomer;

	private Float totalAmountTransaction;

	private String transactionNumber;

	private Date creationTs;

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
		PaymentDTO other = (PaymentDTO) obj;
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
		builder.append("PaymentDTO [idOrder=");
		builder.append(idOrder);
		builder.append(", idCustomer=");
		builder.append(idCustomer);
		builder.append(", totalAmountTransaction=");
		builder.append(totalAmountTransaction);
		builder.append(", transactionNumber=");
		builder.append(transactionNumber);
		builder.append(", creationTs=");
		builder.append(creationTs);
		builder.append("]");
		return builder.toString();
	}

}
