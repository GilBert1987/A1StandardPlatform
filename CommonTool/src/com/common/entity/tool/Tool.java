package com.common.entity.tool;

import java.io.Serializable;
import java.util.List;

import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Tool implements ILastmodifiedTime,IFileDirectory,Serializable{
	private String name;
	private String code;
	private List<Item> listitem;
	private List<ToolShowStyle> liststyle;
	private String remark;
	private String filedir;
	private String lastmodifiedtime;
	private Boolean isUpdate;
	
	@Override
	public String getFiledir() {
		return filedir;
	}
	@Override
	public void setFiledir(String filedir) {
		this.filedir = filedir;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Item> getListitem() {
		return listitem;
	}
	public void setListitem(List<Item> listitem) {
		this.listitem = listitem;
	}
	public List<ToolShowStyle> getListstyle() {
		return liststyle;
	}
	public void setListstyle(List<ToolShowStyle> liststyle) {
		this.liststyle = liststyle;
	}
	@Override
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	
	@Override
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
	
	@Override
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	
	@Override
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
}
