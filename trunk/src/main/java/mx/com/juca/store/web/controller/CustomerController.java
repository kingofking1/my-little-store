package mx.com.juca.store.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.exception.ApplicationException;
import mx.com.juca.store.business.service.ICustomerService;
import mx.com.juca.store.util.GenericConstants;
import mx.com.juca.store.web.ShoppingCart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 18, 2011
 */
@Controller
@RequestMapping(value = "/authentication/*")
public class CustomerController extends mx.com.juca.store.web.BaseCustomController{
	
	private static final Logger log = Logger.getLogger(CustomerController.class);

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(value = "doLogin.do", method=RequestMethod.POST)
	public String authenticateUser(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@ModelAttribute("CustomerDTO") CustomerDTO paramCustomerDTO,
			@RequestParam("rememberMe") Boolean rememberMe) {
		
		log.debug("authenticateUser="+paramCustomerDTO.getEmail());
		CustomerDTO customerDTO = null;
		
		if(StringUtils.isNotEmpty(paramCustomerDTO.getEmail()) && StringUtils.isNotEmpty(paramCustomerDTO.getPassword())){//if user didn't provide either fields
			try {
				customerDTO = customerService.doSignIn(paramCustomerDTO.getEmail(), paramCustomerDTO.getPassword());
			} catch (ApplicationException ex) {
				log.error(ex);
				modelMap.put(GenericConstants.KEY_FATAL, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
			}			
			if(customerDTO!=null){				
				log.debug("User authenticated successfully");
				//Adding shoppingCart and put customer into UserContainer
				customerDTO.setShoppingCart(new ShoppingCart());
				modelMap.put(GenericConstants.KEY_USER, customerDTO);
				this.getUserContainer(request).setCustomerDTO(customerDTO);
				
				if(rememberMe){
					log.debug("Populate Headers and Cookie");
					this.generateAuthenticationCookie(request, response);
					this.generateAuthenticationHeader(request, response);
				}
				
				return "redirect:/showLandingPage.do"; //Send user to Landig page
			}else{
				modelMap.put(GenericConstants.KEY_ERROR, paramCustomerDTO.getEmail()+" and password provided were not found, please review your password and user Id and try again");
			}
			return "authentication/login";//show jsp
		}else{
			modelMap.put(GenericConstants.KEY_ERROR, "Email and Password are mandatory fields.");
		}
		return "redirect:/authentication/login.do";
	}
	
	@RequestMapping(value = "notLoggedIn.do")
	public String notLoggedIn(HttpServletRequest request, ModelMap modelMap) {
		log.debug("notLoggedIn");
		modelMap.put(GenericConstants.KEY_INFO, "Your session has expired. Please sign up again.");
		return "authentication/logout"; // show jsp
	}	
	

	@RequestMapping(value = "logout.do")
	public String logoutUser(HttpServletRequest request, HttpServletResponse response, 
			ModelMap modelMap) {
		log.debug("logoutUser");
		this.getUserContainer(request).cleanUp();
		if(request.getSession(false)!=null){
			request.getSession(false).invalidate();
		}
		//Delete cookie
		this.expireAuthenticationCookie(request, response);
		return "redirect:/authentication/login.do";
	}
	
	@RequestMapping(value = "login.do")
	public String showLogin(HttpServletRequest request, ModelMap modelMap) {
		log.debug("Looking for Auth Cookie");
		if(this.isAuthenticationCookiePresent(request)){
			log.debug("Present");
			Cookie cookie = this.getAuthenticationCookie(request);
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setEmail(StringUtils.substringBefore(cookie.getValue(), "|-|"));
			customerDTO.setPassword(StringUtils.substringAfterLast(cookie.getValue(), "|-|"));
			return this.authenticateUser(request, null, modelMap, customerDTO, false);	
		}else{
			log.debug("Nothing");
			log.debug("Create new DTO and Show login.jsp");
			CustomerDTO customerDTO = new CustomerDTO();
			modelMap.put("CustomerDTO", customerDTO);			
			return "authentication/login";//show jsp
		}
	}
	
	
	@RequestMapping(value = "registerCustomer.htm",method=RequestMethod.GET)
	public String showSignUpScreen(HttpServletRequest request, ModelMap modelMap, 
			CustomerDTO customerDTO){
		log.debug("Create new DTO and Show register_customer");
		if(customerDTO==null){
			customerDTO = new CustomerDTO();
		}
		modelMap.put("CustomerDTO", customerDTO);
		return "sign_up/register_customer";
	}
	
	@RequestMapping(value = "registerCustomer.htm",method=RequestMethod.POST)
	public String signUpCustomer(HttpServletRequest request, ModelMap modelMap, 
			@ModelAttribute("CustomerDTO") CustomerDTO paramCustomerDTO,
			@RequestParam(value="captchaText", required=true) final String verificationText){
		
		log.debug("Signup customer");
		Boolean result = false;
		Boolean isResponseCorrect = false;
		
		String sessionId = request.getSession().getId();
		try {
			isResponseCorrect = this.captchaService.validateResponseForID(request.getSession().getId(), verificationText);
		}catch(CaptchaServiceException ex) {
			//should not happen, may be thrown if the id is not valid
			log.warn("Session Id "+sessionId+" Not valid",ex);
		}

		try {		
			if(isResponseCorrect){
				result = this.customerService.doSignUp(paramCustomerDTO);
			}else{
				modelMap.put(GenericConstants.KEY_ERROR, "Verification text incorrect, please type it again");
			}
		}catch(ApplicationException ex){
			log.error(ex);
			modelMap.put(GenericConstants.KEY_ERROR, "ErrorCode "+ex.getErrorCode()+": "+ex.getErrorMessage());
		} catch (Exception ex) {
			log.error("Error while processing sign up",ex);
			modelMap.put(GenericConstants.KEY_FATAL, ex.getMessage()+". Cause: "+ex.getCause().getCause().getMessage());
		}
		if(result){
			log.debug("Signup was successfull for "+paramCustomerDTO.getEmail()+", proceed to SignIn");
			return this.authenticateUser(request, null, modelMap, paramCustomerDTO, false);	
		}
		
		return this.showSignUpScreen(request, modelMap, paramCustomerDTO);
	}	
}