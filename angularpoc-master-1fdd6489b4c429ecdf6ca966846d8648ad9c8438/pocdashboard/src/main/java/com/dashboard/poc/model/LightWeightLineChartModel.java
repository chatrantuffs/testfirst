/**
 *
 */
package com.dashboard.poc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @Author : Pratyush
 *
 * @Date : Sep 2, 2016
 *
 * @Description : LightWeightLineChartModel.java
 */

@Component
public class LightWeightLineChartModel implements Serializable{

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	private int lowerBound;
	private int upperBound;
	private String title = "Line Chart";
	private String description = "Line Chart Details";
	
	@SuppressWarnings("rawtypes")
	List<Map> listValues;
	
	/**
	 * @return the listValues
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getListValues() {
		return listValues;
	}
	/**
	 * @param listValues the listValues to set
	 */
	@SuppressWarnings("rawtypes")
	public void setListValues(List<Map> listValues) {
		this.listValues = listValues;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the lowerBound
	 */
	public int getLowerBound() {
		return lowerBound;
	}
	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	/**
	 * @return the upperBound
	 */
	public int getUpperBound() {
		return upperBound;
	}
	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
	
}
