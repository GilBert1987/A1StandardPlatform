package com.platform.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;

public class PostSelect extends TagSupport implements TagShow  {
	
	
	private static final long serialVersionUID = -5801651341377502021L;
	private static Logger log = Logger.getLogger(PostSelect.class);
	private String id = null;
	private String name = null;
	private String required = null;
	private String infoname = null;
	private String multiple = null;
	private String strclass = null;
	private String clientCheckFun=null;
	private Integer showtype;
	private String style = null;
	private CommonService comService;
	private LocalCommonService localComService;
	private String initpostsys ="shiro";
	private String initpostsql ="select id,name from sc_post";

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getStrclass() {
		return strclass;
	}

	public void setStrclass(String strclass) {
		this.strclass = strclass;
	}

	public String getClientCheckFun() {
		return clientCheckFun;
	}

	public void setClientCheckFun(String clientCheckFun) {
		this.clientCheckFun = clientCheckFun;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	
	public String getInitpostsys() {
		return initpostsys;
	}

	public void setInitpostsys(String initpostsys) {
		this.initpostsys = initpostsys;
	}

	public String getInitpostsql() {
		return initpostsql;
	}

	public void setInitpostsql(String initpostsql) {
		this.initpostsql = initpostsql;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}

	@Override
	public void setShowtype(Integer value) {
		this.showtype=value;
	}
	
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		HttpServletRequest request;
		request=(HttpServletRequest)this.pageContext.getRequest();
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		return super.EVAL_BODY_INCLUDE;
	}
	
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		JspWriter out;
		String strPostName;
		String strPostId;
		StringBuilder strInput;
		List<Map> list;
		Boolean blInfo;
		out = pageContext.getOut();
		blInfo=false;
		list=null;
		strInput=new StringBuilder();
		
		if(0 != showtype){
			try
			{
				list=getPostInfo();
			}
			catch(Exception e)
			{
				new JspException(e.getMessage());
			}
			if(null!=list){
				if(1 == showtype)
				{
					strInput.append("<select id='"+id+"' readonly='readonly' ");
					strInput.append(" required=\""+("true".equals(required)?"true":"false")+"\" ");
					strInput.append(" showname=\""+infoname+"\" ");
					if(style != null)
					{
						strInput.append("style='"+this.style +"'");
					}
					if(strclass != null)
					{
						strInput.append("class='"+this.strclass +"'");
					}
					
					strInput.append(">");
					for (Map<String, String> reMap : list) {
						strPostName=reMap.get("name");
						strPostId = reMap.get("id");
						strInput.append("<option value='"+strPostId+"'>"+strPostName+"</option>");
							blInfo=true;
						
						
					}
					strInput.append("'</select>");
				}
				if(2 == showtype){
					strInput.append("<select id='"+id+"' readonly='readonly' multiple='"+multiple+"'");
					strInput.append(" required=\""+("true".equals(required)?"true":"false")+"\" ");
					strInput.append(" showname=\""+infoname+"\" ");
					if(style != null)
					{
						strInput.append("style='"+this.style +"'");
					}
					if(strclass != null)
					{
						strInput.append("class='"+this.strclass +"'");
					}
					strInput.append(">");
					for (Map<String, String> reMap : list) {
					
							strPostName=reMap.get("name");
							strPostId = reMap.get("id");
							strInput.append("<option value='"+strPostId+"'>"+strPostName+"</option>");
							blInfo=true;
							
							
					}
					strInput.append("'</select>");
					
					
				}
			}
			
			try {
				out.print(strInput);
			} catch (IOException e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return super.SKIP_BODY;
	}
	
	//杩斿洖id,name2鍒椾俊鎭�
		private List<Map> getPostInfo() throws ServletException{
			List<Map> list;
			String strValue;
			String[] strArrid;
			StringBuilder strSqlInfo;
			Boolean blInfo;
			list=null;
			strValue="";
			strSqlInfo=new StringBuilder();
			blInfo=false;
			initpostsys = initpostsys!=null?initpostsys:"shiro";
			initpostsql = initpostsql!=null?initpostsql:"select id,name form sc_post";
			if(null!=initpostsys && null!=initpostsql){
				try
				{
					list=(List<Map>)comService.queryListSql(initpostsys, initpostsql,null);
					
				}
				catch(Exception e)
				{
					new ServletException("initpostsys error or initpostsql error");
				}
			}
			return list;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public void setDataBind(String dataBind) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getDataBind() {
			// TODO Auto-generated method stub
			return null;
		}

}
