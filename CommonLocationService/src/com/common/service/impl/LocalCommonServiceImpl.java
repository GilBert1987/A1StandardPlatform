package com.common.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.Document;
import org.xml.sax.InputSource;

import com.common.comparator.ComparatorMenuShortCut;
import com.common.comparator.ComparatorMenuSubMenu;
import com.common.comparator.ComparatorQueryButton;
import com.common.comparator.ComparatorQuerySearch;
import com.common.comparator.ComparatorQueryTitle;
import com.common.comparator.ComparatorToolItem;
import com.common.comparator.ComparatorTreeButton;
import com.common.dao.RedisDao;
import com.common.entity.activiti.WorkFlow;
import com.common.entity.firebutton.Button;
import com.common.entity.form.DynamicParm;
import com.common.entity.form.Form;
import com.common.entity.frame.Frame;
import com.common.entity.frame.FrameNode;
import com.common.entity.menu.Menu;
import com.common.entity.menu.ShortCut;
import com.common.entity.menu.SubMenu;
import com.common.entity.query.ChoiceTime;
import com.common.entity.query.Column;
import com.common.entity.query.PriKey;
import com.common.entity.query.Query;
import com.common.entity.query.QueryButton;
import com.common.entity.query.Quick;
import com.common.entity.query.Search;
import com.common.entity.query.ShowStyle;
import com.common.entity.query.Title;
import com.common.entity.tool.Item;
import com.common.entity.tool.ItemHtml;
import com.common.entity.tool.ItemShow;
import com.common.entity.tool.Tool;
import com.common.entity.tool.ToolShowStyle;
import com.common.entity.tree.Tree;
import com.common.entity.tree.TreeHtml;
import com.common.entity.tree.TreeNode;
import com.common.entity.tree.TreeSqlInfo;
import com.common.iface.ILastmodifiedTime;
import com.common.jasper.JspC;
import com.common.jasper.compiler.JspCompilationContext;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.DateConverter;
import com.common.tool.PropertyType;
import com.common.tool.XmlUnit;
import com.common.unit.EncodeUtil;
import com.common.unit.FileUtil;
import com.common.unit.HtmlUnit;
import com.common.unit.JsonUnit;

public class LocalCommonServiceImpl implements LocalCommonService {

	private static Logger log = Logger.getLogger(LocalCommonServiceImpl.class);
	
	private CommonService commonService;
	
	@Resource
	private CoreService coreService;
	
	@Resource
	private RedisDao redisDao;
		
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public Boolean existsFile(String strPath) {
		return XmlUnit.existsFile(strPath);
	}
	
	public Map saveMapByAttr(Map mapInfo,String name,String value){
		Object[] objList;
		objList=new Object[1];
		objList[0]=value;
		mapInfo.put(name,objList);
		return mapInfo;
	}
	
	public List<Map> loadMapByReadisKey(String strKey){
		List<Map> list;
		Map<String,Object> obj;
		obj=(Map<String,Object>)redisDao.load(strKey);
		redisDao.delete(strKey);
		list=(List<Map>)obj.get("retMap");
		return list;
	}
	
	@Override
	public void saveRedisObj(Map mapInfo, HttpServletRequest request)
	{
		String strKey;
		strKey="";
		try {
			strKey=commonService.getKeybyTabCol("com", "sc_redis", "id");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		redisDao.addOrUpdate(strKey, mapInfo);
		request.setAttribute("redisKey", strKey);
	}

	
	@Override
	public String saveMapByReadisKey(List<Map> list){
		String strKey;
		strKey="";
		try {
			strKey=commonService.getKeybyTabCol("com", "sc_redis", "id");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		redisDao.addOrUpdate(strKey, list);
		return strKey;
	}
	
	// 通用查询SQL
	@Override
	public StringBuilder execTableInfo(String name, Form form,
			HttpServletRequest request) {
		String strKey;
		String strSqlInfo;
		List<Map> listMap;
		List<Map> listDb;
		Object[] params;
		Object[] sysParams;
		List<DynamicParm> listDparm;
		StringBuilder strSql;
		StringBuilder strResult;
		listDb=null;
		listMap=null;
		strSql = new StringBuilder();
		strResult = new StringBuilder();
		params=new Object[]{
			name	
		};
		strSqlInfo="SELECT dbinfo FROM sc_database WHERE dbname=?";
		try
		{
			listDb = commonService.queryListSql("com", strSqlInfo,params);
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
		}
		if(null!=listDb && listDb.size()!=0)
		{
			sysParams=new Object[]{
				listDb.get(0).get("dbinfo"),
				form.getDataname()
			};
			strResult.append("select ");
			strSql.append("SELECT COLUMN_NAME ");
			strSql.append("FROM information_schema.COLUMNS ");
			strSql.append("WHERE (1=1) AND TABLE_SCHEMA=? AND Table_Name=?");
			try
			{
				listMap = commonService.queryListSql(name, strSql.toString(),sysParams);
			}
			catch(Exception e){
				log.error(e.getMessage(),e);
			}
			if(null!=listMap)
			{
				for (Map<String, Object> hMap : listMap) {
					strKey = "";
					for (Map.Entry<String, Object> entry : hMap.entrySet()) {
						if ("COLUMN_NAME".equals(entry.getKey())) {
							strKey = entry.getValue().toString();
							strResult.append(" " + strKey + " ,");
							continue;
						}
					}
				}
				strResult.deleteCharAt(strResult.length() - 1);
				strResult.append(" from " + form.getDataname() + " where (1=1)");
				listDparm = form.getDyparm();
				for (DynamicParm Dparm : listDparm) {
					strResult.append(" and (" + Dparm.getValue() + "='"
							+ request.getParameter(Dparm.getKey()) + "')");
				}
			}
		}
		return strResult;
	}
	
	@Override
	public void writeJs(String jsInfo, HttpServletRequest request) {
		Object[] objlist;
		Map<String,Object[]> mapInfo;
		objlist=new Object[1];
		mapInfo=new HashMap<String,Object[]>();
		jsInfo=XmlUnit.getDeXmlString(jsInfo);
		jsInfo=HtmlUnit.getValueByRequest(jsInfo, request, null);
		objlist[0]=jsInfo;
		mapInfo.put("map._backjs", objlist);
		setRequestAttrByMap(request,mapInfo,"${map._backjs}","_backjs");
	}
	
	@Override
	public void setRequestAttrByMap(HttpServletRequest request,Map<String,Object[]> mapInfo,String mapName,String attrName){
		Object[] objArr;
		mapName=HtmlUnit.ascii2Native(mapName);
		objArr=HtmlUnit.getObejctArrByMap(mapName,mapInfo);
		if(null!=objArr)
		{
			if(objArr.length==1)
			{
				request.setAttribute(attrName, objArr[0]);
			}
			if(objArr.length>1)
			{
				request.setAttribute(attrName, objArr);
			}
		}
	}
	
	@Override
	public String dataBindInfo(HttpServletRequest request, String dataKey) {
		String strKey;
		List<Map> listMap;
		listMap = null;
		if (request.getAttribute("_dataInfo") != null) {
			listMap = (List<Map>) request.getAttribute("_dataInfo");
		}
		strKey = "";
		if (listMap != null) {
			for (Map<String, Object> hMap : listMap) {
				strKey = "";
				for (Map.Entry<String, Object> entry : hMap.entrySet()) {
					if (dataKey.equals(entry.getKey())) {
						if(null!=entry.getValue()){
							strKey = entry.getValue().toString();
						}
						else
						{
							strKey="";
						}
						continue;
					}
				}
				if ("".equals(strKey) == false) {
					break;
				}
			}
		}
		return strKey;
	}
	
	@Override
	public Map<String, Object> getVariableMap(HttpServletRequest request) {
		String data;
		Class<?> targetType;
		Object objectValue;
		Map<String, Object> variables;
		JSONArray jsonArray;
		JSONObject jsObject;
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		variables = new HashMap<String, Object>();
		data = "";
		if (request.getParameter("formdata") != null) {
			data = request.getParameter("formdata");
		}
		if ("".equals(data) == false) {
			jsonArray = JSONArray.fromObject(data);
			for (int i = 0; i < jsonArray.size(); i++) {
				jsObject = (JSONObject) jsonArray.get(i);
				targetType = Enum.valueOf(PropertyType.class,
						jsObject.getString("type")).getValue();
				objectValue = ConvertUtils.convert(jsObject.getString("value"),
						targetType);
				variables.put(jsObject.getString("key"), objectValue);
			}
		}
		return variables;
	}
	
	@Override
	public String getUrlInfoByMap(String urlinfo, Map<String,Object[]> mapInfo) {
		return HtmlUnit.getValueByMap(urlinfo, mapInfo);
	}

	@Override
	public String getUrlInfoByParam(String urlinfo, HttpServletRequest request) {
		String strUrl;
		String strKeyInfo;
		Boolean blInfo;
		String[] strUrlInfo;
		Enumeration enumName;
		Map<String, String> param;
		strUrlInfo=null;
		param=null;
		if(urlinfo.indexOf("?")>0)
		{
			strUrlInfo=new String[2];
			strUrlInfo[0]=urlinfo.substring(0, urlinfo.indexOf("?"));
			strUrlInfo[1]=urlinfo.substring(urlinfo.indexOf("?"),urlinfo.length());
		}
		if(strUrlInfo!=null)
		{
			strUrl=strUrlInfo[0];
		}
		else
		{
			strUrl=urlinfo;
		}
		try
		{
			param=coreService.URLRequest(urlinfo);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		enumName=request.getParameterNames();
		while(enumName.hasMoreElements())
		{
			blInfo=false;
			strKeyInfo=enumName.nextElement().toString();
			if(null!=param){
				for(Entry<String, String> entry:param.entrySet()){
					if(entry.getKey().toLowerCase().equals(strKeyInfo.toLowerCase())==true)
					{
						blInfo=true;
						break;
					}
				}
			}
			if(strUrl.indexOf("?")==-1)
			{
				strUrl+="?"+strKeyInfo+"="+request.getParameter(strKeyInfo);
			}
			else
			{
				strUrl+="&"+strKeyInfo+"="+request.getParameter(strKeyInfo);
			}
			if(blInfo==true)
			{
				param.remove(strKeyInfo);
			}
		}
		for(Entry<String, String> entry:param.entrySet()){
			if(strUrl.indexOf("?")==-1)
			{
				strUrl+="?"+entry.getKey()+"="+entry.getValue();
			}
			else
			{
				strUrl+="&"+entry.getKey()+"="+entry.getValue();
			}
		}
		return strUrl;
	}

	@Override
	public void delXmlFileByPath(String path,HttpServletRequest request)
	{
		FileUtil.deleteXML(request.getSession().getServletContext().getRealPath(path));
	}

	@Override
	public void getTabKeyByRequest(String name, String strTable, String strCol,HttpServletRequest request) {
		String strKey;
		String jsonData;
		Object[] objlist;
		Map<String,Object[]> mapInfo;
		strKey="";
		objlist=new Object[1];
		mapInfo=new HashMap<String,Object[]>();
		try{
			strKey=commonService.getKeybyTabCol(name, strTable, strCol);
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		jsonData="{\"dataKey\":\""+strKey+"\"}";
		objlist[0]=JsonUnit.parseJSONToMap(jsonData);
		mapInfo.put("map._saveObj", objlist);
		setRequestAttrByMap(request,mapInfo,"${map._saveObj}","_saveObj");
	}

	@Override
	public void saveformAndGenClass(String strCode,String strJSPPath,String configInfo,HttpServletRequest request,HttpServletResponse response){
		Form form;
		String strKey;
		String strValue;
		String strJspInfo;
		String strConPath;
		String strEncode;
		String strPrefix;
		String strSuffix;
		JSONObject jsonConfig;
		Iterator itConfig;
		Map<String, String> config;
		form=null;
		strEncode="UTF-8";
		strConPath="";
		strPrefix="";
		strSuffix="";
		config=new HashMap<String,String>();
		if("".equals(configInfo)==false)
		{
			jsonConfig=JSONObject.fromObject(configInfo);
			itConfig=jsonConfig.keys();
			while(itConfig.hasNext())
			{
				strKey=itConfig.next().toString();
				strValue=jsonConfig.getString(strKey);
				config.put(strKey, strValue);
				if("defaultEncoding".equals(strKey)==true){
					strEncode=strValue;
				}
				if("prefix".equals(strKey)==true){
					strPrefix=strValue;
				}
				if("suffix".equals(strKey)==true){
					strSuffix=strValue;
				}
			}
			try {
				strConPath=strPrefix+strCode+strSuffix;
				form = XmlUnit.formatXmltoForm(XmlUnit.loadInit(request.getSession().getServletContext().getRealPath(strConPath)));
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			if(null!=form ){
				strJspInfo=getJspStringByForm(form,config,request,response);
				if("".equals(strJspInfo)==false)
				{
					generateClass(strEncode,strJspInfo,strJSPPath,Long.valueOf(form.getLastmodifiedtime()), request);
				}
			}
		}
	}

	@Override
	public void generateClass(String SourceEnc, String jspContent,String JspFile,Long LastModified, HttpServletRequest request) {
		JspC jspc;
		String strUriRoot;
		String strOutRoot;
		StringBuilder jspBuilder;
		ServletContext servletContext;
		jspc=new JspC();
		jspBuilder=new StringBuilder();
		jspBuilder.append(jspContent);
		servletContext=request.getSession().getServletContext();
		strUriRoot=servletContext.getRealPath("/");
		strOutRoot=servletContext.getAttribute("javax.servlet.context.tempdir").toString();
		jspc.setSourceEnc(SourceEnc);
		jspc.setJspContent(jspBuilder);
		jspc.setUriroot(strUriRoot);
		jspc.setOutputDir(strOutRoot);
		jspc.setJspFiles(JspFile);
		jspc.setJspContentLastModified(LastModified);
		jspc.setCompile(true);
		jspc.execute();
	}
	
	@Override
	public Boolean checkClassLastmodifiedtime(String strSourcePath,Long sourcelasttime,HttpServletRequest request) {
		JspC jspc;
		Boolean blInfo;
		File targetfile;
		String strUriRoot;
		String strOutRoot;
		String strClassName;
		StringBuilder jspBuilder;
		ServletContext servletContext;
		JspCompilationContext context;
		jspc=new JspC();
		jspBuilder=new StringBuilder();
		servletContext=request.getSession().getServletContext();
		strUriRoot=servletContext.getRealPath("/");
		strOutRoot=servletContext.getAttribute("javax.servlet.context.tempdir").toString();
		jspc.setJspContent(jspBuilder);
		jspc.setUriroot(strUriRoot);
		jspc.setOutputDir(strOutRoot);
		jspc.setJspFiles(strSourcePath);
		blInfo=false;
		context=new JspCompilationContext(strSourcePath,jspc,request.getSession().getServletContext(),null,null);
		strClassName=context.getClassFileName();
		targetfile=new File(strClassName);
		if(targetfile.exists()==false || sourcelasttime.equals(targetfile.lastModified())==false){
			blInfo=true;
		}
		return blInfo;
	}
	
	@Override
	public String getJspStringByForm(Form form, Map<String, String> config, HttpServletRequest request,HttpServletResponse response) {
		int iShowtype;
		Button btnInfo;
		Iterator<String> itstring;
		StringBuilder strTemp;
		StringBuilder strBuild;
		StringBuilder strJsInfo;
		StringBuilder strbtnInfo;
		StringBuilder strFrom;
		StringBuilder strHiddenFrom;
		List<Button> listBtn;
		List<String> listJs;
		Map<String, List<Map<String, String>>> mapAuthority;
		String strTag;
		String strFine;
		String strTemplatepage = "";
		String strJstag = "";
		String strFormtag = "";
		String strTaglist = "";
		String strAuthority="";
		String strTempPageInfo="";
		String strEncode ="UTF-8";
		String strJsbutton ="";
		String strButtontag ="";
		String strHiddentag ="";
		String strFormname ="";
		String strTitlename ="";
		String strShowbutton ="";
		String strShowformname ="";
		strBuild = new StringBuilder();
		strTemp = new StringBuilder();
		strJsInfo = new StringBuilder();
		strbtnInfo = new StringBuilder();
		strFrom = new StringBuilder();
		strHiddenFrom = new StringBuilder();
		iShowtype=0;
		mapAuthority=null;
		strTemplatepage = config.get("templatepage");
		strJstag = config.get("jstag");
		strFormtag = config.get("formtag");
		strTaglist = config.get("taglist");
		strEncode = config.get("defaultEncoding");
		strJsbutton=config.get("jsbutton");
		strButtontag=config.get("buttontag");
		strHiddentag=config.get("hiddentag");
		strFormname=config.get("formname");
		strTitlename=config.get("titlename");
		strShowbutton=config.get("showbutton");
		strShowformname=config.get("showformname");
		itstring = form.getTags().iterator();
		while (itstring.hasNext()) {
			strTag = itstring.next();
			strTemp.append(strTag+"\r\n");
		}
		itstring = form.getHiddenform().iterator();
		while (itstring.hasNext()) {
			strTag = itstring.next();
			strHiddenFrom.append(strTag+"\r\n");
		}
		try
		{
			strTempPageInfo=FileUtil.readFileToString(request.getSession().getServletContext().getRealPath(strTemplatepage),strEncode);
		}
		catch(IOException e){
			log.error(e.getMessage(),e);
		}
		strFrom.append(form.getDynamicform());
		strBuild.append(strTempPageInfo);
		strFine = strBuild.toString().replace(strJstag, form.getJstag());
		strFine = strFine.replace(strFormtag, strFrom.toString());
		strFine = strFine.replace(strTaglist, strTemp.toString());
		listBtn = form.getButtons();
		for (int i = 0; i < listBtn.size(); i++) {
			btnInfo = listBtn.get(i);
			listJs = btnInfo.getJsFuction();
			strJsInfo.append("if(id=='" + btnInfo.getId() + "'){\r\n");
			for (int j = 0; j < listJs.size(); j++) {
				strJsInfo.append("if(true==blInfo && "
						+ listJs.get(j).replaceAll("\r", "")
								.replaceAll("\n", "").trim()
						+ "==false){blInfo = false;}");
			}
			strJsInfo.append("\r\n}\r\n");
			strbtnInfo.append("<"+btnInfo.getTag()+" id=\"" + btnInfo.getId()
					+ "\" clientclick=\"btnSubmit('" + btnInfo.getId() + "');\" />");
		}
		strFine=strFine.replace(strJsbutton, strJsInfo.toString());
		strFine=strFine.replace(strButtontag, strbtnInfo.toString());
		strFine=strFine.replace(strHiddentag, strHiddenFrom.toString());
		strFine=strFine.replace(strFormname, form.getName());
		strFine=strFine.replace(strTitlename, form.getTitle());
		strFine=strFine.replace(strShowbutton, form.getShowButton());
		strFine=strFine.replace(strShowformname, form.getShowFormName());
		strAuthority=request.getAttribute("_authority").toString();
		mapAuthority=XmlUnit.getAuthorityElement(form.getAuthoritys(), strAuthority);
		for(Map<String,String> econtrol:form.getControls())
		{
			iShowtype = 0;
			if("".equals(econtrol.get("id"))==false){
				if(mapAuthority!=null)
				{
					iShowtype=getShowType(request,response, mapAuthority,econtrol.get("id"));
					request.setAttribute("_"+econtrol.get("id")+"_show", iShowtype);
				}
				strFine = XmlUnit.changeHTMLTag(strFine, econtrol);
			}
		}
		if(log.isDebugEnabled()==true)
		{
			log.debug("form page");
			log.debug(strFine);
		}
		return strFine;
	}

	@Override
	public String getJspStringByQuery(Query query,Map<String, Object> retMap,Map<String, String> config,HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder strBuild;
		String strJstag = "";
		String strTemplatepage = "";
		String strFine = "";
		String strHead = "";
		String strButtonlist = "";
		String strTableHead = "";
		String strTableTotal = "";
		String strTitlename = "";
		String strQuickquery = "";
		String strQuery = "";
		String strDefaultEncoding ="UTF-8";
		strBuild = new StringBuilder();
		strTitlename = config.get("titlename");
		strTemplatepage = config.get("templatepage");
		strJstag = config.get("jstag");
		strHead = config.get("head");
		strButtonlist = config.get("buttonlist");
		strTableHead = config.get("tableHead");
		strTableTotal = config.get("tableTotal");
		strQuickquery = config.get("quickquery");
		strQuery = config.get("query");
		strDefaultEncoding = config.get("defaultEncoding");
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession().getServletContext().getRealPath(strTemplatepage)),strDefaultEncoding));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		strFine = strBuild.toString();
		strFine = strFine.replace(strJstag, query.getJsfunction());
		strFine = strFine.replace(strHead, query.getHead());
		strFine = strFine.replace(strTitlename, query.getTitle());
		strFine = strFine.replace(strButtonlist, getButtonlistByQuery(query));
		strFine = strFine.replace(strQuery,getQueryByQuery(query,strDefaultEncoding));
		strFine = strFine.replace(strQuickquery,getQuickqueryByQuery(query,retMap,request));
		strFine = strFine.replace(strTableHead, getTableHeadByQuery(query));
		strFine = strFine.replace(strTableTotal, getTableTotalByQuery(query));
		if(log.isDebugEnabled()==true)
		{
			log.debug("query page");
			log.debug(strFine);
		}
		return strFine;
		
	}
	
	private String getTableTotalByQuery(Query query) {
		int iWidth;
		Title title;
		PriKey priKey;
		int iMultiple;
		int iHiddenCount;
		String strResult;
		Boolean blHasCount;
		Boolean blHasNumber;
		Boolean blHasPriKey;
		Boolean blHasTotalInfo;
		List<Column> columnList;
		List<ShowStyle> showlist;
		StringBuilder strResultInfo;
		ComparatorQueryTitle comparColumn;
		columnList=query.getColumns();
		showlist=null;
		iHiddenCount=0;
		blHasNumber=false;
		blHasPriKey=false;
		blHasTotalInfo=false;
		strResultInfo=new StringBuilder();
		priKey=query.getPrikey();
		comparColumn=new ComparatorQueryTitle();
		if(query.getPagestyle()!=null)
		{
			showlist=query.getPagestyle().getShowlist();
		}
		Collections.sort(columnList, comparColumn);
		for(Column colObj :columnList){
			title=colObj.getTitle();
			if("1".equals(title.getIsshow()) && "".equals(colObj.getGrouptype())==false && "0".equals(colObj.getGrouptype())==false)
			{
				blHasTotalInfo=true;
				break;
			}
		}
		if(true==blHasTotalInfo)
		{
			if(showlist!=null)
			{
				for(ShowStyle sstyle:showlist)
				{
					if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow())){
						blHasNumber=true;
						break;
					}
				}
			}
			strResultInfo.append("<tr class='ui-jqgrid-labels'>");
			if("".equals(priKey.getKey())==false && "".equals(priKey.getType())==false && "0".equals(priKey.getType())==false){
				strResultInfo.append("<th class=\"ui-th-column ui-th-ltr\" ");
				if(true==blHasNumber)
				{
					strResultInfo.append(" data-width=\"120px\" w=\"120\" width=\"120px\" ");
					strResultInfo.append(" data-colspan=\"2\" ");
				}
				else
				{
					strResultInfo.append(" data-width=\"60px\" w=\"60\" width=\"60px\" ");
				}
				strResultInfo.append(">合计");
				strResultInfo.append("</th>");
				blHasPriKey=true;
			}
			if(showlist!=null)
			{
				for(ShowStyle sstyle:showlist)
				{
					if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow()) && false==blHasPriKey){
						strResultInfo.append("<th class='ui-th-column ui-th-ltr' data-width=\"60px\" w=\"60\" width=\"60px\" >合计</th>");
						break;
					}
				}
			}
			for(Column col:columnList){
				iWidth=1;
				title=col.getTitle();
				try
				{
					iMultiple=Integer.parseInt(title.getWidth());
				}
				catch(Exception e)
				{
					iMultiple=1;
					log.error(e.getMessage(),e);
					log.error("宽度计算错误!");
				}
				iWidth=iWidth*iMultiple;
				if("1".equals(col.getTypeid()))
				{
					blHasCount=true;
					if(null==col.getDataSysId() || "".equals(col.getDataSysId().trim()))
					{
						blHasCount=false;
					}
					if(null==col.getTitColId() || "".equals(col.getTitColId().trim()))
					{
						blHasCount=false;
					}
					if(null==col.getRelColId() || "".equals(col.getRelColId().trim()))
					{
						blHasCount=false;
					}
					if(null==col.getCurrColId() || "".equals(col.getCurrColId().trim()))
					{
						blHasCount=false;
					}
					if(null ==col.getGrouptype())
					{
						blHasCount=false;
					}
					if("0".equals(col.getGrouptype())==true)
					{
						blHasCount=false;
					}
					if("".equals(col.getGrouptype())==true)
					{
						blHasCount=false;
					}
					if(false==blHasCount)
					{
						strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' style='text-align:center;' ");
						if("1".equals(title.getIsshow())==true){
							strResultInfo.append(" data-visible='true' ");
							strResultInfo.append(" data-width=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}px\" w=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}\" width=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}px\" ");
						}
						else
						{
							strResultInfo.append(" data-visible='false' ");
						}
						strResultInfo.append(" data-colspan=\"${fn:length(requestScope."+col.getKey()+")}\" ");
						strResultInfo.append(">");
						strResultInfo.append("</th>");
					}
					else
					{
						strResultInfo.append("<c:forEach var='row' items='${requestScope."+col.getKey()+"}'>");
						strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='_total_"+col.getKey()+"_${row.code}' style='text-align:center;' ");
						strResultInfo.append(">");
						strResultInfo.append("${requestScope._total_"+col.getKey()+"[row.code]}");
						strResultInfo.append("</th>");
						strResultInfo.append("</c:forEach>");
					}
				}
				else
				{
					if("0".equals(title.getIsshow())==false){
						strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' style='text-align:center;' ");
						strResultInfo.append(" data-visible='true' ");
						strResultInfo.append(" data-width=\""+iWidth+"px\" w=\""+iWidth+"\" width=\""+iWidth+"px\" ");
						strResultInfo.append(">");
						strResultInfo.append("${requestScope._total_"+col.getKey()+"}");
						strResultInfo.append("</th>");
					}
					else
					{
						iHiddenCount++;
					}
				}
			}
			if(0!=iHiddenCount)
			{
				strResultInfo.append("<th class='ui-th-column ui-th-ltr' style='text-align:center;display:none;' ");
				strResultInfo.append(" data-colspan=\""+iHiddenCount+"\" data-visible='true' ");
				strResultInfo.append(" data-width=\"0px\" w=\"0\" width=\"0px\" ");
				strResultInfo.append(">");
				strResultInfo.append("</th>");
			}
			strResultInfo.append("</tr>");
		}
		strResult=strResultInfo.toString();
		return strResult;
	}

	@Override
	public String getJspStringByMenu(Menu menu, Map<String, String> config,HttpServletRequest request, HttpServletResponse response) {
		int i;
		StringBuilder strBuild;
		List<SubMenu> list;
		List<SubMenu> listsubmenu;
		ComparatorMenuSubMenu comparMenuSubMenu;
		String strTemplatepage = "";
		String strFine = "";
		String strTitlename = "";
		String strShortcut = "";
		String strMenus = "";
		String strSubmenuResult="";
		String strEncode ="UTF-8";
		comparMenuSubMenu=new ComparatorMenuSubMenu();
		strBuild = new StringBuilder();
		strTitlename = config.get("titlename");
		strTemplatepage = config.get("templatepage");
		strShortcut = config.get("shortcut");
		strMenus = config.get("menus");
		strEncode = config.get("defaultEncoding");
		listsubmenu=menu.getListSubMenus();
		list=new ArrayList<SubMenu>();
		i=0;
		request.setAttribute("hasRoot", menu.getIroot());
		request.setAttribute("rootUrl", menu.getRooturl());
		for(SubMenu submenu:listsubmenu){
			if("-1".equals(submenu.getParentcode())==true && "1".equals(submenu.getIsshow())==true){
				list.add(submenu);
				continue;
			}
		}
		Collections.sort(list, comparMenuSubMenu);
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession().getServletContext().getRealPath(strTemplatepage)),strEncode));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		strFine = strBuild.toString();
		strFine = strFine.replace(strTitlename, menu.getTitle());
		try {
			strFine = strFine.replace(strShortcut, getShortcutMenu(menu,strEncode,request,response));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}
		for(SubMenu subMenu:list)
		{
			strSubmenuResult=getLoopSubMenu(menu,subMenu,(i==0),strSubmenuResult);
			i++;
		}
		strFine = strFine.replace(strMenus, strSubmenuResult);
		if(log.isDebugEnabled()==true)
		{
			log.debug("menu page");
			log.debug(strFine);
		}
		return strFine;
	}

	@Override
	public String getJspStringByFrame(Frame frame,Map<String, String> config, HttpServletRequest request,HttpServletResponse response) {
		StringBuilder strBuild;
		String strTemplatepage = "";
		String strTitlename="";
		String strFine = "";
		String strEncode = "Utf-8";
		strBuild = new StringBuilder();
		strTemplatepage = config.get("templatepage");
		strTitlename = config.get("titlename");
		strEncode = config.get("defaultEncoding");
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession().getServletContext().getRealPath(strTemplatepage)),strEncode));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		strFine = strBuild.toString();
		strFine = strFine.replace(strTitlename, frame.getName());
		setRequestData(frame,request,response);
		if(log.isDebugEnabled()==true)
		{
			log.debug("frame page");
			log.debug(strFine);
		}
		return strFine;
	}

	@Override
	public String getJspStringByTree(Tree tree, Map<String, String> config,HttpServletRequest request, HttpServletResponse response)
	{
		StringBuilder strBuild;
		String strTemplatepage = "";
		String strTitlename="";
		String strJstag="";
		String strFine = "";
		String strEncode = "UTF-8";
		strBuild = new StringBuilder();
		strTemplatepage = config.get("templatepage");
		strTitlename = config.get("titlename");
		strJstag = config.get("jstag");
		strEncode = config.get("defaultEncoding");
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession().getServletContext().getRealPath(strTemplatepage)),strEncode));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		strFine = strBuild.toString();
		strFine = strFine.replace(strTitlename, tree.getName());
		strFine = strFine.replace(strJstag, tree.getJsinfo());
		setTreeData(tree,request,response);
		if(log.isDebugEnabled()==true)
		{
			log.debug("tree page");
			log.debug(strFine);
		}
		return strFine;
	}
	
	@Override
	public String getJspStringByTool(Tool tool, Map<String, String> config,HttpServletRequest request, HttpServletResponse response) {
		StringBuilder strBuild;
		String strTemplatepage = "";
		String strTitlename="";
		String strFine = "";
		String strEncode="UTF-8";
		strBuild = new StringBuilder();
		strTemplatepage = config.get("templatepage");
		strTitlename = config.get("titlename");
		strEncode = config.get("defaultEncoding");
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession().getServletContext().getRealPath(strTemplatepage)),strEncode));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		strFine = strBuild.toString();
		strFine = strFine.replace(strTitlename, tool.getName());
		setToolData(tool,request,response);
		if(log.isDebugEnabled()==true)
		{
			log.debug("tool page");
			log.debug(strFine);
		}
		return strFine;
	}

	@Override
	public String getJspStringByWF(WorkFlow wf, Map<String, Map<String, List<Map<String, String>>>> authoritys,Map<String, String> config, HttpServletRequest request,HttpServletResponse response) {
		int iShowtype;
		Button btnInfo;
		Iterator<String> itstring;
		StringBuilder strTemp;
		StringBuilder strBuild;
		StringBuilder strJsInfo;
		StringBuilder strFrom;
		StringBuilder strHiddenFrom;
		List<Button> listBtn;
		List<String> listJs;
		Map<String, List<Map<String, String>>> mapAuthority;
		String strbtnInfo;
		String strTag;
		String strFine;
		String strTemplatepage = "";
		String strJstag = "";
		String strFormtag = "";
		String strTaglist = "";
		String strAuthority="";
		String strHiddentag = "";
		String strJsbutton = "";
		String strTitlename = "";
		String strEncode = "UTF-8";
		strBuild = new StringBuilder();
		strTemp = new StringBuilder();
		strJsInfo = new StringBuilder();
		strFrom = new StringBuilder();
		strHiddenFrom = new StringBuilder();
		iShowtype=0;
		mapAuthority=null;
		strTemplatepage = config.get("templatepage");
		strJstag = config.get("jstag");
		strFormtag = config.get("formtag");
		strTaglist = config.get("taglist");
		strHiddentag = config.get("hiddentag");
		strJsbutton = config.get("jsbutton");
		strTitlename = config.get("titlename");
		strEncode=config.get("defaultEncoding");
		itstring = wf.getTags().iterator();
		while (itstring.hasNext()) {
			strTag = itstring.next();
			strTemp.append(strTag+"\r\n");
		}
		itstring = wf.getHiddenform().iterator();
		while (itstring.hasNext()) {
			strTag = itstring.next();
			strHiddenFrom.append(strTag+"\r\n");
		}
		strFrom.append(wf.getDynamicform());
		try {
			strBuild.append(FileUtil.readFileToString((request.getSession()
					.getServletContext().getRealPath(strTemplatepage)),strEncode));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		strFine = strBuild.toString().replace(strJstag, wf.getJstag());
		strFine = strFine.replace(strFormtag, strFrom.toString());
		strFine = strFine.replace(strTaglist, strTemp.toString());
		listBtn = wf.getButtons();
		for (int i = 0; i < listBtn.size(); i++) {
			btnInfo = listBtn.get(i);
			listJs = btnInfo.getJsFuction();
			strJsInfo.append("if(id=='" + btnInfo.getId() + "'){\r\n");
			for (int j = 0; j < listJs.size(); j++) {
				strJsInfo.append("if("
						+ listJs.get(j).replaceAll("\r", "")
								.replaceAll("\n", "").trim()
						+ "==false){return false;}");
			}
			strJsInfo.append("\r\n}\r\n");
			strbtnInfo="<"+btnInfo.getTag()+" id=\"" + btnInfo.getId()
					+ "\" clientclick=\"btnSubmit('" + btnInfo.getId() + "');\" ";
			strFine=strFine.replaceAll("<"+btnInfo.getTag()+"[^>]+id=\"" + btnInfo.getId() + "\"", strbtnInfo);
		}
		strFine = strFine.replace(strJsbutton, strJsInfo.toString());
		strFine = strFine.replace(strHiddentag, strHiddenFrom.toString());
		strFine = strFine.replace(strTitlename, wf.getName());
		strAuthority=(request.getAttribute("_authority")==null?"":request.getAttribute("_authority").toString());
		mapAuthority=XmlUnit.getAuthorityElement(authoritys, strAuthority);
		for(Map<String,String> econtrol:wf.getControls())
		{
			iShowtype = 0;
			if("".equals(econtrol.get("id"))==false){
				if(mapAuthority!=null)
				{
					iShowtype=getShowType(request,response, mapAuthority,econtrol.get("id"));
					request.setAttribute("_"+econtrol.get("id")+"_show", iShowtype);
				}
			}
		}
		if(log.isDebugEnabled()==true)
		{
			log.debug("wf page");
			log.debug(strFine);
		}
		return strFine;
	}
	
	private void setToolData(Tool tool,HttpServletRequest request,HttpServletResponse response) {
		int i;
		String strUrl;
		List<ItemHtml> listHtml;
		ItemShow itemShow;
		ItemHtml itemHtml;
		ToolShowStyle toolStyle;
		String strstyle;
		ComparatorToolItem comptoolItem;
		i=0;
		toolStyle=null;
		comptoolItem=new ComparatorToolItem();
		listHtml=new ArrayList<ItemHtml>();
		Collections.sort(tool.getListitem(), comptoolItem);
		strstyle=request.getParameter("toolstyle")==null?"":request.getParameter("toolstyle");
		if("".equals(strstyle)==false)
		{
			for(ToolShowStyle style:tool.getListstyle()){
				if(strstyle.equals(style.getCode())==true){
					toolStyle=style;
					break;
				}
			}
		}
		for(Item item:tool.getListitem()){
			strUrl=item.getUrl();
			strUrl=getUrlInfo(strUrl,request,response);
			itemHtml=new ItemHtml();
			itemHtml.setCode(item.getCode());
			itemHtml.setName(item.getName());
			itemHtml.setUrl(strUrl);
			itemHtml.setOrder(item.getOrder());
			itemHtml.setCode(item.getCode());
			itemHtml.setBtnclass(item.getBtnclass());
			itemHtml.setIcon(item.getIcon());
			itemHtml.setIconstyle(item.getIconstyle());
			itemHtml.setIsshow("true");
			if(i==0)
			{
				itemHtml.setIsselect("true");
			}
			else
			{
				itemHtml.setIsselect("false");
			}
			if(toolStyle!=null){
				itemShow=getItemShow(item,toolStyle.getListshow());
				if(itemShow!=null)
				{
					itemHtml.setIsshow(itemShow.getIsshow());
					itemHtml.setIsselect(itemShow.getIsselect());
				}
			}
			listHtml.add(itemHtml);
			i++;
		}
		request.setAttribute("_listHtml", listHtml);
	}
	
	private ItemShow getItemShow(Item item,List<ItemShow> listshow){
		ItemShow itemShow;
		itemShow=null;
		for(ItemShow shownode:listshow){
			if(shownode.getCode().equals(item.getCode())==true){
				itemShow=shownode;
				break;
			}
		}
		return itemShow;
	}
	
	private void setTreeData(Tree tree,HttpServletRequest request,HttpServletResponse response) {
		TreeHtml treeHtml;
		List<TreeNode> listNode;
		List<TreeHtml> listHtml;
		ComparatorTreeButton comparatorTreeButton;
		Enumeration<String> enuparam;
		TreeNode nodeInfo;
		String strRoot;
		String strparam;
		Map<String,String> mapparam;
		nodeInfo=null;
		comparatorTreeButton = new ComparatorTreeButton();
		mapparam=new HashMap<String,String>();
		listHtml=new ArrayList<TreeHtml>();
		listNode=tree.getListNode();
		for(TreeNode node:listNode){
			if("0".equals(node.getLevel())==true)
			{
				nodeInfo=node;
				break;
			}
		}
		strRoot=request.getParameter("rootid")==null?"":request.getParameter("rootid");
		treeHtml=new TreeHtml();
		treeHtml.setName(tree.getName());
		treeHtml.setId(strRoot);
		treeHtml.setPid(strRoot);
		treeHtml.setOpen(true);
		treeHtml.setIcon("../../css/zTreeStyle/img/diy/1_open.png");
		treeHtml.setIsParent(true);
		listHtml.add(treeHtml);
		if(nodeInfo!=null){
			getNodeList(tree.getListNode(),nodeInfo,listHtml,request,response,strRoot);
		}
		for(TreeNode node:listNode){
			Collections.sort(node.getListButton(), comparatorTreeButton);
		}
		request.setAttribute("_treenodeobj", JSONArray.fromObject(tree.getListNode()));
		request.setAttribute("_listhtml", JSONArray.fromObject(listHtml));
		enuparam=request.getParameterNames();
		while(enuparam.hasMoreElements())
		{
			strparam=enuparam.nextElement();
			mapparam.put(strparam, request.getParameter(strparam));
		}
		request.setAttribute("_urlparam",JSONObject.fromObject(mapparam));
	}
	
	
	private void getNodeList(List<TreeNode> list, TreeNode nodeInfo,
			List<TreeHtml> listHtml, HttpServletRequest request,HttpServletResponse response, String strRoot) {
		long lChildSize;
		TreeHtml treeHtml;
		TreeNode nodeNext;
		TreeNode nodeCurr;
		String strSql;
		String strValue;
		String strReValue;
		String strOption;
		String strViewpath;
		TreeSqlInfo treeSql;
		List<Map> resultMap;
		strValue="";
		resultMap=null;
		strViewpath=request.getAttribute("_viewpath").toString();
		treeSql=nodeInfo.getSqlInfo();
		nodeNext=getNextNode(list,nodeInfo);
		strOption="".equals(nodeInfo.getOption())==true?"0":nodeInfo.getOption();
		if(treeSql != null && "sql".equals(treeSql.getType())==true){
			strSql=getSqlInfo(treeSql.getSqlInfo(),request,response,strRoot);
			try {
				resultMap=commonService.queryListSql(treeSql.getDatabase(),strSql,null);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
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
				setTreeOption(strOption,treeHtml,"false".equals(nodeInfo.getAsync()));
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
				if("".equals(nodeInfo.getTarget())==true)
				{
					treeHtml.setTarget(strViewpath+"_Main");
				}
				else
				{
					treeHtml.setTarget(nodeInfo.getTarget());
				}
				nodeCurr="1".equals(nodeInfo.getType())==true?nodeInfo:nodeNext;
				lChildSize=0;
				if(nodeCurr!=null){
					lChildSize=getChildCount(nodeCurr,request,response,treeHtml.getId());
				}
				if(lChildSize!=0)
				{
					treeHtml.setIsParent(true);
				}
				else{
					nodeCurr = nodeNext;
					if(nodeCurr!=null){
						lChildSize=getChildCount(nodeCurr,request,response,treeHtml.getId());
					}
					if(lChildSize!=0)
					{
						treeHtml.setIsParent(true);
					}
					else
					{
						treeHtml.setIsParent(false);
					}
				}
				if(null !=treeHtml.getUrl() && "".equals(treeHtml.getUrl())==false)
				{
					treeHtml.setUrl(HtmlUnit.getValueByRequest(treeHtml.getUrl(), request, response));
				}
				listHtml.add(treeHtml);
				if(nodeCurr!=null && "false".equals(nodeInfo.getAsync())==true && lChildSize!=0)
				{
					getNodeList(list,nodeCurr,listHtml,request,response,treeHtml.getId());
				}
			}
		}
	}
	
	private void setTreeOption(String strOption, TreeHtml treeHtml, boolean blInfo) {
		if(blInfo==true){
			if("0".equals(strOption)==true)
			{
				treeHtml.setOpen(false);
			}
			if("1".equals(strOption)==true)
			{
				treeHtml.setOpen(true);
			}
		}
		else
		{
			treeHtml.setOpen(false);
		}
	}
	private long getChildCount(TreeNode nodeNext, HttpServletRequest request,HttpServletResponse response, String id) {
		long iResult;
		String strSql;
		TreeSqlInfo treeSql;
		iResult=0;
		treeSql=nodeNext.getSqlInfo();
		if(treeSql!=null && "sql".equals(treeSql.getType())==true){
			strSql=getSqlInfo(treeSql.getSqlInfo(),request,response,id);
			try
			{
				iResult=commonService.queryCountSql(treeSql.getDatabase(),strSql,null);
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
		}
		return iResult;
	}
	private TreeNode getNextNode(List<TreeNode> list, TreeNode nodeInfo) {
		int iLevel;
		int iNextLevel;
		TreeNode nodeNext;
		String strLevel;
		String strNextLevel;
		nodeNext=null;
		strLevel=("".equals(nodeInfo.getLevel())==true?"0":nodeInfo.getLevel());
		iLevel=Integer.parseInt(strLevel);
		for(TreeNode node:list){
			strNextLevel=("".equals(node.getLevel())==true?"0":node.getLevel());
			iNextLevel=Integer.parseInt(strNextLevel);
			if((iLevel+1)==iNextLevel)
			{
				nodeNext=node;
				break;
			}
		}
		return nodeNext;
	}
	
	private String getSqlInfo(String sqlinfo, HttpServletRequest request, HttpServletResponse response, String strRoot) {
		sqlinfo=sqlinfo.replace("${tree.parent}", strRoot);
		sqlinfo=HtmlUnit.getValueByRequest(sqlinfo, request, response);
		return sqlinfo;
	}
	
	private void setRequestData(Frame frame, HttpServletRequest request,HttpServletResponse response) {
		Frame copyFrame;
		List<FrameNode> list;
		FrameNode copynode;
		copyFrame=new Frame();
		list=new ArrayList<FrameNode>();
		for(FrameNode node:frame.getNodelist()){
			copynode=new FrameNode();
			copynode.setUrl(getUrlInfo(node.getUrl(), request,response));
			copynode.setType(node.getType());
			copynode.setNumber(node.getNumber());
			list.add(copynode);
		}
		copyFrame.setName(frame.getName());
		copyFrame.setCode(frame.getCode());
		copyFrame.setType(frame.getType());
		copyFrame.setNodelist(list);
		request.setAttribute("_frame", copyFrame);
	}
	
	private String getButtonlistByQuery(Query query){
		String strResult;
		String strBtnClass;
		String strIcon;
		String strIconStyle;
		JSONObject jsObj;
		List<QueryButton> buttonlist;
		ComparatorQueryButton comparButton;
		strResult="";
		strIcon="";
		strIconStyle="";
		strBtnClass="btn btn-default";
		comparButton=new ComparatorQueryButton();
		buttonlist=query.getButtons();
		Collections.sort(buttonlist, comparButton);
		strResult="<div class=\"btn-group\">";
		for(QueryButton button:buttonlist){
			if(null!=button.getIcon() && "".equals(button.getIcon())==false){
				jsObj=JSONObject.fromObject(button.getIcon());
				strBtnClass=jsObj.getString("btnclass");
				strIcon=jsObj.getString("icon");
				strIconStyle=jsObj.getString("style");
			}
			strResult+="<button type=\"button\" class=\""+strBtnClass+" querybutton\" ";
			strResult+="onclick=\""+button.getJsclick()+"\" >";
			if(null!=strIcon && "".equals(strIcon)==false){
				strResult+="<i class=\""+strIcon+"\" style=\""+strIconStyle+"\"></i>";
			}
			strResult+=button.getName();
			strResult+="</button>";
		}
		strResult+="</div>";
		return strResult;
	}
	
	private String getQueryByQuery(Query query,String defaultEncoding){
		Search search;
		List<Column> collist;
		ComparatorQuerySearch comparSearch;
		String strResult;
		String strSelect;
		String strSelectExpress;
		String strInputValue;
		String strJson;
		String strSearchInfo;
		strResult="<div class='form-group'>";
		collist=query.getColumns();
		comparSearch=new ComparatorQuerySearch();
		Collections.sort(collist, comparSearch);
		strSelectExpress="<select id='selexperee' class='form-control'><option value=\"=\">等于</option><option value=\"like\">包含</option><option value=\"in\">分组</option></select>";
		strInputValue="<table class='form-control' style='border: 0px;vertical-align: top;padding: 0px 0px 0px 0px;'><tbody><tr><td id='tdvalue'><input id='selvalue'  class='form-control' type='input' value='' /></td></tr></tbody></table>";
		strSelect = "<select id='searchName' class='form-control' onchange='changeSearchName(this);'>";
		strSelect +="<option value=''>请选择</option>";
		for(Column colInfo:collist){
			strJson="";
			strSearchInfo="";
			search=colInfo.getSearch();
			if(search!=null)
			{
				if("1".equals(search.getIsshow())==true)
				{
					strSearchInfo=search.getSearchinfo();
					strSearchInfo=XmlUnit.getEnXmlString(strSearchInfo);
					strJson="{\\\"key\\\":\\\""+colInfo.getKey()+"\\\",\\\"sysid\\\":\\\""+query.getSysid()+"\\\",\\\"type\\\":\\\""+colInfo.getType()+"\\\",\\\"searchType\\\":\\\""+search.getType()+"\\\",\\\"searchDataType\\\":\\\""+search.getDatatype()+"\\\",\\\"searchInfo\\\":\\\""+strSearchInfo+"\\\"}";
					strSelect +="<option value='"+strJson+"'>" + colInfo.getName() + "</option>";
				}
			}
			
		}
		strSelect +="</select>";
		strResult += strSelect;
		strResult += strSelectExpress;
		strResult += strInputValue;
		strResult +="</div>";
		return strResult;
	}
	
	private String getTableHeadByQuery(Query query){
		Title title;
		String strResult;
		Boolean blHasHead;
		List<Column> columnList;
		blHasHead=false;
		columnList=query.getColumns();
		strResult="";
		for(Column colObj :columnList){
			title=colObj.getTitle();
			if("".equals(title.getSehead())==false && "1".equals(title.getIsshow()))
			{
				blHasHead=true;
				break;
			}
		}
		if(false==blHasHead)
		{
			strResult=getSingleTableHeadByQuery(query);
		}
		if(true==blHasHead)
		{
			strResult=getMultipleTableHeadByQuery(query);
		}
		return strResult;
	}
	
	private String getMultipleTableHeadByQuery(Query query){
		int iWidth;
		Title title;
		int colSpan;
		PriKey priKey;
		int iMultiple;
		int iColWidth;
		int iHiddenCount;
		int iColNowWidth;
		String strTmpKey;
		String strParCol;
		String strParKey;
		String strResult;
		String strTypeId;
		String strParTypeId;
		Boolean blHasNumber;
		Boolean blHasPriKey;
		List<Column> columnList;
		List<ShowStyle> showlist;
		StringBuilder strResultInfo;
		Map<String,Integer> mapColInfo;
		Map<String,Integer> mapColWidth;
		ComparatorQueryTitle comparColumn;
		columnList=query.getColumns();
		strParCol="";
		strParKey="";
		showlist=null;
		iHiddenCount=0;
		strParTypeId="0";
		blHasNumber=false;
		blHasPriKey=false;
		mapColWidth=new HashMap<String,Integer>();
		mapColInfo=new HashMap<String,Integer>();
		strResultInfo=new StringBuilder();
		priKey=query.getPrikey();
		comparColumn=new ComparatorQueryTitle();
		if(query.getPagestyle()!=null)
		{
			showlist=query.getPagestyle().getShowlist();
		}
		Collections.sort(columnList, comparColumn);
		for(Column colObj :columnList){
			title=colObj.getTitle();
			iColWidth=Integer.parseInt(title.getWidth());
			strTmpKey=(null==title.getSehead()?"":title.getSehead());
			if("1".equals(title.getIsshow()))
			{
				strTypeId=colObj.getTypeid();
				if(false==(strParTypeId.equals(strTypeId)))
				{
					mapColInfo.put(colObj.getKey(), 1);
					mapColWidth.put(colObj.getKey(), iColWidth);
					strParCol=colObj.getKey();
					strParKey=strTmpKey;
					strParTypeId=colObj.getTypeid();
					continue;
				}
				if(strParKey.equals(strTmpKey))
				{
					if("".equals(strParCol)==true)
					{
						mapColWidth.put(colObj.getKey(), iColWidth);
						mapColInfo.put(colObj.getKey(), 1);
						strParCol=colObj.getKey();
						strParKey=strTmpKey;
					}
					else
					{
						iColNowWidth=mapColWidth.get(strParCol);
						colSpan=mapColInfo.get(strParCol);
						colSpan++;
						iColNowWidth+=iColWidth;
						mapColWidth.put(strParCol, iColNowWidth);
						mapColInfo.put(strParCol, colSpan);
						mapColInfo.put(colObj.getKey(), 0);
					}
				}
				else
				{
					mapColInfo.put(colObj.getKey(), 1);
					mapColWidth.put(colObj.getKey(), iColWidth);
					strParCol=colObj.getKey();
					strParKey=strTmpKey;
				}
			}
		}
		strParKey="";
		if(showlist!=null)
		{
			for(ShowStyle sstyle:showlist)
			{
				if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow())){
					blHasNumber=true;
					break;
				}
			}
		}
		strResultInfo.append("<tr>");
		if("".equals(priKey.getKey())==false && "".equals(priKey.getType())==false && "0".equals(priKey.getType())==false){
			strResultInfo.append("<th class=\"querymutil ui-th-column ui-th-ltr\" data-width=\"60px\" w=\"60\" width=\"60px\">");
			strResultInfo.append("</th>");
		}
		if(showlist!=null)
		{
			for(ShowStyle sstyle:showlist)
			{
				if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow())){
					strResultInfo.append("<th class='querymutil ui-th-column ui-th-ltr' data-width=\"60px\" w=\"60\" width=\"60px\"></th>");
					break;
				}
			}
		}
		for(Column col:columnList){
			iWidth=1;
			title=col.getTitle();
			try
			{
				iMultiple=Integer.parseInt(title.getWidth());
			}
			catch(Exception e)
			{
				iMultiple=1;
				log.error(e.getMessage(),e);
				log.error("宽度计算错误!");
			}
			iWidth=iWidth*iMultiple;
			if("1".equals(col.getTypeid()))
			{
				strResultInfo.append("<c:forEach var='row' items='${requestScope."+col.getKey()+"}'>");
				if("0".equals(title.getIsshow())==false){
					strResultInfo.append("<th class='querymutil ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"_${row.code}' style='text-align:center;'>");
					strResultInfo.append("</th>");
				}
				else{
					strResultInfo.append("<th data-field='"+col.getKey()+"_${row.code}' data-visible='false' data-width=\""+iWidth+"px\" width='"+iWidth+"px' w='"+iWidth+"' style='text-align:center;display:none;' class='querymutil ui-th-column ui-th-ltr'>");
					strResultInfo.append("</th>");
				}
				strResultInfo.append("</c:forEach>");
			}
			else
			{
				if("0".equals(title.getIsshow())==false){
					strResultInfo.append("<th class='querymutil ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"' style='text-align:center;'>");
					strResultInfo.append("</th>");
				}
				else
				{
					strResultInfo.append("<th data-field='"+col.getKey()+"' data-visible='false' data-width=\""+iWidth+"px\" width='"+iWidth+"px' w='"+iWidth+"' style='text-align:center;display:none;' class='querymutil ui-th-column ui-th-ltr'>");
					strResultInfo.append("</th>");
				}
			}
		}
		strResultInfo.append("</tr>");
		strResultInfo.append("<tr class='ui-jqgrid-labels'>");
		if("".equals(priKey.getKey())==false && "".equals(priKey.getType())==false && "0".equals(priKey.getType())==false){
			strResultInfo.append("<th class=\"ui-th-column ui-th-ltr\" ");
			if(true==blHasNumber)
			{
				strResultInfo.append(" data-width=\"120px\" w=\"120\" width=\"120px\" ");
				strResultInfo.append(" data-colspan=\"2\" ");
			}
			else
			{
				strResultInfo.append(" data-width=\"60px\" w=\"60\" width=\"60px\" ");
			}
			strResultInfo.append(">");
			strResultInfo.append("</th>");
			blHasPriKey=true;
		}
		if(showlist!=null)
		{
			for(ShowStyle sstyle:showlist)
			{
				if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow()) && false==blHasPriKey){
					strResultInfo.append("<th class='ui-th-column ui-th-ltr' data-width=\"60px\" w=\"60\" width=\"60px\" ");
					strResultInfo.append("></th>");
					break;
				}
			}
		}
		for(Column col:columnList){
			iWidth=1;
			title=col.getTitle();
			try
			{
				iMultiple=Integer.parseInt(title.getWidth());
			}
			catch(Exception e)
			{
				iMultiple=1;
				log.error(e.getMessage(),e);
				log.error("宽度计算错误!");
			}
			iWidth=iWidth*iMultiple;
			if("1".equals(col.getTypeid()))
			{
				strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' style='text-align:center;' ");
				if("1".equals(title.getIsshow())==true){
					strResultInfo.append(" data-visible='true' ");
					strResultInfo.append(" data-width=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}px\" w=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}\" width=\"${fn:length(requestScope."+col.getKey()+")*"+iWidth+"}px\" ");
				}
				else
				{
					strResultInfo.append(" data-visible='false' ");
				}
				strResultInfo.append(" data-colspan=\"${fn:length(requestScope."+col.getKey()+")}\" ");
				strResultInfo.append(">");
				strResultInfo.append((null==title.getSehead()?"":title.getSehead()));
				strResultInfo.append("</th>");				
			}
			else
			{
				if("0".equals(title.getIsshow())==false){
					colSpan=mapColInfo.get(col.getKey());
					if(0!=colSpan)
					{
						iColNowWidth=mapColWidth.get(col.getKey());
						strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' style='text-align:center;' ");
						strResultInfo.append(" data-colspan=\""+colSpan+"\" data-visible='true' ");
						strResultInfo.append(" data-width=\""+iColNowWidth+"px\" w=\""+iColNowWidth+"\" width=\""+iColNowWidth+"px\" ");
						strResultInfo.append(">");
						strResultInfo.append(title.getSehead());
						strResultInfo.append("</th>");
					}
				}
				else
				{
					iHiddenCount++;
				}
			}
		}
		if(0!=iHiddenCount)
		{
			strResultInfo.append("<th class='ui-th-column ui-th-ltr' style='text-align:center;display:none;' ");
			strResultInfo.append(" data-colspan=\""+iHiddenCount+"\" data-visible='true' ");
			strResultInfo.append(" data-width=\"0px\" w=\"0\" width=\"0px\" ");
			strResultInfo.append(">");
			strResultInfo.append("</th>");
		}
		strResultInfo.append("</tr>");
		strResultInfo.append("<tr>");
		if("".equals(priKey.getKey())==false && "".equals(priKey.getType())==false && "0".equals(priKey.getType())==false){
			strResultInfo.append("<th class=\"ui-th-column ui-th-ltr\" data-width=\"60px\" w=\"60\" width=\"60px\" ");
			strResultInfo.append(">");
			if("2".equals(priKey.getType())==true)
			{
				strResultInfo.append("<input type=\"checkbox\" onclick=\"fn_selAll(this);\" />");
			}
			strResultInfo.append("选择");
			strResultInfo.append("</th>");
		}
		if(showlist!=null)
		{
			for(ShowStyle sstyle:showlist)
			{
				if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow())){
					strResultInfo.append("<th class='ui-th-column ui-th-ltr' data-width=\"60px\" w=\"60\" width=\"60px\" ");
					strResultInfo.append(">序号</th>");
					break;
				}
			}
		}
		for(Column col:columnList){
			iWidth=1;
			title=col.getTitle();
			try
			{
				iMultiple=Integer.parseInt(title.getWidth());
			}
			catch(Exception e)
			{
				iMultiple=1;
				log.error(e.getMessage(),e);
				log.error("宽度计算错误!");
			}
			iWidth=iWidth*iMultiple;
			if("1".equals(col.getTypeid()))
			{
				if("0".equals(title.getIsshow())==false){
					strResultInfo.append("<c:forEach var='row' items='${requestScope."+col.getKey()+"}'>");
					strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"_${row.code}' style='text-align:center;' ");
					strResultInfo.append(">");
					strResultInfo.append("${row.value}");
					strResultInfo.append("</th>");
					strResultInfo.append("</c:forEach>");
				}					
			}
			else
			{
				if("0".equals(title.getIsshow())==false && "".equals(title.getSehead())==false){
					strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"' style='text-align:center;' ");
					strResultInfo.append(">");
					strResultInfo.append(col.getName());
					strResultInfo.append("</th>");
				}
				else
				{
					if("0".equals(title.getIsshow())==false)
					{
						strResultInfo.append("<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"' style='text-align:center;' ");
						strResultInfo.append(">");
						strResultInfo.append(col.getName());
						strResultInfo.append("</th>");
					}
					else
					{
						strResultInfo.append("<th data-field='"+col.getKey()+"' data-visible='false' data-width=\""+iWidth+"px\" width='"+iWidth+"px' w='"+iWidth+"' style='text-align:center;display:none;' class='ui-th-column ui-th-ltr' ");
						strResultInfo.append(">");
						strResultInfo.append(col.getName());
						strResultInfo.append("</th>");
					}
				}
			}
		}
		strResultInfo.append("</tr>");
		strResult=strResultInfo.toString();
		return strResult;
	}
	
	private String getSingleTableHeadByQuery(Query query){
		int iWidth;
		int iMultiple;
		String strResult;
		Title title;
		PriKey priKey;
		List<Column> columnList;
		List<ShowStyle> showlist;
		ComparatorQueryTitle comparColumn;
		showlist=null;
		priKey=query.getPrikey();
		if(query.getPagestyle()!=null)
		{
			showlist=query.getPagestyle().getShowlist();
		}
		columnList=query.getColumns();
		comparColumn=new ComparatorQueryTitle();
		strResult = "";
		strResult +="<tr class='ui-jqgrid-labels'>";
		if("".equals(priKey.getKey())==false && "".equals(priKey.getType())==false && "0".equals(priKey.getType())==false){
			strResult +="<th class=\"ui-th-column ui-th-ltr\" data-width=\"60px\" w=\"60\" width=\"60px\">";
			if("2".equals(priKey.getType())==true)
			{
				strResult +="<input type=\"checkbox\" onclick=\"fn_selAll(this);\" />";
			}
			strResult +="选择";
			strResult +="</th>";
		}
		if(showlist!=null)
		{
			for(ShowStyle sstyle:showlist)
			{
				if("6".equals(sstyle.getType()) && "1".equals(sstyle.getIsshow())){
					strResult +="<th class='ui-th-column ui-th-ltr' data-width=\"60px\" w=\"60\" width=\"60px\">序号</th>";
					break;
				}
			}
		}
		Collections.sort(columnList, comparColumn);
		for(Column col:columnList){
			iWidth=1;
			title=col.getTitle();
			try
			{
				iMultiple=Integer.parseInt(title.getWidth());
			}
			catch(Exception e)
			{
				iMultiple=1;
				log.error(e.getMessage(),e);
				log.error("宽度计算错误!");
			}
			iWidth=iWidth*iMultiple;
			if("1".equals(col.getTypeid()))
			{
				strResult += "<c:forEach var='row' items='${requestScope."+col.getKey()+"}'>";
				if("0".equals(title.getIsshow())==false){
					strResult +="<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"_${row.code}' style='text-align:center;'>";
					strResult +="${row.value}";
					strResult +="</th>";
				}
				else{
					strResult +="<th data-field='"+col.getKey()+"_${row.code}' data-visible='false' data-width=\""+iWidth+"px\" width='"+iWidth+"px' w='"+iWidth+"' style='text-align:center;display:none;' class='ui-th-column ui-th-ltr'>";
					strResult +="${row.value}";
					strResult +="</th>";
				}
				strResult += "</c:forEach>";
			}
			else
			{
				if("0".equals(title.getIsshow())==false){
					strResult +="<th class='ui-th-column ui-th-ltr"+("1".equals(title.getIsedit())==true ? " queryheadedit" : "")+"' data-visible='true' data-width=\""+iWidth+"px\" w='"+iWidth+"' width='"+iWidth+"px' data-field='"+col.getKey()+"' style='text-align:center;'>";
					strResult +=col.getName();
					strResult +="</th>";
				}
				else
				{
					strResult +="<th data-field='"+col.getKey()+"' data-visible='false' data-width=\""+iWidth+"px\" width='"+iWidth+"px' w='"+iWidth+"' style='text-align:center;display:none;' class='ui-th-column ui-th-ltr'>";
					strResult +=col.getName();
					strResult +="</th>";
				}
			}
		}
		strResult +="</tr>";
		return strResult;
	}
	
	@SuppressWarnings("deprecation")
	//执行Sql
	private String getQuickqueryByQuery(Query query, Map<String, Object> retMap, HttpServletRequest request) {
		String strTable;
		String strSelect;
		String strInput;
		String strCode;
		String strValue;
		ChoiceTime choiceTime;
		List<Column> listcolumn;
		List<Map> resultMap;
		Quick quick;
		Search search;
		resultMap=null;
		listcolumn=query.getColumns();
		strTable="";
		choiceTime=query.getChoicetime();
		if("1".equals(choiceTime.getType())==true){
			strTable +="<input type=\"input\" class='form-control' id=\"begin_datetime\" name=\"begin_datetime\" value=\"${requestScope.begin_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
			strTable +="<input type=\"input\" class='form-control' id=\"end_datetime\" name=\"end_datetime\" value=\"${requestScope.end_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
		}
		if("2".equals(choiceTime.getType())==true){
			strTable +="<input type=\"input\" class='form-control' id=\"begin_datetime\" name=\"begin_datetime\" value=\"${requestScope.begin_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
			strTable +="<input type=\"input\" class='form-control' id=\"end_datetime\" name=\"end_datetime\" value=\"${requestScope.end_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
		}
		if("3".equals(choiceTime.getType())==true){
			strTable +="<input type=\"input\" class='form-control' id=\"begin_datetime\" name=\"begin_datetime\" value=\"${requestScope.begin_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
			strTable +="<input type=\"input\" class='form-control' id=\"end_datetime\" name=\"end_datetime\" value=\"${requestScope.end_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
		}
		if("4".equals(choiceTime.getType())==true){
			strTable +="<input type=\"input\" class='form-control' id=\"begin_datetime\" name=\"begin_datetime\" value=\"${requestScope.begin_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
			strTable +="<input type=\"input\" class='form-control' id=\"end_datetime\" name=\"end_datetime\" value=\"${requestScope.end_datetime}\" onclick=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true});\" style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
		}
		for(Column colObj : listcolumn){
			quick=colObj.getQuick();
			search=colObj.getSearch();
			if("1".equals(quick.getIsshow())==true)
			{
				strTable +="<div class='form-group'>";
				if("1".equals(quick.getShowname())==true)
				{
					strTable +="<span class='navbar-brand searchTitle'>"+colObj.getName()+":</span>";
				}
				if("1".equals(quick.getShowoperation())==true)
				{
					request.setAttribute("_"+colObj.getKey()+"expree", request.getParameter(colObj.getKey()+"expree")==null?"":request.getParameter(colObj.getKey()+"expree"));;
					strSelect="<select class='form-control' name='"+colObj.getKey()+"expree'>";
					if("string".equals(colObj.getType())==true)
					{
						strSelect +="<option value='=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='='}\">selected='selected'</c:if>>等于</option>";
						strSelect +="<option value='like' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='like'}\">selected='selected'</c:if>>like</option>";
					}
					if("number".equals(colObj.getType())==true)
					{
						strSelect +="<option value='='  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='='}\">selected='selected'</c:if>>等于</option>";
						strSelect +="<option value='>'  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='>'}\">selected='selected'</c:if>>大于</option>";
						strSelect +="<option value='<'  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='<'}\">selected='selected'</c:if>>小于</option>";
						strSelect +="<option value='>=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='>='}\">selected='selected'</c:if>>大等于</option>";
						strSelect +="<option value='<=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='<='}\">selected='selected'</c:if>>小等于</option>";
						strSelect +="<option value='!=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='!='}\">selected='selected'</c:if>>不等于</option>";
					}
					if("datetime".equals(colObj.getType())==true)
					{
						strSelect +="<option value='='  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='='}\">selected='selected'</c:if>>等于</option>";
						strSelect +="<option value='>'  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='>'}\">selected='selected'</c:if>>大于</option>";
						strSelect +="<option value='<'  <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='<'}\">selected='selected'</c:if>>小于</option>";
						strSelect +="<option value='>=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='>='}\">selected='selected'</c:if>>大等于</option>";
						strSelect +="<option value='<=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='<='}\">selected='selected'</c:if>>小等于</option>";
						strSelect +="<option value='!=' <c:if test=\"${requestScope._"+colObj.getKey()+"expree=='!='}\">selected='selected'</c:if>>不等于</option>";
					}
					strSelect+="</select>";
					strTable+=strSelect;
				}
				request.setAttribute("_" + colObj.getKey() + "value", request.getParameter(colObj.getKey()+"value")==null?"":request.getParameter(colObj.getKey()+"value"));
				if("datetime".equals(search.getType())==true){
					strInput="<input id='"+colObj.getKey()+"value' class='form-control' name='"+colObj.getKey()+"value' type='input' value='${requestScope._"+colObj.getKey()+"value}' onClick='WdatePicker()' style='background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;' />";
					strTable+=strInput;
				}
				if("input".equals(search.getType())==true){
					strInput="<input id='"+colObj.getKey()+"value' class='form-control' name='"+colObj.getKey()+"value' type='input' value='${requestScope._"+colObj.getKey()+"value}' />";
					strTable+=strInput;
				}
				if("select".equals(search.getType())==true && "sql".equals(search.getDatatype())){
					strSelect = "<select class='form-control' name='"+colObj.getKey()+"value'>";
					strSelect +="<option value='' <c:if test=\"${requestScope._"+colObj.getKey()+"value==''}\">selected='selected'</c:if>>请选择</option>";
					try
					{
						resultMap = (List<Map>)retMap.get(colObj.getKey()+"_select");
					}
					catch(Exception e)
					{
						log.error(e.getMessage(),e);
					}
					if(null!=resultMap){
						for(Map<String, Object> hMap:resultMap)
						{
							strCode="";
							strValue="";
							for (Map.Entry<String, Object> entry: hMap.entrySet()) {
								if ("code".equals(entry.getKey())) {
									strCode=entry.getValue().toString();
									continue;
								}
								if ("value".equals(entry.getKey())) {
									strValue=entry.getValue().toString();
									continue;
								}
							}
							if("".equals(strCode)==false && "".equals(strValue)==false){
								strSelect +="<option value='"+strValue+"' <c:if test=\"${requestScope._"+colObj.getKey()+"value=='"+strValue+"'}\">selected='selected'</c:if>>"+strCode+"</option>";
							}
						}
					}
					strSelect +="</select>";
					strTable+=strSelect;
				}
				strTable+="</div>";
			}
		}
		return strTable;
	}
	
	private String getLoopSubMenu(Menu menu, SubMenu rootMenu,boolean isRoot, String strSubmenuResult) {
		StringBuilder strMenu;
		strMenu=new StringBuilder();
		strMenu.append(getSubMenu(menu,rootMenu,0,isRoot,strSubmenuResult));
		return strMenu.toString();
	}

	private String getShortcutMenu(Menu menu,String strEncode, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		List<ShortCut> list;
		String strUrl;
		String strResult;
		ComparatorMenuShortCut comparMenuShortCut;
		strResult="";
		list=menu.getListShortCuts();
		comparMenuShortCut=new ComparatorMenuShortCut();
		Collections.sort(list, comparMenuShortCut);
		if(list.size()>0)
		{
			for(ShortCut shortcut:list){
				strUrl=shortcut.getUrl();
				strUrl=HtmlUnit.getValueByRequest(strUrl, request, response);
				strResult+="<shiro:hasPermission name=\"" + shortcut.getCode() + "\">\r\n";
				strResult+="<li>";
				strResult+="<a href=\"javascript:ShortCutClick('${pageContext.request.contextPath}"+XmlUnit.getEnXmlString(strUrl) +"');\">";
				strResult+="<div>";
				strResult+="<i class=\""+(shortcut.getPic().equals("")?"fa fa-glass bg-success white":shortcut.getPic())+"\"></i>";
				strResult+=shortcut.getName()+"</div>";
				strResult+="</a>";
				strResult+="</li>";
				strResult+="<li class=\"divider\"></li>";
				strResult+="</shiro:hasPermission>\r\n";
			}
		}
		return strResult;
	}
	
	private String getSubMenu(Menu menu,SubMenu subRoot, int type,boolean isRoot, String strResult) {
		int i;
		boolean blIsRoot;
		String strIcon;
		String strClass;
		List<SubMenu> list;
		List<SubMenu> listsubmenu;
		ComparatorMenuSubMenu comparMenuSubMenu;
		comparMenuSubMenu=new ComparatorMenuSubMenu();
		listsubmenu=menu.getListSubMenus();
		list=new ArrayList<SubMenu>();
		i=0;
		for(SubMenu submenu:listsubmenu){
			if(subRoot.getCode().equals(submenu.getParentcode())==true && "1".equals(submenu.getIsshow())==true){
				list.add(submenu);
				continue;
			}
		}
		Collections.sort(list, comparMenuSubMenu);
		if(type==0)
		{
			strIcon="fa-tasks";
			strClass="nav-second-level";
		}
		else
		{
			strIcon=(type==1?"fa-edit":"fa-asterisk");
			strClass="nav-third-level";
		}
		strResult+="<shiro:hasPermission name=\"" + subRoot.getCode() + "\">\r\n";
		if(true==isRoot && 0==type)
		{
			strResult+="<li class=\"active\">";
		}
		else
		{
			if(true==isRoot)
			{
				strResult+="<li class=\"active menuactive\">";
			}
			else
			{
				strResult+="<li>";
			}
		}
		if(subRoot.getUrl().equals("")||subRoot.getUrl().equals("#")){
			strResult+="<a href=\"#\">";
			
		}else{
			strResult+="<a class=\"J_menuItem\" href=\""+(subRoot.getUrl().equals("")?"#":"${pageContext.request.contextPath}"+subRoot.getUrl())+"\">";
			
		}
		if(1 == type)
		{
			strResult+="<i class=\"icon-double-angle-right\"></i>";
		}
		strResult+="<i class=\""+(subRoot.getPic().equals("")?"fa "+strIcon:subRoot.getPic())+"\"></i>";
		strResult+="<span class=\"nav-label menu-text\"> ";
		strResult+=subRoot.getName();
		strResult+="</span>";
		if(list.size()>0)
		{
			strResult+="<b class=\"arrow icon-angle-down\"></b>";   //存在子节点则生成向下箭头			
		}
		strResult+="</a>";
		if(list.size()>0)
		{
			strResult+="<ul class=\"submenu nav "+strClass+" collapse "+(true==isRoot?"in":"")+"\" parentkey=\""+subRoot.getParentcode()+"\" key=\""+subRoot.getCode()+"\">";
		}
		type++;
		if(list.size()>0)
		{
			for(SubMenu submenu:list){
				blIsRoot=(true==isRoot && 0==i);
				strResult=getSubMenu(menu,submenu,type,blIsRoot,strResult);
				i++;
			}
		}
		if(type>0)
		{
			if(list.size()>0)
			{
				strResult+="</ul>\r\n";
			}
			strResult+="</li>\r\n";
		}
		strResult+="</shiro:hasPermission>\r\n";
		return strResult;
	}
	
	@Override
	public int getShowType(HttpServletRequest request,HttpServletResponse response, Map<String, List<Map<String, String>>> mapAuthority, String strId) {
		int ishowType;
		int ieleShowType;
		String strauthinfo;
		String strTemp;
		JSONObject jsonObj;
		List<Map<String, String>> listShow;
		jsonObj=null;
		listShow=mapAuthority.get(strId);
		ishowType = 0;
		if(listShow!=null)
		{
			for (Map<String,String> show : listShow) {
				ieleShowType = Integer.parseInt(show.get("showtype"));
				strauthinfo=show.get("authinfo").trim();
				if("".equals(strauthinfo)==false)
				{
					strauthinfo=XmlUnit.getDeXmlString(show.get("authinfo").trim());
				}
				if(ishowType < ieleShowType){
					if("".equals(strauthinfo)==false)
					{
						try
						{
							jsonObj=JSONObject.fromObject(strauthinfo);
						}catch(Exception e){
							log.error(e.getMessage(), e);
							continue;
						}
						if(null!=jsonObj && 0==jsonObj.getInt("type") && "".equals(jsonObj.getString("expree"))==false){
							strTemp=HtmlUnit.getValueByRequest(jsonObj.getString("expree"), request, response);
							if("true".equals(strTemp.toLowerCase()))
							{
								ishowType = ieleShowType;
							}
						}
						if(null!=jsonObj && 1==jsonObj.getInt("type") && "".equals(jsonObj.getString("expree"))==false){
							strTemp=HtmlUnit.getValueByRequest(jsonObj.getString("expree"), request, response);
							try {
								if("true".equals(coreService.getShowTypeBySql(jsonObj.getString("sysid"), strTemp,null).toLowerCase())==true)
								{
									ishowType = ieleShowType;
								}
							} catch (Exception e) {
								log.error(e.getMessage(),e);
							}
						}
					}
					else
					{
						ishowType = ieleShowType;
					}
				}
			}
		}
		return ishowType;
	}

	@Override
	public void saveTable(String name, HttpServletRequest request) {
		Boolean blIsUpdate;
		String strKey;
		String strPreKey;
		String strValue;
		String jsonData;
		String strSqlInfo;
		List<Map> listMap;
		List<Map> listSysMap;
		Form form;
		Object obj;
		StringBuilder strSql;
		strSql=new StringBuilder();
		blIsUpdate=null;
		listSysMap=null;
		listMap=null;
		form=null;
		jsonData="";
		obj=request.getAttribute("_blIsUpdate");
		if(obj!=null){
			blIsUpdate=(Boolean)obj;
		}
		obj=request.getAttribute("_form");
		if(obj!=null){
			form=(Form)obj;
		}
		if(blIsUpdate != null && form != null)
		{
			strSqlInfo="SELECT dbinfo FROM sc_database WHERE dbname='"+name+"'";
			try{
				listSysMap = commonService.queryListSql("com",strSqlInfo,null);
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
			if(listSysMap!=null && listSysMap.size()!=0)
			{
				strSql.append("SELECT COLUMN_NAME,COLUMN_KEY ");
				strSql.append("FROM information_schema.COLUMNS ");
				strSql.append("WHERE (1=1) AND TABLE_SCHEMA='"+listSysMap.get(0).get("dbinfo").toString()+"' AND Table_Name='"+form.getDataname()+"';");
				try{
					listMap = commonService.queryListSql(name,strSql.toString(),null);
					jsonData=saveTableInfo(name,form,blIsUpdate,listMap,request);
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
				request.setAttribute("_saveObj", JSONObject.fromObject(jsonData));
			}
		}
	}
	
	@Override
	public void saveMSSqlTable(String name, HttpServletRequest request) {
		String jsonData;
		List<Map> listMap;
		String strSqlInfo;
		Boolean blIsUpdate;
		List<Map> listSysMap;
		Form form;
		Object obj;
		StringBuilder strSql;
		strSql=new StringBuilder();
		blIsUpdate=null;
		listSysMap=null;
		listMap=null;
		form=null;
		jsonData="";
		obj=request.getAttribute("_blIsUpdate");
		if(obj!=null){
			blIsUpdate=(Boolean)obj;
		}
		obj=request.getAttribute("_form");
		if(obj!=null){
			form=(Form)obj;
		}
		if(blIsUpdate != null && form != null)
		{
			strSqlInfo="SELECT dbinfo FROM sc_database WHERE dbname='"+name+"'";
			try{
				listSysMap = commonService.queryListSql("com",strSqlInfo,null);
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
			if(listSysMap!=null && listSysMap.size()!=0)
			{
				strSql.append("select cols.COLUMN_NAME,isnull(priinfo.colkey,'') as COLUMN_KEY ");
				strSql.append("from information_schema.COLUMNS as cols ");
				strSql.append("left join ( ");
				strSql.append("	  SELECT SYSCOLUMNS.name as colname,'PRI' as colkey ");
				strSql.append("	  FROM SYSCOLUMNS ");
				strSql.append("	  join SYSOBJECTS ");
				strSql.append("	  on SYSOBJECTS.parent_obj = SYSCOLUMNS.id "); 
				strSql.append("	  join SYSINDEXES ");
				strSql.append("	  on SYSINDEXES.id = SYSCOLUMNS.id AND SYSOBJECTS.name = SYSINDEXES.name "); 
				strSql.append("	  join SYSINDEXKEYS  ");
				strSql.append("	  on SYSINDEXKEYS.indid = SYSINDEXES.indid AND SYSINDEXKEYS.id = SYSCOLUMNS.id AND SYSCOLUMNS.colid = SYSINDEXKEYS.colid ");
				strSql.append("	  WHERE SYSCOLUMNS.id = object_id('"+form.getDataname()+"') AND SYSOBJECTS.xtype = 'PK' ");
				strSql.append(") as priinfo ");
				strSql.append("on cols.COLUMN_NAME=priinfo.colname ");
				strSql.append("WHERE (1=1) AND cols.TABLE_CataLog='"+listSysMap.get(0).get("dbinfo").toString()+"' AND cols.Table_Name='"+form.getDataname()+"' ");
				try{
					listMap = commonService.queryListSql(name,strSql.toString(),null);
					jsonData=saveTableInfo(name,form,blIsUpdate,listMap,request);
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
				request.setAttribute("_saveObj", JSONObject.fromObject(jsonData));
			}
		}
	}
	
	private String saveTableInfo(String name,Form form,Boolean blIsUpdate,List<Map> listMap,HttpServletRequest request)
	{
		String strKey;
		String strValue;
		Boolean blPrKey;
		String jsonData;
		String strPreKey;
		String strsetInfo;
		String strcollInfo;
		Boolean blWhereKey;
		String strwhereInfo;
		StringBuilder strset;
		StringBuilder strcoll;
		StringBuilder strwhere;
		List<Object> insertList;
		List<Object> updateList;
		List<DynamicParm> listDparm;
		strcoll=new StringBuilder();
		strset=new StringBuilder();
		strwhere=new StringBuilder();
		insertList=new ArrayList<Object>();
		updateList=new ArrayList<Object>();
		jsonData="{";
		if(blIsUpdate==false && null !=listMap)
		{
			for(Map<String, Object> hMap:listMap)
			{
				strKey="";
				strPreKey="";
				blPrKey=false;
				for (Map.Entry<String, Object> entry: hMap.entrySet()) {
					if ("COLUMN_NAME".equals(entry.getKey())) {
						strKey=entry.getValue().toString();
						continue;
					}
					if ("COLUMN_KEY".equals(entry.getKey())) {
						strPreKey=entry.getValue().toString();
						continue;
					}
				}
				strValue=request.getParameter(strKey);
				if("PRI".equals(strPreKey)==true)
				{
					blPrKey=true;
					try {
						strValue=commonService.getKeybyTabCol(name,form.getDataname(),strKey);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
				if(strValue!=null)
				{
					if(blPrKey==false)
					{
						jsonData+="\""+strKey+"\":\""+XmlUnit.getEnXmlString(strValue)+"\",";
					}
					else
					{
						jsonData+="\""+strKey+"\":[{\"value\":\""+strValue+"\",\"prkey\":true}],";
					}
					strcoll.append(strKey+",");
					insertList.add(strValue);
				}
			}
			jsonData=(jsonData.length()==1?jsonData:jsonData.substring(0, jsonData.length()-1))+"}";
			strcollInfo=StringUtils.isEmpty(strcoll.toString())==true?"":strcoll.toString().substring(0, strcoll.toString().length()-1);
			try
			{
				commonService.insertObj(name,form.getDataname(),strcollInfo,insertList.toArray());
			}catch(Exception e)
			{
				log.error("inert "+e);
				log.error(e.getMessage(),e);
			}
		}
		else
		{
			listDparm=form.getDyparm();
			if(null!= listMap)
			{
				for(Map<String, Object> hMap:listMap)
				{
					for (Map.Entry<String, Object> entry: hMap.entrySet()) {
						if ("COLUMN_NAME".equals(entry.getKey())) {
							strKey=entry.getValue().toString();
							blWhereKey=false;
							for(DynamicParm dparm:listDparm)
							{
								if(dparm.getValue().equals(strKey)==true){
									blWhereKey=true;
									break;
								}
							}
							if(blWhereKey==true)
							{
								jsonData+="\""+strKey+"\":[{\"value\":\""+request.getParameter(strKey)+"\",\"prkey\":true}],";
								strwhere.append("and ("+strKey+"='"+request.getParameter(strKey)+"')");
							}
							else
							{
								if(request.getParameter(strKey)!=null)
								{
									jsonData+="\""+strKey+"\":\""+XmlUnit.getEnXmlString(request.getParameter(strKey))+"\",";
									strset.append(" "+strKey+",");
									updateList.add(request.getParameter(strKey));
								}
								else
								{
									jsonData+="\""+strKey+"\":\"null\",";
									strset.append(" "+strKey+",");
								}
							}
						}
					}
				}
			}
			jsonData=(jsonData.length()==1?jsonData:jsonData.substring(0, jsonData.length()-1))+"}";
			strsetInfo="".equals(strset.toString())==true?"":strset.toString().substring(0, strset.toString().length()-1);
			strwhereInfo="".equals(strwhere.toString())==true?"":strwhere.toString();
			try
			{
				commonService.updateObj(name,form.getDataname(),strsetInfo,updateList.toArray(),strwhereInfo);
			}
			catch(Exception e)
			{
				log.error("update "+e);
				log.error(e.getMessage(),e);
			}
		}
		return jsonData;
	}

	@Override
	public String getUrlInfo(String urlinfo, HttpServletRequest request,HttpServletResponse response) {
		String strReturn;
		urlinfo=XmlUnit.getDeXmlString(urlinfo);
		strReturn=HtmlUnit.getValueByRequest(urlinfo, request ,response);
		return strReturn;
	}

	@Override
	public void redirecturl(String strurl, HttpServletRequest request,HttpServletResponse response) {
		try {
			strurl=XmlUnit.getDeXmlString(strurl);
			strurl=HtmlUnit.getValueByRequest(strurl, request, response);
			response.sendRedirect(strurl);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void removeCache(String cacheKey, HttpServletRequest request) {
		Ehcache cache;
		CacheManager manager;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		if(null!=cache)
		{
			cache.remove(cacheKey);
		}
	}
	
	@Override
	public Form formatXmltoForm(Document loadInit) {
		return XmlUnit.formatXmltoForm(loadInit);
	}

	@Override
	public Frame formatXmltoFrame(Document loadInit) {
		return XmlUnit.formatXmltoFrame(loadInit);
	}

	@Override
	public Query formatXmltoQuery(Document loadInit) {
		return XmlUnit.formatXmltoQuery(loadInit);
	}

	@Override
	public Tool formatXmltoTool(Document loadInit) {
		return XmlUnit.formatXmltoTool(loadInit);
	}

	@Override
	public Tree formatXmltoTree(Document loadInit) {
		return XmlUnit.formatXmltoTree(loadInit);
	}

	@Override
	public Document loadInit(String strPath) throws Exception {
		return XmlUnit.loadInit(strPath);
	}
	
	@Override
	public void writeEncryptFile(String sb, String strPath,String defaultEncoding)
	{
		File file;
		File fileDir;
		String strDir;
		String strEncodeInfo;
		strDir = strPath.substring(0, strPath.lastIndexOf("\\"));
		file = new File(strPath);
		fileDir = new File(strDir);
		if (!fileDir.isDirectory()) {
			fileDir.mkdirs();
		}
		try
		{
			strEncodeInfo=EncodeUtil.Encrypt(sb);
			FileUtil.writeStringToFile(file,strEncodeInfo,Charset.forName(defaultEncoding),false);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public long StringFileBuffer(String sb, String strPath,
			ILastmodifiedTime lastModifyTime, String defaultEncoding)
			throws IOException {
		File file;
		File fileDir;
		String strDir;
		long modifiedTime;
		FileOutputStream out;
		strDir = strPath.substring(0, strPath.lastIndexOf("\\"));
		file = new File(strPath);
		fileDir = new File(strDir);
		modifiedTime = file.lastModified();
		if (!fileDir.isDirectory()) {
			fileDir.mkdirs();
		}
		if (String.valueOf(modifiedTime).equals(
				lastModifyTime.getLastmodifiedtime()) == false) {
			if (file.exists() == false) {
				file.createNewFile();
			} else {
				file.delete();
			}
			try
			{
				FileUtil.writeStringToFile(file,sb,Charset.forName(defaultEncoding),false);
			}
			catch(IOException e)
			{
				log.error(e.getMessage(),e);
			}
			modifiedTime = file.lastModified();
		}
		return modifiedTime;
	}

	@Override
	//接收Map
	public Map<String, Object[]> getMapByRequest(HttpServletRequest request) {
		Map properties;
		Iterator entries;
		Map<String,Object[]> returnMap;
		String name;
		Object[] value;
		Enumeration headerNames;
		Enumeration attrNames;
		name="";
		value=null;
		properties =request.getParameterMap();  
		returnMap = new HashMap<String,Object[]>();  
	    entries = properties.entrySet().iterator();  
	    Entry<String,String[]> entry;  
	    while (entries.hasNext()) {  
	        entry = (Map.Entry) entries.next();  
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = null;  
	        }else if(valueObj instanceof String[]){
	        	value=(String[])valueObj;
	        }else{
	        	value=new Object[1];
	        	value[0]=valueObj;
	        }
	        if(value instanceof java.io.Serializable)
	        {
	        	returnMap.put("map."+name, value);
	        }
	    }
	    headerNames=request.getHeaderNames();
	    while(headerNames.hasMoreElements())
	    {
	    	value=new Object[1];
	    	name=(String)headerNames.nextElement();
	    	value[0]=request.getHeader(name);
	    	if(value[0] instanceof java.io.Serializable)
	    	{
	    		returnMap.put("head."+name, value);
	    	}
	    }
	    attrNames=request.getAttributeNames();
	    while(attrNames.hasMoreElements())
	    {
	    	value=new Object[1];
	    	name=(String)attrNames.nextElement();
	    	value[0]=request.getAttribute(name);
	    	if(value[0] instanceof java.io.Serializable && "_form".equals(name)==false)
	    	{
	    		returnMap.put("attr."+name, value);
	    	}
	    }
	    return returnMap;
	}

	@Override
	public List<Map> getFileList(String strPath, String strExec) {
		File rootDir;
		List<Map> list;
		String[] strTemp;
		Map<String,String> mapInfo;
		list=new ArrayList<Map>();
		try
		{
			rootDir=new File(strPath);
			if(rootDir.exists()==true && rootDir.isDirectory()==true){
				mapInfo=new HashMap<String, String>();
				mapInfo.put("code", "-1");
				mapInfo.put("value", "--请选择--");
				list.add(mapInfo);
				strTemp=rootDir.list();
				for(int i=0;i<strTemp.length;i++)
				{
					if(strTemp[i].substring(strTemp[i].length()-("."+strExec).length(),strTemp[i].length()).equals("."+strExec)==true)
					{
						mapInfo=new HashMap<String, String>();
						mapInfo.put("code", strTemp[i]);
						mapInfo.put("value", strTemp[i]);
						list.add(mapInfo);
					}
				}
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		return list;
	}

	public String changeHtmlInfo(String strDynamicform,List<Map<String, String>> controls) {
		org.w3c.dom.Document document;
		org.w3c.dom.Element element;
		DOMParser parser;
		String strId;
		String strImage;
		String strTag;
		String strTitle;
		StringReader read;
		InputSource source;
		StringBuilder strReturn;
		parser = new DOMParser();
		strReturn=new StringBuilder();
		read=null;
		strTag="";
		strImage="";
		try
		{
			read=new StringReader(strDynamicform);
			source=new InputSource(read);
			parser.parse(source);
			document=parser.getDocument();
			for(int i=0;i<controls.size();i++)
			{
				strId=controls.get(i).get("id");
				element=document.getElementById(strId);
				if(null != element)
				{
					strTitle=element.getNodeName().toLowerCase();
					strTag="<"+strTitle+" id=\""+strId+"\" />";
					strImage="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strId+"','title':'"+strTitle+"'}\" />";
					strDynamicform = strDynamicform.replace(strTag,strImage);
					strTag="<"+strTitle+" id=\""+strId+"\"/>";
					strDynamicform = strDynamicform.replace(strTag,strImage);
					strTag="<"+strTitle+" id=\""+strId+"\" ></"+strTitle+">";
					strDynamicform = strDynamicform.replace(strTag,strImage);
					strTag="<"+strTitle+" id=\""+strId+"\"></"+strTitle+">";
					strDynamicform = strDynamicform.replace(strTag,strImage);
				}
			}
			strReturn.append(strDynamicform);
		}catch(Exception e)
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
		return strReturn.toString();
	}

	@Override
	public List<Map> loadMapByReadisKeyNoDel(String strKey) {
		List<Map> list;
		list=(List<Map>)redisDao.load(strKey);
		return list;
	}

	@Override
	public void saveMapByReadisKeyNoDel(String strKey, List<Map> list) {
		redisDao.addOrUpdate(strKey, list);
	}
	
	@Override
	public void saveBtnStatus(String key,String btnId,String status){
		Map mapInfo;
		List<Map> listMapInfo;
		listMapInfo=new ArrayList<Map>();
		mapInfo=new HashMap<String,String>();
		mapInfo.put("btnId", btnId);
		mapInfo.put("status", status);
		listMapInfo.add(mapInfo);
		this.saveMapByReadisKeyNoDel(key, listMapInfo);
	}
}
