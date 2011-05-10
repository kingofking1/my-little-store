package mx.com.juca.store.business.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ProductOrder DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
@XmlType(name="ProductOrder")
public class ProductOrderDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1850024200265454106L;
	private ProductDTO productDTO;
	private OrderDTO orderDTO;
	private int numberItems;

	@XmlElement(name="product")
	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	@XmlElement(name="order")
	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	@XmlElement(name="totalNumberItems")
	public int getNumberItems() {
		return numberItems;
	}

	public void setNumberItems(int numberItems) {
		this.numberItems = numberItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orderDTO == null) ? 0 : orderDTO.hashCode());
		result = prime * result
				+ ((productDTO == null) ? 0 : productDTO.hashCode());
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
		ProductOrderDTO other = (ProductOrderDTO) obj;
		if (orderDTO == null) {
			if (other.orderDTO != null)
				return false;
		} else if (!orderDTO.equals(other.orderDTO))
			return false;
		if (productDTO == null) {
			if (other.productDTO != null)
				return false;
		} else if (!productDTO.equals(other.productDTO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductOrderDTO [productDTO=" + productDTO + ", orderDTO="
				+ orderDTO + ", numberItems=" + numberItems + "]";
	}

}
