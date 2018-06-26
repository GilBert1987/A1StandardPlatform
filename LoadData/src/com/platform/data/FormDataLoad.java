package com.platform.data;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.common.entity.firebutton.Button;
import com.common.entity.firebutton.RelObjInfo;
import com.common.entity.form.DynamicParm;
import com.common.entity.form.Form;
import com.common.entity.login.User;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class FormDataLoad implements IFormDataLoad {

	private static Logger log = Logger.getLogger(FormDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Form form;
		Integer type;
		byte[] bytes;
		byte[] buttonBytes;
		byte[] authoritysBytes;
		byte[] controlsBytes;
		byte[] tagsBytes;
		byte[] hiddenformBytes;
		byte[] dyparmBytes;
		String strId;
		String strControls;
		String strAuthoritys;
		String strTags;
		String strHiddens;
		String strButtons;
		String strDynamicform;
		String strControlValue;
		String strFormInfo;
		String strButtonInfo;
		String strAuthorityInfo;
		String strControlInfo;
		String strTagInfo;
		String strHiddenInfo;
		String strDyparmInfo;
		JSONObject jsonObj;
		JSONArray jdyparm;
		List<String> tags;
		List<Button> buttons;
		List<String> hiddenform;
		List<DynamicParm> dyparm;
		List<Map> listSqlMap;
		Map mapList;
		Object[] params;
		CoreService coreService;
		CommonService commService;
		Map<String, String> mapcontrol;
		LocalCommonService localcommService;
		Entry<String, Map<String, List<Map<String, String>>>> entryauthority;
		Entry<String, List<Map<String, String>>> entryauthcontrol;
		List<Map<String, String>> controls;
		Map<String, Map<String, List<Map<String, String>>>> authoritys;
		Map<String, List<Map<String, String>>> mapauthory;
		Iterator<Map<String, String>> itcontrol;
		Iterator<Entry<String, Map<String, List<Map<String, String>>>>> itauthority;
		Iterator<Entry<String, List<Map<String, String>>>> itauthcontrol;
		List<Map<String, String>> listShow;
		StringBuilder strSqlInfo;
		form=null;
		strControls="[]";
		strSqlInfo=new StringBuilder();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		if(request.getParameter("id")!=null){
			listSqlMap=null;
			strId=request.getParameter("id").toString();
			params=new Object[]{
				strId
			};
			strSqlInfo.append("select ");
			strSqlInfo.append("forminfo,buttons,authoritys,controls,tags,hiddenform,dyparm,type ");
			strSqlInfo.append("from sc_form where id=? ");
			strSqlInfo.append("and forminfo is not null ");
			strSqlInfo.append("and buttons is not null ");
			strSqlInfo.append("and authoritys is not null ");
			strSqlInfo.append("and controls is not null ");
			strSqlInfo.append("and tags is not null ");
			strSqlInfo.append("and hiddenform is not null ");
			strSqlInfo.append("and dyparm is not null ");
			try {
				listSqlMap=commService.queryListSql("com", strSqlInfo.toString(),params);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			if(0!=listSqlMap.size())
			{
				mapList=listSqlMap.get(0);
				type=(Integer)mapList.get("type");
				bytes=(byte[])mapList.get("forminfo");
				buttonBytes=(byte[])mapList.get("buttons");
				authoritysBytes=(byte[])mapList.get("authoritys");
				controlsBytes=(byte[])mapList.get("controls");
				tagsBytes=(byte[])mapList.get("tags");
				hiddenformBytes=(byte[])mapList.get("hiddenform");
				dyparmBytes=(byte[])mapList.get("dyparm");
				strFormInfo=new String(bytes,Charset.forName("UTF-8"));
				strButtonInfo=new String(buttonBytes,Charset.forName("UTF-8"));
				strAuthorityInfo=new String(authoritysBytes,Charset.forName("UTF-8"));
				strControlInfo=new String(controlsBytes,Charset.forName("UTF-8"));
				strTagInfo=new String(tagsBytes,Charset.forName("UTF-8"));
				strHiddenInfo=new String(hiddenformBytes,Charset.forName("UTF-8"));
				strDyparmInfo=new String(dyparmBytes,Charset.forName("UTF-8"));
				form = coreService.readFormEncryptString(strFormInfo,strButtonInfo,strAuthorityInfo,strControlInfo,strTagInfo,strHiddenInfo,strDyparmInfo,type);
			}
			else
			{
				if (!XmlUnit.existsFile(request.getSession().getServletContext().getRealPath("/WEB-INF/form/"+strId+".form")).booleanValue())
			    {
					log.error(strId+" form file is null");
			    }
				else
				{
					try {
						form = XmlUnit.formatXmltoForm(XmlUnit.loadInit(request.getSession().getServletContext().getRealPath("/WEB-INF/form/"+strId+".form")));
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
			if(form!=null)
			{
				authoritys=form.getAuthoritys();
				buttons=form.getButtons();
				controls=form.getControls();
				tags=form.getTags();
				hiddenform=form.getHiddenform();
				dyparm=form.getDyparm();
				form.setAuthoritys(null);
				form.setButtons(null);
				form.setTags(null);
				form.setHiddenform(null);
				form.setControls(null);
				form.setDyparm(null);
				strDynamicform=form.getDynamicform();
				form.setDynamicform(localcommService.changeHtmlInfo(strDynamicform,controls).replace("\t", "").replace("\r", "").replace("\n", "").replace("&nbsp;", ""));
				jsonObj=JSONObject.fromObject(form);
				if(buttons.size()!=0)
				{
					strButtons="[";
					for(Button btnInfo:buttons)
					{
						strButtons+="{";
						strButtons+="\"id\":\""+btnInfo.getId()+"\",\"tag\":\""+btnInfo.getTag()+"\",";
						strButtons+="\"clientfunction\":[";
						for(String strTemp:(List<String>)btnInfo.getJsFuction())
						{
							strButtons+="{\"function\":\""+XmlUnit.getEnXmlString(strTemp).replace("%2520", "%20")+"\"},";
						}
						if(btnInfo.getJsFuction().size()>0)
						{
							strButtons=strButtons.substring(0,strButtons.length()-1);
						}
						strButtons+="],";
						strButtons+="\"firefunction\":[";
						for(RelObjInfo relObj:(List<RelObjInfo>)btnInfo.getFireFuction()){
							strButtons+="{\"fireclass\":\""+relObj.getClassName()+"\",\"method\":\""+relObj.getMethod()+"\",";
							strButtons+="\"parm\":[";
							for(Map<String,String>map:(List<Map<String,String>>)relObj.getList()){
								strButtons+="{";
								for(Entry<String, String>entry:map.entrySet()){
									strButtons+="\"key\":\""+entry.getKey()+"\",\"value\":\""+XmlUnit.getEnXmlString(entry.getValue()).replace("%2520", "%20")+"\"";
								}
								strButtons+="},";
							}
							if(relObj.getList().size()>0)
							{
								strButtons=strButtons.substring(0,strButtons.length()-1);
							}
							strButtons+="]},";
						}
						if(btnInfo.getFireFuction().size()>0)
						{
							strButtons=strButtons.substring(0,strButtons.length()-1);
						}
						strButtons+="]";
						strButtons+="},";
					}
					if(buttons.size()>0)
					{
						strButtons=strButtons.substring(0,strButtons.length()-1);
					}
					strButtons+="]";
					jsonObj.element("buttons", strButtons);
				}
				if(controls.size()!=0)
				{
					strControls="[";
					itcontrol=controls.iterator();
					while(itcontrol.hasNext())
					{
						strControls+="{\"control\":[";
						mapcontrol=itcontrol.next();
						if(mapcontrol.keySet().size()>0)
						{
							strControls+="{";
						}
						for(Entry<String, String> entry:mapcontrol.entrySet()){
							strControls+="\"";
							strControls+=entry.getKey();
							strControls+="\":\"";
							strControlValue=XmlUnit.getEnXmlString(entry.getValue());
							strControlValue=strControlValue.replace("\"", "&quot;");
							strControlValue=strControlValue.replace("\\", "\\\\");
							strControlValue=strControlValue.replace("%2520", "%20");
							strControls+=strControlValue;
							strControls+="\",";
						}
						if(mapcontrol.keySet().size()>0)
						{
							strControls=strControls.substring(0,strControls.length()-1);
							strControls+="}";
						}
						strControls+="]},";
					}
					strControls=strControls.substring(0,strControls.length()-1);
					strControls+="]";
					jsonObj.element("controls", strControls);
				}
				if(dyparm.size()!=0)
				{
					jdyparm=JSONArray.fromObject(dyparm);
					jsonObj.element("dyparm", jdyparm);
				}
				if(hiddenform.size()!=0)
				{
					strHiddens="[";
					for(String strTemp:hiddenform){
						strHiddens+="{\"hiddenform\":\""+XmlUnit.getEnXmlString(localcommService.changeHtmlInfo(strTemp,controls))+"\"},";
					}
					if(hiddenform.size()>0)
					{
						strHiddens=strHiddens.substring(0,strHiddens.length()-1);
					}
					strHiddens+="]";
					jsonObj.element("hiddenform", strHiddens);
				}
				if(tags.size()!=0)
				{
					strTags="[";
					for(String strTemp:tags){
						strTags+="{\"tag\":\""+XmlUnit.getEnXmlString(strTemp).replace("%2520", "%20")+"\"},";
					}
					if(tags.size()>0)
					{
						strTags=strTags.substring(0,strTags.length()-1);
					}
					strTags+="]";
					jsonObj.element("tags", strTags);
				}
				if(authoritys.size()!=0)
				{
					strAuthoritys="[";
					itauthority=authoritys.entrySet().iterator();
					while(itauthority.hasNext())
					{
						entryauthority=itauthority.next();
						strAuthoritys+="{\"authority\":[";
						strAuthoritys+="{\"code\":\""+entryauthority.getKey()+"\",\"control\":[";
						mapauthory=entryauthority.getValue();
						itauthcontrol=mapauthory.entrySet().iterator();
						while(itauthcontrol.hasNext())
						{
							entryauthcontrol=itauthcontrol.next();
							strAuthoritys+="{\"id\":\""+entryauthcontrol.getKey()+"\",\"show\":[";
							listShow=entryauthcontrol.getValue();
							for(Map<String,String> mapInfo:listShow){
								strAuthoritys+="{";
								strAuthoritys+="\"showtype\":\""+XmlUnit.getEnXmlString(mapInfo.get("showtype")==null?"":mapInfo.get("showtype")).replace("%2520", "%20")+"\",";
								strAuthoritys+="\"authinfo\":\""+XmlUnit.getEnXmlString(mapInfo.get("authinfo")==null?"":mapInfo.get("authinfo")).replace("%2520", "%20")+"\"";
								strAuthoritys+="},";
							}
							if(listShow.size()>0)
							{
								strAuthoritys=strAuthoritys.substring(0,strAuthoritys.length()-1);
							}
							strAuthoritys+="]},";
						}
						if(mapauthory.entrySet().size()>0)
						{
							strAuthoritys=strAuthoritys.substring(0,strAuthoritys.length()-1);
						}
						strAuthoritys+="]}";
						strAuthoritys+="]},";
					}
					strAuthoritys=strAuthoritys.substring(0,strAuthoritys.length()-1);
					strAuthoritys+="]";
					jsonObj.element("authoritys", strAuthoritys);
				}
				else
				{
					jsonObj.element("authoritys", "[]");
				}
				request.setAttribute("_formdata", jsonObj);
			}
		}
		request.setAttribute("_formcontrols", strControls);
	}

	public void saveForm(String code,HttpServletRequest request,HttpServletResponse response) {
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
		JSONArray jsontags;
		JSONArray jsonlist;
		JSONArray jsonfirelist;
		JSONArray jsonfireparmlist;
		JSONArray jsoncontrols;
		JSONArray jsonauthoritys;
		JSONArray jsonauthoritycontrols;
		JSONArray jsonshows;
		JSONObject jobj;
		JSONObject jobjControl;
		JSONObject jobjClientFunction;
		JSONObject jobjFireparm;
		JSONObject jobjauthority;
		JSONObject jobjauthoritycontrol;
		JSONObject jobjshow;
		DynamicParm dparm;
		Button btnInfo;
		CoreService coreService;
		LocalCommonService localCommonService;
		List<Map<String,String>> list;
		Map<String,String> map;
		Map<String, String> mapcontrol;
		Map<String,String> mapshow;
		CacheManager manager;
		Ehcache cache;
		Subject subject;
		User user;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		form=new Form();
		tags=new ArrayList<String>();
		buttons=new ArrayList<Button>();
		hiddenform=new ArrayList<String>();
		dyparm=new ArrayList<DynamicParm>();
		controls = new ArrayList<Map<String,String>>();
		authoritys = new HashMap<String, Map<String, List<Map<String, String>>>>();
		form.setSysid(request.getParameter("sysid"));
		form.setCode(code);
		form.setDataname(request.getParameter("dataname"));
		form.setDatatype(request.getParameter("datatype"));
		form.setJstag(request.getParameter("jstag"));
		form.setName(request.getParameter("name"));
		form.setRemark(request.getParameter("remark"));
		form.setShowButton(request.getParameter("showButton"));
		form.setShowFormName(request.getParameter("showFormName"));
		form.setTitle(request.getParameter("title"));
		form.setDynamicform(request.getParameter("dynamicform"));
		form.setFiledir(request.getParameter("filedir"));
		form.setTags(tags);
		form.setButtons(buttons);
		form.setHiddenform(hiddenform);
		form.setDyparm(dyparm);
		form.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		form.setIsUpdate(true);
		form.setControls(controls);
		form.setAuthoritys(authoritys);
		strtags=request.getParameter("tags");
		strdyparm=request.getParameter("dyparm");
		strhiddenform=request.getParameter("hiddenform");
		strbuttons=request.getParameter("buttons");
		strcontrols=request.getParameter("controls");
		strcontrols=strcontrols.replace("\"[]\"", "\"%5B%5D\"");
		strauthoritys=request.getParameter("authoritys");
		if("".equals(strcontrols)==false)
		{
			jsontags = JSONArray.fromObject(strcontrols.replaceAll("\r", "").replaceAll("\n", ""));
			for(int i=0;i<jsontags.size();i++)
			{
				jobj = (JSONObject) jsontags.get(i);
				jsoncontrols=(JSONArray)jobj.get("control");
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
							strcontrolvalue=XmlUnit.getDeXmlString(strcontrolvalue);
							if(strcontrolvalue.equals("%5B%5D")==true){
								strcontrolvalue="[]";
							}
							mapcontrol.put(strcontrolkey, strcontrolvalue);
						}
					}
					controls.add(mapcontrol);
				}
			}
		}
		if("".equals(strauthoritys)==false)
		{
			jsontags = JSONArray.fromObject(strauthoritys.replaceAll("\r", "").replaceAll("\n", ""));
			for(int i=0;i<jsontags.size();i++)
			{
				jobj = (JSONObject) jsontags.get(i);
				jsonauthoritys=(JSONArray)jobj.get("authority");
				for(int j=0;j<jsonauthoritys.size();j++)
				{
					authControls=new HashMap<String, List<Map<String, String>>>();
					jobjauthority=(JSONObject)jsonauthoritys.get(j);
					jsonauthoritycontrols=(JSONArray)jobjauthority.get("control");
					for(int k=0;k<jsonauthoritycontrols.size();k++)
					{
						listshow=new ArrayList<Map<String,String>>();
						jobjauthoritycontrol=(JSONObject)jsonauthoritycontrols.get(k);
						jsonshows=(JSONArray)jobjauthoritycontrol.get("show");
						for(int u=0;u<jsonshows.size();u++)
						{
							mapshow=new HashMap<String,String>();
							jobjshow=(JSONObject)jsonshows.get(u);
							mapshow.put("showtype", jobjshow.get("showtype").toString());
							mapshow.put("authinfo", XmlUnit.getDeXmlString(jobjshow.get("authinfo").toString()));
							listshow.add(mapshow);
						}
						authControls.put(jobjauthoritycontrol.get("id").toString(), listshow);
					}
					authoritys.put(jobjauthority.get("code").toString(),authControls);
				}
			}
		}
		if("".equals(strtags)==false)
		{
			jsontags = JSONArray.fromObject(strtags.replaceAll("\r", "").replaceAll("\n", ""));
			for(int i=0;i<jsontags.size();i++)
			{
				jobj = (JSONObject) jsontags.get(i);
				strtag=(String)jobj.get("tag");
				strtag=XmlUnit.getDeXmlString(strtag);
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
				jobj = (JSONObject) jsontags.get(i);
				strhform=(String)jobj.get("hiddenform");
				strhform=XmlUnit.getDeXmlString(strhform);
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
				jsonlist=(JSONArray)jobj.get("clientfunction");
				for(int j=0;j<jsonlist.size();j++)
				{
					jobjClientFunction=(JSONObject)jsonlist.get(j);
					strclientfunction=(String)jobjClientFunction.get("function");
					strclientfunction=XmlUnit.getDeXmlString(strclientfunction);
					if(strclientfunction!=null)
					{
						jsFuction.add(strclientfunction);
					}
				}
				jsonfirelist=(JSONArray)jobj.get("firefunction");
				for(int j=0;j<jsonfirelist.size();j++)
				{
					relObj=new RelObjInfo();
					list=new ArrayList<Map<String,String>>();
					jobjClientFunction=(JSONObject)jsonfirelist.get(j);
					strclass=(String)jobjClientFunction.get("fireclass");
					strmethod=(String)jobjClientFunction.get("method");
					jsonfireparmlist=(JSONArray)jobjClientFunction.get("parm");
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
						strmethodkey=(String)jobjFireparm.get("key");
						strmethodvalue=(String)jobjFireparm.get("value");
						map=new HashMap<String, String>();
						strmethodvalue=XmlUnit.getDeXmlString(strmethodvalue);
						map.put(strmethodkey, strmethodvalue);
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
		form=getDynamicform(form);
		if(form.getCode().trim().equals("")==false && "configform".equals(form.getCode().trim())==false)
		{
			form.setIsUpdate(true);
			coreService.writeFormEncryptSql(form, "sc_form", user.getId());
			localCommonService.writeEncryptFile(XmlUnit.formtoXml(form, "utf-8").asXML(), request.getSession().getServletContext().getRealPath("/WEB-INF/form/"+form.getCode()+".form"),"utf-8");
			if(null!=cache)
			{
				cache.remove("form:/WEB-INF/form/"+code+".form");
			}
		}
	}

	private Form getDynamicform(Form form) {
		List<Map<String, String>> controls;
		List<Map<String, String>> conlist;
		List<String> listHidden;
		org.w3c.dom.Document document;
		org.w3c.dom.Node node;
		org.w3c.dom.Node nodeControl;
		org.w3c.dom.Node nodeValue;
		NodeList nodelist;
		DOMParser parser;
		StringReader read;
		InputSource source;
		String strInfo;
		String strIsControl;
		String strNodeValue;
		String strConId;
		String strImgId;
		String strImgTitle;
		String strImgInfo;
		String strTagInfo;
		String strHidden;
		JSONObject jsonObject;
		Boolean blInfo;
		parser = new DOMParser();
		conlist=new ArrayList<Map<String, String>>();
		read = null;
		try{
			strInfo=form.getDynamicform();
			read=new StringReader(strInfo);
			source=new InputSource(read);
			parser.parse(source);
			document=parser.getDocument();
			controls=form.getControls();
			listHidden=form.getHiddenform();
			for(Map<String, String> mapControl:controls){
				blInfo=false;
				strConId="";
				if(null != mapControl.get("id"))
				{
					strConId=mapControl.get("id");
				}
				if(null != mapControl.get("type") && "button".equals(mapControl.get("type"))==true)
				{
					conlist.add(mapControl);
				}
				else
				{
					nodelist=document.getElementsByTagName("IMG");
					for(int i=0;i<nodelist.getLength();i++)
					{
						strIsControl="";
						strNodeValue="";
						node=nodelist.item(i);
						jsonObject=null;
						nodeControl=node.getAttributes().getNamedItem("control");
						if(null!=nodeControl){
							strIsControl=nodeControl.getNodeValue();
						}
						nodeValue=node.getAttributes().getNamedItem("value");
						if(null!=nodeValue){
							strNodeValue=nodeValue.getNodeValue();
							strNodeValue=strNodeValue.replace("'", "\"");
							jsonObject=JSONObject.fromObject(strNodeValue);
						}
						if("1".equals(strIsControl)==true && null !=jsonObject)
						{
							strImgId=jsonObject.get("id").toString();
							strImgTitle=jsonObject.get("title").toString();
							if(strImgId.equals(strConId)==true)
							{
								strTagInfo="<"+strImgTitle+" id=\""+strImgId+"\" />";
								strImgInfo="<img src=\"[\n]?../../img/custcontrol.png\" control=\"[\n]?1\" value=\"[\n]?\\{'id':'"+strImgId+"','title':'"+strImgTitle+"'\\}\"[^>]?/>";
								strInfo=strInfo.replace("&#39;", "'");
								strInfo=strInfo.replaceAll(strImgInfo, strTagInfo);
								blInfo=true;
								break;
							}
						}
					}
					for(int i=0;i<listHidden.size();i++)
					{
						strHidden=listHidden.get(i);
						if(-1!=strHidden.indexOf(strConId))
						{
							blInfo=true;
							break;
						}
					}
					if(blInfo==true)
					{
						conlist.add(mapControl);
					}
				}
			}
			for(int i=0;i<listHidden.size();i++)
			{
				strIsControl="";
				strNodeValue="";
				strHidden=listHidden.get(i);
				read=new StringReader(strHidden);
				source=new InputSource(read);
				parser.parse(source);
				document=parser.getDocument();
				nodelist=document.getElementsByTagName("IMG");
				node=nodelist.item(0);
				jsonObject=null;
				nodeControl=node.getAttributes().getNamedItem("control");
				if(null!=nodeControl){
					strIsControl=nodeControl.getNodeValue();
				}
				nodeValue=node.getAttributes().getNamedItem("value");
				if(null!=nodeValue){
					strNodeValue=nodeValue.getNodeValue();
					strNodeValue=strNodeValue.replace("'", "\"");
					jsonObject=JSONObject.fromObject(strNodeValue);
				}
				if("1".equals(strIsControl)==true && null !=jsonObject)
				{
					strImgId=jsonObject.get("id").toString();
					strImgTitle=jsonObject.get("title").toString();
					strTagInfo="<"+strImgTitle+" id=\""+strImgId+"\" />";
					strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\" />";
					strHidden=strHidden.replace("&#39;", "'");
					strHidden=strHidden.replace(strImgInfo, strTagInfo);
					strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\" >";
					strHidden=strHidden.replace(strImgInfo, strTagInfo);
					strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\"/>";
					strHidden=strHidden.replace("&#39;", "'");
					strHidden=strHidden.replace(strImgInfo, strTagInfo);
					strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\">";
					strHidden=strHidden.replace(strImgInfo, strTagInfo);
					listHidden.set(i, strHidden);
				}
			}
			form.setDynamicform(strInfo);
			form.setControls(conlist);
		}
		catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		finally{
			if(null!=read)
			{
				try
				{
					read.close();
				}
				catch(Exception e)
				{
					log.error(e.getMessage(),e);
				}
			}
		}
		return form;
	}
}
