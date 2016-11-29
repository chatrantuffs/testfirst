package com.dashboard.poc.security.util;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dashboard.poc.custom.model.TokenModel;
import com.dashboard.poc.model.User;
import com.dashboard.poc.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	static Logger logger = Logger.getLogger(CustomAuthenticationProvider.class.getName());

	@Autowired
	UserService userService;
	/** The token map. */
	static public HashMap<String, TokenModel> tokenMap = new HashMap<String, TokenModel>();
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		@SuppressWarnings("unused")
		String operatorStr = userService.loadOperatorByOparatorName(username);
				
		final String userStr = "{\"username\":\"admin\",\"password\":\"admin\"}";
		@SuppressWarnings("unused")
		User operatorObj = null;
		try {
			operatorObj = mapper.readValue(userStr,
			User.class);
			} catch (Exception e) {
				logger.error(e,e);
			}
				
			@SuppressWarnings("unused")
			final String password = authentication.getCredentials().toString();
				//String token = userService.authenticate(username, password);
				
		return null;
	}

}
