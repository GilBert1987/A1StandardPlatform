package com.common.jms.customer.queue;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.common.entity.query.Query;
import com.common.entity.query.RowInfo;
import com.common.service.CommonService;
import com.common.service.QueueSenderService;

/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component("excelReceiver2")
public class ExcelReceiver2 implements MessageListener {
	
	private static Logger log = Logger.getLogger(ExcelReceiver2.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private QueueSenderService queueSender;
	
	@Override
	public void onMessage(Message message) {
		String strMsg;
		String strKey;
		Map[] subList;
		Query queryObj;
		String strCount;
		Object[] params;
		String strWhere;
		String strSqlInfo;
		String strCurrkey;
		String strQueryId;
		String strTitleId;
		String strJsonInfo;
		JSONObject jsonObj;
		JSONArray jsonParams;
		Object[] queryParams;
		JSONArray jsonExtends;
		JSONArray jsonSubList;
		List<RowInfo> listRow;
		Object[] updateParams;
		String strSqlInfoList;
		List<Map> listSubArray;
		List<Map> listQueryData;
		JSONArray jsonExtendCol;
		StringBuilder strSqlBuilder;
		JSONArray jsonExtendCountCol;
		Map<String, Object> mapQueryInfo;
		params=new Object[2];
		strKey="";
		queryParams=new Object[1];
		strSqlBuilder=new StringBuilder();
		updateParams=new Object[1];
		try{
			strJsonInfo=((TextMessage)message).getText();
			jsonObj=JSONObject.fromObject(strJsonInfo);
			strQueryId=jsonObj.getString("queryid");
			strTitleId=jsonObj.getString("titleid");
			strKey=jsonObj.getString("key");
			strSqlInfoList=jsonObj.getString("sql");
			strCurrkey=jsonObj.getString("currkey");
			strWhere=jsonObj.getString("where");
			jsonSubList=jsonObj.getJSONArray("subList");
			jsonExtends=jsonObj.getJSONArray("extend");
			jsonExtendCol=jsonObj.getJSONArray("extendcol");
			jsonParams=jsonObj.getJSONArray("listParams");
			jsonExtendCountCol=jsonObj.getJSONArray("extendcountcol");
			queryObj=(Query)commonService.loadRedisObj(strQueryId);
			params[0]=2;
			params[1]=0;
			commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
			subList=fileLoadByKey(strCurrkey);
			if(null!=subList)
			{
				listSubArray=Arrays.asList(subList);
				listRow=commonService.getQueryList(1, queryObj, listSubArray);
				saveFileBy(listRow, strCurrkey);
				updateParams[0]=1;
				commonService.updateObj("com", "sc_exportexcel", "se_status", updateParams, "and id='"+strCurrkey+"'");
				strSqlBuilder.append("SELECT COUNT(*) AS countInfo ");
				strSqlBuilder.append("FROM sc_exportexcel ");
				strSqlBuilder.append("WHERE se_key=? AND se_status=1");
				queryParams[0]=strKey;
				listQueryData=commonService.queryListSql("com", strSqlBuilder.toString(), queryParams);
				params[0]=2;
				params[1]=1;
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				if(null!=listQueryData && 1==listQueryData.size())
				{
					mapQueryInfo=listQueryData.get(0);
					strCount=mapQueryInfo.get("countInfo").toString();
					if(jsonSubList.size()==Integer.parseInt(strCount)){
						params[0]=3;
						params[1]=0;
						commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
						strMsg="{\"sql\":\""+strSqlInfoList+"\",\"where\":\""+strWhere+"\",\"queryid\":\""+strQueryId+"\",\"subList\":"+jsonSubList.toString()+",\"titleid\":\""+strTitleId+"\",\"key\":\""+strKey+"\",\"extend\":"+jsonExtends.toString()+",\"extendcol\":"+jsonExtendCol.toString()+",\"extendcountcol\":"+jsonExtendCountCol.toString()+",\"listParams\":"+jsonParams.toString()+"}";
						queueSender.send("3", strMsg);
						params[0]=3;
						params[1]=1;
						commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
					}
				}
			}
			else
			{
				params[0]=0;
				params[1]=-1;
				try {
					strSqlInfo="DELETE sc_exportexcel FROM sc_exportexcel WHERE se_key='"+strKey+"' ";
					commonService.execNoListSql("com", strSqlInfo, null);
					commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				} catch (Exception e1) {
					log.error(e1.toString(),e1);
				}
			}
		}
		catch (Exception e) {
			params[0]=0;
			params[1]=-1;
			try {
				strSqlInfo="DELETE sc_exportexcel FROM sc_exportexcel WHERE se_key='"+strKey+"' ";
				commonService.execNoListSql("com", strSqlInfo, null);
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
			} catch (Exception e1) {
				log.error(e1.toString(),e1);
			}
			log.error(e.toString(),e);
		}
	}

	private Map[] fileLoadByKey(String strRedisKey) {
		Map[] list;
		File fileInfo;
		HessianInput hinput;
		FileInputStream fileInStream;
		BufferedInputStream bufferedIn;
		list=null;
		bufferedIn=null;
		fileInStream=null;
		try
		{
			fileInfo=new File("../tmp/"+strRedisKey+".bin");
			fileInStream=new FileInputStream(fileInfo);
			bufferedIn=new BufferedInputStream(fileInStream);
			hinput = new HessianInput(bufferedIn);  
			list=(Map[]) hinput.readObject();
		}
		catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		finally{
			IOUtils.closeQuietly(bufferedIn);
			IOUtils.closeQuietly(fileInStream);
		}
		return list;
	}
	
	private void saveFileBy(List<RowInfo> list, String strRedisKey) {
		File fileInfo;
		HessianOutput ho;
		FileOutputStream fos;
		ByteArrayOutputStream bos;
		ho=null;
		fos=null;
		bos=null;
		try
		{
			fileInfo=new File("../tmp/"+strRedisKey+".bin");
			fos=new FileOutputStream(fileInfo);
			bos=new ByteArrayOutputStream();
			ho = new HessianOutput(bos); 
			ho.writeObject(list);
			ho.flush();
			bos.writeTo(fos);
			bos.flush();
		}
		catch(Exception ee){
			log.error(ee.toString(),ee);
		}
		finally{
			if(null!=ho)
			{
				try {
					ho.close();
				} catch (IOException e) {
					log.error(e.toString(),e);
				}
			}
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
		}
	}
}
