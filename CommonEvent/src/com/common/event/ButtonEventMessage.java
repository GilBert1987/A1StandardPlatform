package com.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.entity.firebutton.Button;

public class ButtonEventMessage {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Button btninfo;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Button getBtninfo() {
		return btninfo;
	}

	public void setBtninfo(Button btninfo) {
		this.btninfo = btninfo;
	}
}
