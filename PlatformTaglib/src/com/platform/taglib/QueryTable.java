package com.platform.taglib;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.common.entity.query.Column;
import com.common.entity.query.Query;
import com.common.entity.query.Title;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;
import com.common.unit.HtmlUnit;

public class QueryTable extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(QueryTable.class);
	private String id;
	private String dataBind;
	private Integer showtype;
	private String coltype;
	private String clientFun;
	private CoreService coreService;
	private CommonService comService;
	private LocalCommonService localCommonService;
	
	public String getColtype() {
		return coltype;
	}

	public void setColtype(String coltype) {
		this.coltype = coltype;
	}

	public String getClientFun() {
		return clientFun;
	}

	public void setClientFun(String clientFun) {
		this.clientFun = clientFun;
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
		this.dataBind=dataBind;
	}

	@Override
	public void setShowtype(Integer itagtype) {
		this.showtype=itagtype;
	}

	@Override
	public Integer getShowtype() {
		return this.showtype;
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
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", (HttpServletRequest)this.pageContext.getRequest());
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		try {
			input+=createGenericTable((HttpServletRequest)pageContext.getRequest());
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private String createGenericTable(HttpServletRequest request) {
		String hasNo;
		String hasSel;
		String colInfo;
		String datasql;
		Map mapRowInfo;
		String datatype;
		String dataname;
		String strTitle;
		String strAlign;
		String strWidth;
		String datamenu;
		StringBuilder sb;
		String strColInfo;
		JSONArray menuList;
		JSONObject menuObj;
		List<Map> listMapInfo;
		Map<String, Object> mapInfo;
		mapInfo=null;
		menuList=null;
		hasNo="0";
		hasSel="1";
		datatype="sql";
		dataname="com";
		sb=new StringBuilder();
		datasql="SELECT id,name FROM sc_query WHERE typeid='"+coltype+"'";
		datamenu="[{\"title\":\"编码\",\"width\":\"170\",\"align\":\"left\",\"col\":\"id\"},{\"title\":\"名称\",\"width\":\"220\",\"align\":\"left\",\"col\":\"name\"}]";
		sb.append("<div class=\"fixed-table-container\">");
		sb.append("<table id=\""+id+"_table\" class=\"table table-hover\" style=\"width:100%;table-layout:fixed;\">");
		sb.append("<thead>");
		sb.append("<tr>");
		if("1".equals(hasSel)==true)
		{
			sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
			sb.append("选择");
			sb.append("</th>");
		}
		if("1".equals(hasNo)==true)
		{
			sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
			sb.append("序号");
			sb.append("</th>");
		}
		if(null != datamenu && "".equals(datamenu)==false)
		{
			menuList=JSONArray.fromObject(datamenu);
			for(int i=0;i<menuList.size();i++)
			{
				menuObj=menuList.getJSONObject(i);
				strTitle=menuObj.getString("title");
				strWidth=menuObj.getString("width");
				sb.append("<th class=\"ui-th-column ui-th-ltr\" w=\""+strWidth+"\" width=\""+strWidth+"px\">");
				sb.append(strTitle);
				sb.append("</th>");
			}
		}
		sb.append("</tr>");
		sb.append("</thead>");
		if(showtype!=0)
		{
			if(null!=menuList && null != datasql && "".equals(datasql)==false && null != dataname && "".equals(dataname)==false && true=="sql".equals(datatype))
			{
				try {
					mapInfo=comService.queryFullSql(dataname, datasql, null);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(null!=mapInfo)
				{
					listMapInfo=(List)mapInfo.get("retMap");
					sb.append("<tbody>");
					for(int j=0;j<listMapInfo.size();j++)
					{
						sb.append("<tr>");
						mapRowInfo=listMapInfo.get(j);
						for(int i=0;i<menuList.size();i++)
						{
							menuObj=menuList.getJSONObject(i);
							strAlign=menuObj.getString("align");
							strWidth=menuObj.getString("width");
							strColInfo=menuObj.getString("col");
							if(0==i && "1".equals(hasSel)==true)
							{
								sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
								sb.append("<input name=\""+id+"_radio\" type=\"radio\" value=\"");
								if("id".equals(strColInfo)==true)
								{
									colInfo=loadQueryColumns((null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()),request);
									sb.append(colInfo);
								}
								sb.append("\" ");
								sb.append("onclick=\""+clientFun+"\" ");
								sb.append(">");
								sb.append("</td>");
							}
							if(0==i && "1".equals(hasNo)==true)
							{
								sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
								sb.append((j+1));
								sb.append("</td>");
							}
							if(null!=strColInfo && "".equals(strColInfo)==false)
							{
								sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\""+strWidth+"\"  width=\""+strWidth+"px\" align=\""+strAlign+"\">");
								sb.append("<span alt=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString())+"\" title=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString())+"\">");
								sb.append((null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()));
								sb.append("</span>");
								sb.append("</td>");
							}
						}
						sb.append("</tr>");
					}
					sb.append("</tbody>");
				}
			}
		}
		sb.append("</table>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String loadQueryColumns(String code,HttpServletRequest request) {
		Map map;
		Query query;
		Title title;
		byte[] bytes;
		Integer type;
		List<Map> list;
		String strCode;
		String strValue;
		String strSqlInfo;
		JSONArray jsonArr;
		JSONObject jsonObj;
		String strEncodeMap;
		List<Map> listSqlMap;
		String strTitleSqlInfo;
		Object[] queryConfigParams;
		Map<String, Object> queryConfig;
		query=null;
		jsonArr=new JSONArray();
		strSqlInfo="select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
		queryConfigParams=new Object[]{code};
		try {
			queryConfig=comService.queryFullSql("com", strSqlInfo,queryConfigParams);
			listSqlMap=(List<Map>)queryConfig.get("retMap");
			if(0!=listSqlMap.size())
			{
				map=listSqlMap.get(0);
				bytes=(byte[])map.get("bytesinfo");
				type=(Integer)map.get("type");
				strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
				if(1==type)
				{
					strEncodeMap=coreService.readEncryptSql(strEncodeMap);
				}
				query = localCommonService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
			}
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		if(null!=query){
			for(Column column:query.getColumns()){
				if(null!=column.getTitle()){
					title=column.getTitle();
					if("1".equals(title.getIsshow())==true){
						if("1".equals(column.getTypeid()))
						{
							if(null!=column.getTitleSysId() && null != column.getTitleSqlInfo()){
								strTitleSqlInfo=HtmlUnit.getValueByRequest(column.getTitleSqlInfo(), request, null);
								try
								{
									list=comService.queryListSql(column.getTitleSysId(), strTitleSqlInfo,null);
									for(Map mapInfo:list){
										strCode=column.getKey()+"_"+mapInfo.get("code").toString();
										strValue=mapInfo.get("value").toString();
										jsonObj=new JSONObject();
										jsonObj.put("key", strCode);
										jsonObj.put("name", strValue);
										jsonObj.put("type", "extend");
										jsonArr.add(jsonObj);
									}
								}
								catch(Exception e)
								{
									log.error(e.toString(),e);
								}
							}
						}
						else
						{
							jsonObj=new JSONObject();
							jsonObj.put("key", column.getKey());
							jsonObj.put("name", column.getName());
							jsonObj.put("type", column.getType());
							jsonArr.add(jsonObj);
						}
					}
				}
			}
		}
		return jsonArr.toString().replaceAll("\"", "&quot;");
	}
}
