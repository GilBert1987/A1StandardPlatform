package com.common.entity.activiti;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.common.entity.firebutton.Button;
import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class WorkFlow implements ILastmodifiedTime,IFileDirectory,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1295415142216849265L;
	private String code;
	private String name;
	private String initformkey;
	private String jstag;
	private String dynamicform;
	private String filedir;
	private String remark;
	private String defid;
	private Integer version;
	private String jsonstring;
	private List<ActivitForm> forms;
	private List<FormKey> formkeys;
	private List<String> tags;
	private List<Button> buttons;
	private List<String> hiddenform;
	private List<Map<String,String>> controls;
	private String lastmodifiedtime;
	private Boolean isUpdate;
	
	public String getDynamicform() {
		return dynamicform;
	}

	public void setDynamicform(String dynamicform) {
		this.dynamicform = dynamicform;
	}

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

	public List<ActivitForm> getForms() {
		return forms;
	}

	public void setForms(List<ActivitForm> forms) {
		this.forms = forms;
	}

	public List<FormKey> getFormkeys() {
		return formkeys;
	}

	public void setFormkeys(List<FormKey> formkeys) {
		this.formkeys = formkeys;
	}

	public String getInitformkey() {
		return initformkey;
	}

	public void setInitformkey(String initformkey) {
		this.initformkey = initformkey;
	}

	public String getJsonstring() {
		return jsonstring;
	}

	public void setJsonstring(String jsonstring) {
		this.jsonstring = jsonstring;
	}

	/**
	 * @return the defid
	 */
	public String getDefid() {
		return defid;
	}

	/**
	 * @param defid the defid to set
	 */
	public void setDefid(String defid) {
		this.defid = defid;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
