/**
 * 
 */
package com.dashboard.poc.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.exception.CustomException;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
@PropertySource("classpath:config.properties")
public class FilterUtil {
	
	@Autowired
	private Environment env;
	
	private final String _DOT_SEPARATOR = ".";
	private final String _COMA_SEPARATOR = ",";
	private final String PUBLIC_URL = "public";
	private final String _EMPTY_STR = StringUtils.EMPTY; 
	private final String _YES = "Y"; 
	
	static Logger logger = Logger.getLogger(FilterUtil.class.getName());
	
	public String getDotSeparator(){
		return _DOT_SEPARATOR;
	}
	
	public String unAuthorizedIPRangeMsg(){
		return env.getProperty("error.unauthorized.ip");
	}
	
	public boolean getAuthIPRange(final String ipAddress) throws CustomException{
		boolean isException = false;
		try{
			final String defaultIPRange = env.getProperty("default.iprange");
			final String defaultIPRestrictRange = env.getProperty("default.iprange.restrict.range");
			final String defaultLocalHostAccess = env.getProperty("default.localhost.access");
			if(StringUtils.isBlank(defaultLocalHostAccess) || (StringUtils.isNotBlank(defaultLocalHostAccess) && defaultLocalHostAccess.equalsIgnoreCase(_YES))){
				if(Arrays.asList(SystemConstant._DEFAULT_LOCALHOST_URL).contains(ipAddress)){
					if(StringUtils.isNotBlank(defaultIPRestrictRange)){
						if(Integer.parseInt(defaultIPRestrictRange) >= 2){
							return getIPRangeValidation(ipAddress, defaultIPRange);
						}else{
							logger.error(env.getProperty("error.ip.restrict.range"));
							isException = true;
							throw new CustomException(env.getProperty("error.ip.restrict.range"));
						}
					}else{
						return getIPRangeValidation(ipAddress, defaultIPRange);
					}
				}else{
					logger.error(env.getProperty("error.restict.localhost.address"));
					isException = true;
					throw new CustomException(env.getProperty("error.restict.localhost.address"));
				}
			}else{
				logger.error(env.getProperty("error.restict.localhost.address"));
				isException = true;
				throw new CustomException(env.getProperty("error.restict.localhost.address"));
			}
			
		}catch(Exception e){
			if(!isException){
				logger.error(env.getProperty("error.default.ip.range"));
			}
			throw new CustomException(isException?e.getMessage():env.getProperty("error.default.ip.range"));
		}
	}
	
	private boolean getIPRangeValidation(final String ipAddress, final String defaultIPRange) throws CustomException{
		boolean valideIPAddressRange = false;
		try{
			List<String> defaultIPList = new ArrayList<String>();
			if(StringUtils.isNotBlank(defaultIPRange)){
				final StringTokenizer ipAddrToken = new StringTokenizer(defaultIPRange,
						_COMA_SEPARATOR);
				//List down all the IP from the properties file
				while (ipAddrToken.hasMoreTokens()) {
					defaultIPList.add(ipAddrToken.nextToken().toLowerCase());
				}
				
				//if the IP address is public then allow all to access the url
				if(defaultIPList.contains(PUBLIC_URL.toLowerCase())){
					valideIPAddressRange = true;
					return true;
				}else {
					if(defaultIPList.size() > 0){
						for(String selectedIP : defaultIPList){
							if (selectedIP.startsWith(validIPRageStr(ipAddress))) {
								valideIPAddressRange = true;
					        }
						}
					}else{
						if(defaultIPList.contains(ipAddress.toLowerCase())){
							valideIPAddressRange = true;
						}
					}
				}
			}
			return valideIPAddressRange;		//if there is no entry then the default is public
		}catch(Exception e){
			logger.error(env.getProperty("error.validate.ip.range"));
			throw new CustomException(env.getProperty("error.validate.ip.range"));
		}
	}
	private String validIPRageStr(final String ipAddress){
		logger.info("request from IP Address : " + ipAddress);
		final StringTokenizer ipAddrToken = new StringTokenizer(ipAddress,
				_DOT_SEPARATOR);
		int dots = 0;
		String byte1 = _EMPTY_STR;
		String byte2 = _EMPTY_STR;

		while (ipAddrToken.hasMoreTokens()) {
			++dots;
			if (dots == 1) {
				byte1 = ipAddrToken.nextToken();
			} else {
				byte2 = ipAddrToken.nextToken();
				break;
			}
		}
		return byte1 + _DOT_SEPARATOR + byte2;
	}
}
