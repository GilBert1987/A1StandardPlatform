package com.common.viewResolver;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.log4j.Logger;

import com.common.entity.tree.Tree;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class TreeView extends PlatformResourceView{
	
	private static Logger log = Logger.getLogger(TreeView.class);
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String titlename;
	private String jstag;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public String getJstag() {
		return jstag;
	}
	public void setJstag(String jstag) {
		this.jstag = jstag;
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
		String strViewPath;
		String strSqlInfo;
		HttpServletRequest requestToExpose;
		String dispatcherPath;
		RequestDispatcher rd;
		List<Map> listSqlMap;
		Object[] params;
		Map<String, String> config;
		Tree tree;
		CoreService coreService;
		CommonService commService;
		LocalCommonService localcommService;
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
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
		if (titlename == null) {
			throw new ServletException("Check config titlename");
		}
		if (jstag == null) {
			throw new ServletException("Check config jstag");
		}
		if (cacheName == null) {
			throw new ServletException("Check config cacheName");
		}
		strViewPath=request.getAttribute("_viewpath").toString();
		params=new Object[]{
				strViewPath
		};
		strSqlInfo="select type,bytesinfo from sc_tree where id=? and bytesinfo is not null";
		listSqlMap=commService.queryListSql("com", strSqlInfo,params);
		if(0==listSqlMap.size())
		{
			if(localcommService.existsFile(request.getSession().getServletContext().getRealPath(getUrl()))==false)
			{
				throw new ServletException(getUrl()+" Tree file is null");
			}
		}
		config = new HashMap<String, String>();
		config.put("templatepage", templatepage);
		config.put("temppage", temppage);
		config.put("prefix", prefix);
		config.put("suffix", suffix);
		config.put("titlename", titlename);
		config.put("jstag", jstag);
		config.put("defaultEncoding", defaultEncoding);
		if(0==listSqlMap.size())
		{
			tree = getTreeByRequest(requestToExpose,localcommService);
		}
		else
		{
			tree = getTreeBySqlRequest(requestToExpose,localcommService,coreService,listSqlMap.get(0));
		}
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage + ("".equals(tree.getFiledir())==true?"":(tree.getFiledir()+"/")) + strTempJsp;
		if (treeToClass(strViewPath,coreService,localcommService,tree, config, request,response,strTempJsp) == true) {
			rd = getRequestDispatcher(requestToExpose, strTempJsp);
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
	
	private Tree getTreeBySqlRequest(HttpServletRequest requestToExpose,
			LocalCommonService localcommService, CoreService coreService,
			Map map) {
		Tree tree;
		byte[] bytes;
		Integer type;
		String strEncodeMap;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("tree:"+getUrl());
		bytes=(byte[])map.get("bytesinfo");
		type=(Integer)map.get("type");
		strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
		if(null!=type && 0!=type)
		{
			strEncodeMap=coreService.readEncryptSql(strEncodeMap);
		}
		if(null != element)
		{
			tree=(Tree)element.getObjectValue();
		}
		else
		{
			tree = localcommService.formatXmltoTree(coreService.decryptString(strEncodeMap));
			element=new net.sf.ehcache.Element("tree:"+getUrl(),tree);
			cache.put(element);
		}
		return tree;
	}
	private boolean treeToClass(String strCode, CoreService coreService, LocalCommonService localcommService, Tree tree,
			Map<String, String> config, HttpServletRequest request,
			HttpServletResponse response, String strTempJsp) {
		Boolean blInfo;
		String strJspPage;
		tree.setIsUpdate(false);
		strJspPage=localcommService.getJspStringByTree(tree, config, request, response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(tree.getLastmodifiedtime()));
		if(localcommService.checkClassLastmodifiedtime(strTempJsp,Long.valueOf(tree.getLastmodifiedtime()),request)==true)
		{
			tree.setIsUpdate(true);
			coreService.writeEncryptSql(strCode, XmlUnit.treetoXml(tree, this.defaultEncoding).asXML(), "sc_tree", "system");
			localcommService.writeEncryptFile(XmlUnit.treetoXml(tree, this.defaultEncoding).asXML(), request.getSession().getServletContext().getRealPath(getUrl()), this.defaultEncoding);
		}
		blInfo = true;
		return blInfo;
	}
	private Tree getTreeByRequest(HttpServletRequest request,LocalCommonService localcommService) throws Exception {
		Tree tree;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("tree:"+getUrl());
		if(null != element)
		{
			tree=(Tree)element.getObjectValue();
		}
		else
		{
			tree = localcommService.formatXmltoTree(localcommService.loadInit(request.getSession()
					.getServletContext().getRealPath(getUrl())));
			element=new net.sf.ehcache.Element("tree:"+getUrl(),tree);
			cache.put(element);
		}
		return tree;
	}
}
