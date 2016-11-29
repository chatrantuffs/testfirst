/**
 * 
 */
package com.dashboard.poc.security.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dashboard.poc.enums.EncryptTypeEnum;
import com.dashboard.poc.exception.CustomException;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
@PropertySource("classpath:config.properties")
public class CustomEncryptionUtil {
	
	@Autowired
	private Environment env;
	
	static Logger logger = Logger.getLogger(CustomEncryptionUtil.class.getName());
	
	public String getEncryptedPassword(final String password) throws CustomException{
		final String encryptFormat = StringUtils.isNotBlank(env.getProperty("encryption.type"))?
									 env.getProperty("encryption.type"):EncryptTypeEnum.DEFAULT.encryptType();
		String encryptedPassword = StringUtils.EMPTY;
		try{							 
			final MessageDigest messageDigest = MessageDigest.getInstance(encryptFormat);
			
			if(encryptFormat.equalsIgnoreCase(EncryptTypeEnum.SHA1.encryptType())){
				encryptedPassword = getDefaultEncryptedPassword(messageDigest, password);
			}else{
				encryptedPassword = getEncryptedPassword(messageDigest, password);
			}
		}catch(UnsupportedEncodingException e){
			logger.error("Error in encrypting the password, " + e.getMessage());
			throw new CustomException(e);
		}
		catch(NoSuchAlgorithmException e){
			logger.error("Error in encrypting the password, " + e.getMessage());
			throw new CustomException(e);
		}
		return encryptedPassword;
	}
	
	//SHA-1, DEFAULT
	private String getDefaultEncryptedPassword(final MessageDigest messageDigest, final String password) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		messageDigest.reset();
		messageDigest.update(password.getBytes("UTF-8"));
		final byte byteData[] = messageDigest.digest();
		
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
	
	//MD5, SHA256
	private String getEncryptedPassword(final MessageDigest messageDigest, final String password) throws NoSuchAlgorithmException{
		messageDigest.update(password.getBytes());
		final byte byteData[] = messageDigest.digest();
		
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
	
}
