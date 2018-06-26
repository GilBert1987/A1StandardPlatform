package com.platform.data;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;

public class SystemPropertyDataLoad implements IFormDataLoad {
	
	private static Logger log = Logger.getLogger(SystemPropertyDataLoad.class);
	
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String sysid;
		String strId;
		String strSql;
		Object[] objArr;
		List<Map> listMap;
		CommonService commService;
		commService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		strSql="SELECT CC_ID,CC_Name,CC_Index,CC_ShortName,CC_Remark,CC_ParentID,CCT_ID ";
		strSql+="FROM sc_commoncode where CC_ID=? ";
		sysid=request.getParameter("sysid");
		strId=request.getParameter("id");
		if(null!=sysid && null!=strId)
		{
			listMap=null;
			objArr=new Object[]{strId};
			try {
				listMap=commService.queryListSql(sysid, strSql, objArr);
			} catch (Exception e) {
				log.error(e);
			}
			request.setAttribute("_dataInfo", listMap);
		}
	}

}
