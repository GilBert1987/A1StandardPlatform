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

public class UserSelect extends TagSupport implements TagShow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928785330177388062L;
	
	private static Logger log = Logger.getLogger(UserSelect.class);
	private String id;
	private String url;
	private String name;
	private String multiple;
	private Integer showtype;
	private CommonService comService;
	private String initusersys;
	private String initusersql;
	private String singlepic="../../img/single.png";
	private String muiltpic="../../img/muilt.png";
	private String picwidth="20px";
	private String picheight="20px";
	private String clientfun="fn_platform_selUser(this);";
	
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

	public String getInitusersys() {
		return initusersys;
	}

	public void setInitusersys(String initusersys) {
		this.initusersys = initusersys;
	}

	public String getInitusersql() {
		return initusersql;
	}

	public void setInitusersql(String initusersql) {
		this.initusersql = initusersql;
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
		String strUserName;
		String strUserId;
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
				list=getUserInfo();
			}
			catch(Exception e)
			{
				new JspException(e.getMessage());
			}
			if(null!=list){
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_username' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strUserName=reMap.get("realname");
						if(false==blInfo){
							strInput.append(strUserName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strUserName);
						}
					}
					strInput.append("' />");
				}
				if(2 == showtype){
					strInput.append("<input id='"+id+"_username' type='input' readonly='readonly' value='");
					for (Map<String, String> reMap : list) {
						strUserName=reMap.get("realname");
						if(false==blInfo){
							strInput.append(strUserName);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strUserName);
						}
					}
					strInput.append("' />");
					if("true".equals(multiple)==true){
						strInput.append("<img id='"+id+"_img' src='"+muiltpic+"' url='"+url+"' userid='"+id+"_userid' username='"+id+"_username' style='width:"+picwidth+";height:"+picheight+";cursor:pointer;' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img id='"+id+"_img' src='"+singlepic+"' url='"+url+"' userid='"+id+"_userid' username='"+id+"_username' style='width:"+picwidth+";height:"+picheight+";cursor:pointer;' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					blInfo=false;
					strInput.append("<input id='"+id+"_userid' name='"+name+"' type='hidden' value='");
					for (Map<String, String> reMap : list) {
						strUserId=reMap.get("id");
						if(false==blInfo){
							strInput.append(strUserId);
							blInfo=true;
						}
						else
						{
							strInput.append(","+strUserId);
						}
					}
					strInput.append("' />");
				}
			}
			else
			{
				if(1 == showtype)
				{
					strInput.append("<input id='"+id+"_username' type='input' readonly='readonly' value='' />");
				}
				else
				{
					strInput.append("<input id='"+id+"_username' type='input' readonly='readonly' value='' />");
					if("true".equals(multiple)==true){
						strInput.append("<img id='"+id+"_img' src='"+muiltpic+"' url='"+url+"' ismultiple='"+multiple+"' userid='"+id+"_userid' username='"+id+"_username' style='width:"+picwidth+";height:"+picheight+";cursor:pointer;' onclick='"+clientfun+"'/>");
					}
					else{
						strInput.append("<img id='"+id+"_img' src='"+singlepic+"' url='"+url+"' ismultiple='"+multiple+"' userid='"+id+"_userid' username='"+id+"_username' style='width:"+picwidth+";height:"+picheight+";cursor:pointer;' onclick='"+clientfun+"'/>");
					}
					if(null==name){
						name=id;
					}
					strInput.append("<input id='"+id+"_userid' name='"+name+"' type='hidden' value='' />");
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
	
	//返回id,realname2列信息
	private List<Map> getUserInfo() throws ServletException{
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
		if(null!=initusersys && null!=initusersql && false=="".equals(initusersys) && false=="".equals(initusersql)){
			try
			{
				list=(List<Map>)comService.queryListSql(initusersys, initusersql,null);
				if (list != null) {
					for (Map<String, Object> reMap : list) {
						//只取第一列信息
						for (Map.Entry<String, Object> entry : reMap.entrySet()) {
							strValue=("".equals(strValue)==true?entry.getValue().toString():strValue + "," + entry.getValue().toString());
							break;
						}
					}
					if("".equals(strValue)==false){
						strArrid=strValue.split(",");
						if(0!=strArrid.length)
						{
							strSqlInfo.append("SELECT id,realname ");
							strSqlInfo.append("FROM sc_user ");
							strSqlInfo.append("WHERE id IN ( ");
							for(String strUserid:strArrid){
								if("".equals(strUserid)==false){
									if(blInfo==false)
									{
										strSqlInfo.append("select '"+strUserid+"' as id ");
										blInfo=true;
									}
									else
									{
										strSqlInfo.append("union ");
										strSqlInfo.append("select '"+strUserid+"' as id ");
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
				new ServletException("initusersys error or initusersql error");
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
