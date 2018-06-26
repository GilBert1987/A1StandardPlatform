package com.common.viewResolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class QueryViewResolver extends PlatformResourceViewResolver{
	
	private String defaultEncoding;
	private String prefix;
	private String temppage;
	private String templatepage;
	private String suffix;
	private String jstag;
	private String titlename;
	private String head;
	private String buttonlist;
	private String toollist;
	private String tableHead;
	private String tableTotal;
	private String quickquery;
	private String query;
	private String cacheName;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getQuickquery() {
		return quickquery;
	}

	public void setQuickquery(String quickquery) {
		this.quickquery = quickquery;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getButtonlist() {
		return buttonlist;
	}

	public void setButtonlist(String buttonlist) {
		this.buttonlist = buttonlist;
	}

	public String getToollist() {
		return toollist;
	}

	public void setToollist(String toollist) {
		this.toollist = toollist;
	}

	public String getTableHead() {
		return tableHead;
	}

	public void setTableHead(String tableHead) {
		this.tableHead = tableHead;
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
	public String getTableTotal() {
		return tableTotal;
	}
	public void setTableTotal(String tableTotal) {
		this.tableTotal = tableTotal;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class requiredViewClass() {
		return QueryView.class;
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
		QueryView view;
		view=(QueryView)super.buildView(viewName);
		view.setDefaultEncoding(this.defaultEncoding);
		view.setTemppage(this.temppage);
		view.setTemplatepage(this.templatepage);
		view.setPrefix(this.prefix);
		view.setSuffix(this.suffix);
		view.setJstag(this.jstag);
		view.setHead(this.head);
		view.setButtonlist(this.buttonlist);
		view.setToollist(this.toollist);
		view.setTableHead(this.tableHead);
		view.setTableTotal(this.tableTotal);
		view.setTitlename(this.titlename);
		view.setQuery(this.query);
		view.setQuickquery(this.quickquery);
		view.setCacheName(this.cacheName);
		return view;
	}	
}
