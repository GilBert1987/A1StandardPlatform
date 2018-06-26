package com.common.jms.producer.topic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.common.service.CommonService;
import com.common.service.TopicSenderService;

/**
 * 
 * @author liang
 * @description   Topic生产者发送消息到Topic
 * 
 */
@Slf4j
@Component("topicSenderService")
public class TopicSenderServiceImpl implements TopicSenderService {
		
	@Resource
	private CommonService commonService;
	
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param topicName 队列名称
	 * @param message 消息内容
	 */
	public void send(String id,final String message){
		Topic topic;
		Map mapInfo;
		String link;
		String strSql;
		String trInfo;
		Object[] params;
		String listname;
		Connection conn;
		Session session;
		String queueName;
		List<Map> listInfo;
		String computerInfo;
		TextMessage textMessage;
		MessageProducer producer;
		ConnectionFactory connFactory;
		listInfo=null;
		session=null;
		conn=null;
		producer=null;
		params=new Object[1];
		strSql="SELECT CONCAT(IFNULL(computer.ac_name,''),'_',IFNULL(send.as_servertype,''),'_',IFNULL(send.as_destinationtype,'')) AS computerInfo, ";
		strSql+="IFNULL(send.as_listname,'') AS listname,ifnull(computer.ac_link,'') as link,ifnull(computer.ac_trInfo,'') as trInfo ";
		strSql+="FROM activemq_send AS send ";
		strSql+="JOIN activemq_computer AS computer ";
		strSql+="ON computer.id=send.ac_id ";
		strSql+="WHERE send.as_destinationtype='queue' AND send.as_id=?";
		params[0]=id;
		try {
			listInfo=commonService.queryListSql("activemq", strSql, params);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(null!=listInfo && 1==listInfo.size())
		{
			mapInfo=listInfo.get(0);
			if(null!=mapInfo.get("computerInfo") && null!=mapInfo.get("listname")){
				computerInfo=mapInfo.get("computerInfo").toString();
				link=mapInfo.get("link").toString();
				trInfo=mapInfo.get("trInfo").toString();
				listname=mapInfo.get("listname").toString();
				queueName=computerInfo+":"+listname;
				connFactory=new ActiveMQConnectionFactory(link);
				try {
					conn=connFactory.createConnection();
					if("1".equals(trInfo)==true)
					{
						session=conn.createSession(true,Session.AUTO_ACKNOWLEDGE);
					}
					if("0".equals(trInfo)==true)
					{
						session=conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
					}
					if(null != session)
					{
						topic=session.createTopic(queueName);
						producer=session.createProducer(topic);
						textMessage=session.createTextMessage(message);
						producer.setDeliveryMode(DeliveryMode.PERSISTENT);
						producer.send(textMessage);
					}
					if("1".equals(trInfo)==true)
					{
						session.commit();
					}
				} catch (JMSException e) {
					log.error(e.getMessage(),e);
				}
				finally{
					if(null!=producer)
					{
						try {
							producer.close();
						} catch (JMSException e) {
							log.error(e.getMessage(),e);
						}
					}
					if(null!=session)
					{
						try {
							session.close();
						} catch (JMSException e) {
							log.error(e.getMessage(),e);
						}
					}
					if(null!=conn)
					{
						try {
							conn.close();
						} catch (JMSException e) {
							log.error(e.getMessage(),e);
						}
					}
				}
			}
		}
	}

}
