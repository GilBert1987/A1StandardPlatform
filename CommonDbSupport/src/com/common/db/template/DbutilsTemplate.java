package com.common.db.template;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.common.db.handler.CustomerMapHandler;
import com.common.db.support.CustomerContextHolder;

public class DbutilsTemplate {
	
	private DataSource defaultDataSource;
	private Map<String, DataSource> targetSqlDataSources;
	private static Logger log = Logger.getLogger(DbutilsTemplate.class);

	public void init(){
		String id;
		String dsType;
		String dbname;
		String strSql;
		QueryRunner qr;
		Connection conn;
		String strItemSql;
		Properties properties;
		DruidDataSource druidDs;
		Map<String,Object> mapInfo;
		CustomerMapHandler mapHandler;
		Map<String,Object> mapListInfo;
		List<Map<String,Object>> rowList;
		List<Map<String,Object>> rowItemList;
		conn=null;
		mapInfo=null;
		mapListInfo=null;
		if(null==targetSqlDataSources)
		{
			targetSqlDataSources=new HashMap<String, DataSource>();
		}
		qr = new QueryRunner(defaultDataSource);
		try {
			mapHandler=new CustomerMapHandler();
			strSql="SELECT id,dbname,dstype FROM sc_database WHERE dbenable=1";
			strItemSql="SELECT dbid,dbkey,dbvalue FROM sc_databaseproperties WHERE dbenable=1";
			conn=defaultDataSource.getConnection();
			mapInfo=qr.query(conn, strSql, mapHandler);
			mapListInfo=qr.query(conn, strItemSql, mapHandler);
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		if(null!=mapInfo && null != mapListInfo)
		{
			rowList=(List<Map<String,Object>>)mapInfo.get("retMap");
			rowItemList=(List<Map<String,Object>>)mapListInfo.get("retMap");
			for(Map<String,Object> mapRow:rowList){
				id = mapRow.get("id").toString();
				dbname = mapRow.get("dbname").toString();
				dsType = mapRow.get("dstype").toString();
				if("1".equals(dsType)==true)
				{
					druidDs=new DruidDataSource();
					properties=new Properties();
					druidDs.configFromPropety(properties);
					for(Map<String,Object> mapItemRow:rowItemList){
						if(id.equals(mapItemRow.get("dbid").toString())==true)
						{
							if("url".equals(mapItemRow.get("dbkey").toString())==true)
							{
								druidDs.setUrl(mapItemRow.get("dbvalue").toString());
								continue;
							}
							if("username".equals(mapItemRow.get("dbkey").toString())==true)
							{
								druidDs.setUsername(mapItemRow.get("dbvalue").toString());
								continue;
							}
							if("password".equals(mapItemRow.get("dbkey").toString())==true)
							{
								druidDs.setPassword(mapItemRow.get("dbvalue").toString());
								continue;
							}
							if("driverClass".equals(mapItemRow.get("dbkey").toString())==true)
							{
								druidDs.setDriverClassName(mapItemRow.get("dbvalue").toString());
								continue;
							}
							properties.setProperty(mapItemRow.get("dbkey").toString(), mapItemRow.get("dbvalue").toString());
						}
					}
					try{
						druidDs.init();
					}
					catch(Exception e){
						log.error(e.getMessage(),e);
					}
					targetSqlDataSources.put(dbname, druidDs);
				}
			}
		}
	}
	
	public void close(){
		DataSource ds;
		Set<String> keys;
		Iterator iterKey;
		DruidDataSource druidDs;
		if(null!=targetSqlDataSources)
		{
			keys=targetSqlDataSources.keySet();
			iterKey=keys.iterator();
			while(iterKey.hasNext()==true)
			{
				ds=targetSqlDataSources.get(iterKey.next().toString());
				if(ds instanceof DruidDataSource)
				{
					druidDs=(DruidDataSource)ds;
					druidDs.close();
				}
			}
		}
	}
	
	/**
	 * @return the targetSqlDataSources
	 */
	public Map<String, DataSource> getTargetSqlDataSources() {
		return targetSqlDataSources;
	}

	/**
	 * @param targetSqlDataSources the targetSqlDataSources to set
	 */
	public void setTargetSqlDataSources(Map<String, DataSource> targetSqlDataSources) {
		this.targetSqlDataSources = targetSqlDataSources;
	}

	/**
	 * @return the defaultDataSource
	 */
	public DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	/**
	 * @param defaultDataSource the defaultDataSource to set
	 */
	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public DataSource getDataSource() {
		DataSource retDataSource;
		retDataSource=targetSqlDataSources.get(CustomerContextHolder.getContextType());
		if(null==retDataSource)
		{
			retDataSource=this.defaultDataSource;
		}
		return retDataSource;
	}
}
