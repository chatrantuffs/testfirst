/**
 * 
 */
package com.dashboard.poc.custom.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class TokenModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	private Long userId;

	private String userName;

	private Date lastUpdated;
	
	TokenModel(){
		
	}
	
	public TokenModel(String token) {
		this.token = token;
		lastUpdated = new Date();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
