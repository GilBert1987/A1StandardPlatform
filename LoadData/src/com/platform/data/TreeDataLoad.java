package com.platform.data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.common.entity.login.User;
import com.common.entity.tree.Tree;
import com.common.entity.tree.TreeButton;
import com.common.entity.tree.TreeNode;
import com.common.entity.tree.TreeSqlInfo;
import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;
import com.common.service.CoreService;
import com.common.service.LocalCommonService;
import com.common.tool.XmlUnit;

public class TreeDataLoad implements IFormDataLoad {

	private static Logger log = Logger.getLogger(TreeDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) {
		Tree tree;
		Integer type;
		TreeNode trNode;
		TreeButton trButton;
		TreeSqlInfo trSqlInfo;
		List<TreeNode> listNode;
		List<TreeButton> listButton;
		byte[] bytes;
		String strId;
		String strSqlInfo;
		String strEncodeMap;
		JSONObject jsonObj;
		List<Map> listSqlMap;
		Map mapList;
		Object[] params;
		CoreService coreService;
		CommonService commService;
		LocalCommonService localcommService;
		tree = null;
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localcommService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		if (request.getParameter("id") != null) {
			listSqlMap=null;
			strId = request.getParameter("id").toString();
			params=new Object[]{
				strId
			};
			strSqlInfo = "select type,bytesinfo from sc_tree where id=? and bytesinfo is not null";
			try {
				listSqlMap = commService.queryListSql("com", strSqlInfo,params);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			if (0 != listSqlMap.size()) {
				mapList = listSqlMap.get(0);
				bytes = (byte[]) mapList.get("bytesinfo");
				type = (Integer) mapList.get("type");
				strEncodeMap = new String(bytes, Charset.forName("UTF-8"));
				if(null!=type && 0!=type)
				{
					strEncodeMap=coreService.readEncryptSql(strEncodeMap);
				}
				tree = localcommService.formatXmltoTree(coreService.decryptString(strEncodeMap));
			} 
			else {
				if (!XmlUnit.existsFile(request.getSession().getServletContext().getRealPath("/WEB-INF/tree/" + strId+ ".tree")).booleanValue()) {
					log.error(strId + " tree file is null");
				} else {
					try {
						tree = XmlUnit.formatXmltoTree(XmlUnit
								.loadInit(request
										.getSession()
										.getServletContext()
										.getRealPath(
												"/WEB-INF/tree/" + strId
														+ ".tree")));
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Xml init Error by id" + strId);
					}
				}
			}
			if (tree != null) {
				listNode = tree.getListNode();
				for (int i = 0; i < listNode.size(); i++) {
					trNode = listNode.get(i);
					listButton = trNode.getListButton();
					trSqlInfo = trNode.getSqlInfo();
					trSqlInfo.setSqlInfo(XmlUnit.getEnXmlString(XmlUnit
							.getDeXmlString(trSqlInfo.getSqlInfo())).replace("%2520", "%20"));
					trNode.setSelfun(XmlUnit.getEnXmlString(XmlUnit
							.getDeXmlString(trNode.getSelfun())).replace("%2520", "%20"));
					for (int j = 0; j < listButton.size(); j++) {
						trButton = listButton.get(j);
						trButton.setFun(XmlUnit.getEnXmlString(XmlUnit
								.getDeXmlString(trButton.getFun())).replace("%2520", "%20"));
					}
				}
				jsonObj = JSONObject.fromObject(tree);
				request.setAttribute("_treedata", jsonObj);
			}
		}
	}
	
	public void saveTree(String code,HttpServletRequest request,HttpServletResponse response){
		Tree tree;
		TreeNode trNode;
		TreeSqlInfo trSqlInfo;
		TreeButton trButton;
		List<TreeButton> btnlist;
		List<TreeNode> list;
		JSONArray jslist;
		JSONArray jsbtnlist;
		JSONObject jsNode;
		JSONObject jsbuttonNode;
		JSONObject jsSqlNode;
		CacheManager manager;
		Ehcache cache;
		Subject subject;
		User user;
		CoreService coreService;
		LocalCommonService localCommonService;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		coreService=(CoreService)ReflectFactory.getObjInstance("com.common.service.CoreService", request);
		localCommonService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", request);
		tree=new Tree();
		list=new ArrayList<TreeNode>();
		tree.setCode(code);
		tree.setJsinfo(XmlUnit.getDeXmlString((null==request.getParameter("jsinfo")?"":request.getParameter("jsinfo"))));
		tree.setFiledir(request.getParameter("filedir"));
		tree.setRemark((null==request.getParameter("remark")?"":request.getParameter("remark")));
		tree.setName(request.getParameter("name"));
		tree.setSelectnode(request.getParameter("selectnode"));
		tree.setLastmodifiedtime(String.valueOf(System.currentTimeMillis()));
		tree.setIsUpdate(true);
		tree.setListNode(list);
		if(null!=request.getParameter("listNode"))
		{
			jslist=JSONArray.fromObject(request.getParameter("listNode"));
			for(int i=0;i<jslist.size();i++){
				trNode=new TreeNode();
				trSqlInfo=new TreeSqlInfo();
				btnlist=new ArrayList<TreeButton>();
				jsNode=(JSONObject)jslist.get(i);
				jsSqlNode=(JSONObject)jsNode.get("sqlInfo");
				trNode.setAsync(jsNode.getString("async"));
				trNode.setCss(jsNode.getString("css"));
				trNode.setIcon(jsNode.getString("icon"));
				trNode.setIconOpen(jsNode.getString("iconOpen"));
				trNode.setIconClose(jsNode.getString("iconClose"));
				trNode.setLevel(jsNode.getString("level"));
				trNode.setName(jsNode.getString("name"));
				trNode.setNodeid(jsNode.getString("nodeid"));
				trNode.setNodetext(jsNode.getString("nodetext"));
				trNode.setOption(jsNode.getString("option"));
				trNode.setRemark((null==jsNode.getString("remark")?"":jsNode.getString("remark")));
				trNode.setSelfun(XmlUnit.getDeXmlString((null==jsNode.getString("selfun")?"":jsNode.getString("selfun"))));
				trNode.setTarget(jsNode.getString("target"));
				trNode.setType(jsNode.getString("type"));
				trNode.setUrl(jsNode.getString("url"));
				trNode.setSqlInfo(trSqlInfo);
				trNode.setListButton(btnlist);
				trSqlInfo.setDatabase(jsSqlNode.getString("database"));
				trSqlInfo.setType(jsSqlNode.getString("type"));
				trSqlInfo.setSqlInfo(XmlUnit.getDeXmlString(jsSqlNode.getString("sqlInfo")));
				jsbtnlist=jsNode.getJSONArray("listButton");
				for(int j=0;j<jsbtnlist.size();j++)
				{
					trButton=new TreeButton();
					jsbuttonNode=(JSONObject)jsbtnlist.get(j);
					trButton.setFun(XmlUnit.getDeXmlString(jsbuttonNode.getString("fun")));
					trButton.setIco(jsbuttonNode.getString("ico"));
					trButton.setName(jsbuttonNode.getString("name"));
					trButton.setOrder(jsbuttonNode.getString("order"));
					trButton.setType(jsbuttonNode.getString("type"));
					btnlist.add(trButton);
				}
				list.add(trNode);
			}
		}
		if(tree.getCode().trim().equals("")==false && "configform".equals(tree.getCode().trim())==false)
		{
			tree.setIsUpdate(true);
			coreService.writeEncryptSql(code, XmlUnit.treetoXml(tree, "utf-8").asXML(), "sc_tree", user.getId());
			localCommonService.writeEncryptFile(XmlUnit.treetoXml(tree, "utf-8").asXML(), request.getSession().getServletContext().getRealPath("/WEB-INF/tree/"+tree.getCode()+".tree"),"utf-8");
			if(null!=cache)
			{
				cache.remove("tree:/WEB-INF/tree/"+code+".tree");
			}
		}
	}

}
