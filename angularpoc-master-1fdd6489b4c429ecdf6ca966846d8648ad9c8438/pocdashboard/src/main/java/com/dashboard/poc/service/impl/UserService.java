/**
 * 
 */
package com.dashboard.poc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dashboard.poc.dao.ILoginDao;
import com.dashboard.poc.facade.CustomFacade;
import com.dashboard.poc.model.User;
import com.dashboard.poc.security.util.CustomNimbusJoseJWTUtil;
import com.dashboard.poc.service.IUserService;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Service
public class UserService implements IUserService{

	static Logger logger = Logger.getLogger(UserService.class.getName());
	
	@Autowired
	CustomFacade customFacade;
	
	@Autowired
	private ILoginDao loginDao;
	
	@Autowired
	CustomNimbusJoseJWTUtil customNimbusJoseJWTUtil;

	/*@Resource(name = "authenticationManager")
	private AuthenticationManager authManager;*/
	
	@Override
	public String loadOperatorByOparatorName(final String username) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			List<User> userList = loginDao
					.loadOperatorByUserName(username);

			if (!userList.isEmpty()) {
				user = userList.get(0);
			}

		} catch (Exception exception) {
			logger.error(exception, exception);
		}
		return customFacade.convertObjToString(user);
	}

	@Override
	public String authenticate(final String username, final String password) {
		// TODO Auto-generated method stub
		Collection<SimpleGrantedAuthority> grantedAuth = new ArrayList<SimpleGrantedAuthority>();

		grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
		@SuppressWarnings("unused")
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				username, password, grantedAuth);

		@SuppressWarnings("unused")
		Authentication authentication1 = null;
		String token = null;

		try {

			/*authentication1 = this.authManager.authenticate(auth);

			SecurityContextHolder.getContext().setAuthentication(
					authentication1);
*/
			try {
				token = customNimbusJoseJWTUtil.createToken(username);
			} catch (Exception e) {

				logger.error("Error : " + e.getMessage());

			}

		} catch (AuthenticationException e1) {
			throw e1;
		}

		return token;
	}

}
