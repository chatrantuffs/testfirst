/**
 * 
 */
package com.dashboard.poc.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dashboard.poc.constant.SystemConstant;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	static Logger logger = Logger.getLogger(CustomSuccessHandler.class.getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int operatorId = 1;
		try {
			/*HttpSession session = request.getSession(true);
			String user = authentication.getName();
			String userServiceJson = userService
					.loadOperatorByOparatorName(user);
			String buttonValues = userService.getButtonLavelValues();
			JSONObject jsonObj = new JSONObject(buttonValues);
			JSONArray loadButtonLavelarray = jsonObj.getJSONArray("data");
			Map<String, String> buttonValuesList = new HashMap<String, String>();
			ButtonLabel buttonLabelsession = null;
			for (int i = 0; i < loadButtonLavelarray.length(); i++) {
				JSONObject buttonLabelarrayJsonObject = (JSONObject) loadButtonLavelarray
						.get(i);
				buttonLabelsession = new ButtonLabel();
				buttonLabelsession
						.setButtonLabelText(buttonLabelarrayJsonObject
								.getString(SystemConstant.BUTTONLABELTEXT));
				String checkValue = buttonLabelarrayJsonObject
						.getString(SystemConstant.BUTTONLABELTEXT);
				if (checkValue == null || checkValue.isEmpty()) {
					checkValue = buttonLabelarrayJsonObject
							.getString(SystemConstant.BUTTONLABELNAME);
				}
				buttonValuesList.put(buttonLabelarrayJsonObject
						.getString(SystemConstant.BUTTONLABELNAME), checkValue);
			}
			session.setAttribute(SystemConstant.BUTTONLABELVALUES,buttonValuesList);  
			OperatorTo checkUserStatus = mapper.readValue(userServiceJson,
					OperatorTo.class);
			 session.setAttribute(SystemConstant.OPRT_ID,checkUserStatus.getOperatorId());
                         session.setAttribute(SystemConstant.OP_ID,String.valueOf(checkUserStatus.getOperatorId()));
                        
			String accountLock = userService.chkUserAcconutLock(user);
			boolean accountLockFlag = Boolean.parseBoolean(accountLock);
			
			//Set the Operator table values
			userService.updateOperatorDailyPayAmt(user,String.valueOf(operatorId));
			
			if (checkUserStatus.getOperatorStatus() < 1 || checkUserStatus.getOperatorStatus() == 2) {
				String systemErrorMessage = userService.userErrMessage();
				JSONObject errorObject = null;
				try {
					errorObject = new JSONObject(systemErrorMessage);
				} catch (Exception ex) {
					MDC.put(UIConstant.OPERATORID, operatorId);
					LOGGER.error(ex, ex);
				}
				try {
					String accountDisabled = (String) errorObject
							.get(SystemMsg.ACCOUNT_DISABLED.toString());
					session.setAttribute(SystemConstant.ACCOUNT_DISABLED,
							accountDisabled);
					response.sendRedirect(SystemConstant.LOGIN_FAILED);
				} catch (Exception ex) {
					MDC.put(UIConstant.OPERATORID, operatorId);
					LOGGER.error(ex, ex);
				}
			} 
			else if ( checkUserStatus.getOperatorStatus() == 3) {
				String systemErrorMessageDisable = userService.userErrMessage();

				JSONObject errorObject1 = new JSONObject(systemErrorMessageDisable);
		
				try {
					String inactiveUser = (String) errorObject1.get(SystemMsg.FIELD_NOT_EXISTS.toString());
					String error = inactiveUser.replaceAll(SystemConstant.F1,
							"User");
					session.setAttribute("inactiveUser", error);
					response.sendRedirect(SystemConstant.LOGIN_FAILED);
					
				} catch (Exception ex) {
					MDC.put(UIConstant.OPERATORID, operatorId);
					LOGGER.error(ex, ex);
				}
			}else if (accountLockFlag == true) {
				String systemErrorMessage = userService.userErrMessage();
				JSONObject jObject = null;
				try {
					jObject = new JSONObject(systemErrorMessage);
				} catch (Exception ex) {
					MDC.put(UIConstant.OPERATORID, operatorId);
					LOGGER.error(ex, ex);
				}
				String accountLockError = null;
				try {
					accountLockError = (String) jObject
							.get(SystemMsg.ACCOUNT_LOCKED.toString());
				} catch (Exception ex) {
					MDC.put(UIConstant.OPERATORID, operatorId);
					LOGGER.error(ex, ex);
				}
				session.setAttribute(SystemConstant.ACCOUNT_LOCKED,
						accountLockError);
				session.setAttribute(SystemConstant.USER_NAME, user);
				response.sendRedirect(SystemConstant.LOGIN_FAILED);
			} else {
				String defaultPage = userService
						.getDefaultPageByUser(checkUserStatus.getOperatorId());
				JSONArray defaultPageArray = new JSONArray(defaultPage);

				List<RoleCheck> defaultPageList = new ArrayList<RoleCheck>();
				int duplicatecheck = 0;

				for (int i = 0; i < defaultPageArray.length(); i++) {
					JSONObject sysJsonObject = (JSONObject) defaultPageArray
							.get(i);
					RoleCheck roleCheck = new RoleCheck();
					roleCheck.setSystemSetupID(sysJsonObject
							.getInt(JSONlConstant.SYSTEMSETUPID));
					roleCheck.setCode(sysJsonObject
							.getString(JSONlConstant.CODE));
					roleCheck.setDescription(sysJsonObject
							.getString(JSONlConstant.DESCRIPTION));

					if (duplicatecheck == sysJsonObject
							.getInt(JSONlConstant.VALUE)) {

					} else {
						roleCheck.setValue(sysJsonObject
								.getInt(JSONlConstant.VALUE));
					}

					roleCheck.setText(sysJsonObject
							.getString(JSONlConstant.TEXT));
					defaultPageList.add(roleCheck);
					duplicatecheck = sysJsonObject.getInt(JSONlConstant.VALUE);
				}

				Collections.sort(defaultPageList);
				session.setAttribute(SystemConstant.OPRT_ID,
						checkUserStatus.getOperatorId());
                                session.setAttribute(SystemConstant.OP_ID,String.valueOf(checkUserStatus.getOperatorId()));
				session.setAttribute(SystemConstant.DEFAULT_PAGE_LIST,
						defaultPageList);
				session.setAttribute(SystemConstant.NAME,
						checkUserStatus.getUserID());
				response.sendRedirect(SystemConstant.WELCOME);
			}*/
                       
		} catch (Exception ex) {
			MDC.put(SystemConstant.OPERATORID, operatorId);
			logger.error(ex, ex);
		}
	}

}
