package mx.com.juca.store.business.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Product DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
@XmlType(name="Product")
public class ProductDTO implements java.io.Serializable {

	private static final long serialVersionUID = -4373729623746800251L;
	private Integer idProduct;
	private CategoryDTO categoryDTO = new CategoryDTO();
	private String name;
	private String description;
	private String code;
	private Float price = 0F;
	private int quantity;
	private BrandDTO brandDTO = new BrandDTO();
	private List<ProductOrderDTO> productOrders = new ArrayList<ProductOrderDTO>(0);	

	@XmlElement(name="productId")
	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	@XmlElement(name="category")
	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlElement(name="price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@XmlElement(name="itemsInStock")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@XmlElement(name="brand")
	public BrandDTO getBrandDTO() {
		return brandDTO;
	}

	public void setBrandDTO(BrandDTO brandDTO) {
		this.brandDTO = brandDTO;
	}

	@XmlElement(nillable=true, required=false)
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ProductDTO other = (ProductDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO [idProduct=" + idProduct+ ", name=" + name + ", description="
				+ description + ", code=" + code + ", price=" + price
				+ ", quantity=" + quantity+ "]";
	}
}