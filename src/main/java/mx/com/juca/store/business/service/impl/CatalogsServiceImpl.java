package mx.com.juca.store.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.com.juca.store.business.dto.BrandDTO;
import mx.com.juca.store.business.dto.CategoryDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICatalogsService;
import mx.com.juca.store.persistence.dao.IBrandDAO;
import mx.com.juca.store.persistence.dao.ICategoryDAO;
import mx.com.juca.store.persistence.dao.IProductDAO;
import mx.com.juca.store.persistence.domain.Brand;
import mx.com.juca.store.persistence.domain.Category;
import mx.com.juca.store.persistence.domain.Product;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.util.Transfomer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation
 * 
 * @author Juan Carlos Cruz
 * @since February 12, 2011
 */
public class CatalogsServiceImpl implements ICatalogsService {
	
	private static final Logger log = Logger.getLogger(CatalogsServiceImpl.class);
	
	@Autowired
	private IProductDAO productDAO;
	
	@Autowired
	private ICategoryDAO categoryDAO;
	
	@Autowired
	private IBrandDAO brandDAO;	

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<ProductDTO> getProductsByCategory(CategoryDTO categoryDTO) throws ApplicationException{
		log.debug(categoryDTO);
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		try{
			List<Product> list = this.productDAO.findByProperty("category.idCategory", categoryDTO.getIdCategory());
			log.debug("Items retrieved "+list.size());	
			for(Product product: list){
				result.add(Transfomer.convertToDTO(product));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<ProductDTO> getRandomProducts(Integer max) throws ApplicationException {
		log.debug(max);
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		try{
			List<Product> list = this.productDAO.getRandomProducts(max);
			log.debug("Items retrieved "+list.size());	
			for(Product product: list){
				result.add(Transfomer.convertToDTO(product));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;	
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<CategoryDTO> getAllCategories() throws ApplicationException {
		List<CategoryDTO> result = new ArrayList<CategoryDTO>();
		try{
			List<Category> list = this.categoryDAO.getAll();
			log.debug("Items retrieved "+list.size());	
			for(Category category: list){
				result.add(Transfomer.convertToDTO(category));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean addCategory(CategoryDTO categoryDTO) throws ApplicationException {
		log.debug(categoryDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.categoryDAO.save(Transfomer.convertToDomain(categoryDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean modifyCategory(CategoryDTO categoryDTO) throws ApplicationException {
		log.debug(categoryDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.categoryDAO.update(Transfomer.convertToDomain(categoryDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean deleteCategory(CategoryDTO categoryDTO) throws ApplicationException {
		log.debug(categoryDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.categoryDAO.delete(Transfomer.convertToDomain(categoryDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean addProduct(ProductDTO productDTO) throws ApplicationException {
		log.debug(productDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.productDAO.save(Transfomer.convertToDomain(productDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean addProductStock(ProductDTO productDTO, Integer numberItems) throws ApplicationException {
		log.debug(productDTO);
		Boolean result = Boolean.FALSE;
		try{
			Product product = this.productDAO.findById(productDTO.getIdProduct());
			product.setQuantity(product.getQuantity() + numberItems);
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;	
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean modifyProduct(ProductDTO productDTO) throws ApplicationException {
		log.debug(productDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.productDAO.update(Transfomer.convertToDomain(productDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;	
	}

	@Override
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public Boolean deleteProduct(ProductDTO productDTO) throws ApplicationException {
		log.debug(productDTO);
		Boolean result = Boolean.FALSE;
		try{
			this.productDAO.delete(Transfomer.convertToDomain(productDTO));
			result = Boolean.TRUE;
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;	
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)	
	public ProductDTO getProductById(Integer idProduct)
			throws ApplicationException {
		log.debug(idProduct);
		ProductDTO result = null;
		try{
			Product product = this.productDAO.findById(idProduct);
			log.debug("Item retrieved "+product);	
			if(product==null){
				throw new ApplicationException("Product "+idProduct+", was not found");
			}
			result = Transfomer.convertToDTO(product);
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<ProductDTO> getAllProducts(Integer maxResults) throws ApplicationException {
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		try{
			List<Product> list = this.productDAO.getAll(maxResults);
			log.debug("Items retrieved "+list.size());	
			for(Product product: list){
				result.add(Transfomer.convertToDTO(product));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<ProductDTO> getProductsByExample(ProductDTO productDTO)
			throws ApplicationException {
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		try{
			List<Product> list = this.productDAO.findByExample(Transfomer.convertToDomain(productDTO));
			log.debug("Items retrieved "+list.size());	
			for(Product product: list){
				result.add(Transfomer.convertToDTO(product));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED, propagation=Propagation.NOT_SUPPORTED)
	public List<BrandDTO> getAllBrands() throws ApplicationException {
		List<BrandDTO> result = new ArrayList<BrandDTO>();
		try{
			List<Brand> list = this.brandDAO.getAll();
			log.debug("Items retrieved "+list.size());	
			for(Brand brand: list){
				result.add(Transfomer.convertToDTO(brand));
			}
		}catch (DataAccessException exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while accessing to the DB", exception, GenericConstants.DB_RELATED_ERROR);
		}catch (Exception exception) {
			log.error("Error while executing process", exception);
			throw new ApplicationException("Error while executing process: "+exception.getMessage(), exception);
		}
		return result;
	}	
	
}