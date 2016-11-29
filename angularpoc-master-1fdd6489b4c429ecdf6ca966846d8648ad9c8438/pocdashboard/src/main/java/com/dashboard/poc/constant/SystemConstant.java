/**
 * 
 */
package com.dashboard.poc.constant;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class SystemConstant {
	public final static String _AUTH_TOKEN_SECRET = "my@test*83Secret";
	public final static String _AUTH_TOKEN_ISSUER = "admin@issue*123Ok";
	public final static String _AUTH_TOKEN_SUBJECT = "admin@Subject*2016S";
	
	private final int _ERROR_CODE_NOT_FOUND = 1;
	private final int _ERROR_CODE_CUSTOM_EXCEPTION = 100;
	private final int _ERROR_CODE_NOT_AUTHORIZED = 10;
	
	private final String _CONS_MILLISECONDS = "MILLISECONDS";
	private final String _CONS_SECONDS = "SECONDS";
	private final String _CONS_MINUTES = "MINUTES";
	private final String _CONS_HOURS = "HOURS";
	private final String _CONS_DAYS = "DAYS";
	
	public final static String _SESSION_USERID = "userid";
	private final String _EMPTY_STRING = StringUtils.EMPTY;
	
	public final static String[] _DEFAULT_LOCALHOST_URL = new String[] {"127.0.0.1", "localhost"};
	public static final String _AUTH_TOKEN_KEY = "auth_token";
	
	public static final String OPERATORID = "OperatorID";
	
	public static final String _CONSTANT_YES = "y";
	public static final String _CONSTANT_NO = "n";
	
	public static final String _DEFAULT_CHART_SIN_KEY = "Sin Wave";
	public static final String _DEFAULT_CHART_COS_KEY = "Cosine Wave";
	public static final String _DEFAULT_CHART_SIN2_KEY = "Another sine wave";
	
	public static final String _DEFAULT_CHART_SIN_COLOR = "#ff7f0e";
	public static final String _DEFAULT_CHART_COS_COLOR = "#2ca02c";
	public static final String _DEFAULT_CHART_SIN2_COLOR = "#7777ff";
	
	public static final String _CONSTANT_SIN = "sin";
	public static final String _CONSTANT_COS = "cos";
	public static final String _CONSTANT_SIN2 = "sin2";
	
	public static final String _CONSTANT_TRUE = "true";
	public static final String _CONSTANT_FALSE = "false";
	
	public String emptyString(){
		return _EMPTY_STRING;
	}
	
	public String getConsMilliSeconds(){
		return _CONS_MILLISECONDS;
	}
	
	public String getConsSeconds(){
		return _CONS_SECONDS;
	}
	
	public String getConsMinutes(){
		return _CONS_MINUTES;
	}
	
	public String getConsHours(){
		return _CONS_HOURS;
	}
	
	public String getConsDays(){
		return _CONS_DAYS;
	}
	
	public int getErrCodeNotFound(){
		return _ERROR_CODE_NOT_FOUND;
	}
	
	public int getErrCustomException(){
		return _ERROR_CODE_CUSTOM_EXCEPTION;
	}
	
	public int getErroNotAuthorized(){
		return _ERROR_CODE_NOT_AUTHORIZED;
	}
}
