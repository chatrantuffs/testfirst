/**
 * 
 */
package com.dashboard.poc.exception;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public class CustomException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message; 
	
	public CustomException(String message) { 
		this.message = message; 
	} 
	
	public CustomException(Throwable cause, String message) { 
		super(cause); 
		this.message = message; 
	} 
	
	public String getMessage() { 
		return message;
	}
	
    public CustomException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public CustomException() {
    }
}
