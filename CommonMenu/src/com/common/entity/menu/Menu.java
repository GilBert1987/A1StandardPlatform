package com.common.entity.menu;

import java.io.Serializable;
import java.util.List;
import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Menu implements ILastmodifiedTime,IFileDirectory,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private String title;
	private String code;
	private String titlepic;
	private Integer iroot;
	private String rooturl;
	private String lastmodifiedtime;
	private String filedir;
	private String remark;
	private Integer level;
	private List<ShortCut> listShortCuts;
	private List<SubMenu> listSubMenus;
	private Boolean isUpdate;
	
	@Override
	public String getFiledir() {
		return filedir;
	}
	@Override
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
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
	public List<ShortCut> getListShortCuts() {
		return listShortCuts;
	}
	public void setListShortCuts(List<ShortCut> listShortCuts) {
		this.listShortCuts = listShortCuts;
	}
	public List<SubMenu> getListSubMenus() {
		return listSubMenus;
	}
	public void setListSubMenus(List<SubMenu> listSubMenus) {
		this.listSubMenus = listSubMenus;
	}
	public String getRooturl() {
		return rooturl;
	}
	public void setRooturl(String rooturl) {
		this.rooturl = rooturl;
	}
	/**
	 * @return the iroot
	 */
	public Integer getIroot() {
		return iroot;
	}
	/**
	 * @param iroot the iroot to set
	 */
	public void setIroot(Integer iroot) {
		this.iroot = iroot;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
}
