package com.common.viewResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.common.entity.firebutton.Button;
import com.common.entity.form.DynamicParm;
import com.common.entity.form.Form;
import com.common.event.ButtonClickEvent;
import com.common.event.ButtonEventMessage;
import com.common.event.ButtonManager;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class FormView extends PlatformResourceView {

	private static Logger log = Logger.getLogger(FormView.class);
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String jstag;
	private String formtag;
	private String hiddentag;
	private String buttontag;
	private String jsbutton;
	private String suffix;
	private String taglist;
	private String formname;
	private String titlename;
	private String showbutton;
	private String showformname;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getShowformname() {
		return showformname;
	}
	public void setShowformname(String showformname) {
		this.showformname = showformname;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public String getShowbutton() {
		return showbutton;
	}
	public void setShowbutton(String showbutton) {
		this.showbutton = showbutton;
	}
	public String getHiddentag() {
		return hiddentag;
	}
	public void setHiddentag(String hiddentag) {
		this.hiddentag = hiddentag;
	}
	public String getTaglist() {
		return taglist;
	}
	public void setTaglist(String taglist) {
		this.taglist = taglist;
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

	public String getJstag() {
		return jstag;
	}

	public void setJstag(String jstag) {
		this.jstag = jstag;
	}

	public String getFormtag() {
		return formtag;
	}

	public void setFormtag(String formtag) {
		this.formtag = formtag;
	}

	public String getButtontag() {
		return buttontag;
	}

	public void setButtontag(String buttontag) {
		this.buttontag = buttontag;
	}

	public String getJsbutton() {
		return jsbutton;
	}

	public void setJsbutton(String jsbutton) {
		this.jsbutton = jsbutton;
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
		Date currDate;
		SimpleDateFormat sdf;
		List<Map> listMap;
		CoreService coreService;
		CommonService commService;
		LocalCommonService localcommService;
		Boolean blIsUpdate;
		Boolean blCheckPrkey;
		String strTempJsp;
		String strUrlInfo;
		String strViewPath;
		String strFormData;
		String strRelClass;
		StringBuilder strSql;
		List<Button> btnlist;
		List<Map> listSqlMap;
		Object[] params;
		List<DynamicParm> dyparm;
		DynamicParm dparm;
		Form form;
		JSONObject jsObj;
		IFormDataLoad iformDataLoad;
		HttpServletRequest requestToExpose;
		String dispatcherPath;
		RequestDispatcher rd;
		Map<String, String> config;
		ButtonClickEvent event;
		ButtonEventMessage bemessage;
		ButtonManager manager;
		StringBuilder strSqlInfo;
		currDate=new Date();
		strSqlInfo=new StringBuilder();
		config = new HashMap<String, String>();
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Determine which request handle to expose to the RequestDispatcher.
		requestToExpose = getRequestToExpose(request);
		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);
		// Determine the path for the request dispatcher.
		dispatcherPath = prepareForRendering(requestToExpose, response);
		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		strUrlInfo = requestToExpose.getAttribute("_urlInfo").toString();
		if (rd == null) {
			log.error("Could not get RequestDispatcher");
			throw new ServletException(
					"Could not get RequestDispatcher for ["
							+ getUrl()
							+ "]: Check that the corresponding file exists within your web application archive!");
		}
		if (templatepage == null) {
			log.error("Check config templatepage");
			throw new ServletException("Check config templatepage");
		}
		if (temppage == null) {
			log.error("Check config temppage");
			throw new ServletException("Check config temppage");
		}
		if (jstag == null) {
			log.error("Check config jstag");
			throw new ServletException("Check config jstag");
		}
		if (formtag == null) {
			log.error("Check config formtag");
			throw new ServletException("Check config formtag");
		}
		if (suffix == null) {
			log.error("Check config suffix");
			throw new ServletException("Check config suffix");
		}
		if (cacheName == null) {
			log.error("Check config cacheName");
			throw new ServletException("Check config cacheName");
		}
		if (strUrlInfo == null) {
			log.error("strUrlInfo parm error");
			throw new ServletException("strUrlInfo parm error");
		}
		strViewPath=request.getAttribute("_viewpath").toString();
		strSqlInfo.append("select ");
		strSqlInfo.append("forminfo,buttons,authoritys,controls,tags,hiddenform,dyparm,type ");
		strSqlInfo.append("from sc_form where id=? ");
		strSqlInfo.append("and forminfo is not null ");
		strSqlInfo.append("and buttons is not null ");
		strSqlInfo.append("and authoritys is not null ");
		strSqlInfo.append("and controls is not null ");
		strSqlInfo.append("and tags is not null ");
		strSqlInfo.append("and hiddenform is not null ");
		strSqlInfo.append("and dyparm is not null ");
		params=new Object[]{
			strViewPath	
		};
		listSqlMap=commService.queryListSql("com", strSqlInfo.toString(),params);
		if(0==listSqlMap.size())
		{
			if(localcommService.existsFile(request.getSession().getServletContext().getRealPath(getUrl()))==false)
			{
				throw new ServletException(getUrl()+" form file is null");
			}
		}
		config.put("templatepage", this.templatepage);
		config.put("temppage", this.temppage);
		config.put("prefix", this.prefix);
		config.put("suffix", this.suffix);
		config.put("jstag", this.jstag);
		config.put("formtag", this.formtag);
		config.put("taglist", this.taglist);
		config.put("hiddentag", this.hiddentag);
		config.put("defaultEncoding",this.defaultEncoding);
		config.put("jsbutton",this.jsbutton);
		config.put("buttontag",this.buttontag);
		config.put("formname",this.formname);
		config.put("titlename",this.titlename);
		config.put("showbutton",this.showbutton);
		config.put("showformname",this.showformname);
		if(0==listSqlMap.size())
		{
			form = getFormByRequest(requestToExpose,localcommService);
		}
		else
		{
			form = getFormBySqlRequest(requestToExpose,coreService,listSqlMap.get(0));
		}
		form.setIsUpdate(false);
		dyparm=form.getDyparm();
		blIsUpdate=true;
		blCheckPrkey=false;
		for(int i=0;i<dyparm.size();i++)
		{
			dparm=dyparm.get(i);
			if(request.getParameter(dparm.getKey())==null || ("".equals(request.getParameter(dparm.getKey()))==true)){
				blIsUpdate=false;
				break;
			}
		}
		if(blIsUpdate==false){
			for(int i=0;i<dyparm.size();i++)
			{
				dparm=dyparm.get(i);
				if(request.getParameter(dparm.getKey())!=null && ("".equals(request.getParameter(dparm.getKey()))==false)){
					blCheckPrkey=true;
					break;
				}
			}
			if(blCheckPrkey==true)
			{
				log.error("Check Url Config Primary key");
				throw new ServletException("Check Url Config Primary key");
			}
		}
		else{
			if("table".equals(form.getDatatype().toLowerCase())==true)
			{
				if("".equals(form.getDataname())==false){
					strSql=localcommService.execTableInfo(form.getSysid(),form,request);
					listMap=commService.queryListSql(form.getSysid(),strSql.toString(),null);
					if(listMap.size()!=0)
					{
						request.setAttribute("_dataInfo", listMap);
					}
					else
					{
						log.error("The Key has not data");
						throw new ServletException("The Key has not data");
					}
				}
			}
			if("relobj".equals(form.getDatatype().toLowerCase())==true){
				strFormData=form.getDataname();
				jsObj=JSONObject.fromObject(strFormData);
				if(jsObj==null)
				{
					log.error("relObj Error Check dataname");
					throw new ServletException("relObj Error Check dataname");
				}
				else
				{
					strRelClass=jsObj.getString("classinfo");
					if("".equals(strRelClass)==true)
					{
						log.error("relObj.class Error Check dataname classinfo");
						throw new ServletException("relObj.class Error Check dataname classinfo");
					}
					iformDataLoad=(IFormDataLoad)ReflectFactory.getObjInstance(strRelClass, request);
					iformDataLoad.setFormDataLoad(requestToExpose,response);
				}
			}
		}
		if("".equals(request.getAttribute("_authority"))==true)
		{
			if(blIsUpdate==true)
			{
				request.setAttribute("_authority",strViewPath+"_edit");
			}
			else
			{
				request.setAttribute("_authority",strViewPath+"_add");
			}
		}
		request.setAttribute("_form", form);
		request.setAttribute("_blIsUpdate", blIsUpdate);
		request.setAttribute("_currDate", sdf.format(currDate));
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage + ("".equals(form.getFiledir())==true?"":(form.getFiledir()+"/")) + strTempJsp;
		if (formToClass(strViewPath,coreService,localcommService,form, config, request,response,strTempJsp) == true) {
			rd = getRequestDispatcher(requestToExpose, strTempJsp);
		}
		if ("post".equals(request.getMethod().toLowerCase()) == true) {
			manager = (ButtonManager) request.getAttribute("_manager");
			bemessage = new ButtonEventMessage();
			bemessage.setRequest(request);
			bemessage.setResponse(response);
			btnlist = form.getButtons();
			for (int i = 0; i < btnlist.size(); i++) {
				if (request.getParameter("_idvalue").toString()
						.equals(btnlist.get(i).getId()) == true) {
					bemessage.setBtninfo(btnlist.get(i));
					break;
				}
			}
			event = new ButtonClickEvent(new Object(), bemessage);
			manager.fireButtonClick(event);
		}
		response.addHeader("X-XSS-Protection", "0");
		// If already included or response already committed, perform include,
		// else forward.
		if (useInclude(requestToExpose, response)) {
			response.setContentType(getContentType());
			rd.include(requestToExpose, response);
		}
		else {
			// Note: The forwarded resource is supposed to determine the content
			// type itself.
			rd.forward(requestToExpose, response);
		}
	}
	
	private Form getFormBySqlRequest(HttpServletRequest requestToExpose,CoreService coreService, Map mapList) {
		Form form;
		Integer type;
		byte[] bytes;
		byte[] buttonBytes;
		byte[] authoritysBytes;
		byte[] controlsBytes;
		byte[] tagsBytes;
		byte[] hiddenformBytes;
		byte[] dyparmBytes;
		String strFormInfo;
		String strButtonInfo;
		String strAuthorityInfo;
		String strControlInfo;
		String strTagInfo;
		String strHiddenInfo;
		String strDyparmInfo;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("form:"+getUrl());
		bytes=(byte[])mapList.get("forminfo");
		buttonBytes=(byte[])mapList.get("buttons");
		authoritysBytes=(byte[])mapList.get("authoritys");
		controlsBytes=(byte[])mapList.get("controls");
		tagsBytes=(byte[])mapList.get("tags");
		hiddenformBytes=(byte[])mapList.get("hiddenform");
		dyparmBytes=(byte[])mapList.get("dyparm");
		type=(Integer)mapList.get("type");
		strFormInfo=new String(bytes,Charset.forName("UTF-8"));
		strButtonInfo=new String(buttonBytes,Charset.forName("UTF-8"));
		strAuthorityInfo=new String(authoritysBytes,Charset.forName("UTF-8"));
		strControlInfo=new String(controlsBytes,Charset.forName("UTF-8"));
		strTagInfo=new String(tagsBytes,Charset.forName("UTF-8"));
		strHiddenInfo=new String(hiddenformBytes,Charset.forName("UTF-8"));
		strDyparmInfo=new String(dyparmBytes,Charset.forName("UTF-8"));
		if(null != element)
		{
			form=(Form)element.getObjectValue();
		}
		else
		{
			form = coreService.readFormEncryptString(strFormInfo,strButtonInfo,strAuthorityInfo,strControlInfo,strTagInfo,strHiddenInfo,strDyparmInfo,type);
			element=new net.sf.ehcache.Element("form:"+getUrl(),form);
			cache.put(element);
		}
		return form;
	}
	private boolean formToClass(String strCode, CoreService coreService, LocalCommonService localcommService,Form form,Map<String, String> config, HttpServletRequest request,HttpServletResponse response, String strTempJsp){
		Boolean blInfo;
		String strJspPage;
		form.setIsUpdate(false);
		strJspPage=localcommService.getJspStringByForm(form, config, request, response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(form.getLastmodifiedtime()));
		if(localcommService.checkClassLastmodifiedtime(strTempJsp,Long.valueOf(form.getLastmodifiedtime()),request)==true)
		{
			form.setIsUpdate(true);
			coreService.writeFormEncryptSql(form, "sc_form", "system");
			localcommService.writeEncryptFile(XmlUnit.formtoXml(form, this.defaultEncoding).asXML(), request.getSession().getServletContext().getRealPath(getUrl()), this.defaultEncoding);
		}
		blInfo = true;
		return blInfo;
	}

	private Form getFormByRequest(HttpServletRequest request,LocalCommonService localcommService) throws Exception {
		Form form;
		CacheManager manager;
		Ehcache cache;
		net.sf.ehcache.Element element;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache(cacheName);
		element=(net.sf.ehcache.Element) cache.get("form:"+getUrl());
		if(null != element)
		{
			form=(Form)element.getObjectValue();
		}
		else
		{
			form = localcommService.formatXmltoForm(localcommService.loadInit(request.getSession()
				.getServletContext().getRealPath(getUrl())));
			element=new net.sf.ehcache.Element("form:"+getUrl(),form);
			cache.put(element);
		}
		return form;
	}
}
