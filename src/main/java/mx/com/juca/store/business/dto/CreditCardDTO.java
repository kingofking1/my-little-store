package mx.com.juca.store.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * CreditCard DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
public class CreditCardDTO implements java.io.Serializable {

	private static final long serialVersionUID = -7636418839908102248L;
	private Integer idCreditCard;
	private CustomerDTO customerDTO;
	private String number;
	private String securityCode;
	private String type;
	private String expirationDate;
	private Date creationTs;
	private Date modificationTs;
	private List<OrderDTO> orders = new ArrayList<OrderDTO>(0);

	public Integer getIdCreditCard() {
		return idCreditCard;
	}

	public void setIdCreditCard(Integer idCreditCard) {
		this.idCreditCard = idCreditCard;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getCreationTs() {
		return creationTs;
	}

	public void setCreationTs(Date creationTs) {
		this.creationTs = creationTs;
	}

	public Date getModificationTs() {
		return modificationTs;
	}

	public void setModificationTs(Date modificationTs) {
		this.modificationTs = modificationTs;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		CreditCardDTO other = (CreditCardDTO) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCardDTO [idCreditCard=" + idCreditCard + ", number=Ending in "
				+ StringUtils.right(number,4) + ", securityCode=" + securityCode + ", type=" + type
				+ ", expirationDate=" + expirationDate + ", creationTs="
				+ creationTs + ", modificationTs="+modificationTs+"]";
	}

}
