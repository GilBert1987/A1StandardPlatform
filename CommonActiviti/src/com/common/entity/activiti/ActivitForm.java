package com.common.entity.activiti;

import java.io.Serializable;

public class ActivitForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7200228850190145760L;
	private String code;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
