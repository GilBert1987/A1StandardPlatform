package com.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;

public class InputBase extends TagSupport implements TagShow{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6298052145030697386L;
	private static Logger log = Logger.getLogger(InputBase.class);
	private String id = null;
	private String type = null;
	private String backtype = null;
	private String dataBind = null;
	private String clientclick = null;
	private String style = null;
	private String value = null;
	private String strclass = null;
	private String placeholder=null;
	private Integer showtype;
	private String required = null;
	private String infoname = null;
	private String datamax=null;
	private String datamin=null;
	private String datastep=null;
	private Integer datamaxlength=null;
	private String dataregtype=null;
	private LocalCommonService localComService;

	public Integer getDatamaxlength() {
		return datamaxlength;
	}

	public void setDatamaxlength(Integer datamaxlength) {
		this.datamaxlength = datamaxlength;
	}

	public String getDataregtype() {
		return dataregtype;
	}

	public void setDataregtype(String dataregtype) {
		this.dataregtype = dataregtype;
	}
	
	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getInfoname() {
		return infoname;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}
	
	public String getStrclass() {
		return strclass;
	}

	public void setStrclass(String strclass) {
		this.strclass = strclass;
	}

	@Override
	public void setShowtype(Integer itagtype) {
		this.showtype=itagtype;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}
	
	public String getClientclick() {
		return clientclick;
	}

	public void setClientclick(String clientclick) {
		this.clientclick = clientclick;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value=value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	

	public String getDatamax() {
		return datamax;
	}

	public void setDatamax(String datamax) {
		this.datamax = datamax;
	}

	public String getDatamin() {
		return datamin;
	}

	public void setDatamin(String datamin) {
		this.datamin = datamin;
	}

	public String getDatastep() {
		return datastep;
	}

	public void setDatastep(String datastep) {
		this.datastep = datastep;
	}

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		localComService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		required=(required==null?"":required);
		infoname=(infoname==null?"":infoname);
		try {
			switch (type) {
				case "passwd":input=createPasswd();break;
				case "input":input=createInput();break;
				case "date":input=createDate();break;
				case "hidden":input=createHidden();break;
				case "number":input=createNumber();break;
				case "decimal":input=createDecimal();break;
			};
			out.println(input);
		} catch (IOException e) {
			log.error(e);
		}
		return super.SKIP_BODY;
	}
	
	private String createDate() {
		StringBuilder value;
		value=new StringBuilder();
		value.append("<div class=\"input-group\" id=\"div_"+id+"\">");
		value.append("<input onkeydown=\"if(event.keyCode==13){return false;}\" coltype='" + (this.backtype==null?"":this.backtype) + "' ");
		value.append("id='"+id+"' ");
		value.append(" class=\""+((strclass==null||"".equals(strclass)==true)?"form-control":strclass)+"\" ");
		value.append(" "+(placeholder==null?"":"placeholder=\""+placeholder+"\""));
		value.append(" showname=\""+infoname+"\" style=\"    background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true});\" ");
		if(dataBind!=null)
		{
			value.append(" name='"+dataBind+"' ");
			dataValidator(value);
		}
		else
		{
			value.append("name='"+id+"' ");
		}
		if(style != null)
		{
			value.append("style=\""+this.style);
			showTypeValue(value,0);
		}
		else{
			showTypeInfo(value,0);
		}
		dataBindInfo(value);
		value.append(" />");
		value.append("</div>");
		return value.toString();
	}

	private String createDecimal() {
		StringBuilder value;
		value=new StringBuilder();
		value.append("<div class=\"input-group spinner\" data-trigger=\"spinner\" id=\"div_"+id+"\">");
		value.append("<input coltype='" + (this.backtype==null?"":this.backtype) + "' ");
		value.append("id='"+id+"' ");
		value.append(" class=\""+((strclass==null||"".equals(strclass)==true)?"form-control":strclass)+"\" ");
		value.append(" placeholder=\""+(placeholder==null?"":placeholder)+"\" ");
		value.append(" "+("true".equals(required)?"required":"")+" ");
		value.append(" showname=\""+infoname+"\" ");
		if(null!=datamax && "".equals(datamax)==false)
		{
			value.append(" data-max=\""+datamax+"\" ");
		}
		if(null!=datamin && "".equals(datamin)==false)
		{
			value.append(" data-min=\""+datamin+"\" ");
		}
		if(null!=datastep && "".equals(datastep)==false)
		{
			value.append(" data-step=\""+datastep+"\" ");
		}
		if(dataBind!=null)
		{
			value.append(" name='"+dataBind+"' ");
			dataValidator(value);
		}
		else
		{
			value.append("name='"+id+"' ");
		}
		if(style != null)
		{
			value.append("style=\""+this.style);
			showTypeValue(value,0);
		}
		else{
			showTypeInfo(value,0);
		}
		dataBindInfo(value);
		value.append(" />");
		value.append("<div class=\"input-group-addon\">");
		value.append("<a href=\"javascript:;\" class=\"spin-up\" data-spin=\"up\"><i class=\"fa fa-caret-up\"></i></a>");
		value.append("<a href=\"javascript:;\" class=\"spin-down\" data-spin=\"down\"><i class=\"fa fa-caret-down\"></i></a>");
		value.append("</div>");
		value.append("</div>");
		return value.toString();
	}

	private String createNumber() {
		StringBuilder value;
		value=new StringBuilder();
		value.append("<div class=\"input-group spinner\" data-trigger=\"spinner\" id=\"div_"+id+"\">");
		value.append("<input coltype='" + (this.backtype==null?"":this.backtype) + "' ");
		value.append("id='"+id+"' ");
		value.append(" class=\""+((strclass==null||"".equals(strclass)==true)?"form-control":strclass)+"\" ");
		value.append(" placeholder=\""+(placeholder==null?"":placeholder)+"\" ");
		value.append(" "+("true".equals(required)?"required":"")+" ");
		value.append(" showname=\""+infoname+"\" ");
		if(null!=datamax && "".equals(datamax)==false)
		{
			value.append(" data-max=\""+datamax+"\" ");
		}
		if(null!=datamin && "".equals(datamin)==false)
		{
			value.append(" data-min=\""+datamin+"\" ");
		}
		if(null!=datastep && "".equals(datastep)==false)
		{
			value.append(" data-step=\""+datastep+"\" ");
		}
		if(dataBind!=null)
		{
			value.append(" name='"+dataBind+"' ");
			dataValidator(value);
		}
		else
		{
			value.append("name='"+id+"' ");
		}
		if(style != null)
		{
			value.append("style=\""+this.style);
			showTypeValue(value,0);
		}
		else{
			showTypeInfo(value,0);
		}
		dataBindInfo(value);
		value.append(" />");
		value.append("<div class=\"input-group-addon\">");
		value.append("<a href=\"javascript:;\" class=\"spin-up\" data-spin=\"up\"><i class=\"fa fa-caret-up\"></i></a>");
		value.append("<a href=\"javascript:;\" class=\"spin-down\" data-spin=\"down\"><i class=\"fa fa-caret-down\"></i></a>");
		value.append("</div>");
		value.append("</div>");
		return value.toString();
	}

	private void dataValidator(StringBuilder strvalue) {
		String strRegExp;
		JSONObject jsonObj;
		Boolean blHasType;
		blHasType=false;
		if("true".equals(required)==true)
		{
			strvalue.append(" data-validation-engine=\"validate[required]\" ");
			strvalue.append(" data-errormessage-value-missing=\"");
			strvalue.append("不能为空!");
			strvalue.append("\""); 
		}
		if(null!=datamaxlength && 0!=datamaxlength)
		{
			strvalue.append(" maxlength=\""+datamaxlength+"\" ");
			strvalue.append(" data-bv-stringlength-message=\"");
			strvalue.append("输入不能超过"+datamaxlength+"长度");
			strvalue.append("\""); 
		}
		if(null!=dataregtype && "".equals(dataregtype)==false)
		{
			jsonObj=JSONObject.fromObject(dataregtype);
			if("mail".equals(jsonObj.getString("type"))==true)
			{
				strvalue.append(" type=\"email\" ");
				blHasType=true;
			}
			if("url".equals(jsonObj.getString("type"))==true)
			{
				strvalue.append(" type=\"url\" ");
				blHasType=true;
			}
			if("regexp".equals(jsonObj.getString("type"))==true)
			{
				strRegExp=jsonObj.getString("regexp");
				if("".equals(strRegExp)==false)
				{
					strvalue.append(" pattern=\""+strRegExp+"\"");
					strvalue.append(" data-bv-regexp-message=\"不符合格式要求\"");
				}
			}
		}
		if(false==blHasType)
		{
			strvalue.append(" type=\"text\" ");
		}
	}
	
	private String createPasswd() {
		StringBuilder value;
		value=new StringBuilder();
		value.append("<div class=\"input-group\" id=\"div_"+id+"\">");
		value.append("<input onkeydown=\"if(event.keyCode==13){return false;}\" type='password' coltype='" + (this.backtype==null?"":this.backtype) + "'  ");
		value.append(" class=\""+((strclass==null||"".equals(strclass)==true)?"form-control":strclass)+"\" ");
		value.append(" placeholder=\""+(placeholder==null?"":placeholder)+"\" ");
		value.append(" showname=\""+infoname+"\" ");
		if(style != null)
		{
			value.append("style=\""+this.style);
			showTypeValue(value,0);
		}
		else{
			showTypeInfo(value,0);
		}
		value.append("id='"+id+"' ");
		if(dataBind!=null)
		{
			value.append("name='"+dataBind+"'");
		}
		else
		{
			value.append("name='"+id+"'");
		}
		dataBindInfo(value);
		value.append(" />");
		value.append("</div>");
		return value.toString();
	}

	private String createHidden(){
		StringBuilder value;
		value=new StringBuilder();
		value.append("<input type='hidden' coltype='" + (this.backtype==null?"":this.backtype) + "' ");
		value.append("id='"+id+"' ");
		if(dataBind!=null)
		{
			value.append("name='"+dataBind+"'");
		}
		else
		{
			value.append("name='"+id+"'");
		}
		dataBindInfo(value);
		showTypeInfo(value,0);
		value.append(" />");
		return value.toString();
	}
	
	private String createInput(){
		StringBuilder value;
		value=new StringBuilder();
		value.append("<div class=\"input-group\" id=\"div_"+id+"\">");
		value.append("<input onkeydown=\"if(event.keyCode==13){return false;}\" coltype='" + (this.backtype==null?"":this.backtype) + "' ");
		value.append("id='"+id+"' ");
		value.append(" class=\""+((strclass==null||"".equals(strclass)==true)?"form-control":strclass)+"\" ");
		value.append(" "+(placeholder==null?"":"placeholder=\""+placeholder+"\""));
		value.append(" showname=\""+infoname+"\" ");
		if(dataBind!=null)
		{
			value.append(" name='"+dataBind+"' ");
			dataValidator(value);
		}
		else
		{
			value.append("name='"+id+"' ");
		}
		if(style != null)
		{
			value.append("style=\""+this.style);
			showTypeValue(value,0);
		}
		else{
			showTypeInfo(value,0);
		}
		dataBindInfo(value);
		value.append(" />");
		value.append("</div>");
		return value.toString();
	}
	
	private void dataBindInfo(StringBuilder strValue){
		String strKey;
		HttpServletRequest request;
		strKey="";
		request=(HttpServletRequest) this.pageContext.getRequest();
		if(null!=this.dataBind && "".equals(this.dataBind)==false)
		{
			strKey=localComService.dataBindInfo(request, this.dataBind);
		}
		if("".equals(strKey)==false)
		{
			strValue.append(" value='" + strKey + "' ");
		}
		else
		{
			if("".equals(this.value)==false && this.value!=null)
			{
				strValue.append(" value='" + this.value  + "' ");
			}
		}
	}
	
	private void showTypeValue(StringBuilder strValue,int type){
		int ishowtype;
		ishowtype=0;
		try
		{
			ishowtype=Integer.parseInt(showtype.toString());
		}
		catch(Exception e)
		{
			log.error(e);
		}
		if(type==0)
		{
			switch(ishowtype)
			{
				case 0:
					strValue.append(";display:none;\" ");
					break;
				case 1:
					strValue.append("\" readonly=\"readonly\" ");
					break;
				default:strValue.append("\" ");
			}
		}
	}
	
	private void showTypeInfo(StringBuilder strValue,int type){
		int ishowtype;
		ishowtype=0;
		try
		{
			ishowtype=Integer.parseInt(showtype.toString());
		}
		catch(Exception e)
		{
			log.error(e);
		}
		if(type==0)
		{
			switch(ishowtype)
			{
				case 0:
					strValue.append(" style=\"display:none;\" ");
					break;
				case 1:
					strValue.append(" readonly=\"readonly\" ");
					break;
			}
		}
	}
	
	/* 释放资源 */
	public void release() {
		super.release();
	}

	public String getBacktype() {
		return backtype;
	}

	public void setBacktype(String backtype) {
		this.backtype = backtype.toUpperCase();
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}	
}
