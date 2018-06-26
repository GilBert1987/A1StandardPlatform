package com.platform.data;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.common.entity.menu.Menu;
import com.common.entity.menu.ShortCut;
import com.common.entity.menu.SubMenu;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CoreService;

public class MenuDataLoad implements IFormDataLoad {

	private static Logger log = Logger.getLogger(MenuDataLoad.class);
	private CoreService coreService;
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Menu menu;
		String strId;
		JSONObject jsonObj;
		if(request.getParameter("id")!=null){
			menu=null;
			coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
			strId=request.getParameter("id").toString();
			try {
				menu = coreService.getMenu(strId);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			if (menu==null)
		    {
				log.error(strId+" Menu file is null");
		    }
			else
			{
				jsonObj=JSONObject.fromObject(menu);
				request.setAttribute("_menudata", jsonObj);
			}
		}
	}

	public void saveMenu(String code,HttpServletRequest request,HttpServletResponse response) {
		Menu menu;
		Integer iHasroot;
		String strHasroot;
		List<ShortCut> listCuts;
		List<SubMenu> listMenus;
		JSONArray jscutlist;
		JSONArray jsmenulist;
		JSONObject jsonCutItem;
		JSONObject jsonMenuItem;
		ShortCut cutInfo;
		SubMenu menuInfo;
		CacheManager manager;
		Ehcache cache;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		menu=new Menu();
		iHasroot=0;
		strHasroot=request.getParameter("hasroot");
		listCuts=new ArrayList<ShortCut>();
		listMenus=new ArrayList<SubMenu>();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		menu.setCode(code);
		menu.setTitle(request.getParameter("title"));
		menu.setTitlepic(request.getParameter("titlepic"));
		menu.setRemark(request.getParameter("remark"));
		menu.setFiledir(request.getParameter("filedir"));
		menu.setRooturl(request.getParameter("rooturl"));
		menu.setLevel((null==request.getParameter("level")?0:Integer.parseInt(request.getParameter("level").toString())));
		if(null!=strHasroot && "".equals(strHasroot)==false)
		{
			iHasroot=Integer.parseInt(strHasroot);
		}
		menu.setIroot(iHasroot);
		menu.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		menu.setListShortCuts(listCuts);
		menu.setListSubMenus(listMenus);
		menu.setIsUpdate(true);
		if(null!=request.getParameter("listShortCuts") && ("".equals(request.getParameter("listShortCuts"))==false)){
			jscutlist=JSONArray.fromObject(request.getParameter("listShortCuts"));
			for(int i=0;i<jscutlist.size();i++){
				jsonCutItem=(JSONObject)jscutlist.get(i);
				cutInfo=new ShortCut();
				cutInfo.setCode(jsonCutItem.getString("code"));
				cutInfo.setName(jsonCutItem.getString("name"));
				cutInfo.setOrder(jsonCutItem.getString("order"));
				cutInfo.setPic(jsonCutItem.getString("pic"));
				cutInfo.setUrl(jsonCutItem.getString("url"));
				listCuts.add(cutInfo);
			}
		}
		if(null!=request.getParameter("listSubMenus") && ("".equals(request.getParameter("listSubMenus"))==false)){
			jsmenulist=JSONArray.fromObject(request.getParameter("listSubMenus"));
			for(int i=0;i<jsmenulist.size();i++){
				jsonMenuItem=(JSONObject)jsmenulist.get(i);
				menuInfo=new SubMenu();
				menuInfo.setCode(jsonMenuItem.getString("code"));
				menuInfo.setName(jsonMenuItem.getString("name"));
				menuInfo.setOrder(jsonMenuItem.getString("order"));
				menuInfo.setOpentype(jsonMenuItem.getString("opentype").equals("null")?"0":jsonMenuItem.getString("opentype"));
				menuInfo.setParentcode(jsonMenuItem.getString("parentcode"));
				menuInfo.setPic(jsonMenuItem.getString("pic"));
				menuInfo.setUrl(jsonMenuItem.getString("url"));
				menuInfo.setIsshow(jsonMenuItem.getString("isshow"));
				listMenus.add(menuInfo);
			}
		}
		if(menu.getCode().trim().equals("")==false && "configform".equals(menu.getCode().trim())==false)
		{
			try {
				coreService.saveMenu(menu);
			} catch (Exception e) {
				log.error(e.toString(),e);
			}
			if(null!=cache)
			{
				cache.remove("menu:/WEB-INF/menu/"+code+".menu");
			}
		}
	}

	
}
