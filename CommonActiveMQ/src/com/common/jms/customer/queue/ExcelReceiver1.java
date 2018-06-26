package com.common.jms.customer.queue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import com.caucho.hessian.io.HessianOutput;
import com.common.service.CommonService;
import com.common.service.QueueSenderService;


/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component("excelReceiver1")
public class ExcelReceiver1 implements MessageListener {

	private static Logger log = Logger.getLogger(ExcelReceiver1.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private QueueSenderService queueSender;
	
	@Override
	public void onMessage(Message message) {
		int modSize;
		int listSize;
		Map[] subList;
		String strKey;
		String strMsg;
		List<Map> list;
		String strSysId;
		String strWhere;
		String strOrder;
		Object[] params;
		Object[] objList;
		String[] strKeys;
		String strQueryId;
		String strTitleId;
		String strSqlInfo;
		String strDatatype;
		String strJsonInfo;
		JSONObject jsonObj;
		String strRedisKey;
		JSONArray jsonParams;
		JSONArray jsonExtend;
		JSONArray jsonExtendCol;
		JSONArray jsonExtendCountCol;
		Object[] insertParams;
		List<Object> listParam;
		StringBuilder strRedisKeys;
		Map<String, Object> mapInfo;
		StringBuilder strSqlInfoBuilder;
		strKey="";
		params=new Object[2];
		insertParams=new Object[3];
		strRedisKeys=new StringBuilder();
		listParam=new ArrayList<Object>();
		strSqlInfoBuilder=new StringBuilder();
		try {
			strJsonInfo=((TextMessage)message).getText();
			if("".equals(strJsonInfo)==false)
			{
				jsonObj=JSONObject.fromObject(strJsonInfo);
				strQueryId=jsonObj.getString("queryid");
				strTitleId=jsonObj.getString("titleid");
				strKey=jsonObj.getString("key");
				strSysId=jsonObj.getString("sysid");
				strDatatype=jsonObj.getString("datatype");
				strSqlInfo=jsonObj.getString("sql");
				strWhere=jsonObj.getString("where");
				strOrder=jsonObj.getString("orderBy");
				jsonExtend=jsonObj.getJSONArray("extend");
				jsonParams=jsonObj.getJSONArray("listParams");
				jsonExtendCol=jsonObj.getJSONArray("extendcol");
				jsonExtendCountCol=jsonObj.getJSONArray("extendcountcol");
				for(int i=0;i<jsonParams.size();i++)
				{
					listParam.add(jsonParams.getString(i));
				}
				if("".equals(strSysId)==false && "".equals(strDatatype)==false && "".equals(strSqlInfo)==false)
				{
					strSqlInfoBuilder.append(strSqlInfo);
					strSqlInfoBuilder.append(" where (1=1) ");
					strSqlInfoBuilder.append(strWhere);
					if("".equals(strOrder)==false)
					{
						strSqlInfoBuilder.append(" order by "+ strOrder);
					}
					if("sql".equals(strDatatype))
					{
						mapInfo=commonService.queryFullSql(strSysId, strSqlInfoBuilder.toString(), listParam.toArray());
						list=(List<Map>)mapInfo.get("retMap");
						listSize=list.size()/10000;
						modSize=list.size()%10000;
						strKeys=new String[listSize+1];
						strRedisKeys.append("[");
						for(int i=0;i<listSize;i++)
						{
							strRedisKey=commonService.getKeybyTabCol("com", "sc_redis", "id");
							objList=Arrays.copyOfRange(list.toArray(), i*10000, ((i+1)*10000));
							subList=new Map[objList.length];
							for(int j=0;j<objList.length;j++)
							{
								subList[j]=(Map)objList[j];
							}
							saveFileBy(subList,strRedisKey);
							strKeys[i]=strRedisKey;
							insertParams[0]=strRedisKey;
							insertParams[1]=0;
							insertParams[2]=strKey;
							commonService.insertObj("com", "sc_exportexcel", "id,se_status,se_key", insertParams);
							if(0 != i)
							{
								strRedisKeys.append(",");
							}
							strRedisKeys.append("{\"key\":\""+strRedisKey+"\"}");
						}
						objList=Arrays.copyOfRange(list.toArray(), listSize*10000, listSize*10000+modSize);
						subList=new Map[objList.length];
						for(int j=0;j<objList.length;j++)
						{
							subList[j]=(Map)objList[j];
						}
						strRedisKey=commonService.getKeybyTabCol("com", "sc_redis", "id");
						saveFileBy(subList,strRedisKey);
						if(0 != listSize)
						{
							strRedisKeys.append(",");
						}
						strRedisKeys.append("{\"key\":\""+strRedisKey+"\"}");
						insertParams[0]=strRedisKey;
						insertParams[1]=0;
						insertParams[2]=strKey;
						commonService.insertObj("com", "sc_exportexcel", "id,se_status,se_key", insertParams);
						strKeys[listSize]=strRedisKey;
						strRedisKeys.append("]");
						params[0]=1;
						params[1]=1;
						commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
						for(int i=0;i<strKeys.length;i++)
						{
							strMsg="{\"sql\":\""+strSqlInfo+"\",\"where\":\""+strWhere+"\",\"queryid\":\""+strQueryId+"\",\"currkey\":\""+strKeys[i]+"\",\"subList\":"+strRedisKeys.toString()+",\"titleid\":\""+strTitleId+"\",\"key\":\""+strKey+"\",\"extend\":"+jsonExtend.toString()+",\"extendcol\":"+jsonExtendCol.toString()+",\"extendcountcol\":"+jsonExtendCountCol.toString()+",\"listParams\":"+jsonParams.toString()+"}";
							queueSender.send("2", strMsg);
						}
					}
				}
			}
		}catch (Exception e) {
			params[0]=0;
			params[1]=-1;
			try {
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
			} catch (Exception e1) {
				log.error(e1.toString(),e1);
			}
			log.error(e.toString(),e);
		}
	}

	private void saveFileBy(Map[] subList, String strRedisKey) {
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
			ho.writeObject(subList);
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
