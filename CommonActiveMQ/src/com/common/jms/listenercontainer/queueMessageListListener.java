package com.common.jms.listenercontainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.common.service.CommonService;
import com.common.tool.ApplicationContextHelper;
import com.common.unit.ComputerInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class queueMessageListListener {
	
	@Resource 
	private CommonService commService;
	
	private String servertype;
	
	private Map<String,Connection> mapconnection=new HashMap<String,Connection>();
	private Map<String,Session> mapsession=new HashMap<String,Session>();
	private Map<String,MessageConsumer> mapconsumer=new HashMap<String,MessageConsumer>();
	
	public String getServertype() {
		return servertype;
	}

	public void setServertype(String servertype) {
		this.servertype = servertype;
	}
	
	public void init()
	{
		String id;
		String name;
		Map mapInfo;
		Map mapLink;
		Queue queue;
		String strSql;
		String strLink;
		List<Map> list;
		Object objInfo;
		String listname;
		Object[] params;
		Session session;
		Connection conn;
		String classinfo;
		String strTrInfo;
		List<Map> listInfo;
		String computerinfo;
		MessageConsumer consumer;
		Map<String,String> ipMapInfo;
		ConnectionFactory connFactory;
		name="";
		session=null;
		listname=null;
		classinfo=null;
		listInfo=null;
		computerinfo=null;
		params=new Object[3];
		strSql ="SELECT id,ifnull(ac_name,'') as name,ifnull(ac_link,'') as link,ifnull(ac_trInfo,0) as trInfo ";
		strSql+="FROM activemq_computer ";
		strSql+="WHERE ac_state='1' ";
		try {
			list=commService.queryListSql("activemq", strSql, null);
			if(null!=list && 0!=list.size())
			{
				for(int i=0;i<list.size();i++)
				{
					mapInfo=list.get(i);
					id=mapInfo.get("id").toString();
					strLink=mapInfo.get("link").toString();
					strTrInfo=mapInfo.get("trInfo").toString();
					connFactory=new ActiveMQConnectionFactory(strLink);
					conn=connFactory.createConnection();
					conn.start();
					mapconnection.put(id, conn);
					if("1".equals(strTrInfo)==true)
					{
						session=conn.createSession(true,Session.AUTO_ACKNOWLEDGE);
					}
					if("0".equals(strTrInfo)==true)
					{
						session=conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
					}
					if(null != session)
					{
						mapsession.put(id, session);
						ipMapInfo=ComputerInfo.getIpconfig();
						if(null!=ipMapInfo.get("COMPUTERNAME"))
						{
							name=ipMapInfo.get("COMPUTERNAME").toString();
						}
						params[0]=servertype;
						params[1]=name;
						params[2]=id;
						strSql ="SELECT CONCAT(IFNULL(computer.ac_name,''),'_',IFNULL(interface.al_servertype,'')) AS computerinfo, ";
						strSql+="IFNULL(interface.al_listname,'') AS listname,IFNULL(interface.al_classinfo,'') AS classinfo ";
						strSql+="FROM activemq_computer AS computer ";
						strSql+="JOIN activemq_interface AS interface ";
						strSql+="ON computer.id=interface.ac_id ";
						strSql+="WHERE interface.al_servertype=? and computer.ac_name=? and computer.id=? and interface.al_destinationtype='queue'";
						try {
							listInfo=commService.queryListSql("activemq", strSql, params);
						} catch (Exception e) {
							log.error(e.getMessage(),e);
						}
						if(null!=listInfo)
						{
							for(int x=0;x<listInfo.size();x++)
							{
								mapLink=listInfo.get(x);
								if(null!=mapLink.get("listname"))
								{
									listname=mapLink.get("listname").toString();
								}
								if(null!=mapLink.get("computerinfo"))
								{
									computerinfo=mapLink.get("computerinfo").toString()+"_queue";
								}
								if(null!=mapLink.get("classinfo"))
								{
									classinfo=mapLink.get("classinfo").toString();
								}
								if(null != listname && null != classinfo && null != computerinfo)
								{
									log.info("=============QueueMSGListener start====================");
									log.info("QueueMSGDestinationName:"+computerinfo+":"+listname);
									log.info("QueueMSGBeanName:"+classinfo);
									queue=session.createQueue(computerinfo+":"+listname);
									objInfo=ApplicationContextHelper.getBean(Class.forName(classinfo));
									if(null!=objInfo)
									{
										consumer=session.createConsumer(queue);
										mapconsumer.put(id+"_"+computerinfo+":"+listname, consumer);
										if(objInfo instanceof MessageListener)
										{
											consumer.setMessageListener((MessageListener) objInfo);
										}
									}
									log.info("=============QueueMSGListener end====================");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void destroy(){
		Connection conn;
		Session session;
		MessageConsumer consumer;
		for (String in : mapconsumer.keySet()) {
			consumer=mapconsumer.get(in);
			try {
				consumer.close();
			} catch (JMSException e) {
				log.error(e.getMessage(),e);
			}
		}
		for (String in : mapsession.keySet()) {
			session=mapsession.get(in);
			try {
				session.close();
			} catch (JMSException e) {
				log.error(e.getMessage(),e);
			}
		}
		for (String in : mapconnection.keySet()) {
			conn=mapconnection.get(in);
			try {
				conn.close();
			} catch (JMSException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
}
