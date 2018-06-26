package com.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.entity.login.User;
import com.common.entity.query.Query;
import com.common.event.ButtonClickEventListenerImpl;
import com.common.event.ButtonManager;
import com.common.ftp.util.FTPUtil;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.service.WorkflowService;

@Controller
@RequestMapping("/platform")
public class PlatformController {
	
	private static final String form = ".form";
	private static final String query = ".query";
	private static final String menu = ".menu";
	private static final String tree = ".tree";
	private static final String tool = ".tool";
	private static final String frame= ".frame";
	private static final String treelist = "/tree/commontree";
	private static final String treedir = "../tree";
	private static final String treeempty = "/tree/emptypage";
	private static final String workflow= ".wf";
	
	private static Logger log = Logger.getLogger(PlatformController.class);
	
	@Resource
	private CoreService coreService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private WorkflowService workflowService;
	
	@Resource
	private LocalCommonService localcommService;
	
	@Resource
	private FTPUtil ftpUtil;
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/changeurl", method={RequestMethod.GET})
	public String Changeurl(@RequestParam("urlInfo") String urlInfo, HttpServletRequest request) {
		String urlChangeInfo;
		urlChangeInfo="";
		try {
			urlChangeInfo=URLDecoder.decode(urlInfo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.toString(),e);
		}
		return "redirect:"+urlChangeInfo;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/form/{viewpath:[\\s\\S]+}.form", method={RequestMethod.GET,RequestMethod.POST})
	public String FormView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		User user;
		String strUrl;
		Subject subject;
		ButtonManager manager;
		manager=new ButtonManager();
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		manager.addButtonListener(new ButtonClickEventListenerImpl());
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_manager", manager);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_authority", request.getParameter("authority")==null?"":request.getParameter("authority"));
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+form;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/query/{viewpath:[\\s\\S]+}.query", method={RequestMethod.GET,RequestMethod.POST})
	public String QueryView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) {
		User user;
		String strUrl;
		String strKey;
		Query queryObj;
		Subject subject;
		String strRetUrl;
		String strSqlInfo;
		List<Map> listSqlMap;
		Object[] queryConfigParams;
		Map<String, Object> queryConfig;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		strRetUrl=viewpath+query;
		if("1".equals(request.getParameter("_exportExcel"))==true)
		{
			strRetUrl=null;
			if(null != request.getParameter("_exportExcelKey") && "".equals(request.getParameter("_exportExcelKey"))==false){
				strKey=request.getParameter("_exportExcelKey").toString();
				try {
					strSqlInfo="select type,bytesinfo from sc_query where id=? and bytesinfo is not null";
					queryConfigParams=new Object[]{viewpath};
					queryConfig=commonService.queryFullSql("com", strSqlInfo,queryConfigParams);
					listSqlMap=(List<Map>)queryConfig.get("retMap");
					if(0==listSqlMap.size())
					{
						queryObj = getQueryByRequest(request,strRetUrl);
					}
					else
					{
						queryObj = getQueryBySqlRequest(request,listSqlMap.get(0));
					}
					response.addHeader("Content-Disposition","attachment;filename="+new String(queryObj.getName().getBytes("gb2312"),"ISO8859-1")+".xlsx");
					response.setContentType("octets/stream");
					ftpUtil.downloadFile(response.getOutputStream(), strKey+".xlsx", "/exportExcel");
				} catch (Exception e) {
					log.error(e.toString(),e);
				}
			}
		}
		return strRetUrl;
	}
	
	private Query getQueryBySqlRequest(HttpServletRequest requestToExpose,Map map) {
		Query query;
		byte[] bytes;
		Integer type;
		String strEncodeMap;
		bytes=(byte[])map.get("bytesinfo");
		type=(Integer)map.get("type");
		strEncodeMap=new String(bytes,Charset.forName("UTF-8"));
		if ((type != null) && (type.intValue() != 0))
	    {
			strEncodeMap = coreService.readEncryptSql(strEncodeMap);
	    }
		query = localcommService.formatXmltoQuery(coreService.decryptString(strEncodeMap));
		return query;
	}
	
	private Query getQueryByRequest(HttpServletRequest request,String retUrl)  throws Exception {
		Query query;
		query = localcommService.formatXmltoQuery(localcommService.loadInit(request.getSession().getServletContext().getRealPath(retUrl)));
		return query;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/menu/{viewpath:[\\s\\S]+}.menu", method={RequestMethod.GET,RequestMethod.POST})
	public String MenuView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		String strUrl;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+menu;
	}
	
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/tree/{viewpath:[\\s\\S]+}.tree", method={RequestMethod.GET,RequestMethod.POST})
	public String TreeView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		String strUrl;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+tree;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/tree/{viewpath:[\\s\\S]+}.treelist", method={RequestMethod.GET})
	public String TreeListView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		String strUrl;
		String strResult;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strResult=treelist;
		strUrl=treedir+"/"+viewpath+tree;
		strUrl+=(request.getQueryString()==null?"":"?"+request.getQueryString());
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return strResult;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/tree/empty", method={RequestMethod.GET})
	public String TreeEmptyView(HttpServletRequest request) {
		return treeempty;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/tool/{viewpath:[\\s\\S]+}.tool", method={RequestMethod.GET,RequestMethod.POST})
	public String ToolView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		String strUrl;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+tool;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/frame/{viewpath:[\\s\\S]+}.frame", method={RequestMethod.GET,RequestMethod.POST})
	public String FrameView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		String strUrl;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+frame;
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(value="/workflow/{viewpath:[\\s\\S]+}.wf", method={RequestMethod.GET,RequestMethod.POST})
	public String WorkflowView(@PathVariable("viewpath") String viewpath, Map<String, Object> map,HttpServletRequest request) {
		User user;
		String strUrl;
		Subject subject;
		ButtonManager manager;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		manager=new ButtonManager();
		manager.addButtonListener(new ButtonClickEventListenerImpl());
		strUrl=request.getSession().getServletContext().getContextPath()+request.getServletPath();
		strUrl+=request.getQueryString()==null?"":"?"+request.getQueryString();
		if(request.getParameter("taskId")==null)
		{
			request.setAttribute("_taskId", null);
		}
		else
		{
			request.setAttribute("_taskId", request.getParameter("taskId"));
		}
		request.setAttribute("_urlInfo", strUrl);
		request.setAttribute("_manager", manager);
		request.setAttribute("_viewpath", viewpath);
		request.setAttribute("_user", user);
		request.setAttribute("_userInfo", user);
		return viewpath+workflow;
	}
}
