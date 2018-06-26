package com.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.entity.query.Query;
import com.common.entity.query.Quick;
import com.common.entity.query.Title;
import com.common.entity.tree.TreeButton;
import com.common.entity.tree.TreeHtml;
import com.common.entity.tree.TreeNode;
import com.common.entity.tree.TreeSqlInfo;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.service.QueueSenderService;
import com.common.service.WorkflowService;
import com.common.tool.XmlUnit;
import com.common.unit.ClassUnit;
import com.common.unit.HtmlUnit;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	private static Logger log = Logger.getLogger(AjaxController.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private WorkflowService workflowService;
	
	@Resource
	private LocalCommonService localcommService;
	
	@Resource
	private CoreService coreService;
	
	@Resource
	private QueueSenderService queueSender;
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/query/search", method={RequestMethod.POST})
	public @ResponseBody JSONArray searchName(HttpServletRequest request) {
		List<Map> resultMap;
		String strType;
		String strInfo;
		String strDataName;
		strType=request.getParameter("type");
		strInfo=request.getParameter("info");
		strDataName=request.getParameter("dataname");
		resultMap=null;
		if("sql".equals(strType)&& "".equals(strInfo)==false){
			try {
				strInfo=XmlUnit.getDeXmlString(strInfo);
				resultMap=commonService.queryListSql(strDataName,strInfo,null);
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
		}
		return JSONArray.fromObject(resultMap);
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/jarfile/search", method={RequestMethod.POST})
	public @ResponseBody JSONArray getTldList(String jarfile) {
		return ClassUnit.getTldFilePackage(jarfile);
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/createKey", method={RequestMethod.POST})
	public @ResponseBody JSONObject createKey(String sysName,String table,String colInfo)
	{
		String id;
		String strResult;
		JSONObject jsonObj;
		id="";
		jsonObj=null;
		try {
			id=commonService.getKeybyTabCol(sysName, table, colInfo);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		strResult = "{\"msg\":\""+id+"\"}";
		jsonObj=JSONObject.fromObject(strResult);
		return jsonObj;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/tempSave", method={RequestMethod.POST})
	public @ResponseBody JSONObject tempSave(String info)
	{
		String id;
		String strResult;
		JSONObject jsonObj;
		id="";
		jsonObj=null;
		try {
			id=commonService.saveRedisObj(info);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		strResult = "{\"id\":\""+id+"\"}";
		jsonObj=JSONObject.fromObject(strResult);
		return jsonObj;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/tempLoad", method={RequestMethod.POST})
	public @ResponseBody String tempLoad(String id)
	{
		String strResult;
		id="";
		strResult="";
		try {
			strResult=commonService.loadRedisObj(id).toString();
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		return strResult;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/existKey", method={RequestMethod.POST})
	public @ResponseBody Boolean existKey(String sysName,String table,String key,String value,String perKey,String perValue)
	{
		Boolean blInfo;
		blInfo=false;
		try {
			blInfo=commonService.existKey(sysName,table,key,value,perKey,perValue);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		return blInfo;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/workflow/checkkey", method={RequestMethod.POST})
	public @ResponseBody JSONObject checkKey(String key,String id)
	{
		List<Map> list;
		JSONObject jsonObj;
		String strSql;
		String strJsonInfo;
		Object[] params;
		list=null;
		strJsonInfo="";
		if("".equals(key)==false)
		{
			params=new Object[]{
				key,
				id
			};
			strSql="SELECT ID_ AS id FROM act_re_model WHERE key_=? and id_!=?";
			try {
				list=commonService.queryListSql("wf", strSql,params);
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
			if(list.size()>0)
			{
				strJsonInfo="{\"msg\":\"0\"}";
			}
			else
			{
				strJsonInfo="{\"msg\":\"1\"}";
			}
		}
		else
		{
			strJsonInfo="{\"msg\":\"0\"}";
		}
		jsonObj=JSONObject.fromObject(strJsonInfo);
		return jsonObj;
	}
	
	
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/query/exportExcel", method={RequestMethod.POST})
	public @ResponseBody JSONObject exportExcel(HttpServletRequest request,HttpServletResponse response) {
		int iSum;
		Quick quick;
		Date dateNow;
		int iColCount;
		String strMsg;
		String strKey;
		String strDate;
		Query queryObj;
		Title titleObj;
		String strType;
		int iExtendSum;
		String viewpath;
		String strWhere;
		int iExtendCount;
		Map mapTitleInfo;
		String strStatus;
		String strRetUrl;
		String strCodeKey;
		String strSqlInfo;
		String strSendMsg;
		String strQueryKey;
		String strTitleKey;
		String strReqValue;
		String strReqExpree;
		String strDataSysId;
		String strTitleSysId;
		SimpleDateFormat sdf;
		String strQuickWhere;
		List<Map> listSqlMap;
		String strFileSqlInfo;
		List<Map> listFileMap;
		String strDataSqlInfo;
		String strTitleSqlInfo;
		List<Map> listTitleMap;
		List<Object> listParam;
		Object[] insertObjParams;
		JSONObject jsonResultObj;
		List<Map> listTitleDataMap;
		Object[] queryConfigParams;
		StringBuilder strParamInfo;
		StringBuilder strGroupInfo;
		StringBuilder strSendBuilder;
		StringBuilder strSqlInfoList;
		Map<String, Object> mapRetList;
		Map<String, Object> queryConfig;
		iColCount=0;
		viewpath="";
		strWhere="";
		strMsg="";
		strKey="";
		iSum=0;
		iExtendSum=0;
		iExtendCount=0;
		dateNow=new Date();
		jsonResultObj=null;
		listTitleMap=new ArrayList<Map>();
		strParamInfo=new StringBuilder();
		strGroupInfo=new StringBuilder();
		strSqlInfoList=new StringBuilder();
		strSendBuilder=new StringBuilder();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		listParam=new ArrayList<Object>();
		strDate=sdf.format(dateNow);
		if(null!=request.getParameter("viewpath"))
		{
			viewpath=request.getParameter("viewpath").toString();
		}
		if("".equals(viewpath)==false)
		{
			strCodeKey="";
			if(null!=request.getParameter("exportExcelKey"))
			{
				strCodeKey=request.getParameter("exportExcelKey").toString();
			}
			strRetUrl=viewpath+".query";
			strFileSqlInfo="SELECT path,currname,type,status,createdate FROM sc_fileinfo WHERE id='"+strCodeKey+"'";
			strSqlInfo="select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
			queryConfigParams=new Object[]{viewpath};
			try {
				listFileMap=commonService.queryListSql("com", strFileSqlInfo, null);
				if(null==listFileMap || 0==listFileMap.size())
				{
					/*
					insertObjParams=new Object[6];
					insertObjParams[0]=strCodeKey;
					insertObjParams[1]=viewpath;
					insertObjParams[2]=strCodeKey+".bin";
					insertObjParams[3]=1;
					insertObjParams[4]=0;
					insertObjParams[5]=strDate;
					commonService.insertObj("com", "sc_fileinfo", "id,path,currname,type,status,createdate", insertObjParams);
					queryConfig=commonService.queryFullSql("com", strSqlInfo,queryConfigParams);
					listSqlMap=(List<Map>)queryConfig.get("retMap");
					if(0==listSqlMap.size())
					{
						queryObj = getQueryByRequest(request,strRetUrl);
					}
					else
					{
						queryObj = getQueryBySqlRequest(request,listSqlMap.get(0));
					}
					if(request.getParameter("querySearchName")!=null && "".equals(request.getParameter("querySearchName"))==false &&request.getParameter("querySearchExpree")!=null && "".equals(request.getParameter("querySearchExpree"))==false && request.getParameter("querySearchValue")!=null)
					{
						if("like".equals(request.getParameter("querySearchExpree").toString())==true || "between".equals(request.getParameter("querySearchExpree").toString())==true)
						{
							if("like".equals(request.getParameter("querySearchExpree").toString())==true)
							{
								strWhere =" and ("+request.getParameter("querySearchName").toString()+" like ?)";
								listParam.add("%"+request.getParameter("querySearchValue").toString()+"%");
							}
						}
						else
						{
							strWhere =" and ("+request.getParameter("querySearchName").toString()+" "+request.getParameter("querySearchExpree").toString()+" ?)";
							listParam.add(request.getParameter("querySearchValue").toString());
						}
					}
					request.setAttribute("_querySearchName", request.getParameter("querySearchName"));
					request.setAttribute("_querySearchExpree", request.getParameter("querySearchExpree"));
					request.setAttribute("_querySearchValue", request.getParameter("querySearchValue"));
					request.setAttribute("begin_datetime", request.getParameter("begin_datetime"));
					request.setAttribute("end_datetime", request.getParameter("end_datetime"));
					strGroupInfo.append("[");
					strParamInfo.append("[");
					for(Column colObj : queryObj.getColumns()){
						quick=colObj.getQuick();
						titleObj=colObj.getTitle();
						strReqExpree="";
						if("1".equals(quick.getIsshow())==true){
							strQuickWhere =" and ("+colObj.getKey()+" ";
							strReqValue=request.getParameter(colObj.getKey()+"value");
							if("1".equals(quick.getShowoperation())==true){
								strReqExpree=request.getParameter(colObj.getKey()+"expree");
								if("like".equals(strReqExpree) || "between".equals(strReqExpree)){
									if("like".equals(strReqExpree)){
										strQuickWhere +="like ?)";
									}
									if("between".equals(strReqExpree)){
										strQuickWhere +="=?)";
									}
								}
								else
								{
									strQuickWhere +=strReqExpree+"?)";
								}
							}
							else
							{
								strQuickWhere +="=?)";
							}
							if("".equals(strReqValue)==false){
								strWhere += strQuickWhere;
								if("like".equals(strReqExpree)==true){
									strReqValue="%"+strReqValue+"%";
								}
								listParam.add(strReqValue);
							}
						}
						if("1".equals(titleObj.getIsshow()) && "0".equals(colObj.getTypeid()) && false=="0".equals(colObj.getGrouptype()))
						{
							if(0!=iSum)
							{
								strGroupInfo.append(",");
							}
							strGroupInfo.append("{\"key\":\""+colObj.getKey()+"\",\"group\":"+colObj.getGrouptype()+"}");
							iSum++;
						}
						if("0".equals(colObj.getTypeid())==true || null == colObj.getTypeid()){
							mapTitleInfo=new HashMap<String,Object>();
							mapTitleInfo.put("id", colObj.getKey());
							mapTitleInfo.put("title", colObj.getName());
							mapTitleInfo.put("isshow", colObj.getTitle().getIsshow());
							mapTitleInfo.put("align", colObj.getTitle().getAlign());
							listTitleMap.add(mapTitleInfo);
						}
						if("1".equals(colObj.getTypeid())==true){
							strKey="";
							strTitleSysId=colObj.getTitleSysId();
							strTitleSqlInfo=colObj.getTitleSqlInfo();
							strDataSysId = colObj.getDataSysId();
							strDataSqlInfo = colObj.getDataSqlInfo();
							strDataSqlInfo=this.getSqlInfo(strDataSqlInfo, request, response);
							strTitleSqlInfo=this.getSqlInfo(strTitleSqlInfo, request, response);
							try {
								strKey=commonService.queryBigSql(strTitleSysId, strTitleSqlInfo, null);
							} catch (Exception e) {
								log.error(e.toString(),e);
							}
							listTitleDataMap=localcommService.loadMapByReadisKey(strKey);
							for(Map mapData:listTitleDataMap){
								if(null != mapData.get("value") && null != mapData.get("code"))
								{
									mapTitleInfo=new HashMap<String,Object>();
									mapTitleInfo.put("id", colObj.getKey()+"_"+mapData.get("code").toString());
									mapTitleInfo.put("title", mapData.get("value").toString());
									mapTitleInfo.put("isshow", titleObj.getIsshow());
									mapTitleInfo.put("align", titleObj.getAlign());
									listTitleMap.add(mapTitleInfo);
								}
							}
							try {
								strKey=commonService.queryBigSql(strDataSysId, strDataSqlInfo, null);
							} catch (Exception e) {
								log.error(e.toString(),e);
							}
							if(0!=iExtendSum)
							{
								strParamInfo.append(",");
							}
							strParamInfo.append("{\"key\":\""+colObj.getKey()+"\",\"paramKey\":\""+strKey+"\"}");
							iExtendSum++;
						}
					}
					strParamInfo.append("]");
					strGroupInfo.append("]");
					strQueryKey=commonService.saveRedisObj(queryObj);
					strTitleKey=commonService.saveRedisObj(listTitleMap);
					strSqlInfoList.append("select * from ( ");
					strSqlInfoList.append(getSqlInfo(queryObj.getSqlinfo(),request,response));
					strSqlInfoList.append(") as t ");
					strSendBuilder.append("{");
					strSendBuilder.append("\"queryid\":\""+strQueryKey+"\",");
					strSendBuilder.append("\"titleid\":\""+strTitleKey+"\",");
					strSendBuilder.append("\"key\":\""+strCodeKey+"\",");
					strSendBuilder.append("\"code\":\""+viewpath+"\",");
					strSendBuilder.append("\"sysid\":\""+queryObj.getSysid()+"\",");
					strSendBuilder.append("\"datatype\":\""+queryObj.getDatatype()+"\",");
					strSendBuilder.append("\"sql\":\""+strSqlInfoList.toString().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
					strSendBuilder.append("\"where\":\""+strWhere.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
					strSendBuilder.append("\"orderBy\":\""+queryObj.getOrderkey().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
					strSendBuilder.append("\"listParams\":[");
					for(int i=0;i<listParam.size();i++)
					{
						if(0!=i)
						{
							strSendBuilder.append(",");
						}
						strSendBuilder.append("\""+listParam.get(i).toString()+"\"");
					}
					strSendBuilder.append("],");
					strSendBuilder.append("\"extend\":"+strParamInfo.toString()+",");
					strSendBuilder.append("\"group\":"+strGroupInfo.toString()+",");
					strSendBuilder.append("\"extendcol\":[");
					for(Column colObj : queryObj.getColumns()){
						if(0!=iColCount)
						{
							strSendBuilder.append(",");
						}
						strSendBuilder.append("{\"key\":\""+colObj.getKey()+"\",\"sqlinfo\":\""+getSqlInfo(colObj.getTitleSqlInfo(),request,response)+"\"}");
						iColCount++;
					}
					strSendBuilder.append("],");
					strSendBuilder.append("\"extendcountcol\":[");
					for(Column colObj : queryObj.getColumns()){
						if(0!=iExtendCount)
						{
							strSendBuilder.append(",");
						}
						strSendBuilder.append("{\"key\":\""+colObj.getKey()+"\",\"sqlinfo\":\""+getSqlInfo(colObj.getDataSqlInfo(),request,response)+"\"}");
						iExtendCount++;
					}
					strSendBuilder.append("]");
					strSendBuilder.append("}");
					strSendMsg=strSendBuilder.toString();
					queueSender.send("export.excelNo1", strSendMsg);
					strMsg="{\"type\":1,\"status\":0,\"msg\":\"正在生成数据!\"}";
					jsonResultObj=JSONObject.fromObject(strMsg);
					*/
					strMsg="{\"type\":1,\"status\":0,\"msg\":\"正在生成数据!\"}";
					jsonResultObj=JSONObject.fromObject(strMsg);
				}
				else
				{
					if(1==listFileMap.size())
					{
						mapRetList=listFileMap.get(0);
						strType=mapRetList.get("type").toString();
						strStatus=mapRetList.get("status").toString();
						if("0".equals(strType) && "-1".equals(strStatus))
						{
							strMsg="{\"type\":0,\"status\":-1,\"msg\":\"数据导出失败!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("1".equals(strType) && "0".equals(strStatus))
						{
							strMsg="{\"type\":1,\"status\":0,\"msg\":\"正在生成数据!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("1".equals(strType) && "1".equals(strStatus))
						{
							strMsg="{\"type\":1,\"status\":1,\"msg\":\"数据生成完毕,触发数据加载!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("2".equals(strType) && "0".equals(strStatus))
						{
							strMsg="{\"type\":2,\"status\":0,\"msg\":\"数据加载中!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("2".equals(strType) && "1".equals(strStatus))
						{
							strMsg="{\"type\":2,\"status\":1,\"msg\":\"数据加载完毕!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("3".equals(strType) && "0".equals(strStatus))
						{
							strMsg="{\"type\":3,\"status\":0,\"msg\":\"准备数据转换!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("3".equals(strType) && "1".equals(strStatus))
						{
							strMsg="{\"type\":3,\"status\":1,\"msg\":\"数据转换完毕!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("4".equals(strType) && "0".equals(strStatus))
						{
							strMsg="{\"type\":4,\"status\":0,\"msg\":\"生成Excel中!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("4".equals(strType) && "1".equals(strStatus))
						{
							strMsg="{\"type\":4,\"status\":1,\"msg\":\"生成Excel完毕!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("5".equals(strType) && "0".equals(strStatus))
						{
							strMsg="{\"type\":5,\"status\":0,\"msg\":\"Excel生成完毕,正在保存Ftp!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
						if("5".equals(strType) && "1".equals(strStatus))
						{
							strMsg="{\"type\":5,\"status\":1,\"msg\":\"保存Ftp完毕!\"}";
							jsonResultObj=JSONObject.fromObject(strMsg);
						}
					}
				}
			} catch (Exception e) {
				log.error("-----------------exportExcel Error--------------------------");
				log.error("query:"+viewpath);
				log.error("sqlInfo:"+strSqlInfo);
				log.error(e.toString(),e);
				log.error("-----------------exportExcel Error End--------------------------");
			}
		}
		return jsonResultObj;
	}
		
	private String getSqlInfo(String sqlinfo, HttpServletRequest request,HttpServletResponse response) {
		sqlinfo=HtmlUnit.getValueByRequest(sqlinfo, request, response);
		return sqlinfo;
	}
	
	private Query getQueryBySqlRequest(HttpServletRequest requestToExpose,Map map) {
		Query query;
		byte[] bytes;
		Integer type;
		String strEncodeMap;
		bytes=(byte[])map.get("bytesinfo");
		type=(Integer)map.get("type");
		strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
		if ((type != null) && (type.intValue() != 0))
	    {
			strEncodeMap = coreService.readEncryptSql(strEncodeMap);
	    }
		query = localcommService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
		return query;
	}
	
	private Query getQueryByRequest(HttpServletRequest request,String retUrl)  throws Exception {
		Query query;
		query = localcommService.formatXmltoQuery(localcommService.loadInit(request.getSession().getServletContext().getRealPath(retUrl)));
		return query;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/query/delete", method={RequestMethod.POST})
	public @ResponseBody JSONObject delTable(HttpServletRequest request) {
		String strTableName;
		String strKey;
		String strValue;
		String strDataName;
		strDataName=request.getParameter("dataname");
		strTableName=request.getParameter("table");
		strKey=request.getParameter("key");
		strValue=request.getParameter("value");
		try {
			commonService.deleteObj(strDataName, strTableName, strKey, strValue);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		return JSONObject.fromObject("{\"msg\":\"sucess\"}");
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/tree/data", method={RequestMethod.POST})
	public @ResponseBody JSONArray gettreedata(HttpServletRequest request) {
		Integer iLevel;
		String strCheckId;
		String strNode;
		String strData;
		String strUrl;
		String strPath;
		TreeNode treeNode;
		TreeNode treeNext;
		TreeButton[] buttons;
		TreeHtml trcurr;
		JSONObject jsonInfo;
		JSONArray jsonArr;
		JSONArray jsonDataArr;
		JSONArray jsonButtonArr;
		List<TreeButton> buttonlist;
		List<TreeNode> nodelist;
		List<TreeHtml> htmllist;
		List<TreeHtml> resultlist;
		Map<String,String> mapparam;
		strCheckId=request.getParameter("id");
		strNode=request.getParameter("node");
		strData=request.getParameter("data");
		strUrl=request.getParameter("url");
		strPath=request.getParameter("path");
		jsonArr=JSONArray.fromObject(strNode);
		jsonDataArr = JSONArray.fromObject(strData);
		nodelist=new ArrayList<TreeNode>();
		htmllist=new ArrayList<TreeHtml>(); 
		resultlist=new ArrayList<TreeHtml>();
		trcurr=null;
		treeNext=null;
		iLevel=0;
		mapparam=(Map<String,String>)JSONObject.fromObject(strUrl);
		for(Object obj:jsonArr)
		{
			buttonlist=new ArrayList<TreeButton>();
			jsonInfo=JSONObject.fromObject(obj);
			treeNode=(TreeNode)JSONObject.toBean(jsonInfo, TreeNode.class);
			jsonButtonArr=JSONArray.fromObject(((JSONObject)obj).get("listButton"));
			buttons=(TreeButton[])JSONArray.toArray(jsonButtonArr,TreeButton.class);
			for(int i=0;i<buttons.length;i++)
			{
				buttonlist.add(buttons[i]);
			}
			treeNode.setListButton(buttonlist);
			nodelist.add(treeNode);
		}
		for(Object obj:jsonDataArr)
		{
			jsonInfo=JSONObject.fromObject(obj);
			htmllist.add((TreeHtml)JSONObject.toBean(jsonInfo, TreeHtml.class));
		}
		for(TreeHtml treeobj:htmllist)
		{
			if(strCheckId.equals(treeobj.getId()))
			{
				trcurr=treeobj;
				break;
			}
		}
		if(trcurr!=null){
			iLevel=Integer.parseInt("".equals(trcurr.getLevel())==true?"0":trcurr.getLevel());
			for(TreeNode nodeInfo:nodelist){
				if(nodeInfo.getLevel().equals(iLevel.toString())==true){
					treeNext=nodeInfo;
					break;
				}
			}
			if(treeNext!=null && "1".equals(treeNext.getType())==true){
				setTreeHtmlList(resultlist,treeNext,nodelist,strCheckId,mapparam,strPath);
			}
			if(resultlist.size()==0)
			{
				iLevel=Integer.parseInt("".equals(trcurr.getLevel())==true?"0":trcurr.getLevel());
				iLevel=iLevel+1;
				for(TreeNode nodeInfo:nodelist){
					if(nodeInfo.getLevel().equals(iLevel.toString())==true){
						treeNext=nodeInfo;
						break;
					}
				}
				if(treeNext!=null){
					setTreeHtmlList(resultlist,treeNext,nodelist,strCheckId,mapparam,strPath);
				}
			}
		}
		return JSONArray.fromObject(resultlist);
	}

	private void setTreeHtmlList(List<TreeHtml> resultlist, TreeNode nodeInfo,List<TreeNode> nodelist, String strRoot, Map<String, String> mapparam, String strPath) {
		int iLevel;
		long lResult;
		Integer iNextLevel;
		TreeHtml treeHtml;
		TreeSqlInfo treeSql;
		TreeNode nodeNext;
		String strSql;
		String strValue;
		String strReValue;
		String strUrl;
		List<Map> resultMap;
		nodeNext=null;
		resultMap=null;
		treeSql=nodeInfo.getSqlInfo();
		iLevel=Integer.parseInt("".equals(nodeInfo.getLevel())==true?"0":nodeInfo.getLevel());
		if("sql".equals(treeSql.getType())==true){
			strSql=getSqlInfo(treeSql.getSqlInfo(),mapparam,strRoot);
			try {
				resultMap=commonService.queryListSql(treeSql.getDatabase(),strSql,null);
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
			for(Map<String, Object> hMap:resultMap)
			{
				treeHtml=new TreeHtml();
				treeHtml.setIcon(nodeInfo.getIcon());
				treeHtml.setIconClose(nodeInfo.getIconClose());
				treeHtml.setIconOpen(nodeInfo.getIconOpen());
				treeHtml.setIconSkin(nodeInfo.getCss());
				treeHtml.setLevel(nodeInfo.getLevel());
				treeHtml.setName(nodeInfo.getNodetext());
				treeHtml.setId(nodeInfo.getNodeid());
				treeHtml.setPid(strRoot);
				treeHtml.setClick(nodeInfo.getSelfun());
				treeHtml.setUrl(nodeInfo.getUrl());
				if("".equals(nodeInfo.getTarget())==false)
				{
					treeHtml.setTarget(nodeInfo.getTarget());
				}
				else{
					treeHtml.setTarget(strPath+"_Main");
				}
				treeHtml.setOpen(false);
				for (Map.Entry<String, Object> entry: hMap.entrySet()) {
					strValue="${data."+entry.getKey()+"}";
					strReValue=treeHtml.getName();
					if(strReValue.indexOf(strValue)>-1){
						strReValue=strReValue.replace(strValue, entry.getValue().toString());
						treeHtml.setName(strReValue);
					}
					strReValue=treeHtml.getUrl();
					if(strReValue.indexOf(strValue)>-1){
						strReValue=strReValue.replace(strValue, entry.getValue().toString());
						treeHtml.setUrl(strReValue);
					}
					strReValue=treeHtml.getClick();
					if(strReValue.indexOf(strValue)>-1){
						strReValue=strReValue.replace(strValue, entry.getValue().toString());
						treeHtml.setClick(strReValue);
					}
					strReValue=treeHtml.getId();
					if(strReValue.indexOf(strValue)>-1){
						strReValue=strReValue.replace(strValue, entry.getValue().toString());
						treeHtml.setId(strReValue);
					}
				}
				if("1".equals(nodeInfo.getType())==true){
					nodeNext=nodeInfo;
				}
				lResult=0;
				if(nodeNext!=null)
				{
					lResult=getChildCount(nodeNext,mapparam,treeHtml.getId());
				}
				if(lResult==0)
				{
					iNextLevel=iLevel+1;
					for(TreeNode nodecurr:nodelist){
						if(nodecurr.getLevel().equals((iNextLevel).toString())==true){
							nodeNext=nodecurr;
							break;
						}
					}
					if(nodeNext!=null)
					{
						lResult=getChildCount(nodeNext,mapparam,treeHtml.getId());
					}
				}
				if(lResult==0)
				{
					treeHtml.setIsParent(false);
				}else{
					treeHtml.setIsParent(true);
				}
				strUrl=treeHtml.getUrl();
				if(null!=strUrl){
					for(Map.Entry<String, String> entry:mapparam.entrySet()){
						
						strUrl=strUrl.replace("${param."+entry.getKey()+"}", entry.getValue());
					}
					treeHtml.setUrl(strUrl);
				}
				resultlist.add(treeHtml);
			}
		}
	}
	
	private long getChildCount(TreeNode nodeNext, Map<String,String> mapparam, String id) {
		long lResult;
		String strSql;
		TreeSqlInfo treeSql;
		lResult=0;
		treeSql=nodeNext.getSqlInfo();
		if(treeSql!=null && "sql".equals(treeSql.getType())==true){
			strSql=getSqlInfo(treeSql.getSqlInfo(),mapparam,id);
			try {
				lResult=commonService.queryCountSql(treeSql.getDatabase(),strSql,null);
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
		}
		return lResult;
	}
	
	private String getSqlInfo(String sqlinfo, Map<String,String> mapparam, String strRoot) {
		for(Map.Entry<String, String> entry:mapparam.entrySet()){
			sqlinfo=sqlinfo.replace("${param."+entry.getKey()+"}", entry.getValue());
		}
		sqlinfo=sqlinfo.replace("${tree.parent}", strRoot);
		return sqlinfo;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/getsystemproperty", method={RequestMethod.POST})
	public @ResponseBody JSONArray getSystemProperty(String sysid,String property)
	{
		String strCCID;
		String strCCName;
		String strCCIndex;
		String strCCRemark;
		String strCCParentID;
		String strCCShortName;
		String strSubCount;
		List<Map> listMap;
		Map mapInfo;
		StringBuilder strInfo;
		StringBuilder strBuilder;
		Object[] params;
		listMap=null;
		strBuilder=new StringBuilder();
		strInfo=new StringBuilder();
		strInfo.append("SELECT sc_commoncode.CC_ID,sc_commoncode.CC_Name,sc_commoncode.CC_Index,sc_commoncode.CC_ShortName,");
		strInfo.append("sc_commoncode.CC_Remark,sc_commoncode.CC_ParentID,COUNT(subcommoncode.CC_ID) AS subcount ");
		strInfo.append("FROM sc_commoncode ");
		strInfo.append("LEFT JOIN sc_commoncode AS subcommoncode ");
		strInfo.append("ON sc_commoncode.CC_ID=subcommoncode.CC_ParentID ");
		strInfo.append("WHERE sc_commoncode.CCT_ID=? ");
		strInfo.append("GROUP BY sc_commoncode.CC_ID,sc_commoncode.CC_Name,sc_commoncode.CC_Index,sc_commoncode.CC_ShortName,sc_commoncode.CC_Remark,sc_commoncode.CC_ParentID ");
		strInfo.append("ORDER BY sc_commoncode.CC_ID,sc_commoncode.CC_Index ");
		params=new Object[]{
			property
		};
		try {
			listMap=commonService.queryListSql(sysid, strInfo.toString(),params);
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		strBuilder.append("[");
		for(int i=0;i<listMap.size();i++)
		{
			strCCID="";
			strCCName="";
			strCCIndex="";
			strCCRemark="";
			strCCParentID="";
			strCCShortName="";
			strSubCount="";
			mapInfo=listMap.get(i);
			if(null!=mapInfo.get("CC_ID")){
				strCCID=mapInfo.get("CC_ID").toString();
			}
			if(null!=mapInfo.get("CC_ParentID")){
				strCCParentID=mapInfo.get("CC_ParentID").toString();
			}
			if(null!=mapInfo.get("CC_Name")){
				strCCName=mapInfo.get("CC_Name").toString();
			}
			if(null!=mapInfo.get("CC_Index")){
				strCCIndex=mapInfo.get("CC_Index").toString();
			}
			if(null!=mapInfo.get("CC_ShortName")){
				strCCShortName=mapInfo.get("CC_ShortName").toString();
			}
			if(null!=mapInfo.get("CC_Remark")){
				strCCRemark=mapInfo.get("CC_Remark").toString();
			}
			if(null!=mapInfo.get("subcount")){
				strSubCount=mapInfo.get("subcount").toString();
			}
			strBuilder.append("{");
			strBuilder.append("\"id\":\""+strCCID.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"name\":\""+strCCName.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"order\":\""+strCCIndex.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"shortname\":\""+strCCShortName.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"remark\":\""+strCCRemark.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"pid\":\""+strCCParentID.replaceAll("\"", "\\\\\"")+"\",");
			strBuilder.append("\"subcount\":\""+strSubCount.replaceAll("\"", "\\\\\"")+"\"");
			strBuilder.append("}");
			if(i!=listMap.size()-1)
			{
				strBuilder.append(",");
			}
		}
		strBuilder.append("]");
		return JSONArray.fromObject(strBuilder.toString());
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/interface/search", method={RequestMethod.POST})
	public @ResponseBody JSONArray getJarList(String jarfile) {
		return ClassUnit.getInterFacePackage(jarfile);
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/method/search", method={RequestMethod.POST})
	public @ResponseBody JSONObject getMethodInfo(String id) {
		StringBuilder strSql;
		StringBuilder strParamSql;
		StringBuilder strJsonInfo;
		List<Map> listMap;
		List<Map> listParamMap;
		Map mapInfo;
		Map mapParamInfo;
		Object[] params;
		JSONObject jsonObj;
		jsonObj=null;
		listMap=null;
		listParamMap=null;
		strSql=new StringBuilder();
		strParamSql=new StringBuilder();
		strJsonInfo=new StringBuilder();
		params=new Object[]{
			id
		};
		strSql.append("SELECT logicinfo.name as classname,ifnull(logicinfo.remark,'') as classremark,methodinfo.name,ifnull(methodinfo.remark,'') as remark ");
		strSql.append("FROM sc_logicmethod AS methodinfo ");
		strSql.append("JOIN sc_logic AS logicinfo ");
		strSql.append("ON logicinfo.id=methodinfo.logid ");
		strSql.append("WHERE methodinfo.id=?");
		try {
			listMap=commonService.queryListSql("com", strSql.toString(),params);
		} catch (Exception e1) {
			log.error(e1);
		}
		strParamSql.append("SELECT name,IFNULL(init,'') AS init,IFNULL(remark,'') AS remark FROM sc_logicparam ");
		strParamSql.append("WHERE lmid=? ");
		strParamSql.append("ORDER BY lp_index");
		try {
			listParamMap=commonService.queryListSql("com", strParamSql.toString(),params);
		} catch (Exception e1) {
			log.error(e1);
		}
		if(0!=listMap.size())
		{
			try {
				mapInfo=listMap.get(0);
				strJsonInfo.append("{");
				strJsonInfo.append("\"classinfo\":\""+mapInfo.get("classname").toString()+"\",");
				strJsonInfo.append("\"classremark\":\""+URLEncoder.encode(mapInfo.get("classremark").toString(),"UTF-8")+"\",");
				strJsonInfo.append("\"methodinfo\":\""+mapInfo.get("name").toString()+"\",");
				strJsonInfo.append("\"methodremark\":\""+URLEncoder.encode(mapInfo.get("remark").toString(),"UTF-8")+"\",");
				strJsonInfo.append("\"params\":[");
				for(int i=0;i<listParamMap.size();i++)
				{
					mapParamInfo=listParamMap.get(i);
					strJsonInfo.append("{");
					strJsonInfo.append("\"param\":\""+mapParamInfo.get("name").toString()+"\",");
					strJsonInfo.append("\"init\":\""+URLEncoder.encode(mapParamInfo.get("init").toString(),"UTF-8")+"\",");
					strJsonInfo.append("\"remark\":\""+URLEncoder.encode(mapParamInfo.get("remark").toString(),"UTF-8")+"\"");
					strJsonInfo.append("}");
					if(i!=(listParamMap.size()-1))
					{
						strJsonInfo.append(",");
					}
				}
				strJsonInfo.append("]");
				strJsonInfo.append("}");
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(),e);
			}
		}
		try
		{
			jsonObj=JSONObject.fromObject(strJsonInfo.toString());
		}
		catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		return jsonObj;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/wfclaim", method={RequestMethod.POST})
	public @ResponseBody JSONObject wfclaim(String taskId,String userId)
	{
		String strResult;
		JSONObject jsonObj;
		workflowService.taskClaim(taskId, userId);
		strResult = "{\"msg\":\""+taskId+"\"}";
		jsonObj=JSONObject.fromObject(strResult);
		return jsonObj;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/saveBtnStatus", method={RequestMethod.POST})
	public @ResponseBody JSONObject saveBtnStatus(String key,String btnId,String status)
	{
		Map mapInfo;
		String strResult;
		JSONObject jsonObj;
		List<Map> listMapInfo;
		listMapInfo=new ArrayList<Map>();
		mapInfo=new HashMap<String,String>();
		mapInfo.put("btnId", btnId);
		mapInfo.put("status", status);
		listMapInfo.add(mapInfo);
		strResult = "{\"msg\":\"success\"}";
		commonService.saveMapByReadisKeyNoDel(key, listMapInfo);
		jsonObj=JSONObject.fromObject(strResult);
		return jsonObj;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/common/getBtnStatus", method={RequestMethod.POST})
	public @ResponseBody JSONObject getBtnStatus(String key)
	{
		Map mapInfo;
		String strBtnId;
		String strResult;
		String strBtnStat;
		JSONObject jsonObj;
		List<Map> listMapInfo;
		listMapInfo=commonService.loadMapByReadisKeyNoDel(key);
		if(null!=listMapInfo && 1==listMapInfo.size())
		{
			mapInfo=listMapInfo.get(0);
			strBtnId=mapInfo.get("btnId").toString();
			strBtnStat=mapInfo.get("status").toString();
			strResult = "{\"msg\":\"success\",\"btnId\":\""+strBtnId+"\",\"status\":\""+strBtnStat+"\"}";
		}
		else
		{
			strResult = "{\"msg\":\"error\"}";
		}
		jsonObj=JSONObject.fromObject(strResult);
		return jsonObj;
	}
	
}

