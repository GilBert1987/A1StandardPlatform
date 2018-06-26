package com.common.entity.query;

import java.io.Serializable;

public class Title implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8271583599875044490L;
	
	private String sehead;
	private String isshow;
	private String order;
	private String isedit;
	private String width;
	private String align;
	
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getIsedit() {
		return isedit;
	}
	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getSehead() {
		return sehead;
	}
	public void setSehead(String sehead) {
		this.sehead = sehead;
	}
}
