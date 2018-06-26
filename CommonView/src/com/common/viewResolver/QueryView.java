package com.common.viewResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
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
import com.common.entity.query.ShowStyle;
import com.common.entity.query.Title;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.service.QueueSenderService;
import com.common.tool.Page;
import com.common.tool.XmlUnit;
import com.common.unit.HtmlUnit;

public class QueryView extends PlatformResourceView{
	
	private static Logger log = Logger.getLogger(QueryView.class);
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String jstag;
	private String titlename;
	private String head;
	private String buttonlist;
	private String toollist;
	private String tableHead;
	private String tableTotal;
	private String quickquery;
	private String strquery;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getQuickquery() {
		return quickquery;
	}

	public void setQuickquery(String quickquery) {
		this.quickquery = quickquery;
	}

	public String getQuery() {
		return strquery;
	}

	public void setQuery(String query) {
		this.strquery = query;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getButtonlist() {
		return buttonlist;
	}

	public void setButtonlist(String buttonlist) {
		this.buttonlist = buttonlist;
	}

	public String getToollist() {
		return toollist;
	}

	public void setToollist(String toollist) {
		this.toollist = toollist;
	}

	public String getTableHead() {
		return tableHead;
	}

	public void setTableHead(String tableHead) {
		this.tableHead = tableHead;
	}
	
	public String getTableTotal() {
		return tableTotal;
	}
	public void setTableTotal(String tableTotal) {
		this.tableTotal = tableTotal;
	}

	public String getJstag() {
		return jstag;
	}

	public void setJstag(String jstag) {
		this.jstag = jstag;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	
	public String getDefaultEncoding() {
		return defaultEncoding;
	}
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getTemppage() {
		return temppage;
	}
	public void setTemppage(String temppage) {
		this.temppage = temppage;
	}
	public String getTemplatepage() {
		return templatepage;
	}
	public void setTemplatepage(String templatepage) {
		this.templatepage = templatepage;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageNum;
		Page pageobj;
		Boolean blLoad;
		String colInfo;
		String strWhere;
		String strTempJsp;
		String[] strReqArr;
		String strQuickWhere;
		String strReqExpree;
		String strReqValue;
		String strViewPath;
		CoreService coreService;
		CommonService commService;
		LocalCommonService localcommService;
		HttpServletRequest requestToExpose;
		String dispatcherPath;
		RequestDispatcher rd;
		Map<String, String> config;
		Query query;
		Quick quick;
		String strSqlInfo;
		Calendar calendar;
		Title title;
		PageStyle pageStyle;
		List<Object> listParam;
		List<ShowStyle> listStyle;
		Map<String, Object> queryConfig;
		Map<String, Object> retMap;
		List<Map> listSqlMap;
		Object[] queryConfigParams;
		blLoad=true;
		calendar = Calendar.getInstance();
		listParam=new ArrayList<Object>();
		strWhere="";
		strQuickWhere="";
		strReqArr=null;
		retMap=null;
		colInfo="";
		// Determine which request handle to expose to the RequestDispatcher.
		requestToExpose = getRequestToExpose(request);
		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);
		// Determine the path for the request dispatcher.
		dispatcherPath = prepareForRendering(requestToExpose, response);
		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		if (rd == null) {
			throw new ServletException(
					"Could not get RequestDispatcher for ["
							+ getUrl()
							+ "]: Check that the corresponding file exists within your web application archive!");
		}
		if (templatepage == null) {
			throw new ServletException("Check config templatepage");
		}
		if (temppage == null) {
			throw new ServletException("Check config temppage");
		}
		if (titlename == null){
			throw new ServletException("Check config titlename");
		}
		if (jstag == null) {
			throw new ServletException("Check config jstag");
		}
		if (suffix == null) {
			throw new ServletException("Check config suffix");
		}
		if (head == null) {
			throw new ServletException("Check config head");
		}
		if (buttonlist == null) {
			throw new ServletException("Check config buttonlist");
		}
		if (toollist == null) {
			throw new ServletException("Check config toollist");
		}
		if (tableHead == null) {
			throw new ServletException("Check config tableHead");
		}
		if (tableTotal == null) {
			throw new ServletException("Check config tableTotal");
		}
		if (quickquery == null) {
			throw new ServletException("Check config quickquery");
		}
		if (strquery == null) {
			throw new ServletException("Check config query");
		}
		if (cacheName == null) {
			throw new ServletException("Check config cacheName");
		}
		strViewPath=request.getAttribute("_viewpath").toString();
		strSqlInfo="select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
		queryConfigParams=new Object[]{strViewPath};
		queryConfig=commService.queryFullSql("com", strSqlInfo,queryConfigParams);
		listSqlMap=(List<Map>)queryConfig.get("retMap");
		if(0==listSqlMap.size())
		{
			if(localcommService.existsFile(request.getSession().getServletContext().getRealPath(getUrl()))==false)
			{
				throw new ServletException(getUrl()+" Query file is null");
			}
		}
		config = new HashMap<String, String>();
		config.put("titlename", titlename);
		config.put("templatepage", templatepage);
		config.put("temppage", temppage);
		config.put("prefix", prefix);
		config.put("suffix", suffix);
		config.put("jstag", jstag);
		config.put("head", head);
		config.put("buttonlist", buttonlist);
		config.put("toollist", toollist);
		config.put("tableHead", tableHead);
		config.put("tableTotal", tableTotal);
		config.put("quickquery", quickquery);
		config.put("query", strquery);
		config.put("defaultEncoding", defaultEncoding);
		if(0==listSqlMap.size())
		{
			query = getQueryByRequest(requestToExpose,localcommService);
		}
		else
		{
			query = getQueryBySqlRequest(requestToExpose,localcommService,coreService,listSqlMap.get(0));
		}
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage + ("".equals(query.getFiledir())==true?"":(query.getFiledir()+"/")) + strTempJsp;
		pageobj=new Page();
		pageStyle=query.getPagestyle();
		listStyle=pageStyle.getShowlist();
		for(int i=0;i<listStyle.size();i++)
		{
			if("11".equals(listStyle.get(i).getType()) && "1".equals(listStyle.get(i).getIsshow())){
				blLoad=false;
				break;
			}
		}
		for(Column colObj : query.getColumns()){
			title=colObj.getTitle();
			if("1".equals(title.getIsshow())==true && (null == colObj.getTypeid() || "0".equals(colObj.getTypeid()))){
				colInfo+=colObj.getKey()+",";
			}
		}
		if ("get".equals(request.getMethod().toLowerCase()) == true) {
			pageNum=1;
			pageobj.setPageNum(1);
			pageobj.setNumPerPage(Integer.parseInt(query.getChoicetime().getPagenum()));
			pageobj.setQueryWhere("");
			pageobj.setOrderField(query.getOrderkey());
			if("1".equals(query.getChoicetime().getType())==true){
				request.setAttribute("begin_datetime", calendar.get(Calendar.YEAR));
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
				request.setAttribute("end_datetime", calendar.get(Calendar.YEAR));
			}
			if("2".equals(query.getChoicetime().getType())==true){
				request.setAttribute("begin_datetime",datechange(calendar.getTime(),"yyyy-MM"));
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
				request.setAttribute("end_datetime",datechange(calendar.getTime(),"yyyy-MM"));
			}
			if("3".equals(query.getChoicetime().getType())==true){
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
				request.setAttribute("begin_datetime",datechange(calendar.getTime(),"yyyy-MM-dd"));
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
				request.setAttribute("end_datetime",datechange(calendar.getTime(),"yyyy-MM-dd"));
			}
			if("4".equals(query.getChoicetime().getType())==true){
				calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)-1);
				request.setAttribute("begin_datetime",datechange(calendar.getTime(),"yyyy-MM-dd HH:mm:ss"));
				calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+1);
				request.setAttribute("end_datetime",datechange(calendar.getTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			
		}
		else
		{
			blLoad=true;
			pageNum=Integer.parseInt(request.getParameter("page.pageNum"));
			pageobj.setPageNum(Integer.parseInt(request.getParameter("page.pageNum")));
			pageobj.setNumPerPage(Integer.parseInt(request.getParameter("page.numPerPage")));
			pageobj.setOrderField(request.getParameter("page.orderField"));
			if(request.getParameter("querySearchName")!=null && "".equals(request.getParameter("querySearchName"))==false &&request.getParameter("querySearchExpree")!=null && "".equals(request.getParameter("querySearchExpree"))==false && request.getParameter("querySearchValue")!=null)
			{
				if("like".equals(request.getParameter("querySearchExpree").toString())==true || "between".equals(request.getParameter("querySearchExpree").toString())==true || "in".equals(request.getParameter("querySearchExpree").toString())==true)
				{
					if("like".equals(request.getParameter("querySearchExpree").toString())==true)
					{
						strWhere =" and ("+request.getParameter("querySearchName").toString()+" like ?)";
						listParam.add("%"+request.getParameter("querySearchValue").toString()+"%");
					}
					if("in".equals(request.getParameter("querySearchExpree").toString())==true)
					{
						strReqArr=request.getParameter("querySearchValue").toString().split(",");
						strWhere =" and ("+request.getParameter("querySearchName").toString()+" in (";
						for(int i=0;i<strReqArr.length;i++)
						{
							strWhere +="?";
							listParam.add(strReqArr[i]);
							if(i != (strReqArr.length-1))
							{
								strWhere +=",";
							}
						}
						strWhere +="))";
					
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
			for(Column colObj : query.getColumns()){
				quick=colObj.getQuick();
				strReqExpree="";
				if("1".equals(quick.getIsshow())==true){
					strQuickWhere =" and ("+colObj.getKey()+" ";
					strReqValue=request.getParameter(colObj.getKey()+"value");
					if("1".equals(quick.getShowoperation())==true){
						strReqExpree=request.getParameter(colObj.getKey()+"expree");
						if("like".equals(strReqExpree) || "between".equals(strReqExpree) || "in".equals(strReqExpree)){
							if("like".equals(strReqExpree)){
								strQuickWhere +="like ?)";
							}
							if("between".equals(strReqExpree)){
								strQuickWhere +="=?)";
							}
							if("in".equals(strReqExpree)){
								if("".equals(strReqValue)==false)
								{
									strReqArr=strReqValue.split(",");
								}
								if(null!=strReqArr)
								{
									strQuickWhere +="in (";
									for(int i=0;i<strReqArr.length;i++)
									{
										strQuickWhere+="'"+strReqArr[i]+"'";
										if(i!=(strReqArr.length-1))
										{
											strQuickWhere+=",";
										}
									}
									strQuickWhere +="))";
								}
								else
								{
									strQuickWhere +="in (''))";
								}
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
			}
			pageobj.setQueryWhere(strWhere);
		}
		if("0".equals(request.getParameter("_exportExcel"))==true)
		{
			excelQueryList(query,colInfo,pageobj,pageNum,localcommService,commService,blLoad,listParam,retMap,request,response);
		}
		if(null==request.getParameter("_exportExcel") || "".equals(request.getParameter("_exportExcel"))==true)
		{
			showQueryList(query,colInfo,pageobj,pageNum,commService,blLoad,listParam,retMap,request,response);
		}
		if(null!=retMap)
		{
			if (queryToClass(strViewPath,coreService,localcommService,query,(Map<String,Object>)retMap.get("colSearch"), config, request,response,strTempJsp) == true) {
				rd = getRequestDispatcher(requestToExpose, strTempJsp);
			}
		}
		else{
			if (queryToClass(strViewPath,coreService,localcommService,query,null, config, request,response,strTempJsp) == true) {
				rd = getRequestDispatcher(requestToExpose, strTempJsp);
			}
		}
		response.addHeader("X-XSS-Protection", "0");
		// If already included or response already committed, perform include,
		// else forward.
		if (useInclude(requestToExpose, response)) {
			response.setContentType(getContentType());
			if (logger.isDebugEnabled()) {
				logger.debug("Including resource [" + getUrl()
						+ "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.include(requestToExpose, response);
		}
		else {
			// Note: The forwarded resource is supposed to determine the content
			// type itself.
			if (logger.isDebugEnabled()) {
				logger.debug("Forwarding to resource [" + getUrl()
						+ "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.forward(requestToExpose, response);
		}
	}
	
	private void excelQueryList(Query query, String colInfo, Page pageobj,int pageNum, LocalCommonService localcommService, CommonService commService, Boolean blLoad,List<Object> listParam, Map<String, Object> retMap,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int iSum;
		Date dateNow;
		String strKey;
		int iColCount;
		String strDate;
		Title titleObj;
		int iExtendSum;
		int iExtendCount;
		Map mapTitleInfo;
		String strCodeKey;
		String strSendMsg;
		String strQueryKey;
		String strTitleKey;
		String strDataSysId;
		String strTitleSysId;
		SimpleDateFormat sdf;
		String strDataSqlInfo;
		String strTitleSqlInfo;
		List<Map> listTitleMap;
		Object[] insertObjParams;
		List<Map> listTitleDataMap;
		StringBuilder strParamInfo;
		StringBuilder strGroupInfo;
		StringBuilder strSendBuilder;
		StringBuilder strSqlInfoList;
		QueueSenderService queueSender;
		iSum=0;
		iColCount=0;
		iExtendSum=0;
		strCodeKey="";
		iExtendCount=0;
		dateNow=new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		strDate=sdf.format(dateNow);
		queueSender=(QueueSenderService)ReflectFactory.getObjInstance("com.common.service.QueueSenderService", request);
		if(null!=request.getParameter("_exportExcelKey"))
		{
			strCodeKey=request.getParameter("_exportExcelKey").toString();
			insertObjParams=new Object[6];
			insertObjParams[0]=strCodeKey;
			insertObjParams[1]=query.getCode();
			insertObjParams[2]=strCodeKey+".bin";
			insertObjParams[3]=1;
			insertObjParams[4]=0;
			insertObjParams[5]=strDate;
			commService.insertObj("com", "sc_fileinfo", "id,path,currname,type,status,createdate", insertObjParams);
		}
		strGroupInfo=new StringBuilder();
		strParamInfo=new StringBuilder();
		listTitleMap=new ArrayList<Map>();
		strSendBuilder=new StringBuilder();
		strSqlInfoList=new StringBuilder();
		strGroupInfo.append("[");
		strParamInfo.append("[");
		for(Column colObj : query.getColumns()){
			titleObj=colObj.getTitle();
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
					strKey=commService.queryBigSql(strTitleSysId, strTitleSqlInfo, null);
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
					strKey=commService.queryBigSql(strDataSysId, strDataSqlInfo, null);
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
		strQueryKey=commService.saveRedisObj(query);
		strTitleKey=commService.saveRedisObj(listTitleMap);
		strSqlInfoList.append("select * from ( ");
		strSqlInfoList.append(getSqlInfo(query.getSqlinfo(),request,response));
		strSqlInfoList.append(") as t ");
		strSendBuilder.append("{");
		strSendBuilder.append("\"queryid\":\""+strQueryKey+"\",");
		strSendBuilder.append("\"titleid\":\""+strTitleKey+"\",");
		strSendBuilder.append("\"key\":\""+strCodeKey+"\",");
		strSendBuilder.append("\"code\":\""+query.getCode()+"\",");
		strSendBuilder.append("\"sysid\":\""+query.getSysid()+"\",");
		strSendBuilder.append("\"datatype\":\""+query.getDatatype()+"\",");
		strSendBuilder.append("\"sql\":\""+strSqlInfoList.toString().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
		strSendBuilder.append("\"where\":\""+pageobj.getQueryWhere().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
		strSendBuilder.append("\"orderBy\":\""+query.getOrderkey().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",");
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
		for(Column colObj : query.getColumns()){
			if(0!=iColCount)
			{
				strSendBuilder.append(",");
			}
			strSendBuilder.append("{\"key\":\""+colObj.getKey()+"\",\"sqlinfo\":\""+getSqlInfo(colObj.getTitleSqlInfo(),request,response)+"\"}");
			iColCount++;
		}
		strSendBuilder.append("],");
		strSendBuilder.append("\"extendcountcol\":[");
		for(Column colObj : query.getColumns()){
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
		queueSender.send("1", strSendMsg);
	}
	
	private void showQueryList(Query query, String colInfo, Page pageobj, int pageNum, CommonService commService, Boolean blLoad, List<Object> listParam, Map<String, Object> retMap, HttpServletRequest request, HttpServletResponse response) {
		Title title;
		Search search;
		PriKey keyInfo;
		String strSqlInfo;
		String strCountKey;
		Boolean blCountInfo;
		List<Map> rowListMap;
		String strCountValue;
		String strCountColId;
		String strCountSysId;
		String strCountSqlInfo;
		String strCountcurrColId;
		List<Map> rowCountListMap;
		Map<String, Object> sqlMap;
		String strCountChangeSqlInfo;
		Map<String, Object> searchMap;
		Map<String, Object> mapRowInfo;
		Map<String, Object> retCountMap;
		Map<String, Object> sqlCountMap;
		Map<String, Object> retTotalMap;
		Map<String, Object> mapRowCountRet;
		Map<String, Object> mapRowCountInfo;
		retMap = null;
		sqlMap=new HashMap<String,Object>();
		searchMap=new HashMap<String,Object>();
		sqlCountMap=new HashMap<String,Object>();
		if("".equals(colInfo)==false)
		{
			colInfo=colInfo.substring(0, colInfo.length()-1);
		}
		strSqlInfo=this.getSqlInfo(query.getSqlinfo(),request,response);
		sqlMap.put("sysid",query.getSysid());
		sqlMap.put("sqllist", JSONObject.fromObject("{\"colinfo\":\""+colInfo+"\",\"sql\":\""+strSqlInfo.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"where\":\""+pageobj.getQueryWhere().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"orderBy\":\""+pageobj.getOrderField().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"pageNum\":"+(pageNum-1)+",\"pageSize\":"+pageobj.getNumPerPage()+"}"));
		if(null != query.getInitsql() && query.getInitsql().getStrsql()!=null && "".equals(query.getInitsql().getStrsql())==false)
		{
			sqlMap.put("initsql", this.getSqlInfo(query.getInitsql().getStrsql(),request,response).replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " "));
		}
		//执行Sql
		for(Column colObj:query.getColumns()){
			search=colObj.getSearch();
			if("select".equals(search.getType()) && "sql".equals(search.getDatatype()) && "".equals(search.getSearchinfo())==false){
				searchMap.put(colObj.getKey()+"_select",search.getSearchinfo().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " "));
			}
		}
		sqlMap.put("colSearch",searchMap);
		keyInfo=query.getPrikey();
		if(null!=keyInfo && null!=keyInfo.getKey() && "".equals(keyInfo.getKey())==false)
		{
			sqlMap.put("priKey", keyInfo.getKey());
		}
		if(true==blLoad)
		{
			try {
				retMap=commService.getQueryMapByQuery(sqlMap,listParam.toArray());
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		for(Column colObj:query.getColumns()){
			title=colObj.getTitle();
			if("1".equals(title.getIsshow()) && "0".equals(colObj.getTypeid()) && "0".equals(colObj.getGrouptype())==false)
			{
				retTotalMap=null;
				try
				{
					if(true==blLoad){
						retTotalMap=commService.getQueryTotalByQuery(sqlMap,colObj.getGrouptype(),colObj.getKey(),listParam.toArray());
					}
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
				if(null!=retTotalMap)
				{
					rowListMap=(List<Map>)retTotalMap.get("retMap");
					if(null!=rowListMap && 1==rowListMap.size()){
						mapRowInfo=(Map<String, Object>)rowListMap.get(0);
						request.setAttribute("_total_"+colObj.getKey(), mapRowInfo.get(colObj.getKey()));
					}
				}
			}
			if("1".equals(title.getIsshow()) && "1".equals(colObj.getTypeid()) && "0".equals(colObj.getGrouptype())==false)
			{
				blCountInfo=true;
				mapRowCountRet=new HashMap<String,Object>();
				strCountSysId=colObj.getDataSysId();
				strCountColId=colObj.getTitColId();
				strCountSqlInfo=colObj.getDataSqlInfo();
				strCountcurrColId=colObj.getCurrColId();
				if(null==strCountSysId || "".equals(strCountSysId.trim()))
				{
					blCountInfo=false;
				}
				if(null==strCountColId || "".equals(strCountColId.trim()))
				{
					blCountInfo=false;
				}
				if(null==strCountSqlInfo || "".equals(strCountSqlInfo.trim()))
				{
					blCountInfo=false;
				}
				if(null==strCountcurrColId || "".equals(strCountcurrColId.trim()))
				{
					blCountInfo=false;
				}
				if(true==blCountInfo)
				{
					retCountMap=null;
					strCountChangeSqlInfo=this.getSqlInfo(strCountSqlInfo,request,response);
					sqlCountMap.put("sysid",strCountSysId);
					sqlCountMap.put("sqllist", JSONObject.fromObject("{\"colinfo\":\""+strCountcurrColId+"\",\"sql\":\""+strCountChangeSqlInfo.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"where\":\""+pageobj.getQueryWhere().replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"orderBy\":\"\",\"pageNum\":-1,\"pageSize\":-1}"));
					try
					{
						if(true==blLoad){
							retCountMap=commService.getQueryDynamicTotalByQuery(sqlCountMap,colObj.getGrouptype(),strCountColId,strCountcurrColId,listParam.toArray());	
						}
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if(null!=retCountMap)
					{
						rowCountListMap=(List<Map>)retCountMap.get("retMap");
						if(null!=rowCountListMap && 0!=rowCountListMap.size()){
							for(int i=0;i<rowCountListMap.size();i++)
							{
								mapRowCountInfo=(Map<String, Object>)rowCountListMap.get(i);
								strCountKey=(null!=mapRowCountInfo.get(strCountColId)?mapRowCountInfo.get(strCountColId).toString():"");
								strCountValue=(null!=mapRowCountInfo.get(strCountcurrColId)?mapRowCountInfo.get(strCountcurrColId).toString():"");
								if(null!=strCountKey && "".equals(strCountKey)==false && null!=strCountValue && "".equals(strCountValue)==false)
								{
									if(null==mapRowCountRet.get(strCountKey))
									{
										mapRowCountRet.put(strCountKey, strCountValue);
									}
								}
							}
						}
					}
					request.setAttribute("_total_"+colObj.getKey(), mapRowCountRet);
				}
			}
		}
		request.setAttribute("page", pageobj);
		DynamicdataInfo(commService,pageobj,query,retMap,request,response);
	}
	
	private Query getQueryBySqlRequest(HttpServletRequest requestToExpose,LocalCommonService localcommService, CoreService coreService,Map map) {
		Query query;
		byte[] bytes;
		Integer type;
		String strEncodeMap;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("query:"+getUrl());
		bytes=(byte[])map.get("bytesinfo");
		type=(Integer)map.get("type");
		strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
		if(null!=type && 0!=type)
		{
			strEncodeMap=coreService.readEncryptSql(strEncodeMap);
		}
		if(null != element)
		{
			query=(Query)element.getObjectValue();
		}
		else
		{
			query = localcommService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
			element=new net.sf.ehcache.Element("query:"+getUrl(),query);
			cache.put(element);
		}
		return query;
	}
	
	private boolean queryToClass(String strCode, CoreService coreService,LocalCommonService localcommService,Query query, Map<String, Object> map, Map<String, String> config,HttpServletRequest request,HttpServletResponse response, String strTempJsp) {
		Boolean blInfo;
		String strJspPage;
		query.setIsUpdate(false);
		if(null==map)
		{
			map=new HashMap<String, Object>();
		}
		strJspPage=localcommService.getJspStringByQuery(query,map,config, request, response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(query.getLastmodifiedtime()));
		if(localcommService.checkClassLastmodifiedtime(strTempJsp,Long.valueOf(query.getLastmodifiedtime()),request)==true)
		{
			query.setIsUpdate(true);
			coreService.writeEncryptSql(strCode, XmlUnit.querytoXml(query, this.defaultEncoding).asXML(), "sc_query", "system");
			localcommService.writeEncryptFile(XmlUnit.querytoXml(query, this.defaultEncoding).asXML(), request.getSession().getServletContext().getRealPath(getUrl()), this.defaultEncoding);
		}
		blInfo = true;
		return blInfo;
	}
	
	private void DynamicdataInfo(CommonService commService,Page pageobj, Query query, Map<String, Object> retMap, HttpServletRequest request,HttpServletResponse response){
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
		pageInfo=null;
		hashData=new HashMap<String,Object>();
		priKey=query.getPrikey();
		initSql=query.getInitsql();
		pagestyle=query.getPagestyle();
		totNum=0;
		mapInfoList=new HashMap<String,Map>();
		if(null!=retMap)
		{
			pageInfo=(PageInfo<Map>)retMap.get("sqllist");
			retList=pageInfo.getList();
		}
		else
		{
			retList=new ArrayList<Map>();
		}
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
		rowList=commService.getQueryList(iRow,query,retList);
		if("".equals(initSql.getStrsql())==false && initSql.getStrsql()!=null && null!=retMap)
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
		if(null!=retMap)
		{
			colMap=(Map<String,Object>)retMap.get("colSearch");
		}
		else
		{
			colMap=new HashMap<String,Object>();
		}
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
					hExMap=commService.queryFullSql(titleSysId, strExSqlInfo,null);
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
							dataMap=commService.queryFullSql(colobj.getDataSysId(), strSqlDataInfo,listObj.toArray());
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
	}
	
	private String datechange(Date date, String pattern) {
		String demo;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat(pattern);  
        demo = sdf.format(date);  
        return demo;  
    }  
	
	private String getSqlInfo(String sqlinfo, HttpServletRequest request,HttpServletResponse response) {
		sqlinfo=HtmlUnit.getValueByRequest(sqlinfo, request, response);
		return sqlinfo;
	}

	private Query getQueryByRequest(HttpServletRequest request,LocalCommonService localcommService)  throws Exception {
		Query query;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("query:"+getUrl());
		if(null != element)
		{
			query=(Query)element.getObjectValue();
		}
		else
		{
			query = localcommService.formatXmltoQuery(localcommService.loadInit(request.getSession().getServletContext().getRealPath(getUrl())));
			element=new net.sf.ehcache.Element("query:"+getUrl(),query);
			cache.put(element);
		}
		return query;
	}
}
