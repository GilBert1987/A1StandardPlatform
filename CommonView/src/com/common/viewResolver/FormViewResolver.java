package com.common.viewResolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class FormViewResolver extends PlatformResourceViewResolver{
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String jstag;
	private String formtag;
	private String buttontag;
	private String jsbutton;
	private String suffix;
	private String taglist;
	private String hiddentag;
	private String formname;
	private String titlename;
	private String showbutton;
	private String showformname;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getShowformname() {
		return showformname;
	}
	public void setShowformname(String showformname) {
		this.showformname = showformname;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public String getShowbutton() {
		return showbutton;
	}
	public void setShowbutton(String showButton) {
		this.showbutton = showButton;
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
	public String getFormtag() {
		return formtag;
	}
	public void setFormtag(String formtag) {
		this.formtag = formtag;
	}
	public String getButtontag() {
		return buttontag;
	}
	public void setButtontag(String buttontag) {
		this.buttontag = buttontag;
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class requiredViewClass() {
		return FormView.class;
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
		FormView view;
		view=(FormView)super.buildView(viewName);
		view.setDefaultEncoding(this.defaultEncoding);
		view.setJsbutton(this.jsbutton);
		view.setTemppage(this.temppage);
		view.setTemplatepage(this.templatepage);
		view.setJstag(this.jstag);
		view.setFormtag(this.formtag);
		view.setButtontag(this.buttontag);
		view.setPrefix(this.prefix);
		view.setSuffix(this.suffix);
		view.setTaglist(this.taglist);
		view.setHiddentag(this.hiddentag);
		view.setFormname(this.formname);
		view.setTitlename(this.titlename);
		view.setShowbutton(this.showbutton);
		view.setShowformname(this.showformname);
		view.setCacheName(this.cacheName);
		return view;
	}
	
	
}
