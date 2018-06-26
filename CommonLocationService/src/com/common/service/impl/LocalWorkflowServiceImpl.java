package com.common.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.common.entity.activiti.ActivitForm;
import com.common.entity.activiti.FormKey;
import com.common.entity.activiti.WorkFlow;
import com.common.entity.firebutton.Button;
import com.common.entity.firebutton.RelObjInfo;
import com.common.entity.login.User;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.LocalWorkflowService;
import com.common.service.WorkflowService;
import com.common.tool.XmlUnit;
import com.common.unit.HtmlUnit;

public class LocalWorkflowServiceImpl implements LocalWorkflowService{

	private static Logger log = Logger.getLogger(LocalWorkflowServiceImpl.class);
	
	private WorkflowService wfService;
	
	private CommonService commonService;
	
	private LocalCommonService localService;
	
	public WorkflowService getWfService() {
		return wfService;
	}

	public void setWfService(WorkflowService wfService) {
		this.wfService = wfService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public LocalCommonService getLocalService() {
		return localService;
	}

	public void setLocalService(LocalCommonService localService) {
		this.localService = localService;
	}
	
	@Override
	public void completeTaskWithCommentCmd(String strTkid,HttpServletRequest request,HttpServletResponse response){
		String strDeployMentId;
		Map<String,Object> variables;
		try
		{
			strDeployMentId=wfService.getDeploymentIdByTaskId(strTkid);
			variables=getWorkFlowVariableMap(strDeployMentId,request,response);
			wfService.completeTaskWithCommentCmd(strTkid, variables);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void redirectWF(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String urlInfo;
		String strListTask;
		String inid;
		JSONArray jsonArray;
		JSONObject jsonObject;
		inid="";
		urlInfo=request.getAttribute("_urlInfo").toString();
		if(request.getAttribute("_wfcurrtaskid")!=null)
		{
			strListTask=request.getAttribute("_wfcurrtaskid").toString();
		}
		else
		{
			strListTask="";
		}
		if("".equals(strListTask)==false)
		{
			jsonArray=JSONArray.fromObject(strListTask);
			if(jsonArray.size()>0)
			{
				jsonObject=(JSONObject)jsonArray.get(0);
				inid=jsonObject.getString("inid");
			}
		}
		if(urlInfo.indexOf("inid")==-1)
		{
			if(urlInfo.indexOf("?")>0)
			{
				urlInfo=urlInfo+"&inid="+inid;
			}
			else
			{
				urlInfo=urlInfo+"?inid="+inid;
			}
		}
		response.sendRedirect(urlInfo);
	}
	
	@Override
	public void startProcess(String processkey,String businessKey,HttpServletRequest request,HttpServletResponse response){
		String strDeploymentId;
		String strListTask;
		Map<String,Object> variables;
		Subject subJect;
		User user;
		subJect=SecurityUtils.getSubject();
		user=(User)subJect.getPrincipal();
		strListTask="";
		strDeploymentId="";
		try{
			strDeploymentId=wfService.getDeploymentIdByProcesskey(processkey);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		variables=getWorkFlowVariableMap(strDeploymentId,request,response);
		try{
			strListTask=wfService.startProcess(strDeploymentId,user.getId(), businessKey, variables);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		request.setAttribute("_wfcurrtaskid", strListTask);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getWorkFlowVariableMap(String deployId, HttpServletRequest request, HttpServletResponse response){
		String strKey;
		String strVer;
		String strSql;
		Object[] param;
		String strdefkey;
		String strTaskId;
		List<Map> listMap;
		String strUserInfo;
		String strUserList;
		String strUserType;
		JSONArray jsonUserList;
		JSONArray jsonUserItem;
		JSONObject jsonUserObj;
		Map<String, Object> map;
		Map<String, String> mapWFForm;
		param=new Object[1];
		strKey=null;
		strVer=null;
		listMap=null;
		map=localService.getVariableMap(request);
		strdefkey=map.get("defkey").toString();
		param[0]=strdefkey;
		strSql="SELECT Key_ AS keyinfo,Version_ AS verinfo FROM act_re_procdef WHERE id_=?";
		try {
			listMap=commonService.queryListSql("wf", strSql, param);
		} catch (Exception e1) {
			log.error(e1.getMessage(),e1);
		}
		if(null!=listMap && 1==listMap.size())
		{
			strKey=listMap.get(0).get("keyinfo").toString();
			strVer=listMap.get(0).get("verinfo").toString();
		}
		if(null!=strKey && null !=strVer){
			try {
				mapWFForm = (Map<String, String>)wfService.getWFFormInfoData(strKey, Integer.parseInt(strVer));
				strUserList = mapWFForm.get("taskuserlist").toString();
				jsonUserList=JSONArray.fromObject(strUserList);
				for(int i=0;i<jsonUserList.size();i++)
				{
					strTaskId="";
					strUserType="";
					strUserInfo="";
					jsonUserObj=jsonUserList.getJSONObject(i);
					strTaskId=jsonUserObj.getString("id");
					strUserType=jsonUserObj.getString("usertype");
					jsonUserItem=jsonUserObj.getJSONArray("userlist");
					strUserInfo=getUsersByGroup(jsonUserItem,request,response);
					if("0".equals(strUserType))
					{
						if(null!=map.get(strTaskId+"_assigneeUserId"))
						{
							strUserInfo=map.get(strTaskId+"_assigneeUserId").toString();
						}
						map.put(strTaskId+"_assigneeUserId", strUserInfo);
						map.put(strTaskId+"_assigneeUsersId", Arrays.asList(strUserInfo.split(",")));
					}
					if("1".equals(strUserType))
					{
						if(null!=map.get(strTaskId+"_candidateUsersId"))
						{
							strUserInfo=map.get(strTaskId+"_candidateUsersId").toString();
						}
						map.put(strTaskId+"_candidateUsersId", strUserInfo);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			
		}
		return map;
	}
	
	private String getUsersByGroup(JSONArray jsonUserItems,HttpServletRequest request, HttpServletResponse response){
		String strUsersId;
		JSONObject jsonUserItem;
		String strAssignmentType;
		String strAssignmentUserId;
		String strAssigneeUserId;
		strAssigneeUserId="";
		strUsersId="";
		if(null!=jsonUserItems){
			for(int i=0;i<jsonUserItems.size();i++)
			{
				strUsersId="";
				jsonUserItem=(JSONObject)jsonUserItems.get(i);
				strAssignmentType=jsonUserItem.getString("assignment_type");
				strAssignmentUserId=jsonUserItem.getString("resourceassignmentexpr");
				if("singleUser".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false && "undefined".equals(strAssignmentUserId)==false)
				{
					strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strAssignmentUserId:","+strAssignmentUserId);
				}
				if("postGroups".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false)
				{
					try{
						strUsersId=wfService.getPostUser(strAssignmentUserId);
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if("".equals(strUsersId)==false)
					{
						strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strUsersId:","+strUsersId);
					}
				}
				if("deptGroups".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false)
				{
					try{
						strUsersId=wfService.getDeptUser(strAssignmentUserId);
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if("".equals(strUsersId)==false)
					{
						strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strUsersId:","+strUsersId);
					}
				}
				if("roleGroups".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false)
				{
					try{
						strUsersId=wfService.getRoleUser(strAssignmentUserId);
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if("".equals(strUsersId)==false)
					{
						strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strUsersId:","+strUsersId);
					}
				}
				if("deptPostGroups".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false)
				{
					try{
						strUsersId=wfService.getDeptPostUser(strAssignmentUserId);
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if("".equals(strUsersId)==false)
					{
						strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strUsersId:","+strUsersId);
					}
				}
				if("custSql".equals(strAssignmentType)==true && "".equals(strAssignmentUserId)==false && "undefined".equals(strAssignmentUserId)==false)
				{
					strAssignmentUserId=HtmlUnit.getValueByRequest(strAssignmentUserId,request,response);
					try{
						strUsersId=wfService.getUsersIdBySql(strAssignmentUserId);
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if("".equals(strUsersId)==false)
					{
						strAssigneeUserId=strAssigneeUserId+(strAssigneeUserId.equals("")==true?strUsersId:","+strUsersId);
					}
				}
			}
		}
		return strAssigneeUserId;
	}

	@Override
	public WorkFlow getWFByActivitiConfig(String strIdInfo) {
		WorkFlow wf;
		Map mapInfo;
		String strId;
		String strKey;
		String strVer;
		String strTemp;
		String strType;
		List<Map> list;
		Button btnInfo;
		String strName;
		Object[] params;
		Map mapFormInfo;
		String strHidId;
		String strKeyId;
		String strIdKey;
		Iterator iterKey;
		String strClient;
		String strJsinfo;
		String strTagObj;
		String strFireKey;
		String strBtnlist;
		String strHidlist;
		String strConlist;
		String strTaginfo;
		Date dtUpdateTime;
		String strAuthinfo;
		String strShowtype;
		String strFormlist;
		String strRoleList;
		String strKeyValue;
		String strFireValue;
		JSONObject jsonShow;
		FormKey formKeyInfo;
		String strStartCode;
		String strHidTagInfo;
		SimpleDateFormat sdf;
		String strUpdatetime;
		StringBuilder strSql;
		List<Button> btnList;
		JSONObject jsonParam;
		ActivitForm formInfo;
		RelObjInfo relObjInfo;
		String strDynamicform;
		List<String> listHids;
		List<String> listTags;
		String strTaskformlist;
		String strTaskuserlist;
		JSONObject jsonFormKey;
		JSONObject jsonRoleKey;
		JSONArray jsonRoleList;
		JSONArray jsonListShow;
		JSONObject jsonTagInfo;
		JSONObject jsonHidInfo;
		JSONObject jsonBtnInfo;
		List<ActivitForm> forms;
		List<String> listClient;
		JSONObject jsonRoleInfo;
		JSONObject jsonFireInfo;
		JSONArray jsonListParams;
		JSONObject jsonAuthority;
		JSONObject jsonControlObj;
		JSONObject jsonClientInfo;
		JSONArray jsonListFormKey;
		JSONArray jsonListRoleKey;
		JSONArray jsonListTagInfo;
		JSONArray jsonListHidInfo;
		JSONArray jsonListBtnInfo;
		JSONObject jsonListFormObj;
		List<FormKey> listFormKeys;
		JSONArray jsonListFormList;
		JSONArray jsonListFireList;
		JSONArray jsonListControls;
		JSONObject jsonControlChild;
		JSONArray jsonListClientList;
		JSONArray jsonListAuthoritys;
		List<RelObjInfo> listRelObjs;
		Map<String,String> mapFireKey;
		Map<String, String> mapControl;
		JSONArray jsonListControlChilds;
		List<Map<String,String>> mapList;
		Map<String,String> authorityControl;
		List<Map<String, String>> listControls;
		List<Map<String,String>> authorityControls;
		Map<String, List<Map<String,String>>> authority;
		Map<String, Map<String, List<Map<String,String>>>> authoritys;
		wf=null;
		mapFormInfo=null;
		dtUpdateTime=null;
		params=new Object[1];
		strSql = new StringBuilder();
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("".equals(strIdInfo)==false)
		{
			list=null;
			params[0]=strIdInfo;
			strSql.append("SELECT id_ as idinfo,NAME_ as nameinfo,KEY_ AS keyinfo,VERSION_ AS verinfo ");
			strSql.append("FROM act_re_procdef ");
			strSql.append("WHERE DEPLOYMENT_ID_=? ");
			try {
				list=commonService.queryListSql("wf", strSql.toString(), params);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			if(null!=list && 1==list.size()){
				mapInfo=list.get(0);
				strName=mapInfo.get("nameinfo").toString();
				strKey=mapInfo.get("keyinfo").toString();
				strVer=mapInfo.get("verinfo").toString();
				strIdKey=mapInfo.get("idinfo").toString();
				try {
					mapFormInfo=wfService.getWFFormInfoData(strKey, Integer.parseInt(strVer));
				} catch (Exception e) {
					 log.error(e.getMessage(),e);
				}
				if(null!=mapFormInfo)
				{
					wf=new WorkFlow();
					forms=new ArrayList<ActivitForm>();
					listFormKeys=new ArrayList<FormKey>();
					wf.setFormkeys(listFormKeys);
					strJsinfo=mapFormInfo.get("jsinfo").toString();
					strTemp=mapFormInfo.get("temp").toString();
					strBtnlist=mapFormInfo.get("btnlist").toString();
					strHidlist=mapFormInfo.get("hidlist").toString();
					strConlist=mapFormInfo.get("conlist").toString();
					strFormlist=mapFormInfo.get("formlist").toString();
					strTaginfo=mapFormInfo.get("taginfo").toString();
					strRoleList=mapFormInfo.get("roleList").toString();
					strDynamicform=mapFormInfo.get("dynamicform").toString();
					strTaskformlist=mapFormInfo.get("taskformlist").toString();
					strTaskuserlist=mapFormInfo.get("taskuserlist").toString();
					strUpdatetime=mapFormInfo.get("updatetime").toString();
					strStartCode=mapFormInfo.get("startcode").toString();
					jsonListFormList=JSONArray.fromObject(strFormlist);
					jsonListFormKey=JSONArray.fromObject(strTaskformlist);
					jsonListRoleKey=JSONArray.fromObject(strRoleList);
					jsonListTagInfo=JSONArray.fromObject(strTaginfo);
					jsonListHidInfo=JSONArray.fromObject(strHidlist);
					jsonListBtnInfo=JSONArray.fromObject(strBtnlist);
					jsonListControls=JSONArray.fromObject(strConlist);
					wf.setCode(strKey);
					wf.setForms(forms);
					wf.setName(strName);
					wf.setFiledir(strTemp);
					wf.setDefid(strIdKey);
					wf.setVersion(Integer.parseInt(strVer));
					wf.setInitformkey(strStartCode);
					strJsinfo=XmlUnit.getDeXmlString(strJsinfo);
					strDynamicform=XmlUnit.getDeXmlString(strDynamicform);
					wf.setJstag(strJsinfo);
					try {
						dtUpdateTime=sdf.parse(strUpdatetime);
					} catch (ParseException e) {
						log.error(e.getMessage(),e);
					}
					if(null!=dtUpdateTime)
					{
						wf.setLastmodifiedtime(String.valueOf(dtUpdateTime.getTime()));
					}
					if(null!=jsonListFormKey)
					{
						for(int i=0;i<jsonListFormKey.size();i++)
						{
							formKeyInfo=new FormKey();
							jsonFormKey=jsonListFormKey.getJSONObject(i);
							formKeyInfo.setId(jsonFormKey.getString("id"));
							formKeyInfo.setStartcode(jsonFormKey.getString("startcode"));
							formKeyInfo.setEndcode(jsonFormKey.getString("endcode"));
							listFormKeys.add(formKeyInfo);
						}
					}
					if(null!=jsonListFormList)
					{
						for(int i=0;i<jsonListFormList.size();i++)
						{
							formInfo=new ActivitForm();
							jsonListFormObj=jsonListFormList.getJSONObject(i);
							formInfo.setCode(jsonListFormObj.getString("code"));
							formInfo.setUrl(XmlUnit.getDeXmlString(jsonListFormObj.getString("url")));
							forms.add(formInfo);
						}
					}
					if(null != wf.getFormkeys() && null!=jsonListRoleKey)
					{
						for(FormKey formKey:wf.getFormkeys())
						{
							if(null!=formKey.getId())
							{
								jsonRoleList=null;
								for(int i=0;i<jsonListRoleKey.size();i++)
								{
									jsonRoleKey=jsonListRoleKey.getJSONObject(i);
									if(true==formKey.getId().equals(jsonRoleKey.getString("id"))){
										jsonRoleList=jsonRoleKey.getJSONArray("roleList");
										break;
									}
								}
								if(null!=jsonRoleList)
								{
									authoritys=new HashMap<String, Map<String, List<Map<String,String>>>>();
									formKey.setAuthoritys(authoritys);
									for(int i=0;i<jsonRoleList.size();i++)
									{
										authority=new HashMap<String, List<Map<String,String>>>();
										jsonRoleInfo=jsonRoleList.getJSONObject(i);
										strType=jsonRoleInfo.getString("type");
										jsonListAuthoritys=jsonRoleInfo.getJSONArray("authority");
										authoritys.put(strType, authority);
										for(int j=0;j<jsonListAuthoritys.size();j++)
										{
											authorityControls=new ArrayList<Map<String,String>>();
											jsonAuthority=jsonListAuthoritys.getJSONObject(j);
											strId=jsonAuthority.getString("id");
											jsonListShow=jsonAuthority.getJSONArray("show");
											authority.put(strId, authorityControls);
											for(int u=0;u<jsonListShow.size();u++)
											{
												authorityControl=new HashMap<String,String>();
												jsonShow=jsonListShow.getJSONObject(u);
												strAuthinfo=XmlUnit.getDeXmlString(jsonShow.getString("authinfo"));
												strShowtype=jsonShow.getString("showtype");
												authorityControl.put("authinfo", strAuthinfo);
												authorityControl.put("showtype", strShowtype);
												authorityControls.add(authorityControl);
											}
										}
									}
								}
							}
						}
					}
					if(null!=jsonListTagInfo)
					{
						listTags=new ArrayList<String>();
						wf.setTags(listTags);
						for(int i=0;i<jsonListTagInfo.size();i++)
						{
							jsonTagInfo=jsonListTagInfo.getJSONObject(i);
							strTagObj=jsonTagInfo.getString("tag");
							strTagObj=XmlUnit.getDeXmlString(strTagObj);
							listTags.add(strTagObj);
						}
					}
					if(null!=jsonListBtnInfo)
					{
						btnList=new ArrayList<Button>();
						wf.setButtons(btnList);
						for(int i=0;i<jsonListBtnInfo.size();i++)
						{
							btnInfo=new Button();
							btnList.add(btnInfo);
							listClient=new ArrayList<String>();
							listRelObjs=new ArrayList<RelObjInfo>();
							jsonBtnInfo=jsonListBtnInfo.getJSONObject(i);
							btnInfo.setId(jsonBtnInfo.getString("id"));
							btnInfo.setTag(jsonBtnInfo.getString("tag"));
							btnInfo.setJsFuction(listClient);
							btnInfo.setFireFuction(listRelObjs);
							jsonListClientList=jsonBtnInfo.getJSONArray("clientfunction");
							jsonListFireList=jsonBtnInfo.getJSONArray("firefunction");
							for(int j=0;j<jsonListClientList.size();j++)
							{
								jsonClientInfo=jsonListClientList.getJSONObject(j);
								strClient=jsonClientInfo.getString("function");
								strClient=XmlUnit.getDeXmlString(strClient);
								listClient.add(strClient);
							}
							for(int j=0;j<jsonListFireList.size();j++)
							{
								relObjInfo=new RelObjInfo();
								listRelObjs.add(relObjInfo);
								jsonFireInfo=jsonListFireList.getJSONObject(j);
								relObjInfo.setClassName(jsonFireInfo.getString("fireclass"));
								relObjInfo.setMethod(jsonFireInfo.getString("method"));
								jsonListParams=jsonFireInfo.getJSONArray("parm");
								mapList=new ArrayList<Map<String,String>>();
								relObjInfo.setList(mapList);
								for(int u=0;u<jsonListParams.size();u++)
								{
									mapFireKey=new HashMap<String,String>();
									jsonParam=jsonListParams.getJSONObject(u);
									strFireKey=jsonParam.getString("key");
									strFireValue=jsonParam.getString("value");
									strFireValue=XmlUnit.getDeXmlString(strFireValue);
									mapFireKey.put(strFireKey, strFireValue);
									mapList.add(mapFireKey);
								}
							}
						}
					}
					if(null!=jsonListControls)
					{			
						listControls=new ArrayList<Map<String, String>>();
						wf.setControls(listControls);
						for(int i=0;i<jsonListControls.size();i++)
						{
							jsonControlObj=jsonListControls.getJSONObject(i);
							jsonListControlChilds=jsonControlObj.getJSONArray("control");
							for(int u=0;u<jsonListControlChilds.size();u++)
							{
								mapControl=new HashMap<String,String>();
								jsonControlChild=jsonListControlChilds.getJSONObject(u);
								iterKey=jsonControlChild.keys();
								while(iterKey.hasNext())
								{
									strKeyId=iterKey.next().toString();
									strKeyValue=jsonControlChild.getString(strKeyId);
									strKeyValue=XmlUnit.getDeXmlString(strKeyValue);
									mapControl.put(strKeyId, strKeyValue);
								}
								listControls.add(mapControl);
							}
						}
					}
					if(null!=jsonListHidInfo)
					{
						listHids=new ArrayList<String>();
						wf.setHiddenform(listHids);
						for(int i=0;i<jsonListHidInfo.size();i++)
						{
							jsonHidInfo=jsonListHidInfo.getJSONObject(i);
							strHidId=jsonHidInfo.getString("id");
							strHidTagInfo="<fm:input id=\""+strHidId+"\" />";
							for(Map<String,String> econtrol:wf.getControls())
							{
								strHidTagInfo = XmlUnit.changeHTMLTag(strHidTagInfo, econtrol);
							}
							listHids.add(strHidTagInfo);
						}
					}
					strDynamicform=getWFDynamicForm(strDynamicform,wf.getControls());
					for(Map<String,String> econtrol:wf.getControls())
					{
						strDynamicform = XmlUnit.changeHTMLTag(strDynamicform, econtrol);
					}
					wf.setDynamicform(strDynamicform);
				}
			}
		}
		return wf;
	}
	
	private String getWFDynamicForm(String strDynamicform,List<Map<String, String>> controls){
		String strConId;
		String strImgId;
		DOMParser parser;
		NodeList nodelist;
		StringReader read;
		String strImgInfo;
		String strTagInfo;
		InputSource source;
		String strImgTitle;
		String strIsControl;
		String strNodeValue;
		org.w3c.dom.Node node;
		JSONObject jsonObject;
		org.w3c.dom.Node nodeValue;
		org.w3c.dom.Node nodeControl;
		org.w3c.dom.Document document;
		parser = new DOMParser();
		read=new StringReader(strDynamicform);
		source=new InputSource(read);
		try{
			parser.parse(source);
			document=parser.getDocument();
			for(Map<String, String> mapControl:controls){
				strConId="";
				if(null != mapControl.get("id"))
				{
					strConId=mapControl.get("id");
				}
				nodelist=document.getElementsByTagName("IMG");
				for(int i=0;i<nodelist.getLength();i++)
				{
					strIsControl="";
					strNodeValue="";
					node=nodelist.item(i);
					jsonObject=null;	
					nodeControl=node.getAttributes().getNamedItem("control");
					if(null!=nodeControl){
						strIsControl=nodeControl.getNodeValue();
					}
					nodeValue=node.getAttributes().getNamedItem("value");
					if(null!=nodeValue){
						strNodeValue=nodeValue.getNodeValue();
						strNodeValue=strNodeValue.replace("'", "\"");
						jsonObject=JSONObject.fromObject(strNodeValue);
					}
					if("1".equals(strIsControl)==true && null !=jsonObject)
					{
						strImgId=jsonObject.get("id").toString();
						strImgTitle=jsonObject.get("title").toString();
						if(strImgId.equals(strConId)==true)
						{
							strTagInfo="<"+strImgTitle+" id=\""+strImgId+"\" />";
							strImgInfo="<img src=\"[\n]?../../img/custcontrol.png\" control=\"[\n]?1\" value=\"[\n]?\\{'id':'"+strImgId+"','title':'"+strImgTitle+"'\\}\"[^>]?/>";
							strDynamicform=strDynamicform.replace("&#39;", "'");
							strDynamicform=strDynamicform.replaceAll(strImgInfo, strTagInfo);
							break;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		finally{
			if(null!=read)
			{
				try
				{
					read.close();
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
			}
		}
		return strDynamicform;
	}
}
