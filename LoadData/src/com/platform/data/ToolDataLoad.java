package com.platform.data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.common.entity.login.User;
import com.common.entity.tool.Item;
import com.common.entity.tool.ItemShow;
import com.common.entity.tool.Tool;
import com.common.entity.tool.ToolShowStyle;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class ToolDataLoad implements IFormDataLoad  {
	
	private static Logger log = Logger.getLogger(ToolDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Tool tool;
		byte[] bytes;
		String strId;
		Integer type;
		String strSqlInfo;
		String strEncodeMap;
		JSONObject jsonObj;
		List<Map> listSqlMap;
		Map mapList;
		Object[] params;
		CoreService coreService;
		CommonService commService;
		LocalCommonService localcommService;
		tool = null;
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		if (request.getParameter("id") != null) {
			listSqlMap=null;
			strId = request.getParameter("id").toString();
			strSqlInfo = "select type,bytesinfo from sc_tool where id=? and bytesinfo is not null";
			params=new Object[]{
				strId
			};
			try {
				listSqlMap = commService.queryListSql("com", strSqlInfo,params);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			if (0 != listSqlMap.size()) {
				mapList = listSqlMap.get(0);
				bytes = (byte[]) mapList.get("bytesinfo");
				type=(Integer)mapList.get("type");
				strEncodeMap = new String(bytes, Charset.forName("UTF-8"));
				if(null!=type && 0!=type)
				{
					strEncodeMap=coreService.readEncryptSql(strEncodeMap);
				}
				tool = localcommService.formatXmltoTool(coreService.decryptString(strEncodeMap));
			} else {
				if (!XmlUnit
						.existsFile(
								request.getSession()
										.getServletContext()
										.getRealPath(
												"/WEB-INF/tool/" + strId
														+ ".tool"))
						.booleanValue()) {
					log.error(strId + " tool file is null");
				} else {
					try {
						tool = XmlUnit.formatXmltoTool(XmlUnit
								.loadInit(request
										.getSession()
										.getServletContext()
										.getRealPath(
												"/WEB-INF/tool/" + strId
														+ ".tool")));
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Xml init Error by id" + strId);
					}
				}
			}
			if (tool != null) {
				jsonObj = JSONObject.fromObject(tool);
				request.setAttribute("_tooldata", jsonObj);
			}
		}
	}
	
	
	public void saveTool(String code,HttpServletRequest request,HttpServletResponse response) {
		Tool tool;
		Item item;
		ToolShowStyle toolStyle;
		ItemShow itemShow;
		JSONArray jsitemlist;
		JSONArray jsstylelist;
		JSONArray jslistshow;
		JSONObject jsonItem;
		JSONObject jsonShow;
		JSONObject jsonShowItem;
		List<Item> listItem;
		List<ToolShowStyle> listshow;
		List<ItemShow> listItemShow;
		CacheManager manager;
		Ehcache cache;
		Subject subject;
		User user;
		CoreService coreService;
		LocalCommonService localCommonService;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		tool=new Tool();
		listItem=new ArrayList<Item>();
		listshow=new ArrayList<ToolShowStyle>();
		tool.setCode(code);
		tool.setName(request.getParameter("name"));
		tool.setRemark(request.getParameter("remark"));
		tool.setFiledir(request.getParameter("filedir"));
		tool.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		tool.setListitem(listItem);
		tool.setListstyle(listshow);
		tool.setIsUpdate(true);
		if(null!=request.getParameter("listitem") && ("".equals(request.getParameter("listitem"))==false)){
			jsitemlist=JSONArray.fromObject(request.getParameter("listitem"));
			for(int i=0;i<jsitemlist.size();i++){
				jsonItem=(JSONObject)jsitemlist.get(i);
				item=new Item();
				item.setCode(jsonItem.getString("code"));
				item.setName(jsonItem.getString("name"));
				item.setOrder(jsonItem.getString("order"));
				item.setUrl(jsonItem.getString("url"));
				item.setIcon(jsonItem.getString("icon"));
				item.setIconstyle(jsonItem.getString("iconstyle"));
				item.setBtnclass(jsonItem.getString("btnclass"));
				listItem.add(item);
			}
		}
		if(null!=request.getParameter("liststyle") && ("".equals(request.getParameter("liststyle"))==false)){
			jsstylelist=JSONArray.fromObject(request.getParameter("liststyle"));
			for(int i=0;i<jsstylelist.size();i++){
				jsonShow=(JSONObject)jsstylelist.get(i);
				toolStyle=new ToolShowStyle();
				listItemShow=new ArrayList<ItemShow>();
				toolStyle.setCode(jsonShow.getString("code"));
				toolStyle.setName(jsonShow.getString("name"));
				toolStyle.setRemark(jsonShow.getString("remark"));
				jslistshow=(JSONArray)jsonShow.get("listshow");
				toolStyle.setListshow(listItemShow);
				for(int j=0;j<jslistshow.size();j++)
				{
					jsonShowItem=(JSONObject)jslistshow.get(j);
					itemShow=new ItemShow();
					itemShow.setCode(jsonShowItem.getString("code"));
					itemShow.setIsselect(jsonShowItem.getString("isselect"));
					itemShow.setIsshow(jsonShowItem.getString("isshow"));
					listItemShow.add(itemShow);
				}
				listshow.add(toolStyle);
			}
		}
		if(tool.getCode().trim().equals("")==false && "configform".equals(tool.getCode().trim())==false)
		{
			tool.setIsUpdate(true);
			coreService.writeEncryptSql(code, XmlUnit.tooltoXml(tool, "utf-8").asXML(), "sc_tool", user.getId());
			localCommonService.writeEncryptFile(XmlUnit.tooltoXml(tool, "utf-8").asXML(), request.getSession().getServletContext().getRealPath("/WEB-INF/tool/"+tool.getCode()+".tool"),"utf-8");
			if(null!=cache)
			{
				cache.remove("tool:/WEB-INF/tool/"+code+".tool");
			}
		}
	}
}
