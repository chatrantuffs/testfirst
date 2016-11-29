/**
 * 
 */
package com.dashboard.poc.custom.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Component
public class ResponseDataModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int recordCounts;
	private int successCounts;
	private int failureCounts;
	/**
	 * @return the recordCounts
	 */
	public int getRecordCounts() {
		return recordCounts;
	}
	/**
	 * @param recordCounts the recordCounts to set
	 */
	public void setRecordCounts(int recordCounts) {
		this.recordCounts = recordCounts;
	}
	/**
	 * @return the successCounts
	 */
	public int getSuccessCounts() {
		return successCounts;
	}
	/**
	 * @param successCounts the successCounts to set
	 */
	public void setSuccessCounts(int successCounts) {
		this.successCounts = successCounts;
	}
	/**
	 * @return the failureCounts
	 */
	public int getFailureCounts() {
		return failureCounts;
	}
	/**
	 * @param failureCounts the failureCounts to set
	 */
	public void setFailureCounts(int failureCounts) {
		this.failureCounts = failureCounts;
	}
	
}
