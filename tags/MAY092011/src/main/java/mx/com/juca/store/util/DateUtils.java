package mx.com.juca.store.util;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Feb 22, 2011
 */
public final class DateUtils {
	
	public static final String FORMAT_MMDDYYYY = "MM/dd/yyyy";
	public static final String FORMAT_MMYYYY = "MM/yyyy";
	

	private DateUtils(){		
	}

	public static Date parseDate(final String date, final String pattern){
		Date result = null;
		try{
			result = org.apache.commons.lang.time.DateUtils.parseDate(date, new String[]{pattern});
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String formatDate(final Date date, final String pattern){
		String result = null;
		try{
			result = DateFormatUtils.format(date, pattern);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/*
	public static String formatDate(final Date date, final String pattern) {
		String resultado = null;
		try {
			DateFormat formatter = new SimpleDateFormat(pattern);
			resultado = formatter.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultado;
	}
	
	public static Date parseDate(final String date, final String pattern) {
		Date resultado = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			DateFormat formatter = new SimpleDateFormat(pattern);
			calendar.setTime(formatter.parse(date));
			calendar.clear(Calendar.HOUR);
			calendar.clear(Calendar.MINUTE);
			calendar.clear(Calendar.SECOND);
			resultado = calendar.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultado;
	}
	*/
}
