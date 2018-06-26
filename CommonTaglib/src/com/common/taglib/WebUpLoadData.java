package com.common.taglib;

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

public class WebUpLoadData extends TagSupport implements TagShow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7744180258472880304L;
	private static Logger log = Logger.getLogger(WebUpLoadData.class);
	private String id = null;
	private String dataBind = null;
	private String pathId=null;
	private String multiple=null;
	private String delFunction=null;
	private String type=null;
	private Integer showtype=0;
	private CommonService comService;
	private LocalCommonService localComService;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	public String getPathId() {
		return pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	public String getDelFunction() {
		return delFunction;
	}

	public void setDelFunction(String delFunction) {
		this.delFunction = delFunction;
	}

	@Override
	public void setShowtype(Integer itagtype) {
		this.showtype=itagtype;
	}

	@Override
	public Integer getShowtype() {
		return this.showtype;
	}

	@Override
	public void setInfoname(String infoname) {
		
	}

	@Override
	public String getInfoname() {
		return null;
	}
	
	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		localComService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		JspWriter out;
		String strOut;
		out = pageContext.getOut();
		try
		{
			strOut=createUpHtml();
			out.println(strOut);
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return super.SKIP_BODY;
	}
	
	private String createUpHtml() {
		String strId;
		String strSql;
		String strIcon;
		String strName;
		String strPathId;
		String strFileId;
		String strOrgName;
		List<Map> listMap;
		Map mapData;
		Object[] params;
		String strDataBindValue;
		StringBuilder sb;
		HttpServletRequest request;
		listMap=null;
		strId=(id==null?"":id);
		strName=(dataBind==null?"":dataBind);
		request=(HttpServletRequest)this.pageContext.getRequest();
		strDataBindValue=localComService.dataBindInfo(request, strName);
		strPathId=(pathId==null?"":pathId);
		sb=new StringBuilder();
		sb.append("<div style=\"display:none;\">");
		if(null==type || "".equals(type))
		{
			type="{}";
		}
		if(null==strDataBindValue || "".equals(strDataBindValue))
		{
			try {
				strDataBindValue=comService.getKeybyTabCol("com", "sc_file", "id");
			} catch (Exception e) {
				log.error(e.toString(), e);
			}
		}
		if(null==multiple || "".equals(multiple))
		{
			multiple="false";
		}
		sb.append("<input name=\""+strName+"\" type=\"hidden\" value=\""+strDataBindValue+"\" />");
		sb.append("<input id=\"_"+strId+"_attribute\" type=\"hidden\" value=\"{%22groupId%22:%22"+strDataBindValue+"%22,%22accept%22:"+type+",%22pathId%22:%22"+strPathId+"%22,%22multiple%22:"+multiple+"}\" />");
		sb.append("</div>");
		if(showtype!=0)
		{
			if(showtype==2)
			{
				sb.append("<div class=\"fixed-table-container\">");
				sb.append("<table class=\"table table-hover\" style=\"width:100%;\">");
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"80%\">");
				sb.append("文件");
				sb.append("</th>");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"20%\">");
				sb.append("操作");
				sb.append("</th>");
				sb.append("</tr>");
				sb.append("</thead>");
				sb.append("</table>");
				sb.append("</div>");
			}
			if(showtype==1)
			{
				sb.append("<div class=\"fixed-table-container\">");
				sb.append("<table class=\"table table-hover\" style=\"width:100%;\">");
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"100%\">");
				sb.append("文件");
				sb.append("</th>");
				sb.append("</tr>");
				sb.append("</thead>");
				sb.append("</table>");
				sb.append("</div>");
			}
			strSql = "SELECT sc_file.id,sc_file.org_name,sc_filetype.icon,sc_file.read_filepath ";
			strSql += "FROM sc_file ";
			strSql += "LEFT JOIN sc_filetype ";
			strSql += "ON sc_file.file_type=sc_filetype.type ";
			strSql += "WHERE sc_file.groupID=? ";
			params=new Object[]{
				strDataBindValue
			};
			try {
				listMap=comService.queryListSql("com", strSql,params);
			} catch (Exception e) {
				log.error(e);
			}
			sb.append("<div class=\"fixed-table-container\">");
			sb.append("<table id=\""+id+"_table\" class=\"table table-hover\" bind=\""+strName+"\" style=\"width:100%;\">");
			if(null!=listMap)
			{
				for(int i=0;i<listMap.size();i++)
				{
					strIcon=null;
					mapData=listMap.get(i);
					strOrgName=mapData.get("org_name").toString();
					strFileId=mapData.get("id").toString();
					if(null!=mapData.get("icon"))
					{
						strIcon=mapData.get("icon").toString();
					}
					strIcon=((strIcon==null||"".equals(strIcon)==true)?"fa fa-file":strIcon);
					sb.append("<tr class=\"jqgrow ui-row-ltr\">");
					sb.append("<td class=\"ui-td-column ui-td-ltr\" width=\"80%\" bind=\""+strName+"\">");
					if(showtype==2)
					{
						sb.append("<a href=\"#\" onclick=\"fn_filedown('"+strFileId+"');\"><i class=\""+strIcon+"\">&nbsp;"+strOrgName+"</a>");
					}
					if(showtype==1)
					{
						sb.append("<i class=\""+strIcon+"\">&nbsp;"+strOrgName);
					}
					sb.append("</td>");
					if(showtype==2)
					{
						sb.append("<td class=\"data_\" width=\"20%\">");
						sb.append("<a href=\"javascript:void(0)\" keyid=\""+mapData.get("id").toString()+"\" onclick=\""+delFunction+"\">删除</a>");
						sb.append("</td>");
					}
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
			sb.append("</div>");
			sb.append("<div class=\"fixed-table-container\">");
			sb.append("<table id=\""+id+"_table_filelist\" class=\"table table-hover\" bind=\""+strName+"\" style=\"width:100%;\">");
			sb.append("<tbody>");
			sb.append("</tbody>");
			sb.append("</table>");
			sb.append("</div>");
			if(2==showtype){
				sb.append("<table id=\""+id+"_table_upload\" style=\"width:100%;\">");
				sb.append("<tr class=\"jqgrow ui-row-ltr\">");
				sb.append("<td class=\"data_\" width=\"100%\" bind=\""+strName+"\">");
				sb.append("<div id=\""+id+"_upload\"/>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");
				sb.append("<iframe id=\"_"+strId+"_iframe\" onload=\"fn_addWebUpdate('"+id+"');\" style=\"display:none;\"></iframe>");
			}
		}
		return sb.toString();
	}

	public void saveUpload(String controlId,String userId,HttpServletRequest request){
		String strUpdataValue;
		Object[] updateParams;
		String strControlValue;
		strControlValue=request.getParameter(controlId);
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		if("".equals(strControlValue)==false)
		{
			updateParams=new Object[]{
				userId
			};
			strUpdataValue="user_id";
			try {
				comService.updateObj("com", "sc_file",strUpdataValue,updateParams," and (groupId='"+strControlValue+"')");
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
		}
	}

}
