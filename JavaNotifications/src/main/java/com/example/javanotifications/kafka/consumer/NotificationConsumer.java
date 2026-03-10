package com.example.javanotifications.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.javanotifications.kafka.application.ProcessNotificationUseCasePort;
import com.example.javanotifications.outbox.dto.NotificationPayload;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class NotificationConsumer {
	private final ProcessNotificationUseCasePort processNotification;
	private final ObjectMapper mapper;
	
	public NotificationConsumer(ProcessNotificationUseCasePort processNotification, ObjectMapper mapper) {
		this.processNotification = processNotification;
		this.mapper = mapper;
	}
	
	@KafkaListener(topics = "notifications")
	public void consume(NotificationPayload message) {
		try {
			processNotification.execute(message);
		}
		catch (Exception e) {
			log.error("Failed to process message, {}", message.toString(), e);
		}
	}

}
