package com.platform.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.TagShow;

public class OrgPostSelect extends TagSupport implements TagShow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928785330177388062L;
	
	private static Logger log = Logger.getLogger(OrgPostSelect.class);
	private String id;
	private String url;
	private String name;
	private String multiple;
	private Integer showtype;
	private CommonService comService;
	private String initorgpostsys;
	private String initorgpostsql;
	private String singlepic="../../img/single.png";
	private String muiltpic="../../img/muilt.png";
	private String picwidth="20px";
	private String picheight="20px";
	private String clientfun="fn_platform_selOrgPost(this);";
	
	public String getClientfun() {
		return clientfun;
	}

	public void setClientfun(String clientfun) {
		this.clientfun = clientfun;
	}

	public String getPicwidth() {
		return picwidth;
	}

	public void setPicwidth(String picwidth) {
		this.picwidth = picwidth;
	}

	public String getPicheight() {
		return picheight;
	}

	public void setPicheight(String picheight) {
		this.picheight = picheight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String getInitorgpostsys() {
		return initorgpostsys;
	}

	public void setInitorgpostsys(String initorgpostsys) {
		this.initorgpostsys = initorgpostsys;
	}

	public String getInitorgpostsql() {
		return initorgpostsql;
	}

	public void setInitorgpostsql(String initorgpostsql) {
		this.initorgpostsql = initorgpostsql;
	}

	public String getSinglepic() {
		return singlepic;
	}

	public void setSinglepic(String singlepic) {
		this.singlepic = singlepic;
	}

	public String getMuiltpic() {
		return muiltpic;
	}

	public void setMuiltpic(String muiltpic) {
		this.muiltpic = muiltpic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	@Override
	public Integer getShowtype() {
		return showtype;
	}

	@Override
	public void setShowtype(Integer value) {
		this.showtype=value;
	}
	
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		HttpServletRequest request;
		request=(HttpServletRequest)this.pageContext.getRequest();
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		return super.EVAL_BODY_INCLUDE;
	}
	
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		JspWriter out;
		String strOrgName;
		String strOrgId;
		String strPostName;
		String strPostId;
		StringBuilder strInput;
		List<Map> list;
		Boolean blInfo;
		out = pageContext.getOut();
		blInfo=false;
		list=null;
		strInput=new StringBuilder();
		url=(url==null?"":url);
		if(0 != showtype){
			try
			{
				list=getOrgPostInfo();
			}
			catch(Exception e)
			{
				new JspException(e.getMessage());
			}
			if(null!=list){
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_orgpostname' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strOrgName=reMap.get("org_name");
						strPostName = reMap.get("post_name");
						
						if(false==blInfo){
							strInput.append(strOrgName+"-"+strPostName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgName+"-"+strPostName);
						}
					}
					strInput.append("' />");
				}
				if(2 == showtype){
					strInput.append("<input id='"+id+"_orgpostname' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strOrgName=reMap.get("org_name");
						strPostName = reMap.get("post_name");
						
						if(false==blInfo){
							strInput.append(strOrgName+"-"+strPostName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgName+"-"+strPostName);
						}
					}
					strInput.append("' />");
					if("true".equals(multiple)==true){
						strInput.append("<img src='"+muiltpic+"' url='"+url+"' orgpostid='"+id+"_orgpostid' orgpostname='"+id+"_orgpostname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img src='"+singlepic+"' url='"+url+"' orgpostid='"+id+"_orgpostid' orgpostname='"+id+"_orgpostname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					blInfo=false;
					strInput.append("<input id='"+id+"_orgpostid' name='"+name+"' type='hidden' value='");
					for (Map<String, String> reMap : list) {
						strOrgId=reMap.get("org_id");
						strPostId = reMap.get("post_id");
						
						if(false==blInfo){
							strInput.append(strOrgId +"-"+strPostId);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgId +"-"+strPostId);
						}
					}
					strInput.append("' />");
				}
			}
			else
			{
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_orgpostname' type='input' readonly='readonly' value='' />");
				}
				else
				{
					strInput.append("<input id='"+id+"_orgpostname' type='input' readonly='readonly' value='' />");
					if("true".equals(multiple)==true){
						strInput.append("<img src='"+muiltpic+"' url='"+url+"' ismultiple='"+multiple+"' orgpostid='"+id+"_orgpostid' orgpostname='"+id+"_orgpostname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img src='"+singlepic+"' url='"+url+"' ismultiple='"+multiple+"' orgpostid='"+id+"_orgpostid' orgpostname='"+id+"_orgpostname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					strInput.append("<input id='"+id+"_orgpostid' name='"+name+"' type='hidden' value='' />");
				}
			}
			try {
				out.print(strInput);
			} catch (IOException e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return super.SKIP_BODY;
	}
	
	private List<Map> getOrgPostInfo() throws ServletException{
		List<Map> list;
		List<Map> listreturn;
		String strValue;
		String[] strArrid;
		StringBuilder strSqlInfo;
		Boolean blInfo;
		list=null;
		listreturn=null;
		strValue="";
		strSqlInfo=new StringBuilder();
		blInfo=false;
		initorgpostsys ="shiro";
		initorgpostsql ="select post_id,sc_post.name as post_name,org_id,sc_organization.name as org_name from (sc_post_org join sc_post on sc_post_org.post_id=sc_post.id) join sc_organization on sc_organization.id= sc_post_org.org_id;";
		if(null!=initorgpostsys && null!=initorgpostsql){
			try
			{
				list=(List<Map>)comService.queryListSql(initorgpostsys, initorgpostsql,null);
			}
			catch(Exception e)
			{
				new ServletException("initorgpostsys error or initorgpostsql error");
			}
		}
		return listreturn;
	}

	@Override
	public void setDataBind(String dataBind) {
		
	}

	@Override
	public String getDataBind() {
		return null;
	}

	@Override
	public void setInfoname(String infoname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInfoname() {
		// TODO Auto-generated method stub
		return null;
	}
}
