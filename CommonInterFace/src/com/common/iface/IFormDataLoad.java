package com.common.iface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFormDataLoad {
	void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) throws ServletException;
}
