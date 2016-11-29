/**
 * 
 */
package com.dashboard.poc.enums;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public enum TokenTimeEnum {
	MILLISECONDS, SECONDS, MINUTES, HOURS, DAY;

	public long time(final String tokenDurationType, final long timeduration) {
		if(MILLISECONDS.name().equalsIgnoreCase(tokenDurationType)){
			return 1L * timeduration;
		}else if(SECONDS.name().equalsIgnoreCase(tokenDurationType)){
			return 1000L * timeduration;
		}else if(MINUTES.name().equalsIgnoreCase(tokenDurationType)){
			return 1000L * 60L * timeduration;
		}else if(HOURS.name().equalsIgnoreCase(tokenDurationType)){
			return 1000L * 60L * 60L * timeduration;
		}else if(DAY.name().equalsIgnoreCase(tokenDurationType)){
			return 1000L * 60L * 60L * 24L * timeduration;
		}else{
			throw new AssertionError("Unknown Time Duration " + this);
		}
	}
}
