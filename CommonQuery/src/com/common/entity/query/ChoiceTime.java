package com.common.entity.query;

import java.io.Serializable;

public class ChoiceTime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1211437062196212665L;
	
	private String type;
	private String pagenum;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	
}
