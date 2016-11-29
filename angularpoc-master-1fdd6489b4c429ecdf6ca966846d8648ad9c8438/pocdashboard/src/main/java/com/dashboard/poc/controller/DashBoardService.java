/**
 * 
 */
package com.dashboard.poc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.constant.UrlConstant;
import com.dashboard.poc.custom.model.ResponseModel;
import com.dashboard.poc.model.CumulativeLineChartModel;
import com.dashboard.poc.model.DashBoardModel;
import com.dashboard.poc.model.LightWeightLineChartModel;
import com.dashboard.poc.model.LineChartModel;
import com.dashboard.poc.security.util.AuthTokenUtil;
import com.dashboard.poc.util.CreateResposeUtil;
import com.dashboard.poc.util.CustomUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Api(value = "DashBoardService", description = "Dash Board Service")// Swagger annotation
@RestController
@RequestMapping(value = UrlConstant._DASHBOARD_SERVICE_URL)
@PropertySource("classpath:applicationResources.properties")
public class DashBoardService {

	@Autowired
	SystemConstant systemConstant;
	
	@Autowired
	private Environment env;
	
	@Autowired
	CreateResposeUtil createResposeUtil;
	
	@Autowired
	AuthTokenUtil authTokenUtil;
	
	@Autowired
	CustomUtil customUtil;
	
	@Autowired 
	private HttpSession httpSession;
	
	@Autowired
	DashBoardModel dashBoardModel;
	
	@Autowired
	LineChartModel lineChartModel;
	
	@Autowired
	CumulativeLineChartModel cumulativeLineChartModel;
	
	@Autowired
	LightWeightLineChartModel lightWeightLineChartModel;
	
	static Logger logger = Logger.getLogger(DashBoardService.class.getName());
	
	@ApiOperation(value = "Returns appropriate Http Response code", notes = "Returns the dashboard result.")
	@RequestMapping(value = UrlConstant._DASHBOARD_URL, method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> validateUser(@ApiParam(name = "auth_token", value = "auth_token", required = true) @RequestHeader("auth_token") String auth_token,
			@ApiParam(name = "userid", value = "userid", required = true) @RequestHeader("userid") String userid, HttpServletResponse response) {
		System.out.println("Dashboard test");
		System.out.println("User Id : " + userid);
		System.out.println("Auth Token : " + auth_token);
		ResponseModel responseModel = null;
		DashBoardModel dashBoardModel = new DashBoardModel();
		
		System.out.println("User name from Http Session : " + httpSession.getAttribute(SystemConstant._SESSION_USERID));
		
		/*DashBoardModel dashBoardModel = new DashBoardModel();
		if(StringUtils.isBlank(token)){
			System.out.println("Token Blank");
			responseModel = createResposeUtil.buildResponseObject(env.getProperty("error.unauthorized.msg"));
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.UNAUTHORIZED);
		}
		
		try{
			String userid = StringUtils.EMPTY;
			if(httpSession != null && httpSession.getAttribute(systemConstant.getUserSessionId()) != null){
				System.out.println("Getting the Userid");
				userid = httpSession.getAttribute(systemConstant.getUserSessionId()).toString();
				System.out.println("User id : " + userid);
			}
			if(authTokenUtil.verifyAuthToken(token, userid))
				customUtil.setAuthToken(userid, response);
			System.out.println("Auth Token Verified");
			dashBoardModel.setId(1);
			dashBoardModel.setName("Dashboard");
			dashBoardModel.setDescription("Test Description");
			responseModel = createResposeUtil.buildResponseObject("data");
			System.out.println("Created the Data");
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
		}catch(CustomException e){
			System.out.println("Esception : " + e.getMessage());
			responseModel = createResposeUtil.buildResponseObject(dashBoardModel, systemConstant.getErrCustomException(), e.getMessage());
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.EXPECTATION_FAILED);
		}*/
		dashBoardModel.setId(1);
		dashBoardModel.setName("Dashboard");
		dashBoardModel.setDescription("Test Description");
		responseModel = createResposeUtil.buildResponseObject("data");
		System.out.println("Created the Data");
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Returns appropriate Http Response code", notes = "Returns the dashboard lineChart data.")
	@RequestMapping(value = UrlConstant._LINE_CHART_URL, method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> lineChart(@ApiParam(name = "auth_token", value = "Token", required = true) @RequestHeader("auth_token") String token, 
			@ApiParam(name = "isLightWeight", value = "isLightWeight") @RequestParam("isLightWeight") String lightweight, HttpServletResponse response) {
		System.out.println("Dashboard test");
		System.out.println("Auth Token : " + token);
		
		//Here the upper and outer bound should be fetched from DB
		final int lowerBound = 100;
		final int uperBound = 200;
		ResponseModel responseModel = null;
		if(StringUtils.isNotBlank(lightweight) && lightweight.equals(SystemConstant._CONSTANT_YES)){
			lightWeightLineChartModel.setLowerBound(lowerBound);
			lightWeightLineChartModel.setUpperBound(uperBound);
			
			@SuppressWarnings("rawtypes")
			final List<Map> listMap = customUtil.prepareLightWeightLineChartData();
			lightWeightLineChartModel.setListValues(listMap);
			responseModel = createResposeUtil.buildResponseObject(lightWeightLineChartModel);
		}else{
			final List<LineChartModel> listLineChartModels = customUtil.prepareLineChartData(lowerBound, uperBound);
			responseModel = createResposeUtil.buildResponseObject(listLineChartModels);
		}
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Returns appropriate Http Response code", notes = "Returns the dashboard cumulativeLineChart data.")
	@RequestMapping(value = UrlConstant._CUMULATIVE_CHART_URL, method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> cumulativeLineChart(@ApiParam(name = "auth_token", value = "Token", required = true) @RequestHeader("auth_token") String token, HttpServletResponse response) {
		System.out.println("Dashboard test");
		System.out.println("Auth Token : " + token);
		ResponseModel responseModel = null;
		List<CumulativeLineChartModel> listCumulativeChartData = new ArrayList<CumulativeLineChartModel>();
		listCumulativeChartData.add(createModel());
		responseModel = createResposeUtil.buildResponseObject(listCumulativeChartData);
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
	}
		
	
	private CumulativeLineChartModel createModel(){
		CumulativeLineChartModel model = new CumulativeLineChartModel();
		
		List<Double> list1 = new ArrayList<Double>();
		List<List<Double>> list = new ArrayList<List<Double>>();
		
		list1.add(new Double(1083297600000d));
		list1.add(new Double(-2.974623048543));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1085976000000d));
		list1.add(new Double(-1.7740300785979));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1088568000000d));
		list1.add(new Double(4.4681318138177));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1091246400000d));
		list1.add(new Double(7.0242541001353));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1093924800000d));
		list1.add(new Double(7.5709603667586));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1096516800000d));
		list1.add(new Double(20.612245065736));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1099195200000d));
		list1.add(new Double(21.698065237316));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1101790800000d));
		list1.add(new Double(40.501189458018));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1101790800000d));
		list1.add(new Double(40.501189458018));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1104469200000d));
		list1.add(new Double(50.464679413194));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1107147600000d));
		list1.add(new Double(48.917421973355));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1109566800000d));
		list1.add(new Double(63.750936549160));
		list.add(list1);
		list1 = null;

		list1 = new ArrayList<Double>();
		list1.add(new Double(1112245200000d));
		list1.add(new Double(59.072499126460));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1114833600000d));
		list1.add(new Double(43.373158880492));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1117512000000d));
		list1.add(new Double(54.490918947556));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1120104000000d));
		list1.add(new Double(73.450103545496));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1125460800000d));
		list1.add(new Double(71.714526354907));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1128052800000d));
		list1.add(new Double(85.221664349607));
		list.add(list1);
		list1 = null;
		
		list1 = new ArrayList<Double>();
		list1.add(new Double(1130734800000d));
		list1.add(new Double(77.769261392481));
		list.add(list1);
		
		model.setKey("Long");
		model.setMean(250);
		model.setValues(list);
		
		return model;
	}
}
