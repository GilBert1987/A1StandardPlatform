package com.common.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.common.tool.ApplicationContextHolder;

public class MainStart {
	
	public static void main(String[] args) throws IOException {  
        ClassPathXmlApplicationContext context;
        context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml",
        		"classpath:shiro.xml",
        		"classpath:spring-*.xml"});
        ApplicationContextHolder.getInstance().setApplicationContext(context);
        context.start();
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
        System.in.read();  
    }  
}
