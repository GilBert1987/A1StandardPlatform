package com.common.viewResolver;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.log4j.Logger;
import com.common.entity.menu.Menu;
import com.common.factory.ReflectFactory;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;

public class MenuView extends PlatformResourceView{
	
	private static Logger log = Logger.getLogger(MenuView.class);
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String titlename;
	private String shortcut;
	private String menus;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getShortcut() {
		return shortcut;
	}
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
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
		String strTempJsp;
		HttpServletRequest requestToExpose;
		String dispatcherPath;
		RequestDispatcher rd;
		Map<String, String> config;
		Menu menu;
		LocalCommonService localcommService;
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		// Determine which request handle to expose to the RequestDispatcher.
		requestToExpose = getRequestToExpose(request);
		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);
		// Determine the path for the request dispatcher.
		dispatcherPath = prepareForRendering(requestToExpose, response);
		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		rd = getRequestDispatcher(requestToExpose, dispatcherPath);
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
		if (shortcut == null){
			throw new ServletException("Check config shortcut");
		}
		if (menus == null){
			throw new ServletException("Check config menus");
		}
		if (cacheName == null) {
			throw new ServletException("Check config cacheName");
		}
		config = new HashMap<String, String>();
		config.put("titlename", titlename);
		config.put("templatepage", templatepage);
		config.put("temppage", temppage);
		config.put("prefix", prefix);
		config.put("suffix", suffix);
		config.put("shortcut", shortcut);
		config.put("menus", menus);
		config.put("defaultEncoding", defaultEncoding);
		menu = getMenuByRequest(requestToExpose);
		if(menu==null)
		{
			throw new ServletException(getUrl()+" Menu file is null");
		}
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage  + ("".equals(menu.getFiledir())==true?"":(menu.getFiledir()+"/")) + strTempJsp;
		if (menuToClass(localcommService,menu, config, request,response,strTempJsp) == true) {
			rd = getRequestDispatcher(requestToExpose, strTempJsp);
		}
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
	
	private boolean menuToClass(LocalCommonService localcommService, Menu menu,
			Map<String, String> config, HttpServletRequest request,
			HttpServletResponse response, String strTempJsp) {
		Boolean blInfo;
		String strJspPage;
		menu.setIsUpdate(false);
		strJspPage=localcommService.getJspStringByMenu(menu, config,request,response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(menu.getLastmodifiedtime()));
		blInfo = true;
		return blInfo;
	}
	
	private Menu getMenuByRequest(HttpServletRequest request) throws Exception {
		Menu menu;
		CacheManager manager;
		Ehcache cache;
		CoreService coreService;
		net.sf.ehcache.Element element;
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("menu:"+getUrl());
		if(null != element)
		{
			menu=(Menu)element.getObjectValue();
		}
		else
		{
			menu = coreService.getMenu(request.getAttribute("_viewpath").toString());
			element=new net.sf.ehcache.Element("menu:"+getUrl(),menu);
			cache.put(element);
		}
		return menu;
	}
	
	public String getMenus() {
		return menus;
	}
	
	public void setMenus(String menus) {
		this.menus = menus;
	}
}
