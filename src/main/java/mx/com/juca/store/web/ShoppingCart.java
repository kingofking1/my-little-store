package mx.com.juca.store.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.com.juca.store.business.dto.ProductDTO;

/**
 * 
 * @author Juan Carlos Cruz
 * @since February 11, 2011
 */
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1579942628138039921L;

	private List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(0);

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}
	
	/**
	 * @param productDTO
	 */
	public void addProductDTO(ProductDTO productDTO) {
		if(this.productDTOs.indexOf(productDTO)==-1){
			this.productDTOs.add(productDTO);
		}else{
			ProductDTO productDTO2 = this.productDTOs.get(this.productDTOs.indexOf(productDTO));
			productDTO2.setQuantity(productDTO2.getQuantity() + productDTO.getQuantity());
			this.updateProductDTO(productDTO2);
		}		
	}
	
	/**
	 * @param productDTO
	 */
	public void updateProductDTO(ProductDTO productDTO) {
		this.removeProductDTO(productDTO);
		this.addProductDTO(productDTO);
	}
	
	/**
	 * @param productDTO
	 */
	public void removeProductDTO(ProductDTO productDTO) {	
		if(this.productDTOs.indexOf(productDTO)!=-1){
			this.productDTOs.remove(this.productDTOs.indexOf(productDTO));	
		}		
	}	
}