package mx.com.juca.store.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.util.GenericConstants;


/**
 * All Controllers must extend from this class
 * 
 * @author Juan Cruz
 * @since Nov 21 2008
 * 
 */
public abstract class BaseCustomController {
	
	/**
	 * Get an object from session.
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return Object
	 */
	protected final Object getSessionObject(HttpServletRequest request,
			String attrName) {
		Object sessionObj = null;
		HttpSession session = request.getSession(false);

		if (session != null) {
			sessionObj = session.getAttribute(attrName);
		}

		return sessionObj;
	}

	/**
	 * Put an object into session.
	 * 
	 * @param request
	 * @param attrName
	 * @param attrValue
	 * 
	 */
	protected final void putObjectInSession(HttpServletRequest request,
			String attrName, Object attrValue) {
		HttpSession session = request.getSession(false);
    if (session != null) {
        session.setAttribute(attrName, attrValue);
    }
	}

	/**
	 * Verify if user is logged in or not.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @return boolean
	 */
	protected final boolean isLoggedIn(HttpServletRequest request) {
		UserContainer container = this.getUserContainer(request);
		if (container.getCustomerDTO() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get information about current user.
	 * 
	 * @param request
	 * 
	 * @return CustomerDTO
	 */
	protected final CustomerDTO getUserAuthenticated(HttpServletRequest request) {
		return this.getUserContainer(request).getCustomerDTO();
	}

	/**
	 * Get an object from context.
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return Object
	 */
	protected final Object getApplicationObject(HttpServletRequest request,
			String attrName) {
      Object result = null;
      HttpSession session = request.getSession(false);
      if (session != null) {
        result = session.getServletContext().getAttribute(attrName);
      }
      return result;
	}

	/**
	 * Get UserContainer.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @return UserContainer
	 */
	protected final UserContainer getUserContainer(HttpServletRequest request) {
		UserContainer userContainer = (UserContainer) getSessionObject(request,GenericConstants.KEY_USER_CONTAINER);

		// Create UserContainer for this user if it doesn't exist...
		if (userContainer == null) {
			userContainer = new UserContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute(GenericConstants.KEY_USER_CONTAINER,userContainer);
		}
		return userContainer;
	}
	
	/**
	 * Generates Authentication cookie, to keep user authenticated
	 * 
	 * @param request
	 * @param response
	 */
	protected final void generateAuthenticationCookie(HttpServletRequest request, HttpServletResponse response){
		//Generate cookie content
		StringBuffer value =  new StringBuffer();
		value.append(this.getUserAuthenticated(request).getEmail());
		value.append("|-|");
		value.append(request.getSession().getId());
		value.append("|-|");
		value.append(request.getSession().getLastAccessedTime());
		value.append("|-|");
		value.append(this.getUserAuthenticated(request).getPassword());

		Cookie cookie = new Cookie(GenericConstants.AUTH_SESSION,value.toString());
		cookie.setPath("/");
		cookie.setMaxAge((GenericConstants.TIME_TO_LIVE) * 5);

		//Add cookie to response
		response.addCookie(cookie);
	}
	
	/**
	 * Generates Authentication Header (just for fun)
	 * 
	 * @param request
	 * @param response
	 */
	protected final void generateAuthenticationHeader(HttpServletRequest request, HttpServletResponse response){
		StringBuffer value =  new StringBuffer();
		value.append(request.getSession().getId());
		value.append("|");
		value.append(request.getSession().getLastAccessedTime());
		
		response.setHeader(GenericConstants.AUTH_SESSION, value.toString());
	}
	
	/**
	 * Verifies if Authentication cookie is present
	 * 
	 * @param request
	 * @return boolean
	 */
	protected final boolean isAuthenticationCookiePresent(HttpServletRequest request){
		boolean result = false;
		Cookie cookie = this.getAuthenticationCookie(request);
    if(cookie!=null){
      result = true;
    }  
		return result;
	}
	
	/**
	 * Retrieve Authentication cookie
	 * @param request
	 * @return Cookie
	 */
	protected final Cookie getAuthenticationCookie(HttpServletRequest request){
		Cookie result = null;
		for (Cookie cookie : request.getCookies()){
			if(GenericConstants.AUTH_SESSION.equalsIgnoreCase(cookie.getName())){
				result = cookie;
			}
		}
		return result;
	}
	
	/**
	 * Expires Authentication cookie
	 * @param request
	 * @param response
	 */
	protected final void expireAuthenticationCookie(HttpServletRequest request, HttpServletResponse response){
		for (Cookie cookie : request.getCookies()){
			if(GenericConstants.AUTH_SESSION.equalsIgnoreCase(cookie.getName())){
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}
	
}
