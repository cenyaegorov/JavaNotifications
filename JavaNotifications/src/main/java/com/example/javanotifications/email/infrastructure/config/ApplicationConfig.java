package com.example.javanotifications.email.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.application.port.out.repositories.OutboxRepository;
import com.example.javanotifications.email.application.usecase.SendNotificationUsecase;
import com.example.javanotifications.outbox.infrastructure.persistence.PostgresOutboxRepository;

@Configuration
public class ApplicationConfig {
	@Bean
	public SendNotificationUsecase sendNotificationUsecase(NotificationRepository notificationRepository) {
		OutboxRepository outboxRepository = new PostgresOutboxRepository();
		return new SendNotificationUsecase(notificationRepository, outboxRepository);
	}

}
