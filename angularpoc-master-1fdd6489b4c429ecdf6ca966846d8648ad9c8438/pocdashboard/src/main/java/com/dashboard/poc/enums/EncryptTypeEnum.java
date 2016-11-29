/**
 * 
 */
package com.dashboard.poc.enums;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

public enum EncryptTypeEnum {
	MD5() {
		@Override
		public String encryptType() {
			return "MD5";
		}
	},
	SHA1() {
		@Override
		public String encryptType() {
			return "SHA-1";
		}
	},
	SHA256() {
		@Override
		public String encryptType() {
			return "SHA-256";
		}
	},
	DEFAULT() {
		@Override
		public String encryptType() {
			return "SHA-1";
		}
	};
	
	public abstract String encryptType();

	private EncryptTypeEnum() {
	}

}
