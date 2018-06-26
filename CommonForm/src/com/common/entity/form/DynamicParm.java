package com.common.entity.form;

import java.io.Serializable;

public class DynamicParm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5856992432848239596L;
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
