package com.common.tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.caucho.hessian.io.HessianInput;
import com.common.entity.firebutton.Button;
import com.common.entity.firebutton.RelObjInfo;
import com.common.entity.form.DynamicParm;
import com.common.entity.form.Form;
import com.common.entity.frame.Frame;
import com.common.entity.frame.FrameNode;
import com.common.entity.query.ChoiceTime;
import com.common.entity.query.Column;
import com.common.entity.query.InitSql;
import com.common.entity.query.PageStyle;
import com.common.entity.query.PriKey;
import com.common.entity.query.Query;
import com.common.entity.query.QueryButton;
import com.common.entity.query.Quick;
import com.common.entity.query.Search;
import com.common.entity.query.ShowStyle;
import com.common.entity.query.Title;
import com.common.entity.query.Unit;
import com.common.entity.tool.Item;
import com.common.entity.tool.ItemShow;
import com.common.entity.tool.Tool;
import com.common.entity.tool.ToolShowStyle;
import com.common.entity.tree.Tree;
import com.common.entity.tree.TreeButton;
import com.common.entity.tree.TreeNode;
import com.common.entity.tree.TreeSqlInfo;
import com.common.unit.EncodeUtil;
import com.common.unit.FileUtil;

public class XmlUnit {

	private static Logger log = Logger.getLogger(XmlUnit.class);
	
	public static Boolean existsFile(String strPath)
	{
		File file;
		file=new File(strPath);
		return file.exists();
	}
		
	public static Map<String, List<Map<String, String>>> getAuthorityElement(Map<String, Map<String, List<Map<String, String>>>>  mapAuthoritys,
			String strAuthority) {
		return mapAuthoritys.get(strAuthority);
	}
	
	public static String getEnXmlString(String strTemp){
		try {
			strTemp=strTemp.replace(" ", "%20");
			strTemp=URLEncoder.encode(strTemp,"UTF-8");
			strTemp=strTemp.replace("'", "&apos;");
			strTemp=strTemp.replace("\"", "&quot;");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return strTemp;
	}
	
	public static String getDeXmlString(String strTemp){
		try {
			strTemp = strTemp.replaceAll("%(?![0-9a-fA-F]{2})", "%25");  
			strTemp=URLDecoder.decode(strTemp, "UTF-8");
			strTemp=strTemp.replace("%20", " ");
			strTemp=strTemp.replace("&apos;", "'");
			strTemp=strTemp.replace("&quot;", "\"");
			strTemp=strTemp.replace("\r", " ");
			strTemp=strTemp.replace("\n", " ");
			strTemp=strTemp.replace("\t", " ");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return strTemp;
	}

	// iShowtype
	public static String changeHTMLTag(String strHtml, Map<String,String> econtrol) {
		int istart;
		int ilen;
		int ifind;
		Iterator<Entry<String, String>> iter;
		Pattern p;
		Matcher m;
		Entry<String, String> attr;
		String strcompile;
		String strValue;
		String strFine;
		String strAttrValue;
		StringBuilder strReturn;
		strFine = "";
		strReturn = new StringBuilder();
		strcompile = "<[^>]+id=\"" + econtrol.get("id")
				+ "\"[^>]+>";
		//替换
		p = Pattern.compile(strcompile);
		m = p.matcher(strHtml);
		if (true == m.find()) {
			strValue = m.group();
			istart = strValue.indexOf("id=\""+ econtrol.get("id") + "\"");
			istart += ("id=\"" + econtrol.get("id") + "\"").length();
			strReturn.append(strValue.substring(0, istart));
			iter = econtrol.entrySet().iterator();
			while (iter.hasNext()) {
				attr = (Entry<String, String>) iter.next();
				if ("id".equals(attr.getKey()) == false) {
					strAttrValue=attr.getValue();
					strAttrValue=strAttrValue.replaceAll("\\\\", "\\\\\\\\");
					strAttrValue=strAttrValue.replaceAll("\"", "\\\\\"");
					strReturn.append(" " + attr.getKey() + "=\""
							+ strAttrValue+ "\" ");
				}
			}
			strReturn.append(" showtype=\"${requestScope._"
					+ econtrol.get("id") + "_show}\" ");
			ifind = strHtml.indexOf(strValue);
			ilen = strValue.length();
			strReturn.append(strValue.substring(istart, strValue.length()));
			strFine = strHtml.substring(0, ifind);
			strFine += strReturn.toString();
			strFine += strHtml.substring(ifind + ilen, strHtml.length());
		} else {
			strFine = strHtml;
		}
		return strFine;
	}

	public static Document loadInit(String strPath) throws Exception {
		SAXReader saxReader;
		Document document;
		String strXmlInfo;
		saxReader = new SAXReader();
		if(FileUtil.isXmlFile(strPath)==true){
			document = saxReader.read(new File(strPath));
		}
		else
		{
			strXmlInfo=EncodeUtil.Decrypt(FileUtil.readFileToString(strPath));
			document=DocumentHelper.parseText(strXmlInfo);
		}
		return document;
	}
	
	public static Document decryptString(String strInfo){
		Document document;
		String strDeInfo;
		byte[] bytes;
		ByteArrayInputStream is;
		HessianInput hi;
		document=null;
		is=null;
		try
		{
			bytes=org.apache.commons.codec.binary.Base64.decodeBase64(strInfo);
			is = new ByteArrayInputStream(bytes);
			hi = new HessianInput(is);
			strDeInfo=hi.readString();
			document=DocumentHelper.parseText(strDeInfo);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		finally{
			if(null!=is)
			{
				try {
					is.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return document;
	}

	public static Document formtoXml(Form form, String strFormat) {
		Document document;
		Element root;
		Element controls;
		Element dynamicparm;
		Element jsinfo;
		Element include;
		Element dynamicform;
		Element hiddenform;
		Element buttons;
		Element remark;
		Element clientfunction;
		Element firefunction;
		Element e;
		Element fireclass;
		Element firemethod;
		Element fireparm;
		Element echild;
		Element authoritys;
		Element eleauthority;
		Element eleauthoritycontrol;
		Element eleshow;
		Element elecontrol;
		List<Map<String, String>> mapShow;
		List<Map<String,String>> listControl;
		Entry<String, Map<String, List<Map<String,String>>>> entryAuthority;
		Entry<String, List<Map<String, String>>> entryAuthorityControl;
		Entry<String, String> entryControl;
		Map<String, List<Map<String, String>>> mapAuthorityControl;
		Map<String, Map<String, List<Map<String,String>>>> mapAuthority;
		Iterator<Entry<String, Map<String, List<Map<String,String>>>>> itAuthority;
		Iterator<Entry<String, List<Map<String, String>>>> itAuthorityControl;
		Iterator<Entry<String, String>> itControl;
		List<Map<String, String>> parmlist;
		Map<String, String> parm;
		Set<String> set;
		Iterator<String> iter;
		String strkey;
		String strvalue;
		document = null;
		if (form.getIsUpdate() == true) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(strFormat);
			root = document.addElement("form");
			root.addAttribute("code", form.getCode());
			root.addAttribute("filedir", form.getFiledir());
			root.addAttribute("name", form.getName());
			root.addAttribute("title", form.getTitle());
			root.addAttribute("showbutton", form.getShowButton());
			root.addAttribute("showformname", form.getShowFormName());
			root.addAttribute("sysid", form.getSysid());
			root.addAttribute("dataname", form.getDataname());
			root.addAttribute("datatype", form.getDatatype());
			root.addAttribute("lastmodifiedtime", form.getLastmodifiedtime()
					.equals("") ? String.valueOf(System.currentTimeMillis()) : form.getLastmodifiedtime());
			root.addAttribute("xmlns", "http://commonplatform.com/1.0/form.xsd");
			controls = root.addElement("controls");
			dynamicparm = root.addElement("dynamicparm");
			jsinfo = root.addElement("jsinfo");
			include = root.addElement("include");
			dynamicform = root.addElement("dynamicform");
			hiddenform = root.addElement("hiddenform");
			buttons = root.addElement("buttons");
			authoritys = root.addElement("authoritys");
			remark = root.addElement("remark");
			mapAuthority=form.getAuthoritys();
			itAuthority=mapAuthority.entrySet().iterator();
			while(itAuthority.hasNext())
			{
				entryAuthority=itAuthority.next();
				eleauthority=authoritys.addElement("authority");
				eleauthority.addAttribute("code", entryAuthority.getKey());
				mapAuthorityControl=entryAuthority.getValue();
				itAuthorityControl=mapAuthorityControl.entrySet().iterator();
				while(itAuthorityControl.hasNext())
				{
					entryAuthorityControl=itAuthorityControl.next();
					mapShow=entryAuthorityControl.getValue();
					eleauthoritycontrol=eleauthority.addElement("control");
					eleauthoritycontrol.addAttribute("id", entryAuthorityControl.getKey());
					for(Map<String,String> mapInfo:mapShow)
					{
						eleshow=eleauthoritycontrol.addElement("show");
						eleshow.addAttribute("showtype", mapInfo.get("showtype"));
						eleshow.addAttribute("authinfo", mapInfo.get("authinfo"));
					}
				}
			}
			listControl=form.getControls();
			for(Map<String,String> mapInfo:listControl)
			{
				elecontrol=controls.addElement("control");
				itControl=mapInfo.entrySet().iterator();
				while(itControl.hasNext())
				{
					entryControl=itControl.next();
					elecontrol.addAttribute(entryControl.getKey(), entryControl.getValue());
				}
			}
			for (DynamicParm dyparm : (List<DynamicParm>)form.getDyparm()) {
				e = dynamicparm.addElement("parm");
				e.addAttribute("key", dyparm.getKey());
				e.addAttribute("value", dyparm.getValue());
			}
			for (Button btnInfo : (List<Button>)form.getButtons()) {
				e = buttons.addElement("button");
				e.addAttribute("id", btnInfo.getId());
				e.addAttribute("tag", btnInfo.getTag());
				clientfunction = e.addElement("clientfunction");
				firefunction = e.addElement("firefunction");
				for (RelObjInfo reObj : (List<RelObjInfo>)btnInfo.getFireFuction()) {
					fireclass = firefunction.addElement("class");
					fireclass.addAttribute("name", reObj.getClassName());
					firemethod = fireclass.addElement("method");
					firemethod.addAttribute("name", reObj.getMethod());
					parmlist = reObj.getList();
					for (int i = 0; i < parmlist.size(); i++) {
						fireparm = firemethod.addElement("parm");
						parm = parmlist.get(i);
						set = parm.keySet();
						iter = set.iterator();
						while (iter.hasNext()) {
							strkey = (String) iter.next();
							strvalue = parm.get(strkey);
							fireparm.addAttribute("name", strkey);
							fireparm.addAttribute("value", strvalue);
						}
					}
				}
				for (String strJs : (List<String>)btnInfo.getJsFuction()) {
					echild = clientfunction.addElement("function");
					echild.setText(strJs);
				}
			}
			for (String strHidden : (List<String>)form.getHiddenform()) {
				e = hiddenform.addElement("hiddenInfo");
				e.setText(strHidden);
			}
			for (String strTag : (List<String>)form.getTags()) {
				e = include.addElement("tag");
				e.setText(strTag);
			}
			jsinfo.setText(form.getJstag());
			dynamicform.setText(form.getDynamicform());
			remark.setText(form.getRemark());
		}
		return document;
	}

	public static Document querytoXml(Query query, String strFormat) {
		Document document;
		Element root;
		Element sqlinfo;
		Element defaultparm;
		Element primarykey;
		Element orderkey;
		Element initsql;
		Element choicetime;
		Element pagestyle;
		Element jsfunction;
		Element columns;
		Element search;
		Element title;
		Element unit;
		Element quick;
		Element buttons;
		Element remark;
		Element button;
		Element column;
		Element show;
		Quick quickobj;
		Title titleobj;
		Search searchobj;
		Unit unitobj;
		document = null;
		if (query.getIsUpdate() == true) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(strFormat);
			root = document.addElement("query");
			root.addAttribute("name", query.getName());
			root.addAttribute("filedir", query.getFiledir());
			root.addAttribute("title", query.getTitle());
			root.addAttribute("head", query.getHead());
			root.addAttribute("code", query.getCode());
			root.addAttribute("sysid", query.getSysid());
			root.addAttribute("datatype", query.getDatatype());
			root.addAttribute("lastmodifiedtime", query.getLastmodifiedtime()
					.equals("") ? String.valueOf(System.currentTimeMillis()) : query.getLastmodifiedtime());
			root.addAttribute("xmlns",
					"http://commonplatform.com/1.0/query.xsd");
			sqlinfo = root.addElement("sqlinfo");
			defaultparm = root.addElement("defaultparm");
			primarykey = root.addElement("primarykey");
			orderkey = root.addElement("orderkey");
			initsql = root.addElement("initsql");
			choicetime = root.addElement("choicetime");
			pagestyle = root.addElement("pagestyle");
			jsfunction = root.addElement("jsfunction");
			columns = root.addElement("columns");
			buttons = root.addElement("buttons");
			remark = root.addElement("remark");
			sqlinfo.setText(query.getSqlinfo());
			remark.setText(query.getRemark());
			defaultparm.addAttribute("value", query.getDefaultparm());
			primarykey.addAttribute("key", query.getPrikey().getKey());
			primarykey.addAttribute("type", query.getPrikey().getType());
			orderkey.addAttribute("value", query.getOrderkey());
			initsql.addAttribute("type", query.getInitsql().getType());
			initsql.setText(query.getInitsql().getStrsql());
			choicetime.addAttribute("type", query.getChoicetime().getType());
			choicetime.addAttribute("pagenum", query.getChoicetime()
					.getPagenum());
			pagestyle.addAttribute("type", query.getPagestyle().getType());
			jsfunction.setText(query.getJsfunction());
			if (query.getPagestyle() != null) {
				for (ShowStyle showobj : (List<ShowStyle>)query.getPagestyle().getShowlist()) {
					show = pagestyle.addElement("show");
					show.addAttribute("type", showobj.getType());
					show.addAttribute("isshow", showobj.getIsshow());
				}
			}
			for (QueryButton btnInfo : (List<QueryButton>)query.getButtons()) {
				button = buttons.addElement("button");
				button.addAttribute("name", btnInfo.getName());
				button.addAttribute("icon", btnInfo.getIcon());
				button.addAttribute("order", btnInfo.getOrder());
				button.addAttribute("jsclick", btnInfo.getJsclick());
			}
			for (Column colInfo : (List<Column>)query.getColumns()) {
				column = columns.addElement("column");
				column.addAttribute("key", colInfo.getKey());
				column.addAttribute("name", colInfo.getName());
				column.addAttribute("type", colInfo.getType());
				column.addAttribute("typeid",colInfo.getTypeid());
				column.addAttribute("relColId",colInfo.getRelColId());
				column.addAttribute("titColId",colInfo.getTitColId());
				column.addAttribute("currColId",colInfo.getCurrColId());
				column.addAttribute("currValId",colInfo.getCurrValId());
				column.addAttribute("dataSysId",colInfo.getDataSysId());
				column.addAttribute("titleSysId",colInfo.getTitleSysId());
				column.addAttribute("dataSqlInfo",colInfo.getDataSqlInfo());
				column.addAttribute("titleSqlInfo",colInfo.getTitleSqlInfo());
				column.addAttribute("grouptype", colInfo.getGrouptype());
				quickobj = colInfo.getQuick();
				searchobj = colInfo.getSearch();
				unitobj = colInfo.getUnit();
				titleobj = colInfo.getTitle();
				if (searchobj != null) {
					search = column.addElement("search");
					if(null!=searchobj.getSearchinfo()){
						search.setText(searchobj.getSearchinfo());
					}
					search.addAttribute("isshow", searchobj.getIsshow());
					search.addAttribute("order", searchobj.getOrder());
					search.addAttribute("type", searchobj.getType());
					search.addAttribute("datatype", searchobj.getDatatype());
				}
				if (titleobj != null) {
					title = column.addElement("title");
					title.addAttribute("isshow", titleobj.getIsshow());
					title.addAttribute("order", titleobj.getOrder());
					title.addAttribute("isedit", titleobj.getIsedit());
					title.addAttribute("width", titleobj.getWidth());
					title.addAttribute("align", titleobj.getAlign());
					title.addAttribute("sehead", titleobj.getSehead());
				}
				if (unitobj != null) {
					unit = column.addElement("unit");
					unit.addAttribute("merge", unitobj.getMerge());
					unit.addAttribute("mergerefer", unitobj.getMergerefer());
					unit.addAttribute("mergecolumn", unitobj.getMergecolumn());
				}
				if (quickobj != null) {
					quick = column.addElement("quick");
					quick.addAttribute("isshow", quickobj.getIsshow());
					quick.addAttribute("showname", quickobj.getShowname());
					quick.addAttribute("showoperation",quickobj.getShowoperation());
				}
			}
		}
		return document;
	}

	@SuppressWarnings("unchecked")
	public static Form formatXmltoForm(Document doc) {
		Form form;
		DynamicParm dparm;
		List<Button> listbtns;
		List<String> listtags;
		List<String> listjsFuction;
		List<String> listhiddenform;
		List<DynamicParm> listdynamicparm;
		List<Map<String, String>> listparm;
		Element controls;
		Element elejsinfo;
		Element root;
		Element remark;
		Element tag;
		Element hiddenformList;
		Element jsFuction;
		Element firefunction;
		Element clientfunction;
		Element button;
		Element eleclass;
		Element elemethod;
		Element eleparm;
		Element includeList;
		Element dynamicparm;
		Element btnList;
		Element dynamicformList;
		Element authoritys;
		Element control;
		Element authority;
		String dynamicform;
		String jsinfo;
		String strRemark;
		Button btn;
		RelObjInfo relObj;
		List<Element> btnInfo;
		List<Element> elejsList;
		List<Element> eleclassList;
		List<Element> eleparmList;
		List<RelObjInfo> relObjList;
		List<Element> authorityControls;
		Iterator<Attribute> iterAttrib;
		Attribute attControl; 
		Map<String, String> parm;
		Map<String,String> mapControl;
		Map<String,String> mapShow;
		List<Map<String,String>> listControls;
		Map<String, List<Map<String,String>>> mapAuthority;
		List<Map<String,String>> listShow;
		Map<String, Map<String, List<Map<String,String>>>> mapAuthoritys;
		form = new Form();
		listtags = new ArrayList<String>();
		listbtns = new ArrayList<Button>();
		listhiddenform = new ArrayList<String>();
		listdynamicparm = new ArrayList<DynamicParm>();
		listControls=new ArrayList<Map<String,String>>();
		mapAuthoritys=new HashMap<String, Map<String, List<Map<String,String>>>>();
		dynamicform = "";
		jsinfo = "";
		strRemark = "";
		// 获取根元素
		root = doc.getRootElement();
		hiddenformList = root.element("hiddenform");
		elejsinfo = root.element("jsinfo");
		includeList = root.element("include");
		dynamicformList = root.element("dynamicform");
		btnList = root.element("buttons");
		dynamicparm = root.element("dynamicparm");
		remark = root.element("remark");
		authoritys = root.element("authoritys");
		jsinfo = elejsinfo.getText();
		strRemark = remark.getText();
		controls = root.element("controls");
		form.setRemark(strRemark);
		form.setCode(root.attribute("code").getValue());
		form.setFiledir(root.attribute("filedir").getValue());
		form.setName(root.attribute("name").getValue());
		form.setTitle(root.attribute("title").getValue());
		form.setShowButton(root.attribute("showbutton").getValue());
		form.setShowFormName(root.attribute("showformname").getValue());
		form.setSysid(root.attribute("sysid").getValue());
		form.setDataname(root.attribute("dataname").getValue());
		form.setDatatype(root.attribute("datatype").getValue());
		form.setLastmodifiedtime(root.attribute("lastmodifiedtime").getValue());
		for(Iterator<Element> iterchild = controls.elements("control").iterator(); iterchild.hasNext();){
			mapControl=new HashMap<String,String>();
			control=(Element) iterchild.next();
			iterAttrib=control.attributeIterator();
			while(iterAttrib.hasNext()){
				attControl=(Attribute)iterAttrib.next();
				mapControl.put(attControl.getName(),attControl.getValue().toString());
			}
			listControls.add(mapControl);
		}
		for (Iterator<Element> iterchild = hiddenformList
				.elements("hiddenInfo").iterator(); iterchild.hasNext();) {
			tag = (Element) iterchild.next();
			listhiddenform.add(tag.getText());
		}
		for (Iterator<Element> iterchild = dynamicparm.elements("parm")
				.iterator(); iterchild.hasNext();) {
			tag = (Element) iterchild.next();
			dparm = new DynamicParm();
			dparm.setKey(tag.attribute("key").getValue());
			dparm.setValue(tag.attribute("value").getValue());
			listdynamicparm.add(dparm);
		}
		for (Iterator<Element> iterchild = includeList.elements("tag")
				.iterator(); iterchild.hasNext();) {
			tag = (Element) iterchild.next();
			listtags.add(tag.getText());
		}
		dynamicform = dynamicformList.getText();
		btnInfo = btnList.elements("button");
		for (Iterator<Element> itbut = btnInfo.iterator(); itbut.hasNext();) {
			button = (Element) itbut.next();
			btn = new Button();
			listjsFuction = new ArrayList<String>();
			relObjList = new ArrayList<RelObjInfo>();
			listbtns.add(btn);
			btn.setId(button.attribute("id").getValue());
			btn.setTag(button.attribute("tag").getValue());
			jsFuction = button.element("clientfunction");
			elejsList = jsFuction.elements("function");
			btn.setJsFuction(listjsFuction);
			btn.setFireFuction(relObjList);
			for (Iterator<Element> itjs = elejsList.iterator(); itjs.hasNext();) {
				clientfunction = (Element) itjs.next();
				listjsFuction.add(clientfunction.getText());
			}
			firefunction = button.element("firefunction");
			eleclassList = firefunction.elements("class");
			for (Iterator<Element> itclass = eleclassList.iterator(); itclass
					.hasNext();) {
				relObj = new RelObjInfo();
				eleclass = (Element) itclass.next();
				relObj.setClassName(eleclass.attribute("name").getValue());
				elemethod = eleclass.element("method");
				relObj.setMethod(elemethod.attribute("name").getValue());
				listparm = new ArrayList<Map<String, String>>();
				eleparmList = elemethod.elements("parm");
				relObj.setList(listparm);
				relObjList.add(relObj);
				for (Iterator<Element> itparm = eleparmList.iterator(); itparm
						.hasNext();) {
					parm = new HashMap<String, String>();
					eleparm = (Element) itparm.next();
					parm.put(eleparm.attribute("name").getValue(), eleparm
							.attribute("value").getValue());
					listparm.add(parm);
				}
			}
		}
		for(Iterator<Element> iterchild = authoritys.elements("authority").iterator(); iterchild.hasNext();){
			mapAuthority=new HashMap<String, List<Map<String,String>>>();
			authority=iterchild.next();
			authorityControls=authority.elements("control");
			for(Element authControl:authorityControls)
			{
				listShow=new ArrayList<Map<String,String>>();
				for(Element show:(List<Element>)authControl.elements("show"))
				{
					mapShow=new HashMap<String,String>();
					mapShow.put("showtype", show.attribute("showtype").getValue().toString());
					mapShow.put("authinfo", show.attribute("authinfo").getValue().toString());
					listShow.add(mapShow);
				}
				mapAuthority.put(authControl.attribute("id").getValue().toString(), listShow);
			}
			mapAuthoritys.put(authority.attribute("code").getValue().toString(), mapAuthority);
		}
		form.setAuthoritys(mapAuthoritys);
		form.setControls(listControls);
		form.setDynamicform(dynamicform);
		form.setJstag(jsinfo);
		form.setTags(listtags);
		form.setButtons(listbtns);
		form.setHiddenform(listhiddenform);
		form.setDyparm(listdynamicparm);
		return form;
	}

	@SuppressWarnings("unchecked")
	public static Query formatXmltoQuery(Document doc) {
		List<ShowStyle> showlist;
		List<Column> columnslist;
		List<QueryButton> buttonslist;
		ShowStyle showStyle;
		Query query;
		Column columnObj;
		QueryButton buttonObj;
		Search search;
		Title title;
		Unit unit;
		Quick quick;
		Element root;
		Element column;
		Element button;
		Element sqlinfo;
		Element defaultparm;
		Element primarykey;
		Element orderkey;
		Element initsql;
		Element choicetime;
		Element pagestyle;
		Element jsfunction;
		Element columns;
		Element buttons;
		Element remark;
		Element elesearch;
		Element eletitle;
		Element eleunit;
		Element elequick;
		Element eleshowStyle;
		Iterator<Element> iteshowStyle;
		Iterator<Element> itecolumn;
		Iterator<Element> itebutton;
		List<Element> eleshowlist;
		List<Element> elecolumnlist;
		List<Element> elebuttonlist;
		InitSql initsqlobj;
		PriKey prikey;
		ChoiceTime choicetimeobj;
		PageStyle pagestyleobj;
		query = new Query();
		initsqlobj = new InitSql();
		prikey = new PriKey();
		choicetimeobj = new ChoiceTime();
		pagestyleobj = new PageStyle();
		showlist = new ArrayList<ShowStyle>();
		columnslist = new ArrayList<Column>();
		buttonslist = new ArrayList<QueryButton>();
		query.setInitsql(initsqlobj);
		query.setChoicetime(choicetimeobj);
		query.setPrikey(prikey);
		query.setPagestyle(pagestyleobj);
		query.setColumns(columnslist);
		query.setButtons(buttonslist);
		root = doc.getRootElement();
		sqlinfo = root.element("sqlinfo");
		defaultparm = root.element("defaultparm");
		primarykey = root.element("primarykey");
		orderkey = root.element("orderkey");
		initsql = root.element("initsql");
		choicetime = root.element("choicetime");
		pagestyle = root.element("pagestyle");
		jsfunction = root.element("jsfunction");
		columns = root.element("columns");
		buttons = root.element("buttons");
		remark = root.element("remark");
		initsqlobj.setStrsql(initsql.getText().trim());
		initsqlobj.setType(initsql.attribute("type").getValue());
		prikey.setKey(primarykey.attribute("key").getValue());
		prikey.setType(primarykey.attribute("type").getValue());
		choicetimeobj.setPagenum(choicetime.attribute("pagenum").getValue());
		choicetimeobj.setType(choicetime.attribute("type").getValue());
		pagestyleobj.setType(pagestyle.attribute("type").getValue());
		pagestyleobj.setShowlist(showlist);
		eleshowlist = pagestyle.elements("show");
		iteshowStyle = eleshowlist.iterator();
		query.setLastmodifiedtime(root.attribute("lastmodifiedtime").getValue());
		while (iteshowStyle.hasNext()) {
			showStyle = new ShowStyle();
			eleshowStyle = iteshowStyle.next();
			showStyle.setType(eleshowStyle.attribute("type").getValue());
			showStyle.setIsshow(eleshowStyle.attribute("isshow").getValue());
			showlist.add(showStyle);
		}
		elecolumnlist = columns.elements("column");
		itecolumn = elecolumnlist.iterator();
		while (itecolumn.hasNext()) {
			search = new Search();
			title = new Title();
			unit = new Unit();
			quick = new Quick();
			columnObj = new Column();
			column = itecolumn.next();
			columnObj.setKey(column.attribute("key")==null?"":column.attribute("key").getValue());
			columnObj.setName(column.attribute("name")==null?"":column.attribute("name").getValue());
			columnObj.setType(column.attribute("type")==null?"":column.attribute("type").getValue());
			columnObj.setRelColId(column.attribute("relColId")==null?"":column.attribute("relColId").getValue());
			columnObj.setTitColId(column.attribute("titColId")==null?"":column.attribute("titColId").getValue());
			columnObj.setCurrColId(column.attribute("currColId")==null?"":column.attribute("currColId").getValue());
			columnObj.setCurrValId(column.attribute("currValId")==null?"":column.attribute("currValId").getValue());
			columnObj.setDataSysId(column.attribute("dataSysId")==null?"":column.attribute("dataSysId").getValue());
			columnObj.setTitleSysId(column.attribute("titleSysId")==null?"":column.attribute("titleSysId").getValue());
			columnObj.setDataSqlInfo(column.attribute("dataSqlInfo")==null?"":column.attribute("dataSqlInfo").getValue());
			columnObj.setTitleSqlInfo(column.attribute("titleSqlInfo")==null?"":column.attribute("titleSqlInfo").getValue());
			columnObj.setGrouptype(column.attribute("grouptype")==null?"":column.attribute("grouptype").getValue());
			if(null!=column.attribute("typeid"))
			{
				columnObj.setTypeid(column.attribute("typeid").getValue());
			}
			else
			{
				columnObj.setTypeid("0");
			}
			elesearch = column.element("search");
			eletitle = column.element("title");
			eleunit = column.element("unit");
			elequick = column.element("quick");
			if(null!=elesearch){
				if(null!=elesearch.attribute("datatype"))
				{
					search.setDatatype(elesearch.attribute("datatype").getValue());
				}
				if(null!=elesearch.attribute("isshow"))
				{
					search.setIsshow(elesearch.attribute("isshow").getValue());
				}
				if(null!=elesearch.attribute("order"))
				{
					search.setOrder(elesearch.attribute("order").getValue());
				}
				if(null!=elesearch.attribute("type"))
				{
					search.setType(elesearch.attribute("type").getValue());
				}
				if(null!=elesearch.getText())
				{
					search.setSearchinfo(elesearch.getText().trim());
				}
			}
			if(null!=eletitle){
				title.setSehead((null==eletitle.attribute("sehead")?"":eletitle.attribute("sehead").getValue()));
				title.setIsedit(eletitle.attribute("isedit").getValue());
				title.setIsshow(eletitle.attribute("isshow").getValue());
				title.setOrder(eletitle.attribute("order").getValue());
				title.setWidth(eletitle.attribute("width").getValue());
				if(null!=eletitle.attribute("align"))
				{
					title.setAlign(eletitle.attribute("align").getValue());
				}
				else
				{
					title.setAlign("");
				}
			}
			if(null!=eleunit){
				if(null!=eleunit.attribute("merge"))
				{
					unit.setMerge(eleunit.attribute("merge").getValue());
				}
				if(null!=eleunit.attribute("mergecolumn"))
				{
					unit.setMergecolumn(eleunit.attribute("mergecolumn").getValue());
				}
				if(null!=eleunit.attribute("mergerefer"))
				{
					unit.setMergerefer(eleunit.attribute("mergerefer").getValue());
				}
			}
			if(null!=elequick){
				if(null!=elequick.attribute("isshow")){
					quick.setIsshow(elequick.attribute("isshow").getValue());
				}
				if(null!=elequick.attribute("showname")){
					quick.setShowname(elequick.attribute("showname").getValue());
				}
				if(null!=elequick.attribute("showoperation")){
					quick.setShowoperation(elequick.attribute("showoperation").getValue());
				}
			}
			columnObj.setSearch(search);
			columnObj.setTitle(title);
			columnObj.setUnit(unit);
			columnObj.setQuick(quick);
			columnslist.add(columnObj);
		}
		elebuttonlist = buttons.elements("button");
		itebutton = elebuttonlist.iterator();
		while (itebutton.hasNext()) {
			buttonObj = new QueryButton();
			button = itebutton.next();
			buttonObj.setName(button.attribute("name").getValue());
			buttonObj.setIcon(button.attribute("icon").getValue());
			buttonObj.setJsclick(button.attribute("jsclick").getValue());
			buttonObj.setOrder(button.attribute("order").getValue());
			buttonslist.add(buttonObj);
		}
		query.setName(root.attribute("name").getValue());
		query.setCode(root.attribute("code").getValue());
		query.setFiledir(root.attribute("filedir").getValue());
		query.setTitle(root.attribute("title").getValue());
		query.setHead(root.attribute("head").getValue());
		query.setSysid(root.attribute("sysid").getValue());
		query.setDatatype(root.attribute("datatype").getValue());
		query.setSqlinfo(sqlinfo.getText().trim());
		query.setDefaultparm(defaultparm.attribute("value").getValue());
		query.setOrderkey(orderkey.attribute("value").getValue());
		query.setJsfunction(jsfunction.getText().trim());
		query.setRemark(remark.getText().trim());
		return query;
	}

	@SuppressWarnings("unchecked")
	public static Tree formatXmltoTree(Document doc) {
		Tree tree;
		List<TreeNode> listNode;
		List<TreeButton> listButton;
		TreeNode treenode;
		TreeButton treebutton;
		TreeSqlInfo sqlInfo;
		Element root;
		Element elebutton;
		Element eleJsinfo;
		Element elesqlInfo;
		Element elebuttons;
		Element elenodes;
		Element eleremark;
		Element elenoderemark;
		Iterator<Element> iteButton;
		List<Element> eleButtonlist;
		List<Element> eleNodelist;
		tree = new Tree();
		listNode = new ArrayList<TreeNode>();
		root = doc.getRootElement();
		eleJsinfo = root.element("jsinfo");
		eleremark = root.element("remark");
		elenodes = root.element("nodes");
		tree.setJsinfo(eleJsinfo.getText());
		tree.setRemark(eleremark.getText());
		tree.setSelectnode(root.attribute("selectnode").getValue());
		tree.setCode(root.attribute("code").getValue());
		tree.setName(root.attribute("name").getValue());
		tree.setFiledir(root.attribute("filedir").getValue());
		tree.setLastmodifiedtime(root.attribute("lastmodifiedtime").getValue());
		tree.setListNode(listNode);
		eleNodelist = elenodes.elements("node");
		for (Element elenode : eleNodelist) {
			treenode = new TreeNode();
			sqlInfo = new TreeSqlInfo();
			elebuttons = elenode.element("buttons");
			eleButtonlist = elebuttons.elements("button");
			elenoderemark = elenode.element("remark");
			elesqlInfo = elenode.element("sqlinfo");
			treenode.setCss(elenode.attribute("css").getValue());
			treenode.setLevel(elenode.attribute("level").getValue());
			treenode.setName(elenode.attribute("name").getValue());
			treenode.setType(elenode.attribute("type").getValue());
			treenode.setNodeid(elenode.attribute("nodeid").getValue());
			treenode.setNodetext(elenode.attribute("nodetext").getValue());
			treenode.setOption(elenode.attribute("option").getValue());
			treenode.setUrl(elenode.attribute("url").getValue());
			treenode.setTarget(elenode.attribute("target").getValue());
			treenode.setIcon(elenode.attribute("icon").getValue());
			treenode.setIconOpen(elenode.attribute("iconopen").getValue());
			treenode.setIconClose(elenode.attribute("iconclose").getValue());
			treenode.setAsync(elenode.attribute("async").getValue());
			treenode.setSelfun(elenode.attribute("selfun").getValue());
			treenode.setRemark(elenoderemark.getText());
			listButton = new ArrayList<TreeButton>();
			treenode.setListButton(listButton);
			treenode.setSqlInfo(sqlInfo);
			sqlInfo.setDatabase(elesqlInfo.attribute("database").getValue());
			sqlInfo.setType(elesqlInfo.attribute("type").getValue());
			sqlInfo.setSqlInfo(elesqlInfo.getText());
			iteButton = eleButtonlist.iterator();
			while (iteButton.hasNext()) {
				treebutton = new TreeButton();
				elebutton = iteButton.next();
				treebutton.setFun(elebutton.attribute("fun").getValue());
				treebutton.setIco(elebutton.attribute("ico").getValue());
				treebutton.setName(elebutton.attribute("name").getValue());
				treebutton.setOrder(elebutton.attribute("order").getValue());
				treebutton.setType(elebutton.attribute("type").getValue());
				listButton.add(treebutton);
			}
			listNode.add(treenode);
		}
		return tree;
	}

	public static Document treetoXml(Tree tree, String strFormat) {
		Document document;
		Element root;
		Element elenode;
		Element elenodes;
		Element elebutton;
		Element eleJsinfo;
		Element elesqlInfo;
		Element elebuttons;
		Element eleremark;
		Element elenoderemark;
		TreeSqlInfo sqlInfo;
		List<TreeButton> listButton;
		document = null;
		if (tree.getIsUpdate() == true) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(strFormat);
			root = document.addElement("tree");
			root.addAttribute("name", tree.getName());
			root.addAttribute("code", tree.getCode());
			root.addAttribute("filedir", tree.getFiledir());
			root.addAttribute("selectnode", tree.getSelectnode());
			root.addAttribute("lastmodifiedtime", tree.getLastmodifiedtime()
					.equals("") ? String.valueOf(System.currentTimeMillis()) : tree.getLastmodifiedtime());
			root.addAttribute("xmlns", "http://commonplatform.com/1.0/tree.xsd");
			elenodes = root.addElement("nodes");
			for (TreeNode node : tree.getListNode()) {
				elenode = elenodes.addElement("node");
				sqlInfo = node.getSqlInfo();
				elenode.addAttribute("level", node.getLevel());
				elenode.addAttribute("name", node.getName());
				elenode.addAttribute("type", node.getType());
				elenode.addAttribute("nodeid", node.getNodeid());
				elenode.addAttribute("nodetext", node.getNodetext());
				elenode.addAttribute("option", node.getOption());
				elenode.addAttribute("url", node.getUrl());
				elenode.addAttribute("target", node.getTarget());
				elenode.addAttribute("css", node.getCss());
				elenode.addAttribute("icon", node.getIcon());
				elenode.addAttribute("iconopen", node.getIconOpen());
				elenode.addAttribute("iconclose", node.getIconClose());
				elenode.addAttribute("selfun", node.getSelfun());
				elenode.addAttribute("async", node.getAsync());
				elesqlInfo = elenode.addElement("sqlinfo");
				elesqlInfo.addAttribute("database", sqlInfo.getDatabase());
				elesqlInfo.addAttribute("type", sqlInfo.getType());
				elesqlInfo.setText(sqlInfo.getSqlInfo());
				elenoderemark = elenode.addElement("remark");
				elenoderemark.setText(node.getRemark());
				listButton = node.getListButton();
				elebuttons = elenode.addElement("buttons");
				if (listButton != null && listButton.size() != 0) {
					for (TreeButton tb : listButton) {
						elebutton = elebuttons.addElement("button");
						elebutton.addAttribute("name", tb.getName());
						elebutton.addAttribute("order", tb.getOrder());
						elebutton.addAttribute("fun", tb.getFun());
						elebutton.addAttribute("ico", tb.getIco());
						elebutton.addAttribute("type", tb.getType());
					}
				}
			}
			eleJsinfo = root.addElement("jsinfo");
			eleremark = root.addElement("remark");
			eleJsinfo.setText(tree.getJsinfo());
			eleremark.setText(tree.getRemark());
		}
		return document;
	}

	@SuppressWarnings("unchecked")
	public static Tool formatXmltoTool(Document doc) {
		Tool tool;
		Item item;
		ItemShow itemShow;
		ToolShowStyle toolShowStyle;
		List<Item> listitem;
		List<ToolShowStyle> liststyle;
		List<ItemShow> listshow;
		Element root;
		Element elestyles;
		Element eleitems;
		Element eleremark;
		Element elenoderemark;
		Element eleitemshow;
		List<Element> eleStylelist;
		List<Element> eleItemlist;
		List<Element> eleItemShowlist;
		Iterator<Element> iterItemShow;
		tool = new Tool();
		listitem = new ArrayList<Item>();
		liststyle = new ArrayList<ToolShowStyle>();
		root = doc.getRootElement();
		eleitems = root.element("items");
		elestyles = root.element("styles");
		eleremark = root.element("remark");
		eleItemlist = eleitems.elements("item");
		eleStylelist = elestyles.elements("show");
		tool.setCode(root.attribute("code").getValue());
		tool.setName(root.attribute("name").getValue());
		tool.setFiledir(root.attribute("filedir").getValue());
		tool.setRemark(eleremark.getText());
		tool.setLastmodifiedtime(root.attribute("lastmodifiedtime").getValue());
		tool.setListitem(listitem);
		tool.setListstyle(liststyle);
		for (Element elenode : eleItemlist) {
			item = new Item();
			item.setCode(elenode.attribute("code").getValue());
			item.setName(elenode.attribute("name").getValue());
			item.setUrl(elenode.attribute("url").getValue());
			item.setOrder(elenode.attribute("order").getValue());
			if(null!=elenode.attribute("btnclass"))
			{
				item.setBtnclass(elenode.attribute("btnclass").getValue());
			}
			else
			{
				item.setBtnclass("");
			}
			if(null!=elenode.attribute("iconstyle"))
			{
				item.setIconstyle(elenode.attribute("iconstyle").getValue());
			}
			else
			{
				item.setIconstyle("");
			}
			if(null!=elenode.attribute("icon"))
			{
				item.setIcon(elenode.attribute("icon").getValue());
			}
			else
			{
				item.setIcon("");
			}
			listitem.add(item);
		}
		for (Element elenode : eleStylelist) {
			elenoderemark = elenode.element("remark");
			toolShowStyle = new ToolShowStyle();
			toolShowStyle.setCode(elenode.attribute("code").getValue());
			toolShowStyle.setName(elenode.attribute("name").getValue());
			toolShowStyle.setRemark(elenoderemark.getText());
			listshow = new ArrayList<ItemShow>();
			toolShowStyle.setListshow(listshow);
			eleItemShowlist = elenode.elements("item");
			iterItemShow = eleItemShowlist.iterator();
			while (iterItemShow.hasNext()) {
				eleitemshow = (Element) iterItemShow.next();
				itemShow = new ItemShow();
				itemShow.setCode(eleitemshow.attribute("code").getValue());
				itemShow.setIsselect(eleitemshow.attribute("isselect")
						.getValue());
				itemShow.setIsshow(eleitemshow.attribute("isshow").getValue());
				listshow.add(itemShow);
			}
			liststyle.add(toolShowStyle);
		}
		return tool;
	}

	public static Document tooltoXml(Tool tool, String strFormat) {
		Document document;
		Element root;
		Element eleitems;
		Element eleitem;
		Element elestyles;
		Element eleremark;
		Element eleshow;
		Element eleshowitem;
		Element eleshowremark;
		List<ItemShow> listshow;
		document = null;
		if (tool.getIsUpdate() == true) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(strFormat);
			root = document.addElement("tool");
			root.addAttribute("name", tool.getName());
			root.addAttribute("code", tool.getCode());
			root.addAttribute("filedir", tool.getFiledir());
			root.addAttribute("lastmodifiedtime", tool.getLastmodifiedtime()
					.equals("") ? String.valueOf(System.currentTimeMillis()) : tool.getLastmodifiedtime());
			root.addAttribute("xmlns", "http://commonplatform.com/1.0/tool.xsd");
			eleitems = root.addElement("items");
			elestyles = root.addElement("styles");
			if (tool.getListitem().size() > 0) {
				for (Item item : (List<Item>)tool.getListitem()) {
					eleitem = eleitems.addElement("item");
					eleitem.addAttribute("code", item.getCode());
					eleitem.addAttribute("name", item.getName());
					eleitem.addAttribute("url", item.getUrl());
					eleitem.addAttribute("order", item.getOrder());
					eleitem.addAttribute("btnclass", item.getBtnclass());
					eleitem.addAttribute("icon", item.getIcon());
					eleitem.addAttribute("iconstyle", item.getIconstyle());
				}
			}
			if (tool.getListstyle().size() > 0) {
				for (ToolShowStyle showStyle : (List<ToolShowStyle>)tool.getListstyle()) {
					eleshow = elestyles.addElement("show");
					eleshow.addAttribute("code", showStyle.getCode());
					eleshow.addAttribute("name", showStyle.getName());
					listshow = showStyle.getListshow();
					if (listshow != null) {
						for (ItemShow itemShow : listshow) {
							eleshowitem = eleshow.addElement("item");
							eleshowitem
									.addAttribute("code", itemShow.getCode());
							eleshowitem.addAttribute("isshow",
									itemShow.getIsshow());
							eleshowitem.addAttribute("isselect",
									itemShow.getIsselect());
						}
					}
					eleshowremark = eleshow.addElement("remark");
					eleshowremark.setText(showStyle.getRemark());
				}
			}
			eleremark = root.addElement("remark");
			eleremark.setText(tool.getRemark());
		}
		return document;
	}

	@SuppressWarnings("unchecked")
	public static Frame formatXmltoFrame(Document doc) {
		Frame frame;
		FrameNode node;
		List<FrameNode> listnode;
		Element root;
		Element eleframes;
		Element eleremark;
		List<Element> eleFrameNodes;
		frame = new Frame();
		listnode = new ArrayList<FrameNode>();
		root = doc.getRootElement();
		eleframes = root.element("framenodes");
		eleremark = root.element("remark");
		eleFrameNodes = eleframes.elements("framenode");
		frame.setNodelist(listnode);
		frame.setCode(root.attribute("code").getValue());
		frame.setFiledir(root.attribute("filedir").getValue());
		frame.setName(root.attribute("name").getValue());
		frame.setType(root.attribute("type").getValue());
		frame.setLastmodifiedtime(root.attribute("lastmodifiedtime").getValue());
		frame.setRemark(eleremark.getText());
		for (Element elenode : eleFrameNodes) {
			node = new FrameNode();
			node.setType(elenode.attribute("type").getValue());
			node.setUrl(elenode.attribute("url").getValue());
			node.setNumber(elenode.attribute("number").getValue());
			listnode.add(node);
		}
		return frame;
	}

	public static Document frametoXml(Frame frame, String strFormat) {
		Document document;
		Element root;
		Element eleframes;
		Element eleframenode;
		Element eleremark;
		List<FrameNode> listframe;
		document = null;
		if (frame.getIsUpdate() == true) {
			document = DocumentHelper.createDocument();
			document.setXMLEncoding(strFormat);
			root = document.addElement("frame");
			root.addAttribute("name", frame.getName());
			root.addAttribute("code", frame.getCode());
			root.addAttribute("filedir", frame.getFiledir());
			root.addAttribute("type", frame.getType());
			root.addAttribute("lastmodifiedtime", frame.getLastmodifiedtime()
					.equals("") ? String.valueOf(System.currentTimeMillis()) : frame.getLastmodifiedtime());
			root.addAttribute("xmlns",
					"http://commonplatform.com/1.0/frame.xsd");
			eleframes = root.addElement("framenodes");
			listframe = frame.getNodelist();
			if (listframe != null) {
				for (FrameNode node : listframe) {
					eleframenode = eleframes.addElement("framenode");
					eleframenode.addAttribute("type", node.getType());
					eleframenode.addAttribute("url", node.getUrl());
					eleframenode.addAttribute("number", node.getNumber());
				}
			}
			eleremark = root.addElement("remark");
			eleremark.setText(frame.getRemark());
		}
		return document;
	}

	/*
	public static WorkFlow formatJsontoWorkflow(String id, JSONObject jsObj) {
		WorkFlow workflow;
		Iterator itKeys;
		String strColKey;
		String strColValue;
		Button btnInfo;
		String strInfo;
		RelObjInfo relObjeInfo;
		JSONObject jsonProperties;
		JSONObject jsonFromInfo;
		JSONObject jsonTag;
		JSONObject jsonHidden;
		JSONObject jsonControl;
		JSONObject jsonFormlist;
		JSONObject jsonBtn;
		JSONObject jsonClientFun;
		JSONObject jsonFireFun;
		JSONObject jsonConIdInfo;
		JSONObject jsonParam;
		JSONArray jsonTags;
		JSONArray jsonHiddens;
		JSONArray jsonControls;
		JSONArray jsonchildShapes;
		JSONArray jsonFormlists;
		JSONArray jsonBtnlists;
		JSONArray jsonClientFuns;
		JSONArray jsonFireFuns;
		JSONArray jsonConIdInfos;
		JSONArray jsonParams;
		ActivitForm form;
		List<String> listTag;
		List<String> listHidden;
		List<ActivitForm> listForms;
		List<Button> listButton;
		List<String> listclientfunction;
		List<RelObjInfo> listfireFuction;
		List<Map<String,String>> parms;
		List<Map<String,String>> controls;
		Map<String,String> parm;
		Map<String,String> mapcontrol;
		listTag=new ArrayList<String>();
		listHidden=new ArrayList<String>();
		controls=new ArrayList<Map<String,String>>();
		listForms=new ArrayList<ActivitForm>();
		listButton=new ArrayList<Button>();
		workflow=new WorkFlow();
		jsonProperties=(JSONObject)jsObj.get("properties");
		jsonFromInfo=(JSONObject)jsonProperties.get("forminfo");
		jsonTags=(JSONArray)jsonFromInfo.get("taginfo");
		jsonchildShapes=(JSONArray)jsObj.get("childShapes");
		jsonHiddens=(JSONArray)jsonFromInfo.get("hidlist");
		jsonControls=(JSONArray)jsonFromInfo.get("conlist");
		jsonFormlists=(JSONArray)jsonFromInfo.get("formlist");
		jsonBtnlists=(JSONArray)jsonFromInfo.get("btnlist");
		for(int i=0;i<jsonTags.size();i++){
			strInfo="";
			jsonTag=(JSONObject)jsonTags.get(i);
			strInfo=getDeXmlString(jsonTag.getString("tag"));
			listTag.add(strInfo);
		}
		for(int i=0;i<jsonHiddens.size();i++){
			strInfo="";
			jsonHidden=(JSONObject)jsonHiddens.get(i);
			strInfo="<fm:input id=\""+jsonHidden.getString("id")+"\" />";
			listHidden.add(strInfo);
		}
		for(int i=0;i<jsonControls.size();i++)
		{
			mapcontrol=new HashMap<String,String>();
			jsonControl=(JSONObject)jsonControls.get(i);
			jsonConIdInfos=(JSONArray)jsonControl.get("control");
			if(jsonConIdInfos.size()>=1)
			{
				jsonConIdInfo=(JSONObject)jsonConIdInfos.get(0);
				itKeys=jsonConIdInfo.keys();
				while(itKeys.hasNext())
				{
					strColKey=itKeys.next().toString();
					strColValue="";
					strColValue=getDeXmlString(jsonConIdInfo.getString(strColKey));
					mapcontrol.put(strColKey, strColValue);
				}
			}
			controls.add(mapcontrol);
		}
		for(int i=0;i<jsonFormlists.size();i++){
			form=new ActivitForm();
			strInfo="";
			jsonFormlist=(JSONObject)jsonFormlists.get(i);
			form.setCode(jsonFormlist.getString("code"));
			strInfo=getDeXmlString(jsonFormlist.getString("url"));
			form.setUrl(strInfo);
			listForms.add(form);
		}
		for(int i=0;i<jsonBtnlists.size();i++){
			btnInfo=new Button();
			listclientfunction=new ArrayList<String>();
			listfireFuction=new ArrayList<RelObjInfo>();
			jsonBtn=(JSONObject)jsonBtnlists.get(i);
			btnInfo.setId(jsonBtn.getString("id"));
			btnInfo.setTag(jsonBtn.getString("tag"));
			btnInfo.setJsFuction(listclientfunction);
			btnInfo.setFireFuction(listfireFuction);
			jsonClientFuns=(JSONArray)jsonBtn.get("clientfunction");
			jsonFireFuns=(JSONArray)jsonBtn.get("firefunction");
			for(int j=0;j<jsonClientFuns.size();j++)
			{
				strInfo="";
				jsonClientFun=(JSONObject)jsonClientFuns.get(j);
				strInfo=getDeXmlString(jsonClientFun.getString("function"));
				listclientfunction.add(strInfo);
			}
			for(int j=0;j<jsonFireFuns.size();j++)
			{
				parms=new ArrayList<Map<String,String>>();
				jsonFireFun=(JSONObject)jsonFireFuns.get(j);
				jsonParams=(JSONArray)jsonFireFun.get("parm");
				relObjeInfo=new RelObjInfo();
				relObjeInfo.setClassName(jsonFireFun.getString("fireclass"));
				relObjeInfo.setMethod(jsonFireFun.getString("method"));
				relObjeInfo.setList(parms);
				for(int u=0;u<jsonParams.size();u++)
				{
					jsonParam=(JSONObject)jsonParams.get(u);
					parm=new HashMap<String,String>();
					parm.put(jsonParam.getString("key"), getDeXmlString(jsonParam.getString("value")));
					parms.add(parm);
				}
				listfireFuction.add(relObjeInfo);
			}
			listButton.add(btnInfo);
		}
		workflow.setCode(id);
		workflow.setName(jsonProperties.getString("name"));
		workflow.setFiledir(jsonFromInfo.getString("temp"));
		strInfo="";
		strInfo=getDeXmlString(jsonFromInfo.getString("jsinfo"));
		workflow.setJstag(strInfo);
		strInfo="";
		strInfo=getDeXmlString(jsonFromInfo.getString("dynamicform"));
		strInfo=getXmlDynamicform(strInfo,jsonBtnlists);
		workflow.setDynamicform(strInfo);
		workflow.setLastmodifiedtime(jsObj.getString("initTime"));
		workflow.setRemark(jsonProperties.getString("documentation"));
		workflow.setTags(listTag);
		workflow.setHiddenform(listHidden);
		workflow.setControls(controls);
		workflow.setForms(listForms);
		workflow.setJsonstring(jsObj.toString());
		workflow.setButtons(listButton);
		updateWFInfo(workflow,jsonchildShapes);
		return workflow;
	}

	private static String getXmlDynamicform(String strInfo,JSONArray jsonBtnlists) {
		String strImgId;
		String strImgTitle;
		String strTagInfo;
		String strImgInfo;
		JSONObject jsonObject;
		for(int i=0;i<jsonBtnlists.size();i++)
		{
			jsonObject=(JSONObject)jsonBtnlists.get(i);
			strImgId=jsonObject.get("id").toString();
			strImgTitle=jsonObject.get("tag").toString();
			strTagInfo="<"+strImgTitle+" id=\""+strImgId+"\" />";
			strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\"/>";
			strInfo=strInfo.replace("&#39;", "'");
			strInfo=strInfo.replace(strImgInfo, strTagInfo);
			strImgInfo="<img src=\"../../img/custcontrol.png\" control=\"1\" value=\"{'id':'"+strImgId+"','title':'"+strImgTitle+"'}\">";
			strInfo=strInfo.replace(strImgInfo, strTagInfo);
		}
		return strInfo;
	}

	private static void updateWFInfo(WorkFlow wf,JSONArray jsonchildShapes){
		FormKey formKey;
		String strStartId;
		String strFormKey;
		String strFormkeydefinition;
		JSONArray jsoncontrolroles;
		JSONArray jsonAuthoritys;
		JSONArray jsonShows;
		JSONObject jsonchildShape;
		JSONObject jsonstencil;
		JSONObject jsonproperties;
		JSONObject jsonformKey;
		JSONObject jsoncontrolrole;
		JSONObject jsonAuthority;
		JSONObject jsonShow;
		List<FormKey> listFormkey;
		Map<String, Map<String, List<Map<String,String>>>> authoritys;
		Map<String, List<Map<String,String>>> authority;
		Map<String,String> mapShow;
		List<Map<String,String>> authcontrol;
		listFormkey=new ArrayList<FormKey>();
		strStartId="";
		jsonformKey=null;
		for(int i=0;i<jsonchildShapes.size();i++)
		{
			formKey=new FormKey();
			authoritys=new HashMap<String, Map<String, List<Map<String,String>>>>();
			jsonchildShape=(JSONObject)jsonchildShapes.get(i);
			jsonstencil=(JSONObject)jsonchildShape.get("stencil");
			jsonproperties=(JSONObject)jsonchildShape.get("properties");
			jsoncontrolroles=(JSONArray)jsonproperties.get("controlrole");
			if("StartNoneEvent".equals(jsonstencil.getString("id"))==true){
				strStartId=jsonchildShape.getString("resourceId");
				formKey.setId(jsonchildShape.getString("resourceId"));
				strFormkeydefinition=jsonproperties.getString("formkeydefinition");
				strFormkeydefinition=getDeXmlString(strFormkeydefinition);
				jsonformKey=JSONObject.fromObject(strFormkeydefinition);
				formKey.setStartcode(jsonformKey.getString("startcode"));
				formKey.setEndcode(jsonformKey.getString("endcode"));
				for(int j=0;j<jsoncontrolroles.size();j++)
				{
					jsoncontrolrole=(JSONObject)jsoncontrolroles.get(j);
					authority=new HashMap<String, List<Map<String,String>>>();
					jsonAuthoritys=(JSONArray)jsoncontrolrole.get("authority");
					for(int u=0;u<jsonAuthoritys.size();u++)
					{
						authcontrol=new ArrayList<Map<String,String>>();
						jsonAuthority=(JSONObject)jsonAuthoritys.get(u);
						jsonShows=(JSONArray)jsonAuthority.get("show");
						for(int x=0;x<jsonShows.size();x++)
						{
							jsonShow=(JSONObject)jsonShows.get(x);
							mapShow=new HashMap<String,String>();
							mapShow.put("showtype", jsonShow.getString("showtype"));
							mapShow.put("authinfo", jsonShow.getString("authinfo"));
							authcontrol.add(mapShow);
						}
						authority.put(jsonAuthority.getString("id"), authcontrol);
					}
					authoritys.put(jsoncontrolrole.getString("type"),authority);
				}
				formKey.setAuthoritys(authoritys);
				listFormkey.add(formKey);
			}
			if("UserTask".equals(jsonstencil.getString("id"))==true)
			{
				strFormKey=getDeXmlString(jsonproperties.getString("formkeydefinition"));
				if("".equals(strFormKey)==false)
				{
					jsonformKey=JSONObject.fromObject(strFormKey);
				}
				formKey.setId(jsonchildShape.getString("resourceId"));
				formKey.setStartcode(jsonformKey.getString("startcode"));
				formKey.setEndcode(jsonformKey.getString("endcode"));
				for(int j=0;j<jsoncontrolroles.size();j++)
				{
					jsoncontrolrole=(JSONObject)jsoncontrolroles.get(j);
					authority=new HashMap<String, List<Map<String,String>>>();
					jsonAuthoritys=(JSONArray)jsoncontrolrole.get("authority");
					for(int u=0;u<jsonAuthoritys.size();u++)
					{
						authcontrol=new ArrayList<Map<String,String>>();
						jsonAuthority=(JSONObject)jsonAuthoritys.get(u);
						jsonShows=(JSONArray)jsonAuthority.get("show");
						for(int x=0;x<jsonShows.size();x++)
						{
							jsonShow=(JSONObject)jsonShows.get(x);
							mapShow=new HashMap<String,String>();
							mapShow.put("showtype", jsonShow.getString("showtype"));
							mapShow.put("authinfo", jsonShow.getString("authinfo"));
							authcontrol.add(mapShow);
						}
						authority.put(jsonAuthority.getString("id"), authcontrol);
					}
					authoritys.put(jsoncontrolrole.getString("type"),authority);
				}
				formKey.setAuthoritys(authoritys);
				listFormkey.add(formKey);
			}
		}
		wf.setInitformkey(strStartId);
		wf.setFormkeys(listFormkey);
	}
	*/
}