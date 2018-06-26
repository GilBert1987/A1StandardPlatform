package com.common.entity.firebutton;

import java.io.Serializable;
import java.util.List;

public class Button implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934807183209652853L;
	private String id;
	private String tag;
	private List<String> jsFuction;
	private List<RelObjInfo> fireFuction;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public List<String> getJsFuction() {
		return jsFuction;
	}
	public void setJsFuction(List<String> jsFuction) {
		this.jsFuction = jsFuction;
	}
	public List<RelObjInfo> getFireFuction() {
		return fireFuction;
	}
	public void setFireFuction(List<RelObjInfo> fireFuction) {
		this.fireFuction = fireFuction;
	}
}
