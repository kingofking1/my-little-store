package mx.com.juca.store.business.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Brand DTO
 * 
 * @author Juan Carlos Cruz
 * @since February 9, 2011
 */
@XmlType(name="Brand")
public class BrandDTO implements java.io.Serializable {

	private static final long serialVersionUID = -5464250120107393669L;
	private Integer idBrand;
	private String name;
	private List<ProductDTO> products = new ArrayList<ProductDTO>(0);

	@XmlElement(name="brandId")
	public Integer getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Integer idBrand) {
		this.idBrand = idBrand;
	}

	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(nillable=true, required=false)
	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BrandDTO other = (BrandDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BrandDTO [idBrand=" + idBrand + ", name=" + name + "]";
	}

}
