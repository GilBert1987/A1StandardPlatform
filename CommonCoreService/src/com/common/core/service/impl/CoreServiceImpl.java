package com.common.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.ws.rs.Path;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.xerial.snappy.Snappy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.common.entity.firebutton.Button;
import com.common.entity.firebutton.RelObjInfo;
import com.common.entity.form.DynamicParm;
import com.common.entity.form.Form;
import com.common.entity.menu.Menu;
import com.common.entity.menu.ShortCut;
import com.common.entity.menu.SubMenu;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.tool.XmlUnit;
import com.common.unit.FileUtil;

@Path("CoreService")
public class CoreServiceImpl implements CoreService{

	private static Logger log = Logger.getLogger(CoreServiceImpl.class);
	
	@Autowired
	private CommonService commService;
	
	@Override
	public String BufferedFileReader(String path, String strFormat)throws IOException {
		return FileUtil.readFileToString(path, strFormat);
	}

	@Override
	public String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public String getShowTypeBySql(String sysid, String strSql,Object[] params) throws Exception {
		Map<String, Object> mapInfo;
		Map<String,Object> hasRow;
		Set<String> keySet;
		String strKey;
		String strValue;
		String strReturn;
		Iterator iterKey;
		List<Map<String,Object>> mapList;
		strReturn="false";
		mapList=null;
		mapInfo=commService.queryFullSql(sysid, strSql,params);
		if(null!=mapInfo && 0!=mapInfo.size())
		{
			mapList=(List<Map<String,Object>>)mapInfo.get("retMap");
		}
		if(mapList!=null && mapList.size()>1){
			strReturn="true";
		}
		else
		{
			if(mapList.size()==1){
				hasRow=(Map<String,Object>)mapList.get(0);
				keySet=hasRow.keySet();
				iterKey=keySet.iterator();
				while(iterKey.hasNext())
				{
					strKey=iterKey.next().toString();
					strValue=hasRow.get(strKey).toString();
					if("0".equals(strValue)==false){
						strReturn="true";
						break;
					}
				}
			}
		}
		return strReturn;
	}
	
	@Override
	public String getStringTypeBySql(String sysid, String strSql,Object[] params) throws Exception {
		List<Map> mapInfo;
		Map<String,String> hasRow;
		Set<String> keySet;
		String strKey;
		String strValue;
		String strReturn;
		Iterator iterKey;
		strReturn="";
		mapInfo=commService.queryListSql(sysid, strSql,params);
		if(null!=mapInfo && mapInfo.size()>0){
			for(int i=0;i<mapInfo.size();i++)
			{
				hasRow=(Map<String,String>)mapInfo.get(i);
				keySet=hasRow.keySet();
				iterKey=keySet.iterator();
				while(iterKey.hasNext())
				{
					strKey=iterKey.next().toString();
					strValue=hasRow.get(strKey).toString();
					strReturn=strReturn+("".equals(strReturn)==true?strValue:","+strValue);
				}
			}
		}
		return strReturn;
	}

	@Override
	public void saveMenu(Menu menu) throws Exception {
		String strSql;
		String code;
		String id;
		String value;
		String subId;
		ShortCut shortCut;
		SubMenu rootSubMenu;
		List<ShortCut> listCuts;
		List<SubMenu> listMenus;
		List<Map> mapInfo;
		Object[] selParams;
		Object[] insertMenuParams;
		Object[] updateMenuParams;
		Object[] delMenuParams;
		Object[] shortcutParams;
		Object[] subMenuParams;
		code=menu.getCode();
		listCuts=menu.getListShortCuts();
		listMenus=menu.getListSubMenus();
		rootSubMenu=null;
		strSql="select id from sc_menu where code=?";
		selParams=new Object[]{
			code
		};
		mapInfo=commService.queryListSql("shiro", strSql,selParams);
		if(mapInfo.size()==0){
			id=commService.getKeybyTabCol("shiro", "sc_menu", "id");
			insertMenuParams=new Object[]{
				id,
				code,
				menu.getTitle(),
				menu.getFiledir(),
				menu.getTitlepic(),
				menu.getLastmodifiedtime(),
				menu.getRemark(),
				menu.getIroot(),
				menu.getRooturl(),
				menu.getLevel()
			};
			commService.insertObj("shiro", "sc_menu", "id,code,title,filedir,titlepic,lastmodifiedtime,remark,hasroot,rooturl,level", insertMenuParams);
		}
		else
		{
			updateMenuParams=new Object[]{
					menu.getTitle(),
					menu.getFiledir(),
					menu.getTitlepic(),
					menu.getLastmodifiedtime(),
					menu.getRemark(),
					menu.getIroot(),
					menu.getRooturl(),
					menu.getLevel()
			};
			value="title,filedir,titlepic,lastmodifiedtime,remark,hasroot,rooturl,level";
			id=mapInfo.get(0).get("id").toString();
			commService.updateObj("shiro", "sc_menu",value,updateMenuParams,"and id='"+id+"'");
		}
		delMenuParams=new Object[]{
			id
		};
		strSql="delete from sc_menu_shortcut where menu_id=?";
		commService.execNoListSql("shiro", strSql,delMenuParams);
		strSql="delete from sc_menu_submenu where menu_id=?";
		commService.execNoListSql("shiro", strSql,delMenuParams);
		for(int i=0;i<listCuts.size();i++)
		{
			subId=commService.getKeybyTabCol("shiro", "sc_menu_shortcut", "id");
			shortCut=listCuts.get(i);
			shortcutParams=new Object[]{
				subId,
				id,
				shortCut.getCode(),
				shortCut.getName(),
				shortCut.getPic(),
				shortCut.getUrl(),
				shortCut.getOrder()
			};
			commService.insertObj("shiro", "sc_menu_shortcut", "id,menu_id,CODE,NAME,pic,url,showindex",shortcutParams);
		}
		for(int i=0;i<listMenus.size();i++)
		{
			if("-1".equals(listMenus.get(i).getParentcode())==true){
				rootSubMenu=listMenus.get(i);
				subId=commService.getKeybyTabCol("shiro", "sc_menu_submenu", "id");
				subMenuParams=new Object[]{
					subId,
					id,
					rootSubMenu.getCode(),
					rootSubMenu.getName(),
					rootSubMenu.getPic(),
					rootSubMenu.getUrl(),
					rootSubMenu.getIsshow(),
					rootSubMenu.getOpentype(),
					rootSubMenu.getParentcode(),
					rootSubMenu.getOrder(),
					1
				};
				commService.insertObj("shiro", "sc_menu_submenu", "id,menu_id,CODE,NAME,pic,url,isshow,opentype,parentcode,showindex,LEVEL", subMenuParams);
				saveSubMenu(id,rootSubMenu,listMenus,2);
			}
		}
	}

	@Override
	public Menu getMenu(String code) throws Exception {
		Menu menu;
		String strMenuId;
		String strSql;
		List<Map> mapInfo;
		List<Map> mapSubCut;
		List<Map> mapSubMenu;
		ShortCut shortCut;
		SubMenu subMenu;
		Object[] codeParams;
		Object[] menuParams;
		Object[] subMenuParams;
		List<ShortCut> listCuts;
		List<SubMenu> listMenus;
		menu=null;
		listCuts=new ArrayList<ShortCut>();
		listMenus=new ArrayList<SubMenu>();
		codeParams=new Object[]{
			code
		};
		strSql="select id,title,level,filedir,titlepic,lastmodifiedtime,remark,hasroot,rooturl from sc_menu where code=?";
		mapInfo=commService.queryListSql("shiro", strSql,codeParams);
		if(mapInfo.size()!=0)
		{
			menu=new Menu();
			strMenuId=mapInfo.get(0).get("id").toString();
			menu.setCode(code);
			menu.setTitle(mapInfo.get(0).get("title").toString());
			menu.setTitlepic(mapInfo.get(0).get("titlepic").toString());
			menu.setFiledir(mapInfo.get(0).get("filedir").toString());
			menu.setIroot((null==mapInfo.get(0).get("hasroot")?Integer.parseInt("0"):Integer.parseInt(mapInfo.get(0).get("hasroot").toString())));
			menu.setLastmodifiedtime(mapInfo.get(0).get("lastmodifiedtime").toString());
			menu.setRooturl((null==mapInfo.get(0).get("rooturl"))?"":mapInfo.get(0).get("rooturl").toString());
			menu.setRemark((null==mapInfo.get(0).get("remark"))?"":mapInfo.get(0).get("remark").toString());
			menu.setListShortCuts(listCuts);
			menu.setListSubMenus(listMenus);
			menu.setLevel((null==mapInfo.get(0).get("level")?Integer.parseInt("0"):Integer.parseInt(mapInfo.get(0).get("level").toString())));
			menuParams=new Object[]{
				strMenuId
			};
			strSql="SELECT CODE,NAME,pic,url,showindex FROM sc_menu_shortcut WHERE menu_id=?";
			mapSubCut=commService.queryListSql("shiro", strSql,menuParams);
			for(int i=0;i<mapSubCut.size();i++)
			{
				shortCut=new ShortCut();
				shortCut.setCode(mapSubCut.get(i).get("CODE").toString());
				shortCut.setName(mapSubCut.get(i).get("NAME").toString());
				shortCut.setPic(mapSubCut.get(i).get("pic").toString());
				shortCut.setUrl(mapSubCut.get(i).get("url").toString());
				shortCut.setOrder(mapSubCut.get(i).get("showindex").toString());
				listCuts.add(shortCut);
			}
			subMenuParams=new Object[]{
				strMenuId
			};
			strSql="SELECT CODE,NAME,pic,url,isshow,opentype,parentcode,showindex FROM shirotable.sc_menu_submenu WHERE menu_id=?";
			mapSubMenu=commService.queryListSql("shiro", strSql,subMenuParams);
			for(int i=0;i<mapSubMenu.size();i++)
			{
				subMenu=new SubMenu();
				subMenu.setCode(mapSubMenu.get(i).get("CODE").toString());
				subMenu.setName(mapSubMenu.get(i).get("NAME").toString());
				subMenu.setPic(mapSubMenu.get(i).get("pic").toString());
				subMenu.setUrl(mapSubMenu.get(i).get("url").toString());
				subMenu.setIsshow(mapSubMenu.get(i).get("isshow").toString());
				subMenu.setOpentype(mapSubMenu.get(i).get("opentype").toString());
				subMenu.setParentcode(mapSubMenu.get(i).get("parentcode").toString());
				subMenu.setOrder(mapSubMenu.get(i).get("showindex").toString());
				listMenus.add(subMenu);
			}
		}
		return menu;
	}


	@Override
	public void delMenu(String code) throws Exception {
		String id;
		String strSql;
		Object[] selParams;
		Object[] delParams;
		List<Map> mapInfo;
		id="";
		selParams=new Object[]{
			code
		};
		strSql="select id from sc_menu where code=?";
		mapInfo=commService.queryListSql("shiro", strSql,selParams);
		if(0!=mapInfo.size())
		{
			id=mapInfo.get(0).get("id").toString();
			delParams=new Object[]{
				id	
			};
			strSql="delete from sc_menu_shortcut where menu_id=?";
			commService.execNoListSql("shiro", strSql,delParams);
			strSql="delete from sc_menu_submenu where menu_id=?";
			commService.execNoListSql("shiro", strSql,delParams);
			strSql="delete from sc_menu where id=?";
			commService.execNoListSql("shiro", strSql,delParams);
		}
	}

	@Override
	public Map<String, String> URLRequest(String URL)
	{
	    Map mapRequest;
	    String[] arrSplit;
	    String strUrlParam;
	    mapRequest = new HashMap();
	    arrSplit = null;
	    strUrlParam = TruncateUrlPage(URL);
	    if (strUrlParam == null) {
	    	return mapRequest;
	    }
	    arrSplit = strUrlParam.split("[&]");
	    for (String strSplit : arrSplit) {
	    	String[] arrSplitEqual = null;
	    	arrSplitEqual = strSplit.split("[=]");
	    	if (arrSplitEqual.length > 1)
		    {
		    	mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
		    }
	    	else if (arrSplitEqual[0] != "")
	    	{
	    		mapRequest.put(arrSplitEqual[0], "");
	    	}
	    }
	    return mapRequest;
	}
	

	@Override
	public Form readFormEncryptString(String strForm, String strButtons,String strAuthoritys,String strControls,String strTags,String strHiddenform,String strDyparmBytes,Integer type) {
		return rdFormEncryptString(strForm, strButtons,strAuthoritys,strControls,strTags,strHiddenform,strDyparmBytes,type);
	}
	
	private Form rdFormEncryptString(String strForm, String strButtons,String strAuthoritys,String strControls,String strTags,String strHiddenform,String strDyparmBytes,Integer type) {
		byte[] bytes;
		byte[] buttonBytes;
		byte[] authoritysBytes;
		byte[] controlsBytes;
		byte[] tagsBytes;
		byte[] hiddenformBytes;
		byte[] dyparmBytes;
		Form form;
		String strtag;
		String strtags;
		String strdyparm;
		String strparmkey;
		String strparmvalue;
		String strhiddenform;
		String strhform;
		String strbuttons;
		String strbutId;
		String strbutTag;
		String strclientfunction;
		String strclass;
		String strmethod;
		String strmethodkey;
		String strmethodvalue;
		String strcontrols;
		String strauthoritys;
		String strcontrolkey;
		String strcontrolvalue;
		String strauthkey;
		String strauthvalue;
		String strFormInfo;
		RelObjInfo relObj;
		List<String> tags;
		List<Button> buttons;
		List<String> hiddenform;
		List<DynamicParm> dyparm;
		List<RelObjInfo> fireFuction;
		List<String> jsFuction;
		List<Map<String, String>> controls;
		Map<String, Map<String, List<Map<String, String>>>> authoritys;
		Map<String, List<Map<String, String>>> authControls;
		List<Map<String, String>> listshow;
		Iterator iterkey;
		Iterator iterauthkey;
		JSONArray jsontags;
		JSONArray jsonlist;
		JSONArray jsonfirelist;
		JSONArray jsonfireparmlist;
		JSONArray jsoncontrols;
		JSONArray jsonauthoritys;
		JSONArray jsonshows;
		JSONObject jobj;
		JSONObject jsonForm;
		JSONObject jobjControl;
		JSONObject jobjClientFunction;
		JSONObject jobjFireparm;
		JSONObject jobjauthority;
		JSONObject jobjauthoritycontrol;
		JSONObject jobjshow;
		DynamicParm dparm;
		Button btnInfo;
		List<Map<String,String>> list;
		Map<String,String> map;
		Map<String, String> mapcontrol;
		Map<String,String> mapshow;
		ByteArrayInputStream is;
		HessianInput hi;
		is=null;
		form=new Form();
		tags=new ArrayList<String>();
		buttons=new ArrayList<Button>();
		hiddenform=new ArrayList<String>();
		dyparm=new ArrayList<DynamicParm>();
		controls=new ArrayList<Map<String,String>>();
		authoritys=new HashMap<String, Map<String, List<Map<String,String>>>>();
		form.setButtons(buttons);
		form.setAuthoritys(authoritys);
		form.setTags(tags);
		form.setControls(controls);
		form.setHiddenform(hiddenform);
		form.setDyparm(dyparm);
		try {
			bytes=Base64.decodeBase64(strForm);
			buttonBytes=Base64.decodeBase64(strButtons);
			authoritysBytes=Base64.decodeBase64(strAuthoritys);
			controlsBytes=Base64.decodeBase64(strControls);
			tagsBytes=Base64.decodeBase64(strTags);
			hiddenformBytes=Base64.decodeBase64(strHiddenform);
			dyparmBytes=Base64.decodeBase64(strDyparmBytes);
			if(null!=type && 0!=type)
			{
				bytes=this.uncompress(bytes);
				buttonBytes=this.uncompress(buttonBytes);
				authoritysBytes=this.uncompress(authoritysBytes);
				controlsBytes=this.uncompress(controlsBytes);
				tagsBytes=this.uncompress(tagsBytes);
				hiddenformBytes=this.uncompress(hiddenformBytes);
				dyparmBytes=this.uncompress(dyparmBytes);
			}
			is = new ByteArrayInputStream(bytes);
			hi = new HessianInput(is);
			strFormInfo=hi.readString();
			strbuttons=new String(buttonBytes,"UTF-8");
			strauthoritys=new String(authoritysBytes,"UTF-8");
			strcontrols=new String(controlsBytes,"UTF-8");
			strtags=new String(tagsBytes,"UTF-8");
			strhiddenform=new String(hiddenformBytes,"UTF-8");
			strdyparm=new String(dyparmBytes,"UTF-8");
			jsonForm=JSONObject.fromObject(strFormInfo);
			form.setCode(jsonForm.getString("code"));
			form.setSysid(jsonForm.getString("sysid"));
			form.setDataname(XmlUnit.getDeXmlString(jsonForm.getString("dataname")));
			form.setDatatype(jsonForm.getString("datatype"));
			form.setName(jsonForm.getString("name"));
			form.setTitle(jsonForm.getString("title"));
			form.setShowButton(jsonForm.getString("showButton"));
			form.setShowFormName(jsonForm.getString("showFormName"));
			form.setRemark(XmlUnit.getDeXmlString(jsonForm.getString("remark")));
			form.setDynamicform(XmlUnit.getDeXmlString(jsonForm.getString("dynamicform")));
			form.setJstag(XmlUnit.getDeXmlString(jsonForm.getString("jstag")));
			form.setFiledir(jsonForm.getString("filedir"));
			form.setLastmodifiedtime(jsonForm.getString("lastmodifiedtime"));
			if("".equals(strcontrols)==false)
			{
				jsoncontrols = JSONArray.fromObject(strcontrols.replaceAll("\r", "").replaceAll("\n", ""));
				for(int j=0;j<jsoncontrols.size();j++)
				{
					jobjControl=(JSONObject)jsoncontrols.get(j);
					iterkey=jobjControl.keys();
					mapcontrol=new HashMap<String,String>();
					while(iterkey.hasNext()==true)
					{
						strcontrolkey=iterkey.next().toString();
						strcontrolvalue=jobjControl.get(strcontrolkey).toString();
						if("".equals(strcontrolkey)==false){
							if(strcontrolvalue.equals("%5B%5D")==true){
								strcontrolvalue="[]";
							}
							mapcontrol.put(strcontrolkey, strcontrolvalue);
						}
					}
					controls.add(mapcontrol);
				}
			}
			if("".equals(strauthoritys)==false)
			{
				jsonauthoritys = JSONArray.fromObject(strauthoritys.replaceAll("\r", "").replaceAll("\n", ""));
				for(int j=0;j<jsonauthoritys.size();j++)
				{
					jobjauthority=(JSONObject)jsonauthoritys.get(j);
					iterkey=jobjauthority.keys();
					while(iterkey.hasNext()==true)
					{
						authControls=new HashMap<String, List<Map<String, String>>>();
						strauthkey=iterkey.next().toString();
						strauthvalue=jobjauthority.get(strauthkey).toString();
						jobjauthoritycontrol=JSONObject.fromObject(strauthvalue);
						iterauthkey=jobjauthoritycontrol.keys();
						while(iterauthkey.hasNext()==true)
						{
							listshow=new ArrayList<Map<String, String>>();
							strcontrolkey=iterauthkey.next().toString();
							strcontrolvalue=jobjauthoritycontrol.get(strcontrolkey).toString();
							if("".equals(strcontrolvalue)==false)
							{
								jsonshows=JSONArray.fromObject(strcontrolvalue);
								for(int u=0;u<jsonshows.size();u++)
								{
									mapshow=new HashMap<String,String>();
									jobjshow=(JSONObject)jsonshows.get(u);
									mapshow.put("showtype", jobjshow.get("showtype").toString());
									mapshow.put("authinfo", jobjshow.get("authinfo").toString());
									listshow.add(mapshow);
								}
								authControls.put(strcontrolkey, listshow);
							}
						}
						authoritys.put(strauthkey,authControls);
					}
				}
			}
			if("".equals(strtags)==false)
			{
				jsontags = JSONArray.fromObject(strtags.replaceAll("\r", "").replaceAll("\n", ""));
				for(int i=0;i<jsontags.size();i++)
				{
					strtag=jsontags.getString(i);
					if(strtag!=null)
					{
						tags.add(strtag);
					}
				}
			}
			if("".equals(strdyparm)==false)
			{
				jsontags = JSONArray.fromObject(strdyparm.replaceAll("\r", "").replaceAll("\n", ""));
				for(int i=0;i<jsontags.size();i++)
				{
					dparm=new DynamicParm();
					jobj = (JSONObject) jsontags.get(i);
					strparmkey=(String)jobj.get("key");
					strparmvalue=(String)jobj.get("value");
					if(strparmkey!=null)
					{
						dparm.setKey(strparmkey);
					}
					if(strparmvalue!=null)
					{
						dparm.setValue(strparmvalue);
					}
					dyparm.add(dparm);
				}
			}
			if("".equals(strhiddenform)==false)
			{
				jsontags = JSONArray.fromObject(strhiddenform.replaceAll("\r", "").replaceAll("\n", ""));
				for(int i=0;i<jsontags.size();i++)
				{
					strhform=jsontags.getString(i);
					if(strhform!=null)
					{
						hiddenform.add(strhform);
					}
				}
			}
			if("".equals(strbuttons)==false)
			{
				jsontags = JSONArray.fromObject(strbuttons.replaceAll("\r", "").replaceAll("\n", ""));
				for(int i=0;i<jsontags.size();i++)
				{
					btnInfo=new Button();
					fireFuction=new ArrayList<RelObjInfo>();
					jsFuction=new ArrayList<String>();
					jobj = (JSONObject) jsontags.get(i);
					strbutId=(String)jobj.get("id");
					strbutTag=(String)jobj.get("tag");
					if(strbutId!=null)
					{
						btnInfo.setId(strbutId);
					}
					if(strbutTag!=null)
					{
						btnInfo.setTag(strbutTag);
					}
					jsonlist=(JSONArray)jobj.get("jsFuction");
					for(int j=0;j<jsonlist.size();j++)
					{
						strclientfunction=jsonlist.getString(j);
						if(strclientfunction!=null)
						{
							jsFuction.add(strclientfunction);
						}
					}
					jsonfirelist=(JSONArray)jobj.get("fireFuction");
					for(int j=0;j<jsonfirelist.size();j++)
					{
						relObj=new RelObjInfo();
						list=new ArrayList<Map<String,String>>();
						jobjClientFunction=(JSONObject)jsonfirelist.get(j);
						strclass=(String)jobjClientFunction.get("className");
						strmethod=(String)jobjClientFunction.get("method");
						jsonfireparmlist=(JSONArray)jobjClientFunction.get("list");
						if(strclass!=null)
						{
							relObj.setClassName(strclass);
						}
						if(strmethod!=null)
						{
							relObj.setMethod(strmethod);
						}
						for(int k=0;k<jsonfireparmlist.size();k++)
						{
							jobjFireparm = (JSONObject)jsonfireparmlist.get(k);
							iterkey=jobjFireparm.keys();
							map=new HashMap<String, String>();
							while(iterkey.hasNext()==true)
							{
								strmethodkey=iterkey.next().toString();
								strmethodvalue=jobjFireparm.getString(strmethodkey);
								map.put(strmethodkey, strmethodvalue);
							}
							list.add(map);
						}
						relObj.setList(list);
						fireFuction.add(relObj);
					}
					btnInfo.setFireFuction(fireFuction);
					btnInfo.setJsFuction(jsFuction);
					buttons.add(btnInfo);
				}
			}
		} 
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		finally{
			IOUtils.closeQuietly(is);
		}
		return form;
	}
	
	@Override
	public void writeFormEncryptSql(Form form, String table,String userId) {
		wtFormEncryptSql(form,table,userId);
	}

	private void wtFormEncryptSql(Form form, String table,String userId) {
		byte[] bytes;
		byte[] buttonBytes;
		byte[] authoritysBytes;
		byte[] controlsBytes;
		byte[] tagsBytes;
		byte[] hiddenformBytes;
		byte[] dyparmBytes;
		JSONArray jsonButtons;
		JSONArray jsonAuthoritys;
		JSONArray jsonControls;
		JSONArray jsonTags;
		JSONArray jsonHiddenform;
		JSONArray jsonDyparm;
		HessianOutput output;
		StringBuilder strSql;
		StringBuilder strJsonInfo;
		ByteArrayOutputStream os;
		Object[] params;
		strSql=new StringBuilder();
		strJsonInfo=new StringBuilder();
		os = new ByteArrayOutputStream();
		try {
			jsonButtons=JSONArray.fromObject(form.getButtons());
			jsonAuthoritys=JSONArray.fromObject(form.getAuthoritys());
			jsonControls=JSONArray.fromObject(form.getControls());
			jsonTags=JSONArray.fromObject(form.getTags());
			jsonHiddenform=JSONArray.fromObject(form.getHiddenform());
			jsonDyparm=JSONArray.fromObject(form.getDyparm());
			strJsonInfo.append("{");
			strJsonInfo.append("\"sysid\":\""+form.getSysid()+"\",");
			strJsonInfo.append("\"dataname\":\""+XmlUnit.getEnXmlString(form.getDataname())+"\",");
			strJsonInfo.append("\"datatype\":\""+form.getDatatype()+"\",");
			strJsonInfo.append("\"code\":\""+form.getCode()+"\",");
			strJsonInfo.append("\"name\":\""+form.getName()+"\",");
			strJsonInfo.append("\"title\":\""+form.getTitle()+"\",");
			strJsonInfo.append("\"showButton\":\""+form.getShowButton()+"\",");
			strJsonInfo.append("\"showFormName\":\""+form.getShowFormName()+"\",");
			strJsonInfo.append("\"remark\":\""+XmlUnit.getEnXmlString(form.getRemark())+"\",");
			strJsonInfo.append("\"dynamicform\":\""+XmlUnit.getEnXmlString(form.getDynamicform())+"\",");
			strJsonInfo.append("\"jstag\":\""+XmlUnit.getEnXmlString(form.getJstag())+"\",");
			strJsonInfo.append("\"filedir\":\""+form.getFiledir()+"\",");
			strJsonInfo.append("\"lastmodifiedtime\":"+form.getLastmodifiedtime()+"");
			strJsonInfo.append("}");
			output=new HessianOutput(os);
			output.writeString(strJsonInfo.toString());
			bytes=os.toByteArray();
			buttonBytes=jsonButtons.toString().getBytes(Charset.forName("UTF-8"));
			authoritysBytes=jsonAuthoritys.toString().getBytes(Charset.forName("UTF-8"));
			controlsBytes=jsonControls.toString().getBytes(Charset.forName("UTF-8"));
			tagsBytes=jsonTags.toString().getBytes(Charset.forName("UTF-8"));
			hiddenformBytes=jsonHiddenform.toString().getBytes(Charset.forName("UTF-8"));
			dyparmBytes=jsonDyparm.toString().getBytes(Charset.forName("UTF-8"));
			bytes=this.compress(bytes);
			buttonBytes=this.compress(buttonBytes);
			authoritysBytes=this.compress(authoritysBytes);
			controlsBytes=this.compress(controlsBytes);
			tagsBytes=this.compress(tagsBytes);
			hiddenformBytes=this.compress(hiddenformBytes);
			dyparmBytes=this.compress(dyparmBytes);
			if("system".equals(userId)==false){
				strSql.append("update "+table+" ");
				strSql.append("set type=1,updateuser= ?, ");
				strSql.append("updatetime=NOW(), ");
				strSql.append("forminfo=?, ");
				strSql.append("buttons=?, ");
				strSql.append("authoritys=?, ");
				strSql.append("controls=?, ");
				strSql.append("tags=?, ");
				strSql.append("hiddenform=?, ");
				strSql.append("dyparm=? ");
				strSql.append("where id=?");
				params=new Object[]{
					userId,
					Base64.encodeBase64String(bytes),
					Base64.encodeBase64String(buttonBytes),
					Base64.encodeBase64String(authoritysBytes),
					Base64.encodeBase64String(controlsBytes),
					Base64.encodeBase64String(tagsBytes),
					Base64.encodeBase64String(hiddenformBytes),
					Base64.encodeBase64String(dyparmBytes),
					form.getCode()
				};
			}
			else
			{
				strSql.append("update "+table+" ");
				strSql.append("set type=1,systemuptime=NOW(), ");
				strSql.append("forminfo=?, ");
				strSql.append("buttons=?, ");
				strSql.append("authoritys=?, ");
				strSql.append("controls=?, ");
				strSql.append("tags=?, ");
				strSql.append("hiddenform=?, ");
				strSql.append("dyparm=? ");
				strSql.append("where id=?");
				params=new Object[]{
					Base64.encodeBase64String(bytes),
					Base64.encodeBase64String(buttonBytes),
					Base64.encodeBase64String(authoritysBytes),
					Base64.encodeBase64String(controlsBytes),
					Base64.encodeBase64String(tagsBytes),
					Base64.encodeBase64String(hiddenformBytes),
					Base64.encodeBase64String(dyparmBytes),
					form.getCode()
				};
			}
			commService.execNoListSql("com", strSql.toString(), params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		finally{
			IOUtils.closeQuietly(os);
		}
	}

	@Override
	public void writeEncryptSql(String code,String sb, String table,String userId) {
		wtEncryptSql(code,sb,table,userId);
	}
	
	private void wtEncryptSql(String code,String sb, String table,String userId) {
		byte[] bytes;
		String strSql;
		Object[] selParams;
		try {
			bytes=sb.getBytes(Charset.forName("UTF-8"));
			bytes=this.compress(bytes);
			if("system".equals(userId)==false){
				selParams=new Object[]{
					userId,
					org.apache.commons.codec.binary.Base64.encodeBase64String(bytes),
					code
				};
				strSql="update "+table+" ";
				strSql+="set updateuser= ?,updatetime=NOW(),bytesinfo=?,type=1 ";
				strSql+="where id=?";
			}
			else
			{
				selParams=new Object[]{
						org.apache.commons.codec.binary.Base64.encodeBase64String(bytes),
						code
				};
				strSql="update "+table+" ";
				strSql+="set bytesinfo=?,type=1,systemuptime=NOW() ";
				strSql+="where id=?";
			}
			commService.execNoListSql("com", strSql,selParams);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public Document decryptString(String strInfo){
		return XmlUnit.decryptString(strInfo);
	}
	
	private void saveSubMenu(String menuId,SubMenu rootMenu,List<SubMenu> listMenus,int level) throws Exception{
		String subId;
		Object[] params;
		SubMenu subMenu;
		for(int i=0;i<listMenus.size();i++)
		{
			subMenu=listMenus.get(i);
			if(subMenu.getParentcode().equals(rootMenu.getCode())==true)
			{
				subId=commService.getKeybyTabCol("shiro", "sc_menu_submenu", "id");
				params=new Object[]{
					subId,
					menuId,
					subMenu.getCode(),
					subMenu.getName(),
					subMenu.getPic(),
					subMenu.getUrl(),
					subMenu.getIsshow(),
					subMenu.getOpentype(),
					subMenu.getParentcode(),
					subMenu.getOrder(),
					level
				};
				commService.insertObj("shiro", "sc_menu_submenu", "id,menu_id,CODE,NAME,pic,url,isshow,opentype,parentcode,showindex,LEVEL", params);
				saveSubMenu(menuId,subMenu,listMenus,level+1);
			}
		}
	}
	
	private String TruncateUrlPage(String strURL) {
		String strAllParam;
		String[] arrSplit;
		strAllParam = null;
		arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	@Override
	public byte[] compress(byte[] byteInfo) throws Exception {
		return compressbyte(byteInfo);
	}
	
	private byte[] compressbyte(byte[] byteInfo) throws Exception {
		byte[] compressed;
		compressed =Snappy.compress(byteInfo);
		return compressed;
	}

	@Override
	public byte[] uncompress(byte[] byteInfo) throws Exception {
		return uncompressbyte(byteInfo);
	}
	
	private byte[] uncompressbyte(byte[] byteInfo) throws Exception {
		byte[] compressed;
		compressed =Snappy.uncompress(byteInfo);
		return compressed;
	}

	@Override
	public String readEncryptSql(String data) {
		return rdEncryptSql(data);
	}
	
	private String rdEncryptSql(String data) {
		byte[] bytes;
		String strResult;
		String strEncodeMap;
		HessianOutput output;
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();
		strResult="";
		strEncodeMap="";
		try {
			bytes=Base64.decodeBase64(data);
			bytes=this.uncompress(bytes);
			strEncodeMap=new String(bytes,"UTF-8");
			output=new HessianOutput(os);
			output.writeString(strEncodeMap);
			bytes=os.toByteArray();
			strResult=Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		finally{
			IOUtils.closeQuietly(os);
		}
		return strResult;
	}

	@Override
	public void writeEncryptWorkFlowSql(Map mapInfo) {
		writeEncryptWFSql(mapInfo);
	}

	private void writeEncryptWFSql(Map mapInfo) {
		Object[] maps;
		String strSql;
		String strKey;
		String strVer;
		String strHkey;
		String strHbtnlist;
		String strHconlist;
		String strHformlist;
		String strHtemp;
		String strHhidlist;
		String strHjsinfo;
		String strHtaginfo;
		String strHdynamicform;
		String strUserid;
		String strEndCode;
		String strRoleList;
		String strStartCode;
		String strTaskformlist;
		String strTaskuserlist;
		byte[] bytebtnlist;
		byte[] byteconlist;
		byte[] byteformlist;
		byte[] bytetemp;
		byte[] bytehidlist;
		byte[] bytejsinfo;
		byte[] bytetaginfo;
		byte[] bytedynamicform;
		byte[] byterolelist;
		byte[] bytetaskformlist;
		byte[] bytetaskuserlist;
		Object[] queryParams;
		Object[] insertParams;
		Object[] updateParams;
		List<Map> listQuery;
		SimpleDateFormat sdf;
		queryParams=new String[2];
		insertParams=new String[19];
		updateParams=new String[16];
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		maps=(Object[])mapInfo.get("map.hKey");
		strHkey=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hbtnlist");
		strHbtnlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hconlist");
		strHconlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hformlist");
		strHformlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hhidlist");
		strHhidlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.htemp");
		strHtemp=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hjsinfo");
		strHjsinfo=maps[0].toString();
		maps=(Object[])mapInfo.get("map.htaginfo");
		strHtaginfo=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hdynamicform");
		strHdynamicform=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hupdateUser");
		strUserid=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hroleList");
		strRoleList=maps[0].toString();
		maps=(Object[])mapInfo.get("map.htaskformlist");
		strTaskformlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.htaskuserlist");
		strTaskuserlist=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hstartcode");
		strStartCode=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hendcode");
		strEndCode=maps[0].toString();
		maps=(Object[])mapInfo.get("map.hver");
		strVer=maps[0].toString();
		try
		{
			strSql="select id from sc_workflow where wkkey=? and ver=?";
			queryParams[0]=strHkey;
			queryParams[1]=strVer;
			listQuery=commService.queryListSql("com", strSql, queryParams);
			bytebtnlist=strHbtnlist.getBytes(Charset.forName("UTF-8"));
			byteconlist=strHconlist.getBytes(Charset.forName("UTF-8"));
			byteformlist=strHformlist.getBytes(Charset.forName("UTF-8"));
			bytetemp=strHtemp.getBytes(Charset.forName("UTF-8"));
			bytehidlist=strHhidlist.getBytes(Charset.forName("UTF-8"));
			bytejsinfo=strHjsinfo.getBytes(Charset.forName("UTF-8"));
			bytetaginfo=strHtaginfo.getBytes(Charset.forName("UTF-8"));
			bytedynamicform=strHdynamicform.getBytes(Charset.forName("UTF-8"));
			byterolelist=strRoleList.getBytes(Charset.forName("UTF-8"));
			bytetaskformlist=strTaskformlist.getBytes(Charset.forName("UTF-8"));
			bytetaskuserlist=strTaskuserlist.getBytes(Charset.forName("UTF-8"));
			//--------------------------------------
			bytebtnlist=this.compress(bytebtnlist);
			byteconlist=this.compress(byteconlist);
			byteformlist=this.compress(byteformlist);
			bytehidlist=this.compress(bytehidlist);
			bytejsinfo=this.compress(bytejsinfo);
			bytetaginfo=this.compress(bytetaginfo);
			bytedynamicform=this.compress(bytedynamicform);
			byterolelist=this.compress(byterolelist);
			bytetaskformlist=this.compress(bytetaskformlist);
			bytetaskuserlist=this.compress(bytetaskuserlist);
			//--------------------------------------
			if(null!=listQuery && 0==listQuery.size())
			{
				strKey=commService.getKeybyTabCol("com", "sc_workflow", "id");
				insertParams[0] =strKey;
				insertParams[1] =strHkey;
				insertParams[2] =strVer;
				insertParams[3] =Base64.encodeBase64String(bytetaginfo);
				insertParams[4] =Base64.encodeBase64String(bytejsinfo);
				insertParams[5] =Base64.encodeBase64String(bytedynamicform);
				insertParams[6] =Base64.encodeBase64String(bytetemp);
				insertParams[7] =Base64.encodeBase64String(bytebtnlist);
				insertParams[8] =Base64.encodeBase64String(bytehidlist);
				insertParams[9] =Base64.encodeBase64String(byteconlist);
				insertParams[10]=Base64.encodeBase64String(byteformlist);
				insertParams[11]=Base64.encodeBase64String(byterolelist);
				insertParams[12]=Base64.encodeBase64String(bytetaskformlist);
				insertParams[13]=Base64.encodeBase64String(bytetaskuserlist);
				insertParams[14]=strStartCode;
				insertParams[15]=strEndCode;
				insertParams[16]=strUserid;
				insertParams[17]=sdf.format(new Date());
				insertParams[18]="1";
				commService.insertObj("com", "sc_workflow", "id,wkkey,ver,taginfo,jsinfo,dynamicform,temp,btnlist,hidlist,conlist,formlist,roleList,taskformlist,taskuserlist,startcode,endcode,updateuser,updatetime,type", insertParams);
			}
			else
			{
				updateParams[0] =Base64.encodeBase64String(bytetaginfo);
				updateParams[1] =Base64.encodeBase64String(bytejsinfo);
				updateParams[2] =Base64.encodeBase64String(bytedynamicform);
				updateParams[3] =Base64.encodeBase64String(bytetemp);
				updateParams[4] =Base64.encodeBase64String(bytebtnlist);
				updateParams[5] =Base64.encodeBase64String(bytehidlist);
				updateParams[6] =Base64.encodeBase64String(byteconlist);
				updateParams[7] =Base64.encodeBase64String(byteformlist);
				updateParams[8] =Base64.encodeBase64String(byterolelist);
				updateParams[9] =Base64.encodeBase64String(bytetaskformlist);
				updateParams[10]=Base64.encodeBase64String(bytetaskuserlist);
				updateParams[11]=strStartCode;
				updateParams[12]=strEndCode;
				updateParams[13]=strUserid;
				updateParams[14]=sdf.format(new Date());
				updateParams[15]="1";
				commService.updateObj("com", "sc_workflow", "taginfo,jsinfo,dynamicform,temp,btnlist,hidlist,conlist,formlist,roleList,taskformlist,taskuserlist,startcode,endcode,updateuser,updatetime,type", updateParams, "and wkkey='"+strHkey+"' and ver='"+strVer+"'");
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public Map readEncryptWorkFlowSql(String strKey, Integer ver) {
		return readEncryptWFSql(strKey, ver);
	}
	
	private Map readEncryptWFSql(String strKey, Integer ver){
		Map mapInfo;
		Integer type;
		Map retValue;
		String strSql;
		String strTemp;
		Object[] params;
		String strJsinfo;
		byte[] bTemp;
		byte[] bJsinfo;
		byte[] bConlist;
		byte[] bHidlist;
		byte[] bTaginfo;
		byte[] bBtnlist;
		byte[] bFormlist;
		byte[] bRoleList;
		List<Map> listMap;
		String strConlist;
		String strHidlist;
		String strTaginfo;
		String strBtnlist;
		String strNewtemp;
		String strFormlist;
		String strRoleList;
		byte[] bDynamicform;
		String strNewjsinfo;
		byte[] bTaskformlist;
		byte[] bTaskuserlist;
		String strNewBtnlist;
		String strNewhidlist;
		String strNewconlist;
		String strNewtaginfo;
		String strNewformlist;
		String strNewroleList;
		String strDynamicform;
		String strTaskformlist;
		String strTaskuserlist;
		String strNewdynamicform;
		String strNewtaskformlist;
		String strNewtaskuserlist;
		retValue=null;
		listMap=null;
		params=new Object[2];
		strNewBtnlist="";
		strNewhidlist="";
		strNewconlist="";
		strNewtaginfo="";
		strNewformlist="";
		strNewroleList="";
		strNewdynamicform="";
		strNewtaskformlist="";
		strNewtaskuserlist="";
		strNewjsinfo="";
		strNewtemp="";
		strSql="select taginfo,jsinfo,dynamicform,temp,btnlist,hidlist,conlist,formlist,roleList,taskformlist,taskuserlist,startcode,endcode,updatetime,type from sc_workflow where wkkey=? and ver=?";
		params[0]=strKey;
		params[1]=ver;
		try {
			listMap=commService.queryListSql("com", strSql.toString(), params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(1==listMap.size())
		{
			mapInfo=listMap.get(0);
			retValue=new HashMap<String,String>();
			bJsinfo=(byte[])mapInfo.get("jsinfo");
			type=(Integer)mapInfo.get("type");
			bDynamicform=(byte[])mapInfo.get("dynamicform");
			bTemp=(byte[])mapInfo.get("temp");
			bBtnlist=(byte[])mapInfo.get("btnlist");
			bHidlist=(byte[])mapInfo.get("hidlist");
			bConlist=(byte[])mapInfo.get("conlist");
			bFormlist=(byte[])mapInfo.get("formlist");
			bTaginfo=(byte[])mapInfo.get("taginfo");
			bRoleList=(byte[])mapInfo.get("roleList");
			bTaskformlist=(byte[])mapInfo.get("taskformlist");
			bTaskuserlist=(byte[])mapInfo.get("taskuserlist");
			strJsinfo=new String(bJsinfo,Charset.forName("UTF-8"));
			strDynamicform=new String(bDynamicform,Charset.forName("UTF-8"));
			strTemp=new String(bTemp,Charset.forName("UTF-8"));
			strBtnlist=new String(bBtnlist,Charset.forName("UTF-8"));
			strHidlist=new String(bHidlist,Charset.forName("UTF-8"));
			strConlist=new String(bConlist,Charset.forName("UTF-8"));
			strFormlist=new String(bFormlist,Charset.forName("UTF-8"));
			strTaginfo=new String(bTaginfo,Charset.forName("UTF-8"));
			strRoleList=new String(bRoleList,Charset.forName("UTF-8"));
			strTaskformlist=new String(bTaskformlist,Charset.forName("UTF-8"));
			strTaskuserlist=new String(bTaskuserlist,Charset.forName("UTF-8"));
			bTemp=Base64.decodeBase64(strTemp);
			bJsinfo=Base64.decodeBase64(strJsinfo);
			bBtnlist=Base64.decodeBase64(strBtnlist);
			bHidlist=Base64.decodeBase64(strHidlist);
			bConlist=Base64.decodeBase64(strConlist);
			bFormlist=Base64.decodeBase64(strFormlist);
			bTaginfo=Base64.decodeBase64(strTaginfo);
			bRoleList=Base64.decodeBase64(strRoleList);
			bDynamicform=Base64.decodeBase64(strDynamicform);
			bTaskformlist=Base64.decodeBase64(strTaskformlist);
			bTaskuserlist=Base64.decodeBase64(strTaskuserlist);
			if(null!=type && 0!=type)
			{
				try {
					bJsinfo=this.uncompress(bJsinfo);
					bDynamicform=this.uncompress(bDynamicform);
					bBtnlist=this.uncompress(bBtnlist);
					bHidlist=this.uncompress(bHidlist);
					bConlist=this.uncompress(bConlist);
					bFormlist=this.uncompress(bFormlist);
					bTaginfo=this.uncompress(bTaginfo);
					bRoleList=this.uncompress(bRoleList);
					bTaskformlist=this.uncompress(bTaskformlist);
					bTaskuserlist=this.uncompress(bTaskuserlist);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			try{
				strNewBtnlist=new String(bBtnlist,"UTF-8");
				strNewhidlist=new String(bHidlist,"UTF-8");
				strNewconlist=new String(bConlist,"UTF-8");
				strNewtaginfo=new String(bTaginfo,"UTF-8");
				strNewformlist=new String(bFormlist,"UTF-8");
				strNewroleList=new String(bRoleList,"UTF-8");
				strNewdynamicform=new String(bDynamicform,"UTF-8");
				strNewtaskformlist=new String(bTaskformlist,"UTF-8");
				strNewtaskuserlist=new String(bTaskuserlist,"UTF-8");
				strNewjsinfo=new String(bJsinfo,"UTF-8");
				strNewtemp=new String(bTemp,"UTF-8");
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
			}
			retValue.put("jsinfo", strNewjsinfo);
			retValue.put("temp", strNewtemp);
			retValue.put("btnlist", strNewBtnlist);
			retValue.put("hidlist", strNewhidlist);
			retValue.put("conlist", strNewconlist);
			retValue.put("formlist", strNewformlist);
			retValue.put("taginfo", strNewtaginfo);
			retValue.put("roleList", strNewroleList);
			retValue.put("dynamicform", strNewdynamicform);
			retValue.put("taskformlist", strNewtaskformlist);
			retValue.put("taskuserlist", strNewtaskuserlist);
			retValue.put("startcode", mapInfo.get("startcode").toString());
			retValue.put("endcode", mapInfo.get("endcode").toString());
			retValue.put("updatetime", mapInfo.get("updatetime").toString());
		}
		return retValue;
	}

}
