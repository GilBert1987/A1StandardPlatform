package com.common.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import com.common.entity.login.User;

public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {

	/**
	 * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		User user;
		user = (User)subject.getPrincipal();
		HttpServletRequest httpServletRequest;
		HttpServletResponse httpServletResponse;
		httpServletRequest = (HttpServletRequest)request;
		httpServletResponse = (HttpServletResponse)response;
		httpServletRequest.getSession().setAttribute("user", user);
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
				|| request.getParameter("ajax") == null) {// 不是ajax请求
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
		} else {	
			httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
		}
		return false;
	}
}
