/**
 * 
 */
package com.dashboard.poc.security.util;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Transactional(readOnly = true)
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	static Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());
	
	public CustomUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails user = null;
		try {

			/*String operator1 = userService.loadOperatorByOparatorName(userId);

			OperatorTo operatorTo = mapper.readValue(operator1,
					OperatorTo.class);
			String jsonRole = userService.loadOpratorRoles(userId,String.valueOf(operatorTo.getOperatorId()));
			JSONArray jarray = new JSONArray(jsonRole);
			Collection<SimpleGrantedAuthority> roleList = new ArrayList<SimpleGrantedAuthority>();
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject sysJsonObject = (JSONObject) jarray.get(i);
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
						sysJsonObject.getString("role"));
				roleList.add(simpleGrantedAuthority);

			}*/
			
			Collection<SimpleGrantedAuthority> roleList = new ArrayList<SimpleGrantedAuthority>();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
					"admin");
			
			roleList.add(simpleGrantedAuthority);
			user = new User("admin", "admin", true, true, true, true, roleList);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
		}
		return user;
	}

}
