package com.platform.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.quartz.entity.QuartzSchedule;
import com.common.service.QuartzScheduleService;

public class QuartzDataLoad  implements IFormDataLoad {

	private QuartzScheduleService quartzService;
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response)throws ServletException {
		String strGroup;
		String strName;
		JSONObject jsonObj;
		QuartzSchedule quartzSched;
		strGroup=(request.getParameter("group")==null?"":request.getParameter("group").toString());
		strName=(request.getParameter("name")==null?"":request.getParameter("name").toString());
		quartzService=(QuartzScheduleService)ReflectFactory.getObjInstance("com.common.service.QuartzScheduleService", request);
		if(strGroup.equals("")==false && strName.equals("")==false)
		{
			jsonObj=quartzService.getQuartz(strGroup,strName);
			quartzSched=(QuartzSchedule)JSONObject.toBean(jsonObj, QuartzSchedule.class);
			quartzSched.setParamInfo((JSONObject.fromObject(quartzSched.getMap())).toString());
		}
		else
		{
			quartzSched=new QuartzSchedule();
			strName=quartzService.getNewQuartzName(strGroup);
			quartzSched.setGroup(strGroup);
			quartzSched.setName(strName);
			quartzSched.setDescription("");
			quartzSched.setSecond("0");
			quartzSched.setMinute("*");
			quartzSched.setHour("*");
			quartzSched.setDay("*");
			quartzSched.setMonth("*");
			quartzSched.setWeek("?");
			quartzSched.setYear("");
			quartzSched.setParamInfo("{}");
			quartzSched.setExecutor("");
		}
		request.setAttribute("_quartz", quartzSched);
	}

}
