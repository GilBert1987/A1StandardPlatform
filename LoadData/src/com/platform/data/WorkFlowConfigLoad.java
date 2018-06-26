package com.platform.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.WorkflowService;

public class WorkFlowConfigLoad implements IFormDataLoad {
	
	private WorkflowService workflowService;
	private static Logger log = Logger.getLogger(WorkFlowConfigLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String inid;
		String strSql;
		JSONObject jsonTaskObj;
		JSONArray jsonTaskList;
		inid=request.getParameter("inid");
		workflowService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", request);
		jsonTaskList=workflowService.getUserTaskDefinition(inid);
		for(int i=0;i<jsonTaskList.size();i++)
		{
			jsonTaskObj=jsonTaskList.getJSONObject(i);
			if(true==jsonTaskObj.getBoolean("isCurr"))
			{
				request.setAttribute("_isMulti", jsonTaskObj.getBoolean("isMulti"));
				break;
			}
		}
	}
}
