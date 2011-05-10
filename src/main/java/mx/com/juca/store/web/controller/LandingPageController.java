package mx.com.juca.store.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICatalogsService;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.BaseCustomController;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 18, 2011
 */
@Controller
public class LandingPageController extends BaseCustomController {
	
	private static final Logger log = Logger.getLogger(LandingPageController.class);
	
	@Autowired
	private ICatalogsService catalogsService;

	@RequestMapping(value = "/showLandingPage.do")
	public String showInitialPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		if(!this.isLoggedIn(request)){//Verify if user is authenticated
			return "redirect:/authentication/notLoggedIn.do";
		}		
		log.debug("show initial page / welcome page");		
		log.debug("Get random products");
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		Integer max = RandomUtils.JVM_RANDOM.nextInt(GenericConstants.MAX_NUMBER_PRODUCTS);
		if(max==0){
			max = GenericConstants.MIN_NUMBER_PRODUCTS; //
		}
		try {
			list = catalogsService.getRandomProducts(max);
			modelMap.put(GenericConstants.KEY_LIST_ITEMS, list);
		} catch (ApplicationException ex) {
			log.error(ex);
			modelMap.put(GenericConstants.KEY_FATAL, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		}

		return "start/landing_page";//show jsp
	}
}
