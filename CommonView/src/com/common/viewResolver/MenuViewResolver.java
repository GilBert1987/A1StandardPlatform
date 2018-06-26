package com.common.viewResolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class MenuViewResolver extends PlatformResourceViewResolver{
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String titlename;
	private String shortcut;
	private String menus;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	public String getShortcut() {
		return shortcut;
	}
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public String getDefaultEncoding() {
		return defaultEncoding;
	}
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getTemppage() {
		return temppage;
	}
	public void setTemppage(String temppage) {
		this.temppage = temppage;
	}
	public String getTemplatepage() {
		return templatepage;
	}
	public void setTemplatepage(String templatepage) {
		this.templatepage = templatepage;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class requiredViewClass() {
		return MenuView.class;
	}
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		View result;
		result=null;
		if(viewName.endsWith(getSuffix())==true)
		{
			viewName=viewName.substring(0, viewName.length()-getSuffix().length());
			result = super.loadView(viewName, locale);
		}
		return result;
	}
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		MenuView view;
		view=(MenuView)super.buildView(viewName);
		view.setDefaultEncoding(this.defaultEncoding);
		view.setTemppage(this.temppage);
		view.setTemplatepage(this.templatepage);
		view.setPrefix(this.prefix);
		view.setSuffix(this.suffix);
		view.setTitlename(this.titlename);
		view.setShortcut(this.shortcut);
		view.setMenus(this.menus);
		view.setCacheName(this.cacheName);
		return view;
	}	
}
