/**
 * 
 */
package com.dashboard.poc.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.custom.model.AuthToken;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.util.ConfigPropertyReaderUtil;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
@PropertySource("classpath:applicationResources.properties")
public class AuthTokenUtil {
	
	@Autowired
	ConfigPropertyReaderUtil configPropertyReaderUtil;
	
	@Autowired
	private Environment env;
	
	static Logger logger = Logger.getLogger(AuthTokenUtil.class.getName());
	
	public String createAuthToken(final String userId) throws CustomException {
		 
		try{
			final long _AUTH_TOKEN_DURATION = configPropertyReaderUtil.getAuthTokenDuration();
			
			//The JWT signature algorithm we will be using to sign the token
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
			
			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);
			
			//We will sign our JWT with our ApiKey secret
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SystemConstant._AUTH_TOKEN_SECRET);
			Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
			 
			//Let's set the JWT Claims
			JwtBuilder builder = Jwts.builder().setId(userId)
			                                .setIssuedAt(now)
			                                .setSubject(SystemConstant._AUTH_TOKEN_SUBJECT)
			                                .setIssuer(SystemConstant._AUTH_TOKEN_ISSUER)
			                                .signWith(signatureAlgorithm, signingKey);
			 
			//if it has been specified, let's add the expiration
			if (_AUTH_TOKEN_DURATION >= 0) {
			    long expMillis = nowMillis + _AUTH_TOKEN_DURATION;
			    Date exp = new Date(expMillis);
			    builder.setExpiration(exp);
			}
			 
			if(logger.isDebugEnabled())
				logger.debug("Auth Token Created Successfully");
			
			//Builds the JWT and serializes it to a compact, URL-safe string
			return builder.compact();
		}catch(Exception e){
			logger.error("Error in Creating the Auth Token, " + e.getMessage());
			throw new CustomException(env.getProperty("error.authtoken.create"));
		}
	}
	
	public AuthToken parseAuthToken(final String jwt) {
		//This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser()         
		   .setSigningKey(DatatypeConverter.parseBase64Binary(SystemConstant._AUTH_TOKEN_SECRET))
		   .parseClaimsJws(jwt).getBody();
		AuthToken tempAuthToken = new AuthToken();
		tempAuthToken.setUserId(claims.getId());
		tempAuthToken.setExpiresWhen(claims.getExpiration());
		tempAuthToken.setIssuedWhen(claims.getIssuedAt());
		tempAuthToken.setIsuuedBy(claims.getIssuer());
		if(logger.isDebugEnabled()){
			logger.debug("Auth Token Parsed Successfully");
		}
		return tempAuthToken;
	}
	
	public boolean verifyAuthToken(final String jwt, final String userId) throws CustomException{
		final AuthToken tempAuthToken = parseAuthToken(jwt);
		if(tempAuthToken == null)
			throw new CustomException(env.getProperty("error.authtoken.parse"));
		
		final Date ExpireDate = tempAuthToken.getExpiresWhen();
		final long nowMillis = System.currentTimeMillis();
		final Date now = new Date(nowMillis);
		logger.info("Token Expire time : " + ExpireDate + "\nNow time :  " + now + " TEST : " + now.compareTo(ExpireDate));
		
		if(StringUtils.isNotBlank(userId) && userId.equalsIgnoreCase(tempAuthToken.getUserId())){
			if(ExpireDate.compareTo(now) >= 0){
				logger.info("Auth Token Verified Successfully");
				return true;
	    	}else{
	    		logger.warn("Invalid Auth Token");
	    		throw new CustomException(env.getProperty("error.authtoken.expires"));
	    	}
		}else{
			logger.warn("Unauthorized user");
			throw new CustomException(env.getProperty("error.authtoken.invalid.user"));
		}
	}
	
	public static void main(String[] args) {
		AuthTokenUtil u =  new AuthTokenUtil();
		//System.out.println(u.createAuthToken("id"));
		try {
			u.verifyAuthToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJpZCIsImlhdCI6MTQ2OTI1Mjc1Mywic3ViIjoic2VjdXJpdHkiLCJpc3MiOiJwcmF0eXVzIiwiZXhwIjoxNDY5MjYyODUzfQ.e7Yv8l-8yTqeIwH3-aI4_j_MChAytw0Hw31gcm0bTm4", "admin");
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
