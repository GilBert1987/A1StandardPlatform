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

public class RoleSelect extends TagSupport implements TagShow  {
	
	
	private static final long serialVersionUID = -5801651341377502021L;
	private static Logger log = Logger.getLogger(RoleSelect.class);
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
	private String initrolesys ="shiro";
	private String initrolesql ="select id,name from sc_role";

	
	
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

	
	

	public String getInitrolesys() {
		return initrolesys;
	}

	public void setInitrolesys(String initrolesys) {
		this.initrolesys = initrolesys;
	}

	public String getInitrolesql() {
		return initrolesql;
	}

	public void setInitrolesql(String initrolesql) {
		this.initrolesql = initrolesql;
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
				list=getRoleInfo();
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
	
	//返回id,name2列信息
		private List<Map> getRoleInfo() throws ServletException{
			List<Map> list;
			String strValue;
			String[] strArrid;
			StringBuilder strSqlInfo;
			Boolean blInfo;
			list=null;
			strValue="";
			strSqlInfo=new StringBuilder();
			blInfo=false;
			initrolesys = initrolesys!=null && !initrolesys.equals("")?initrolesys:"shiro";
			initrolesql = initrolesql!=null && !initrolesql.equals("")?initrolesql:"select id,name from sc_role";
			if(null!=initrolesys && null!=initrolesql){
				try
				{
					list=(List<Map>)comService.queryListSql(initrolesys, initrolesql,null);
					
				}
				catch(Exception e)
				{
					new ServletException("initrolesys error or initrolesql error");
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
