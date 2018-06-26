package com.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class IndexController {
	private static final String INDEX="/success";
	private static final String LOGIN_PAGE = "/login";
	
	@RequiresAuthentication 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		Subject subject;
		String strurl;		
		subject = SecurityUtils.getSubject();
		if(null!=subject.getPrincipal())
		{
			strurl=INDEX;
		}
		else
		{
			strurl=LOGIN_PAGE;
		}
		return "redirect:"+strurl;
	}
}
