package com.common.viewResolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class WorkFlowViewResolver  extends PlatformResourceViewResolver {
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String jstag;
	private String jsbutton;
	private String suffix;
	private String taglist;
	private String hiddentag;
	private String formtag;
	private String titlename;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getFormtag() {
		return formtag;
	}
	public void setFormtag(String formtag) {
		this.formtag = formtag;
	}
	
	public String getJsbutton() {
		return jsbutton;
	}
	public void setJsbutton(String jsbutton) {
		this.jsbutton = jsbutton;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getHiddentag() {
		return hiddentag;
	}
	public void setHiddentag(String hiddentag) {
		this.hiddentag = hiddentag;
	}
	public String getTaglist() {
		return taglist;
	}
	public void setTaglist(String taglist) {
		this.taglist = taglist;
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
	public String getJstag() {
		return jstag;
	}
	public void setJstag(String jstag) {
		this.jstag = jstag;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class requiredViewClass() {
		return WorkFlowView.class;
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
		WorkFlowView view;
		view=(WorkFlowView)super.buildView(viewName);
		view.setDefaultEncoding(this.defaultEncoding);
		view.setJsbutton(this.jsbutton);
		view.setTemppage(this.temppage);
		view.setTemplatepage(this.templatepage);
		view.setJstag(this.jstag);
		view.setPrefix(this.prefix);
		view.setSuffix(this.suffix);
		view.setTaglist(this.taglist);
		view.setHiddentag(this.hiddentag);
		view.setTitlename(this.titlename);
		view.setFormtag(this.formtag);
		view.setCacheName(this.cacheName);
		return view;
	}
}
