package com.common.viewResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.common.entity.activiti.ActivitForm;
import com.common.entity.activiti.FormKey;
import com.common.entity.activiti.WorkFlow;
import com.common.entity.firebutton.Button;
import com.common.entity.login.User;
import com.common.event.ButtonClickEvent;
import com.common.event.ButtonEventMessage;
import com.common.event.ButtonManager;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.LocalWorkflowService;
import com.common.service.WorkflowService;

public class WorkFlowView extends PlatformResourceView {

	private static Logger log = Logger.getLogger(WorkFlowView.class);
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String jstag;
	private String hiddentag;
	private String jsbutton;
	private String suffix;
	private String taglist;
	private String formtag;
	private String titlename;
	private String cacheName;

	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getFormtag() {
		return formtag;
	}
	public void setFormtag(String formtag) {
		this.formtag = formtag;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
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
		Map mapInfo;
		String strSql;
		Boolean blInfo;
		String strInid;
		Object[] params;
		String strUrlOut;
		String strTempJsp;
		String strUrlInfo;
		String strFormKey;
		String strTaskKey;
		List<Map> listMap;
		String strIsSubmit;
		List<Task> listTask;
		FormKey taskformkey;
		ProcessInstance instance;
		CommonService comService;
		WorkflowService workflowService;
		LocalCommonService localcommService;
		List<Button> btnlist;
		Map<String,Map<String,List<Map<String,String>>>> authoritys;
		WorkFlow wf;
		HttpServletRequest requestToExpose;
		String dispatcherPath;
		RequestDispatcher rd;
		Map<String, String> config;
		ButtonClickEvent event;
		ButtonEventMessage bemessage;
		ButtonManager manager;
		Subject subJect;
		User user;
		subJect=SecurityUtils.getSubject();
		user=(User)subJect.getPrincipal();
		// Determine which request handle to expose to the RequestDispatcher.
		requestToExpose = getRequestToExpose(request);
		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);
		// Determine the path for the request dispatcher.
		dispatcherPath = prepareForRendering(requestToExpose, response);
		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		strUrlInfo = requestToExpose.getAttribute("_urlInfo").toString();
		strInid="";
		strFormKey="";
		strUrlOut="";
		taskformkey=null;
		authoritys=null;
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
		if (jstag == null) {
			throw new ServletException("Check config jstag");
		}
		if (suffix == null) {
			throw new ServletException("Check config suffix");
		}
		if (strUrlInfo == null) {
			throw new ServletException("strUrlInfo parm error");
		}
		if (cacheName == null) {
			throw new ServletException("Check config cacheName");
		}
		strIsSubmit="0";
		config = new HashMap<String, String>();
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		config.put("templatepage", templatepage);
		config.put("temppage", temppage);
		config.put("prefix", prefix);
		config.put("suffix", suffix);
		config.put("jstag", jstag);
		config.put("taglist", taglist);
		config.put("hiddentag", hiddentag);
		config.put("formtag", formtag);
		config.put("titlename", titlename);
		config.put("jsbutton", jsbutton);
		config.put("defaultEncoding", defaultEncoding);
		wf = getWorkFlowByRequest(requestToExpose);
		if(null==wf)
		{
			throw new ServletException(getUrl()+" WorkFlow is null or not deployment!");
		}
		wf.setIsUpdate(false);
		request.setAttribute("_wf", wf);
		request.setAttribute("_wfdefkey", wf.getDefid());
		strTempJsp = getUrl().substring(prefix.length(), getUrl().length());
		strTempJsp = strTempJsp.substring(0,
				strTempJsp.length() - suffix.length())
				+ ".run";
		strTempJsp = temppage + ("".equals(wf.getFiledir())==true?"":(wf.getFiledir()+"/")) + strTempJsp;
		if ("get".equals(request.getMethod().toLowerCase()) == true) {
			comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
			workflowService=(WorkflowService)ReflectFactory.getObjInstance("com.common.service.WorkflowService", request);
			if(request.getParameter("inid")!=null)
			{
				blInfo=false;
				params=new Object[1];
				strInid=request.getParameter("inid").toString();
				params[0]=strInid;
				strSql="SELECT issubmit FROM act_re_url WHERE inid=?";
				listMap=comService.queryListSql("wf", strSql, params);
				instance=workflowService.getProcessInstanceByInid(strInid);
				request.setAttribute("_wfstatus", "1");
				if(null!=listMap && 1==listMap.size())
				{
					mapInfo=listMap.get(0);
					strIsSubmit=mapInfo.get("issubmit").toString();
				}
				if(instance!=null && "1".equals(strIsSubmit)==true)
				{
					listTask=workflowService.getCurrTask(instance);
					for(int i=0;i<listTask.size();i++)
					{
						strTaskKey=listTask.get(i).getId();
						strFormKey = workflowService.getFormKeyByTaskID(strTaskKey);
						blInfo=workflowService.getControlTaskInfo(strTaskKey,user.getId(),user.getUserRoles(),user.getUserOrgs(),user.getUserPosts());
						if(blInfo==true)
						{
							request.setAttribute("_wfcurrtkid", strTaskKey);
							request.setAttribute("_wfcurrform", strFormKey);
							break;
						}
					}
				}
				else
				{
					if(null!=instance && "0".equals(strIsSubmit)==true)
					{
						listTask=workflowService.getCurrTask(instance);
						if(null!=listTask && 0 < listTask.size())
						{
							strTaskKey=listTask.get(0).getId();
							request.setAttribute("_wfcurrtkid", strTaskKey);
						}
						request.setAttribute("_wfstatus", "0");
						strFormKey = wf.getInitformkey();
						blInfo=true;
					}
					if("1".equals(strIsSubmit)==true || "2".equals(strIsSubmit)==true)
					{
						request.setAttribute("_wfstatus", "9");
						strFormKey=wf.getInitformkey();
					}
				}
			}
			else{
				request.setAttribute("_wfstatus", "0");
				strFormKey = wf.getInitformkey();
				blInfo=true;
			}
			if("".equals(strFormKey)==false)
			{
				request.setAttribute("_wfformtkid", strFormKey);
				for(FormKey key:wf.getFormkeys()){
					if(key.getId().toLowerCase().equals(strFormKey.toLowerCase())==true){
						taskformkey=key;
						break;
					}
				}
				if(taskformkey!=null)
				{
					authoritys=taskformkey.getAuthoritys();
					for(ActivitForm form:wf.getForms())
					{
						//需要判断是否是当前节点
						if(blInfo==true && form.getCode().toLowerCase().equals(taskformkey.getStartcode().toLowerCase())==true)
						{
							request.setAttribute("_authority", "0");
							strUrlOut=form.getUrl();
							break;
						}
						if(blInfo==false && form.getCode().toLowerCase().equals(taskformkey.getEndcode().toLowerCase())==true)
						{
							request.setAttribute("_authority", "1");
							strUrlOut=form.getUrl();
							break;
						}
					}
					strUrlOut=localcommService.getUrlInfoByParam(strUrlOut, request);
					request.setAttribute("_urlform", strUrlOut);
				}
			}
		}
		if(authoritys==null)
		{
			authoritys=new HashMap<String,Map<String,List<Map<String,String>>>>();
		}
		if (workflowToClass(localcommService,wf,authoritys, config, request,response,strTempJsp) == true) {
			rd = getRequestDispatcher(requestToExpose, strTempJsp);
		}
		if ("post".equals(request.getMethod().toLowerCase()) == true) {
			manager = (ButtonManager) request.getAttribute("_manager");
			bemessage = new ButtonEventMessage();
			bemessage.setRequest(request);
			bemessage.setResponse(response);
			btnlist = wf.getButtons();
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
	
	private boolean workflowToClass(LocalCommonService localcommService,WorkFlow wf, Map<String, Map<String, List<Map<String, String>>>> authoritys,Map<String, String> config, HttpServletRequest request,HttpServletResponse response, String strTempJsp) {
		Boolean blInfo;
		String strJspPage;
		wf.setIsUpdate(false);
		strJspPage=localcommService.getJspStringByWF(wf,authoritys, config, request, response);
		request.setAttribute("_jspPage", strJspPage);
		request.setAttribute("_encode", this.defaultEncoding);
		request.setAttribute("_lastmodifiedtime", Long.valueOf(wf.getLastmodifiedtime()));
		blInfo = true;
		return blInfo;
	}

	private WorkFlow getWorkFlowByRequest(HttpServletRequest request) throws Exception {
		WorkFlow wf;
		String strId;
		String strInid;
		List<Map> list;
		Object[] params;
		StringBuilder strSql;
		String strIdInfo;
		Ehcache cache;
		CacheManager manager;
		CommonService comService;
		LocalWorkflowService localworkflowService;
		net.sf.ehcache.Element element;
		wf=null;
		strIdInfo="";
		strSql=new StringBuilder();
		strInid=(request.getParameter("inid")==null?"":request.getParameter("inid").toString());
		strId=(request.getAttribute("_viewpath")==null?"":request.getAttribute("_viewpath").toString());
		if("".equals(strInid)==false)
		{
			params=new Object[]{
				request.getParameter("inid").toString()
			};
			strSql.append("SELECT act_ge_bytearray.DEPLOYMENT_ID_ AS id ");
			strSql.append("FROM act_re_url ");
			strSql.append("JOIN act_ge_bytearray "); 
			strSql.append("ON act_re_url.deploymentId=act_ge_bytearray.DEPLOYMENT_ID_ ");
			strSql.append("WHERE act_ge_bytearray.NAME_ LIKE '%.json' AND act_re_url.inid=?");
		}
		else
		{
			params=new Object[]{
				strId
			};
			strSql.append("SELECT act_re_procdef.DEPLOYMENT_ID_ as id ");
			strSql.append("FROM act_re_model ");
			strSql.append("JOIN act_re_procdef ");
			strSql.append("ON act_re_model.DEPLOYMENT_ID_=act_re_procdef.DEPLOYMENT_ID_ ");
			strSql.append("JOIN act_ge_bytearray ");
			strSql.append("ON act_ge_bytearray.DEPLOYMENT_ID_=act_re_model.DEPLOYMENT_ID_ ");
			strSql.append("WHERE act_ge_bytearray.NAME_ LIKE '%.json' AND act_re_model.key_=? ");
		}
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		localworkflowService=(LocalWorkflowService)ReflectFactory.getObjInstance("com.common.service.LocalWorkflowService", request);
		list=comService.queryListSql("wf", strSql.toString(),params);
		if(list.size()==1)
		{
			strIdInfo=list.get(0).get("id").toString();
			if("".equals(strIdInfo)==false)
			{
				manager=CacheManager.getInstance();
				cache=manager.getEhcache(cacheName);
				element=(net.sf.ehcache.Element) cache.get("wf:"+strIdInfo);
				if(null != element)
				{
					wf=(WorkFlow)element.getObjectValue();
				}
				else
				{
					wf = localworkflowService.getWFByActivitiConfig(strIdInfo);
					element=new net.sf.ehcache.Element("wf:"+strIdInfo,wf);
					cache.put(element);
				}
			}
		}
		return wf;
	}	
}
