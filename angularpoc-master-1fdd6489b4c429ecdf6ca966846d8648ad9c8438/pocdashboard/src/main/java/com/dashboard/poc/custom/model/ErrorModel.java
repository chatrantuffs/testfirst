/**
 * 
 */
package com.dashboard.poc.custom.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class ErrorModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int errorCode = 0;
	private String errorDescription = StringUtils.EMPTY;
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public Object getErrorDescription() {
		return errorDescription;
	}
}
