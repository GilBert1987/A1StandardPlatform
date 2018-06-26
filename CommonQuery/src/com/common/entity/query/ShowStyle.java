package com.common.entity.query;

import java.io.Serializable;

public class ShowStyle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5577905366268263975L;
	
	private String type;
	private String isshow;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
}
