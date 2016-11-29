/**
 * 
 */
package com.dashboard.poc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dashboard.poc.custom.model.ErrorModel;
import com.dashboard.poc.custom.model.ResponseModel;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CreateResposeUtil {
	
	@Autowired
	ResponseModel responseModel;
	
	@Autowired
	ErrorModel errorModel;
	
	public ResponseModel buildResponseObject(final Object data){
		responseModel.setData(data);
		responseModel.setError(new ErrorModel());
		return responseModel;
	}
	
	public ResponseModel buildResponseObject(final Object data, final int errorCode, final String errorDescription){
		responseModel.setData(data);
		errorModel.setErrorCode(errorCode);
		errorModel.setErrorDescription(errorDescription);
		responseModel.setError(errorModel);
		return responseModel;
	}
}
