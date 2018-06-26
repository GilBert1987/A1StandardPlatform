package com.platform.data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import com.common.entity.login.User;
import com.common.entity.query.ChoiceTime;
import com.common.entity.query.Column;
import com.common.entity.query.InitSql;
import com.common.entity.query.PageStyle;
import com.common.entity.query.PriKey;
import com.common.entity.query.Query;
import com.common.entity.query.QueryButton;
import com.common.entity.query.Quick;
import com.common.entity.query.Search;
import com.common.entity.query.ShowStyle;
import com.common.entity.query.Title;
import com.common.entity.query.Unit;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class QueryDataLoad implements IFormDataLoad  {

	private static Logger log = Logger.getLogger(QueryDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Query query;
		byte[] bytes;
		String strId;
		Integer type;
		String strSqlInfo;
		String strEncodeMap;
		String strDataSqlInfo;
		Search search;
		List<Column> collist;
		List<QueryButton> btnlist;
		JSONObject jsonObj;
		List<Map> listSqlMap;
		Map mapList;
		Object[] params;
		CoreService coreService;
		CommonService commonService;
		LocalCommonService localcommService;
		query = null;
		commonService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		if(request.getParameter("id")!=null){
			listSqlMap=null;
			strId=request.getParameter("id").toString();
			params=new Object[]{
				strId
			};
			strSqlInfo="select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
			try {
				listSqlMap=commonService.queryListSql("com", strSqlInfo,params);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			if(0!=listSqlMap.size())
			{
				mapList=listSqlMap.get(0);
				bytes=(byte[])mapList.get("bytesinfo");
				type=(Integer)mapList.get("type");
				strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
				if(null!=type && 0!=type)
				{
					strEncodeMap=coreService.readEncryptSql(strEncodeMap);
				}
				query = localcommService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
			}
			else
			{
				if (!XmlUnit.existsFile(request.getSession().getServletContext().getRealPath("/WEB-INF/query/"+strId+".query")).booleanValue())
			    {
					log.error(strId+" query file is null");
			    }
				else
				{
					try {
						query = XmlUnit.formatXmltoQuery(XmlUnit.loadInit(request.getSession().getServletContext().getRealPath("/WEB-INF/query/"+strId+".query")));
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Xml init Error by id"+strId);
					}
				}
			}
			if (query != null) {
				collist = query.getColumns();
				for (Column col : collist) {
					search = col.getSearch();
					if (null != search && null != search.getSearchinfo()) {
						search.setSearchinfo(XmlUnit.getEnXmlString(XmlUnit
								.getDeXmlString(search.getSearchinfo())).replace("%2520", "%20"));
					}
					if(null!=col.getDataSqlInfo())
					{
						strDataSqlInfo = col.getDataSqlInfo();
						col.setDataSqlInfo(XmlUnit.getEnXmlString(XmlUnit.getDeXmlString(strDataSqlInfo)).replace("%2520", "%20"));
					}
					if(null!=col.getTitleSqlInfo())
					{
						col.setTitleSqlInfo(XmlUnit.getEnXmlString(XmlUnit.getDeXmlString(col.getTitleSqlInfo())).replace("%2520", "%20"));
					}
				}
				btnlist = query.getButtons();
				for (QueryButton btnInfo : btnlist) {
					if (null != btnInfo.getJsclick()) {
						btnInfo.setJsclick(XmlUnit.getEnXmlString(XmlUnit
								.getDeXmlString(btnInfo.getJsclick())).replace("%2520", "%20"));
					}
				}
				jsonObj = JSONObject.fromObject(query);
				request.setAttribute("_querydata", jsonObj);
			}
		}
	}
		
	public void saveQuery(String code,HttpServletRequest request,HttpServletResponse response) {
		int icount;
		Query query;
		PageStyle pstyle;
		Column colInfo;
		Quick quick;
		Search search;
		Title title;
		Unit unit;
		InitSql initSql;
		QueryButton button;
		List<Column> collist;
		List<Column> colFinlist;
		List<QueryButton> btnlist;
		List<ShowStyle> showlist;
		ShowStyle showInfo;
		ChoiceTime chTime;
		PriKey prikey;
		JSONObject jsObj;
		JSONObject jsColInfo;
		JSONObject jsShowObj;
		JSONObject jsQuick;
		JSONObject jsSearch;
		JSONObject jsTitle;
		JSONObject jsUnit;
		JSONObject jsButton;
		JSONArray jsshowlist;
		JSONArray jscollist;
		JSONArray jsbtnlist;
		CacheManager manager;
		Ehcache cache;
		Select select;
		String colKeyInfo;
		String colNameInfo;
		Expression expression;
		PlainSelect body;
		Boolean blInfo;
		Column colFinInfo;
		SelectExpressionItem expressItem;
		List<SelectItem> listSelItem;
		List<Entry<String, Integer>> listEntry;
		HashMap<String,Integer> colMap;
		Iterator<Entry<String, Integer>> itNextInfo;
		Entry<String, Integer> entryInfo;
		Subject subject;
		User user;
		CoreService coreService;
		Entry<String, Integer> entryCol;
		LocalCommonService localCommonService;
		net.sf.jsqlparser.schema.Column column;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		query=new Query();
		pstyle=new PageStyle();
		collist=new ArrayList<Column>();
		colFinlist=new ArrayList<Column>(); 
		btnlist=new ArrayList<QueryButton>();
		query.setColumns(colFinlist);
		query.setButtons(btnlist);
		query.setSysid(request.getParameter("sysid"));
		query.setCode(code);
		query.setDatatype(request.getParameter("datatype"));
		query.setName(request.getParameter("name"));
		query.setHead(request.getParameter("head"));
		query.setRemark(request.getParameter("remark"));
		query.setTitle(request.getParameter("title"));
		query.setFiledir(request.getParameter("filedir"));
		query.setOrderkey(request.getParameter("orderkey"));
		query.setDefaultparm(request.getParameter("defaultparm"));
		query.setSqlinfo(request.getParameter("sqlinfo"));
		query.setJsfunction(request.getParameter("jsfunction"));
		query.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		colMap=null;
		if(null!=request.getParameter("pagestyle")){
			jsObj=JSONObject.fromObject(request.getParameter("pagestyle"));
			showlist=new ArrayList<ShowStyle>();
			jsshowlist=(JSONArray)jsObj.get("showlist");
			for(int i=0;i<jsshowlist.size();i++){
				showInfo=new ShowStyle();
				jsShowObj=(JSONObject)jsshowlist.get(i);
				showInfo.setIsshow(jsShowObj.getString("isshow"));
				showInfo.setType(jsShowObj.getString("type"));
				showlist.add(showInfo);
			}
			pstyle.setType(jsObj.getString("type"));
			pstyle.setShowlist(showlist);
			query.setPagestyle(pstyle);
		}
		if(null!=request.getParameter("initsql")){
			initSql=new InitSql();
			jsObj=JSONObject.fromObject(request.getParameter("initsql"));
			initSql.setStrsql(jsObj.getString("strsql"));
			initSql.setType(jsObj.getString("type"));
			query.setInitsql(initSql);
		}
		if(null!=request.getParameter("prikey"))
		{
			prikey=new PriKey();
			jsObj=JSONObject.fromObject(request.getParameter("prikey"));
			prikey.setKey(jsObj.getString("key"));
			prikey.setType(jsObj.getString("type"));
			query.setPrikey(prikey);
		}
		if(null!=request.getParameter("choicetime"))
		{
			chTime=new ChoiceTime();
			jsObj=JSONObject.fromObject(request.getParameter("choicetime"));
			chTime.setPagenum(jsObj.getString("pagenum"));
			chTime.setType(jsObj.getString("type"));
			query.setChoicetime(chTime);
		}
		if(null!=request.getParameter("columns"))
		{
			if(query.getSqlinfo().equals("")==false)
			{
				icount=1;
				colMap=new HashMap<String,Integer>();
				try {
					select = (Select)CCJSqlParserUtil.parse(query.getSqlinfo());
					body=(PlainSelect)select.getSelectBody();
					listSelItem=body.getSelectItems();
					for(SelectItem item:listSelItem)
					{
						if(item instanceof SelectExpressionItem)
						{
							expressItem=(SelectExpressionItem)item;
							if(expressItem.getAlias()!=null && expressItem.getAlias().equals("")==false){
								colMap.put(expressItem.getAlias().getName().toString(), icount);
								icount++;
							}
							else
							{
								expression=expressItem.getExpression();
								if(expression instanceof net.sf.jsqlparser.schema.Column){
									column=(net.sf.jsqlparser.schema.Column)expression;
									colMap.put(column.getColumnName().toString(), icount);
									icount++;
								}
							}
						}
					}
				} catch (JSQLParserException e) {
					log.error(e.getMessage(),e);
				}
			}
			jscollist=JSONArray.fromObject(request.getParameter("columns"));
			if(colMap!=null)
			{
				for(int i=0;i<jscollist.size();i++){
					colInfo=new Column();
					quick=new Quick();
					search=new Search();
					title=new Title();
					unit=new Unit();
					blInfo=false;
					jsColInfo=(JSONObject)jscollist.get(i);
					if(colMap.containsKey(jsColInfo.getString("key"))==true){
						blInfo=true;
						if(null!=jsColInfo.getString("typeid"))
						{
							colInfo.setTypeid(jsColInfo.getString("typeid"));
						}
						else
						{
							colInfo.setTypeid("0");
						}
						colInfo.setKey(jsColInfo.getString("key"));
						colInfo.setName(jsColInfo.getString("name"));
						colInfo.setGrouptype(jsColInfo.getString("grouptype"));
						if(null!=jsColInfo.get("title"))
						{
							jsTitle=(JSONObject)jsColInfo.get("title");
							title.setIsedit(jsTitle.getString("isedit"));
							title.setIsshow(jsTitle.getString("isshow"));
							title.setOrder(jsTitle.getString("order"));
							title.setWidth(jsTitle.getString("width"));
							title.setAlign(jsTitle.getString("align"));
							title.setSehead(jsTitle.getString("sehead"));
							if(null!=title.getOrder()){
								colMap.put(colInfo.getKey(), Integer.parseInt(title.getOrder()));
							}
						}
						if("0".equals(colInfo.getTypeid())==true){
							colInfo.setType(jsColInfo.getString("type"));
							if(null!=jsColInfo.get("quick"))
							{
								jsQuick=(JSONObject)jsColInfo.get("quick");
								quick.setIsshow(jsQuick.getString("isshow"));
								quick.setShowname(jsQuick.getString("showname"));
								quick.setShowoperation(jsQuick.getString("showoperation"));
							}
							if(null!=jsColInfo.get("search"))
							{
								jsSearch=(JSONObject)jsColInfo.get("search");
								search.setDatatype(jsSearch.getString("datatype"));
								search.setIsshow(jsSearch.getString("isshow"));
								search.setOrder(jsSearch.getString("order"));
								search.setSearchinfo(XmlUnit.getDeXmlString(jsSearch.getString("searchinfo")));
								search.setType(jsSearch.getString("type"));
							}
							if(null!=jsColInfo.get("unit"))
							{
								jsUnit=(JSONObject)jsColInfo.get("unit");
								unit.setMerge(jsUnit.getString("merge"));
								unit.setMergecolumn(jsUnit.getString("mergecolumn"));
								unit.setMergerefer(jsUnit.getString("mergerefer"));
							}
							colInfo.setQuick(quick);
							colInfo.setSearch(search);
							colInfo.setTitle(title);
							colInfo.setUnit(unit);
						}
					}
					else
					{
						if("1".equals(jsColInfo.getString("typeid"))==true){
							colInfo.setKey(jsColInfo.getString("key"));
							colInfo.setName(jsColInfo.getString("name"));
							colInfo.setTypeid("1");
							colInfo.setGrouptype(jsColInfo.getString("grouptype"));
							colInfo.setRelColId(jsColInfo.getString("relColId"));
							colInfo.setTitColId(jsColInfo.getString("titColId"));
							colInfo.setCurrColId(jsColInfo.getString("currColId"));
							colInfo.setCurrValId(jsColInfo.getString("currValId"));
							colInfo.setDataSysId(jsColInfo.getString("dataSysId"));
							colInfo.setTitleSysId(jsColInfo.getString("titleSysId"));
							colInfo.setDataSqlInfo(XmlUnit.getDeXmlString(jsColInfo.getString("dataSqlInfo")));
							colInfo.setTitleSqlInfo(XmlUnit.getDeXmlString(jsColInfo.getString("titleSqlInfo")));
							if(null!=jsColInfo.get("title"))
							{
								jsTitle=(JSONObject)jsColInfo.get("title");
								title.setIsedit("0");
								title.setIsshow(jsTitle.getString("isshow"));
								title.setOrder(jsTitle.getString("order"));
								title.setWidth(jsTitle.getString("width"));
								title.setAlign(jsTitle.getString("align"));
								if(null!=jsTitle.get("sehead"))
								{
									title.setSehead(jsTitle.getString("sehead"));
								}
								else
								{
									title.setSehead("");
								}
								colInfo.setTitle(title);
								if(null!=title.getOrder()){
									colMap.put(colInfo.getKey(), Integer.parseInt(title.getOrder()));
								}
							}
							blInfo=true;
						}
					}
					if(true==blInfo)
					{
						collist.add(colInfo);
					}
				}
				listEntry=new ArrayList<Entry<String, Integer>>(colMap.entrySet());
				//排序
				Collections.sort(listEntry, new Comparator<Entry<String, Integer>>() {   
				    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				        return o1.getValue().compareTo(o2.getValue());
				    }
				});
				itNextInfo=listEntry.iterator();
				while(itNextInfo.hasNext()==true)
				{
					entryInfo=itNextInfo.next();
					colNameInfo=entryInfo.getKey();
					if(listHasCurrCol(collist,colNameInfo)==false){
						colInfo=new Column();
						quick=new Quick();
						search=new Search();
						title=new Title();
						unit=new Unit();
						colInfo.setKey(colNameInfo);
						colInfo.setGrouptype("");
						colInfo.setName(colNameInfo);
						colInfo.setType("string");
						quick.setIsshow("0");
						quick.setShowname("");
						quick.setShowoperation("");
						search.setIsshow("0");
						search.setDatatype("");
						search.setOrder(entryInfo.getValue().toString());
						search.setSearchinfo("");
						search.setType("input");
						title.setIsedit("0");
						title.setIsshow("1");
						title.setOrder(entryInfo.getValue().toString());
						title.setWidth("100");
						title.setAlign("");
						unit.setMerge("");
						unit.setMergecolumn("");
						unit.setMergerefer("");
						colInfo.setQuick(quick);
						colInfo.setSearch(search);
						colInfo.setTitle(title);
						colInfo.setUnit(unit);
						collist.add(colInfo);
					}
				}
				for(int i=0;i<listEntry.size();i++)
				{
					entryCol=listEntry.get(i);
					colKeyInfo=entryCol.getKey();
					for(int j=0;j<collist.size();j++)
					{
						colFinInfo=collist.get(j);
						if(colKeyInfo.equals(colFinInfo.getKey())==true)
						{
							colFinlist.add(colFinInfo);
							break;
						}
					}
				}
			}
		}
		if(null!=request.getParameter("buttons"))
		{
			jsbtnlist=JSONArray.fromObject(request.getParameter("buttons"));
			for(int i=0;i<jsbtnlist.size();i++){
				button=new QueryButton();
				jsButton=(JSONObject)jsbtnlist.get(i);
				button.setName(jsButton.getString("name"));
				button.setOrder(jsButton.getString("order"));
				button.setJsclick(XmlUnit.getDeXmlString(jsButton.getString("jsclick")));
				button.setIcon(jsButton.getString("icon"));
				btnlist.add(button);
			}
		}
		query.setIsUpdate(true);
		if(query.getCode().trim().equals("")==false && "configform".equals(query.getCode().trim())==false)
		{
			query.setIsUpdate(true);
			coreService.writeEncryptSql(code, XmlUnit.querytoXml(query, "utf-8").asXML(), "sc_query", user.getId());
			localCommonService.writeEncryptFile(XmlUnit.querytoXml(query, "utf-8").asXML(), request.getSession().getServletContext().getRealPath("/WEB-INF/query/"+query.getCode()+".query"),"utf-8");
			if(null!=cache)
			{
				cache.remove("query:/WEB-INF/query/"+code+".query");
			}
		}
	}
	
	private Boolean listHasCurrCol(List<Column> listcol,String strColName)
	{
		Boolean blInfo;
		blInfo=false;
		for(Column colInfo:listcol){
			if(colInfo.getKey().equals(strColName)==true){
				blInfo=true;
				break;
			}
		}
		return blInfo;
	}
}
