package com.common.tool;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.common.entity.activiti.WorkFlow;
import com.common.entity.form.Form;
import com.common.entity.frame.Frame;
import com.common.entity.menu.Menu;
import com.common.entity.query.Query;
import com.common.entity.tool.Tool;
import com.common.entity.tree.Tree;

public class InitCacheListener implements ServletContextListener{

	private static Logger log = Logger.getLogger(InitCacheListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent context) {
		String strPath;
		Ehcache cache;
		CacheManager manager;
		String strFilePath;
		manager=CacheManager.getInstance();
		cache=manager.getEhcache("ClassCachingFilter");
		log.info("server initCache start");
		strFilePath="/WEB-INF/form";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".form","form:",strFilePath);
		strFilePath="/WEB-INF/query";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".query","query:",strFilePath);
		strFilePath="/WEB-INF/tool";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".tool","tool:",strFilePath);
		strFilePath="/WEB-INF/tree";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".tree","tree:",strFilePath);
		strFilePath="/WEB-INF/frame";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".frame","frame:",strFilePath);
		strFilePath="/WEB-INF/menu";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".menu","menu:",strFilePath);
		strFilePath="/WEB-INF/workflow";
		strPath = context.getServletContext().getRealPath(strFilePath);
		initCacheByInfo(cache,strPath,".wf","wf:",strFilePath);
		log.info("server initCache end");
	}

	private void initCacheByInfo(Ehcache cache,String strPath,String strType,String strTitle,String strFilePath){
		File file;
		Object obj;
		String prefix;
		String strFileName;
		File[] listFile;
		Element eleMent;
		file=new File(strPath);
		if(true==file.isDirectory()){
			listFile=file.listFiles();
			for(File fileInfo:listFile){
				prefix="";
				obj=null;
				strFileName=fileInfo.getName();
				prefix=strFileName.substring(strFileName.lastIndexOf("."),strFileName.length());
				if(strType.equals(prefix)==true && ".form".equals(strType)==true){
					try {
						obj = XmlUnit.formatXmltoForm(XmlUnit.loadInit(strPath+"/"+strFileName));
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}
				if(strType.equals(prefix)==true && ".frame".equals(strType)==true){
					try {
						obj = XmlUnit.formatXmltoFrame(XmlUnit.loadInit(strPath+"/"+strFileName));
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}
				if(strType.equals(prefix)==true && ".tool".equals(strType)==true){
					try {
						obj = XmlUnit.formatXmltoTool(XmlUnit.loadInit(strPath+"/"+strFileName));
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}
				if(strType.equals(prefix)==true && ".query".equals(strType)==true){
					try {
						obj = XmlUnit.formatXmltoQuery(XmlUnit.loadInit(strPath+"/"+strFileName));
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}
				if(strType.equals(prefix)==true && ".tree".equals(strType)==true){
					try {
						obj = XmlUnit.formatXmltoTree(XmlUnit.loadInit(strPath+"/"+strFileName));
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}
				if(null != obj)
				{
					eleMent=new Element(strTitle + strFilePath + strFileName,obj);
					cache.put(eleMent);
				}
			}
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
