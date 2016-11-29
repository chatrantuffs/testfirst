/**
 * 
 */
package com.dashboard.poc.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.enums.TokenTimeEnum;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
@PropertySource("classpath:config.properties")
public class ConfigPropertyReaderUtil {

	@Value("${authtoken.settime.format}")
	private String durationFormat;
	
	@Value("${authtoken.duration}")
	private String duration;
	
	@Autowired
	private Environment env;
	
	@Autowired
	SystemConstant systemConstant;
	
	static Logger logger = Logger.getLogger(ConfigPropertyReaderUtil.class.getName());
	
	private long _DEFAULT_DURATION = 1000; //1 Second
	private String _DEFAULT_AUTH_DURATION_FORMAT = "MILLISECONDS"; //Seconds
	
	public long getAuthTokenDuration(){
		
		final String defaultDuration = env.getProperty("authtoken.duration");
		final String defaultDurationFormat = env.getProperty("authtoken.settime.format");
		
		final String _AUTH_TOKEN_DURATION_FORMAT = StringUtils.isNotBlank(durationFormat)? durationFormat : _DEFAULT_AUTH_DURATION_FORMAT;
		TokenTimeEnum tokenTimeEnum = TokenTimeEnum.MILLISECONDS;
		if(_AUTH_TOKEN_DURATION_FORMAT.equalsIgnoreCase(systemConstant.getConsSeconds()))
			tokenTimeEnum = TokenTimeEnum.SECONDS;
		else if(_AUTH_TOKEN_DURATION_FORMAT.equalsIgnoreCase(systemConstant.getConsMinutes()))
			tokenTimeEnum = TokenTimeEnum.MINUTES;
		else if(_AUTH_TOKEN_DURATION_FORMAT.equalsIgnoreCase(systemConstant.getConsHours()))
			tokenTimeEnum = TokenTimeEnum.HOURS;
		else if(_AUTH_TOKEN_DURATION_FORMAT.equalsIgnoreCase(systemConstant.getConsDays()))
			tokenTimeEnum = TokenTimeEnum.DAY;
		else
			tokenTimeEnum = TokenTimeEnum.MILLISECONDS;
		if(logger.isDebugEnabled())
			logger.debug("Auth Token Duration fetched successfully");
		final long authTokenDuration = tokenTimeEnum.time(defaultDurationFormat, StringUtils.isNotBlank(defaultDuration)? Long.parseLong(defaultDuration) : _DEFAULT_DURATION);
		return authTokenDuration;
	}
	
}
