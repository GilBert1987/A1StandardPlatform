package com.common.entity.query;

import java.io.Serializable;
import java.util.List;

public class PageStyle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3502080643496704891L;
	
	private List<ShowStyle> showlist;
	private String type;
	
	public List<ShowStyle> getShowlist() {
		return showlist;
	}
	public void setShowlist(List<ShowStyle> showlist) {
		this.showlist = showlist;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
