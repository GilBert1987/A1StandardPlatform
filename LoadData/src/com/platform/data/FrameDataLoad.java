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

import com.common.entity.frame.Frame;
import com.common.entity.frame.FrameNode;
import com.common.entity.login.User;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class FrameDataLoad implements IFormDataLoad {
	
	private static Logger log = Logger.getLogger(FrameDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Frame frame;
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
		frame=null;
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		if(request.getParameter("id")!=null){
			listSqlMap=null;
			strId=request.getParameter("id").toString();
			params=new Object[]{
				strId
			};
			strSqlInfo="select type,bytesinfo from sc_frame where id=? and bytesinfo is not null";
			try {
				listSqlMap=commService.queryListSql("com", strSqlInfo,params);
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
				frame = localcommService.formatXmltoFrame(coreService.decryptString(strEncodeMap));
			}
			else
			{
				if (!XmlUnit.existsFile(request.getSession().getServletContext().getRealPath("/WEB-INF/frame/"+strId+".frame")).booleanValue())
			    {
					log.error(strId+" frame file is null");
			    }
				else
				{
					try {
						frame = XmlUnit.formatXmltoFrame(XmlUnit.loadInit(request.getSession().getServletContext().getRealPath("/WEB-INF/frame/"+strId+".frame")));
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
			if(frame!=null)
			{
				jsonObj=JSONObject.fromObject(frame);
				request.setAttribute("_framedata", jsonObj);
			}
		}
	}

	public void saveFrame(String code,HttpServletRequest request,HttpServletResponse response){
		Frame frame;
		List<FrameNode> list;
		FrameNode frNode;
		JSONArray jslist;
		JSONObject jsNode;
		CacheManager manager;
		Ehcache cache;
		Subject subject;
		User user;
		CoreService coreService;
		CommonService commonService;
		LocalCommonService localCommonService;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		commonService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		frame=new Frame();
		list=new ArrayList<FrameNode>();
		frame.setCode(code);
		frame.setFiledir(request.getParameter("filedir"));
		frame.setRemark(request.getParameter("remark"));
		frame.setName(request.getParameter("name"));
		frame.setType(request.getParameter("type"));
		frame.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		frame.setIsUpdate(true);
		frame.setNodelist(list);
		if(null!=request.getParameter("nodelist"))
		{
			jslist=JSONArray.fromObject(request.getParameter("nodelist"));
			for(int i=0;i<jslist.size();i++){
				frNode=new FrameNode();
				jsNode=(JSONObject)jslist.get(i);
				frNode.setType(jsNode.getString("type"));
				frNode.setUrl(jsNode.getString("url"));
				frNode.setNumber(jsNode.getString("number"));
				list.add(frNode);
			}
		}
		if(frame.getCode().trim().equals("")==false && "configform".equals(frame.getCode().trim())==false)
		{
			frame.setIsUpdate(true);
			coreService.writeEncryptSql(code, XmlUnit.frametoXml(frame, "utf-8").asXML(), "sc_frame", user.getId());
			localCommonService.writeEncryptFile(XmlUnit.frametoXml(frame, "utf-8").asXML(), request.getSession().getServletContext().getRealPath("/WEB-INF/frame/"+frame.getCode()+".frame"),"utf-8");
			if(null!=cache)
			{
				cache.remove("frame:/WEB-INF/frame/"+code+".frame");
			}
		}
	}
}
