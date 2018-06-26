package com.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Path;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.common.entity.login.Organization;
import com.common.entity.login.Post;
import com.common.entity.login.Role;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.WorkflowService;
import com.common.tool.JsonDateValueProcessor;
import com.common.tool.XmlUnit;
import com.common.workflow.activiti.cmd.CompleteTaskWithCommentCmd;
import com.common.workflow.activiti.cmd.TaskCommitCmd;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("WorkflowService")
public class WorkflowServiceImpl implements WorkflowService {

	private static Logger log = Logger.getLogger(WorkflowServiceImpl.class);
	
	private RuntimeService runtimeService;

	private TaskService taskService;

	private HistoryService historyService;

	private RepositoryService repositoryService;

	private IdentityService identityService;

	private FormService formService;

	private ManagementService managementService;
	
	private CommonService commonService;
	
	private CoreService coreService;
	
	public CoreService getCoreService() {
		return coreService;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	//------------------------------------------------------------
	
	@Override
	public ProcessInstance getProcessInstanceByInid(String strInid){
		ProcessInstance processInstance;
		processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(strInid).singleResult();
		return processInstance;
	}

	@Override
	public String getFormKeyByTaskID(String taskId) {
		Task taskInfo;
		String strReturn;
		strReturn="";
		taskInfo=taskService.createTaskQuery().taskId(taskId).singleResult();
		strReturn=taskInfo.getTaskDefinitionKey();
		return strReturn;
	}

	@Override
	public Boolean getControlTaskInfo(String taskId,String userId,List<Role> listRole,List<Organization> listOrg,List<Post> listPost) {
		Boolean blInfo;
		Task taskInfo;
		blInfo = false;
		taskInfo = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(userId.equals(taskInfo.getAssignee())==true){
			blInfo=true;
		}
		if(blInfo==false)
		{
			if(userId.equals(taskInfo.getCategory())==true){
				blInfo=true;
			}
		}
		if(blInfo==false)
		{
			for(int i=0;i<listRole.size();i++)
			{
				if(listRole.get(i).equals(taskInfo.getCategory())==true){
					blInfo=true;
					break;
				}
			}
		}
		return blInfo;
	}
	
	@Override
	public JSONObject openWFRunData(String deploymentId,String resourceName){
		byte[] bytes;
		String strJsonInfo;
		JSONObject jsonObj;
		InputStream inputStream;
		jsonObj=null;
		bytes=null;
		strJsonInfo=null;
		inputStream=repositoryService.getResourceAsStream(deploymentId, resourceName);
		try {
			bytes=IOUtils.toByteArray(inputStream);
			strJsonInfo=new String(bytes,"UTF-8");
			inputStream.close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		finally{
			IOUtils.closeQuietly(inputStream);
		}
		if (strJsonInfo != null) {
			jsonObj=JSONObject.fromObject(strJsonInfo);
		}
		return jsonObj;
	}

	private JSONObject getWFJsonObj(JSONObject jsonObjInfo){
		String strCollection;
		String strCondition;
		JSONArray jsonchildShapes;
		JSONArray jsonitems;
		JSONObject jsonchildShape;
		JSONObject jsonstencil;
		JSONObject jsonproperties;
		JSONObject jsonitem;
		JSONObject jsonusertaskassignment;
		String strConditionsequenceflow;
		String strFormkeydefinition;
		String strResourceassignmentexpr;
		String strName;
		jsonchildShapes=(JSONArray)jsonObjInfo.get("childShapes");
		for(int i=0;i<jsonchildShapes.size();i++)
		{
			jsonchildShape=(JSONObject)jsonchildShapes.get(i);
			jsonstencil=(JSONObject)jsonchildShape.get("stencil");
			if("SequenceFlow".equals(jsonstencil.getString("id"))==true){
				jsonproperties=(JSONObject)jsonchildShape.get("properties");
				strConditionsequenceflow=jsonproperties.getString("conditionsequenceflow");
				strConditionsequenceflow=XmlUnit.getDeXmlString(strConditionsequenceflow);
				jsonproperties.put("conditionsequenceflow", strConditionsequenceflow);
			}
			if("UserTask".equals(jsonstencil.getString("id"))==true){
				jsonproperties=(JSONObject)jsonchildShape.get("properties");
				jsonusertaskassignment=(JSONObject)jsonproperties.get("usertaskassignment");
				strFormkeydefinition=jsonproperties.getString("formkeydefinition");
				if(null!=jsonproperties.get("multiinstance_collection"))
				{
					strCollection=jsonproperties.getString("multiinstance_collection");
					strCollection=XmlUnit.getDeXmlString(strCollection);
					jsonproperties.put("multiinstance_collection", strCollection);
				}
				if(null!=jsonproperties.get("multiinstance_condition")){
					strCondition=jsonproperties.getString("multiinstance_condition");
					strCondition=XmlUnit.getDeXmlString(strCondition);
					jsonproperties.put("multiinstance_condition", strCondition);
				}
				try {
					strFormkeydefinition=URLDecoder.decode(strFormkeydefinition,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					log.error(e.toString(), e);
				}
				if(null!=jsonusertaskassignment)
				{
					jsonitems=(JSONArray)jsonusertaskassignment.get("items");
					if(null!=jsonitems){
						for(int j=0;j<jsonitems.size();j++)
						{
							jsonitem=(JSONObject)jsonitems.get(j);
							if(null!=jsonitem){
								strResourceassignmentexpr=jsonitem.getString("resourceassignmentexpr");
								strName=jsonitem.getString("name");
								if("undefined".equals(strResourceassignmentexpr)==true)
								{
									jsonitem.put("resourceassignmentexpr", "");
								}
								if("undefined".equals(strName)==true)
								{
									jsonitem.put("name", "");
								}
							}
						}
					}
				}
				strFormkeydefinition=XmlUnit.getDeXmlString(strFormkeydefinition);
				jsonproperties.put("formkeydefinition", strFormkeydefinition);
			}
		}
		return jsonObjInfo;
	}
	
	
	
	private String getFirstUserTaskKey(ProcessDefinition definition) {
		String strKey;
		ActivityImpl startActivity;
		PvmTransition pvmTransition;
		PvmActivity targetActivity;
		ProcessDefinitionEntity definitionEntity;
		strKey="";
		definitionEntity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definition.getId());
		startActivity = definitionEntity.getInitial();
		if (startActivity.getOutgoingTransitions().size() == 1) {
			pvmTransition = startActivity.getOutgoingTransitions().get(0);
			targetActivity = pvmTransition.getDestination();
			if ("userTask".equals(targetActivity.getProperty("type"))==true) {
				strKey=targetActivity.getId();
			}
		}
		return strKey;
	}
		
	@Override
	public List<String> getMultipleUser(String strUserTaskId,ActivityExecution execution) throws Exception{
		String strUsersId;
		String[] strUsers;
		List<String> userIds;
		userIds=new ArrayList<String>();
		strUsersId=(String)execution.getVariable(strUserTaskId);
		strUsers=strUsersId.split(",");
		for(int i=0;i<strUsers.length;i++)
		{
			userIds.add(strUsers[i]);
		}
		return userIds;
	}
	
	@Override
	public String getUsersIdBySql(String strJsonSql) {
		JSONObject jsonSqlInfo;
		String strSysid;
		String strSqlInfo;
		String strReturn;
		strReturn="";
		strJsonSql=strJsonSql.replace("\r", " ");
		strJsonSql=strJsonSql.replace("\n", " ");
		jsonSqlInfo=JSONObject.fromObject(strJsonSql);
		strSysid=jsonSqlInfo.getString("sysid");
		strSqlInfo=jsonSqlInfo.getString("custSql");
		try {
			strReturn=coreService.getStringTypeBySql(strSysid, strSqlInfo,null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return strReturn;
	}
	
	@Override
	public String getDeptPostUser(String strAssignmentUserId) {
		String strSql;
		String strJsonInfo;
		strSql="SELECT user_id FROM sc_user_post WHERE por_id='"+strAssignmentUserId+"'";
		strJsonInfo="{\"sysid\":\"shiro\",\"custSql\":\""+strSql+"\"}";
		return getUsersIdBySql(strJsonInfo);
	}
	
	@Override
	public String getRoleUser(String strAssignmentUserId) {
		String strSql;
		String strJsonInfo;
		strSql="SELECT user_id FROM sc_user_role WHERE role_id='"+strAssignmentUserId+"'";
		strJsonInfo="{\"sysid\":\"shiro\",\"custSql\":\""+strSql+"\"}";
		return getUsersIdBySql(strJsonInfo);
	}
	
	@Override
	public String getDeptUser(String strAssignmentUserId) {
		String strSql;
		String strJsonInfo;
		strSql="SELECT sc_user_post.user_id FROM sc_organization JOIN sc_post_org ON sc_organization.id=sc_post_org.org_id JOIN sc_user_post ON sc_post_org.id=sc_user_post.por_id WHERE sc_organization.id='"+strAssignmentUserId+"'";
		strJsonInfo="{\"sysid\":\"shiro\",\"custSql\":\""+strSql+"\"}";
		return getUsersIdBySql(strJsonInfo);
	}
	
	@Override
	public String getPostUser(String strAssignmentUserId) {
		String strSql;
		String strJsonInfo;
		strSql="SELECT sc_user_post.user_id FROM sc_post_org JOIN sc_user_post ON sc_post_org.id=sc_user_post.por_id WHERE sc_post_org.post_id='"+strAssignmentUserId+"'";
		strJsonInfo="{\"sysid\":\"shiro\",\"custSql\":\""+strSql+"\"}";
		return getUsersIdBySql(strJsonInfo);
	}
	
	@Override
	public String expreeString(String strExpree,Map<String, Object> variables){
		int iStart;
		int iEnd;
		String strReturn;
		String strTemp;
		Iterator<Entry<String, Object>> iterMap;
		Entry<String, Object> entry;
		ExpressionFactory factory;
		ValueExpression valueExpress;
		SimpleContext elContext;
		factory=new ExpressionFactoryImpl();
		elContext=new SimpleContext();
		strReturn=String.copyValueOf(strExpree.toCharArray());
		iterMap=variables.entrySet().iterator();
		while(iterMap.hasNext())
		{
			entry=iterMap.next();
			elContext.setVariable(entry.getKey(),factory.createValueExpression(entry.getValue(),entry.getValue().getClass()));
		}
		try
		{
			iStart=strExpree.indexOf("${");
			while(-1!=iStart){
				iEnd=strExpree.indexOf("}", iStart);
				if(iStart>0)
				{
					if("\\".equals(strExpree.charAt(iStart-1))==true){
						iStart=strExpree.indexOf("${");
						continue;
					}
				}
				if(iEnd>iStart)
				{
					strTemp=strExpree.substring(iStart, (iEnd+1)).trim();
					valueExpress=factory.createValueExpression(elContext,strTemp,String.class);
					strReturn=strReturn.replace(strTemp, valueExpress.getValue(elContext).toString());
					iStart=strExpree.indexOf("${",(iEnd+1));
				}
				else
				{
					iStart=-1;
				}
			}
		}catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		return strReturn;
	}

	@Override
	public String createBussinKey() {
		String strKey;
		strKey="";
		try {
			strKey=commonService.getKeybyTabCol("wf", "act_busskey", "id");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return strKey;
	}
	
	@Override
	public String getDeploymentIdByTaskId(String strTkid){
		Task taskObj;
		ProcessDefinition processDefin;
		taskObj=taskService.createTaskQuery().taskId(strTkid).singleResult();
		processDefin=repositoryService.createProcessDefinitionQuery().processDefinitionId(taskObj.getProcessDefinitionId()).singleResult();
		return processDefin.getDeploymentId();
	}
	
	@Override
	public void completeTaskWithCommentCmd(String strTkid,Map<String,Object> variables){
		CompleteTaskWithCommentCmd cmd;
		try {
			cmd=new CompleteTaskWithCommentCmd(strTkid,variables,"SC_2017101910220001");
			managementService.executeCommand(cmd);
        } catch (IllegalStateException ex) {
        	log.error(ex.getMessage(), ex);
        }
	}
	
	@Override
	public String saveWFModel(String id, String key, String name, String description,String jsonXml)throws JsonProcessingException, IOException {
		Model model;
		String retJson;
		model = repositoryService.getModel(id);
		if(null==model)
		{
			model = repositoryService.newModel();
		}
		model.setKey(key);
		model.setName(name);
		model.setMetaInfo(description);
		model.setTenantId("SystemWeb");
		retJson=getWFJsonString(jsonXml);
		repositoryService.saveModel(model);
		repositoryService.addModelEditorSource(model.getId(),retJson.getBytes("utf-8"));
		return model.getId();
	}
	
	private String getWFJsonString(String jsonXml){
		JSONObject jsonObj;
		JSONObject jsonChildShape;
		JSONObject jsonChildStencil;
		JSONObject jsonChildProperties;
		JSONArray jsonChildShapes;
		String strCollection;
		strCollection="";
		jsonObj=JSONObject.fromObject(jsonXml);
		if(null!=jsonObj){
			jsonChildShapes=(JSONArray)jsonObj.get("childShapes");
			for(int i=0;i<jsonChildShapes.size();i++)
			{
				jsonChildShape=(JSONObject)jsonChildShapes.get(i);
				jsonChildProperties=(JSONObject)jsonChildShape.get("properties");
				jsonChildStencil=(JSONObject)jsonChildShape.get("stencil");
				if(null!=jsonChildStencil)
				{
					if("SequenceFlow".equals(jsonChildStencil.getString("id"))){
						strCollection=jsonChildProperties.getString("conditionalflow");
						strCollection=XmlUnit.getDeXmlString(strCollection);
						jsonChildProperties.put("conditionalflow", strCollection);
					}
				}
			}
		}
		return jsonObj.toString();
	}
	
	@Override
	public String deployWFModel(String id,String key, String name, String description,
			String jsonXml)throws JsonProcessingException, IOException {
		Model modelData;
		JsonNode modelNode;
		byte[] bpmnBytes;
		BpmnModel model;
		JSONObject jsonObjInfo;
		BpmnJsonConverter jsonConverter;
		BpmnXMLConverter xmlConverter;
		String processName;
		Deployment deployment;
		DeploymentBuilder deployBuilder;
		bpmnBytes = null;
		jsonConverter = new BpmnJsonConverter();
		xmlConverter = new BpmnXMLConverter();
		jsonObjInfo=JSONObject.fromObject(jsonXml);
		modelData = repositoryService.getModel(id);
		if(null==modelData)
		{
			modelData = repositoryService.newModel();
		}
		modelData.setName(name);
		modelData.setKey(key);
		if((1==modelData.getVersion() && null==modelData.getDeploymentId())==false)
		{
			modelData.setVersion(modelData.getVersion()+1);
		}
		modelData.setMetaInfo(description);
		jsonObjInfo=getWFJsonObj(jsonObjInfo);
		repositoryService.addModelEditorSource(modelData.getId(),jsonXml.getBytes("utf-8"));
		modelNode = (JsonNode) new ObjectMapper().readTree(jsonObjInfo.toString().getBytes("utf-8"));
		model = jsonConverter.convertToBpmnModel(modelNode);
		bpmnBytes = xmlConverter.convertToXML(model);
		processName = modelData.getId() + ".bpmn20.xml";
		deployBuilder=repositoryService.createDeployment();
		deployBuilder.name(modelData.getName());
		deployBuilder.addString(processName, new String(bpmnBytes, "UTF-8"));
		deployBuilder.addString(modelData.getId()+".json", jsonXml);
		deployBuilder.tenantId(modelData.getTenantId());
		deployBuilder.category(modelData.getCategory());
		deployment = deployBuilder.deploy();
		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);
		return modelData.getId();
	}
	
	@Override
	public String getJsonInfoByDeployId(String strDeployId){
		byte[] bytes;
		List<String> list;
		String strResourceName;
		String strJsonInfo;
		InputStream stream;
		stream=null;
		strJsonInfo="";
		strResourceName="";
		list=repositoryService.getDeploymentResourceNames(strDeployId);
		for(int i=0;i<list.size();i++){
			if(list.get(i).indexOf(".json")!=-1){
				strResourceName=list.get(i);
				break;
			}
		}
		try {
			stream=repositoryService.getResourceAsStream(strDeployId, strResourceName);
			bytes=IOUtils.toByteArray(stream);
			strJsonInfo=new String(bytes,"UTF-8");
			stream.close();
		} catch (IOException e) {
			log.error(e.toString(),e);
		}
		finally{
			IOUtils.closeQuietly(stream);
		}
		return strJsonInfo;
	}

	@Override
	public String delDeployment(String deployId) {
		String strMsg;
		strMsg="";
		try
		{
			repositoryService.deleteDeployment(deployId);
			strMsg="{\"isDel\":true,\"msg\":\"删除成功!\"}";
		}
		catch(Exception e)
		{
			strMsg="{\"isDel\":false,\"msg\":\"删除失败,存在运行数据或定时任务!\"}";
			log.error(e.getMessage(),e);
		}
		return strMsg;
	}
	
	@Override
	public String deleteModel(String modelId){
		Model model;
		String strMsg;
		model=repositoryService.createModelQuery().modelId(modelId).singleResult();
		if(null!=model)
		{
			if(model.getDeploymentId()!=null){
				strMsg="{\"isDel\":false,\"msg\":\"删除失败,必需先删除部署!\"}";
			}
			else
			{
				repositoryService.deleteModel(modelId);
				strMsg="{\"isDel\":true,\"msg\":\"删除成功!\"}";
			}
		}
		else
		{
			strMsg="{\"isDel\":true,\"msg\":\"删除成功!\"}";
		}
		return strMsg;
	}
	
	@Override
	public void updateFirstUserLink(String bussinKey,String strUserId){
		StringBuilder strSql;
		List<Map> listMap;
		Map mapInfo;
		String instId;
		Object[] selParams;
		Object[] updateParams;
		listMap=null;
		strSql=new StringBuilder();
		strSql.append("SELECT Proc_inst_ID_ AS instId ");
		strSql.append("FROM act_ru_execution ");
		strSql.append("WHERE BUSINESS_KEY_=?");
		selParams=new Object[]{
			bussinKey
		};
		try {
			listMap=commonService.queryListSql("wf", strSql.toString(),selParams);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		instId="";
		if(null!=listMap && 0!=listMap.size()){
			mapInfo=listMap.get(0);
			instId=mapInfo.get("instId").toString();
		}
		if("".equals(instId)==false)
		{
			updateParams=new Object[]{
				strUserId
			};
			try {
				commonService.updateObj("wf", "act_ru_identitylink", "user_id_",updateParams, "and (PROC_INST_ID_='"+instId+"') and (user_id_='') and(type_='participant')");
				commonService.updateObj("wf", "act_hi_identitylink", "user_id_",updateParams, "and (PROC_INST_ID_='"+instId+"') and (user_id_='') and(type_='participant')");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			
		}
	}
	
	@Override
	public void saveDeployWFData(String deployId, String resourceName,String jsonXml) {
		Object[] params;
		params=new Object[]{
			jsonXml
		};
		try {
			commonService.updateObj("wf", "act_ge_bytearray", "BYTES_",params, "and DEPLOYMENT_ID_='"+deployId+"' and NAME_='"+resourceName+"'");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	//--------------------------------------------------------------------------------
	
	@Override
	public JSONObject openWFModel(String modelId) throws UnsupportedEncodingException{
		Model model;
		byte[] bytes;
		String modelEditorSource;
		JSONObject jsonObj;
		model = repositoryService.getModel(modelId);
		bytes = repositoryService.getModelEditorSource(model.getId());
		jsonObj=null;
		if (bytes != null) {
			modelEditorSource = new String(bytes, "UTF-8");
			jsonObj=JSONObject.fromObject(modelEditorSource);
		}
		return jsonObj;
	}
	
	@Override
	public Map saveWFModelByMap(Map mapInfo) {
		Model model;
		String strHid;
		Object[] maps;
		String retJson;
		String strHkey;
		String strHname;
		String strHJsonXml;
		Object[] backParams;
		String strHDescription;
		backParams=new String[1];
		maps=(Object[])mapInfo.get("map.hId");
		strHid=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hKey");
		strHkey=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hName");
		strHname=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hJsonXml");
		strHJsonXml=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hDescription");
		strHDescription=maps[0].toString();
		model = repositoryService.getModel(strHid);
		if(null==model)
		{
			model = repositoryService.newModel();
		}
		model.setKey(strHkey);
		model.setName(strHname);
		model.setMetaInfo(strHDescription);
		model.setTenantId("SystemWeb");
		retJson=getWFJsonString(strHJsonXml);
		try
		{
			repositoryService.saveModel(model);
			repositoryService.addModelEditorSource(model.getId(),retJson.getBytes("utf-8"));
			backParams[0]=model.getId();
			mapInfo.put("map._saveObj", backParams);
			mapInfo.put("map.hver", new String[]{"-1"});
			coreService.writeEncryptWorkFlowSql(mapInfo);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		return mapInfo;
	}
	
	@Override
	public void saveDeployWFByRedisKey(String rediskey) {
		Map mapInfo;
		String strHid;
		Object[] maps;
		String[] param;
		Object[] wfparam;
		StringBuilder strSql;
		Map mapKeyListInfo;
		List<Map> listKeyMap;
		mapInfo=null;
		param=new String[2];
		wfparam=new Object[1];
		strSql=new StringBuilder();
		try {
			mapInfo=(Map)commonService.loadRedisObj(rediskey);
		} catch (Exception e1) {
			log.error(e1.getMessage(),e1);
		}
		if(null!=mapInfo)
		{
			maps=(Object[])mapInfo.get("map.hId");
			strHid=maps[0].toString();
			wfparam[0]=strHid;
			listKeyMap=null;
			strSql.append("SELECT key_ AS keyinfo,version_ AS ver ");
			strSql.append("FROM act_re_procdef ");
			strSql.append("WHERE DEPLOYMENT_ID_=?");
			try {
				listKeyMap=commonService.queryListSql("wf", strSql.toString(), wfparam);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			if(null!=listKeyMap && 1==listKeyMap.size())
			{
				mapKeyListInfo=listKeyMap.get(0);
				param[0]=mapKeyListInfo.get("keyinfo").toString();
				param[1]=mapKeyListInfo.get("ver").toString();
				mapInfo.put("map.hKey", new String[]{param[0]});
				mapInfo.put("map.hver", new String[]{param[1]});
				coreService.writeEncryptWorkFlowSql(mapInfo);
			}
		}
	}

	@Override
	public Map deployWFModelByRedisKey(String rediskey) {
		String id;
		String key;
		String name;
		Map mapInfo;
		String strVer;
		Object[] maps;
		String strSql;
		List<Map> list;
		String jsonXml;
		String strTemp;
		Map mapFormList;
		Map mapListInfo;
		BpmnModel model;
		Model modelData;
		String strJsinfo;
		byte[] bpmnBytes;
		String strBtnlist;
		String strHidlist;
		String strConlist;
		String strTaginfo;
		JsonNode modelNode;
		String description;
		String processName;
		String strFormlist;
		String strRoleList;
		String[] backParams;
		Deployment deployment;
		String strDynamicform;
		JSONObject jsonObjInfo;
		String strTaskformlist;
		String strTaskuserlist;
		BpmnXMLConverter xmlConverter;
		DeploymentBuilder deployBuilder;
		BpmnJsonConverter jsonConverter;
		mapInfo=null;
		bpmnBytes = null;
		try {
			mapInfo=(Map)commonService.loadRedisObj(rediskey);
		} catch (Exception e1) {
			log.error(e1.getMessage(),e1);
		}
		if(null!=mapInfo)
		{
			maps=(Object[])mapInfo.get("map.hId");
			id=maps[0].toString();
			maps=(Object[])mapInfo.get("map.hKey");
			key=maps[0].toString();
			maps=(Object[])mapInfo.get("map.hName");
			name=maps[0].toString();
			maps=(Object[])mapInfo.get("map.hJsonXml");
			jsonXml=maps[0].toString();
			maps=(Object[])mapInfo.get("map.hDescription");
			description=maps[0].toString();
			jsonConverter = new BpmnJsonConverter();
			xmlConverter = new BpmnXMLConverter();
			jsonObjInfo=JSONObject.fromObject(jsonXml);
			modelData = repositoryService.getModel(id);
			if(null==modelData)
			{
				modelData = repositoryService.newModel();
			}
			modelData.setName(name);
			modelData.setKey(key);
			if((1==modelData.getVersion() && null==modelData.getDeploymentId())==false)
			{
				modelData.setVersion(modelData.getVersion()+1);
			}
			modelData.setMetaInfo(description);
			jsonObjInfo=getWFJsonObj(jsonObjInfo);
			try
			{
				backParams=new String[1];
				repositoryService.addModelEditorSource(modelData.getId(),jsonXml.getBytes("utf-8"));
				modelNode = (JsonNode) new ObjectMapper().readTree(jsonObjInfo.toString().getBytes("utf-8"));
				model = jsonConverter.convertToBpmnModel(modelNode);
				bpmnBytes = xmlConverter.convertToXML(model);
				processName = modelData.getId() + ".bpmn20.xml";
				deployBuilder=repositoryService.createDeployment();
				deployBuilder.name(modelData.getName());
				deployBuilder.addString(processName, new String(bpmnBytes, "UTF-8"));
				deployBuilder.addString(modelData.getId()+".json", jsonXml);
				deployBuilder.tenantId(modelData.getTenantId());
				deployBuilder.category(modelData.getCategory());
				deployment = deployBuilder.deploy();
				modelData.setDeploymentId(deployment.getId());
				repositoryService.saveModel(modelData);
				backParams[0]=modelData.getId();
				mapInfo.put("map._saveObj", backParams);
				strSql="SELECT version_ AS ver FROM act_re_procdef WHERE DEPLOYMENT_ID_='"+deployment.getId()+"'";
				list=commonService.queryListSql("wf", strSql, null);
				if(null != list && 1==list.size())
				{
					mapListInfo=list.get(0);
					strVer=mapListInfo.get("ver").toString();
					mapFormList=getWFFormInfoData(key,-1);
					strJsinfo=mapFormList.get("jsinfo").toString();
					strTemp=mapFormList.get("temp").toString();
					strBtnlist=mapFormList.get("btnlist").toString();
					strHidlist=mapFormList.get("hidlist").toString();
					strConlist=mapFormList.get("conlist").toString();
					strFormlist=mapFormList.get("formlist").toString();
					strTaginfo=mapFormList.get("taginfo").toString();
					strRoleList=mapFormList.get("roleList").toString();
					strDynamicform=mapFormList.get("dynamicform").toString();
					strTaskformlist=mapFormList.get("taskformlist").toString();
					strTaskuserlist=mapFormList.get("taskuserlist").toString();
					mapInfo.put("map.hbtnlist",new String[]{strBtnlist});
					mapInfo.put("map.hconlist",new String[]{strConlist});
					mapInfo.put("map.hformlist",new String[]{strFormlist});
					mapInfo.put("map.hhidlist",new String[]{strHidlist});
					mapInfo.put("map.htemp",new String[]{strTemp});
					mapInfo.put("map.hjsinfo",new String[]{strJsinfo});
					mapInfo.put("map.htaginfo",new String[]{strTaginfo});
					mapInfo.put("map.hdynamicform",new String[]{strDynamicform});
					mapInfo.put("map.hroleList",new String[]{strRoleList});
					mapInfo.put("map.htaskformlist",new String[]{strTaskformlist});
					mapInfo.put("map.htaskuserlist",new String[]{strTaskuserlist});
					mapInfo.put("map.hver", new String[]{strVer});
					coreService.writeEncryptWorkFlowSql(mapInfo);
				}
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
		}
		return mapInfo;
	}

	@Override
	public Map deleteModelByMap(String modelId,Map mapInfo) {
		String key;
		Model model;
		String strId;
		Object[] maps;
		String strSql;
		Object[] param;
		Map mapListInfo;
		List<Map> listMap;
		String[] strIsDel;
		String[] strMsgInfo;
		strIsDel=new String[1];
		strMsgInfo=new String[1];
		param=new Object[2];
		maps=(Object[])mapInfo.get("map.hKey");
		key=maps[0].toString();
		model=repositoryService.createModelQuery().modelId(modelId).singleResult();
		if(null!=model)
		{
			if(model.getDeploymentId()!=null){
				strIsDel[0]="false";
				strMsgInfo[0]="删除失败,必需先删除部署!";
			}
			else
			{
				listMap=null;
				repositoryService.deleteModel(modelId);
				param[0]=key;
				param[1]=-1;
				strSql="SELECT id from sc_workflow where wkkey=? and ver=?";
				try {
					listMap=commonService.queryListSql("com", strSql, param);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(null!=listMap && 1==listMap.size())
				{
					mapListInfo=listMap.get(0);
					strId=mapListInfo.get("id").toString();
					try {
						commonService.deleteObj("com", "sc_workflow", "id", strId);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
				strIsDel[0]="true";
				strMsgInfo[0]="删除成功!";
			}
		}
		else
		{
			strIsDel[0]="true";
			strMsgInfo[0]="删除成功!";
		}
		mapInfo.put("attr.wf_is_del", strIsDel);
		mapInfo.put("attr.wf_msg", strMsgInfo);
		return mapInfo;
	}
	
	@Override
	public Map delDeploymentByMap(String deployId,Map mapInfo){
		String strId;
		Object[] param;
		Map mapListInfo;
		Object[] wfparam;
		String[] strIsDel;
		List<Map> listMap;
		Map mapKeyListInfo;
		String[] strMsgInfo;
		List<Map> listKeyMap;
		StringBuilder strSql;
		strIsDel=new String[1];
		strMsgInfo=new String[1];
		param=new Object[2];
		wfparam=new Object[1];
		strSql = new StringBuilder();
		try
		{
			wfparam[0]=deployId;
			strSql.append("SELECT key_ AS keyinfo,version_ AS ver ");
			strSql.append("FROM act_re_procdef ");
			strSql.append("WHERE DEPLOYMENT_ID_=?");
			listKeyMap=commonService.queryListSql("wf", strSql.toString(), wfparam);
			if(null!=listKeyMap && 1==listKeyMap.size())
			{
				mapKeyListInfo=listKeyMap.get(0);
				param[0]=mapKeyListInfo.get("keyinfo").toString();
				param[1]=mapKeyListInfo.get("ver").toString();
				repositoryService.deleteDeployment(deployId);
				strSql.delete(0, strSql.length());
				strSql.append("SELECT id from sc_workflow where wkkey=? and ver=?");
				listMap=commonService.queryListSql("com", strSql.toString(), param);
				if(null!=listMap && 1==listMap.size())
				{
					mapListInfo=listMap.get(0);
					strId=mapListInfo.get("id").toString();
					commonService.deleteObj("com", "sc_workflow", "id", strId);
				}
				strIsDel[0]="true";
				strMsgInfo[0]="删除成功!";
			}
			else
			{
				strIsDel[0]="false";
				strMsgInfo[0]="删除失败,未找到对应流程相关信息!";
			}
		}
		catch(Exception e)
		{
			strIsDel[0]="false";
			strMsgInfo[0]="删除失败,存在运行数据或定时任务!";
			log.error(e.getMessage(),e);
		}
		mapInfo.put("attr.wf_is_del", strIsDel);
		mapInfo.put("attr.wf_msg", strMsgInfo);
		return mapInfo;
	}
	@Override
	public void delAllDeployment(String deployId, Boolean blInfo) {
		String strId;
		Object[] param;
		Map mapListInfo;
		Object[] params;
		List<Map> listMap;
		Map mapKeyListInfo;
		List<Map> listKeyMap;
		StringBuilder strSql;
		StringBuilder strDelSql;
		param=new Object[2];
		strSql=new StringBuilder();
		strDelSql=new StringBuilder();
		params=new Object[]{
				deployId
		};
		listKeyMap=null;
		if(blInfo==true)
		{
			strSql.append("DELETE act_re_url ");
			strSql.append("FROM act_re_procdef ");
			strSql.append("JOIN act_re_url ");
			strSql.append("ON act_re_url.processkey=act_re_procdef.KEY_ ");
			strSql.append("WHERE act_re_procdef.DEPLOYMENT_ID_=? ");
			strDelSql.append("DELETE act_hi_suggestion ");
			strDelSql.append("FROM act_hi_suggestion ");
			strDelSql.append("JOIN act_re_url ");
			strDelSql.append("ON act_hi_suggestion.inid=act_re_url.inid ");
			strDelSql.append("JOIN act_re_procdef ");
			strDelSql.append("ON act_re_url.processkey=act_re_procdef.KEY_ ");
			strDelSql.append("WHERE act_re_procdef.DEPLOYMENT_ID_=?");
			try {
				commonService.execNoListSql("wf", strDelSql.toString(),params);
				commonService.execNoListSql("wf", strSql.toString(),params);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		strSql.delete(0, strSql.length());
		strSql.append("SELECT key_ AS keyinfo,version_ AS ver ");
		strSql.append("FROM act_re_procdef ");
		strSql.append("WHERE DEPLOYMENT_ID_=?");
		try {
			listKeyMap=commonService.queryListSql("wf", strSql.toString(), params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(null!=listKeyMap && 1==listKeyMap.size())
		{
			listMap=null;
			mapKeyListInfo=listKeyMap.get(0);
			param[0]=mapKeyListInfo.get("keyinfo").toString();
			param[1]=mapKeyListInfo.get("ver").toString();
			strSql.delete(0, strSql.length());
			strSql.append("SELECT id from sc_workflow where wkkey=? and ver=?");
			try {
				listMap=commonService.queryListSql("com", strSql.toString(), param);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			if(null!=listMap && 1==listMap.size())
			{
				mapListInfo=listMap.get(0);
				strId=mapListInfo.get("id").toString();
				try {
					commonService.deleteObj("com", "sc_workflow", "id", strId);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		repositoryService.deleteDeployment(deployId, blInfo);
	}

	@Override
	public Map saveBusinessKey(Map mapInfo) {
		String[] strArr;
		String[] strBack;
		String businessKey;
		String strListTask;
		strBack=new String[1];
		strArr=(String[])mapInfo.get("map.businesskey");
		businessKey=strArr[0];
		strListTask="[";
		strListTask+="{\"inid\":\"\",\"taskid\":\"\",\"businesskey\":\""+businessKey+"\",\"formkey\":\"\",\"taskdefinitionKey\":\"\"}";
		strListTask+="]";
		strBack[0]=strListTask;
		mapInfo.put("attr._wfcurrtaskid", strBack);
		return mapInfo;
	}

	@Override
	public void saveWFUrlInfo(String strPath,String userId,String strListTask,Map mapInfo) {
		int iLength;
		Date date;
		String urlInfo;
		String businesskey;
		String strKey;
		String inid;
		String processkey;
		String category;
		String wfName;
		String wfCode;
		String wfNameInfo;
		String wfdeploymentId;
		long lcount;
		List<Map> list;
		Object[] params;
		Object[] objArr;
		Object[] retParam;
		List<Object> urlparams;
		List<Object> urlDeploymentIdParams;
		List<Object> urlUpdateParams;
		Object[] urlCountparams;
		JSONArray jsonArray;
		JSONObject jsonObject;
		date=new Date();
		retParam=new Object[1];
		urlparams=new ArrayList<Object>();
		businesskey="";
		inid="";
		lcount=0;
		strKey="";
		category="";
		wfName="";
		wfCode="";
		wfNameInfo="";
		wfdeploymentId="";
		objArr=(Object[])mapInfo.get("attr._urlInfo");
		urlInfo=objArr[0].toString();
		iLength=strPath.length();
		list=null;
		urlInfo=urlInfo.substring(iLength, urlInfo.length());
		jsonArray=JSONArray.fromObject(strListTask);
		objArr=(Object[])mapInfo.get("map.processkey");
		processkey=objArr[0].toString();
		urlDeploymentIdParams=new ArrayList<Object>();
		params=new Object[]{
			processkey
		};
		try {
			list=commonService.queryListSql("wf", "SELECT CATEGORY_ AS typeinfo,name_ AS nameinfo,DEPLOYMENT_ID_ as deploymentId FROM act_re_procdef WHERE key_=? ORDER BY version_ DESC",params);
		} catch (Exception e1) {
			log.error(e1.getMessage(),e1);
		}
		if(list.size()>0)
		{
			category=list.get(0).get("typeinfo").toString();
			wfName=list.get(0).get("nameinfo").toString();
			wfdeploymentId=list.get(0).get("deploymentId").toString();
		}
		if(jsonArray.size()>0)
		{
			jsonObject=(JSONObject)jsonArray.get(0);
			businesskey=jsonObject.getString("businesskey");
			inid=jsonObject.getString("inid");
			if(null!=jsonObject.get("name")){
				wfNameInfo=jsonObject.getString("name");
			}
			if(null!=jsonObject.get("code")){
				wfCode=jsonObject.getString("code");
			}
		}
		try{
			strKey=commonService.getKeybyTabCol("wf", "act_re_url", "id");
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		if("".equals(inid)==true)
		{
			try{
				urlCountparams=new Object[]{
					businesskey
				};
				lcount=commonService.queryCountSql("wf", "select id as countInfo from act_re_url where businkey=?",urlCountparams);
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
			if(lcount==0){
				if(urlInfo.indexOf("?")==-1)
				{
					urlInfo+="?fiid="+businesskey;
				}
				else
				{
					urlInfo+="&fiid="+businesskey;
				}
				try{
					urlparams.add(strKey);
					urlparams.add(wfCode);
					urlparams.add("["+wfName+"]"+wfNameInfo);
					urlparams.add(businesskey);
					urlparams.add(urlInfo);
					urlparams.add(userId);
					urlparams.add(processkey);
					urlparams.add(wfName);
					urlparams.add(category);
					urlparams.add(date);
					urlparams.add(0);
					urlparams.add(0);
					commonService.insertObj("wf", "act_re_url", "id,code,name,businkey,url,user_id,processkey,wfname,category,user_time,issubmit,istempsubmit",urlparams.toArray());
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
			}
		}
		else{
			if(urlInfo.indexOf("inid")==-1)
			{
				urlInfo+="&inid="+inid;
			}
			try{
				urlUpdateParams=new ArrayList<Object>();
				urlUpdateParams.add(inid);
				urlUpdateParams.add(urlInfo);
				urlUpdateParams.add(userId);
				urlUpdateParams.add(0);
				commonService.updateObj("wf", "act_re_url", "inid,url,user_id,istempsubmit",urlUpdateParams.toArray(), "and (businkey='"+businesskey+"')");
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
		}
		retParam[0]=businesskey;
		urlDeploymentIdParams.add(wfdeploymentId);
		try {
			commonService.updateObj("wf", "act_re_url", "deploymentId",urlDeploymentIdParams.toArray(),"and (businkey='"+businesskey+"') AND (IFNULL(deploymentId,'')='')");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public String startProcess(String strDeploymentId,String userId,String businessKey,Map<String,Object> variables){
		String strKey;
		Task taskInfo;
		String strListTask;
		Iterator iterEntry;
		List<Task> tasklist;
		JsonConfig jsonConfig;
		ProcessInstance instance;
		String strFirstUserTaskKey;
		ProcessDefinition definition;
		jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		strListTask="";
		if("".equals(strDeploymentId)==false)
		{
			definition=repositoryService.createProcessDefinitionQuery().deploymentId(strDeploymentId).singleResult();
			strFirstUserTaskKey=getFirstUserTaskKey(definition);
			iterEntry=variables.keySet().iterator();
			if("".equals(strFirstUserTaskKey)==false){
				while(iterEntry.hasNext())
				{
					strKey=(String)iterEntry.next();
					if(strKey.equals(strFirstUserTaskKey+"_assigneeUserId")==true)
					{
						variables.put(strKey, userId);
						break;
					}
				}
			}
			instance=this.startWorkflow(definition.getId(), businessKey, userId, variables);
			tasklist=this.getCurrTask(instance);
			strListTask="[";
			if(tasklist.size()!=0){
				for(int i=0;i<tasklist.size();i++)
				{
					taskInfo=tasklist.get(i);
					strListTask+="{\"inid\":\""+instance.getId()+"\",\"taskid\":\""+taskInfo.getId()+"\",\"businesskey\":\""+businessKey+"\",\"formkey\":\""+taskInfo.getFormKey()+"\",\"taskdefinitionKey\":\""+taskInfo.getTaskDefinitionKey()+"\"},";
				}
				strListTask=strListTask.substring(0,strListTask.length()-1);
			}
			strListTask+="]";
		}
		return strListTask;
	}
	
	@Override
	public ProcessInstance startWorkflow(String processDefinitionId,
			String businessKey, String strUserId, Map<String, Object> variables) {
		ProcessInstance processInstance;
		processInstance=null;
		try{
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(strUserId);
			processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
		}
		catch(Exception e){
			log.error(e.toString(),e);
		}
		return processInstance;
	}
	
	@Override
	public List<Task> getCurrTask(ProcessInstance instance) {
		List<Task> list;
		list=taskService.createTaskQuery().processInstanceId(instance.getId()).orderByTaskCreateTime().desc().list();
		return list;
	}
	
	@Override
	public void delRunWF(String processInstanceId,String userId){
		runtimeService.deleteProcessInstance(processInstanceId, userId+":删除流程");
		historyService.deleteHistoricProcessInstance(processInstanceId);
	}
	
	@Override
	public String getDeploymentIdByProcesskey(String processkey){
		Model model;
		model=repositoryService.createModelQuery().modelKey(processkey).singleResult();
		return model.getDeploymentId();
	}
	
	public List<Map> getCurrNodeUser(String strInid){
		String strKey;
		Object[] params;
		List<Map> listMap;
		String strResource;
		List<Map> listKeyMap;
		List<String> listName;
		StringBuilder strSql;
		Map<String,String> mapInfo;
		mapInfo=null;
		listMap=null;
		listKeyMap=null;
		strSql=new StringBuilder();
		strSql.append("SELECT deploymentId ");
		strSql.append("FROM act_re_url ");
		strSql.append("WHERE inid=?");
		params=new Object[]{
			strInid
		};
		strResource="";
		try {
			listMap=commonService.queryListSql("wf", strSql.toString(), params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		strSql.delete(0, strSql.length());
		strSql.append("SELECT TASK_DEF_KEY_ AS keyinfo ");
		strSql.append("FROM act_ru_task ");
		strSql.append("WHERE PROC_INST_ID_=? ");
		try {
			listKeyMap=commonService.queryListSql("wf", strSql.toString(), params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(1==listMap.size() && 1==listKeyMap.size()){
			mapInfo=listMap.get(0);
			if(null!=mapInfo)
			{
				listName=repositoryService.getDeploymentResourceNames(mapInfo.get("deploymentId"));
				for(int i=listName.size()-1;i>=0;i--)
				{
					listName.get(i);
				}
			}
			mapInfo=listKeyMap.get(0);
			strKey=mapInfo.get("keyinfo");
		}
		return null;
	}

	@Override
	public Map getWFFormInfoData(String strKey, Integer ver) throws UnsupportedEncodingException {
		Map retValue;
		retValue=coreService.readEncryptWorkFlowSql(strKey, ver);
		return retValue;
	}

	@Override
	public void taskClaim(String taskId, String userId) {
		if("".equals(userId)==true)
		{
			userId=null;
		}
		this.taskService.claim(taskId, userId);
	}
	
	@Override
	public String getNextUserTaskInfo(String init,String taskId,Boolean blCurr){
		String strSql;
		long lcount;
		boolean blInfo;
		List<Map> mapList;
		ActivityImpl desc;
		String strTaskIds;
		String strRetValue;
		ActivityImpl source;
		String processDefinitionId;
		ProcessDefinitionEntity def;
		List<ActivityImpl> activitiList;
		source=null;
		lcount=0;
		blInfo=false;
		strTaskIds="";
		strRetValue="[";
		strSql="select count(*) as countInfo from act_re_url where inid='"+init+"' and issubmit=2";
		try {
			mapList=commonService.queryListSql("wf", strSql, null);
			if(null!=mapList && 1==mapList.size())
			{
				if("1".equals(mapList.get(0).get("countInfo").toString())){
					blInfo=true;
				}
			}
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		if(false==blInfo)
		{
			lcount=runtimeService.createProcessInstanceQuery().processInstanceId(init).count();
			if(0!=lcount)
			{
				processDefinitionId=runtimeService.createProcessInstanceQuery().processInstanceId(init).singleResult().getProcessDefinitionId();
				def = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
				activitiList=def.getActivities();
				for(int i=0;i<activitiList.size();i++){
					desc=activitiList.get(i);
					if(desc.getId().equals(taskId)==true)
					{
						source=desc;
						break;
					}
				}
				if(null != source){
					strTaskIds=getNextUserTaskInfoByCurrTask(source,"",blCurr);
					strRetValue+=strTaskIds;
				}
			}
		}
		strRetValue+="]";
		return strRetValue;
	}
	
	private String getNextUserTaskInfoByCurrTask(ActivityImpl source,String taskIds, Boolean blCurr){
		String strTemp;
		Integer iType;
		ActivityImpl desc;
		String strRetValue;
		Expression expression;
		String strExpressionText;
		TaskDefinition taskDefinition;
		Set<Expression> usersExpression;
		Set<Expression> groupExpression;
		UserTaskActivityBehavior userTask;
		List<PvmTransition> outTransitions;
		strRetValue="";
		if(true==blCurr)
		{
			if(source.getActivityBehavior() instanceof UserTaskActivityBehavior || source.getActivityBehavior() instanceof MultiInstanceActivityBehavior){
				strTemp="";
				if(source.getActivityBehavior() instanceof UserTaskActivityBehavior){
					iType=0;
					userTask=(UserTaskActivityBehavior)source.getActivityBehavior();
					taskDefinition=userTask.getTaskDefinition();
					expression=taskDefinition.getAssigneeExpression();
					if(null!=expression)
					{
						strExpressionText=expression.getExpressionText();
						if("".equals(strExpressionText)==false)
						{
							iType=1;
						}
					}
					usersExpression=taskDefinition.getCandidateUserIdExpressions();
					if(null != usersExpression && 0!=usersExpression.size()){
						iType=2;
					}
					groupExpression=taskDefinition.getCandidateGroupIdExpressions();
					if(null != groupExpression &&0!=groupExpression.size()){
						iType=3;
					}
					strTemp="{\"type\":1,\"taskId\":\""+source.getId()+"\",\"taskName\":\""+source.getProperty("name")+"\",\"userType\":"+iType+"}";
				}
				if(source.getActivityBehavior() instanceof MultiInstanceActivityBehavior){
					strTemp="{\"type\":2,\"taskId\":\""+source.getId()+"\",\"taskName\":\""+source.getProperty("name")+"\",\"userType\":4}";
				}
				if("".equals(taskIds)==true)
				{
					strRetValue=strTemp;
				}
				else
				{
					strRetValue=taskIds+","+strTemp;
				}
				return strRetValue;
			}
			else
			{
				outTransitions=source.getOutgoingTransitions();
				for(PvmTransition tr:outTransitions){
					desc=(ActivityImpl)tr.getDestination();
					strRetValue=getNextUserTaskInfoByCurrTask(desc,taskIds,true);
					if("".equals(taskIds)==true)
					{
						taskIds=strRetValue;
					}
				}
			}
		}
		else
		{
			outTransitions=source.getOutgoingTransitions();
			for(PvmTransition tr:outTransitions){
				desc=(ActivityImpl)tr.getDestination();
				strRetValue=getNextUserTaskInfoByCurrTask(desc,taskIds,true);
				if("".equals(taskIds)==true)
				{
					taskIds=strRetValue;
				}
			}
		}
		return strRetValue;
	}

	@Override
	public Map<String, Object> getRunVariablesByProcess(String init) {
		Execution excecution;
		List<Execution> listExec;
		Map<String, Object> mapInfo;
		excecution=null;
		listExec=runtimeService.createExecutionQuery().processInstanceId(init).list();
		if(null!=listExec && 1==listExec.size())
		{
			excecution=runtimeService.createExecutionQuery().processInstanceId(init).singleResult();
			mapInfo=runtimeService.getVariables(excecution.getId());
		}
		else
		{
			if(null!=listExec)
			{
				for(Execution exce : listExec)
				{
					if(exce instanceof ProcessInstance){
						excecution=exce;
						break;
					}
				}
				if(null!=excecution)
				{
					mapInfo=runtimeService.getVariables(excecution.getId());
				}
				else
				{
					mapInfo=new HashMap<String,Object>();
				}
			}
			else
			{
				mapInfo=new HashMap<String,Object>();
			}
		}
		return mapInfo;
	}

	@Override
	public void saveSuggestion(String inid,String processkey,String businkey,String taskid,String suggest,String userId,String userName){
		Date dtNow;
		Map mapInfo;
		String strSql;
		String strKey;
		String strCol;
		List<Map> list;
		boolean blInfo;
		Object[] queryParams;
		Object[] insertParam;
		SimpleDateFormat sdf;
		dtNow=new Date();
		blInfo=false;
		insertParam=new Object[9];
		queryParams=new Object[2];
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strSql="SELECT COUNT(*) AS counturl FROM act_re_url WHERE inid=? AND businkey=? AND issubmit=0";
		strCol="id,inid,processkey,businkey,task_ID,suggest,userId,userName,createDate";
		try {
			queryParams[0]=inid;
			queryParams[1]=businkey;
			list=commonService.queryListSql("wf", strSql, queryParams);
			if(null!=list && 1==list.size())
			{
				mapInfo=list.get(0);
				if(true=="1".equals(mapInfo.get("counturl").toString())){
					blInfo=true;
				}
			}
			strKey=commonService.getKeybyTabCol("wf", "act_hi_suggestion", "id");
			insertParam[0]=strKey;
			insertParam[1]=inid;
			insertParam[2]=processkey;
			insertParam[3]=businkey;
			if(false==blInfo)
			{
				insertParam[4]=taskid;
			}
			insertParam[5]=suggest;
			insertParam[6]=userId;
			insertParam[7]=userName;
			insertParam[8]=sdf.format(dtNow);
			commonService.insertObj("wf", "act_hi_suggestion", strCol, insertParam);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
	}
	
	@Override
	public void taskMoveChangeInfo(String taskid,String deftaskId,String type){
		Task taskInfo;
		Map<String, Object> mapInfo;
		TaskCommitCmd taskCommitCmd;
		taskInfo=taskService.createTaskQuery().taskId(taskid).singleResult();
		mapInfo=taskInfo.getTaskLocalVariables();
		taskCommitCmd=new TaskCommitCmd(taskid,deftaskId,type,mapInfo);
		managementService.executeCommand(taskCommitCmd);
	}
	
	
	@Override
	public void taskMoveBack(String inid){
		Map mapInfo;
		String strKey;
		Map mapRunInfo;
		Map mapOldInfo;
		List<Map> list;
		Object[] params;
		String strTaskId;
		String strUserId;
		List<Map> oldlist;
		List<Map> runlist;
		Object[] runParams;
		Object[] delParams;
		String strRunTaskId;
		String strOldTaskId;
		StringBuilder strSql;
		StringBuilder strRunSql;
		StringBuilder strSqlInfo;
		List<HistoricTaskInstance> listHistTaskstance;
		strTaskId="";
		strUserId="";
		params=new Object[2];
		delParams=new Object[2];
		strSql=new StringBuilder();
		strRunSql=new StringBuilder();
		strSqlInfo=new StringBuilder();
		strSql.append("SELECT DISTINCT task_def_key_ AS defKey ");
		strSql.append("FROM act_hi_taskinst ");
		strSql.append("WHERE proc_inst_id_ = ? ");
		strSql.append("AND act_hi_taskinst.task_def_key_ NOT IN ( ");
		strSql.append("SELECT task_def_key_ ");
		strSql.append("FROM act_ru_task ");
		strSql.append("WHERE proc_inst_id_=? ");
		strSql.append(") ");
		strSql.append("ORDER BY end_time_ DESC ");
		params[0]=inid;
		params[1]=inid;
		try {
			list=commonService.queryListSql("wf", strSql.toString(), params);
			if(0!=list.size())
			{
				strRunSql.append("SELECT id_ as id ");
				strRunSql.append("FROM act_ru_task ");
				strRunSql.append("WHERE proc_inst_id_=? ");
				runParams=new Object[]{
						inid
				};
				mapInfo=list.get(0);
				strKey=mapInfo.get("defKey").toString();
				listHistTaskstance=historyService.createHistoricTaskInstanceQuery().processInstanceId(inid).orderByHistoricTaskInstanceEndTime().desc().list();
				for(HistoricTaskInstance taskHistoric:listHistTaskstance){
					if(strKey.equals(taskHistoric.getTaskDefinitionKey())){
						strTaskId=taskHistoric.getId();
						strUserId=taskHistoric.getAssignee();
						break;
					}
				}
				if("".equals(strTaskId)==false && "".equals(strUserId)==false)
				{
					delParams[0]=strTaskId;
					delParams[1]=strUserId;
					strSqlInfo.append("DELETE FROM act_hi_suggestion ");
					strSqlInfo.append("WHERE task_id=? AND userId=? ");
					commonService.execNoListSql("wf", strSqlInfo.toString(), delParams);
				}
				oldlist=commonService.queryListSql("wf", strRunSql.toString(), runParams);
				if(null!=oldlist && 1==oldlist.size())
				{
					mapOldInfo=oldlist.get(0);
					strOldTaskId=mapOldInfo.get("id").toString();
					this.taskMoveChangeInfo(strOldTaskId, strKey, "SC_2017101910230001");
					runlist=commonService.queryListSql("wf", strRunSql.toString(), runParams);
					if(null!=runlist && 1==runlist.size())
					{
						mapRunInfo=runlist.get(0);
						strRunTaskId=mapRunInfo.get("id").toString();
						this.delegateTaskUsers(strRunTaskId, strUserId);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void taskMoveChange(String inid,String deftaskId,String userId){
		String taskId;
		Task taskItem;
		Map mapOldInfo;
		Map mapRunInfo;
		String taskNewId;
		List<Map> oldlist;
		List<Map> runlist;
		Object[] runParams;
		StringBuilder strRunSql;
		strRunSql=new StringBuilder();
		strRunSql.append("SELECT id_ as id ");
		strRunSql.append("FROM act_ru_task ");
		strRunSql.append("WHERE proc_inst_id_=? ");
		runParams=new Object[]{
			inid
		};
		try {
			oldlist=commonService.queryListSql("wf", strRunSql.toString(), runParams);
			if(null!=oldlist && 1==oldlist.size())
			{
				mapOldInfo=oldlist.get(0);
				taskId=mapOldInfo.get("id").toString();
				if(null!=taskId && null!=userId && false =="".equals(taskId) && false =="".equals(userId))
				{
					this.taskMoveChangeInfo(taskId, deftaskId, "SC_2017101910240001");
					runlist=commonService.queryListSql("wf", strRunSql.toString(), runParams);
					if(null!=runlist && 1==runlist.size())
					{
						mapRunInfo=runlist.get(0);
						taskNewId=mapRunInfo.get("id").toString();
						this.delegateTaskUsers(taskNewId, userId);
					}
				}
			}
		} catch (Exception e1) {
			log.error(e1.toString(),e1);
		}
	}
	
	@Override
	public void delegateTaskUsers(String taskIds,String userId){
		String taskId;
		String[] taskArrIds;
		if(null!=taskIds && "".equals(taskIds)==false)
		{
			taskArrIds=taskIds.split(",");
			for(int i=0;i<taskArrIds.length;i++)
			{
				taskId=taskArrIds[i];
				taskService.delegateTask(taskId, userId);
			}
		}
	}
	
	@Override
	public JSONArray getUserTaskDefinition(String initId){
		Task taskInfo;
		boolean blInfo;
		ActivityImpl desc;
		JSONArray jsonList;
		JSONObject jsonObj;
		List<Task> taskList;
		String processDefinitionId;
		ProcessDefinitionEntity def;
		List<ActivityImpl> activitiList;
		jsonList=new JSONArray();
		taskList=taskService.createTaskQuery().processInstanceId(initId).list();
		processDefinitionId=runtimeService.createProcessInstanceQuery().processInstanceId(initId).singleResult().getProcessDefinitionId();;
		def = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		activitiList=def.getActivities();
		for(int i=0;i<activitiList.size();i++){
			blInfo=false;
			desc=activitiList.get(i);
			jsonObj=new JSONObject();
			if(desc.getActivityBehavior() instanceof UserTaskActivityBehavior){
				jsonObj.put("id", desc.getId());
				jsonObj.put("name", desc.getProperty("name"));
				jsonObj.put("isMulti", false);
				for(int j=0;j<taskList.size();j++)
				{
					taskInfo=taskList.get(j);
					if(desc.getId().equals(taskInfo.getTaskDefinitionKey())==true){
						blInfo=true;
						break;
					}
				}
				jsonObj.put("isCurr", blInfo);
				jsonList.add(jsonObj);
			}
			if(desc.getActivityBehavior() instanceof MultiInstanceActivityBehavior){
				jsonObj.put("id", desc.getId());
				jsonObj.put("name", desc.getProperty("name"));
				jsonObj.put("isMulti", true);
				if(false==blInfo)
				{
					for(int j=0;j<taskList.size();j++)
					{
						taskInfo=taskList.get(j);
						if(desc.getId().equals(taskInfo.getTaskDefinitionKey())==true){
							blInfo=true;
							break;
						}
					}
				}
				jsonObj.put("isCurr", blInfo);
				jsonList.add(jsonObj);
			}
		}
		return jsonList;
	}
	
	@Override
	public void deleteProcessInstanceById(String inid,String deleteKey,String deleteReason){
		Object[] params;
		params=new Object[2];
		params[0]=2;
		params[1]=deleteReason;
		runtimeService.deleteProcessInstance(inid, deleteKey);
		try {
			commonService.updateObj("wf", "act_re_url", "issubmit,delReason", params, "and inid='"+inid+"'");
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
	}
	
	@Override
	public void suspendProcessInstanceById(String inid){
		runtimeService.suspendProcessInstanceById(inid);
	}
	
	@Override
	public void activateProcessInstanceById(String inid){
		runtimeService.activateProcessInstanceById(inid);
	}

}
