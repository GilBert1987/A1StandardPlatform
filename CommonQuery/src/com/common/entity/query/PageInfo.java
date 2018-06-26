package com.common.entity.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageInfo<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2391762728747284895L;
	
	private long rowCount = 0L;
	private Map<String,String> mapColInfo;
	private List<T> list = new ArrayList();
	
	public PageInfo(Map<String,String> mapColInfo,long rowNo, List<T> t) {
		this.rowCount = rowNo;
		this.list = t;
		this.mapColInfo=mapColInfo;
	}

	public long getRowCount() {
		return this.rowCount;
	}

	public List<T> getList() {
		return this.list;
	}

	public Map<String, String> getMapColInfo() {
		return mapColInfo;
	}
}
