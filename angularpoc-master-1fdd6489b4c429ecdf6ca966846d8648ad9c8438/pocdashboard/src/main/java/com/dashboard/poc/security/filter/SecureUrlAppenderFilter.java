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
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

//@WebFilter("/*")
@Component
public class SecureUrlAppenderFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String requestURI = httpServletRequest.getRequestURI();
        
       /* if (requestURI.startsWith("/api") ) {
            request.getRequestDispatcher(requestURI.concat(JSF_VIEW_SUFFIX))
                .forward(request,response);
        } else {
            chain.doFilter(httpServletRequest, response);
        }*/
        System.out.println("Test the URL");
        request.getRequestDispatcher(requestURI.concat("/api"))
        .forward(request,response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
