package com.platform.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.comparator.ComparatorMenuShortCut;
import com.common.comparator.ComparatorMenuSubMenu;
import com.common.entity.menu.Menu;
import com.common.entity.menu.ShortCut;
import com.common.entity.menu.SubMenu;
import com.common.factory.ReflectFactory;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.TagShow;

public class RoleAuthorization extends TagSupport implements TagShow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6474829219435255567L;

	private static Logger log = Logger.getLogger(RoleAuthorization.class);
	private String id=null;
	private String menuid=null;
	private Integer showtype;
	private String roleid=null;
	private CoreService coreService;
	private CommonService comService;
	
	@Override
	public Integer getShowtype() {
		return showtype;
	}

	@Override
	public void setShowtype(Integer value) {
		showtype=value;
	}
		
	public String getMenuid(){
		return menuid;
	}

	public void setMenuid(String value){
		menuid = value;
	}
	
	public String getId(){
		return id;
	}

	public void setId(String value){
		id = value;
	}
	
	public String getRoleid(){
		return roleid;
	}
	
	public void setRoleid(String value){
		roleid=value;
	}
	
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		HttpServletRequest request;
		request=(HttpServletRequest)this.pageContext.getRequest();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		return super.EVAL_BODY_INCLUDE;
	}
	
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		String input;
		JspWriter out;
		Menu menu;
		List permissionlist;
		List<ShortCut> listShortCuts;
		List<SubMenu> listSubMenus;
		Object[] params;
		ComparatorMenuShortCut comparatorShortCut;
		input="";
		permissionlist=null;
		comparatorShortCut=new ComparatorMenuShortCut();
		out = pageContext.getOut();
		if (null!=menuid)
		{
			try {
				menu=coreService.getMenu(menuid);
				listShortCuts=menu.getListShortCuts();
				listSubMenus=menu.getListSubMenus();
				Collections.sort(listShortCuts,comparatorShortCut);
				if(null!=roleid)
				{
					params=new Object[]{
						roleid
					};
					permissionlist=comService.queryListSql("shiro", "SELECT id,role_id,permission FROM sc_role_permission WHERE role_id=?",params);
				}
				buildTabBody(listShortCuts,listSubMenus,permissionlist,out);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return super.SKIP_BODY;
	}
	
	private void buildTabBody(List<ShortCut> listShortCuts,List<SubMenu> listSubMenus,List permissionlist,JspWriter out){
		StringBuilder strBuilder;
		strBuilder=new StringBuilder();
		strBuilder.append("<table id='tabcut' width='100%' >");
		strBuilder.append("<tr>");
		strBuilder.append("<td colspan='4' width='100%' align='left'>");
		strBuilder.append("平台权限配置");
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		strBuilder.append("<tr>");
		strBuilder.append("<td width='10%'><input id='ckallcut' type='checkbox' onclick='fn_ckallcut(this);'/>选择</td>");
		strBuilder.append("<td width='20%'>名称</td>");
		strBuilder.append("<td width='50%'>链接</td>");
		strBuilder.append("<td width='20%'>权限</td>");
		strBuilder.append("</tr>");
		strBuilder.append("<tr>");
		strBuilder.append("<td colspan='4' width='100%'>");
		strBuilder.append("<table id='datacut' width='100%'>");
		strAppendByCuts(strBuilder,listShortCuts,permissionlist);
		strBuilder.append("</table>");
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		strBuilder.append("</table>");
		strBuilder.append("<table id='tabmenu' width='100%' >");
		strBuilder.append("<tr>");
		strBuilder.append("<td colspan='5' width='100%' align='left'>");
		strBuilder.append("菜单权限配置");
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		strBuilder.append("<tr>");
		strBuilder.append("<td width='10%'><input id='ckallmenu' type='checkbox' onclick='fn_ckallmenu(this);'/>选择</td>");
		strBuilder.append("<td width='20%'>名称</td>");
		strBuilder.append("<td width='5%'>等级</td>");
		strBuilder.append("<td width='45%'>链接</td>");
		strBuilder.append("<td width='20%'>权限</td>");
		strBuilder.append("</tr>");
		strBuilder.append("<tr>");
		strBuilder.append("<td colspan='5' width='100%'>");
		strBuilder.append("<table id='datamenu' width='100%'>");
		strAppendByMenus(strBuilder,listSubMenus,permissionlist,"-1",1);
		strBuilder.append("</table>");
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		strBuilder.append("</table>");
		try {
			out.print(strBuilder.toString());
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	private void strAppendByCuts(StringBuilder strBuilder,List<ShortCut> listShortCuts,List permissionlist)
	{
		String name;
		String url;
		if(null!=strBuilder)
		{
			for(ShortCut shortcut:listShortCuts){
				name=shortcut.getName();
				url=shortcut.getUrl();
				strBuilder.append("<tr>");
				strBuilder.append("<td width='10%'><input name='ckcut' type='checkbox'/></td>");
				strBuilder.append("<td width='20%'>"+name+"</td>");
				strBuilder.append("<td width='50%'>"+url+"</td>");
				strBuilder.append("<td width='20%'>");
				strBuilder.append("<select id='"+shortcut.getCode()+"' name='"+shortcut.getCode()+"'>");
				strBuilder.append("<option value='0'>否</option>");
				strBuilder.append("<option value='1' ");
				setRoleSelect(strBuilder,shortcut.getCode(),permissionlist);
				strBuilder.append(">是</option>");
				strBuilder.append("</select>");
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
		}
	}
	
	private void strAppendByMenus(StringBuilder strBuilder,List<SubMenu> listSubMenus,List permissionlist, String parentcode, int ilevel)
	{
		String name;
		String url;
		List<SubMenu> list;
		ComparatorMenuSubMenu comparatorSubMenu;
		comparatorSubMenu=new ComparatorMenuSubMenu();
		list=new ArrayList<SubMenu>();
		if(null!=strBuilder)
		{
			list.addAll(listSubMenus);
			Collections.sort(listSubMenus,comparatorSubMenu);
			for(SubMenu submenu:listSubMenus){
				if(parentcode.equals(submenu.getParentcode())==true)
				{
					name=submenu.getName();
					url=submenu.getUrl();
					strBuilder.append("<tr>");
					strBuilder.append("<td width='10%'><input name='ckmenu' type='checkbox'/></td>");
					strBuilder.append("<td width='20%'>"+name+"</td>");
					strBuilder.append("<td width='5%'>"+ilevel+"</td>");
					strBuilder.append("<td width='45%'>"+url+"</td>");
					strBuilder.append("<td width='20%'>");
					strBuilder.append("<select id='"+submenu.getCode()+"' name='"+submenu.getCode()+"'>");
					strBuilder.append("<option value='0'>否</option>");
					strBuilder.append("<option value='1' ");
					setRoleSelect(strBuilder,submenu.getCode(),permissionlist);
					strBuilder.append(">是</option>");
					strBuilder.append("</select>");
					strBuilder.append("</td>");
					strBuilder.append("</tr>");
					list.remove(submenu);
					strAppendByMenus(strBuilder,list,permissionlist, submenu.getCode(),(ilevel+1));
				}
			}
		}
	}
	
	private void setRoleSelect(StringBuilder strBuilder,String code,List<Map> permissionlist){
		Boolean blInfo;
		String strValue;
		String strPermission;
		blInfo=false;
		for (Map<String,String> hMap : permissionlist)
		{
			strValue="";
			for (Map.Entry entry : hMap.entrySet()) {
				if ("permission".equals(entry.getKey())) {
					strValue = entry.getValue().toString();
				}
			}
			if("".equals(strValue)==false && strValue.equals(code)==true)
			{
				blInfo=true;
				break;
			}
	    }
		if(blInfo==true)
		{
			strBuilder.append("selected='selected'");
		}
	}
	
	public void saveRoleplatform(String strRoleId,String strMenuId,HttpServletRequest request){
		Menu menu;
		List<Map> list;
		String strKeyId;
		StringBuilder sqlBuilder;
		List<ShortCut> listShortCut;
		List<SubMenu> listSubMenu;
		List<String> delList;
		List<String> insertList;
		List<Object[]> listObj;
		Object[] selParams;
		Object[] insertParams;
		sqlBuilder=new StringBuilder();
		insertList=new ArrayList<String>();
		delList = new ArrayList<String>();
		listObj=new ArrayList<Object[]>();
		selParams=new Object[]{
			strRoleId
		};
		list=null;
		comService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		try {
			list=comService.queryListSql("shiro", "SELECT id,permission FROM sc_role_permission WHERE role_id=?",selParams);
		} catch (Exception e1) {
			log.error(e1);
		}
		if (null!=strMenuId)
		{
			try {
				menu=coreService.getMenu(strMenuId);
				listShortCut=menu.getListShortCuts();
				listSubMenu=menu.getListSubMenus();
				for(ShortCut shortCut:listShortCut)
				{
					if("0".equals(request.getParameter(shortCut.getCode()))==true && getPermissionByCode(list,shortCut.getCode())==true)
					{
						delList.add(getIdByPermission(list,shortCut.getCode()));
					}
					if("1".equals(request.getParameter(shortCut.getCode()))==true && getPermissionByCode(list,shortCut.getCode())==false)
					{
						strKeyId=comService.getKeybyTabCol("shiro", "sc_role_permission", "id");
						insertParams=new Object[]{
								strKeyId,
								strRoleId,
								shortCut.getCode()
						};
						listObj.add(insertParams);
						insertList.add("insert into sc_role_permission(id,role_id,permission)values(?,?,?)");
					}
				}
				for(SubMenu subMenu:listSubMenu)
				{
					if("0".equals(request.getParameter(subMenu.getCode()))==true && getPermissionByCode(list,subMenu.getCode())==true)
					{
						delList.add(getIdByPermission(list,subMenu.getCode()));
					}
					if("1".equals(request.getParameter(subMenu.getCode()))==true && getPermissionByCode(list,subMenu.getCode())==false)
					{
						strKeyId=comService.getKeybyTabCol("shiro", "sc_role_permission", "id");
						insertParams=new Object[]{
							strKeyId,
							strRoleId,
							subMenu.getCode()
						};
						listObj.add(insertParams);
						insertList.add("insert into sc_role_permission(id,role_id,permission)values(?,?,?)");
					}
				}
				comService.execNoListSqlByTran("shiro", insertList, listObj.toArray(new Object[listObj.size()][]));
				for(String strKey:delList){
					comService.deleteObj("shiro", "sc_role_permission", "id", strKey);
				}
				
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		
	}
	
	private Boolean getPermissionByCode(List<Map> list,String code){
		Boolean blInfo;
		String strValue;
		blInfo=false;
		for (Map<String,String> hMap : list)
		{
			strValue=hMap.get("permission");
			if(code.equals(strValue)==true)
			{
				blInfo=true;
				break;
			}
	    }
		return blInfo;
	}
	
	private String getIdByPermission(List<Map> list,String code){
		String strValue;
		String strId;
		strValue="";
		strId="";
		for (Map<String,String> hMap : list)
		{
			strValue=hMap.get("permission");
			strId=hMap.get("id");
			if(code.equals(strValue)==true)
			{
				break;
			}
	    }
		return strId;
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
