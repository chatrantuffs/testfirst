/**
 * 
 */
package com.dashboard.poc.security.filter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.custom.model.TokenModel;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64URL;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public class CustomTokenProcessingFilter extends GenericFilterBean{

	static Logger logger = Logger.getLogger(CustomTokenProcessingFilter.class.getName());

	@Autowired
	ServletContext context;

	@Autowired
	CacheManager manager;

	@Autowired
	private TokenModel tokenModel;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub


		try {

			HttpServletRequest httpRequest = this.getAsHttpRequest(request);
			logger.info("Filter triggered AuthenticationTokenProcessingFilter");

			String authToken = this.extractAuthTokenFromRequest(httpRequest);
			if (authToken != null) {

				Ehcache cache = manager.getEhcache("authKeys");

				Element element = cache.get(authToken);
				// Expire the older token
				if (element != null) {
					throw new CredentialsExpiredException(
							"Token Unknown or already expired");
				}

				StringTokenizer strTkn = new StringTokenizer(authToken, ".");

				// Create a new to be signed JWS (JSON web signature) object and verify it with shared key
				JWSObject jswObject = null;
				try {
					jswObject = new JWSObject(
							new Base64URL(strTkn.nextToken()), new Base64URL(
									strTkn.nextToken()), new Base64URL(
									strTkn.nextToken()));
					JWSVerifier verifier = new MACVerifier(SystemConstant._AUTH_TOKEN_SECRET.getBytes());
					boolean verifiedSignature = jswObject.verify(verifier);
					// If the signature is verified check the expiry date
					if (verifiedSignature) {
						Payload payLoad = jswObject.getPayload();
						JSONObject json = new JSONObject(payLoad.toString());
						String username = json.getString("uid");
						Long expdate = json.getLong("exp");

						if (expdate < System.currentTimeMillis()) {
							throw new CredentialsExpiredException("Login again");
						}

						tokenModel.setUserName(username);

						Collection<SimpleGrantedAuthority> grantedAuth = new ArrayList<SimpleGrantedAuthority>();
						grantedAuth
								.add(new SimpleGrantedAuthority("ROLE_USER"));
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								username, null, grantedAuth);
						SecurityContextHolder.getContext().setAuthentication(
								authentication);
					} else {
						throw new BadCredentialsException("Bad JWS signature!");
					}
				} catch (JOSEException e) {
					logger.error("Error : " + e.getMessage());
				} catch (ParseException e) {
					logger.error("Error : " + e.getMessage());
				} catch (JSONException e) {
					logger.error("Error : " + e.getMessage());
				}
			}

			chain.doFilter(request, response);

		} catch (Exception ex) {
			throw new BadCredentialsException("Bad JWS signature!");
		}
	
	}
	
	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}
		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		// Get token from header
		String authToken = httpRequest.getHeader("Authentication");
		// If token not found get it from request parameter
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}

}
