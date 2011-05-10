package mx.com.juca.store.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICatalogsService;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.BaseCustomController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 20, 2011
 */
@Controller
@RequestMapping(value = "/shoppingCart/*")
public class ShoppingCartController extends BaseCustomController {
	
	private static final Logger log = Logger.getLogger(ShoppingCartController.class);
	
	@Autowired
	private ICatalogsService catalogsService;

	@RequestMapping(value = "addItem.do")
	public String addItem(HttpServletRequest request, ModelMap modelMap, 
			@RequestParam("numberOfItems") final Integer numberOfItems,
			@RequestParam("idProduct") final Integer idProduct,
			@RequestParam("price") final Float price,
			@RequestParam("name") final String name) {
		
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		log.debug("Add "+numberOfItems+" of "+idProduct+":"+name+", unit price: "+price);
		
		try {
			ProductDTO productDTO = catalogsService.getProductById(idProduct);
			productDTO.setPrice(price);//In case price is updated you keep the price you saw
			productDTO.setQuantity(numberOfItems); //Number of Items to Add			
			this.getUserAuthenticated(request).getShoppingCart().addProductDTO(productDTO);
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}		
		
		modelMap.put("name", name);
		
		return "shopping_cart/shopping_cart_confirmation";
	}
	
	@RequestMapping(value = "updateItem.do")
	public String updateQuantities(HttpServletRequest request, ModelMap modelMap, 
			@RequestParam("numberOfItems") final Integer numberOfItems,
			@RequestParam("idProduct") final Integer idProduct) {
		
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		log.debug("Updating quantity="+numberOfItems+" for product="+idProduct);
		
		try {
			ProductDTO productDTO = catalogsService.getProductById(idProduct);				
			if(numberOfItems>0){
				productDTO.setQuantity(numberOfItems); //Number of Items to Add			
				this.getUserAuthenticated(request).getShoppingCart().updateProductDTO(productDTO);
			}else{
				//remove product when it's set to 0
				this.getUserAuthenticated(request).getShoppingCart().removeProductDTO(productDTO);
			}
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}				
		return "redirect:/shoppingCart/viewShoppingCart.do";
	}	
	
	@RequestMapping(value="viewShoppingCart.do")
	public String viewShoppingCartContent(HttpServletRequest request, ModelMap modelMap){
		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}
		log.debug("Show content of the Shopping cart");
		Float totalCart = 0F;
		Integer totalItemsCart = 0;
		List<ProductDTO> result = this.getUserAuthenticated(request).getShoppingCart().getProductDTOs();
		
		//Calculate Total in Cart
		for(ProductDTO productDTO : result){
			totalItemsCart = totalItemsCart + productDTO.getQuantity();
			totalCart = new Float(totalCart + (productDTO.getQuantity()*productDTO.getPrice()));
		}
		log.debug("Products in Cart: "+result.size()+" of "+this.getUserAuthenticated(request).getEmail()+", totalPieces="+totalItemsCart);
		modelMap.put("totalItemsCart", totalItemsCart);
		modelMap.put("totalCart", totalCart);
		modelMap.put(GenericConstants.KEY_LIST_ITEMS, result); 
		
		return "shopping_cart/shopping_cart";
	}
}
