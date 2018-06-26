package com.common.service;

public interface TopicSenderService {

	void send(String topicName,final String message);
	
}
