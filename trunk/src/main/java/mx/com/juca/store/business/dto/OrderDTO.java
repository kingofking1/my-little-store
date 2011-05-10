package mx.com.juca.store.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Order DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
@XmlType(name="Order")
public class OrderDTO implements java.io.Serializable {

	private static final long serialVersionUID = -638727840774890638L;	
	private Integer idOrder;	
	private CustomerDTO customerDTO;	
	private Float tax;	
	private Float totalSale;	
	private Date creationTs;	
	private Date modificationTs;
	private List<ProductOrderDTO> productOrders = new ArrayList<ProductOrderDTO>(
			0);

	@XmlElement(name="orderNumber")
	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	@XmlElement(name="customer")
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	@XmlElement(name="tax")
	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	@XmlElement(name="totalSale")
	public Float getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(Float totalSale) {
		this.totalSale = totalSale;
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

	@XmlElement(name="productOrdersList", nillable=true, required=false)
	public List<ProductOrderDTO> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<ProductOrderDTO> productOrders) {
		this.productOrders = productOrders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		OrderDTO other = (OrderDTO) obj;
		if (idOrder == null) {
			if (other.idOrder != null)
				return false;
		} else if (!idOrder.equals(other.idOrder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDTO [idOrder=" + idOrder+ ", tax=" + tax + ", totalSale=" + totalSale
				+ ", creationTs=" + creationTs + ", modificationTs="+modificationTs+"]";
	}

}
