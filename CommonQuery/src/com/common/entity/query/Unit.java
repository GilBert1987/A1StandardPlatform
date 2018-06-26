package com.common.entity.query;

import java.io.Serializable;

public class Unit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 208263083622986743L;
	
	private String merge;
	private String mergerefer;
	private String mergecolumn;
	
	public String getMerge() {
		return merge;
	}
	public void setMerge(String merge) {
		this.merge = merge;
	}
	public String getMergerefer() {
		return mergerefer;
	}
	public void setMergerefer(String mergerefer) {
		this.mergerefer = mergerefer;
	}
	public String getMergecolumn() {
		return mergecolumn;
	}
	public void setMergecolumn(String mergecolumn) {
		this.mergecolumn = mergecolumn;
	}
}
