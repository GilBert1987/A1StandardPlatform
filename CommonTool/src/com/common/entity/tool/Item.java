package com.common.entity.tool;

import java.io.Serializable;

public class Item implements Serializable {
	private String code;
	private String name;
	private String btnclass;
	private String icon;
	private String iconstyle;
	private String url;
	private String order;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getBtnclass() {
		return btnclass;
	}
	public void setBtnclass(String btnclass) {
		this.btnclass = btnclass;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconstyle() {
		return iconstyle;
	}
	public void setIconstyle(String iconstyle) {
		this.iconstyle = iconstyle;
	}
}
