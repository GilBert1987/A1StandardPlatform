package com.common.service;

import java.io.IOException;
import java.util.Map;

import org.dom4j.Document;

import com.common.entity.form.Form;
import com.common.entity.menu.Menu;

public interface CoreService {

	String BufferedFileReader(String path, String strFormat)throws IOException;

	String getUUID();
	
	String getShowTypeBySql(String sysid, String strSql,Object[] params) throws Exception;
	
	String getStringTypeBySql(String sysid, String strSql,Object[] params) throws Exception ;
	
	void saveMenu(Menu menu) throws Exception;

	Menu getMenu(String code) throws Exception;

	void delMenu(String code) throws Exception;
	
	Map<String, String> URLRequest(String URL);
	
	Form readFormEncryptString(String strForm, String strButtons,String strAuthoritys,String strControls,String strTags,String strHiddenform,String strDyparmBytes,Integer type);
	
	void writeFormEncryptSql(Form form, String table,String userId);
	
	void writeEncryptSql(String code,String sb, String table,String userId);
	
	void writeEncryptWorkFlowSql(Map mapListInfo);
	
	Map readEncryptWorkFlowSql(String strKey, Integer ver);
	
	String readEncryptSql(String data);
	
	Document decryptString(String strInfo);
		
	byte[] compress(byte[] byteInfo)throws Exception;
	
	byte[] uncompress(byte[] byteInfo)throws Exception;
}
