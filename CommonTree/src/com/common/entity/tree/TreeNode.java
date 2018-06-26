package com.common.entity.tree;

import java.io.Serializable;
import java.util.List;

public class TreeNode implements Serializable {
	private String level;
	private String name;
	private String type;
	private String nodeid;
	private String nodetext;
	private String option;
	private String url;
	private String css;
	private String icon;
	private String iconOpen;
	private String iconClose;
	private String selfun;
	private String async;
	private List<TreeButton> listButton;
	private TreeSqlInfo sqlInfo;
	private String remark;
	private String target;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getAsync() {
		return async;
	}
	public void setAsync(String async) {
		this.async = async;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconOpen() {
		return iconOpen;
	}
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}
	public String getIconClose() {
		return iconClose;
	}
	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getNodetext() {
		return nodetext;
	}
	public void setNodetext(String nodetext) {
		this.nodetext = nodetext;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getSelfun() {
		return selfun;
	}
	public void setSelfun(String selfun) {
		this.selfun = selfun;
	}
	public List<TreeButton> getListButton() {
		return listButton;
	}
	public void setListButton(List<TreeButton> listButton) {
		this.listButton = listButton;
	}
	public TreeSqlInfo getSqlInfo() {
		return sqlInfo;
	}
	public void setSqlInfo(TreeSqlInfo sqlInfo) {
		this.sqlInfo = sqlInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
