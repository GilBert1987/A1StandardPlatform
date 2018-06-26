package com.common.controller;

import javax.servlet.ServletException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
//@RequestMapping("/")
public class SafeguardControll {
	//@RequestMapping(value="{viewpath:[\\s\\S]+}.run", method={RequestMethod.GET,RequestMethod.POST})
	public void SafeguardView()throws ServletException{
		throw new ServletException("Can't run.Run");
	}
}
