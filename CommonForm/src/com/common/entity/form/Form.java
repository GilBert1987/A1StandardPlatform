package com.common.entity.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.common.entity.firebutton.Button;
import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Form implements ILastmodifiedTime,IFileDirectory,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1583431943515065612L;
	private String sysid;
	private String dataname;
	private String datatype;
	private String code;
	private String name;
	private String title;
	private String showButton;
	private String showFormName;
	private String remark;
	private String dynamicform;
	private String jstag;
	private String filedir;
	private String lastmodifiedtime;
	private List<String> tags;
	private List<Button> buttons;
	private List<String> hiddenform;
	private List<DynamicParm> dyparm;
	private List<Map<String,String>> controls;
	private Map<String, Map<String, List<Map<String,String>>>> authoritys;
	private Boolean isUpdate;
	
	@Override
	public String getFiledir() {
		return filedir;
	}
	@Override
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	@Override
	public String getLastmodifiedtime() {
		return lastmodifiedtime;
	}
	
	@Override
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
	
	public List<DynamicParm> getDyparm() {
		return dyparm;
	}
	public void setDyparm(List<DynamicParm> dyparm) {
		this.dyparm = dyparm;
	}
	public String getShowFormName() {
		return showFormName;
	}
	public void setShowFormName(String showFormName) {
		this.showFormName = showFormName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getDataname() {
		return dataname;
	}
	public void setDataname(String dataname) {
		this.dataname = dataname;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowButton() {
		return showButton;
	}
	public void setShowButton(String showButton) {
		this.showButton = showButton;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<String> getHiddenform() {
		return hiddenform;
	}
	public void setHiddenform(List<String> hiddenform) {
		this.hiddenform = hiddenform;
	}
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	public String getDynamicform() {
		return dynamicform;
	}
	public void setDynamicform(String dynamicform) {
		this.dynamicform = dynamicform;
	}
	public String getJstag() {
		return jstag;
	}
	public void setJstag(String jstag) {
		this.jstag = jstag;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<Map<String,String>> getControls() {
		return controls;
	}
	public void setControls(List<Map<String,String>> controls) {
		this.controls = controls;
	}
	public Map<String, Map<String, List<Map<String,String>>>> getAuthoritys() {
		return authoritys;
	}
	public void setAuthoritys(Map<String, Map<String, List<Map<String,String>>>> authoritys) {
		this.authoritys = authoritys;
	}
}
