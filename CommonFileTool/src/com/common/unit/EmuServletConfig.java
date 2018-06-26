package com.common.unit;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class EmuServletConfig implements ServletConfig{

	private ServletContext context;
	
	
	@Override
	public String getServletName() {
		return null;
	}

	public void setServletContext(ServletContext value) {
		context=value;
	}
	
	@Override
	public ServletContext getServletContext() {
		return context;
	}

	@Override
	public String getInitParameter(String name) {
		return null;
	}

	@Override
	public Enumeration getInitParameterNames() {
		return null;
	}

}
