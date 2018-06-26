package com.common.entity.query;

import java.io.Serializable;

public class Column implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6043597516826578407L;
	
	private String key;
	private String name;
	private String type;
	private String typeid;
	private String grouptype;
	private Search search;
	private Title title;
	private Unit unit;
	private Quick quick;
	private String relColId;
	private String titColId;
	private String currColId;
	private String currValId;
	private String dataSysId;
	private String titleSysId;
	private String dataSqlInfo;
	private String titleSqlInfo;
	
	public String getGrouptype() {
		return grouptype;
	}
	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Quick getQuick() {
		return quick;
	}
	public void setQuick(Quick quick) {
		this.quick = quick;
	}
	/**
	 * @return the typeid
	 */
	public String getTypeid() {
		return typeid;
	}
	/**
	 * @param typeid the typeid to set
	 */
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getRelColId() {
		return relColId;
	}
	public void setRelColId(String relColId) {
		this.relColId = relColId;
	}
	public String getTitColId() {
		return titColId;
	}
	public void setTitColId(String titColId) {
		this.titColId = titColId;
	}
	public String getCurrColId() {
		return currColId;
	}
	public void setCurrColId(String currColId) {
		this.currColId = currColId;
	}
	public String getDataSysId() {
		return dataSysId;
	}
	public void setDataSysId(String dataSysId) {
		this.dataSysId = dataSysId;
	}
	public String getTitleSysId() {
		return titleSysId;
	}
	public void setTitleSysId(String titleSysId) {
		this.titleSysId = titleSysId;
	}
	public String getDataSqlInfo() {
		return dataSqlInfo;
	}
	public void setDataSqlInfo(String dataSqlInfo) {
		this.dataSqlInfo = dataSqlInfo;
	}
	public String getTitleSqlInfo() {
		return titleSqlInfo;
	}
	public void setTitleSqlInfo(String titleSqlInfo) {
		this.titleSqlInfo = titleSqlInfo;
	}
	public String getCurrValId() {
		return currValId;
	}
	public void setCurrValId(String currValId) {
		this.currValId = currValId;
	}
}
