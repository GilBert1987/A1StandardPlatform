package com.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.service.TagShow;

public class IconBase extends TagSupport implements TagShow {

	private static final long serialVersionUID = -6298052145030697386L;
	private static Logger log = Logger.getLogger(IconBase.class);
	private String id = null;
	private String type = null;
	private String style = null;
	private String value = null;
	private String dataBind = null;
	private String strgclass = null;
	private String strclass = null;
	private String placeholder = null;
	private String strbtnclass = null;
	private String strbtnclick = null;
	private String strbtntext = null;
	private Integer showtype;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getStrbtntext() {
		return strbtntext;
	}

	public void setStrbtntext(String strbtntext) {
		this.strbtntext = strbtntext;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	public String getStrclass() {
		return strclass;
	}

	public void setStrclass(String strclass) {
		this.strclass = strclass;
	}
	@Override
	public void setShowtype(Integer itagtype) {
		showtype=itagtype;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		try {
			input = createIcon();
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	
	private String createIcon() {
		String strInput;
		StringBuilder strBuild;
		int showInfo;
		strBuild=new StringBuilder();
		showInfo=showtype;
		strBuild.append("<div ");
		strBuild.append(" class=\""+((strgclass==null||"".equals(strgclass))?"input-group":strgclass)+"\" ");
		strBuild.append(" style=\"");
		if(null!=style)
		{
			strBuild.append(style);
		}
		strBuild.append("\"");
		showTypeInfo(strBuild,0);
		strBuild.append(">");
		strBuild.append("<input id=\""+id+"\" type=\"text\" ");
		if(null!=dataBind)
		{
			strBuild.append(" name=\""+dataBind+"\" ");
		}
		strBuild.append(" class=\""+((strclass==null||"".equals(strclass))?"form-control":strclass)+"\" ");
		strBuild.append(" value=\""+(value==null?"":value)+"\" ");
		strBuild.append(" placeholder=\""+(placeholder==null?"":placeholder)+"\" ");
		showtype=1;
		showTypeInfo(strBuild,0);
		showtype=showInfo;
		strBuild.append(" />");
		strBuild.append("<span class=\"input-group-btn\"");
		showTypeInfo(strBuild,1);
		strBuild.append(">");
		strBuild.append("<button id=\""+id+"_button\" txt=\""+id+"\" ");
		strBuild.append(" class=\""+((strbtnclass==null||"".equals(strbtnclass))?"btn btn-default":strbtnclass)+"\" ");
		strBuild.append(" type=\"button\" ");
		strBuild.append(" onclick=\""+(strbtnclick==null?"":strbtnclick)+"\" ");
		showTypeInfo(strBuild,1);
		strBuild.append(" >"+(strbtntext==null?"":strbtntext)+"</button>");
		strBuild.append("</span>");
		strBuild.append("</div>");
		strInput=strBuild.toString();
		return strInput;
	}

	public String getStrgclass() {
		return strgclass;
	}

	public void setStrgclass(String strgclass) {
		this.strgclass = strgclass;
	}

	public String getStrbtnclass() {
		return strbtnclass;
	}

	public void setStrbtnclass(String strbtnclass) {
		this.strbtnclass = strbtnclass;
	}

	public String getStrbtnclick() {
		return strbtnclick;
	}

	public void setStrbtnclick(String strbtnclick) {
		this.strbtnclick = strbtnclick;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
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
		if(type==1)
		{
			switch(ishowtype)
			{
				case 0:
					strValue.append(" style=\"display:none;\" ");
					break;
				case 1:
					strValue.append(" style=\"display:none;\" ");
					break;
			}
		}
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
