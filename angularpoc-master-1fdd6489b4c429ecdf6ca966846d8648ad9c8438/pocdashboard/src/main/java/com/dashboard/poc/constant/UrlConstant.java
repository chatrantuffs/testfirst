/**
 * 
 */
package com.dashboard.poc.constant;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class UrlConstant {
	//Section for service level constants
	public final static String _API_VERSION_SECURE_URL = "/api";
	public final static String _API_VERSION_URL = "/v1/services";
	
	public final static String _LOGIN_SERVICE_URL = _API_VERSION_URL + "/loginservice";
	public final static String _SECURE_LOGIN_SERVICE_URL = _API_VERSION_SECURE_URL + _LOGIN_SERVICE_URL;
	
	public final static String _DASHBOARD_SERVICE_URL = _API_VERSION_URL + "/dashboardservice";
	public final static String _SECURE_DASHBOARD_SERVICE_URL = _API_VERSION_SECURE_URL + _DASHBOARD_SERVICE_URL;
	
	//Section for method level constants
	public static final String _VALIDATE_USER_URL = "/login";
	public static final String _DASHBOARD_URL = "/dashboards";
	
	//Chart Method Uri
	public static final String _LINE_CHART_URL = "/lineChart";
	public static final String _CUMULATIVE_CHART_URL = "/cumulativeLineChart";
	
	public static final String _LOGIN_FAILED = "/loginfailed";
}
