/**
 * 
 */
package com.dashboard.poc.security.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.poc.constant.SystemConstant;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64URL;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CustomNimbusJoseJWTUtil {
	
	static Logger logger = Logger.getLogger(CustomNimbusJoseJWTUtil.class.getName());
	
	public String createToken(String username)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			JOSEException, JSONException {

		long expires = System.currentTimeMillis() + 1000L * 60 * 30;

		JSONObject json = new JSONObject();
		json.put("uid", username);
		json.put("exp", expires);
		//json.put("ip", httpRequest.getRemoteAddr());

		Payload payload = new Payload(json.toString());

		// Create JWS header with HS256 algorithm
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

		// Create JWS object
		JWSObject jwsObject = new JWSObject(header, payload);

		// Create HMAC signer
		// change this key and put it in configuration file
		String sharedKey = "changeme";

		JWSSigner signer = new MACSigner(sharedKey.getBytes());
		jwsObject.sign(signer);

		// Serialise JWS object to compact format
		String token = jwsObject.serialize();

		return token;
	}

	/**
	 * Refresh token.
	 * 
	 * @param oldToken
	 *            the old token
	 * @return the string
	 */
	@Transactional
	public String refreshToken(String oldToken) {

		String token = "";

		try {
			token = reIssueToken(oldToken);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException
				| JOSEException | JSONException e) {
			logger.error("Error : " + e.getMessage());
		}

		return token;
	}

	/**
	 * Re issue token.
	 * 
	 * @param oldToken
	 *            the old token
	 * @return the string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws JOSEException
	 *             the JOSE exception
	 * @throws JSONException
	 *             the JSON exception
	 */
	public String reIssueToken(String oldToken)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			JOSEException, JSONException {
		String token = "";

		long expires = System.currentTimeMillis() + 1000L * 60 * 30;

		StringTokenizer strTkn = new StringTokenizer(oldToken, ".");
		JWSObject jswObject;

		try {
			jswObject = new JWSObject(new Base64URL(strTkn.nextToken()),
					new Base64URL(strTkn.nextToken()), new Base64URL(
							strTkn.nextToken()));
			JWSVerifier verifier = new MACVerifier(
					SystemConstant._AUTH_TOKEN_SECRET.getBytes());
			boolean verifiedSignature = jswObject.verify(verifier);

			if (verifiedSignature) {
				Payload payLoad = jswObject.getPayload();
				JSONObject json = new JSONObject(payLoad.toString());
				String username = json.getString("uid");

				json.put("uid", username);
				json.put("exp", expires);

				Payload payload = new Payload(json.toString());

				// Create JWS header with HS256 algorithm
				JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

				// Create JWS object
				JWSObject jwsObject = new JWSObject(header, payload);

				// Create HMAC signer
				// change this key and put it in configuration file
				String sharedKey = "changeme";

				JWSSigner signer = new MACSigner(sharedKey.getBytes());
				jwsObject.sign(signer);

				// Serialise JWS object to compact format
				token = jwsObject.serialize();
			}

		} catch (Exception e) {
			@SuppressWarnings("unused")
			BadCredentialsException ex = new BadCredentialsException(
					"Token Already Used or Expired!");
		}

		return token;
	}
}
