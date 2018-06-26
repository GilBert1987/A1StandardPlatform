package com.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.task.Task;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.runtime.ProcessInstance;

import com.common.entity.login.Organization;
import com.common.entity.login.Post;
import com.common.entity.login.Role;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WorkflowService {
	
	ProcessInstance getProcessInstanceByInid(String strInid);
	
	JSONObject openWFRunData(String deploymentId,String resourceName);

	String getFormKeyByTaskID(String strTaskKey);

	Boolean getControlTaskInfo(String taskId, String userId,List<Role> listRole, List<Organization> listOrg, List<Post> listPost);

	String getPostUser(String strAssignmentUserId);

	String getDeptUser(String strAssignmentUserId);

	String getUsersIdBySql(String strJsonSql);

	String getDeptPostUser(String strAssignmentUserId);

	String getRoleUser(String strAssignmentUserId);

	String expreeString(String strExpree, Map<String, Object> variables);
	
	String createBussinKey();

	List<String> getMultipleUser(String strUserTaskId,ActivityExecution execution) throws Exception;

	void completeTaskWithCommentCmd(String strTkid,Map<String, Object> variables);

	String getDeploymentIdByTaskId(String strTkid);

	String saveWFModel(String id, String key, String name, String description,
			String jsonXml) throws JsonProcessingException, IOException;

	String deployWFModel(String id, String key, String name,
			String description, String jsonXml) throws JsonProcessingException,
			IOException;

	String getJsonInfoByDeployId(String strDeployId);
	
	String delDeployment(String deployId);

	String deleteModel(String modelId);

	void updateFirstUserLink(String bussinKey, String strUserId);

	
	//--------------------------------------------------------------------------
	
	String getDeploymentIdByProcesskey(String processkey);
	
	Map saveWFModelByMap(Map mapInfo);
	
	Map deployWFModelByRedisKey(String key);
	
	Map delDeploymentByMap(String deployId,Map mapInfo);
	
	Map deleteModelByMap(String modelId,Map mapInfo);
	
	void delAllDeployment(String deployId,Boolean blInfo);
	
	void saveDeployWFData(String deployId,String resourceName,String jsonXml);
	
	Map saveBusinessKey(Map mapInfo);
	
	void saveWFUrlInfo(String strPath,String userId,String strListTask,Map mapInfo);
	
	String startProcess(String strDeploymentId, String userId,String businessKey, Map<String,Object> variables);
	
	ProcessInstance startWorkflow(String processDefinitionId,String businessKey, String strUserId, Map<String, Object> variables);
	
	List<Task> getCurrTask(ProcessInstance instance);
	
	void delRunWF(String processInstanceId, String userId);
	
	JSONObject openWFModel(String modelId) throws UnsupportedEncodingException;
	
	Map getWFFormInfoData(String strKey,Integer ver) throws UnsupportedEncodingException;

	void saveDeployWFByRedisKey(String rediskey);
	
	void taskClaim(String taskId,String userId);

	String getNextUserTaskInfo(String init, String taskId, Boolean blCurr);

	Map<String, Object> getRunVariablesByProcess(String init);
	
	void saveSuggestion(String inid,String processkey,String businkey,String taskid,String suggest,String userId,String userName);
	
	void taskMoveBack(String inid);

	void delegateTaskUsers(String taskIds, String userId);

	JSONArray getUserTaskDefinition(String initId);

	void taskMoveChange(String inid,String deftaskId,String userId);

	void suspendProcessInstanceById(String inid);

	void activateProcessInstanceById(String inid);

	void taskMoveChangeInfo(String taskid, String deftaskId, String type);

	void deleteProcessInstanceById(String inid, String deleteKey,String deleteReason);
	
}
