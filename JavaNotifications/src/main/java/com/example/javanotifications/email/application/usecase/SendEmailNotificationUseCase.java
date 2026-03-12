package com.example.javanotifications.email.application.usecase;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.in.SendEmailNotificationUseCasePort;
import com.example.javanotifications.email.application.port.out.EventPublisher;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.dto.NotificationPayload;

import jakarta.transaction.Transactional;

public class SendEmailNotificationUseCase implements SendEmailNotificationUseCasePort {
	
	private final NotificationRepository notificationRepository;
	private final OutboxEventRepository outboxRepository;
	
	public SendEmailNotificationUseCase(NotificationRepository notificationRepository, OutboxEventRepository outboxRepository) {
		this.notificationRepository = notificationRepository;
		this.outboxRepository = outboxRepository;
	}

	@Override
	@Transactional
	public boolean execute(SendNotificationCommand command) {
		Notification notification = new Notification(command.requestId(), command.email(), command.payload());
		NotificationPayload payload = toPayload(notification);
		OutboxEvent outboxEvent = new OutboxEvent(command.requestId(), payload);
		notificationRepository.save(notification);
		outboxRepository.saveEvent(outboxEvent);
		return true;
	}
	
	private NotificationPayload toPayload(Notification notification) {
		NotificationPayload payload = new NotificationPayload();

		payload.setRequestId(notification.getId());
		
		return payload;
	}
}
