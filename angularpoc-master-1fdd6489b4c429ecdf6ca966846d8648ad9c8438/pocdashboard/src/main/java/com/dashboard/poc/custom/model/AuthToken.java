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
public class AuthToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private Date issuedWhen;
	private Date expiresWhen;
	private String isuuedBy;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the issuedWhen
	 */
	public Date getIssuedWhen() {
		return issuedWhen;
	}
	/**
	 * @param issuedWhen the issuedWhen to set
	 */
	public void setIssuedWhen(Date issuedWhen) {
		this.issuedWhen = issuedWhen;
	}
	/**
	 * @return the expiresWhen
	 */
	public Date getExpiresWhen() {
		return expiresWhen;
	}
	/**
	 * @param expiresWhen the expiresWhen to set
	 */
	public void setExpiresWhen(Date expiresWhen) {
		this.expiresWhen = expiresWhen;
	}
	/**
	 * @return the isuuedBy
	 */
	public String getIsuuedBy() {
		return isuuedBy;
	}
	/**
	 * @param isuuedBy the isuuedBy to set
	 */
	public void setIsuuedBy(String isuuedBy) {
		this.isuuedBy = isuuedBy;
	}

}
