package com.common.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;

public class ContextLoaderListener implements ServletContextListener{

	private ContextLoader contextLoader;
	
	protected ContextLoader createContextLoader() {
		return new ContextLoader();
	}
	
	public ContextLoader getContextLoader() {
        return this.contextLoader;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.contextLoader = createContextLoader();
        this.contextLoader.initWebApplicationContext(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (this.contextLoader != null) {
            this.contextLoader.closeWebApplicationContext(event.getServletContext());
		}
	}
	
	

}
