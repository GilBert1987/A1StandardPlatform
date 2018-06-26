package com.common.taglib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.entity.activiti.WorkFlow;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.TagShow;
import com.common.service.WorkflowService;

public class WorkFlowUserBase extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 173763821929133995L;
	
	private static Logger log = Logger.getLogger(WorkFlowUserBase.class);
	
	private String id = null;
	private String type = null;
	private String value = null;
	private String init = null;
	private String taskId =null;
	private String clientclick = null;
	private Integer showtype;
	private CommonService comService;
	private WorkflowService wfService;
	
	@Override
	public void setInfoname(String infoname) {
	}

	@Override
	public void setDataBind(String dataBind) {
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
		return null;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getClientclick() {
		return clientclick;
	}

	public void setClientclick(String clientclick) {
		this.clientclick = clientclick;
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
		wfService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", request);
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
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@SuppressWarnings("unchecked")
	private String createTable() {
		Map mapUrl;
		Map mapForm;
		Map mapInfo;
		String strHid;
		String strSql;
		Boolean blCurr;
		List<Map> list;
		Object[] params;
		String strSubmit;
		WorkFlow workFlow;
		String strUserIds;
		List<Map> listUrl;
		String strUserList;
		String strUserNames;
		String strUserCheck;
		String strTaskDefKey;
		String strTaskUserlist;
		String strUserSelother;
		String strIsTempSubmit;
		StringBuilder strTable;
		ServletRequest request;
		JSONObject jsonUserObj;
		JSONArray jsonUserList;
		JSONObject jsonTaskUserObj;
		JSONArray jsonTaskUserList;
		Map<String,Object> variables;
		blCurr=true;
		mapForm=null;
		list=null;
		listUrl=null;
		strTable = new StringBuilder();
		variables=null;
		strSubmit="0";
		strTaskDefKey="";
		strIsTempSubmit="0";
		if(null!=init && "".equals(init)==false && 0 != showtype)
		{
			request=this.pageContext.getRequest();
			workFlow=(WorkFlow)request.getAttribute("_wf");
			try {
				variables = (Map<String, Object>)wfService.getRunVariablesByProcess(init);
			} catch (Exception e) {
				log.error(e);
			}
			if(null != variables)
			{
				params=new Object[1];
				params[0]=init;
				strSql="SELECT issubmit,istempsubmit FROM act_re_url WHERE inid=?";
				try {
					listUrl=comService.queryListSql("wf", strSql, params);
				} catch (Exception e2) {
					log.error(e2);
				}
				if(null!=listUrl && 1==listUrl.size())
				{
					mapUrl=listUrl.get(0);
					strSubmit=mapUrl.get("issubmit").toString();
					strIsTempSubmit=mapUrl.get("istempsubmit").toString();
					if("0".equals(strSubmit)==true)
					{
						blCurr=true;
					}
					if("1".equals(strSubmit)==true)
					{
						blCurr=false;
					}
				}
				params=new Object[1];
				params[0]=taskId;
				strSql="SELECT TASK_DEF_KEY_ AS taskKey FROM act_ru_task WHERE id_=?";
				try {
					list=comService.queryListSql("wf", strSql, params);
				} catch (Exception e1) {
					log.error(e1);
				}
				if(null!=list && 1==list.size())
				{
					mapInfo=list.get(0);
					strTaskDefKey=mapInfo.get("taskKey").toString();
				}
				strUserList = wfService.getNextUserTaskInfo(init, strTaskDefKey,blCurr);
				try {
					mapForm = wfService.getWFFormInfoData(workFlow.getCode(), workFlow.getVersion());
				} catch (UnsupportedEncodingException e) {
					log.error(e);
				}
				jsonUserList=JSONArray.fromObject(strUserList);
				strTable.append("<div class=\"col-xs-12 col-md-12 widget-body\">");
				if(2==this.showtype)
				{
					strTable.append("<table id=\""+id+"_table\" width=\"100%\">");
					strTable.append("<tbody>");
					for(int i=0;i<jsonUserList.size();i++)
					{
						strUserCheck="";
						strUserSelother="";
						jsonUserObj=jsonUserList.getJSONObject(i);
						if(null != mapForm)
						{
							strTaskUserlist=mapForm.get("taskuserlist").toString();
							jsonTaskUserList=JSONArray.fromObject(strTaskUserlist);
							for(int j=0;j<jsonTaskUserList.size();j++)
							{
								jsonTaskUserObj=jsonTaskUserList.getJSONObject(j);
								if(jsonUserObj.getString("taskId").equals(jsonTaskUserObj.getString("id"))==true)
								{
									if(null==jsonTaskUserObj.get("usercheck"))
									{
										log.error("DefKey:"+jsonUserObj.getString("taskId")+" usercheck is null");
									}
									strUserCheck=jsonTaskUserObj.getString("usercheck");
									if(null==jsonTaskUserObj.get("userselother"))
									{
										log.error("DefKey:"+jsonUserObj.getString("taskId")+" userselother is null");
									}
									strUserSelother=jsonTaskUserObj.getString("userselother");
									break;
								}
							}
						}
						strUserIds=getUserIds(variables,jsonUserObj.getString("taskId"),jsonUserObj.getInt("type"),jsonUserObj.getInt("userType"));
						strUserNames=getUserNames(strUserIds);
						strHid=getHidName(jsonUserObj.getString("taskId"),jsonUserObj.getInt("type"),jsonUserObj.getInt("userType"));
						strTable.append("<tr taskid='"+jsonUserObj.getString("taskId")+"' type='"+jsonUserObj.getInt("type")+"' userType='"+jsonUserObj.getInt("userType")+"' userHidId='"+strHid+"' ischeck='"+strUserCheck+"' taskname='"+jsonUserObj.getString("taskName")+"'>");
						strTable.append("<td>");
						strTable.append(jsonUserObj.getString("taskName"));
						strTable.append("</td>");
						strTable.append("<td>");
						if(1==jsonUserObj.getInt("type"))
						{
							strTable.append("单人");
						}
						if(2==jsonUserObj.getInt("type"))
						{
							strTable.append("多人");
						}
						strTable.append("</td>");
						strTable.append("<td>");
						if(0==jsonUserObj.getInt("userType"))
						{
							strTable.append("未定义");
						}
						if(1==jsonUserObj.getInt("userType"))
						{
							strTable.append("接收人");
						}
						if(2==jsonUserObj.getInt("userType"))
						{
							strTable.append("候选人");
						}
						if(3==jsonUserObj.getInt("userType"))
						{
							strTable.append("候选组");
						}
						if(4==jsonUserObj.getInt("userType"))
						{
							strTable.append("多人会签");
						}
						strTable.append("</td>");
						strTable.append("<td>");
						if("1".equals(strUserCheck)==true)
						{
							strTable.append("强制检查");
						}
						if("0".equals(strUserCheck)==true)
						{
							strTable.append("不检查");
						}
						strTable.append("</td>");
						strTable.append("<td>");
						strTable.append("<input id='"+strHid+"' type='hidden' value='"+strUserIds+"' />");
						strTable.append("<input id='"+strHid+"_text' type='text' readonly='readonly'");
						strTable.append(" value='"+strUserNames+"' >");
						strTable.append("</input>");
						if("1".equals(strUserSelother)==true || "1".equals(strIsTempSubmit)==true)
						{
							strTable.append("<img id=\""+strHid+"_img\" ");
							strTable.append(" src=\"../../img/10.png\" ");
							strTable.append(" url=\"../form/SF_201502250001.form\" ");
							strTable.append(" userid=\""+strHid+"\" ");
							strTable.append(" username=\""+strHid+"_text\" ");
							strTable.append(" style=\"width:20px;height:20px;cursor:pointer;\" ");
							strTable.append(" onclick=\"fn_platform_selUser(this);\">");
						}
						strTable.append("</td>");
						strTable.append("</tr>");
					}
					strTable.append("</tbody>");
					strTable.append("</table>");
				}
			}
		}
		return strTable.toString();
	}

	private String getHidName(String taskId,Integer type,Integer userType){
		String strRetName;
		strRetName="";
		if(1==type)
		{
			if(1==userType)
			{
				strRetName=taskId+"_assigneeUserId";
			}
			if(2==userType)
			{
				strRetName=taskId+"_candidateUsersId";
			}
		}
		if(2==type)
		{
			strRetName=taskId+"_assigneeUsersId";
		}
		return strRetName;
	}
	
	private String getUserNames(String strUserIds) {
		Map mapInfo;
		String strSql;
		List<Map> list;
		String strUserName;
		String[] strUserArr;
		strUserName="";
		list=null;
		strUserArr=strUserIds.split(",");
		strSql="SELECT realname FROM sc_user WHERE id IN (";
		for(int i=0;i<strUserArr.length;i++)
		{
			strSql+="'"+strUserArr[i]+"',";	
		}
		if(strUserArr.length>0)
		{
			strSql=strSql.substring(0, strSql.length()-1);
		}
		strSql+=")";
		try {
			list=comService.queryListSql("shiro", strSql, null);
		} catch (Exception e) {
			log.error(e);
		}
		for(int i=0;i<list.size();i++){
			mapInfo=list.get(i);
			strUserName+=(strUserName.equals("")?mapInfo.get("realname").toString():","+mapInfo.get("realname").toString());
		}
		return strUserName;
	}

	private String getUserIds(Map<String, Object> variables, String taskId, int type, int userType) {
		Object obj;
		String userIds;
		List<String> listUsers;
		userIds="";
		if(1==type)
		{
			if(1==userType)
			{
				obj=variables.get(taskId+"_assigneeUserId");
				if(null!=obj)
				{
					userIds=obj.toString();
				}
			}
			if(2==userType)
			{
				obj=variables.get(taskId+"_candidateUsersId");
				if(null!=obj)
				{
					userIds=obj.toString();
				}
			}
		}
		if(2==type)
		{
			obj=variables.get(taskId+"_assigneeUsersId");
			if(null!=obj)
			{
				listUsers=(List<String>)obj;
				for(int i=0;i<listUsers.size();i++){
					if(i==0){
						userIds=listUsers.get(i);
					}
					else
					{
						userIds+=","+listUsers.get(i);
					}
				}
			}
		}
		return userIds;
	}
}
