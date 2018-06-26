package com.platform.data;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.common.factory.ReflectFactory;
import com.common.iface.IFormDataLoad;
import com.common.service.CommonService;

public class BigDataLoad implements IFormDataLoad {
	
	private static Logger log = Logger.getLogger(BigDataLoad.class);
	@Override
	public void setFormDataLoad(HttpServletRequest request,HttpServletResponse response)throws ServletException {
		String strSql;
		Map<String, Object> mapInfo;
		CommonService commonService;
		mapInfo=null;
		commonService=(CommonService)ReflectFactory.getObjInstance("com.common.service.CommonService", request);
		strSql="select count(*) as numCount from ( SELECT USER_DEFINED_FIELD1,USER_DEFINED_FIELD3,UPD_DATE,UPD_USER,LEAF_CATEG_ID,SITE_ID,META_CATEG_NAME,CATEG_LVL2_NAME,CATEG_LVL3_NAME FROM KYLIN_CATEGORY_GROUPINGS ) as t where (1=1)";
		try {
			mapInfo=commonService.queryFullSql("kylin", strSql, null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		request.setAttribute("list", mapInfo);
	}

}
