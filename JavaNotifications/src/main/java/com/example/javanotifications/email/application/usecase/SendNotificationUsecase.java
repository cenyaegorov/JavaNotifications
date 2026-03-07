package com.example.javanotifications.email.application.usecase;

import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.in.SendNotificationUsecasePort;
import com.example.javanotifications.email.application.port.out.EventPublisher;
import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.application.port.out.repositories.OutboxRepository;
import com.example.javanotifications.email.domain.notification.Notification;
import com.example.javanotifications.email.domain.outbox.OutboxEvent;

import jakarta.transaction.Transactional;

public class SendNotificationUsecase implements SendNotificationUsecasePort {
	
	private final NotificationRepository notificationRepository;
	private final OutboxRepository outboxRepository;
	
	public SendNotificationUsecase(NotificationRepository notificationRepository, OutboxRepository outboxRepository) {
		this.notificationRepository = notificationRepository;
		this.outboxRepository = outboxRepository;
	}

	@Override
	@Transactional
	public boolean execute(SendNotificationCommand command) {
		Notification notification = new Notification(command.requestId(), command.email(), command.payload());
		OutboxEvent outboxEvent = new OutboxEvent(command.requestId());
		notificationRepository.save(notification);
		outboxRepository.saveEvent(outboxEvent);
		return true;
	}

}
