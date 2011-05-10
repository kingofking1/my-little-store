package mx.com.juca.store.test;

import java.util.List;

import mx.com.juca.store.business.dto.BrandDTO;
import mx.com.juca.store.business.dto.CategoryDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.service.ICatalogsService;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test Cases for CatalogsService<BR>
 * This class doesn't extend from any class nor implement any external interface.<br>
 * I see the same behavior compared with other test cases.<BR>
 * On the other hand, it's more elegant if we extend from a parent Base class (I'll do that for other test cases).
 * 
 * @author Juan Carlos Cruz
 * @since Feb 14, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/conf/spring/daoApplicationContext.xml",
		"file:src/main/webapp/WEB-INF/conf/spring/serviceApplicationContext.xml" })
public class TestCatalogsService {
	
	static{
		DOMConfigurator.configure("src/main/webapp/WEB-INF/conf/log4j/log4j-config-minimal.xml");
	}

	private static final Logger log = Logger
			.getLogger(TestCatalogsService.class);

	@Autowired
	private ICatalogsService catalogsService;

	@BeforeClass
	public static void executeBeforeClass() {
		log.info("Execute to inizitalize class with @BeforeClass");
	}

	@AfterClass
	public static void executeAfterClass() {
		log.info("Execute to finalize class with @AfterClass");
	}

	@After
	public void executeAfter() {
		log.info("This should be executed after each method with @After");
	}
	
	@Before
	public void executeBefore() {
		log.info("This should be executed before each method with @Before");
	}

	@Test
	public void testGetAllCategories() {
		log.info("Starting test - testGetAllCategories");
		List<CategoryDTO> list = null;
		try {
			list = this.catalogsService.getAllCategories();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(list);
		for(Object object : list){
			log.debug(object);
		}
		log.info("End of test - testGetAllCategories");
	}
	
	@Test
	public void testGetProductsByCategory() {
		log.info("Starting test - testGetProductsByCategory");
		List<ProductDTO> list = null;
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setIdCategory(RandomUtils.JVM_RANDOM.nextInt(6));
		try {
			list = this.catalogsService.getProductsByCategory(categoryDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(list);
		for(Object object : list){
			log.debug(object);
		}
		log.info("End of test - testGetProductsByCategory");
	}
	
	@Test
	@Repeat(2)
	public void testGetRandomProducts() {
		log.info("Starting test - testGetRandomProducts");
		List<ProductDTO> list = null;
		try {
			list = this.catalogsService.getRandomProducts(RandomUtils.JVM_RANDOM.nextInt(6));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(list);
		for(Object object : list){
			log.debug(object);
		}
		log.info("End of test - testGetRandomProducts");
	}
	
	@Test
	@Rollback
	@Transactional
	@Repeat(2)
	public void testAddCategory() {
		log.info("Starting test - testAddCategory");
		Boolean result = false;
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Category "+RandomStringUtils.randomAlphanumeric(20));
		try {
			result = this.catalogsService.addCategory(categoryDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testAddCategory");
	}	
	
	@Test
	@Rollback
	@Transactional
	@Repeat(2)
	public void testAddProduct() {
		log.info("Starting test - testAddProduct");
		Boolean result = false;
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Product "+RandomStringUtils.randomAlphabetic(20));
		productDTO.setQuantity(RandomUtils.JVM_RANDOM.nextInt(20));
		productDTO.setCode(RandomStringUtils.randomNumeric(20));		
		productDTO.setPrice(new Float(RandomUtils.JVM_RANDOM.nextInt(1000)+"."+RandomUtils.JVM_RANDOM.nextInt(99)));
		productDTO.setDescription("Description N/A");
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setIdCategory(RandomUtils.JVM_RANDOM.nextInt(7));		
		productDTO.setCategoryDTO(categoryDTO);
		
		BrandDTO brandDTO = new BrandDTO(); 
		brandDTO.setIdBrand(RandomUtils.JVM_RANDOM.nextInt(22));
		productDTO.setBrandDTO(brandDTO);
		try {
			result = this.catalogsService.addProduct(productDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testAddProduct");
	}	
	
	@Test
	@Rollback
	@Transactional
	@Repeat(2)
	public void testAddProductStock() {
		log.info("Starting test - testAddProductStock");
		Boolean result = false;
		ProductDTO productDTO = new ProductDTO();
		productDTO.setIdProduct(RandomUtils.JVM_RANDOM.nextInt(6));
		try {
			result = this.catalogsService.addProductStock(productDTO, RandomUtils.JVM_RANDOM.nextInt(50));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testAddProductStock");
	}
	
	@Test
	@Rollback
	@Transactional
	@Repeat(1)
	public void testDeleteCategory() {
		log.info("Starting test - testDeleteCategory");
		Boolean result = false;
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setIdCategory(RandomUtils.JVM_RANDOM.nextInt(7));
		try {
			result = this.catalogsService.deleteCategory(categoryDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testDeleteCategory");
	}	
	
	@Test
	@Rollback
	@Transactional
	@Repeat(1)
	public void testDeleteProduct() {
		log.info("Starting test - testDeleteProduct");
		Boolean result = false;
		ProductDTO productDTO = new ProductDTO();
		productDTO.setIdProduct(RandomUtils.JVM_RANDOM.nextInt(6));
		try {
			result = this.catalogsService.deleteProduct(productDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testDeleteProduct");
	}
	
	@Test
	@Rollback
	@Transactional
	@Repeat(1)
	public void testModifyCategory() {
		log.info("Starting test - testModifyCategory");
		Boolean result = false;
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setIdCategory(RandomUtils.JVM_RANDOM.nextInt(7));
		categoryDTO.setName("NEW CATEGORY "+RandomStringUtils.randomAlphabetic(20));
		try {
			result = this.catalogsService.modifyCategory(categoryDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testModifyCategory");
	}	
	
	@Test
	@Rollback
	@Transactional
	@Repeat(1)
	public void testModifyProduct() {
		log.info("Starting test - testModifyProduct");
		Boolean result = false;
		ProductDTO productDTO = new ProductDTO();
		productDTO.setIdProduct(RandomUtils.JVM_RANDOM.nextInt(6));
		productDTO.setName("NEW PRODUCT "+RandomStringUtils.randomAlphabetic(20));
		try {
			result = this.catalogsService.modifyProduct(productDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertTrue(result);
		log.info("End of test - testModifyProduct");
	}	
}