package com.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.TagShow;
import com.common.service.WorkflowService;

public class WorkFlowTaskChange extends TagSupport implements TagShow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(WorkFlowTaskChange.class);
	private String id = null;
	private String dataBind = null;
	private String inid;
	private Integer showtype=0;
	private String taskIsMutil;
	private WorkflowService workflowService;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInid() {
		return inid;
	}

	public void setInid(String inid) {
		this.inid = inid;
	}

	
	/**
	 * @return the taskIsMutil
	 */
	public String getTaskIsMutil() {
		return taskIsMutil;
	}

	/**
	 * @param taskIsMutil the taskIsMutil to set
	 */
	public void setTaskIsMutil(String taskIsMutil) {
		this.taskIsMutil = taskIsMutil;
	}

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		workflowService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		StringBuilder input;
		JspWriter out = pageContext.getOut();
		try {
			input=createTable();
			out.println(input.toString());
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private StringBuilder createTable() {
		StringBuilder sb;
		JSONObject jsonObj;
		JSONArray jsonList;
		sb=new StringBuilder();
		if(2==showtype)
		{
			if(false=="true".equals(taskIsMutil))
			{
				jsonList=workflowService.getUserTaskDefinition(this.inid);
				sb.append("<div class=\"fixed-table-container\">");
				sb.append("<table id=\""+id+"_table\" class=\"table table-hover\" bind=\""+this.dataBind+"\" style=\"width:100%;\">");
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
				sb.append("选择");
				sb.append("</th>");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\"150\" width=\"150px\">");
				sb.append("节点名称");
				sb.append("</th>");
				sb.append("</tr>");
				sb.append("</thead>");
				sb.append("<tbody>");
				for(int i=0;i<jsonList.size();i++)
				{
					jsonObj=jsonList.getJSONObject(i);
					if(false==jsonObj.getBoolean("isMulti") && false==jsonObj.getBoolean("isCurr"))
					{
						sb.append("<tr>");
						sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
						sb.append("<input name=\""+id+"_radio\" type=\"radio\" value=\""+jsonObj.getString("id")+"\" />");
						sb.append("</td>");
						sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"150\" width=\"150px;\">");
						sb.append(jsonObj.getString("name"));
						sb.append("</td>");
						sb.append("</tr>");
					}
				}
				sb.append("</tbody>");
				sb.append("</table>");
				sb.append("</div>");
				sb.append("<iframe style=\"display:none;\" onload=\"fn_initTableWidth('"+id+"_table');\"></iframe>");
			}
		}
		return sb;
	}
	
}
