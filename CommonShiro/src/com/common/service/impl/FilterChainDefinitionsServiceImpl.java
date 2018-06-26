package com.common.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.common.service.CommonService;

public class FilterChainDefinitionsServiceImpl extends AbstractFilterChainDefinitionsService {

	private static Logger log = Logger.getLogger(FilterChainDefinitionsServiceImpl.class);
	
	@Resource
	private CommonService commonService;
	
	@Override
	public Map<String, String> initOtherPermission() {
		List<Map> list;
		String strInfo;
		String strUrl;
		String strRoleId;
		String strTempUrl;
		Entry<String, String> entry;
		Iterator<Entry<String, String>> iterator;
		Map<String,String> rowInfo;
		HashMap<String,String> map;
		strUrl="";
		list=null;
		strInfo="SELECT url,roleid ";
		strInfo+=" FROM( ";
		strInfo+=" 	SELECT sc_menu_submenu.url,sc_role_permission.role_id AS roleid  ";
		strInfo+=" 	FROM sc_menu_submenu  ";
		strInfo+=" 	JOIN sc_role_permission  ";
		strInfo+=" 	ON sc_menu_submenu.code=sc_role_permission.permission  ";
		strInfo+=" 	WHERE sc_menu_submenu.url IS NOT NULL AND sc_menu_submenu.url !='' AND sc_menu_submenu.url !='#'  ";
		strInfo+=" 	UNION ";
		strInfo+=" 	SELECT sc_menu_shortcut.url,sc_role_permission.role_id AS roleid "; 
		strInfo+=" 	FROM sc_menu_shortcut ";
		strInfo+=" 	JOIN sc_role_permission  ";
		strInfo+=" 	ON sc_menu_shortcut.code=sc_role_permission.permission "; 
		strInfo+=" 	WHERE sc_menu_shortcut.url IS NOT NULL AND sc_menu_shortcut.url !='' AND sc_menu_shortcut.url !='#' "; 
		strInfo+=" ) AS t ";
		strInfo+=" ORDER BY url "; 
		map=new HashMap<String,String>();
		try {
			list=commonService.queryListSql("shiro", strInfo,null);
		} catch (Exception e) {
			log.error(e);
		}
		for(int i=0;i<list.size();i++)
		{
			rowInfo=list.get(i);
			strTempUrl=rowInfo.get("url");
			if(strTempUrl.equals(strUrl)==true)
			{
				strRoleId=map.get(strTempUrl);
				map.put(strTempUrl, strRoleId+","+rowInfo.get("roleid"));
			}
			else
			{
				map.put(strTempUrl, rowInfo.get("roleid"));
			}
			strUrl=strTempUrl;
		}
		iterator=map.entrySet().iterator();
		while(iterator.hasNext()){
			entry=iterator.next();
			entry.setValue(String.format(this.ROLE_STRING,entry.getValue()));
		}
		return map;
	}

}
