package com.common.entity.tree;

import java.io.Serializable;

public class TreeSqlInfo implements Serializable {
	
	private String type;
	private String database;
	private String sqlInfo;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getSqlInfo() {
		return sqlInfo;
	}
	public void setSqlInfo(String sqlInfo) {
		this.sqlInfo = sqlInfo;
	}	
}
