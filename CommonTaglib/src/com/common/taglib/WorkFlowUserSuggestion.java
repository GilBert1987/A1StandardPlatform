package com.common.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.TagShow;

public class WorkFlowUserSuggestion  extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(WorkFlowUserSuggestion.class);
	private String id = null;
	private String init = null;
	private String dataBind;
	private Integer showtype;
	private CommonService comService;

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
		return showtype;
	}

	@Override
	public String getDataBind() {
		return this.dataBind;
	}

	@Override
	public String getInfoname() {
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}
	
	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		HttpServletRequest request;
		request=(HttpServletRequest)this.pageContext.getRequest();
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		StringBuilder input;
		JspWriter out = pageContext.getOut();
		try {
			input=createSuggestionTable();
			out.println(input.toString());
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private StringBuilder createSuggestionTable() {
		Map mapInfo;
		Date subDate;
		String strDate;
		String strName;
		Object[] params;
		StringBuilder sb;
		List<Map> listMap;
		StringBuilder strSql;
		SimpleDateFormat sdf;
		listMap=null;
		sb=new StringBuilder();
		strSql=new StringBuilder();
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(showtype!=0)
		{
			strName=(dataBind==null?"":dataBind);
			if(showtype==2)
			{
				sb.append("<table style=\"width:100%;\">");
				sb.append("<tbody>");
				sb.append("<tr>");
				sb.append("<td width=\"20%\" align=\"right\">");
				sb.append("意见:");
				sb.append("</td>");
				sb.append("<td width=\"80%\">");
				sb.append("<textarea id=\""+id+"_remark\" name=\""+strName+"\" style=\"width:100%;height:100px;\"></textarea>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</tbody>");
				sb.append("</table>");
			}
			strSql.append("SELECT name,userName,suggest,submitDate ");
			strSql.append("FROM ( ");
			strSql.append("SELECT act_hi_actinst.ACT_Name_ AS NAME,act_hi_suggestion.userName,act_hi_suggestion.suggest,act_hi_suggestion.createDate AS submitDate ");
			strSql.append("FROM act_hi_suggestion ");
			strSql.append("JOIN act_hi_actinst ");
			strSql.append("ON act_hi_suggestion.inid=act_hi_actinst.proc_inst_id_ ");
			strSql.append("WHERE act_hi_suggestion.inid =? AND act_hi_suggestion.task_id IS NULL AND act_hi_actinst.task_id_ IS NULL and act_hi_actinst.act_type_='startEvent'");
			strSql.append("UNION ");
			strSql.append("SELECT act_hi_actinst.ACT_Name_ AS NAME,act_hi_suggestion.userName,act_hi_suggestion.suggest,act_hi_suggestion.createDate AS submitDate ");
			strSql.append("FROM act_hi_suggestion ");
			strSql.append("JOIN act_hi_actinst ");
			strSql.append("ON act_hi_actinst.task_id_=act_hi_suggestion.task_id AND act_hi_suggestion.inid=act_hi_actinst.proc_inst_id_ ");
			strSql.append("WHERE act_hi_suggestion.inid =? ");
			strSql.append(") AS t ");
			strSql.append("ORDER BY submitDate ASC,name,userName ");
			params=new Object[]{
				init,
				init
			};
			try {
				listMap=comService.queryListSql("wf", strSql.toString(),params);
			} catch (Exception e) {
				log.error(e);
			}
			sb.append("<div class=\"fixed-table-container\">");
			sb.append("<table id=\""+id+"_table\" class=\"table table-hover\" bind=\""+strName+"\" style=\"width:100%;\">");
			sb.append("<thead>");
			sb.append("<tr>");
			sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"15%\">");
			sb.append("节点名称");
			sb.append("</th>");
			sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"15%\">");
			sb.append("用户名");
			sb.append("</th>");
			sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"50%\">");
			sb.append("意见内容");
			sb.append("</th>");
			sb.append("<th class=\"ui-th-column ui-th-ltr\" width=\"20%\">");
			sb.append("时间");
			sb.append("</th>");
			sb.append("</tr>");
			sb.append("</thead>");
			if(null!=listMap)
			{
				sb.append("<tbody>");
				for(int i=0;i<listMap.size();i++)
				{
					strDate="";
					mapInfo=listMap.get(i);
					subDate=(Date)(null==mapInfo.get("submitDate")?null:mapInfo.get("submitDate"));
					if(null!=subDate)
					{
						strDate=sdf.format(subDate);
					}
					sb.append("<tr class=\"jqgrow ui-row-ltr\">");
					sb.append("<td class=\"ui-td-column ui-td-ltr\">");
					sb.append(null==mapInfo.get("name")?"":mapInfo.get("name").toString());
					sb.append("</td>");
					sb.append("<td class=\"ui-td-column ui-td-ltr\">");
					sb.append(null==mapInfo.get("userName")?"":mapInfo.get("userName").toString());
					sb.append("</td>");
					sb.append("<td class=\"ui-td-column ui-td-ltr\">");
					sb.append(null==mapInfo.get("suggest")?"":mapInfo.get("suggest").toString());
					sb.append("</td>");
					sb.append("<td class=\"ui-td-column ui-td-ltr\">");
					sb.append(strDate);
					sb.append("</td>");
					sb.append("</tr>");
				}
				sb.append("</tbody>");
			}
			sb.append("</table>");
			sb.append("</div>");
			sb.append("</div>");
		}
		return sb;
	}

}
