package com.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.entity.login.User;
import com.common.service.FilterChainDefinitionsService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static final String LOGIN_PAGE = "/login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";
	private static Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:"+LOGIN_PAGE;
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout() {
		return LOGIN_DIALOG;
	}
	
	@RequestMapping(value = "/timeout/success", method = { RequestMethod.POST })
	public @ResponseBody
	String timeoutSuccess(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		User shiroUser = (User)subject.getPrincipal();
		return "";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			Map<String, Object> map, HttpServletRequest request) {

		String msg = parseException(request);
		
		map.put("msg", msg);
		map.put("username", username);
		
		return LOGIN_PAGE;
	}

	private String parseException(HttpServletRequest request) {
		String error = (String) request
				.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		log.error(error);
		String msg = "其他错误！";
		if (error != null) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
				msg = "未知帐号错误！";
			else if ("org.apache.shiro.authc.IncorrectCredentialsException"
					.equals(error))
				msg = "密码错误！";
//			else if ("com.ygsoft.security.shiro.IncorrectCaptchaException"
			else if ("com.wiselong.security.shiro.IncorrectCaptchaException"
					.equals(error))
				msg = "验证码错误！";
			else if ("org.apache.shiro.authc.AuthenticationException"
					.equals(error))
				msg = "认证失败！";
			else if ("org.apache.shiro.authc.DisabledAccountException"
					.equals(error))
				msg = "账号被冻结！";
		}
		return "登录失败，" + msg;
	}
}
