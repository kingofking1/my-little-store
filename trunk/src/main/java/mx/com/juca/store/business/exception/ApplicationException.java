package mx.com.juca.store.business.exception;

import mx.com.juca.store.util.GenericConstants;

/**
 * Own Exception
 * 
 * @author Juan Carlos Cruz
 * @since Feb 12, 2011
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 4011149165344148447L;
	private Exception encapsulatedException = new Exception();
	private String errorMessage;
	private Integer errorCode = GenericConstants.APPLICATION_ERROR;

	public ApplicationException(String error, Exception exception) {
		super(error, exception);
		this.errorMessage = error;
		this.encapsulatedException = exception;
	}
	
	public ApplicationException(String error, Exception exception, Integer errorCode) {
		super(error, exception);
		this.errorMessage = error;
		this.encapsulatedException = exception;
		this.errorCode = errorCode;
	}

	public ApplicationException(String error) {
		super(error);
		this.errorMessage = error;
	}
	
	public ApplicationException(String error, Integer errorCode) {
		super(error);
		this.errorMessage = error;
		this.errorCode = errorCode;
	}

	public Exception getEncapsulatedException() {
		return encapsulatedException;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "Error code: "+errorCode+". "+ errorMessage+ ". On "+ encapsulatedException.getStackTrace()[0];
	}

}
