package mx.com.juca.store.business.service;

import java.util.List;

import mx.com.juca.store.business.dto.BrandDTO;
import mx.com.juca.store.business.dto.CategoryDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.exception.ApplicationException;

/**
 * Service Interface
 * 
 * @author Juan Carlos Cruz
 * @since February 10, 2011
 */
public interface ICatalogsService {

	/**
	 * @param categoryDTO
	 * @return List<ProductDTO>
	 */
	public List<ProductDTO> getProductsByCategory(final CategoryDTO categoryDTO) throws ApplicationException;
	
	/**
	 * Always retrieves products in stock
	 * 
	 * @param max	Limit of Items to retrieve (Valid values: 2 - 10)
	 * @return List<ProductDTO>
	 */
	public List<ProductDTO> getRandomProducts(final Integer max) throws ApplicationException;
	
	/**
	 * 
	 * @return	List<CategoryDTO>
	 */
	public List<CategoryDTO> getAllCategories() throws ApplicationException;
	
	/**
	 * 
	 * @param categoryDTO
	 * @return	Boolean
	 */
	public Boolean addCategory(final CategoryDTO categoryDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param categoryDTO
	 * @return	Boolean
	 */
	public Boolean modifyCategory(final CategoryDTO categoryDTO) throws ApplicationException;	
	
	/**
	 * Can't delete categories with Products associated
	 * 
	 * @param categoryDTO
	 * @return	Boolean
	 */
	public Boolean deleteCategory(final CategoryDTO categoryDTO) throws ApplicationException;	
	
	/**
	 * 
	 * @param productDTO
	 * @return	Boolean
	 */
	public Boolean addProduct(final ProductDTO productDTO) throws ApplicationException;
	
	/**
	 * 
	 * @param productDTO
	 * @param	numberItems
	 * @return	Boolean
	 */
	public Boolean addProductStock(final ProductDTO productDTO, final Integer numberItems) throws ApplicationException;	

	/**
	 * 
	 * @param productDTO
	 * @return	Boolean
	 */
	public Boolean modifyProduct(final ProductDTO productDTO) throws ApplicationException;	
	
	/** 
	 * @param productDTO
	 * @return	Boolean
	 */
	public Boolean deleteProduct(final ProductDTO productDTO) throws ApplicationException;

	/**
	 * 
	 * @param idProduct
	 * @return	ProductDTO
	 * @throws ApplicationException
	 */
	public ProductDTO getProductById(final Integer idProduct) throws ApplicationException;

	/**
	 * @param maxResults	Could be NULL
	 * @return	List<ProductDTO>
	 * @throws ApplicationException
	 */
	public List<ProductDTO> getAllProducts(final Integer maxResults) throws ApplicationException;

	/**
	 * 
	 * @param productDTO
	 * @return	List<ProductDTO>
	 * @throws ApplicationException
	 */
	public List<ProductDTO> getProductsByExample(ProductDTO productDTO) throws ApplicationException;
	
	/**
	 * 
	 * @return	List<BrandDTO>
	 */
	public List<BrandDTO> getAllBrands() throws ApplicationException;	
}
