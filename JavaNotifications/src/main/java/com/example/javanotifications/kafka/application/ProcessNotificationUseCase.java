package com.example.javanotifications.kafka.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.email.application.port.out.EmailSender;
import com.example.javanotifications.outbox.dto.NotificationPayload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessNotificationUseCase implements ProcessNotificationUseCasePort{
	private final NotificationRepository repository;
	private final EmailSender sender;
	
	public ProcessNotificationUseCase(NotificationRepository repository, EmailSender sender) {
		this.repository = repository;
		this.sender = sender;
	}

	@Override
	public void execute(NotificationPayload payload) {
		Notification notification = getNotificationById(payload.getRequestId());
		
		try {
			sender.send(notification.getEmail(), notification.getPayload());
		}
		catch (Exception e) {
			log.error("Cannot send email " + notification.getEmail() + " with id " + notification.getId());
			notification.markFailed();
		}

		repository.save(notification);
		
	}
	
	@Transactional
	public Notification getNotificationById(UUID id) {
		Notification notification = repository.findById(id);
		
		if (notification == null) {
			throw new IllegalStateException("Notification " + id + "is not found");
		}
		
		notification.markProcessing();
		repository.save(notification);
		
		return notification;
	}

}
