package com.common.unit;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EmuServlet implements Servlet {

	private ServletConfig servletConfig;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	public void setServletConfig(ServletConfig value){
		servletConfig=value;
	}
	
	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {		
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
		
	}

	

}
