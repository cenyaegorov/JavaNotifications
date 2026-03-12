package com.example.javanotifications.email.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.email.application.port.in.ProcessEmailNotificationUseCasePort;
import com.example.javanotifications.email.application.port.out.EmailSender;
import com.example.javanotifications.outbox.dto.NotificationPayload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessEmailNotificationUseCase implements ProcessEmailNotificationUseCasePort{
	private final NotificationRepository repository;
	private final EmailSender sender;
	
	public ProcessEmailNotificationUseCase(NotificationRepository repository, EmailSender sender) {
		this.repository = repository;
		this.sender = sender;
	}

	@Override
	public void execute(NotificationPayload payload) {
		Notification notification = getNewNotificationById(payload.getRequestId());
		
		try {
			sender.send(notification.getEmail(), notification.getPayload());
			notification.markSent();
		}
		catch (Exception e) {
			log.error("Cannot send email " + notification.getEmail() + " with id " + notification.getId());
			notification.markFailed();
		}

		repository.save(notification);
		
	}
	
	@Transactional
	public Notification getNewNotificationById(UUID id) {
		Notification notification = repository.findByIdAndStatus(id, NotificationStatus.NEW);
		
		if (notification == null) {
			throw new IllegalStateException("Notification " + id + " is not found or already is marked PROCESSING");
		}
		
		notification.markProcessing();
		repository.save(notification);
		
		return notification;
	}

}
