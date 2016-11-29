/**
 * 
 */
package com.dashboard.poc.custom.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class ResponseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object error;
	private Object data;
	
	public void setError(Object error) {
		this.error = error;
	}
	public Object getError() {
		return error;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	public Object getData() {
		return data;
	}
}
