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

public class OrgSelect extends TagSupport implements TagShow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928785330177388062L;
	
	private static Logger log = Logger.getLogger(OrgSelect.class);
	private String id;
	private String url;
	private String name;
	private String multiple;
	private Integer showtype;
	private CommonService comService;
	private String initorgsys;
	private String initorgsql;
	private String singlepic="../../img/single.png";
	private String muiltpic="../../img/muilt.png";
	private String picwidth="20px";
	private String picheight="20px";
	private String clientfun="fn_platform_selOrg(this);";
	
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

		

	public String getInitorgsys() {
		return initorgsys;
	}

	public void setInitorgsys(String initorgsys) {
		this.initorgsys = initorgsys;
	}

	public String getInitorgsql() {
		return initorgsql;
	}

	public void setInitorgsql(String initorgsql) {
		this.initorgsql = initorgsql;
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
				list=getOrgInfo();
			}
			catch(Exception e)
			{
				new JspException(e.getMessage());
			}
			if(null!=list){
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_orgname' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strOrgName=reMap.get("name");
						
						if(false==blInfo){
							strInput.append(strOrgName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgName);
						}
					}
					strInput.append("' />");
				}
				if(2 == showtype){
					strInput.append("<input id='"+id+"_orgname' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strOrgName=reMap.get("name");
						
						if(false==blInfo){
							strInput.append(strOrgName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgName);
						}
					}
					strInput.append("' />");
					if("true".equals(multiple)==true){
						strInput.append("<img src='"+muiltpic+"' url='"+url+"' orgid='"+id+"_orgid' orgname='"+id+"_orgname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img src='"+singlepic+"' url='"+url+"' orgid='"+id+"_orgid' orgname='"+id+"_orgname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					blInfo=false;
					strInput.append("<input id='"+id+"_orgid' name='"+name+"' type='hidden' value='");
					for (Map<String, String> reMap : list) {
						strOrgId=reMap.get("org_id");
						
						if(false==blInfo){
							strInput.append(strOrgId);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strOrgId );
						}
					}
					strInput.append("' />");
				}
			}
			else
			{
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_orgname' type='input' readonly='readonly' value='' />");
				}
				else
				{
					strInput.append("<input id='"+id+"_orgname' type='input' readonly='readonly' value='' />");
					if("true".equals(multiple)==true){
						strInput.append("<img src='"+muiltpic+"' url='"+url+"' ismultiple='"+multiple+"' orgid='"+id+"_orgid' orgname='"+id+"_orgname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img src='"+singlepic+"' url='"+url+"' ismultiple='"+multiple+"' orgid='"+id+"_orgid' orgname='"+id+"_orgname' style='width:"+picwidth+";height:"+picheight+"' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					strInput.append("<input id='"+id+"_orgid' name='"+name+"' type='hidden' value='' />");
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
	
	//杩斿洖id,name,2鍒椾俊鎭�
	private List<Map> getOrgInfo() throws ServletException{
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
		initorgsys ="shiro";
		initorgsql ="select post_id,sc_post.name as post_name,org_id,sc_organization.name as org_name from (sc_post_org join sc_post on sc_post_org.post_id=sc_post.id) join sc_organization on sc_organization.id= sc_post_org.org_id;";
		if(null!=initorgsys && null!=initorgsql){
			try
			{
				list=(List<Map>)comService.queryListSql(initorgsys, initorgsql,null);
				if (list != null) {
					for (Map<String, Object> reMap : list) {
						//鍙彇绗竴鍒椾俊鎭�
						for (Map.Entry<String, Object> entry : reMap.entrySet()) {
							strValue=("".equals(strValue)==true?entry.getValue().toString():strValue + "," + entry.getValue().toString());
							break;
						}
					}
					if("".equals(strValue)==false){
						strArrid=strValue.split(",");
						if(0!=strArrid.length)
						{
							strSqlInfo.append("SELECT id,name ");
							strSqlInfo.append("FROM sc_organization ");
							strSqlInfo.append("WHERE id IN ( ");
							for(String strOrgid:strArrid){
								if("".equals(strOrgid)==false){
									if(blInfo==false)
									{
										strSqlInfo.append("select '"+strOrgid+"' as id ");
										blInfo=true;
									}
									else
									{
										strSqlInfo.append("union ");
										strSqlInfo.append("select '"+strOrgid+"' as id ");
									}
								}
							}
							strSqlInfo.append(") ");
							listreturn=comService.queryListSql("shiro", strSqlInfo.toString(),null);
						}
					}
				}
			}
			catch(Exception e)
			{
				new ServletException("initorgsys error or initorgsql error");
			}
		}
		return listreturn;
	}

	@Override
	public void setDataBind(String dataBind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataBind() {
		// TODO Auto-generated method stub
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
