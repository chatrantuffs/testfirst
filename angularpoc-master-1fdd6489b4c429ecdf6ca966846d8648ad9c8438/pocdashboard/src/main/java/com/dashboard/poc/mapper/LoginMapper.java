/**
 * 
 */
package com.dashboard.poc.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dashboard.poc.model.User;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public interface LoginMapper {
	public User validateUser(final User user);
	public List<User> loadOperatorByUserName(final String username);
}
