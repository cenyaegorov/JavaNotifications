package com.example.javanotifications.email.application.usecase;

import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.in.SendNotificationUsecasePort;
import com.example.javanotifications.email.application.port.out.EventPublisher;
import com.example.javanotifications.email.application.port.out.NotificationRepository;
import com.example.javanotifications.email.domain.notification.Notification;

public class SendNotificationUsecase implements SendNotificationUsecasePort {
	
	private final NotificationRepository repository;
	private final EventPublisher publisher;
	
	public SendNotificationUsecase(NotificationRepository repository, EventPublisher publisher) {
		this.repository = repository;
		this.publisher = publisher;
	}

	@Override
	public void execute(SendNotificationCommand command) {
		Notification notification = new Notification(command.requestId(), command.email());
		repository.save(notification);
		publisher.publishNotification(notification);
	}

}
