package com.common.entity.tool;

import java.io.Serializable;

public class ItemShow implements Serializable {
	private String code;
	private String isshow;
	private String isselect;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public String getIsselect() {
		return isselect;
	}
	public void setIsselect(String isselect) {
		this.isselect = isselect;
	}
}
