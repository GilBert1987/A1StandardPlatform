package com.common.entity.tree;

import java.io.Serializable;
import java.util.List;

import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Tree implements ILastmodifiedTime,IFileDirectory,Serializable{
	private String name;
	private String code;
	private String selectnode;
	private String lastmodifiedtime;
	private String jsinfo;
	private String filedir;
	private String remark;
	private Boolean isUpdate;
	private List<TreeNode> listNode;
	
	@Override
	public String getFiledir() {
		return filedir;
	}
	@Override
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public List<TreeNode> getListNode() {
		return listNode;
	}
	public void setListNode(List<TreeNode> listNode) {
		this.listNode = listNode;
	}
	public String getJsinfo() {
		return jsinfo;
	}
	public void setJsinfo(String jsinfo) {
		this.jsinfo = jsinfo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSelectnode() {
		return selectnode;
	}
	public void setSelectnode(String selectnode) {
		this.selectnode = selectnode;
	}
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
	@Override
	public Boolean getIsUpdate() {
		return this.isUpdate;
	}
	@Override
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate=isUpdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
