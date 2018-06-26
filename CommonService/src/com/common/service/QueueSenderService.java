package com.common.service;

public interface QueueSenderService {
	
	void send(String queueName,final String message);
	
}
