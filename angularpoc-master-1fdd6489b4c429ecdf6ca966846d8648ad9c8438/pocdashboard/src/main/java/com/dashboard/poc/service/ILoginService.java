/**
 * 
 */
package com.dashboard.poc.service;

import java.util.List;

import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.model.User;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public interface ILoginService {
	public User validateUser(final String username, final String password) throws CustomException;
	public List<User> loadOperatorByUserName(final String username);
}
