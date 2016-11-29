/**
 * 
 */
package com.dashboard.poc.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.UrlConstant;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler{

	static Logger logger = Logger.getLogger(CustomFailureHandler.class.getName());
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		/*HttpSession session = request.getSession(true);
		session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		String userName = (String) arg2.getAuthentication().getPrincipal();
		String operator = userService.loadOperatorByOparatorName(userName);
		OperatorTo operatorTo = mapper.readValue(operator, OperatorTo.class);
		session.setAttribute(ServerUriConstant.USER_NAME, userName);
		if (operatorTo != null && operatorTo.getOperatorStatus() != 1) {
			String sysMess = userService.userErrMessage();
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(sysMess);
			} catch (JSONException e) {
			}
			String accountDisabled;
			try {
				accountDisabled = (String) jObject
						.get(SystemMsg.ACCOUNT_DISABLED.toString());
				session.setAttribute(SystemConstant.ACCOUNT_DISABLED,
						accountDisabled);
			} catch (JSONException e) {

			}
		}*/
		response.sendRedirect(UrlConstant._LOGIN_FAILED);
	}

}
