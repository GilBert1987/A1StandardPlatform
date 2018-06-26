package com.common.tool;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PageHandlerInterceptor implements HandlerInterceptor {
	
	private static Logger log = Logger.getLogger(PageHandlerInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception { 
		Calendar cal;
		StringBuffer stringBuffer;
		stringBuffer=new StringBuffer();
		cal = Calendar.getInstance();
		stringBuffer.append(request.getRequestURI());
		stringBuffer.append(" \ntime:"+cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+'-'+cal.get(cal.DATE)+" "+cal.get(cal.HOUR_OF_DAY)+":"+cal.get(cal.MINUTE)+":"+cal.get(cal.SECOND)+" "+cal.get(cal.MILLISECOND));
		log.debug(stringBuffer.toString());
        return true; 
    }
	
	@Override 
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Calendar cal;
		StringBuffer stringBuffer;
		stringBuffer=new StringBuffer();
		cal = Calendar.getInstance();
		stringBuffer.append(request.getRequestURI());
		stringBuffer.append(" \ntime:"+cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+'-'+cal.get(cal.DATE)+" "+cal.get(cal.HOUR_OF_DAY)+":"+cal.get(cal.MINUTE)+":"+cal.get(cal.SECOND)+" "+cal.get(cal.MILLISECOND));
		log.debug(stringBuffer.toString());
    }

}
