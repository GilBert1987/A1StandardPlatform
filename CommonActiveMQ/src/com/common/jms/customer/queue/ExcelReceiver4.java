package com.common.jms.customer.queue;

import java.io.File;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.common.ftp.util.FTPUtil;
import com.common.service.CommonService;
import com.common.service.QueueSenderService;

/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component("excelReceiver4")
public class ExcelReceiver4 implements MessageListener {
	
	private static Logger log = Logger.getLogger(ExcelReceiver4.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private QueueSenderService queueSender;
	
	@Resource
	private FTPUtil ftpUtil;
	
	@Override
	public void onMessage(Message message) {
		File outfile;
		File[] files;
		String strMsg;
		String strKey;
		Object[] params;
		String strSqlInfo;
		String strFileName;
		JSONObject jsonMsg;
		strKey="";
		outfile=null;
		files=new File[1];
		params=new Object[2];
		try {
			strMsg=((TextMessage)message).getText();
			if("".equals(strMsg)==false)
			{
				jsonMsg=JSONObject.fromObject(strMsg);
				strKey=jsonMsg.getString("key");
				strFileName=jsonMsg.getString("filename");
				outfile=new File("../tmp/"+strFileName);
				files[0]=outfile;
				params[0]=5;
				params[1]=0;
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				ftpUtil.uploadFile(files, "/", "/exportExcel");
				params[0]=5;
				params[1]=1;
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				strSqlInfo="DELETE sc_exportexcel FROM sc_exportexcel WHERE se_key='"+strKey+"' ";
				commonService.execNoListSql("com", strSqlInfo, null);
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
		finally{
			if(null!=outfile)
			{
				outfile.delete();
			}
		}
	}

}
