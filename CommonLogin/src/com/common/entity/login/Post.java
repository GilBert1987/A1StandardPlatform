package com.common.entity.login;

import java.io.Serializable;

public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4683506184851485017L;

	private String id;
	
	private String name;
	
	private String level;
	
	private String tenantid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

}
