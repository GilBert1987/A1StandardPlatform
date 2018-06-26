package com.common.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.common.db.handler.CustomerMapHandler;
import com.common.db.support.CustomerContextHolder;
import com.common.db.template.DbutilsTemplate;
import com.common.entity.firebutton.PreKey;
import com.common.entity.query.PageInfo;

public class CommonDBDao {
	
	private static Logger log = Logger.getLogger(CommonDBDao.class);
	
	private DbutilsTemplate dbutilsTemplate;
	
	/*
	 * 
	  	conn.setAutoCommit(false);
		qr.update(conn,strSql, params);
		注意:传入事务支持的时候传入conn qr.update不会关闭连接
	 * 
	 	conn.setAutoCommit(true);
		qr.query(conn,strSqlCountBuilder.toString(),scalarHandler,params)
		注意:如果事务为自动的时候 不传conn会自动取上一个conn 并完成sql后自动关闭连接
	 */
	
	@Resource
    private RedisDao redisDao;
	
	public DataSource getDataSource(String name){
		CustomerContextHolder.setContextType(name);
		return dbutilsTemplate.getDataSource();
	}
	
	public List<Map<String, Object>> execSqlByList(DataSource dataSource,String strSql,Object[] params)throws Exception{
		QueryRunner qr;
		Connection conn;
		List<Map<String, Object>> mapInfo;
		MapHandler mapHandler;
		qr = new QueryRunner(dataSource);
		conn=null;
		mapInfo=null;
		try {
			mapHandler=new MapHandler();
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			mapInfo=qr.execute(conn,strSql, mapHandler, params);
		} catch (SQLException e) {
			log.error("-----------------execSql error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return mapInfo;
	}
	
	public void execNoListSqlByTran(DataSource dataSource,List<String> listSql,Object[][] params)throws Exception{
		int ilistSize;
		QueryRunner qr;
		Connection conn;
		String strSql;
		ilistSize=listSql.size();
		qr = new QueryRunner(dataSource);
		conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<ilistSize;i++)
			{
				strSql=listSql.get(i);
				qr.update(conn,strSql, params[i]);
			}
			conn.commit();
		} catch (SQLException e) {
			if(null!=conn)
			{
				log.error("-----------------execNoListSqlByTran error begin---------------------");
				log.error(listSql);
				log.error(params);
				log.error("-----------------error end-------------------------------------");
				log.error(e.getMessage(),e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el.getMessage(),el);
				}
			}
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	public void execNoListSql(DataSource dataSource,String strSql,Object[] params)throws Exception{
		QueryRunner qr;
		Connection conn;
		qr = new QueryRunner(dataSource);
		conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			qr.update(conn,strSql, params);
			conn.commit();
		} catch (SQLException e) {
			if(null!=conn)
			{
				log.error("-----------------execNoListSql error begin---------------------");
				log.error(strSql);
				log.error(params);
				log.error("-----------------error end-------------------------------------");
				log.error(e.getMessage(),e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el.getMessage(),el);
				}
			}
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	public long queryCountSql(DataSource dataSource,String strSql,Object[] params)throws Exception{
		Long lRetBack;
		QueryRunner qr;
		Connection conn;
		StringBuilder strSqlCountBuilder;
		ScalarHandler<Long> scalarHandler;
		conn=null;
		lRetBack=0l;
		strSqlCountBuilder=new StringBuilder();
		scalarHandler=new ScalarHandler<Long>();
		strSqlCountBuilder.append("select count(*) as countInfo from( ");
		strSqlCountBuilder.append(strSql);
		strSqlCountBuilder.append(" ) as t ");
		strSqlCountBuilder.append("where (1=1) ");
		try
		{
			qr = new QueryRunner(dataSource);
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			lRetBack = qr.query(conn,strSqlCountBuilder.toString(),scalarHandler,params).longValue();
		}
		catch (SQLException e) {
			log.error("-----------------execSql error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return lRetBack;
	}
	
	public Map<String,Object> queryFullSql(DataSource dataSource,String strSql,Object[] params)throws Exception{
		QueryRunner qr;
		Connection conn;
		Map<String,Object> mapInfo;
		CustomerMapHandler mapHandler;
		qr = new QueryRunner(dataSource);
		conn=null;
		mapInfo=null;
		try {
			mapHandler=new CustomerMapHandler();
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			mapInfo=qr.query(conn,strSql, mapHandler, params);
		} catch (SQLException e) {
			log.error("-----------------execSql error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return mapInfo;
	}
	
	public PageInfo<Map> queryPageSql(DataSource dataSource,String strColInfo,String strSql,String strWhere,String strOrder,String strPriKeyId, Object[] params,int pageNum,int pageSize)throws Exception{
		int iRetBack;
		Object[] sqlParams;
		PageInfo<Map> pageMap;
		QueryRunner qr;
		Connection conn;
		Long lRetBack;
		DatabaseMetaData metaData;
		ScalarHandler<Long> scalarHandler;
		Map<String,Object> mapInfo;
		CustomerMapHandler mapHandler;
		StringBuilder strSqlCountBuilder;
		StringBuilder strSqlBuilder;
		ScalarHandler<Integer> scalarIntegerHandler;
		pageMap=null;
		scalarIntegerHandler=new ScalarHandler<Integer>();
		scalarHandler=new ScalarHandler<Long>();
		strSqlBuilder=new StringBuilder();
		strSqlCountBuilder=new StringBuilder();
		strSqlCountBuilder.append("select count(*) as countInfo from( ");
		strSqlCountBuilder.append(strSql);
		strSqlCountBuilder.append(" ) as t ");
		strSqlCountBuilder.append("where (1=1) ");
		if(StringUtils.isEmpty(strWhere)==false)
		{
			strSqlCountBuilder.append(strWhere);
		}
		qr = new QueryRunner(dataSource);
		conn=null;
		mapInfo=null;
		sqlParams=params;
		try {
			lRetBack=0l;
			mapHandler=new CustomerMapHandler();
			conn=dataSource.getConnection();
			conn.setAutoCommit(true);
			metaData=conn.getMetaData();
			if("MYSQL".equals(metaData.getDatabaseProductName().toUpperCase())==true)
			{
				lRetBack = qr.query(conn,strSqlCountBuilder.toString(),scalarHandler,params).longValue();
				if(null==strPriKeyId || "".equals(strPriKeyId))
				{
					strSqlBuilder.append("select * from( ");
					strSqlBuilder.append(strSql);
					strSqlBuilder.append(" ) as t ");
					strSqlBuilder.append("where (1=1) ");
					if(StringUtils.isEmpty(strWhere)==false)
					{
						strSqlBuilder.append(strWhere);
					}
					if(StringUtils.isEmpty(strOrder)==false)
					{
						strSqlBuilder.append(" order by "+strOrder);
					}
					if(-1!=pageNum && -1 != pageSize)
					{
						strSqlBuilder.append(" limit "+pageNum+","+pageSize+"");
					}
				}
				else
				{
					sqlParams=new Object[params.length*2];
					for(int i=0;i<params.length;i++)
					{
						sqlParams[i]=params[i];
						sqlParams[params.length+i]=params[i];
					}
					strSqlBuilder.append("select t.* from( ");
					strSqlBuilder.append("select * from( ");
					strSqlBuilder.append(strSql);
					strSqlBuilder.append(" ) as t ");
					strSqlBuilder.append("where (1=1) ");
					if(StringUtils.isEmpty(strWhere)==false)
					{
						strSqlBuilder.append(strWhere);
					}
					if(StringUtils.isEmpty(strOrder)==false)
					{
						strSqlBuilder.append(" order by "+strOrder);
					}
					strSqlBuilder.append(" ) as t join ( ");
					strSqlBuilder.append("select "+strPriKeyId+" from( ");
					strSqlBuilder.append(strSql);
					strSqlBuilder.append(" ) as t ");
					strSqlBuilder.append("where (1=1) ");
					if(StringUtils.isEmpty(strWhere)==false)
					{
						strSqlBuilder.append(strWhere);
					}
					if(StringUtils.isEmpty(strOrder)==false)
					{
						strSqlBuilder.append(" order by "+strOrder);
					}
					if(-1!=pageNum && -1!=pageSize)
					{
						strSqlBuilder.append(" limit "+pageNum+","+pageSize+"");
					}
					strSqlBuilder.append(" ) as t1 ");
					strSqlBuilder.append("on t."+strPriKeyId+"=t1."+strPriKeyId+" ");
					if(StringUtils.isEmpty(strOrder)==false)
					{
						strSqlBuilder.append(" order by "+strOrder);
					}
					if(-1!=pageNum && -1!=pageSize)
					{
						strSqlBuilder.append(" limit "+pageSize+"");
					}
				}
				
			}
			if("KYLIN".equals(metaData.getDatabaseProductName().toUpperCase())==true)
			{
				lRetBack = qr.query(conn,strSqlCountBuilder.toString(),scalarHandler,params).longValue();
				strSqlBuilder.append("select * from( ");
				strSqlBuilder.append(strSql);
				strSqlBuilder.append(" ) as t ");
				strSqlBuilder.append("where (1=1) ");
				if(StringUtils.isEmpty(strWhere)==false)
				{
					strSqlBuilder.append(strWhere);
				}
				if(StringUtils.isEmpty(strOrder)==false)
				{
					strSqlBuilder.append(" order by "+strOrder);
				}
				if(-1!=pageNum && -1!=pageSize)
				{
					strSqlBuilder.append(" limit "+pageSize+" offset "+pageNum);
				}
			}
			if("MICROSOFT SQL SERVER".equals(metaData.getDatabaseProductName().toUpperCase())==true){
				iRetBack=qr.query(conn,strSqlCountBuilder.toString(),scalarIntegerHandler,params).intValue();
				lRetBack = new Long((long)iRetBack);
				strSqlBuilder.append("WITH cte AS( ");
				strSqlBuilder.append("select top 100 percent * ,ROW_NUMBER() OVER(ORDER BY "+strColInfo+") AS _RowNo from( ");
				strSqlBuilder.append(strSql);
				strSqlBuilder.append(" ) as t ");
				strSqlBuilder.append("where (1=1) ");
				if(StringUtils.isEmpty(strWhere)==false)
				{
					strSqlBuilder.append(strWhere);
				}
				if(StringUtils.isEmpty(strOrder)==false)
				{
					strSqlBuilder.append(" order by "+strOrder);
				}
				strSqlBuilder.append(") ");
				if(-1!=pageNum && -1!=pageSize)
				{
					strSqlBuilder.append("SELECT * FROM cte WHERE _RowNo BETWEEN "+(pageNum+1)+" AND "+((pageNum/pageSize+1)*pageSize)+";");
				}
				else
				{
					strSqlBuilder.append("SELECT * FROM cte");
				}
			}
			mapInfo=qr.query(conn,strSqlBuilder.toString(), mapHandler, sqlParams);
			pageMap=new PageInfo<Map>((Map<String,String>)mapInfo.get("colInfo"),lRetBack,(List)mapInfo.get("retMap"));
		} catch (SQLException e) {
			log.error("-----------------execSql error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.closeQuietly(conn);
		}
		return pageMap;
	}
	
	public void insertObj(DataSource dataSource,String strtable,String strcoll,Object[] params)throws Exception{
		QueryRunner qr;
		Connection conn;
		StringBuilder strSqlBuilder;
		String[] strArr;
		strArr=strcoll.split(",");
		strSqlBuilder=new StringBuilder();
		strSqlBuilder.append("insert into  "+strtable+" ("+strcoll+")");
		strSqlBuilder.append("values(");
		for(int i=0;i<strArr.length;i++)
		{
			if(i==(strArr.length-1))
			{
				strSqlBuilder.append("?");
			}
			else
			{
				strSqlBuilder.append("?,");
			}
		}
		strSqlBuilder.append(")");
		qr = new QueryRunner(dataSource);
		conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			qr.update(conn,strSqlBuilder.toString(), params);
			conn.commit();
		}
		catch(Exception e)
		{
			if(null!=conn)
			{
				log.error("-----------------insertObj error begin---------------------");
				log.error(strtable);
				log.error(strcoll);
				log.error(params);
				log.error("-----------------error end-------------------------------");
				log.error(e.getMessage(),e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el.getMessage(),el);
				}
			}
			throw e;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	public void updateObj(DataSource dataSource,String strtable,String strcoll,Object[] params,String strwhere)throws Exception{
		QueryRunner qr;
		Connection conn;
		StringBuilder strSqlBuilder;
		String[] strArr;
		strArr=strcoll.split(",");
		strSqlBuilder=new StringBuilder();
		strSqlBuilder.append("update "+strtable+" ");
		strSqlBuilder.append("set ");
		for(int i=0;i<strArr.length;i++)
		{
			if(i==(strArr.length-1))
			{
				strSqlBuilder.append(strArr[i]+"=?");
			}
			else
			{
				strSqlBuilder.append(strArr[i]+"=?,");
			}
		}
		strSqlBuilder.append(" where (1=1) ");
		strSqlBuilder.append(strwhere);
		qr = new QueryRunner(dataSource);
		conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			qr.update(conn,strSqlBuilder.toString(), params);
			conn.commit();
		}
		catch(Exception e)
		{
			if(null!=conn)
			{
				log.error("-----------------updateObj error begin---------------------");
				log.error(strtable);
				log.error(strcoll);
				log.error(params);
				log.error(strwhere);
				log.error("-----------------error end-------------------------------");
				log.error(e.getMessage(),e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el.getMessage(),el);
				}
			}
			throw e;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	public String getTabKeyNo(DataSource dataSource,final String dbname,final String strTable,final String strCol)throws Exception{
		String strKey;
		String strYearType;
		String strMonthType;
		String strDayType;
		String strHourType;
		String strMinuteType;
		String strSecondType;
		String strDay;
		String strMonth;
		String strYear;
		String strHour;
		String strMinute;
		String strSecond;
		Object[] params;
		Object[] updateparams;
		String strDayInfo;
		String strMonthInfo;
		String strYearInfo;
		String strHourInfo;
		String strMinuteInfo;
		String strSecondInfo;
		String strNewKey;
		String strOldKey;
		Date dateTime;
		Boolean blInfo;
		Boolean blAdd;
		Integer iNumber;
		SimpleDateFormat sdf;
		PreKey key;
		QueryRunner qr;
		Connection conn;
		Map<String,Object> mapInfo;
		CustomerMapHandler mapHandler;
		StringBuilder strSqlBuilder;
		StringBuilder strUpdateSqlBuilder;
		strKey=null;
		key=null;
		dateTime=new Date();
	    strYear="";
	    strMonth="";
	    strDay="";
	    strHour="";
		strMinute="";
		strSecond="";
		blInfo=false;
		mapHandler=new CustomerMapHandler();
		qr = new QueryRunner(dataSource);
		conn=null;
		params=new Object[]{
			dbname,
			strTable,
			strCol
		};
		strSqlBuilder=new StringBuilder();
		strUpdateSqlBuilder=new StringBuilder();
	    strSqlBuilder.append("SELECT CK_Id AS id,CK_Value AS keyinfo,CK_Year AS YEAR,CK_Month AS MONTH,CK_Day AS DAY,CK_Hour AS HOUR,CK_Minute AS MINUTE,CK_Second AS SECOND,CK_TypeInfo AS typeinfo,CK_Num AS numinfo,CK_Number AS num ");
	    strSqlBuilder.append("FROM sc_systemtablerole ");
	    strSqlBuilder.append("WHERE CK_Id =(select CK_Id from sc_systemtablerole where CK_Dbname=? and CK_Tbname=? and CK_Key=?) ");
		synchronized(this)
		{
			try {
				conn=dataSource.getConnection();
				conn.setAutoCommit(false);
				mapInfo=qr.query(conn,strSqlBuilder.toString(), mapHandler,params);
				key=getMySqlKey(mapInfo);
				if(null!=key)
				{
					strUpdateSqlBuilder.append("update sc_systemtablerole ");
				    strUpdateSqlBuilder.append("set CK_Year=?,CK_Month=?,CK_Day=?,");
				    strUpdateSqlBuilder.append("CK_Hour=?,CK_Minute=?,CK_Second=?,CK_Number=? ");
				    strUpdateSqlBuilder.append("where CK_ID='"+key.getId()+"' ");
				    if(StringUtils.isEmpty(key.getYear())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Year='"+key.getYear()+"') ");
				    }
				    if(StringUtils.isEmpty(key.getMonth())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Month='"+key.getMonth()+"') ");
				    }
				    if(StringUtils.isEmpty(key.getDay())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Day='"+key.getDay()+"') ");
				    }
				    if(StringUtils.isEmpty(key.getHour())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Hour='"+key.getHour()+"') ");
				    }
				    if(StringUtils.isEmpty(key.getMinute())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Minute='"+key.getMinute()+"') ");
				    }
				    if(StringUtils.isEmpty(key.getSecond())==false)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Second='"+key.getSecond()+"') ");
				    }
				    if(key.getNum()!=0)
				    {
				    	strUpdateSqlBuilder.append("and (CK_Number="+key.getNum()+") ");
				    }
					if (key.getTypeinfo().length() == 3) {
						strYearType = key.getTypeinfo().substring(0, 1);
						strMonthType = key.getTypeinfo().substring(1, 2);
						strDayType = key.getTypeinfo().substring(2, 3);
						if (strDayType.equals("1") == true) {
							sdf = new SimpleDateFormat("dd");
							strDay = sdf.format(dateTime);
						}
						if (strMonthType.equals("1") == true) {
							sdf = new SimpleDateFormat("MM");
							strMonth = sdf.format(dateTime);
						}
						if (strYearType.equals("1") == true) {
							sdf = new SimpleDateFormat("yyyy");
							strYear = sdf.format(dateTime);
						}
						if (strYearType.equals("2") == true) {
							sdf = new SimpleDateFormat("yy");
							strYear = sdf.format(dateTime);
						}
					}
					if (key.getTypeinfo().length() == 6) {
						strYearType = key.getTypeinfo().substring(0, 1);
						strMonthType = key.getTypeinfo().substring(1, 2);
						strDayType = key.getTypeinfo().substring(2, 3);
						strHourType = key.getTypeinfo().substring(3, 4);
						strMinuteType = key.getTypeinfo().substring(4, 5);
						strSecondType = key.getTypeinfo().substring(5, 6);
						if (strHourType.equals("1") == true) {
							sdf = new SimpleDateFormat("HH");
							strHour = sdf.format(dateTime);
						}
						if (strMinuteType.equals("1") == true) {
							sdf = new SimpleDateFormat("mm");
							strMinute = sdf.format(dateTime);
						}
						if (strSecondType.equals("1") == true) {
							sdf = new SimpleDateFormat("ss");
							strSecond = sdf.format(dateTime);
						}
						if (strDayType.equals("1") == true) {
							sdf = new SimpleDateFormat("dd");
							strDay = sdf.format(dateTime);
						}
						if (strMonthType.equals("1") == true) {
							sdf = new SimpleDateFormat("MM");
							strMonth = sdf.format(dateTime);
						}
						if (strYearType.equals("1") == true) {
							sdf = new SimpleDateFormat("yyyy");
							strYear = sdf.format(dateTime);
						}
						if (strYearType.equals("2") == true) {
							sdf = new SimpleDateFormat("yy");
							strYear = sdf.format(dateTime);
						}
					}
					if ("".equals(strYear)) {
						strMonth = "";
						strDay = "";
					} else {
						if ("".equals(strMonth)) {
							strDay = "";
						}
					}
					if ("".equals(strYear) && "".equals(strMonth) && "".equals(strDay)) {
						blInfo = true;
					} else {
						if(null!=key.getYear() && null!=key.getMonth() && null!=key.getDay())
						{
							if (key.getTypeinfo().length() == 3 && key.getYear().equals(strYear) && key.getMonth().equals(strMonth) && key.getDay().equals(strDay)) {
								blInfo = true;
							}
							if(null!=key.getHour()  && null!=key.getMinute() && null!=key.getSecond())
							{
								if (key.getTypeinfo().length() == 6 && key.getYear().equals(strYear) && key.getMonth().equals(strMonth) && key.getDay().equals(strDay) && key.getHour().equals(strHour) && key.getMinute().equals(strMinute) && key.getSecond().equals(strSecond)) {
									blInfo = true;
								}
							}
						}
					}
				}
				blAdd=blInfo;
				strDayInfo=strDay;
				strMonthInfo=strMonth;
				strYearInfo=strYear;
				strHourInfo=strHour;
				strMinuteInfo=strMinute;
				strSecondInfo=strSecond;
				strNewKey="platform_"+dbname+"_"+strTable+"_"+strCol+"_"+strYearInfo+"_"+strMonthInfo+"_"+strDayInfo+"_"+strHourInfo+"_"+strMinuteInfo+"_"+strSecondInfo;
				strOldKey="platform_"+dbname+"_"+strTable+"_"+strCol+"_"+key.getYear()+"_"+key.getMonth()+"_"+key.getDay()+"_"+key.getHour()+"_"+key.getMinute()+"_"+key.getSecond();
				iNumber=redisDao.getRedisKey(strNewKey, strOldKey, blAdd);
				strKey = key.getKeyinfo() + strYear + strMonth + strDay + strHour + strMinute + strSecond + String.format("%0" + key.getNuminfo() + "d", iNumber);
				updateparams=new Object[]{
					strYear,
					strMonth,
					strDay,
					strHour,
					strMinute,
					strSecond,
					iNumber					
				};
				log.debug("curr key is:"+strKey);
				qr.update(conn,strUpdateSqlBuilder.toString(), updateparams);
				DbUtils.commitAndClose(conn);
			} catch (Exception e) {
				if (null != conn) {
					log.error("-----------------getKeybyTabCol error begin---------------------");
					log.error(strTable);
					log.error(strCol);
					log.error("-----------------error end--------------------------------------");
					log.error(e.getMessage(),e);
					try {
						DbUtils.rollback(conn);
					} catch (SQLException el) {
						log.error(el.getMessage(),el);
					}
				}
				throw e;
			} finally {
				DbUtils.closeQuietly(conn);
			}
		}
		if(strKey==null)
		{
			strKey=UUID.randomUUID().toString();
		}
		return strKey;
	}
	
	private PreKey getMySqlKey(Map<String, Object> mapInfo) {
		PreKey key;
		List<Map<String,Object>> rowList;
		Map<String,Object> rowMap;
		key=new PreKey();
		rowList=(List<Map<String,Object>>)mapInfo.get("retMap");
		if(1==rowList.size())
		{
			rowMap=rowList.get(0);
			if(null!=rowMap.get("id"))
			{
				key.setId(rowMap.get("id").toString());
			}
			if(null!=rowMap.get("keyinfo"))
			{
				key.setKeyinfo(rowMap.get("keyinfo").toString());
			}
			if(null!=rowMap.get("YEAR"))
			{
				key.setYear(rowMap.get("YEAR").toString());
			}
			if(null!=rowMap.get("MONTH"))
			{
				key.setMonth(rowMap.get("MONTH").toString());
			}
			if(null!=rowMap.get("DAY"))
			{
				key.setDay(rowMap.get("DAY").toString());
			}
			if(null!=rowMap.get("HOUR"))
			{
				key.setHour(rowMap.get("HOUR").toString());
			}
			if(null!=rowMap.get("MINUTE"))
			{
				key.setMinute(rowMap.get("MINUTE").toString());
			}
			if(null!=rowMap.get("SECOND"))
			{
				key.setSecond(rowMap.get("SECOND").toString());
			}
			if(null!=rowMap.get("typeinfo"))
			{
				key.setTypeinfo(rowMap.get("typeinfo").toString());
			}
			if(null!=rowMap.get("numinfo"))
			{
				key.setNuminfo(Integer.parseInt(rowMap.get("numinfo").toString()));
			}
			if(null!=rowMap.get("num"))
			{
				key.setNum(Long.valueOf(rowMap.get("num").toString()));
			}
		}
		return key;
	}

	public void deleteObj(DataSource dataSource,String strtable, String strkey,String strvalue)throws Exception{
		QueryRunner qr;
		Connection conn;
		StringBuilder strSqlBuilder;
		strSqlBuilder=new StringBuilder();
		strSqlBuilder.append("delete from "+strtable+" ");
		strSqlBuilder.append("where "+strkey+"=? ");
		qr = new QueryRunner(dataSource);
		conn=null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			qr.update(conn,strSqlBuilder.toString(), strvalue);
			conn.commit();
		}
		catch(Exception e)
		{
			if(null!=conn)
			{
				log.error("-----------------deleteObj error begin---------------------");
				log.error(strtable);
				log.error(strkey);
				log.error(strvalue);
				log.error("-----------------error end-------------------------------");
				log.error(e.getMessage(),e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el.getMessage(),el);
				}
			}
			throw e;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}

	public Map<String, Object> getQueryList(Map<String, Object> mapQuery, Object[] params)throws Exception {
		int pageSize;
		int pageNum;
		String colInfo;
		JSONObject jsonInfo;
		String strSql;
		String strWhere;
		String strOrderBy;
		String strPriKeyId;
		PageInfo<Map> pageInfo;
		DataSource dataSource;
		Iterator<Entry<String, Object>> itSearch;
		Entry<String, Object> searchEntry;
		Map<String, Object> searchMap;
		Map<String, Object> retMap;
		Map<String, Object> initMap;
		Map<String, Object> searMap;
		Map<String, Object> searMapInfo;
		retMap=new HashMap<String, Object>();
		searMap=new HashMap<String, Object>();
		retMap.put("colSearch", searMap);
		strSql="";
		strPriKeyId="";
		try
		{
			if(null!=mapQuery.get("priKey")){
				strPriKeyId=mapQuery.get("priKey").toString();
			}
			dataSource=this.getDataSource(mapQuery.get("sysid").toString());
			jsonInfo=(JSONObject)mapQuery.get("sqllist");
			if(null!=jsonInfo)
			{
				colInfo=jsonInfo.getString("colinfo");
				strSql=jsonInfo.getString("sql");
				strWhere=jsonInfo.getString("where");
				strOrderBy=jsonInfo.getString("orderBy");
				pageSize=jsonInfo.getInt("pageSize");
				pageNum=jsonInfo.getInt("pageNum");
				pageInfo=this.queryPageSql(dataSource,colInfo, strSql, strWhere,strOrderBy,strPriKeyId,params,pageSize*(pageNum),pageSize);
				retMap.put("sqllist", pageInfo);
			}
			strSql=(String)mapQuery.get("initsql");
			if(null!=strSql){
				initMap=this.queryFullSql(dataSource, strSql, null);
				if(null!=initMap)
				{
					retMap.put("initsql", initMap.get("retMap"));
				}
			}
			searchMap=(Map<String,Object>)mapQuery.get("colSearch");
			if(null!=searchMap){
				itSearch=searchMap.entrySet().iterator();
				while(itSearch.hasNext())
				{
					searchEntry=itSearch.next();
					strSql=searchEntry.getValue().toString();
					strSql=strSql.replaceAll("--", "");
					searMapInfo=this.queryFullSql(dataSource, strSql, null);
					searMap.put(searchEntry.getKey(), searMapInfo.get("retMap"));
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("-----------------getQueryList error begin---------------------");
			log.error(strSql);
			log.error("-----------------error end------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}
		return retMap;
	}
	
	public Map<String, Object> getQueryTotalList(Map<String, Object> mapQuery,String type, String key, Object[] params)throws Exception {
		String strSql;
		String strWhere;
		JSONObject jsonInfo;
		DataSource dataSource;
		StringBuilder strCountSql;
		Map<String, Object> retMap;
		strCountSql=new StringBuilder();
		retMap=new HashMap<String, Object>();
		strSql="";
		try
		{
			dataSource=this.getDataSource(mapQuery.get("sysid").toString());
			jsonInfo=(JSONObject)mapQuery.get("sqllist");
			if(null!=jsonInfo && false==("".equals(type)))
			{
				strSql=jsonInfo.getString("sql");
				strWhere=jsonInfo.getString("where");
				if("1".equals(type)==true)
				{
					strCountSql.append("select SUM("+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				if("2".equals(type)==true)
				{
					strCountSql.append("select MAX("+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				if("3".equals(type)==true)
				{
					strCountSql.append("select MIN("+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				if("4".equals(type)==true)
				{
					strCountSql.append("select AVG("+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				if("5".equals(type)==true)
				{
					strCountSql.append("select COUNT("+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				if("6".equals(type)==true)
				{
					strCountSql.append("select COUNT(DISTINCT "+key+") as "+key+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t");
				}
				retMap=this.queryFullSql(dataSource, strCountSql.toString(), params);
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getQueryList error begin---------------------");
			log.error(strSql);
			log.error("-----------------error end------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}
		return retMap;
	}
	
	public Map<String,Object> getTablColumns(DataSource dataSource,String tablename) throws Exception{
		StringBuilder strSql;
		Object[] param;
		Map<String, Object> mapInfo;
		param=new Object[]{
			tablename
		};
		mapInfo=null;
		strSql=new StringBuilder();
		strSql.append("SELECT COLUMN_NAME,COLUMN_KEY,data_type, ");
		strSql.append("CASE data_type  ");
		strSql.append("WHEN 'varchar' THEN CONCAT('{\"length\":',character_maximum_length,'}')  ");
		strSql.append("WHEN 'decimal' THEN CONCAT('{\"prescision\":',numeric_precision,',\"scale\":',numeric_scale,'}')  ");
		strSql.append("END A type_param,column_comment ");
		strSql.append("FROM information_schema.COLUMNS  ");
		strSql.append("WHERE (1=1) AND TABLE_SCHEMA='pmtable' AND Table_Name=? ");
		try {
			mapInfo=this.queryFullSql(dataSource, strSql.toString(),param);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return mapInfo;
	}

	/**
	 * @return the dbutilsTemplate
	 */
	public DbutilsTemplate getDbutilsTemplate() {
		return dbutilsTemplate;
	}

	/**
	 * @param dbutilsTemplate the dbutilsTemplate to set
	 */
	public void setDbutilsTemplate(DbutilsTemplate dbutilsTemplate) {
		this.dbutilsTemplate = dbutilsTemplate;
	}

	public Map<String, Object> getQueryDynamicTotalByQuery(Map<String, Object> mapQuery, String type, String key,String showvalue, Object[] params)throws Exception  {
		String strSql;
		String strWhere;
		JSONObject jsonInfo;
		DataSource dataSource;
		StringBuilder strCountSql;
		Map<String, Object> retMap;
		strCountSql=new StringBuilder();
		retMap=new HashMap<String, Object>();
		strSql="";
		try
		{
			dataSource=this.getDataSource(mapQuery.get("sysid").toString());
			jsonInfo=(JSONObject)mapQuery.get("sqllist");
			if(null!=jsonInfo && false==("".equals(type)))
			{
				strSql=jsonInfo.getString("sql");
				strWhere=jsonInfo.getString("where");
				if("1".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",SUM("+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				if("2".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",MAX("+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				if("3".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",MIN("+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				if("4".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",AVG("+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				if("5".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",COUNT("+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				if("6".equals(type)==true)
				{
					strCountSql.append("select "+key+" as "+key+",COUNT(DISTINCT "+showvalue+") as "+showvalue+" ");
					strCountSql.append("from ( ");
					strCountSql.append("select * ");
					strCountSql.append("from (" );
					strCountSql.append(strSql);
					strCountSql.append(") as t ");
					strCountSql.append("where (1=1) " + strWhere);
					strCountSql.append(") as t ");
					strCountSql.append("group by "+key);
				}
				retMap=this.queryFullSql(dataSource, strCountSql.toString(), params);
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getQueryList error begin---------------------");
			log.error(strSql);
			log.error("-----------------error end------------------------------------");
			log.error(e.getMessage(),e);
			throw e;
		}
		return retMap;
	}
}
