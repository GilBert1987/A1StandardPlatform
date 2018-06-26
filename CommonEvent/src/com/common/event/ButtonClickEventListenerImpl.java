package com.common.event;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

import com.common.entity.firebutton.Button;
import com.common.entity.firebutton.RelObjInfo;
import com.common.factory.ReflectFactory;
import com.common.service.LocalCommonService;

public class ButtonClickEventListenerImpl implements IButtonClickEventListener{
	
	private static Logger log = Logger.getLogger(ButtonClickEventListenerImpl.class);
	
	//日期类型的转换
	private SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	private Date convertSTD(String date){
		try {
			return simpleDateFormate.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
		
	public String convertDTS(Date date){
		return simpleDateFormate.format(date);
	}

	/**
	 * @param <T>
	 * @param value
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T convertGt(String value, Class<T> clazz) throws Exception {
		Object retObj;
		if (value == null) { // 如果值为null,则返回null
			retObj = null;
		}
		else
		{
			if (value.equals("") && !clazz.getName().equals(String.class.getName())) {
				// 如果value值为"",而且要转为的类型不是string类型，那么就统一返回null，也就是空字符串不能转成任何其他类型的实体，只能返回null
				retObj = null;
			} 
			else
			{
				if (Date.class.getName().equalsIgnoreCase(clazz.getName())) {
					// 增加对从String类型到Date
					retObj = (T) convertSTD(value);
				}
				else
				{
					retObj=ConvertUtils.convert(value, clazz);
				}
			}
		}
		return (T) retObj;
	}

	
	public void runButtonClick(ButtonClickEvent event){
		LocalCommonService localcomService;
		ButtonEventMessage message;
		Button btnInfo;
		Object obj;
		Object retObj;
		Object[] args;
		Class[] classlist;
		Set set;
		Iterator iter;
		String strkey;
		String strParam;
		Method method;
		net.sf.json.JSONArray arrayJsonObj;
		List<RelObjInfo> fireFuction;
		List<Map<String, String>> parmlist;
		Map<String,String> parm;
		Map requestParam;
		Iterator<RelObjInfo> iterRelobj;
		RelObjInfo relObj;
		message=event.getMessage();
		btnInfo=message.getBtninfo();
		classlist=null;
		obj=null;
		relObj=null;
		args=null;
		if(null!=btnInfo)
		{
			fireFuction=btnInfo.getFireFuction();
			iterRelobj=fireFuction.iterator();
			localcomService=(LocalCommonService)ReflectFactory.getObjInstance("com.common.service.LocalCommonService", message.getRequest());
			requestParam=localcomService.getMapByRequest(message.getRequest());
			while(iterRelobj.hasNext())
			{
				try{
					relObj=iterRelobj.next();
					obj=ReflectFactory.getObjInstance(relObj.getClassName(),message.getRequest());
					parmlist=relObj.getList();
					args=new Object[parmlist.size()];
					classlist=new Class[parmlist.size()];
					for(int i=0;i<parmlist.size();i++)
					{
						parm=parmlist.get(i);
						set=parm.keySet();
						iter = set.iterator();
						while (iter.hasNext()) {
							strkey=(String)iter.next();
							try {
								classlist[i]=Class.forName(strkey);
							} catch (ClassNotFoundException e) {
								log.error(e);
							}
							if("javax.servlet.http.HttpServletRequest".toLowerCase().equals(strkey.toLowerCase())==true){
								args[i]=message.getRequest();
							}
							if("javax.servlet.http.HttpServletResponse".toLowerCase().equals(strkey.toLowerCase())==true){
								args[i]=message.getResponse();
							}
							if("java.util.Map".toLowerCase().equals(strkey.toLowerCase())==true){
								args[i]=requestParam;
							}
							if(args[i]==null)
							{
								strParam=parm.get(strkey);
								strParam=localcomService.getUrlInfoByMap(strParam, requestParam);
								strParam=localcomService.getUrlInfo(strParam, message.getRequest(),message.getResponse());
								if(classlist[i].getName().startsWith("[L")==true && classlist[i].getName().endsWith(";")==true)
								{
									arrayJsonObj=net.sf.json.JSONArray.fromObject(strParam);
									args[i]=arrayJsonObj.toArray();
								}
								else
								{
									args[i]=convertGt(strParam,classlist[i]);
								}
							}
						}
					}
					method=ReflectFactory.getMethod(obj, relObj.getMethod(),classlist);
					retObj=ReflectFactory.invoke(obj, method, args);
					if(null != retObj && retObj instanceof Map<?,?>)
					{
						requestParam=(Map<String,Object[]>)retObj;
					}
				}
				catch(Throwable e){
					if(null!=obj)
					{
						log.error(obj.toString());
					}
					else
					{
						log.error("obj is null");
					}
					if(null!=relObj)
					{
						log.error(relObj.getMethod());
					}
					else
					{
						log.error("relObj is null");
					}
					if(null!=classlist){
						for(int i=0;i<classlist.length;i++)
						{
							log.error(classlist[i].toString());
						}
					}
					else
					{
						log.error("classlist is null");
					}
					if(null!=args){
						for(int i=0;i<args.length;i++)
						{
							log.error(args[i].toString());
						}
					}
					else
					{
						log.error("args is null");
					}
					log.error(e.toString(),e);
				}
			}
		}
	}
	
}
