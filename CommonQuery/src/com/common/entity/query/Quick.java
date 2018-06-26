package com.common.entity.query;

import java.io.Serializable;

public class Quick implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7428376870746479423L;
	
	private String isshow;
	private String showname;
	private String showoperation;
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getShowoperation() {
		return showoperation;
	}
	public void setShowoperation(String showoperation) {
		this.showoperation = showoperation;
	}
}
