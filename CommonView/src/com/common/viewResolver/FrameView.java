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

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.common.entity.frame.Frame;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class FrameView extends PlatformResourceView{
	
	private static Logger log = Logger.getLogger(FrameView.class);
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String titlename;
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
		Frame frame;
		String strTempJsp;
		HttpServletRequest requestToExpose;
		String strSqlInfo;
		String strViewPath;
		String dispatcherPath;
		RequestDispatcher rd;
		List<Map> listSqlMap;
		Object[] params;
		Map<String, String> config;
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
		if (cacheName == null) {
			throw new ServletException("Check config cacheName");
		}
		strViewPath=request.getAttribute("_viewpath").toString();
		params=new Object[]{
			strViewPath
		};
		strSqlInfo="select type,bytesinfo from sc_frame where id=? and bytesinfo is not null";
		listSqlMap=commService.queryListSql("com", strSqlInfo,params);
		if(0==listSqlMap.size())
		{
			if(localcommService.existsFile(request.getSession().getServletContext().getRealPath(getUrl()))==false)
			{
				throw new ServletException(getUrl()+" frame file is null");
			}
		}
		config = new HashMap<String, String>();
		config.put("templatepage", templatepage);
		config.put("temppage", temppage);
		config.put("prefix", prefix);
		config.put("suffix", suffix);
		config.put("titlename", titlename);
		config.put("defaultEncoding", defaultEncoding);
		if(0==listSqlMap.size())
		{
			frame = getFrameByRequest(requestToExpose,localcommService);
		}
		else
		{
			frame = getFrameBySqlRequest(requestToExpose,localcommService,coreService,listSqlMap.get(0));
		}
		
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage + ("".equals(frame.getFiledir())==true?"":(frame.getFiledir()+"/")) + strTempJsp;
		if (frameToClass(strViewPath,coreService,localcommService,frame, config, request,response,strTempJsp) == true) {
			rd = getRequestDispatcher(requestToExpose, strTempJsp);
		}
		if ("get".equals(request.getMethod().toLowerCase()) == true) {
			
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
	
	private Frame getFrameBySqlRequest(HttpServletRequest requestToExpose,LocalCommonService localcommService, CoreService coreService,Map map) {
		Frame frame;
		byte[] bytes;
		Integer type;
		String strEncodeMap;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("frame:"+getUrl());
		bytes=(byte[])map.get("bytesinfo");
		type=(Integer)map.get("type");
		strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
		if(null!=type && 0!=type)
		{
			strEncodeMap=coreService.readEncryptSql(strEncodeMap);
		}
		if(null != element)
		{
			frame=(Frame)element.getObjectValue();
		}
		else
		{
			frame = localcommService.formatXmltoFrame(coreService.decryptString(strEncodeMap));
			element=new net.sf.ehcache.Element("frame:"+getUrl(),frame);
			cache.put(element);
		}
		return frame;
	}
	private boolean frameToClass(String strCode, CoreService coreService,LocalCommonService localcommService, Frame frame,
			Map<String, String> config, HttpServletRequest request,
			HttpServletResponse response, String strTempJsp) {
		Boolean blInfo;
		String strJspPage;
		frame.setIsUpdate(false);
		strJspPage=localcommService.getJspStringByFrame(frame, config, request, response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(frame.getLastmodifiedtime()));
		if(localcommService.checkClassLastmodifiedtime(strTempJsp,Long.valueOf(frame.getLastmodifiedtime()),request)==true)
		{
			frame.setIsUpdate(true);
			coreService.writeEncryptSql(strCode, XmlUnit.frametoXml(frame, this.defaultEncoding).asXML(), "sc_frame", "system");
			localcommService.writeEncryptFile(XmlUnit.frametoXml(frame, this.defaultEncoding).asXML(), request.getSession().getServletContext().getRealPath(getUrl()), this.defaultEncoding);
		}
		blInfo = true;
		return blInfo;
	}
	private Frame getFrameByRequest(HttpServletRequest request,LocalCommonService localcommService) throws Exception {
		Frame frame;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("frame:"+getUrl());
		if(null != element)
		{
			frame=(Frame)element.getObjectValue();
		}
		else
		{
			frame = localcommService.formatXmltoFrame(localcommService.loadInit(request.getSession()
					.getServletContext().getRealPath(getUrl())));
			element=new net.sf.ehcache.Element("frame:"+getUrl(),frame);
			cache.put(element);
		}
		return frame;
	}
	
}
