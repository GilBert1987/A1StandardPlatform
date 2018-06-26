package com.common.entity.activiti;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -321246632072005203L;
	private String id;
	private String startcode;
	private String endcode;
	private Map<String, Map<String, List<Map<String,String>>>> authoritys;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartcode() {
		return startcode;
	}
	public void setStartcode(String startcode) {
		this.startcode = startcode;
	}
	public String getEndcode() {
		return endcode;
	}
	public void setEndcode(String endcode) {
		this.endcode = endcode;
	}
	public Map<String, Map<String, List<Map<String,String>>>> getAuthoritys() {
		return authoritys;
	}
	public void setAuthoritys(Map<String, Map<String, List<Map<String,String>>>> authoritys) {
		this.authoritys = authoritys;
	}
}
