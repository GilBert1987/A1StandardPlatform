package com.common.entity.query;

import java.io.Serializable;

public class Search implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 160950768627275382L;
	
	private String isshow;
	private String order;
	private String type;
	private String datatype;
	private String searchinfo;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getSearchinfo() {
		return searchinfo;
	}
	public void setSearchinfo(String searchinfo) {
		this.searchinfo = searchinfo;
	}
}
