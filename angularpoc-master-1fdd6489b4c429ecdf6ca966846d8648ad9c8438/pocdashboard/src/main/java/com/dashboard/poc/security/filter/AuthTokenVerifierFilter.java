/**
 * 
 */
package com.dashboard.poc.security.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.constant.UrlConstant;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.security.util.AuthTokenUtil;
import com.dashboard.poc.util.CustomUtil;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@WebFilter("/v1/*")
@Component
public class AuthTokenVerifierFilter implements Filter{

	private FilterConfig config;
	AuthTokenUtil authTokenUtil = null;
	CustomUtil customUtil = null;
	
	static Logger logger = Logger.getLogger(AuthTokenVerifierFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		final HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		final String requestUri = httpServletRequest.getRequestURI();
		logger.info("Request URI : " + requestUri);
		
		if(requestUri.contains(UrlConstant._LOGIN_SERVICE_URL)){
			chain.doFilter(request, response);
		}else{
			HttpServletResponse httpServletResponse = null;
			try{
				if (response instanceof HttpServletResponse)
					httpServletResponse = (HttpServletResponse) response;
				
				final Map<String, String> headerMap = new HashMap<String, String>();
				
				@SuppressWarnings("rawtypes")
				final Enumeration headerNames = httpServletRequest.getHeaderNames();
				
				//Get all the Header Parameters put into a Map
				while (headerNames.hasMoreElements()) {
					String key = (String) headerNames.nextElement();
					String value = httpServletRequest.getHeader(key);
					headerMap.put(key, value);
				}
				
				//Get the user name from the session
				final String userNameFromSession = httpServletRequest != null ? (String)httpServletRequest.getSession().getAttribute(SystemConstant._SESSION_USERID) : StringUtils.EMPTY;
				
				//Get the Header param values for the userid and the token
				final String tokenHeader = headerMap.isEmpty()?StringUtils.EMPTY:headerMap.containsKey(SystemConstant._AUTH_TOKEN_KEY)?
												  headerMap.get(SystemConstant._AUTH_TOKEN_KEY):StringUtils.EMPTY;
				
				authTokenUtil = WebApplicationContextUtils.
						getRequiredWebApplicationContext(config.getServletContext()).
						getBean(AuthTokenUtil.class);
												  
				final String userNameFromHeader = StringUtils.isNotBlank(tokenHeader) ? authTokenUtil.parseAuthToken(tokenHeader).getUserId():userNameFromSession;
				
				logger.info("User name from header : " + userNameFromHeader + ", and from session is : " + userNameFromSession);
				final String tokenStrHeader = headerMap.isEmpty()?StringUtils.EMPTY:headerMap.containsKey(SystemConstant._AUTH_TOKEN_KEY)?
						headerMap.get(SystemConstant._AUTH_TOKEN_KEY):StringUtils.EMPTY;
						  
				if(authTokenUtil.verifyAuthToken(tokenStrHeader, userNameFromHeader)){
					refreshToken(authTokenUtil, userNameFromHeader, httpServletResponse);
					chain.doFilter(request, response);
				}else{
					httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							"Authorized Token Expired");
				}
			}catch(Exception e){
				logger.error("Error " + e.getMessage());
				httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						e.getMessage());
			}
		}
	}

	private void refreshToken(final AuthTokenUtil authTokenUtil, final String userId, final HttpServletResponse response) throws CustomException{
		final String _AUTH_TOKEN = authTokenUtil.createAuthToken(userId);
		if(logger.isDebugEnabled())
			logger.debug("Auth Token Created and added to the response header successfully");
		response.setHeader(SystemConstant._AUTH_TOKEN_KEY, _AUTH_TOKEN);
		response.setHeader(SystemConstant._SESSION_USERID, StringUtils.EMPTY);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
