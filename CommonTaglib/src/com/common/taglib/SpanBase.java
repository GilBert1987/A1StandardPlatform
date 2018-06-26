package com.common.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;

public class SpanBase extends TagSupport implements TagShow{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5801651341377502021L;
	private static Logger log = Logger.getLogger(SelectBase.class);
	private String id = null;
	private String dataname = null;
	private String datatype = null;
	private String datainit = null;
	private String style = null;
	private String infoname = null;
	private String dataBind = null;
	private Integer showtype;
	private CommonService comService;
	private LocalCommonService localComService;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	public String getDatainit() {
		return datainit;
	}

	public void setDatainit(String datainit) {
		this.datainit = datainit;
	}

	public String getInfoname() {
		return infoname;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	public Integer getShowtype() {
		return showtype;
	}

	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}
	
	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		localComService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", (HttpServletRequest)this.pageContext.getRequest());
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String span="";
		JspWriter out = pageContext.getOut();
		infoname=(infoname==null?"":infoname);
		try {
			span+=createSpan();
			out.println(span);
		} catch (IOException e) {
			log.error(e);
		}
		return super.SKIP_BODY;
	}

	private String createSpan() {
		StringBuilder value;
		List<Map> list;
		Map mapInfo;
		value=new StringBuilder();
		value.append("<span ");
		value.append("id=\""+this.id+"\"");
		value.append("style=\""+this.style+"\"");
		value.append(" >");
		if(null!=showtype && 0!=showtype)
		{
			list=null;
			try {
				list=comService.queryListSql(dataname, datainit,null);
			} catch (Exception e) {
				log.error(e);
			}
			if(0!=list.size())
			{
				mapInfo=list.get(0);
				if(null!=mapInfo.get("value"))
				{
					value.append(mapInfo.get("value").toString());
				}
			}
		}
		value.append("</span>");
		return value.toString();
	}
}
