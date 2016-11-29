/**
 * 
 */
package com.dashboard.poc.service;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public interface IUserService {
	public String loadOperatorByOparatorName(final String username);
	public String authenticate(final String name, final String password);
}
