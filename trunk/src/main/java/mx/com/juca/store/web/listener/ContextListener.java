package mx.com.juca.store.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


/**
 * @author Juan Carlos Cruz
 * @since Feb 14, 2011
 */
public class ContextListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent contextEvent) {
		log.info("Se ha detenido el contexto.");
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		log.info("Se ha iniciado el contexto.");
		/*
		 * Java is little eager to cache, so you must set the System property.
		 * You can do that by passing -Dnetworkaddress.cache.ttl=500 to your app-server, or by doing this: 
		 */		
		System.setProperty("networkaddress.cache.ttl", "500");
	}

}
