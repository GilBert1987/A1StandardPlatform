package com.platform.data;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.WorkflowService;

public class ActivitiPicDataLoad implements IFormDataLoad {

	private WorkflowService workflowService;
	private CommonService commonService;
	private static Logger log = Logger.getLogger(ActivitiDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		List<Map> listMap;
		Map mapInfo;
		StringBuilder type;
		StringBuilder selobj;
		StringBuilder initData;
		StringBuilder sqlInfo;
		String strId;
		String strWFId;
		String strInid;
		String strCategory;
		String strJsonname;
		String strJsonInfo;
		JSONObject jsonObj;
		Object[] params;
		type=new StringBuilder();
		selobj=new StringBuilder();
		initData=new StringBuilder();
		sqlInfo=new StringBuilder();
		strWFId=request.getParameter("wfid");
		strInid=request.getParameter("inid");
		jsonObj=null;
		strCategory="";
		workflowService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", request);
		commonService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		try {
			if(null!=strWFId)
			{
				if(null==strInid || "".equals(strInid)==true)
				{
					params=new Object[]{
						strWFId
					};
					sqlInfo.append("SELECT act_ge_bytearray.DEPLOYMENT_ID_ AS id,act_ge_bytearray.NAME_ AS NAME ");
					sqlInfo.append("FROM act_ge_bytearray ");
					sqlInfo.append("JOIN act_re_procdef ");
					sqlInfo.append("ON act_re_procdef.DEPLOYMENT_ID_=act_ge_bytearray.DEPLOYMENT_ID_ ");
					sqlInfo.append("WHERE act_re_procdef.Key_=? ");
					sqlInfo.append("AND act_ge_bytearray.name_ LIKE '%.json' ");
					sqlInfo.append("ORDER BY act_re_procdef.version_ DESC");
				}
				else
				{
					params=new Object[]{
						strWFId,
						strInid
					};
					sqlInfo.delete(0, sqlInfo.length());
					sqlInfo.append("SELECT act_ge_bytearray.DEPLOYMENT_ID_ AS id,act_ge_bytearray.NAME_ AS NAME ");
					sqlInfo.append("FROM act_ge_bytearray  ");
					sqlInfo.append("JOIN act_re_url ");
					sqlInfo.append("ON act_re_url.DEPLOYMENTID=act_ge_bytearray.DEPLOYMENT_ID_ ");
					sqlInfo.append("WHERE act_re_url.processkey=? AND act_re_url.inid=? ");
					sqlInfo.append("AND act_ge_bytearray.name_ LIKE '%.json' ");
				}
				listMap=commonService.queryListSql("wf", sqlInfo.toString(),params);
				if(null!=listMap && 0!=listMap.size())
				{
					mapInfo=listMap.get(0);
					strId=mapInfo.get("id").toString();
					strJsonname=mapInfo.get("NAME").toString();
					jsonObj=workflowService.openWFRunData(strId,strJsonname);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		initData.append("{");
		initData.append("\"id\":\"\",\"name\":\"\",\"documentation\":\"\",");
		initData.append("\"executionlisteners\":\"\",\"process_id\":\"\",");
		initData.append("\"process_author\": \"\",\"process_executable\": \"Yes\",");
		initData.append("\"process_version\": \"\",\"process_namespace\": \""+(strCategory==null?"":strCategory)+"\"}");
		type.append("[");
		type.append("{\"id\":\"start\",\"src\":\"../../img/48/start_event_empty.png\",\"width\":35,\"height\":35},");
		type.append("{\"id\":\"userTask\",\"src\":\"../../img/48/task_empty.png\",\"width\":70,\"height\":35},");
		type.append("{\"id\":\"fork\",\"src\":\"../../img/48/gateway_exclusive.png\",\"width\":35,\"height\":35},");
		type.append("{\"id\":\"join\",\"src\":\"../../img/48/gateway_parallel.png\",\"width\":35,\"height\":35},");
		type.append("{\"id\":\"end\",\"src\":\"../../img/48/end_event_terminate.png\",\"width\":35,\"height\":35}");
		type.append("]");
		selobj.append("{\"from\":\"\",\"to\":\"\"}");
		if(null!=jsonObj){
			strJsonInfo=jsonObj.toString();
			strJsonInfo=strJsonInfo.replace("'", "&apos;");
			strJsonInfo=strJsonInfo.replace("\"", "&quot;");
			strJsonInfo=strJsonInfo.replace("\r", " ");
			strJsonInfo=strJsonInfo.replace("\n", " ");
			strJsonInfo=strJsonInfo.replace("\t", " ");
			request.setAttribute("initWFData", strJsonInfo);
		}
		request.setAttribute("type",type.toString());
		request.setAttribute("selobj",selobj.toString());
		request.setAttribute("initData", initData);
		request.setAttribute("lastModifyTime", System.currentTimeMillis());
	}
}