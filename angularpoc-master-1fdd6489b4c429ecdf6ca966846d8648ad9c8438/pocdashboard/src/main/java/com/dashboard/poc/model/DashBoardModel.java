/**
 * 
 */
package com.dashboard.poc.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class DashBoardModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	
	private LineChartModel lineChartModel;
	private CumulativeLineChartModel cumulativeLineChartModel;
	
	
	/**
	 * @return the cumulativeLineChartModel
	 */
	public CumulativeLineChartModel getCumulativeLineChartModel() {
		return cumulativeLineChartModel;
	}
	/**
	 * @param cumulativeLineChartModel the cumulativeLineChartModel to set
	 */
	public void setCumulativeLineChartModel(
			CumulativeLineChartModel cumulativeLineChartModel) {
		this.cumulativeLineChartModel = cumulativeLineChartModel;
	}
	/**
	 * @return the lineChartModel
	 */
	public LineChartModel getLineChartModel() {
		return lineChartModel;
	}
	/**
	 * @param lineChartModel the lineChartModel to set
	 */
	public void setLineChartModel(LineChartModel lineChartModel) {
		this.lineChartModel = lineChartModel;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
}
