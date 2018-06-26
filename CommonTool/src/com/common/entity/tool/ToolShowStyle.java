package com.common.entity.tool;

import java.io.Serializable;
import java.util.List;

public class ToolShowStyle implements Serializable {
	private String code;
	private String name;
	private List<ItemShow> listshow;
	private String remark;
	
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
	public List<ItemShow> getListshow() {
		return listshow;
	}
	public void setListshow(List<ItemShow> listshow) {
		this.listshow = listshow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
