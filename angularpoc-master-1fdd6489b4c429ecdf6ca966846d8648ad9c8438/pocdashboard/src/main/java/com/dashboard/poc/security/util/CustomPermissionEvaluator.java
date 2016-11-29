/**
 * 
 */
package com.dashboard.poc.security.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator{

	static Logger logger = Logger.getLogger(CustomPermissionEvaluator.class.getName());
	
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		
		HttpServletRequest request = (HttpServletRequest)permission;		
		HttpSession session = request.getSession();
		
		System.out.println("Session User Id : " + session.getAttribute(SystemConstant._SESSION_USERID));
		
		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
