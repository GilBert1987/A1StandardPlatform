package com.common.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;

import com.common.entity.activiti.WorkFlow;
import com.common.entity.form.Form;
import com.common.entity.frame.Frame;
import com.common.entity.menu.Menu;
import com.common.entity.query.Query;
import com.common.entity.tool.Tool;
import com.common.entity.tree.Tree;
import com.common.iface.ILastmodifiedTime;

public interface LocalCommonService {
	
	Boolean existsFile(String strPath);
	
	Document loadInit(String strPath) throws Exception;
	
	Form formatXmltoForm(Document loadInit);
	
	Frame formatXmltoFrame(Document loadInit);
	
	Query formatXmltoQuery(Document loadInit);
	
	Tool formatXmltoTool(Document loadInit);
	
	Tree formatXmltoTree(Document loadInit);
	
	Map<String, Object[]> getMapByRequest(HttpServletRequest request);
	
	StringBuilder execTableInfo(String name, Form form,HttpServletRequest request);
	
	void redirecturl(String strurl,HttpServletRequest request,HttpServletResponse response);
	
	int getShowType(HttpServletRequest request,HttpServletResponse response, Map<String, List<Map<String, String>>> eleAuthority, String strId);
	
	void saveTable(String name,HttpServletRequest request);
	
	void saveMSSqlTable(String name,HttpServletRequest request);
	
	String getUrlInfo(String urlinfo, HttpServletRequest request,HttpServletResponse reponse);
	
	String getUrlInfoByParam(String urlinfo, HttpServletRequest request);
	
	void writeJs(String jsInfo, HttpServletRequest request);
	
	String dataBindInfo(HttpServletRequest request,String strKey);
	
	Map<String, Object> getVariableMap(HttpServletRequest request);
	
	void delXmlFileByPath(String path,HttpServletRequest request);
	
	void getTabKeyByRequest(String name,String strTable, String strCol, HttpServletRequest request);
	
	void removeCache(String cacheKey,HttpServletRequest request);
	
	String changeHtmlInfo(String strDynamicform,List<Map<String, String>> controls);
	
	Boolean checkClassLastmodifiedtime(String strSourcePath,Long sourcelasttime,HttpServletRequest request);
	
	void generateClass(String SourceEnc,String jspContent,String JspFile,Long LastModified,HttpServletRequest request);
	
	void saveformAndGenClass(String strCode, String strJSPPath, String configInfo,HttpServletRequest request, HttpServletResponse response);

	String getJspStringByForm(Form form, Map<String, String> config,HttpServletRequest request, HttpServletResponse response);

	String getJspStringByQuery(Query query,Map<String, Object> map, Map<String, String> configList, HttpServletRequest request,HttpServletResponse response);

	String getJspStringByMenu(Menu menu, Map<String, String> configList,HttpServletRequest request, HttpServletResponse response);
	
	String getJspStringByTree(Tree tree, Map<String, String> configList,HttpServletRequest request, HttpServletResponse response);

	String getJspStringByFrame(Frame frame,Map<String, String> configList, HttpServletRequest request,HttpServletResponse response);

	String getJspStringByTool(Tool tool, Map<String, String> configList,HttpServletRequest request, HttpServletResponse response);

	String getJspStringByWF(WorkFlow wf, Map<String, Map<String, List<Map<String, String>>>> authoritys,Map<String, String> configList, HttpServletRequest request,HttpServletResponse response);
	
	long StringFileBuffer(String sb, String strPath,ILastmodifiedTime lastModifyTime, String defaultEncoding)throws IOException;

	void setRequestAttrByMap(HttpServletRequest request,Map<String, Object[]> mapInfo, String mapName, String attrName);

	Map saveMapByAttr(Map mapInfo,String name,String value);
	
	String getUrlInfoByMap(String urlinfo, Map<String, Object[]> mapinfo);

	void writeEncryptFile(String sb, String strPath, String defaultEncoding);
	
	List<Map> getFileList(String strPath,String strExec);
	
	List<Map> loadMapByReadisKey(String strKey);
	
	String saveMapByReadisKey(List<Map> list);
	
	List<Map> loadMapByReadisKeyNoDel(String strKey);
	
	void saveMapByReadisKeyNoDel(String strKey,List<Map> list);

	void saveBtnStatus(String key, String btnId, String status);

	void saveRedisObj(Map mapInfo, HttpServletRequest request);

}
