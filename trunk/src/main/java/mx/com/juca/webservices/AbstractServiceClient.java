package mx.com.juca.webservices;


import java.io.IOException;
import java.io.InputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import kaplan.common.config.KapConfig;
import kaplan.common.exception.FatalException;
import kaplan.common.exception.ProcessException;
import kaptest.common.util.TimeSpan;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.BeanFactory;

import com.rivetlogic.core.rwf.cache.CacheAdapter;

/**
 * Abstract base class for all WS calls 
 * 
 * @author Juan Cruz
 * @since	Dec 9, 2011 
 *
 * @param <T>
 */
public abstract class AbstractServiceClient<T>{
	
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AbstractServiceClient.class); 
	
	private final Configuration config = new PropertiesConfiguration("my-application.properties");
	
	private String username = config.getString("kbs_web_service_userName");
	private String password = config.getString("kbs_web_service_password");
	private String serviceDomain = config.getString("kbs_domain");
	
	private static final Integer DEFAULT_TIME_OUT = config.getInt("kbs_web_service_timeout");		
	private static final String DISABLE_CACHING = config.getString("disable.flame.webservices.caching");
	private static final int SUCCESS_CODE = 200;
	public static final int SIMPLE_API_TYPE=1;
	public static final int JAXB_API_TYPE=2;
	public static final int TEXT_API_TYPE=3;
			
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public void setServiceDomain(String serviceDomain) {
		this.serviceDomain = serviceDomain;
	}

	/**
	 * @param serviceUrl
	 * @param params
	 * @param apiType - 1 -Simple API, 2 - JAXB
	 * @return
	 * @throws ProcessException
	 * @throws FatalException
	 */
	protected T invokeService(String serviceUrl, NameValuePair[] params, int apiType) throws ProcessException, FatalException {
		return invokeService(serviceUrl, params, apiType, false);
	}
	
	/**
	 * @param serviceUrl
	 * @param params
	 * @param apiType - 1 -Simple API, 2 - JAXB
	 * @param	isPost
	 * @return
	 * @throws ProcessException
	 * @throws FatalException
	 */
	protected T invokeService(String serviceUrl, NameValuePair[] params, int apiType, Boolean isPost) throws ProcessException, FatalException {
		HttpMethodBase methodBase = null;
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		
		ProtocolSocketFactory socketFactory = new MySSLSocketFactory(); 
        Protocol https = new Protocol( "https", socketFactory, 443); 
        Protocol.registerProtocol( "https", https );
		
		HttpClient client = new HttpClient(connectionManager);
		client.getParams().setParameter("http.socket.timeout", DEFAULT_TIME_OUT);
		
        Credentials defaultcreds = new UsernamePasswordCredentials(username, password);                       
                               
        client.getState().setCredentials(new AuthScope(serviceDomain, AuthScope.ANY_PORT, AuthScope.ANY_REALM), defaultcreds);
        client.getParams().setAuthenticationPreemptive(true);
                
        if(isPost){
        	methodBase = new PostMethod(serviceUrl);	
        }else{
        	methodBase = new GetMethod(serviceUrl);
        }
        
        methodBase.setDoAuthentication(true);
        
        if(params != null){
        	methodBase.setQueryString(params);
        }
        
        TimeSpan searchTimeSpan = new TimeSpan(true, "Invoking webservice at " + serviceUrl);    	
        try {
        	log.debug("Trying to hit " + serviceUrl + " with params: " + methodBase.getQueryString());
        	int status = client.executeMethod(methodBase);
        	if(status == SUCCESS_CODE) {
        		log.debug("Response: " + methodBase.getResponseBodyAsStream());  
        		if(JAXB_API_TYPE==apiType){
        			return readFromJAXBStream(methodBase.getResponseBodyAsStream());            		
        		}else if (SIMPLE_API_TYPE == apiType){
        			Serializer serializer = new Persister();
        			return readFromStream(serializer, methodBase.getResponseBodyAsStream());  
        		}else if (TEXT_API_TYPE == apiType){
        			return readAsString(methodBase.getResponseBodyAsString());
        		}else{
        			log.fatal("Unknown apiType provided ["+apiType+"]");
        		}

        	} else {
        		log.error("Trying to hit " + serviceUrl + " with params: " + methodBase.getQueryString());
        		log.error("Webservice failed and returned status: " + status);            	
        	}
        } catch(HttpException he) {
        	log.error("Trying to hit " + serviceUrl + " with params: " + methodBase.getQueryString());
        	log.error("Exception while invoking web service: " + he);
        } catch(IOException ioe) {
        	log.error("Trying to hit " + serviceUrl + " with params: " + methodBase.getQueryString());
        	log.error("Exception while invoking web service: " + ioe);
        }catch(Exception e) {
        	log.error(e);
        	// release any connection resources used by the method
        	methodBase.releaseConnection();
        	searchTimeSpan.logLap("Params: " + methodBase.getQueryString());
        	throw new FatalException(e);
        } finally {
        	// release any connection resources used by the method
        	methodBase.releaseConnection();
        	searchTimeSpan.logLap("Params: " + methodBase.getQueryString());
        }
		return null;
	}
	
	public abstract T readFromStream(Serializer serializer, InputStream responseBodyAsStream) throws Exception;
	
	public abstract T readFromJAXBStream(InputStream responseBodyAsStream) throws Exception;
	
	/**
	 * Returns response from API (HTTP Call) as String
	 * 
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	public T readAsString(String response) throws Exception{
		return null;
	}

}