package com.common.quartz.job.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzTestJob extends QuartzJobBean {
	
	private static Logger log = Logger.getLogger(QuartzTestJob.class);
	
	/**
	* 要调度的具体任务
	*/
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		String strKey;
		String strValue;
		JobDetail jobDetail;
		JobDataMap dataMap;
		Iterator<String> key;
		log.info("test job start");
		jobDetail=context.getJobDetail();
		dataMap = jobDetail.getJobDataMap();
		log.info("jobDetail Group:"+jobDetail.getKey().getGroup());
		log.info("jobDetail Name:"+jobDetail.getKey().getName());
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
		}
		log.info("test job end");
	}
}
