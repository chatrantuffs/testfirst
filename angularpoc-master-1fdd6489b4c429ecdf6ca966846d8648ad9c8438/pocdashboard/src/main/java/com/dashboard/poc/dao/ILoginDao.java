/**
 * 
 */
package com.dashboard.poc.dao;

import java.util.List;

import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.model.User;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public interface ILoginDao {
	public User validateUser(final User user) throws CustomException;
	public List<User> loadOperatorByUserName(final String username) throws CustomException;
}
