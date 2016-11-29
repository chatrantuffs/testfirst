/**
 * 
 */
package com.dashboard.poc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.controller.LoginService;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.model.LineChartModel;
import com.dashboard.poc.security.util.AuthTokenUtil;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
@PropertySource("classpath:applicationResources.properties")
public class CustomUtil {

	@Autowired
	AuthTokenUtil authTokenUtil;
	
	@Autowired
	private Environment env;
	
	static Logger logger = Logger.getLogger(LoginService.class.getName());
	
	public void setAuthToken(final String userId, final HttpServletResponse response) throws CustomException{
		final String _AUTH_TOKEN = authTokenUtil.createAuthToken(userId);
		if(logger.isDebugEnabled())
			logger.debug("Auth Token Created and added to the response header successfully");
		response.setHeader(SystemConstant._AUTH_TOKEN_KEY, _AUTH_TOKEN);
		response.setHeader(SystemConstant._SESSION_USERID, StringUtils.EMPTY);
	}
	
	public void refreshAuthToken(final String userId, final HttpServletResponse response) throws CustomException{
		final String _AUTH_TOKEN = authTokenUtil.createAuthToken(userId);
		if(logger.isDebugEnabled())
			logger.debug("Auth Token Created and added to the response header successfully");
		response.setHeader(SystemConstant._AUTH_TOKEN_KEY, _AUTH_TOKEN);
		response.setHeader(SystemConstant._SESSION_USERID, StringUtils.EMPTY);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> prepareLightWeightLineChartData(){
		List<Map> data = new ArrayList<Map>();
		data.add(getDefaultLineChartValues(true));
		return data;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LineChartModel> prepareLineChartData( final int lBound, final int uBound){
		if(logger.isDebugEnabled())
			logger.debug("Preparing the data for LineChart");
		List<LineChartModel> listChartModels = new ArrayList<LineChartModel>();
		List<Map> sin = new ArrayList<Map>();
		List<Map> sin2 = new ArrayList<Map>();
		List<Map> cos = new ArrayList<Map>();
		LineChartModel lineChartModel = null;
		Map row = null;
		
		for (int i = lBound; i < uBound; i++) {
			 row = new HashMap();
			 row.put("x", i);
			 row.put("y", Math.sin(i/10));
			 row.put("series", 0);
			 sin.add(row);
			 
			 row = null;
			 row = new HashMap();
			 row.put("x", i);
			 row.put("y", .5 * Math.cos(i/10+ 2) + Math.random() / 10);
			 row.put("series", 1);
			 cos.add(row);
			 row = null;
			 
			 row = null;
			 row = new HashMap();
			 row.put("x", i);
			 row.put("y", i % 10 == 5 ? null : Math.sin(i/10) *0.25 + 0.5);
			 row.put("series", 2);
			 sin2.add(row);
        }
		
		Map<String, Boolean> getChartDisplayState = getChartDisplayState();
		Map<String, LineChartModel> getDefaultLineChartValues = getDefaultLineChartValues(false);
		if(getChartDisplayState.get(SystemConstant._CONSTANT_SIN)){
			lineChartModel = getDefaultLineChartValues.get(SystemConstant._CONSTANT_SIN);
			lineChartModel.setValues(sin);
			lineChartModel.setSeriesIndex(0);
			listChartModels.add(lineChartModel);
			lineChartModel = null;
		}
		
		if(getChartDisplayState.get(SystemConstant._CONSTANT_COS)){
			lineChartModel = getDefaultLineChartValues.get(SystemConstant._CONSTANT_COS);
			lineChartModel.setValues(cos);
			lineChartModel.setSeriesIndex(1);
			listChartModels.add(lineChartModel);
			lineChartModel = null;
		}
		
		if(getChartDisplayState.get(SystemConstant._CONSTANT_SIN2)){
			lineChartModel = getDefaultLineChartValues.get(SystemConstant._CONSTANT_SIN2);
			lineChartModel.setValues(sin2);
			lineChartModel.setSeriesIndex(2);
			listChartModels.add(lineChartModel);
			lineChartModel = null;
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Preparing the data for LineChart done successfully");
		return listChartModels;
	}
	
	private Map<String, Boolean> getChartDisplayState(){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		final boolean shouldDisplySin = StringUtils.isNotBlank(env.getProperty("chart.line.sin.display"))? 
				env.getProperty("chart.line.sin.display").equalsIgnoreCase(SystemConstant._CONSTANT_YES)?
				true:false: false;
		final boolean shouldDisplyCos = StringUtils.isNotBlank(env.getProperty("chart.line.cos.display"))? 
						env.getProperty("chart.line.cos.display").equalsIgnoreCase(SystemConstant._CONSTANT_YES)?
						true:false: false;
		final boolean shouldDisplySin2 = StringUtils.isNotBlank(env.getProperty("chart.line.sin2.display"))? 
				env.getProperty("chart.line.sin2.display").equalsIgnoreCase(SystemConstant._CONSTANT_YES)?
				true:false: false;
		map.put(SystemConstant._CONSTANT_SIN, shouldDisplySin);
		map.put(SystemConstant._CONSTANT_COS, shouldDisplyCos);
		map.put(SystemConstant._CONSTANT_SIN2, shouldDisplySin2);

		return map;
	}
	
	private Map<String, LineChartModel> getDefaultLineChartValues(final boolean isDisplayOptional){
		Map<String, LineChartModel> map = new HashMap<String, LineChartModel>();
		final Map<String, Boolean> displayState = getChartDisplayState();
		LineChartModel lineChartModel = null;
		
		lineChartModel = new LineChartModel();
		lineChartModel.setKey(StringUtils.isNotBlank(env.getProperty("chart.line.sin.key"))?env.getProperty("chart.line.sin.key"):SystemConstant._DEFAULT_CHART_SIN_KEY);
		lineChartModel.setColor(StringUtils.isNotBlank(env.getProperty("chart.line.sin.color"))?env.getProperty("chart.line.sin.color"):SystemConstant._DEFAULT_CHART_SIN_COLOR);
		lineChartModel.setArea(StringUtils.isNotBlank(env.getProperty("chart.line.sin.area"))?Boolean.parseBoolean(env.getProperty("chart.line.sin.key")):false);
		if(isDisplayOptional){
			lineChartModel.setDisplayStatus(displayState.get(SystemConstant._CONSTANT_SIN)?SystemConstant._CONSTANT_TRUE:SystemConstant._CONSTANT_FALSE);
		}
		map.put(SystemConstant._CONSTANT_SIN, lineChartModel);
		lineChartModel = null;
		
		lineChartModel = new LineChartModel();
		lineChartModel.setKey(StringUtils.isNotBlank(env.getProperty("chart.line.cos.key"))?env.getProperty("chart.line.cos.key"):SystemConstant._DEFAULT_CHART_COS_KEY);
		lineChartModel.setColor(StringUtils.isNotBlank(env.getProperty("chart.line.sin.key"))?env.getProperty("chart.line.sin.key"):SystemConstant._DEFAULT_CHART_COS_COLOR);
		lineChartModel.setArea(StringUtils.isNotBlank(env.getProperty("chart.line.sin.key"))?Boolean.parseBoolean(env.getProperty("chart.line.sin.key")):false);
		if(isDisplayOptional){
			lineChartModel.setDisplayStatus(displayState.get(SystemConstant._CONSTANT_COS)?SystemConstant._CONSTANT_TRUE:SystemConstant._CONSTANT_FALSE);
		}
		map.put(SystemConstant._CONSTANT_COS, lineChartModel);
		lineChartModel = null;
		
		
		lineChartModel = new LineChartModel();
		lineChartModel.setKey(StringUtils.isNotBlank(env.getProperty("chart.line.sin2.key"))?env.getProperty("chart.line.sin2.key"):SystemConstant._DEFAULT_CHART_SIN2_KEY);
		lineChartModel.setColor(StringUtils.isNotBlank(env.getProperty("chart.line.sin2.color"))?env.getProperty("chart.line.sin2.color"):SystemConstant._DEFAULT_CHART_SIN2_COLOR);
		lineChartModel.setArea(StringUtils.isNotBlank(env.getProperty("chart.line.sin.key"))?Boolean.parseBoolean(env.getProperty("chart.line.sin.key")):false);
		if(isDisplayOptional){
			lineChartModel.setDisplayStatus(displayState.get(SystemConstant._CONSTANT_SIN2)?SystemConstant._CONSTANT_TRUE:SystemConstant._CONSTANT_FALSE);
		}
		map.put(SystemConstant._CONSTANT_SIN2, lineChartModel);
		lineChartModel = null;
		
		return map;
	}
}
