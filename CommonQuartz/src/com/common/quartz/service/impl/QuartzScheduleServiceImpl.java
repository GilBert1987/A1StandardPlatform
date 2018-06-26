package com.common.quartz.service.impl;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import com.common.quartz.entity.QuartzSchedule;
import com.common.service.CommonService;
import com.common.service.QuartzScheduleService;
import com.common.unit.JsonUnit;

@Service
@Path("QuartzScheduleService")
public class QuartzScheduleServiceImpl implements QuartzScheduleService {
	
	@Resource
	private Scheduler scheduler;
	
	private CommonService commService;
	
	private static Logger log = Logger.getLogger(QuartzScheduleServiceImpl.class);
	
	public CommonService getCommService() {
		return commService;
	}

	public void setCommService(CommonService commService) {
		this.commService = commService;
	}

	@Override
	public void saveQuartz(Map<String,Object[]> map,String userId){
		String strGroup;
		String strName;
		String strExecutor;
		String strParamInfo;
		String strSecond;
		String strMinute;
		String strHour;
		String strDay;
		String strMonth;
		String strWeek;
		String strYear;
		String strDescription;
		QuartzSchedule entity;
		Map<String,Object> mapParam;
		entity=new QuartzSchedule();
		mapParam=new HashMap<String,Object>();
		strGroup=map.get("map.JOB_GROUP")[0].toString();
		strName=map.get("map.JOB_NAME")[0].toString();
		strExecutor=map.get("map.executor")[0].toString();
		strParamInfo=map.get("map.paramInfo")[0].toString();
		strSecond=map.get("map.v_second")[0].toString();
		strMinute=map.get("map.v_min")[0].toString();
		strHour=map.get("map.v_hour")[0].toString();
		strDay=map.get("map.v_day")[0].toString();
		strMonth=map.get("map.v_mouth")[0].toString();
		strWeek=map.get("map.v_week")[0].toString();
		strYear=map.get("map.v_year")[0].toString();
		strDescription=map.get("map.Description")[0].toString();
		if("".equals(strParamInfo)==false)
		{
			mapParam=JsonUnit.parseJSONToMap(strParamInfo);
		}
		entity.setGroup(strGroup);
		entity.setName(strName);
		entity.setExecutor(strExecutor);
		entity.setMap(mapParam);
		entity.setSecond(strSecond);
		entity.setMinute(strMinute);
		entity.setHour(strHour);
		entity.setDay(strDay);
		entity.setMonth(strMonth);
		entity.setWeek(strWeek);
		entity.setYear(strYear);
		entity.setDescription(strDescription);
		save(entity,userId);
	}
	
	@Override
	public void deleteQuartz(String name,String group,String userId){
		delete(name,group);
	}
	
	@Override
	public void oneExecuteQuartz(String name,String group,String userId){
		oneExecute(name,group,userId);
	}
	
	@Override
	public void pauseQuartz(String name,String group){
		pause(name,group);
	}
	
	@Override
	public void resumeQuartz(String name,String group){
		 resume(name,group);
	}
	
	private Boolean save(QuartzSchedule entity,String userId){
		Trigger trigger;
		boolean isValid;
		JobDataMap map;
		Iterator<?> it;
		Class jobClass;
		JobDetail job;
		Boolean blResult;
		JobBuilder jobBuilder;
		Map.Entry<?, ?> ent;
		TriggerBuilder<Trigger> trigBuilder;
		String cronExpression;
		blResult=false;
		if (null != entity && null != entity.getName() && !"".equals(entity.getName()) && null != entity.getGroup() && !"".equals(entity.getGroup())) {
			cronExpression = String.format("%s %s %s %s %s %s %s",
					entity.getSecond(), entity.getMinute(),
					entity.getHour(), entity.getDay(), entity.getMonth(),
					entity.getWeek(),entity.getYear());
			isValid = CronExpression.isValidExpression(cronExpression);
			if (!isValid) {
				log.error("cronExpression error cronExpression:"+cronExpression);
			}
			else
			{
				trigBuilder=newTrigger();
				trigBuilder.withIdentity(entity.getName(), entity.getGroup());
				trigBuilder.withSchedule(cronSchedule(cronExpression));
				trigBuilder.withDescription(entity.getDescription());
				trigger = trigBuilder.build();
				try {
					jobClass = Class.forName(entity.getExecutor());
					jobBuilder = newJob(jobClass);
					jobBuilder.withIdentity(entity.getName(), entity.getGroup());
					jobBuilder.withDescription(entity.getDescription());
					job = jobBuilder.build();
					map = job.getJobDataMap();
					it = entity.getMap().entrySet().iterator();
					while (it.hasNext()) {
						ent = (Entry<?, ?>) it.next();
						map.put((String) ent.getKey(), ent.getValue());
					}
					if ("".equals(delete(entity.getName(),entity.getGroup()))==false) {
						updateJobLog(entity.getName(),entity.getGroup(),userId);
					}
					else
					{
						insertJobLog(entity.getName(),entity.getGroup(),userId);
					}
					scheduler.scheduleJob(job, trigger);
					blResult=true;
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage(),e);
				} catch (SchedulerException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return blResult;
	}
	
	private void insertJobLog(String name, String group, String userId) {
		log.info("Quartz Insert:Group "+group+" Name "+name+" UserID "+userId);
	}

	private void updateJobLog(String name, String group, String userId) {
		log.info("Quartz Update:Group "+group+" Name "+name+" UserID "+userId);
	}

	private String delete(String name,String group){
		JobKey key;
		String strKey;
		strKey="";
		key=null;
		try {
			key = JobKey.jobKey(name, group);
			scheduler.deleteJob(key);
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
		if(key!=null)
		{
			strKey=key.toString();
		}
		return strKey;
	}
	
	@Override
	public JSONObject getQuartz(String group,String name){
		QuartzSchedule entity;
		JobDetail jobDetail;
		List<Map> mapResult;
		String strCronExpress;
		JSONObject jsonObj;
		Object[] params;
		Map<String,Object> mapInfo;
		String[] strArr;
		String strSql;
		jsonObj=null;
		mapResult=null;
		entity=new QuartzSchedule();
		try {
			entity.setGroup(group);
			entity.setName(name);
			jobDetail=scheduler.getJobDetail(JobKey.jobKey(name,group));
			entity.setMap(jobDetail.getJobDataMap());
			entity.setDescription(jobDetail.getDescription());
			entity.setExecutor(jobDetail.getJobClass().toString().replace("class", ""));
			params=new Object[]{
				group,
				name
			};
			strSql="SELECT CRON_EXPRESSION FROM qrtz_cron_triggers WHERE TRIGGER_GROUP=? AND TRIGGER_NAME=?";
			mapResult=commService.queryListSql("qz", strSql,params);
			if(1==mapResult.size())
			{
				mapInfo=mapResult.get(0);
				strCronExpress=mapInfo.get("CRON_EXPRESSION").toString();
				strArr=strCronExpress.split(" ");
				if(strArr.length>=6)
				{
					entity.setSecond(strArr[0]);
				}
				if(strArr.length>=6)
				{
					entity.setMinute(strArr[1]);
				}
				if(strArr.length>=6)
				{
					entity.setHour(strArr[2]);
				}
				if(strArr.length>=6)
				{
					entity.setDay(strArr[3]);
				}
				if(strArr.length>=6)
				{
					entity.setMonth(strArr[4]);
				}
				if(strArr.length>=6)
				{
					entity.setWeek(strArr[5]);
				}
				if(strArr.length==7)
				{
					entity.setYear(strArr[6]);
				}
				jsonObj=JSONObject.fromObject(entity);
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return jsonObj;
	}
	
	@Override
	public String getNewQuartzName(String group){
		return JobKey.createUniqueName(group).replace("-", "");
	}
	
	/*
	private Map<String, Object> select(QuartzSchedule entity){
		JobDetail jobDetail;
		String[] keyArray;
		String params;
		Trigger trigger;
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		keyArray = entity.getId().split("\\.");
		try
		{
			jobDetail = scheduler.getJobDetail(JobKey.jobKey(keyArray[1],keyArray[0]));
			params = JSONArray.fromObject(jobDetail.getJobDataMap()).toString();
			trigger = scheduler.getTrigger(TriggerKey.triggerKey(keyArray[1], keyArray[0]));
			map.put("params", params);
			map.put("jobDetail", jobDetail);
			map.put("trigger", trigger);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return map;
	}
	*/
	
	private String oneExecute(String name,String group,String userId){
		JobKey key;
		JobDetail jobDetail;
		key = null;
		try {
			key = JobKey.jobKey(name, group);
			jobDetail = scheduler.getJobDetail(key);
			scheduler.triggerJob(key,jobDetail.getJobDataMap());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return key.toString();
	}
	
	private String pause(String name,String group){
		JobKey key;
		String strKey;
		strKey="";
		key=null;
		try {
			key = JobKey.jobKey(name, group);
			scheduler.pauseJob(key);
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
		if(key!=null)
		{
			strKey=key.toString();
		}
		return strKey;
	}
	
	private String resume(String name,String group){
		JobKey key;
		String strKey;
		strKey="";
		key=null;
		try {
			key = JobKey.jobKey(name, group);
			scheduler.resumeJob(key);
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
		if(key!=null)
		{
			strKey=key.toString();
		}
		return strKey;
	}
}
