package com.common.quartz.job.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FileCommandQuartzJob extends QuartzJobBean {
	
	private static Logger log = Logger.getLogger(FileCommandQuartzJob.class);
	
	/**
	* 要调度的具体任务
	*/
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		String strKey;
		String strValue;
		String strCmd;
		JobDetail jobDetail;
		JobDataMap dataMap;
		Iterator<String> key;
		strCmd="";
		log.info("FileCommandQuartzJob job start");
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
				if("cmd".equals(strKey)){
					strCmd=strValue;
				}
				log.info("MapKey:"+strKey);
				log.info("MapValue:"+strValue);
			}
			if("".equals(strCmd)==false)
			{
				run_cmd(strCmd);
			}
		}
		log.info("FileCommandQuartzJob job end");
	}
	
	public void run_cmd(String strcmd) {
		int i;
		Runtime rt;
		Process ps;
		rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
		ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
		try {
			ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
			ps.waitFor();  //等待子进程完成再往下执行。
		} catch (Exception e){
			log.error(e.getMessage(),e);
		}
		i = ps.exitValue();  //接收执行完毕的返回值
		if (i == 0) {
			log.info("命令执行完成");
		} else {
			log.info("执行失败.");
		}
		ps.destroy();  //销毁子进程
		ps = null;   
	}
}
