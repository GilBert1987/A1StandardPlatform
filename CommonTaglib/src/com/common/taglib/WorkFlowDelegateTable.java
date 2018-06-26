package com.common.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.TagShow;

public class WorkFlowDelegateTable extends TagSupport implements TagShow{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(WorkFlowDelegateTable.class);
	
	private String id;
	private String tempId;
	private String datamenu;
	private Integer showtype;
	private String dataBind = null;
	private CommonService comService;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void setInfoname(String infoname) {
		
	}

	@Override
	public void setDataBind(String dataBind) {
		this.dataBind=dataBind;
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
	public String getDataBind() {
		return this.dataBind;
	}

	@Override
	public String getInfoname() {
		return null;
	}
	
	/**
	 * @return the tempId
	 */
	public String getTempId() {
		return tempId;
	}

	/**
	 * @param tempId the tempId to set
	 */
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	
	public String getDatamenu() {
		return datamenu;
	}

	public void setDatamenu(String datamenu) {
		this.datamenu = datamenu;
	}

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		try {
			input+=createTable();
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private String createTable() {
		Map mapUser;
		String hasNo;
		String userId;
		Map mapRowInfo;
		Map mapUserInfo;
		String datatype;
		String strTitle;
		String strAlign;
		String strWidth;
		String dataname;
		StringBuilder sb;
		String strColInfo;
		String[] strIdData;
		String strTempData;
		JSONArray menuList;
		JSONObject menuObj;
		List<Map> listMapInfo;
		StringBuilder datasql;
		StringBuilder usersql;
		List<Map> listUserInfo;
		Map<String, Object> mapInfo;
		mapInfo=null;
		menuList=null;
		hasNo="1";
		dataname="wf";
		datatype="sql";
		strIdData=null;
		strTempData=null;
		listUserInfo=null;
		sb=new StringBuilder();
		try {
			strTempData=comService.loadRedisObj(tempId).toString();
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		if(null!=strTempData)
		{
			datasql=new StringBuilder();
			usersql=new StringBuilder();
			usersql.append("SELECT id,realname FROM sc_user ");
			strIdData=strTempData.split(",");
			datasql.append("SELECT act_ru_task.id_ AS id,act_re_url.code,act_re_url.wfname,act_ru_task.name_ AS taskname, ");
			datasql.append("act_re_url.name,act_ru_task.ASSIGNEE_ AS user_id,act_re_url.user_time,act_re_procdef.version_ AS verinfo ");
			datasql.append("FROM act_re_url ");
			datasql.append("JOIN act_hi_procinst ");
			datasql.append("ON act_re_url.inid=act_hi_procinst.proc_inst_id_ ");
			datasql.append("JOIN act_re_procdef ");
			datasql.append("ON act_re_url.deploymentId=act_re_procdef.deployment_id_ ");
			datasql.append("JOIN act_ru_task ");
			datasql.append("ON act_re_url.inid=act_ru_task.proc_inst_id_ ");
			datasql.append("WHERE act_ru_task.id_ IN ( ");
			for(int i=0;i<strIdData.length;i++)
			{
				datasql.append("'"+strIdData[i]+"'");
				if(i!=(strIdData.length-1)){
					datasql.append(",");
				}
			}
			datasql.append(") ");
			datasql.append("order by act_re_url.user_time desc");
			sb.append("<input id=\""+this.dataBind+"\" type=\"hidden\" name=\""+this.dataBind+"\" value=\""+strTempData+"\" />");
			sb.append("<div class=\"fixed-table-container\">");
			sb.append("<table id=\""+id+"_table_generictable\" class=\"table table-hover\" style=\"width:100%;table-layout:fixed;\">");
			sb.append("<thead>");
			sb.append("<tr>");
			if("1".equals(hasNo)==true)
			{
				sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
				sb.append("序号");
				sb.append("</th>");
			}
			if(null != datamenu && "".equals(datamenu)==false)
			{
				menuList=JSONArray.fromObject(datamenu);
				for(int i=0;i<menuList.size();i++)
				{
					menuObj=menuList.getJSONObject(i);
					strTitle=menuObj.getString("title");
					strWidth=menuObj.getString("width");
					sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\""+strWidth+"\" width=\""+strWidth+"px\">");
					sb.append(strTitle);
					sb.append("</th>");
				}
			}
			sb.append("</tr>");
			sb.append("</thead>");
			if(showtype!=0)
			{
				if(null!=menuList && null != datasql && "".equals(datasql)==false && null != dataname && "".equals(dataname)==false && true=="sql".equals(datatype))
				{
					try {
						mapInfo=comService.queryFullSql(dataname, datasql.toString(), null);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					if(null!=mapInfo)
					{
						mapUserInfo=new HashMap<String,String>();
						listMapInfo=(List)mapInfo.get("retMap");
						try {
							if(0!=listMapInfo.size())
							{
								usersql.append("where (1=1) and id in ( ");
							}
							for(int j=0;j<listMapInfo.size();j++)
							{
								userId="";
								mapRowInfo=listMapInfo.get(j);
								if(null!=mapRowInfo.get("user_id"))
								{
									userId=mapRowInfo.get("user_id").toString();
									usersql.append("'"+userId+"'");
									if(j!=(listMapInfo.size()-1)){
										usersql.append(",");
									}
								}
							}
							if(0!=listMapInfo.size())
							{
								usersql.append(")");
							}
							listUserInfo=comService.queryListSql("shiro", usersql.toString(), null);
							if(null!=listUserInfo && 0!=listUserInfo.size())
							{
								for(int i=0;i<listUserInfo.size();i++)
								{
									mapUser=listUserInfo.get(i);
									mapUserInfo.put(mapUser.get("id").toString(), mapUser.get("realname").toString());
								}
							}
						} catch (Exception e1) {
							log.error(e1.toString(),e1);
						}
						sb.append("<tbody>");
						for(int j=0;j<listMapInfo.size();j++)
						{
							sb.append("<tr>");
							mapRowInfo=listMapInfo.get(j);
							if(null != mapRowInfo.get("user_id") && null != mapUserInfo.get(mapRowInfo.get("user_id")))
							{
								mapRowInfo.put("username",mapUserInfo.get(mapRowInfo.get("user_id")));
							}
							for(int i=0;i<menuList.size();i++)
							{
								menuObj=menuList.getJSONObject(i);
								strAlign=menuObj.getString("align");
								strWidth=menuObj.getString("width");
								strColInfo=menuObj.getString("col");
								if(0==i && "1".equals(hasNo)==true)
								{
									sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
									sb.append((j+1));
									sb.append("</td>");
								}
								if(null!=strColInfo && "".equals(strColInfo)==false)
								{
									sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\""+strWidth+"\" width=\""+strWidth+"px\" align=\""+strAlign+"\">");
									sb.append("<span alt=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString())+"\" title=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString())+"\">");
									sb.append((null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()));
									sb.append("</span>");
									sb.append("</td>");
								}
							}
							sb.append("</tr>");
						}
						sb.append("</tbody>");
					}
				}
			}
			sb.append("</table>");
			sb.append("</div>");
			sb.append("<iframe style=\"display:none;\" onload=\"setTimeout(fn_initTableWidth('"+id+"_table_generictable'),200);\"></iframe>");
		}
		return sb.toString();
	}
}
