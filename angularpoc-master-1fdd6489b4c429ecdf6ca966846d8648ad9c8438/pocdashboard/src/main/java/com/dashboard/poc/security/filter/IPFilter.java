/**
 * 
 */
package com.dashboard.poc.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.security.util.FilterUtil;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@WebFilter("/v1/*")
@Component
public class IPFilter implements Filter {

	static Logger logger = Logger.getLogger(IPFilter.class.getName());

	private FilterConfig config;
	FilterUtil filterUtil = null;
	
	public IPFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final String ipAddress = request.getRemoteAddr();
		HttpServletResponse httpServletResponse = null;
		filterUtil = WebApplicationContextUtils.
				  getRequiredWebApplicationContext(config.getServletContext()).
				  getBean(FilterUtil.class);
		if (response instanceof HttpServletResponse)
			httpServletResponse = (HttpServletResponse) response;
		logger.info("IP Address is : " + ipAddress);
		try {
			if (filterUtil.getAuthIPRange(ipAddress)) {
				chain.doFilter(request, response);
			} else {
				logger.error(ipAddress + " is an authorized IP address");
				httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
						filterUtil.unAuthorizedIPRangeMsg());
			}
		} catch (CustomException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
					e.getMessage());
		}
	}

	public void destroy() {
	}

}
