package com.common.entity.query;

import java.io.Serializable;

public class PriKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6050195379047544721L;
	
	private String key;
	private String type;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
