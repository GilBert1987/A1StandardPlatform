package com.common.entity.login;

import java.io.Serializable;

public class Role implements Serializable{
	
	private static final long serialVersionUID = -5537665695891354775L;
	
	private String id;
	
	private String name;
	
	private int type;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
}
