package com.common.entity.query;

import java.io.Serializable;

public class QueryButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4212506806181689537L;
	
	private String name;
	private String icon;
	private String order;
	private String jsclick;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getJsclick() {
		return jsclick;
	}
	public void setJsclick(String jsclick) {
		this.jsclick = jsclick;
	}
}
