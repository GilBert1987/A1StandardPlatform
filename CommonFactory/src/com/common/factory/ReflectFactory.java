package com.common.factory;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ReflectFactory {
	
	private static Logger log = Logger.getLogger(ReflectFactory.class);
	
	public static Object getObjInstance(String classname){
		Object obj=null;
        try{
        	obj=Class.forName(classname).newInstance();
        }catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return obj;
	}
	
	public static Object getObjInstance(String classname,HttpServletRequest request){
		ServletContext servletContext;   
		ApplicationContext ctx;
		Object obj;
		servletContext = request.getSession().getServletContext();  
		ctx =WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		obj=null;
        try{
        	obj=ctx.getBean(classname);
        }catch (Exception e) {
        	
        }
        if(null==obj)
    	{
        	try{
        		obj=ctx.getBean(Class.forName(classname));
        	}catch (Exception e) {
            	
            }
    	}
        if(obj==null)
        {
	        try{
	        	obj=Class.forName(classname).newInstance();
	        }catch (Exception e) {
	        	log.error(e);
	        }
        }
        return obj;
	}
	
	public static Method getMethod(Object obj, String strMethod,Class[] argsClass) {
		Method method;
		method = null;
		try {
			method = obj.getClass().getMethod(strMethod, argsClass);
		} catch (Exception e) {
			log.error(e);
		}
		return method;
	}
	
	 public static Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		 Object temp = method.invoke(obj, args);
		 return temp;
	 }
}
