package com.common.service;

import java.util.Map;

import net.sf.json.JSONObject;

public interface QuartzScheduleService {

	void deleteQuartz(String name, String group, String userId);

	void oneExecuteQuartz(String name, String group, String userId);

	void pauseQuartz(String name, String group);

	void resumeQuartz(String name, String group);

	JSONObject getQuartz(String group, String name);

	String getNewQuartzName(String group);

	void saveQuartz(Map<String,Object[]> map,String userId);

}
