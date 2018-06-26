package com.common.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.entity.activiti.WorkFlow;

public interface LocalWorkflowService {
	
	void startProcess(String processkey, String businessKey,HttpServletRequest request, HttpServletResponse response);

	Map getWorkFlowVariableMap(String deployId, HttpServletRequest request,HttpServletResponse response);
	
	void completeTaskWithCommentCmd(String strTkid, HttpServletRequest request,HttpServletResponse response);
	
	void redirectWF(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	WorkFlow getWFByActivitiConfig(String id); 
}
