package com.common.entity.tree;

import java.io.Serializable;

public class TreeButton implements Serializable {
	
	private String name;
	private String order;
	private String fun;
	private String ico;
	private String type;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFun() {
		return fun;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
