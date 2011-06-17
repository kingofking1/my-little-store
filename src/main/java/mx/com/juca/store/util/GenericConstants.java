package mx.com.juca.store.util;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 12, 2011
 */
public interface GenericConstants {

	String AUTH_SESSION = "AUTH_SESSION";
	Integer TIME_TO_LIVE = 60; 
	
	Integer APPLICATION_ERROR = 99;
	Integer DB_RELATED_ERROR = 8075;
	
	Integer VISA = 0;
	Integer AMEX = 1;
	Integer MASTERCARD = 2;
	Integer OTHER_CARD = 3;
	
	String VISA_DESC = "VISA";
	String AMEX_DESC = "AMEX";
	String MASTERCARD_DESC = "MASTERCARD";
	String OTHER_CARD_DESC = "OTHER";

	String KEY_USER_CONTAINER = "KEY_USER_CONTAINER";
	String KEY_USER = "KEY_USER";
	
	String KEY_INFO = "KEY_INFO";
	String KEY_WARNING = "KEY_WARNING";
	String KEY_ERROR = "KEY_ERROR";
	String KEY_FATAL = "KEY_FATAL";	
	
	Integer MAX_NUMBER_PRODUCTS = 10;
	Integer MIN_NUMBER_PRODUCTS = 3;
	
	String KEY_LIST_ITEMS = "KEY_LIST_ITEMS";
	String KEY_SINGLE_ITEM = "KEY_SINGLE_ITEM";
	String KEY_MAP_TYPE_CARDS = "KEY_MAP_TYPE_CARDS";
	String NOT_AVAILABLE = "NOT AVAILABLE";
	
	String KEY_LIST_CATEGORIES = "KEY_LIST_CATEGORIES";
	String KEY_LIST_BRANDS = "KEY_LIST_BRANDS";
		
}
