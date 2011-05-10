package mx.com.juca.store.web;

import javax.servlet.http.HttpServletRequest;
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
		session.setAttribute(attrName, attrValue);
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
		return request.getSession().getServletContext().getAttribute(attrName);
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
}
