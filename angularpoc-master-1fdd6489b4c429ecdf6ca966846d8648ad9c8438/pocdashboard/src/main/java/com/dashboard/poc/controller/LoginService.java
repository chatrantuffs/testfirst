/**
 * 
 */
package com.dashboard.poc.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dashboard.poc.constant.SystemConstant;
import com.dashboard.poc.constant.UrlConstant;
import com.dashboard.poc.custom.model.ResponseModel;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.model.User;
import com.dashboard.poc.service.ILoginService;
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

@Api(value = "LoginService", description = "Login Service")// Swagger annotation
@RestController
@RequestMapping(value = UrlConstant._LOGIN_SERVICE_URL)
@PropertySource("classpath:applicationResources.properties")
@SessionAttributes("userid")
public class LoginService {

	@Autowired
	ILoginService loginService;
	
	@Autowired
	CreateResposeUtil createResposeUtil;
	
	@Autowired
	CustomUtil customUtil;
	
	@Autowired
	SystemConstant systemConstant;
	
	@Autowired 
	private HttpSession httpSession;
	
	@Autowired
	private Environment env;
	
	static Logger logger = Logger.getLogger(LoginService.class.getName());

	
	@ApiOperation(value = "Returns appropriate Http Response code", notes = "Returns a specific user object based on user name and password given.")
	@RequestMapping(value = UrlConstant._VALIDATE_USER_URL, method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> validateUser(@ApiParam(name = "user", value = "user", required = true) @RequestBody User user,
			@ApiParam(name = "auth_token", value = "Token", required = true) @RequestHeader("auth_token") String token,
			HttpServletResponse response) {
		ResponseModel responseModel = null;
		User rUser = null;
		try{
			if(user != null){
				rUser = loginService.validateUser(user.getUsername(), user.getPassword());
				if(httpSession != null && httpSession.getAttribute(SystemConstant._SESSION_USERID) == null){
					httpSession.setAttribute(SystemConstant._SESSION_USERID, user.getUsername());
				}
				if (rUser == null) {
					logger.info("User not found");
					httpSession.removeAttribute(SystemConstant._SESSION_USERID);
					responseModel = createResposeUtil.buildResponseObject(rUser, systemConstant.getErrCodeNotFound(), env.getProperty("error.user.notfound"));
					return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
				}
				if(logger.isDebugEnabled())
					logger.debug("User logged in to the application successfully");
				
				//Create the token and set it to the response
				customUtil.setAuthToken(user.getUsername(), response);
				//Create the response string
				responseModel = createResposeUtil.buildResponseObject(rUser);
			}
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
		}catch(CustomException e){
			responseModel = createResposeUtil.buildResponseObject(rUser, systemConstant.getErrCustomException(), e.getMessage());
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
