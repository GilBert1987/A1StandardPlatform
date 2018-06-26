package com.common.entity.firebutton;

import java.io.Serializable;
import java.util.*;

public class RelObjInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2845701739177773103L;
	private String className;
	private String method;
	private List<Map<String,String>> list;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<Map<String,String>> getList() {
		return list;
	}
	public void setList(List<Map<String,String>> list) {
		this.list = list;
	}
}
