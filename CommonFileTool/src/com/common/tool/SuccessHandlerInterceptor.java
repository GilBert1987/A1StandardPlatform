package com.common.tool;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.common.entity.login.Organization;
import com.common.entity.login.User;

public class SuccessHandlerInterceptor implements HandlerInterceptor{
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		Subject subject;
		User user;
		Organization org;
		Boolean blInfo;
		Date dateInfo;
		List<Organization> listOrg; 
		String strUserPlatformid;
		String strOrgPlatformid;
    	subject = SecurityUtils.getSubject();
    	user = (User)subject.getPrincipal();
    	listOrg=user.getUserOrgs();
    	strUserPlatformid=user.getPlatformid();
    	org=null;
    	blInfo=false;
    	strOrgPlatformid="";
    	dateInfo=new Date();
    	if(listOrg !=null && listOrg.size()>0)
    	{
    		org=listOrg.get(0);
    		strOrgPlatformid=org.getPlatformid();
    	}
    	if("".equals(strUserPlatformid)==false && "-1".equals(strUserPlatformid)==false && null !=strUserPlatformid)
    	{
    		blInfo=true;
    		response.sendRedirect(request.getContextPath() + "/platform/menu/"+strUserPlatformid+".menu?_t="+dateInfo.getTime());
    	}
    	if(blInfo==false)
    	{
	    	if("".equals(strOrgPlatformid)==false && "-1".equals(strOrgPlatformid)==false && null !=strOrgPlatformid)
	    	{
	    		response.sendRedirect(request.getContextPath() + "/platform/menu/"+strOrgPlatformid+".menu?_t="+dateInfo.getTime());
	    	}
	    	else
	    	{
	    		response.sendRedirect(request.getContextPath() + "/platform/menu/root.menu?_t="+dateInfo.getTime());
	    	}
    	}
        return false; 
    }
	
	@Override 
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
    }
}
