package mx.com.juca.store.web;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import mx.com.juca.store.business.dto.CustomerDTO;

import org.apache.log4j.Logger;

/**
 * Container for users authenticated into application, specially designed to keep in a safe and accessible place 
 * information about user that is logged in.<BR>
 * This class is in charge to free resources once session has reached its timeout.
 * 
 * @author Juan Carlos Cruz
 * @since February 11, 2011
 */
public class UserContainer implements HttpSessionBindingListener {
	private static final Logger log = Logger.getLogger(UserContainer.class);

	private CustomerDTO customerDTO;

	// Constructor por defecto
	public UserContainer() {
		super();
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
		//log.debug("Ading object to session: "+arg0.getName());
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		log.debug("Removing object from session: "+arg0.getName());
	}

	// Liberamos recursos de la sesión.
	public void cleanUp() {
		if (this.customerDTO != null) {
			log.info("Liberando sesión del usuario: " + customerDTO.getEmail());
		}
		setCustomerDTO(null);
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		if(customerDTO!=null){
			log.debug("Adding to UserContainer: " + customerDTO.getEmail());
		}
		this.customerDTO = customerDTO;
	}

	public String toString() {
		String stringValue = ""; 
		if(this.customerDTO!=null){
			stringValue = customerDTO.getEmail();
		}
		return stringValue;
	}	
}