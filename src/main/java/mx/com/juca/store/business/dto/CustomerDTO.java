package mx.com.juca.store.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.ShoppingCart;

import org.apache.commons.lang.StringUtils;

/**
 * Customer DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
@XmlType(name="Customer")
public class CustomerDTO implements java.io.Serializable {

	private static final long serialVersionUID = 3164700478795592401L;
	private int idCustomer;
	private String email;
	private String password = StringUtils.EMPTY;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date creationTs;
	private Date modificationTs;
	private List<OrderDTO> orders = new ArrayList<OrderDTO>(0);
	private List<CreditCardDTO> creditCards = new ArrayList<CreditCardDTO>(0);
	private ShoppingCart shoppingCart;

	@XmlElement(name="customerNumber")
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name="password", defaultValue=GenericConstants.NOT_AVAILABLE)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name="firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name="middleName")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@XmlElement(name="lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name="creationDate")
	public Date getCreationTs() {
		return creationTs;
	}

	public void setCreationTs(Date creationTs) {
		this.creationTs = creationTs;
	}

	@XmlElement(name="modificationDate", required=false)
	public Date getModificationTs() {
		return modificationTs;
	}

	public void setModificationTs(Date modificationTs) {
		this.modificationTs = modificationTs;
	}

	@XmlElement(nillable=true, required=false)
	public List<CreditCardDTO> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCardDTO> creditCards) {
		this.creditCards = creditCards;
	}

	@XmlElement(nillable=true, required=false)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@XmlElement(nillable=true, required=false)
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		CustomerDTO other = (CustomerDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(password==null){
			password = StringUtils.EMPTY;
		}
		return "CustomerDTO [idCustomer=" + idCustomer + ", email=" + email
				+ ", password=" + StringUtils.leftPad(StringUtils.right(password,2), password.length()-2, '*') + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", creationTs=" + creationTs + ", modificationTs="+modificationTs+"]";
	}

}
