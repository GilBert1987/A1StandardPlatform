package com.common.entity.frame;

import java.io.Serializable;
import java.util.List;

import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Frame implements ILastmodifiedTime,IFileDirectory,Serializable{
	private String name;
	private String code;
	private String type;
	private String remark;
	private String filedir;
	private String lastmodifiedtime;
	private Boolean isUpdate;
	private List<FrameNode> nodelist;
	
	@Override
	public String getFiledir() {
		return filedir;
	}
	@Override
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public List<FrameNode> getNodelist() {
		return nodelist;
	}
	public void setNodelist(List<FrameNode> nodelist) {
		this.nodelist = nodelist;
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
