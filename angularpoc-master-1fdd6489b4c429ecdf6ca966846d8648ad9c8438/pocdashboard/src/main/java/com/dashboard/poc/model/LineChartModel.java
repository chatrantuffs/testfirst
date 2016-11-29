/**
 *
 */
package com.dashboard.poc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @Author : Pratyush
 *
 * @Date : Aug 30, 2016
 *
 * @Description : LineChartModel.java
 */
@Component
public class LineChartModel implements Serializable{
	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private List<Map> values;
	private String key;
	private String color;
	private boolean area = false;
	private int seriesIndex = 0;
	@JsonInclude(Include.NON_EMPTY)
	private String displayStatus = StringUtils.EMPTY;
	
	/**
	 * @return the displayStatus
	 */
	public String getDisplayStatus() {
		return displayStatus;
	}
	/**
	 * @param displayStatus the displayStatus to set
	 */
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}
	/**
	 * @return the seriesIndex
	 */
	public int getSeriesIndex() {
		return seriesIndex;
	}
	/**
	 * @param seriesIndex the seriesIndex to set
	 */
	public void setSeriesIndex(int seriesIndex) {
		this.seriesIndex = seriesIndex;
	}
	/**
	 * @return the values
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	@SuppressWarnings("rawtypes")
	public void setValues(List<Map> values) {
		this.values = values;
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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the area
	 */
	public boolean isArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(boolean area) {
		this.area = area;
	}
	
	
}
