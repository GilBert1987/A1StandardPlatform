package com.common.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/unauthorized")
public class UnauthorizedController {
	private static final String Unauthorized="/common/unauthorized";
	
	@RequiresAuthentication 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		request.setAttribute("_UnauthorizedError", "您没有权限操作此模块,请联系相关负责人!");
		return Unauthorized;
	}
}
