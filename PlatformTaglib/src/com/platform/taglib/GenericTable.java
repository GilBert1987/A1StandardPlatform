package com.platform.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.TagShow;

public class GenericTable extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GenericTable.class);
	private String id;
	private Integer showtype;
	private String hasNo = null;
	private String hasSel = null;
	private String datasql = null;
	private String dataname = null;
	private String datatype = null;
	private String dataBind = null;
	private String datamenu = null;
	private String rediskey = null;
	private CommonService comService;
	
	public String getRediskey() {
		return rediskey;
	}

	public void setRediskey(String rediskey) {
		this.rediskey = rediskey;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHasNo() {
		return hasNo;
	}

	public void setHasNo(String hasNo) {
		this.hasNo = hasNo;
	}

	public String getDatasql() {
		return datasql;
	}

	public void setDatasql(String datasql) {
		this.datasql = datasql;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDatamenu() {
		return datamenu;
	}

	public void setDatamenu(String datamenu) {
		this.datamenu = datamenu;
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
	
	public String getHasSel() {
		return hasSel;
	}

	public void setHasSel(String hasSel) {
		this.hasSel = hasSel;
	}
	
	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}
	
	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		try {
			input+=createGenericTable();
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	private String createGenericTable() {
		Entry entry;
		String strSql;
		Map mapRowInfo;
		String strTitle;
		String strAlign;
		String strWidth;
		StringBuilder sb;
		String strColInfo;
		String strRedisKey;
		JSONArray menuList;
		JSONObject menuObj;
		JSONArray jsonArrList;
		List<Map> listMapInfo;
		JSONObject jsonDataObj;
		Iterator<Map.Entry> entries;
		Map<String, Object> mapInfo;
		strSql="";
		mapInfo=null;
		menuList=null;
		strRedisKey="";
		jsonArrList=new JSONArray();
		if(null!=rediskey && ("".equals(rediskey)==false)){
			try {
				strRedisKey=comService.loadRedisObj(rediskey).toString();
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
		}
		sb=new StringBuilder();
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
			if(null!=menuList && null != this.datasql && "".equals(datasql)==false && null != dataname && "".equals(dataname)==false && true=="sql".equals(datatype))
			{
				try {
					if(null!=rediskey && ("".equals(rediskey)==false))
					{
						strSql=datasql.replaceAll("\\|\\{redis\\.key\\}", strRedisKey);
					}
					else
					{
						strSql=datasql;
					}
					mapInfo=comService.queryFullSql(dataname, strSql, null);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(null!=mapInfo)
				{
					listMapInfo=(List)mapInfo.get("retMap");
					sb.append("<tbody>");
					for(int j=0;j<listMapInfo.size();j++)
					{
						jsonDataObj=new JSONObject();
						sb.append("<tr>");
						mapRowInfo=listMapInfo.get(j);
						entries=mapRowInfo.entrySet().iterator();
						while (entries.hasNext()) { 
							entry = entries.next();
							jsonDataObj.put(entry.getKey(), (null==mapRowInfo.get(entry.getKey())?"":mapRowInfo.get(entry.getKey()).toString()));
						}
						for(int i=0;i<menuList.size();i++)
						{
							menuObj=menuList.getJSONObject(i);
							strAlign=menuObj.getString("align");
							strWidth=menuObj.getString("width");
							strColInfo=menuObj.getString("col");
							if(0==i && "1".equals(hasSel)==true)
							{
								sb.append("<td class=\"ui-th-column ui-th-ltr\" w=\"50\" width=\"50px;\">");
								sb.append("<input name=\""+id+"_radio\" type=\"radio\" value=\""+(j+1)+"\">");
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
								sb.append("<span alt=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()).replace("\"", "&quot;")+"\" title=\""+(null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()).replace("\"", "&quot;")+"\">");
								sb.append((null==mapRowInfo.get(strColInfo)?"":mapRowInfo.get(strColInfo).toString()));
								sb.append("</span>");
								sb.append("</td>");
							}
						}
						jsonArrList.add(jsonDataObj);
						sb.append("</tr>");
					}
					sb.append("</tbody>");
				}
			}
		}
		sb.append("</table>");
		sb.append("</div>");
		sb.append("<input id=\""+this.dataBind+"\" type=\"hidden\" name=\""+this.dataBind+"\" value=\""+jsonArrList.toString().replace("\"", "&quot;")+"\" />");
		sb.append("<iframe style=\"display:none;\" onload=\"setTimeout(fn_initTableWidth('"+id+"_table'),200);\"></iframe>");
		return sb.toString();
	}
}
