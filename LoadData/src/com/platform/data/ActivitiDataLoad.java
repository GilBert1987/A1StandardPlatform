package com.platform.data;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.WorkflowService;

public class ActivitiDataLoad implements IFormDataLoad {

	private WorkflowService workflowService;
	private static Logger log = Logger.getLogger(ActivitiDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		boolean blUseMoBan;
		StringBuilder type;
		StringBuilder selobj;
		StringBuilder initData;
		String strId;
		String strJsinfo;
		String strTemp;
		String strBtnlist;
		String strHidlist;
		String strConlist;
		String strFormlist;
		String strTaginfo;
		String strRoleList;
		String strDynamicform;
		String strTaskformlist;
		String strTaskuserlist;
		String strWf_key;
		String strCategory;
		String strJsonInfo;
		JSONObject jsonObj;
		Map mapInfo;
		String strEndCode;
		String strStartCode;
		JSONObject jsonProperties;
		type=new StringBuilder();
		selobj=new StringBuilder();
		initData=new StringBuilder();
		strId=request.getParameter("id");
		strCategory=request.getParameter("typeid");
		jsonObj=null;
		mapInfo=null;
		blUseMoBan=false;
		workflowService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", request);
		try {
			if(null!=strId)
			{
				jsonObj=workflowService.openWFModel(strId);
			}
			else
			{
				//WM_2017103009590001模板
				blUseMoBan=true;
				jsonObj=workflowService.openWFModel("WM_2017103009590001");
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
			if(null!=jsonObj.getJSONObject("properties"))
			{
				jsonProperties=jsonObj.getJSONObject("properties");
				if(null!=jsonProperties)
				{
					strWf_key=jsonProperties.getString("process_id");
					if(null!=strWf_key && "".equals(strWf_key)==false)
					{
						try {
							if(true==blUseMoBan)
							{
								strWf_key="WM_2017103009590001";
								jsonProperties.put("process_id", "");
								mapInfo=workflowService.getWFFormInfoData(strWf_key, -2);
							}
							else
							{
								mapInfo=workflowService.getWFFormInfoData(strWf_key, -1);
							}
						} catch (Exception e) {
							log.error(e.getMessage(),e);
						}
						if(null!=mapInfo)
						{
							strJsinfo=mapInfo.get("jsinfo").toString();
							strTemp=mapInfo.get("temp").toString();
							strBtnlist=mapInfo.get("btnlist").toString();
							strHidlist=mapInfo.get("hidlist").toString();
							strConlist=mapInfo.get("conlist").toString();
							strFormlist=mapInfo.get("formlist").toString();
							strTaginfo=mapInfo.get("taginfo").toString();
							strRoleList=mapInfo.get("roleList").toString();
							strDynamicform=mapInfo.get("dynamicform").toString();
							strTaskformlist=mapInfo.get("taskformlist").toString();
							strTaskuserlist=mapInfo.get("taskuserlist").toString();
							strEndCode=mapInfo.get("endcode").toString();
							strStartCode=mapInfo.get("startcode").toString();
							strBtnlist=strBtnlist.replace("'", "&apos;");
							strBtnlist=strBtnlist.replace("\"", "&quot;");
							strBtnlist=strBtnlist.replace("\r", " ");
							strBtnlist=strBtnlist.replace("\n", " ");
							strBtnlist=strBtnlist.replace("\t", " ");
							strHidlist=strHidlist.replace("'", "&apos;");
							strHidlist=strHidlist.replace("\"", "&quot;");
							strHidlist=strHidlist.replace("\r", " ");
							strHidlist=strHidlist.replace("\n", " ");
							strHidlist=strHidlist.replace("\t", " ");
							strJsinfo=strJsinfo.replace("'", "&apos;");
							strJsinfo=strJsinfo.replace("\"", "&quot;");
							strJsinfo=strJsinfo.replace("\r", " ");
							strJsinfo=strJsinfo.replace("\n", " ");
							strJsinfo=strJsinfo.replace("\t", " ");
							strConlist=strConlist.replace("'", "&apos;");
							strConlist=strConlist.replace("\"", "&quot;");
							strConlist=strConlist.replace("\r", " ");
							strConlist=strConlist.replace("\n", " ");
							strConlist=strConlist.replace("\t", " ");
							strFormlist=strFormlist.replace("'", "&apos;");
							strFormlist=strFormlist.replace("\"", "&quot;");
							strFormlist=strFormlist.replace("\r", " ");
							strFormlist=strFormlist.replace("\n", " ");
							strFormlist=strFormlist.replace("\t", " ");
							strTaginfo=strTaginfo.replace("'", "&apos;");
							strTaginfo=strTaginfo.replace("\"", "&quot;");
							strTaginfo=strTaginfo.replace("\r", " ");
							strTaginfo=strTaginfo.replace("\n", " ");
							strTaginfo=strTaginfo.replace("\t", " ");
							strRoleList=strRoleList.replace("'", "&apos;");
							strRoleList=strRoleList.replace("\"", "&quot;");
							strRoleList=strRoleList.replace("\r", " ");
							strRoleList=strRoleList.replace("\n", " ");
							strRoleList=strRoleList.replace("\t", " ");
							strDynamicform=strDynamicform.replace("'", "&apos;");
							strDynamicform=strDynamicform.replace("\"", "&quot;");
							strDynamicform=strDynamicform.replace("\r", " ");
							strDynamicform=strDynamicform.replace("\n", " ");
							strDynamicform=strDynamicform.replace("\t", " ");
							strTaskformlist=strTaskformlist.replace("'", "&apos;");
							strTaskformlist=strTaskformlist.replace("\"", "&quot;");
							strTaskformlist=strTaskformlist.replace("\r", " ");
							strTaskformlist=strTaskformlist.replace("\n", " ");
							strTaskformlist=strTaskformlist.replace("\t", " ");
							strTaskuserlist=strTaskuserlist.replace("'", "&apos;");
							strTaskuserlist=strTaskuserlist.replace("\"", "&quot;");
							strTaskuserlist=strTaskuserlist.replace("\r", " ");
							strTaskuserlist=strTaskuserlist.replace("\n", " ");
							strTaskuserlist=strTaskuserlist.replace("\t", " ");
							request.setAttribute("jsinfo",strJsinfo);
							request.setAttribute("temp",strTemp);
							request.setAttribute("btnlist",strBtnlist);
							request.setAttribute("hidlist",strHidlist);
							request.setAttribute("conlist",strConlist);
							request.setAttribute("formlist",strFormlist);
							request.setAttribute("taginfo",strTaginfo);
							request.setAttribute("roleList",strRoleList);
							request.setAttribute("dynamicform",strDynamicform);
							request.setAttribute("taskformlist",strTaskformlist);
							request.setAttribute("taskuserlist",strTaskuserlist);
							request.setAttribute("startcode",strStartCode);
							request.setAttribute("endcode",strEndCode);
						}
					}
				}
			}
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
