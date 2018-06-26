package com.common.quartz.job.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {
	
	private static Logger log = Logger.getLogger(TestJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
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
