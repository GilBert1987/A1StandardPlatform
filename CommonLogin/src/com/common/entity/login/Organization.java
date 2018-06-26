package com.common.entity.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Organization implements Serializable{

	/** 描述 */
	private static final long serialVersionUID = -7324011210610828114L;

	private String id;
	
	private String name;

	private String description;
	
	private String platformid;
	
	private String tenantid;


	public String getPlatformid() {
		return platformid;
	}

	public void setPlatformid(String platformid) {
		this.platformid = platformid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 name 的值
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name 的值
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 description 的值
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 description 的值
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

}
