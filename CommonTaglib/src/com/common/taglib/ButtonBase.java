package com.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.service.TagShow;

public class ButtonBase extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1275239354182794435L;
	
	private static Logger log = Logger.getLogger(ButtonBase.class);
	private String id = null;
	private String type = null;
	private String value = null;
	private String clientclick = null;
	private String strclass=null;
	private String strstyle=null;
	private String icon=null;
	private Integer showtype;
	
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

	public String getStrclass() {
		return strclass;
	}

	public void setStrclass(String strclass) {
		this.strclass = strclass;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStrstyle() {
		return strstyle;
	}

	public void setStrstyle(String strstyle) {
		this.strstyle = strstyle;
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

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		try {
			if("button".equals(type)==true)
			{
				input=createButton();
			}
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	
	private String createButton(){
		StringBuilder strvalue;
		strvalue=new StringBuilder();
		strvalue.append("<button type='button' ");
		strvalue.append("class='"+(strclass==null?"btn btn-default":strclass)+"' ");
		strvalue.append("id='"+id+"' ");
		if(this.clientclick!=null)
		{
			strvalue.append(" onclick=\"" + this.clientclick + "\"");
		}
		showTypeInfo(strvalue,0);
		strvalue.append("name='"+id+"'>");
		strvalue.append((icon==null?"":"<i class='"+icon+"' "+(strstyle==null?"":("style='"+strstyle+"'"))+"></i>"));
		strvalue.append(value);
		strvalue.append("</button>");
		return strvalue.toString();
	}
	private void showTypeInfo(StringBuilder strValue,int type){
		if(type==0)
		{
			switch(showtype)
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

	@Override
	public void setDataBind(String dataBind) {
	}

	@Override
	public String getDataBind() {
		return null;
	}

	@Override
	public void setInfoname(String infoname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInfoname() {
		// TODO Auto-generated method stub
		return null;
	}

}
