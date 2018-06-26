package com.common.unit;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.json.JSONArray;

import org.apache.catalina.loader.WebappClassLoader;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClassUnit {
	
	private static Logger log = Logger.getLogger(ClassUnit.class);
	
	public static JSONArray getInterFacePackage(String strJarFile){
		URL url;
		int u;
		URL jarUrl;
		String protocol;
		String pkgPath;
		String className;
		WebappClassLoader loader;
		URL[] urls;
		JarFile jarFile;
		JarEntry jarEntry;
		Class<?> classObj;
		Class<?>[] params;
		Method[] methods;
		JSONArray jsonArr;
		StringBuilder strRetBuilder;
		Enumeration<JarEntry> entries;
		JarURLConnection jarConnection;
		strRetBuilder=new StringBuilder();
		u=0;
		jsonArr=null;
		loader = (WebappClassLoader)Thread.currentThread().getContextClassLoader();
		urls=loader.getURLs();
		strRetBuilder.append("[");
		for(int i=0;i<urls.length;i++)
		{
			url = urls[i];
			protocol = url.getProtocol();
			pkgPath = url.getPath();
			if (pkgPath.indexOf(strJarFile) != -1) {
				try {
					jarUrl=new URL("jar:"+protocol+":"+pkgPath+"!/");
					jarConnection = (JarURLConnection)jarUrl.openConnection();
					jarFile=jarConnection.getJarFile();
					entries=jarFile.entries();
					while(entries.hasMoreElements())
					{
						jarEntry=entries.nextElement();
						if(false==jarEntry.isDirectory() && isCurrFile("class",jarEntry.getName()))
						{
							className=jarEntry.getName().replace("/", ".");
							className=className.substring(0, className.lastIndexOf("."));
							classObj=loader.loadClass(className);
							if(classObj.isInterface()==true)
							{
								if(0!=u)
								{
									strRetBuilder.append(",");
								}
								u++;
								methods=classObj.getMethods();
								strRetBuilder.append("{");
								strRetBuilder.append("\"classinfo\":\""+classObj.getName()+"\",");
								strRetBuilder.append("\"methods\":");
								strRetBuilder.append("[");
								for(int y=0;y<methods.length;y++){
									if(0!=y)
									{
										strRetBuilder.append(",");
									}
									params=methods[y].getParameterTypes();
									strRetBuilder.append("{");
									strRetBuilder.append("\"name\":\""+methods[y].getName()+"\",");
									strRetBuilder.append("\"rettype\":\""+methods[y].getReturnType().getName()+"\",");
									strRetBuilder.append("\"params\":");
									strRetBuilder.append("[");
									for(int z=0;z<params.length;z++)
									{
										if(0!=z)
										{
											strRetBuilder.append(",");
										}
										strRetBuilder.append("{");
										strRetBuilder.append("\"param\":\""+params[z].getName()+"\"");
										strRetBuilder.append("}");
									}
									strRetBuilder.append("]");
									strRetBuilder.append("}");
								}
								strRetBuilder.append("]");
								strRetBuilder.append("}");
							}
						}
					}
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		strRetBuilder.append("]");
		try
		{
			jsonArr = JSONArray.fromObject(strRetBuilder.toString());
		}
		catch(Exception e)
		{
			log.error(e);
		}
		return jsonArr;
	}
	
	public static JSONArray getTldFilePackage(String strJarFile){
		URL url;
		int u;
		URL jarUrl;
		String protocol;
		String pkgPath;
		WebappClassLoader loader;
		URL[] urls;
		Element eleRoot;
		List<Element> eleTags;
		List<Element> eleTagAttrs;
		JarFile jarFile;
		JarEntry jarEntry;
		Document document;
		SAXReader saxReader;
		Element eleShortName;
		Element eleUri;
		Element eleTag;
		Element eleTagName;
		Element eleTagInfo;
		Element eleTagAttr;
		Element eleTagClass;
		Element eleTagAttrName;
		Element eleTagAttrRequired;
		StringBuilder strRetBuilder;
		Element eleTagAttrRtexprvalue;
		InputStream jarEntryInputStream;
		Enumeration<JarEntry> entries;
		JarURLConnection jarConnection;
		strRetBuilder=new StringBuilder();
		u=0;
		loader = (WebappClassLoader)Thread.currentThread().getContextClassLoader();
		urls=loader.getURLs();
		strRetBuilder.append("[");
		for(int i=0;i<urls.length;i++)
		{
			url = urls[i];
			protocol = url.getProtocol();
			pkgPath = url.getPath();
			if (pkgPath.indexOf(strJarFile) != -1) {
				try {
					jarUrl=new URL("jar:"+protocol+":"+pkgPath+"!/");
					jarConnection = (JarURLConnection)jarUrl.openConnection();
					jarFile=jarConnection.getJarFile();
					entries=jarFile.entries();
					while(entries.hasMoreElements())
					{
						jarEntry=entries.nextElement();
						if(false==jarEntry.isDirectory() && isCurrFile("tld",jarEntry.getName()))
						{
							if(0!=u)
							{
								strRetBuilder.append(",");
							}
							u++;
							saxReader=null;
							jarEntryInputStream=null;
							try
							{
								strRetBuilder.append("{");
								saxReader = new SAXReader();
								jarEntryInputStream = jarFile.getInputStream(jarEntry);
								document=saxReader.read(new InputStreamReader(jarEntryInputStream,"UTF-8"));
								eleRoot=document.getRootElement();
								eleShortName=eleRoot.element("shortname");
								eleTags=eleRoot.elements("tag");
								eleUri=eleRoot.element("uri");
								strRetBuilder.append("\"shortname\":\""+eleShortName.getText()+"\",");
								strRetBuilder.append("\"uri\":\""+eleUri.getText()+"\",");
								strRetBuilder.append("\"tags\":[");
								for(int x=0;x<eleTags.size();x++)
								{
									if(0!=x)
									{
										strRetBuilder.append(",");
									}
									eleTag=eleTags.get(x);
									strRetBuilder.append("{");
									eleTagName=eleTag.element("name");
									eleTagInfo=eleTag.element("info");
									eleTagClass=eleTag.element("tagclass");
									strRetBuilder.append("\"name\":\""+eleTagName.getText()+"\",");
									strRetBuilder.append("\"info\":\""+eleTagInfo.getText()+"\",");
									strRetBuilder.append("\"tagclass\":\""+eleTagClass.getText()+"\",");
									eleTagAttrs=eleTag.elements("attribute");
									strRetBuilder.append("\"attrs\":[");
									for(int y=0;y<eleTagAttrs.size();y++)
									{
										if(0!=y)
										{
											strRetBuilder.append(",");
										}
										strRetBuilder.append("{");
										eleTagAttr=eleTagAttrs.get(y);
										eleTagAttrName=eleTagAttr.element("name");
										eleTagAttrRequired=eleTagAttr.element("required");
										eleTagAttrRtexprvalue=eleTagAttr.element("rtexprvalue");
										if(null!=eleTagAttrName){
											strRetBuilder.append("\"name\":\""+eleTagAttrName.getText()+"\"");
										}
										if(null!=eleTagAttrRequired){
											strRetBuilder.append(",");
											strRetBuilder.append("\"required\":\""+("true".equals(eleTagAttrRequired.getText())?"1":"0")+"\"");
										}
										else
										{
											strRetBuilder.append(",");
											strRetBuilder.append("\"required\":\"0\"");
										}
										if(null!=eleTagAttrRtexprvalue){
											strRetBuilder.append(",");
											strRetBuilder.append("\"rtexprvalue\":\""+("true".equals(eleTagAttrRtexprvalue.getText())?"1":"0")+"\"");
										}
										else
										{
											strRetBuilder.append(",");
											strRetBuilder.append("\"rtexprvalue\":\"0\"");
										}
										strRetBuilder.append("}");
									}
									strRetBuilder.append("]");
									strRetBuilder.append("}");
								}
								strRetBuilder.append("]");
							}
							catch(Exception ex)
							{
								log.error(ex);
							}
							finally{
								if(null!=jarEntryInputStream)
								{
									jarEntryInputStream.close();
								}
							}
							strRetBuilder.append("}");
						}
					}
				} catch (Exception e) {
					log.error(e);
				}
				
			}
		}
		strRetBuilder.append("]");
		return JSONArray.fromObject(strRetBuilder.toString());
	}
	
	private static Boolean isCurrFile(String strExec,String strFullName)
	{
		Boolean blInfo;
		blInfo=strExec.equals(strFullName.substring(strFullName.lastIndexOf(".")+1,strFullName.length()).toLowerCase());
		return blInfo;
	}
	
	/*
	private static InputStream readJarResource(JarURLConnection jarConnection){
        InputStream inputStream;
        JarFile jarFile;
        inputStream = null;
        try {
			jarFile = jarConnection.getJarFile();
			if (!jarConnection.getJarEntry().isDirectory()) { //如果jar中内容为目录则不返回inputstream
	            inputStream = jarFile.getInputStream(jarConnection.getJarEntry());
	        }
		} catch (IOException e) {
			log.error(e);
		}
        return inputStream;
    }
    */
}
