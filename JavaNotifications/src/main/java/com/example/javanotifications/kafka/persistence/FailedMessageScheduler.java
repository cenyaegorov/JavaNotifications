package com.example.javanotifications.kafka.persistence;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.email.application.port.out.EmailSender;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FailedMessageScheduler {
	private static final long TIME_OUT_FOR_RETRY_PROCESSING = 60000;
	private final NotificationRepository repository;
	private final EmailSender sender;

	public FailedMessageScheduler(NotificationRepository repository, EmailSender sender) {
		this.repository = repository;
		this.sender = sender;
	}
	
	@Scheduled(fixedDelay = 60000)
	public void processFailed() {
		List<Notification> notifications = getFailedNotifications();
		
		for (Notification notification: notifications) {
			try {
				sender.send(notification.getEmail(), notification.getPayload());
				notification.markSent();
			}
			catch (Exception e) {
				log.error("Cannot send email " + notification.getEmail() + " with id " + notification.getId());
				notification.markFailed();
			}
		}
		repository.saveAll(notifications);
	}
	
	@Transactional
	public List<Notification> getFailedNotifications(){
		List<Notification> notifications = repository.findByStatusAndCompareToNextUpdateLimitWithLock(NotificationStatus.FAILED, Instant.now(), 100);
		
		for (Notification notification: notifications) {
			notification.markProcessing();
		}
		
		repository.saveAll(notifications);
		return notifications;
	}
	
	@Transactional
	@Scheduled(fixedDelay = 60000)
	public void processProcessing() {
		List<Notification> notifications = repository.findByStatusAndCompareToNextUpdateLimitWithLock(NotificationStatus.PROCESSING, Instant.now().plusMillis(TIME_OUT_FOR_RETRY_PROCESSING), 100);
		
		for (Notification notification: notifications) {
			notification.markFailed();
		}
		repository.saveAll(notifications);
	}
}
