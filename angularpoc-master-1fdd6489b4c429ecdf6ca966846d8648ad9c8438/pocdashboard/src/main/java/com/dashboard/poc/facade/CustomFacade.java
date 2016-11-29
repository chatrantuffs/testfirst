/**
 * 
 */
package com.dashboard.poc.facade;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class CustomFacade {
	
	private final GsonBuilder builder = new GsonBuilder();
	private final Gson gson = builder.create();
	
	public String convertObjToString(Object object){
		return object != null ? gson.toJson(object) : StringUtils.EMPTY;
	}
	
}
