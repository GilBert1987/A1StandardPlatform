package com.common.entity.query;

import java.io.Serializable;

public class InitSql implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3127999947550139635L;
	
	private String strsql;
	private String type;
	
	public String getStrsql() {
		return strsql;
	}
	public void setStrsql(String strsql) {
		this.strsql = strsql;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
