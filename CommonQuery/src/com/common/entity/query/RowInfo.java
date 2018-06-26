package com.common.entity.query;

import java.io.Serializable;
import java.util.Map;

public class RowInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5413017558553112600L;
	
	private Long serialNum;
	private String strSelValue;
	private Map<String,Integer[]> colUnit;
	private Map<String,Object> rowInfo;
	
	public Long getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(Long serialNum) {
		this.serialNum = serialNum;
	}
	public Map<String, Integer[]> getColUnit() {
		return colUnit;
	}
	public void setColUnit(Map<String, Integer[]> colUnit) {
		this.colUnit = colUnit;
	}
	public Map<String, Object> getRowInfo() {
		return rowInfo;
	}
	public void setRowInfo(Map<String, Object> rowInfo) {
		this.rowInfo = rowInfo;
	}
	public String getStrSelValue() {
		return strSelValue;
	}
	public void setStrSelValue(String strSelValue) {
		this.strSelValue = strSelValue;
	}
}
