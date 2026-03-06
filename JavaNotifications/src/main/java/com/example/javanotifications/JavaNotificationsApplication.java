package com.example.javanotifications;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.out.NotificationRepository;
import com.example.javanotifications.email.application.port.out.OutboxRepository;
import com.example.javanotifications.email.application.usecase.SendNotificationUsecase;
import com.example.javanotifications.outbox.infrastructure.persistence.PostgresNotificationRepository;
import com.example.javanotifications.outbox.infrastructure.persistence.PostgresOutboxRepository;

@SpringBootApplication
public class JavaNotificationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaNotificationsApplication.class, args);
		SendNotificationCommand command = new SendNotificationCommand(UUID.randomUUID(), "email", "text");
		NotificationRepository notificationRepository = new PostgresNotificationRepository();
		OutboxRepository outboxRepository = new PostgresOutboxRepository();
		SendNotificationUsecase sendUseCase = new SendNotificationUsecase(notificationRepository, outboxRepository);
		//sendUseCase.execute(command);
	}

}
