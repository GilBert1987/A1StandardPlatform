package com.common.entity.query;

import java.io.Serializable;
import java.util.List;

import com.common.iface.IFileDirectory;
import com.common.iface.ILastmodifiedTime;

public class Query implements ILastmodifiedTime,IFileDirectory,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5234678660081284009L;
	
	private String name;
	private String title;
	private String head;
	private String code;
	private String sysid;
	private String datatype;
	private String sqlinfo;
	private String defaultparm;
	private String orderkey;
	private String jsfunction;
	private String remark;
	private String filedir;
	private String lastmodifiedtime;
	private InitSql initsql;
	private PriKey prikey;
	private ChoiceTime choicetime;
	private PageStyle pagestyle;
	private List<Column> columns;
	private List<QueryButton> buttons;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getSqlinfo() {
		return sqlinfo;
	}
	public void setSqlinfo(String sqlinfo) {
		this.sqlinfo = sqlinfo;
	}
	public String getDefaultparm() {
		return defaultparm;
	}
	public void setDefaultparm(String defaultparm) {
		this.defaultparm = defaultparm;
	}
	public String getOrderkey() {
		return orderkey;
	}
	public void setOrderkey(String orderkey) {
		this.orderkey = orderkey;
	}
	public String getJsfunction() {
		return jsfunction;
	}
	public void setJsfunction(String jsfunction) {
		this.jsfunction = jsfunction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public InitSql getInitsql() {
		return initsql;
	}
	public void setInitsql(InitSql initsql) {
		this.initsql = initsql;
	}
	public PriKey getPrikey() {
		return prikey;
	}
	public void setPrikey(PriKey prikey) {
		this.prikey = prikey;
	}
	public ChoiceTime getChoicetime() {
		return choicetime;
	}
	public void setChoicetime(ChoiceTime choicetime) {
		this.choicetime = choicetime;
	}
	public PageStyle getPagestyle() {
		return pagestyle;
	}
	public void setPagestyle(PageStyle pagestyle) {
		this.pagestyle = pagestyle;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<QueryButton> getButtons() {
		return buttons;
	}
	public void setButtons(List<QueryButton> buttons) {
		this.buttons = buttons;
	}
	
	@Override
	public String getLastmodifiedtime() {
		return this.lastmodifiedtime;
	}
	
	@Override
	public void setLastmodifiedtime(String lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}
}
