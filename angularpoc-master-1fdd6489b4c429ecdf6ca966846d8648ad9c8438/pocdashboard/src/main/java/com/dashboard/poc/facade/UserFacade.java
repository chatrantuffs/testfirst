/**
 * 
 */
package com.dashboard.poc.facade;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.model.User;
import com.dashboard.poc.security.util.CustomEncryptionUtil;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class UserFacade {
	
	@Autowired
	CustomEncryptionUtil customEncryptionUtil;
	
	public User updateUser(final String username, final String password) throws CustomException{
		User user = new User();
		user.setUsername(username);
		user.setPassword(customEncryptionUtil.getEncryptedPassword(password));
		return user;
	}
	
	public User validUser(User user){
		user.setPassword(StringUtils.EMPTY);
		return user;
	}
}
