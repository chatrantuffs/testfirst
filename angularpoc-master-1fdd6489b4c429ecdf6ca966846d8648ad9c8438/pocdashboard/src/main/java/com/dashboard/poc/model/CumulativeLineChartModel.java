/**
 *
 */
package com.dashboard.poc.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @Author : Pratyush
 *
 * @Date : Sep 1, 2016
 *
 * @Description : CumulativeLineChartModel.java
 */
@Component
public class CumulativeLineChartModel implements Serializable{

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	
	private int mean;
	private String key;
	private List<List<Double>> values;
	/**
	 * @return the mean
	 */
	public int getMean() {
		return mean;
	}
	/**
	 * @param mean the mean to set
	 */
	public void setMean(int mean) {
		this.mean = mean;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the values
	 */
	public List<List<Double>> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(List<List<Double>> values) {
		this.values = values;
	}

}
