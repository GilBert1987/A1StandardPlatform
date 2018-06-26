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

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.LocalCommonService;
import com.common.service.TagShow;

public class SelectBase extends TagSupport implements TagShow{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5801651341377502021L;
	private static Logger log = Logger.getLogger(SelectBase.class);
	private String id = null;
	private String dataname = null;
	private String datatype = null;
	private String backtype = null;
	private String datainit = null;
	private String datavalue = null;
	private String dataBind = null;
	private String required = null;
	private String infoname = null;
	private String multiple = null;
	private String strclass = null;
	private String clientCheckFun=null;
	private String clientChangeFun=null;
	private Integer size=0;
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
	
	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
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
			input+=createSelect();
			out.println(input);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	
	public String getDataBind() {
		return dataBind;
	}

	public void setDataBind(String dataBind) {
		this.dataBind = dataBind;
	}

	private String createSelect(){
		List<Map> list;
		Map map;
		Iterator iterObj;
		StringBuilder value;
		StringBuilder valueSelect;
		String strKey;
		String strValue;
		String strCode;
		String strInfo;
		strKey="";
		strValue="";
		strCode="";
		strInfo="";
		value=new StringBuilder();
		valueSelect=new StringBuilder();
		list=null;
		if(null!=showtype && 0!=showtype)
		{
			value.append("<select coltype='" + (this.backtype==null?"":this.backtype) + "' ");
			value.append("id='"+id+"' ");
			value.append(" class=\""+(strclass==null?"form-control":strclass)+"\" ");
			if("true".equals(multiple)==true)
			{
				size=(size==0?10:size);
				value.append(" multiple=\"multiple\" ");
				value.append(" size=\""+size+"\" ");
			}
			if(null!=style)
			{
				value.append(" style=\""+style+"\" ");
			}
			value.append(" "+("true".equals(required)?"required":"")+" ");
			value.append(" showname=\""+infoname+"\" ");
			if(clientChangeFun !=null){
				value.append(" onchange=\""+clientChangeFun+"\" ");
			}
			if(dataBind!=null)
			{
				value.append("name='"+dataBind+"'");
			}
			value.append(" >");
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
					if(2==showtype)
					{
						if("".equals(strCode)==false && "".equals(strInfo)==false)
						{
							value.append("<option value='"+strCode+"'");
							dataBindInfo(value,strCode,0);
							value.append(" >");
							value.append(strInfo);
							value.append("</option>");
						}
					}
					if(1==showtype)
					{
						if("".equals(strCode)==false && "".equals(strInfo)==false)
						{
							dataBindInfo(valueSelect,strCode,0);
							if("".equals(valueSelect)==false)
							{
								value.append("<option value='"+strCode+"'");
								value.append(valueSelect);
								value.append(" >");
								value.append(strInfo);
								value.append("</option>");
							}
							valueSelect.delete(0, valueSelect.length());
						}
					}
				}
			}
			value.append("</select>");
		}
		return value.toString();
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
