package com.common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;

public class RadioBase extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5801651341377502021L;
	private static Logger log = Logger.getLogger(RadioBase.class);
	private String id = null;
	private String dataname = null;
	private String datatype = null;
	private String backtype = null;
	private String datainit = null;
	private String datavalue = null;
	private String dataBind = null;
	private String required = null;
	private String infoname = null;
	private String strclass = null;
	private String clientCheckFun=null;
	private String clientChangeFun=null;
	private String align=null;
	private Integer size;
	private Integer modesize;
	private Integer showtype;
	private String style = null;
	private CommonService comService;
	private LocalCommonService localComService;
	
	/**
	 * @return the clientChangeFun
	 */
	public String getClientChangeFun() {
		return clientChangeFun;
	}

	/**
	 * @param clientChangeFun the clientChangeFun to set
	 */
	public void setClientChangeFun(String clientChangeFun) {
		this.clientChangeFun = clientChangeFun;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getInfoname() {
		return infoname;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}
	
	public String getStrclass() {
		return strclass;
	}

	public void setStrclass(String strclass) {
		this.strclass = strclass;
	}
	
	@Override
	public void setShowtype(Integer itagtype) {
		this.showtype=itagtype;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/* 标签初始方法 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		localComService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", (HttpServletRequest)this.pageContext.getRequest());
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", (HttpServletRequest)this.pageContext.getRequest());
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input="";
		JspWriter out = pageContext.getOut();
		required=(required==null?"":required);
		infoname=(infoname==null?"":infoname);
		try {
			input+=createRadio();
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	
	private String createRadio(){
		StringBuilder value;
		List<Map> list;
		Map map;
		Iterator iterObj;
		String strKey;
		String strValue;
		String strCode;
		String strInfo;
		strKey="";
		strValue="";
		strCode="";
		strInfo="";
		value=new StringBuilder();
		list=null;		
		if(null!=showtype && 0!=showtype)
		{
			switch(datatype)
			{
				
				case "sql":
					try {
						list=comService.queryListSql(dataname, datainit,null);
					} catch (Exception e) {
						log.error(e);
					}
					break;
				case "file":list=getFileList();
			}
			if(null!=list)
			{
				if(StringUtils.isEmpty(align)==false)
				{
					value.append("<table style=\"width:100%;\">");
				}
				if("horizontal".equals(align)==true)
				{
					value.append("<tr>");
					value.append("<td>");
				}
				for(int i=0;i<list.size();i++)
				{
					map=list.get(i);
					iterObj=map.keySet().iterator();
					while(iterObj.hasNext())
					{
						strKey=(String)iterObj.next();
						strValue=map.get(strKey).toString();
						if(strKey.toLowerCase().equals("code")==true)
						{
							strCode=strValue;
							continue;
						}
						if(strKey.toLowerCase().equals("value")==true)
						{
							strInfo=strValue;
							continue;
						}
					}
					if("vertical".equals(align)==true)
					{
						value.append("<tr>");
						value.append("<td>");
					}
					value.append("<label for='"+id+"_"+i+"' class='radio-inline'>");
					if(2==showtype)
					{
						if("".equals(strCode)==false && "".equals(strInfo)==false)
						{
											
							value.append("<input type='radio' coltype='" + (this.backtype==null?"":this.backtype) + "' ");
							value.append("id='"+id+"_"+i+"' ");
							value.append(" "+("true".equals(required)?"required":"")+" ");
							value.append(" showname=\""+infoname+"\" ");
							value.append(" value='" + strCode+"' ");
							if(dataBind!=null)
							{
								value.append(" name='"+dataBind+"' ");
							}
							else
							{
								value.append(" name='"+id+"' ");
							}
							dataBindInfo(value,strCode,1);
							value.append(" />");
							value.append(strInfo);
						}
					}
					if(1==showtype)
					{
						if("".equals(strCode)==false && "".equals(strInfo)==false)
						{
												
							value.append("<input type='radio' coltype='" + (this.backtype==null?"":this.backtype) + "' ");
							value.append("id='"+id+"_"+i+"' ");
							value.append(" "+("true".equals(required)?"required":"")+" ");
							value.append(" showname=\""+infoname+"\" ");
							value.append(" value='" + strCode+"'");
							if(dataBind!=null)
							{
								value.append("name='"+dataBind+"'");
							}
							else
							{
								value.append("name='"+id+"'");
							}
							dataBindInfo(value,strCode,1);
							value.append(" value='" + strCode+"'");
							value.append(" disabled='disabled'");
							value.append(" />");
							value.append(strInfo);
						}
					}
					value.append("</label>");
					if("horizontal".equals(align)==true)
					{
						if(null!= modesize && 0 != modesize && i != 0 && i % modesize==0)
						{
							value.append("</tr>");
							value.append("</td>");
							value.append("<tr>");
							value.append("<td>");
						}
					}
					if("vertical".equals(align)==true)
					{
						value.append("</td>");
						value.append("</tr>");
					}
				}
				if("horizontal".equals(align)==true)
				{
					value.append("</td>");
					value.append("</tr>");
				}
				if(StringUtils.isEmpty(align)==false)
				{
					value.append("</table>");
				}
			}
		}
		return value.toString();
	}
	
	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	private List<Map> getFileList() {
		String strPathDir;
		List<Map> list;
		list=null;
		try
		{
			strPathDir=pageContext.getSession().getServletContext().getRealPath(dataname);
			list=localComService.getFileList(strPathDir, this.datainit);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		if(null==list)
		{
			list=new ArrayList<Map>();
		}
		return list;
	}
	
	private void dataBindInfo(StringBuilder strValue,String code, int type){
		String strKey;
		HttpServletRequest request;
		strKey="";
		request=(HttpServletRequest) this.pageContext.getRequest();
		if(null!=this.dataBind && "".equals(this.dataBind)==false)
		{
			strKey=localComService.dataBindInfo(request, this.dataBind);
		}
		switch(type)
		{
			case 0:
				setSelected(strValue,code,strKey);
				break;
			case 1:
				setChecked(strValue,code,strKey);
				break;
		}
	}
	
	private void setSelected(StringBuilder strValue,String code, String strKey)
	{
		if(code.equals(strKey)==true)
		{
			strValue.append(" selected='selected' ");
		}
		else
		{
			if(("".equals(strKey)==false && strKey.contains(code)==true))
			{
				strValue.append(" selected='selected' ");
			}
			else
			{
				if(null!=this.datavalue && this.datavalue.contains(code)==true && "".equals(strKey)==true)
				{
					strValue.append(" selected='selected' ");
				}
			}
		}
	}
	
	private void setChecked(StringBuilder strValue,String code,String strKey)
	{
		if(code.equals(strKey)==true)
		{
			strValue.append(" checked='checked' ");
		}
		else
		{
			if(("".equals(strKey)==false && strKey.contains(code)==true))
			{
				strValue.append(" checked='checked' ");
			}
			else
			{
				if(null!=this.datavalue && this.datavalue.contains(code)==true && "".equals(strKey)==true)
				{
					strValue.append(" checked='checked' ");
				}
			}
		}
	}
	
	/* 释放资源 */
	public void release() {
		super.release();
	}

	public String getDatainit() {
		return datainit;
	}

	public void setDatainit(String datainit) {
		this.datainit = datainit;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = (datavalue==null?"":datavalue);
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public String getBacktype() {
		return backtype;
	}

	public void setBacktype(String backtype) {
		this.backtype = backtype.toUpperCase();
	}

	public String getClientCheckFun() {
		return clientCheckFun;
	}

	public void setClientCheckFun(String clientCheckFun) {
		this.clientCheckFun = clientCheckFun;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public Integer getModesize() {
		return modesize;
	}

	public void setModesize(Integer modesize) {
		this.modesize = modesize;
	}

}
