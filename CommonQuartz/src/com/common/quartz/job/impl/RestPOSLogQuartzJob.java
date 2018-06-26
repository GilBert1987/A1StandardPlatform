package com.common.quartz.job.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.common.service.CommonService;

public class RestPOSLogQuartzJob extends QuartzJobBean {
	
	private static Logger log = Logger.getLogger(RestPOSLogQuartzJob.class);
	
	@Resource
	private CommonService commService;
	
	/**
	* 要调度的具体任务
	*/
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Map mapInfo;
		Date dateNow;
		int ilistSize;
		String strKey;
		DataSource ds;
		List<Map> list;
		Object[] param;
		QueryRunner qr;
		String strValue;
		Connection conn;
		String strSqlInfo;
		String strEatDate;
		JobDataMap dataMap;
		JobDetail jobDetail;
		String strInsertSql;
		String strDeleteSql;
		Iterator<String> key;
		Object[] insertParam;
		Object[] deleteParam;
		SimpleDateFormat sdfDate;
		dateNow=new Date();
		param=new Object[1];
		conn=null;
		sdfDate=new SimpleDateFormat("yyyy-MM-dd");
		log.info("RestPOSLogQuartzJob start");
		jobDetail=context.getJobDetail();
		dataMap = jobDetail.getJobDataMap();
		log.info("jobDetail Group:"+jobDetail.getKey().getGroup());
		log.info("jobDetail Name:"+jobDetail.getKey().getName());
		strInsertSql="INSERT INTO hislog(id,uid,times,description,money,cid,TYPE,dates,restaurantId,posId,posdate)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		strDeleteSql="delete log from log where id=?";
		if(null!=dataMap)
		{
			log.info("jobDetail Map:"+dataMap.toString());
			key=dataMap.keySet().iterator();
			while(key.hasNext())
			{
				strKey=key.next();
				strValue=dataMap.getString(strKey);
				log.info("MapKey:"+strKey);
				log.info("MapValue:"+strValue);
			}
			strEatDate=sdfDate.format(dateNow);
			param[0]=strEatDate;
			strSqlInfo="SELECT id,uid,times,description,money,cid,type,dates,restaurantId,posId,posdate FROM LOG WHERE dates<=?";
			try {
				ds=commService.getDataSource("rest");
				list=commService.queryListSql("rest", strSqlInfo, param);
				ilistSize=list.size();
				qr = new QueryRunner(ds);
				conn=null;
				conn=ds.getConnection();
				conn.setAutoCommit(false);
				for(int i=0;i<ilistSize;i++)
				{
					mapInfo=list.get(i);
					deleteParam=new Object[1];
					deleteParam[0]=mapInfo.get("id");
					insertParam=new Object[11];
					insertParam[0]=mapInfo.get("id");
					insertParam[1]=mapInfo.get("uid");
					insertParam[2]=mapInfo.get("times");
					insertParam[3]=mapInfo.get("description");
					insertParam[4]=mapInfo.get("money");
					insertParam[5]=mapInfo.get("cid");
					insertParam[6]=mapInfo.get("type");
					insertParam[7]=mapInfo.get("dates");
					insertParam[8]=mapInfo.get("restaurantId");
					insertParam[9]=mapInfo.get("posId");
					insertParam[10]=mapInfo.get("posdate");
					qr.update(conn,strInsertSql, insertParam);
					qr.update(conn,strDeleteSql,deleteParam);
				}
				conn.commit();
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				if(null!=conn)
				{
					log.error("-----------------execNoListSqlByTran error begin---------------------");
					log.error(e.getMessage(),e);
					log.error("-----------------error end-------------------------------------");
					try {
						DbUtils.rollback(conn);
					} catch (SQLException el) {
						log.error(el.getMessage(),el);
					}
				}
			}
			finally{
				DbUtils.closeQuietly(conn);
			}
			
		}
		log.info("RestPOSLogQuartzJob end");
	}
}
