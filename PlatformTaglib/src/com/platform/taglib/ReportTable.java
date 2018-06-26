package com.platform.taglib;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.common.entity.query.Column;
import com.common.entity.query.InitSql;
import com.common.entity.query.PageInfo;
import com.common.entity.query.PageStyle;
import com.common.entity.query.PriKey;
import com.common.entity.query.Query;
import com.common.entity.query.Quick;
import com.common.entity.query.RowInfo;
import com.common.entity.query.Search;
import com.common.entity.query.Title;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;
import com.common.tool.Page;
import com.common.unit.HtmlUnit;

public class ReportTable extends TagSupport implements TagShow {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ReportTable.class);

	private String id;
	private Integer width;
	private Integer height;
	private String dataBind;
	private String reportid;
	private String queryName;
	private Integer showtype;
	private String reportTypeId;
	private CoreService coreService;
	private CommonService comService;
	private LocalCommonService localCommonService;

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setInfoname(String infoname) {

	}

	@Override
	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	@Override
	public String getDataBind() {
		return this.dataBind;
	}

	@Override
	public String getInfoname() {
		return null;
	}

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		coreService = (CoreService) ReflectFactory.getObjInstance("com.common.service.CoreService",(HttpServletRequest) this.pageContext.getRequest());
		comService = (CommonService) ReflectFactory.getObjInstance("com.common.service.CommonService",(HttpServletRequest) this.pageContext.getRequest());
		localCommonService = (LocalCommonService) ReflectFactory.getObjInstance("com.common.service.LocalCommonService",(HttpServletRequest) this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input = "";
		JspWriter out = pageContext.getOut();
		try {
			input += createReport((HttpServletRequest) pageContext.getRequest(),(HttpServletResponse) pageContext.getResponse());
			out.println(input);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private String createReport(HttpServletRequest request,HttpServletResponse response) {
		String jsonStr;
		List<Map> listRow;
		StringBuilder strHtml;
		strHtml=new StringBuilder();
		listRow=listReport(request,response);
		jsonStr = JSONArray.fromObject(listRow).toString();
		jsonStr = jsonStr.replace("\"", "&quot;");
		strHtml.append("<input id=\""+this.id+"\" type=\"hidden\" value=\""+jsonStr+"\"/>");
		strHtml.append("<input id=\""+this.id+"_width\" type=\"hidden\" value=\""+width+"\"/>");
		strHtml.append("<input id=\""+this.id+"_height\" type=\"hidden\" value=\""+height+"\"/>");
		strHtml.append("<input id=\""+this.id+"_type\" type=\"hidden\" value=\""+reportTypeId+"\"/>");
		strHtml.append("<input id=\""+this.id+"_title\" type=\"hidden\" value=\""+queryName+"\"/>");
		strHtml.append("<div id=\"div_"+this.id+"\" style=\"width:"+width+"px;height:"+height+"px;\"></div>");
		strHtml.append("<iframe style=\"display:none;\" onload=\"setTimeout(fn_initTableReport('"+this.id+"'),200);\"></iframe>");
		return strHtml.toString();
	}

	private List<Map> listReport(HttpServletRequest request, HttpServletResponse response) {
		Map sqlMap;
		Map retMap;
		Query query;
		Page pageobj;
		String rowkey;
		String colkey;
		String valkey;
		String strSql;
		Map searchMap;
		String colInfo;
		Boolean blInfo;
		List listParam;
		List<Map> list;
		Map mapRowData;
		Map mapRowInfo;
		String queryId;
		Object[] objArr;
		Map queryConfig;
		List listSqlMap;
		String strWhere;
		String rowValue;
		String colValue;
		String valValue;
		Calendar calendar;
		String strSqlInfo;
		List<Map> listRetMap;
		String strQuickWhere;
		List<RowInfo> listRow;
		Object[] queryConfigParams;
		colInfo="";
		list = null;
		retMap=null;
		strWhere="";
		listRow=null;
		rowkey=null;
		colkey=null;
		valkey=null;
		pageobj=new Page();
		queryConfig = null;
		objArr = new Object[1];
		sqlMap = new HashMap();
		searchMap = new HashMap();
		listParam=new ArrayList();
		listRetMap=new ArrayList<Map>();
		calendar = Calendar.getInstance();
		strSqlInfo = "select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
		strSql = "select name,queryid,rowkey,colkey,valkey,width,height,reportid from sc_datavisualization where id=?";
		objArr[0] = reportid;
		try {
			list = (List<Map>) comService.queryListSql("com", strSql, objArr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null != list && 0 != list.size()) {
			mapRowInfo = list.get(0);
			queryName = (null == mapRowInfo.get("name") ? "" : mapRowInfo.get("name").toString());
			width = (null == mapRowInfo.get("width") ? 0 : Integer.parseInt(mapRowInfo.get("width").toString()));
			height = (null == mapRowInfo.get("height") ? 0 : Integer.parseInt(mapRowInfo.get("height").toString()));
			rowkey = (null == mapRowInfo.get("rowkey") ? "" : mapRowInfo.get("rowkey").toString());
			colkey = (null == mapRowInfo.get("colkey") ? "" : mapRowInfo.get("colkey").toString());
			valkey = (null == mapRowInfo.get("valkey") ? "" : mapRowInfo.get("valkey").toString());
			queryId = (null == mapRowInfo.get("queryid") ? "" : mapRowInfo.get("queryid").toString());
			reportTypeId = (null == mapRowInfo.get("reportid") ? "": mapRowInfo.get("reportid").toString());
			queryConfigParams = new Object[] { queryId };
			try {
				queryConfig = comService.queryFullSql("com", strSqlInfo,queryConfigParams);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (null != queryConfig) {
				listSqlMap = (List) queryConfig.get("retMap");
				if (null != listSqlMap && 0 != listSqlMap.size()) {
					query = getQueryBySqlRequest(request,(Map) listSqlMap.get(0));
					if ("get".equals(request.getMethod().toLowerCase())) {
						pageobj.setQueryWhere("");
						pageobj.setOrderField(query.getOrderkey());
						if ("1".equals(query.getChoicetime().getType())) {
							request.setAttribute("begin_datetime",Integer.valueOf(calendar.get(1)));
							calendar.set(1, calendar.get(1) + 1);
							request.setAttribute("end_datetime",Integer.valueOf(calendar.get(1)));
						}
						if ("2".equals(query.getChoicetime().getType())) {
							request.setAttribute("begin_datetime",calendar.get(1) + "-" + (calendar.get(2) + 1));
							calendar.set(2, calendar.get(2) + 1);
							request.setAttribute("end_datetime",calendar.get(1) + "-" + (calendar.get(2) + 1));
						}
						if ("3".equals(query.getChoicetime().getType())) {
							calendar.set(5, calendar.get(5) - 1);
							request.setAttribute("begin_datetime",calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5));
							calendar.set(5, calendar.get(5) + 1);
							request.setAttribute("end_datetime",calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5));
						}
						if ("4".equals(query.getChoicetime().getType())) {
							calendar.set(10, calendar.get(10) - 1);
							request.setAttribute("begin_datetime",calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5) + " " + calendar.get(11) + ":"+ calendar.get(12) + ":" + calendar.get(13));
							calendar.set(10, calendar.get(10) + 1);
							request.setAttribute("end_datetime",calendar.get(1) + "-"+ (calendar.get(2) + 1)+ "-" + calendar.get(5) + " "+ calendar.get(11) + ":"+ calendar.get(12) + ":" +calendar.get(13));
						}
					} else {
						pageobj.setOrderField(request.getParameter("page.orderField"));
						if ((request.getParameter("querySearchName") != null)&& (!"".equals(request.getParameter("querySearchName"))) && (request.getParameter("querySearchExpree") !=null)&& (!"".equals(request.getParameter("querySearchExpree")))&& (request.getParameter("querySearchValue") != null)) {
							if (("like".equals(request.getParameter("querySearchExpree").toString()))||("between".equals(request.getParameter("querySearchExpree").toString()))) {
								if ("like".equals(request.getParameter("querySearchExpree").toString())) {
									strWhere = " and (" + request.getParameter("querySearchName").toString() + " like ?)";
									listParam.add("%" +request.getParameter("querySearchValue").toString() + "%");
								}
							} else {
								strWhere = " and (" + request.getParameter("querySearchName").toString()+ " " + request.getParameter("querySearchExpree").toString() + " ?)";
								listParam.add(request.getParameter("querySearchValue").toString());
							}
						}
						request.setAttribute("_querySearchName",request.getParameter("querySearchName"));
						request.setAttribute("_querySearchExpree",request.getParameter("querySearchExpree"));
						request.setAttribute("_querySearchValue",request.getParameter("querySearchValue"));
						request.setAttribute("begin_datetime",request.getParameter("begin_datetime"));
						request.setAttribute("end_datetime",request.getParameter("end_datetime"));
						for (Column colObj : query.getColumns()) { 
							Quick quick = colObj.getQuick(); String strReqExpree = "";
							if ("1".equals(quick.getIsshow())) { 
								strQuickWhere =" and (" + colObj.getKey() + " "; 
								String strReqValue = request.getParameter(colObj.getKey() + "value"); 
								if("1".equals(quick.getShowoperation())) { 
									strReqExpree = request.getParameter(colObj.getKey() + "expree");
									if (("like".equals(strReqExpree)) ||("between".equals(strReqExpree))) { 
										if("like".equals(strReqExpree)) { 
											strQuickWhere =strQuickWhere + "like ?)"; 
										} 
										if("between".equals(strReqExpree)) { 
											strQuickWhere =strQuickWhere + "=?)"; 
										} 
									} 
									else {
										strQuickWhere = strQuickWhere + strReqExpree + "?)"; 
									} 
								} else {
									strQuickWhere = strQuickWhere + "=?)"; 
								} 
								if(!"".equals(strReqValue)) { 
									strWhere = strWhere +strQuickWhere; 
									if ("like".equals(strReqExpree)) {
										strReqValue = "%" + strReqValue + "%"; 
									}
									listParam.add(strReqValue);
								}
							}
						}
						pageobj.setQueryWhere(strWhere); 
					}
					strSqlInfo = getSqlInfo(query.getSqlinfo(), request, response);
					if("".equals(rowkey)==false)
					{
						colInfo=rowkey;
					}
					if("".equals(colkey)==false)
					{
						colInfo=colkey;
					}
					if("".equals(valkey)==false)
					{
						colInfo=valkey;
					}
					sqlMap.put("sysid", query.getSysid());
					sqlMap.put("sqllist",JSONObject.fromObject("{\"colinfo\":\""+colInfo+"\",\"sql\":\"" +strSqlInfo.replace("\"","\\\"").replaceAll("[\\t\\n\\r]", " ") +"\",\"where\":\"" + pageobj.getQueryWhere().replace("\"","\\\"").replaceAll("[\\t\\n\\r]", " ") + "\",\"orderBy\":\"" + pageobj.getOrderField().replace("\"","\\\"").replaceAll("[\\t\\n\\r]", " ") + "\",\"pageNum\":-1,\"pageSize\":-1}"));
					if ((query.getInitsql() != null) && (query.getInitsql().getStrsql() != null) &&(!"".equals(query.getInitsql().getStrsql()))) {
						sqlMap.put("initsql",getSqlInfo(query.getInitsql().getStrsql(),request,response).replace("\"","\\\"").replaceAll("[\\t\\n\\r]", " ")); 
					} 
					for(Column colObj : query.getColumns()) {
						Search search = colObj.getSearch();
						if(("select".equals(search.getType())) &&("sql".equals(search.getDatatype())) &&(!"".equals(search.getSearchinfo())))
						{
							searchMap.put(colObj.getKey() + "_select",search.getSearchinfo().replace("\"","\\\"").replaceAll("[\\t\\n\\r]", " "));
						}
					}
					sqlMap.put("colSearch", searchMap);
					PriKey keyInfo = query.getPrikey();
				    if ((keyInfo != null) && (keyInfo.getKey() != null) && (!"".equals(keyInfo.getKey())))
				    {
				      sqlMap.put("priKey", keyInfo.getKey());
				    }
				    try {
						retMap = comService.getQueryMapByQuery(sqlMap, listParam.toArray());
						listRow=DynamicdataInfo(pageobj, query, retMap, request, response);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
		}
		if(null!=listRow && null != rowkey && null != colkey && null != valkey)
		{
			for(RowInfo rowInfo:listRow)
			{
				blInfo=false;
				rowValue=rowInfo.getRowInfo().get(rowkey).toString();
				colValue=rowInfo.getRowInfo().get(colkey).toString();
				valValue=rowInfo.getRowInfo().get(valkey).toString();
				if(0!=listRetMap.size())
				{
					for(int i=0;i<listRetMap.size();i++)
					{
						mapRowData=listRetMap.get(i);
						if(rowValue.equals(mapRowData.get("rowid"))==true){
							blInfo=true;
							mapRowData.put(colValue, valValue);
						}
					}
				}
				if(false==blInfo)
				{
					mapRowData=new HashMap<String,Object>();
					mapRowData.put("rowid", rowValue);
					mapRowData.put(colValue, valValue);
					listRetMap.add(mapRowData);
				}
			}
		}
		return listRetMap;
	}
	
	private List<RowInfo> DynamicdataInfo(Page pageobj, Query query, Map<String, Object> retMap, HttpServletRequest request,HttpServletResponse response){
		long iRow;
		long totNum;
		Title title;
		int pageNum;
		int pageSize;
		String strKey;
		PriKey priKey;
		Search search;
		InitSql initSql;
		String strMapKey;
		String strMapVal;
		Boolean blSelect;
		Boolean blIsedit;
		Iterator iterData;
		List<Map> retList;
		List<Map> initMap;
		String titleSysId;
		String strInitType;
		String strSearcode;
		String strRelColId;
		String titleSqlInfo;
		String strSearvalue;
		String strEditValue;
		String strExSqlInfo;
		PageStyle pagestyle;
		List<Map> searchMap;
		List<Object> listObj;
		String strRelColValue;
		List<RowInfo> rowList;
		String strSqlDataInfo;
		PageInfo<Map> pageInfo;
		String strDataWhereInfo;
		List<Map> retExDataList;
		Map<String, Object> hMap;
		List<Map> retExTitleList;
		List<Map> listBodyDataMap;
		List<Map> listMapDataInfo;
		Map<String,Object> colMap;
		Map<String, Object> hExMap;
		Map<String, Object> mapInfo;
		Map<String, Object> dataMap;
		Map<String,Map> mapInfoList;
		StringBuilder strDataSqlInfo;
		Map<String,List<Map>> mapList;
		Map<String,String> mapDataInfo;
		Map<String, Object> dataColMap;
		HashMap<String, Object> hashData;
		Map.Entry<String, String> mapEntryDataInfo;
		initMap=null;
		hashData=new HashMap<String,Object>();
		priKey=query.getPrikey();
		initSql=query.getInitsql();
		pagestyle=query.getPagestyle();
		totNum=0;
		mapInfoList=new HashMap<String,Map>();
		pageInfo=(PageInfo<Map>)retMap.get("sqllist");
		retList=pageInfo.getList();
		strInitType="".equals(initSql.getType())?"0":initSql.getType();
		if(pageInfo!=null){
			totNum=pageInfo.getRowCount();
		}
		pageobj.setTotalCount(totNum);
		pageNum=pageobj.getPageNum();
		pageSize=pageobj.getNumPerPage();
		pageNum=pageNum-1;
		pageSize=0==pageSize?0:pageSize;
		iRow=(pageSize*pageNum)+1;
		rowList=comService.getQueryList(iRow,query,retList);
		if("".equals(initSql.getStrsql())==false && initSql.getStrsql()!=null)
		{
			initMap=(List<Map>)retMap.get("initsql");
		}
		if (priKey.getKey() != null && "".equals(priKey.getKey()) == false
				&& "".equals(priKey.getType()) == false
				&& "0".equals(priKey.getType()) == false) {
			if(rowList!=null && rowList.size()!=0)
			{
				for (RowInfo rowInfo : rowList) {
					strKey = "";
					blSelect = false;
					hMap=rowInfo.getRowInfo();
					for (Map.Entry<String, Object> entry : hMap.entrySet()) {
						if (priKey.getKey().equals(entry.getKey())) {
							strKey = entry.getValue().toString();
							break;
						}
					}
					if (initMap != null) {
						for (Map<String, Object> reMap : initMap) {
							for (Map.Entry<String, Object> entry : reMap.entrySet()) {
								if (priKey.getKey().equals(entry.getKey()) == true
										&& strKey.equals(entry.getValue()
												.toString()) == true) {
									blSelect = true;
									break;
								}
							}
							if (blSelect == true) {
								break;
							}
						}
					}
					if (blSelect == true) {
						switch (priKey.getType()) {
						case "1":
							rowInfo.setStrSelValue("<input type='radio' name='querycheckBox' "
										+ (strInitType.equals("0") == true ? "checked='checked'": "disabled='disabled'")
										+ " key='" + strKey + "' />");
							break;
						case "2":
							rowInfo.setStrSelValue("<input type='checkbox' name='querycheckBox'"
									+ (strInitType.equals("0") == true ? "checked='checked'": "disabled='disabled'")
									+ " key='" + strKey + "' />");
							break;
						}
					} else {
						switch (priKey.getType()) {
						case "1":
							rowInfo.setStrSelValue("<input type='radio' name='querycheckBox' key='"
											+ strKey + "' />");
							break;
						case "2":
							rowInfo.setStrSelValue("<input type='checkbox' name='querycheckBox' key='"
									+ strKey + "' />");
							break;
						}
					}
				}
			}
		}
		colMap=(Map<String,Object>)retMap.get("colSearch");
		for(Column colObj:query.getColumns()){
			search=colObj.getSearch();
			if("select".equals(search.getType()) && "sql".equals(search.getDatatype()) && "".equals(search.getSearchinfo())==false){
				searchMap=(List<Map>)colMap.get(colObj.getKey()+"_select");
				hashData.put(colObj.getKey(), searchMap);
			}
		}
		strSearvalue="";
		strSearcode="";
		for(Map.Entry<String, Object> hashentry:hashData.entrySet())
		{
			if(rowList!=null && rowList.size()!=0)
			{
				for (RowInfo rowInfo : rowList) {
					hMap=rowInfo.getRowInfo();
					for (Map.Entry<String, Object> entry: hMap.entrySet()) {
						if (hashentry.getKey().equals(entry.getKey())) {
							blSelect=false;
							searchMap=(List<Map>)hashentry.getValue();
							for(Map<String, Object> searchMapObj:searchMap)
							{
								for (Map.Entry<String, Object> searchentry: searchMapObj.entrySet()) {
									if("code".equals(searchentry.getKey()))
									{
										strSearcode=searchentry.getValue().toString();
										continue;
									}
									if("value".equals(searchentry.getKey()))
									{
										strSearvalue=searchentry.getValue().toString();
										continue;
									}
								}
								if(strSearvalue.equals(entry.getValue().toString())==true)
								{
									entry.setValue(strSearcode);
									blSelect=true;
									break;
								}
								
							}
							if(blSelect==false)
							{
								entry.setValue("");
							}
						}
					}
				}
			}
		}
		strEditValue="";
		mapList=new HashMap<String,List<Map>>();
		for(Column colobj:query.getColumns())
		{
			title=colobj.getTitle();
			search=colobj.getSearch();
			if("1".equals(colobj.getTypeid())){
				hExMap=null;
				titleSysId = colobj.getTitleSysId();
				titleSqlInfo=colobj.getTitleSqlInfo();
				strExSqlInfo = this.getSqlInfo(titleSqlInfo,request,response);
				try {
					hExMap=comService.queryFullSql(titleSysId, strExSqlInfo,null);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				retExTitleList=(List<Map>)hExMap.get("retMap");
				request.setAttribute(colobj.getKey(), retExTitleList);
				strRelColId = colobj.getRelColId();
				if(rowList!=null && rowList.size()!=0 && StringUtils.isEmpty(colobj.getCurrColId())==false && StringUtils.isEmpty(colobj.getCurrValId())==false && StringUtils.isEmpty(colobj.getDataSysId())==false && StringUtils.isEmpty(colobj.getDataSqlInfo())==false)
				{
					strDataWhereInfo="";
					listObj=new ArrayList<Object>();
					strDataSqlInfo=new StringBuilder();
					strDataSqlInfo.append("select * from ( ");
					strDataSqlInfo.append(colobj.getDataSqlInfo());
					strDataSqlInfo.append(" ) as t");
					strDataSqlInfo.append(" where (1=1) and "+colobj.getCurrValId()+" in ( ");
					for(RowInfo rowInfo : rowList) {
						hMap=rowInfo.getRowInfo();
						if(null!=hMap.get(strRelColId))
						{
							strDataWhereInfo+="?,";
							listObj.add(hMap.get(strRelColId).toString());
						}
					}
					if(listObj.size()>0)
					{
						strDataWhereInfo=strDataWhereInfo.substring(0, strDataWhereInfo.length()-1);
						strDataSqlInfo.append(strDataWhereInfo);
					}
					strDataSqlInfo.append(" )");
					if(listObj.size()>0)
					{
						dataMap=null;
						strSqlDataInfo=this.getSqlInfo(strDataSqlInfo.toString(),request,response);
						try {
							dataMap=comService.queryFullSql(colobj.getDataSysId(), strSqlDataInfo,listObj.toArray());
						} catch (Exception e) {
							log.error(e.getMessage(),e);
						}
						mapInfoList.put(colobj.getKey(), dataMap);
						retExDataList=(List<Map>)dataMap.get("retMap");
						if(null!=retExDataList && 0!=retExDataList.size())
						{
							for(int u=0;u<retExDataList.size();u++)
							{
								dataColMap=retExDataList.get(u);
								if(null!=dataColMap.get(colobj.getTitColId()) && null!=dataColMap.get(colobj.getCurrValId()) && null!=dataColMap.get(colobj.getCurrColId()))
								{
									strRelColValue=dataColMap.get(colobj.getCurrValId()).toString();
									mapDataInfo=new HashMap<String,String>();
									mapDataInfo.put(colobj.getKey()+"_"+dataColMap.get(colobj.getTitColId()).toString(), dataColMap.get(colobj.getCurrColId()).toString());
									if(null!=mapList.get(colobj.getKey()+"_"+strRelColValue)){
										listMapDataInfo=mapList.get(colobj.getKey()+"_"+strRelColValue);
										listMapDataInfo.add(mapDataInfo);
									}
									else
									{
										listMapDataInfo=new ArrayList<Map>();
										listMapDataInfo.add(mapDataInfo);
										mapList.put(colobj.getKey()+"_"+strRelColValue, listMapDataInfo);
									}
								}
							}
						}
					}
				}
			}
			for(Column colInfo:query.getColumns())
			{
				if("1".equals(colInfo.getTypeid())){
					strRelColId = colInfo.getRelColId();
					mapInfo=mapInfoList.get(colInfo.getKey());
					if(null!=mapInfo)
					{
						listBodyDataMap=(List<Map>)mapInfo.get("retMap");
						if(null!=listBodyDataMap)
						{
							for(RowInfo rowInfo:rowList)
							{
								hMap=rowInfo.getRowInfo();
								if(null!=hMap.get(strRelColId)){
									strRelColValue=hMap.get(strRelColId).toString();
									if(null!=strRelColValue && "".equals(strRelColValue)==false)
									{
										if(null!=mapList.get(colInfo.getKey()+"_"+strRelColValue))
										{
											listMapDataInfo=mapList.get(colInfo.getKey()+"_"+strRelColValue);
											for(Map mapInfoData:listMapDataInfo)
											{
												iterData=mapInfoData.entrySet().iterator();
												while(iterData.hasNext())
												{
													mapEntryDataInfo=(Entry<String, String>)iterData.next();
													strMapKey=mapEntryDataInfo.getKey();
													strMapVal=mapEntryDataInfo.getValue();
													hMap.put(strMapKey, strMapVal);
												}
											}
										}
									}
								}
							}
						}					
					}
				}
			}
			if("1".equals(title.getIsedit())==true)
			{
				iRow=0;
				if(rowList!=null && rowList.size()!=0)
				{
					for (RowInfo rowInfo : rowList) {
						hMap=rowInfo.getRowInfo();
						blIsedit=false;
						for (Map.Entry<String, Object> entry: hMap.entrySet()) {
							if(colobj.getKey().equals(entry.getKey())==true){
								blIsedit=true;
								if("select".equals(search.getType()) && "sql".equals(search.getDatatype()) && "".equals(search.getSearchinfo())==false)
								{
									searchMap=null;
									strEditValue="<span id='"+colobj.getKey()+iRow+"editsqpan' ondblclick=\"fn_editcol(this,'"+colobj.getKey()+iRow+"editselect')\" >"+entry.getValue()+"</span>";
									strEditValue +="<select id='"+colobj.getKey()+iRow+"editselect' style='display:none;'>";
									for(Map.Entry<String, Object> hashentry:hashData.entrySet())
									{
										if(colobj.getKey().equals(hashentry.getKey())==true)
										{
											searchMap=(List<Map>)hashentry.getValue();
											break;
										}
									}
									if(searchMap!=null)
									{
										strSearvalue="";
										strSearcode="";
										for(Map<String, Object> searMap:searchMap)
										{
											for (Map.Entry<String, Object> searentry: searMap.entrySet()) {
												if("code".equals(searentry.getKey())==true)
												{
													strSearcode=searentry.getValue().toString();
													continue;
												}
												if("value".equals(searentry.getKey())==true)
												{
													strSearvalue=searentry.getValue().toString();
													continue;
												}
											}
											strEditValue+="<option value='"+strSearvalue+"' "+(strSearcode.equals(entry.getValue())==true?"selected='selected'":"")+">"+strSearcode+"</option>";
										}
									}
									strEditValue +="</select>";
								}
								if("input".equals(search.getType())==true)
								{
									strEditValue = "<span id='"+colobj.getKey()+iRow+"editsqpan' ondblclick=\"fn_editcol(this,'"+colobj.getKey()+iRow+"editinput')\" >"+entry.getValue()+"</span>";
									strEditValue +="<input id='"+colobj.getKey()+iRow+"editinput' type='input' value='"+entry.getValue()+"' style='display:none;'></input>";
								}
								entry.setValue(strEditValue);
							}
						}
						if(blIsedit==false)
						{
							if("select".equals(search.getType()) && "sql".equals(search.getDatatype()) && "".equals(search.getSearchinfo())==false)
							{
								searchMap=null;
								strEditValue="<span id='"+colobj.getKey()+iRow+"editsqpan' ondblclick=\"fn_editcol(this,'"+colobj.getKey()+iRow+"editselect')\" >&nbsp;</span>";
								strEditValue +="<select id='"+colobj.getKey()+iRow+"editselect' style='display:none;'>";
								for(Map.Entry<String, Object> hashentry:hashData.entrySet())
								{
									if(colobj.getKey().equals(hashentry.getKey())==true)
									{
										searchMap=(List<Map>)hashentry.getValue();
										break;
									}
								}
								if(searchMap!=null)
								{
									strSearvalue="";
									strSearcode="";
									for(Map<String, Object> searMap:searchMap)
									{
										for (Map.Entry<String, Object> searentry: searMap.entrySet()) {
											if("code".equals(searentry.getKey())==true)
											{
												strSearcode=searentry.getValue().toString();
												continue;
											}
											if("value".equals(searentry.getKey())==true)
											{
												strSearvalue=searentry.getValue().toString();
												continue;
											}
										}
										strEditValue+="<option value='"+strSearvalue+"'>"+strSearcode+"</option>";
									}
								}
								strEditValue +="</select>";
							}
							if("input".equals(search.getType())==true)
							{
								strEditValue = "<span id='"+colobj.getKey()+iRow+"editsqpan' ondblclick=\"fn_editcol(this,'"+colobj.getKey()+iRow+"editinput')\" >&nbsp;</span>";
								strEditValue +="<input id='"+colobj.getKey()+iRow+"editinput' type='input' value='' style='display:none;'></input>";
							}
							hMap.put(colobj.getKey(), strEditValue);
						}
						iRow++;
					}
				}
			}
		}
		if(pagestyle!=null)
		{
			request.setAttribute("_pagestyletype", pagestyle.getType());
			request.setAttribute("_showlist", pagestyle.getShowlist());
		}
		else
		{
			request.setAttribute("_showlist", null);
		}
		request.setAttribute("_columns", query.getColumns());
		request.setAttribute("_queryData", rowList);
		return rowList;
	}

	private String getSqlInfo(String sqlinfo, HttpServletRequest request,HttpServletResponse response) {
		sqlinfo = HtmlUnit.getValueByRequest(sqlinfo, request, response);
	    return sqlinfo;
	}

	@Override
	public void setShowtype(Integer itagtype) {
		showtype = itagtype;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}

	private Query getQueryBySqlRequest(HttpServletRequest requestToExpose,Map map) {
		Query query;
		byte[] bytes;
		Integer type;
		bytes = (byte[]) map.get("bytesinfo");
		type = (Integer) map.get("type");
		String strEncodeMap = new String(bytes, Charset.forName("UTF-8"));
		if ((type != null) && (type.intValue() != 0)) {
			strEncodeMap = coreService.readEncryptSql(strEncodeMap);
		}
		query = localCommonService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
		return query;
	}
}
