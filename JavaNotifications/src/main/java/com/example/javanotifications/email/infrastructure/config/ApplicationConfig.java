package com.example.javanotifications.email.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.email.application.usecase.SendNotificationUsecase;

@Configuration
public class ApplicationConfig {
	@Bean
	public SendNotificationUsecase sendNotificationUsecase(NotificationRepository notificationRepository, OutboxEventRepository outboxRepository) {
		return new SendNotificationUsecase(notificationRepository, outboxRepository);
	}

}
