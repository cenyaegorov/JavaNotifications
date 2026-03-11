package com.example.javanotifications.email.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.email.application.port.out.EmailSender;
import com.example.javanotifications.kafka.persistence.FailedMessageScheduler;
import com.example.javanotifications.outbox.dto.NotificationPayload;

@ExtendWith(MockitoExtension.class)
public class FailedMessageSchedulerTest {

	@Mock
	NotificationRepository repository;
	
	@Mock
	EmailSender sender;
	
	@InjectMocks
	FailedMessageScheduler scheduler;
	
	@Test
	void shouldMarkLaterProcessingNotificationsAsFailed() {
		Notification notification = getNotification();
		notification.markProcessing();
		List<Notification> notifications = List.of(notification);
		
		when(repository.findByStatusAndCompareToNextUpdateLimitWithLock(eq(NotificationStatus.PROCESSING), any(Instant.class), anyInt())).thenReturn(notifications);
		
		scheduler.processProcessing();
		
		assertEquals(notification.getStatus(), NotificationStatus.FAILED);
		verify(repository, atLeast(1)).saveAll(notifications);;
	}
	@Test
	void shouldRetryFailedNotificationsAndMarkAsSent() {
		Notification notification = getNotification();
		notification.markProcessing();
		notification.markFailed();
		List<Notification> notifications = List.of(notification);
		
		when(repository.findByStatusAndCompareToNextUpdateLimitWithLock(eq(NotificationStatus.FAILED), any(Instant.class), anyInt())).thenReturn(notifications);
		
		scheduler.processFailed();
		
		assertEquals(notification.getStatus(), NotificationStatus.SENT);
		verify(repository, atLeast(1)).save(notification);
	}
	@Test
	void shouldFailRetryFailedNotificationsAnsMarkAsFailed() {
		Notification notification = getNotification();
		notification.markProcessing();
		notification.markFailed();
		List<Notification> notifications = List.of(notification);
		
		when(repository.findByStatusAndCompareToNextUpdateLimitWithLock(eq(NotificationStatus.FAILED), any(Instant.class), anyInt())).thenReturn(notifications);
		doThrow(RuntimeException.class).when(sender).send(anyString(), anyString());
		
		scheduler.processFailed();
		
		assertEquals(notification.getStatus(), NotificationStatus.FAILED);
		verify(repository, atLeast(1)).save(notification);
	}
	@Test
	void shouldFailRetryFailedNotificationsMaxAttemptsAndMarkAsDead() {
		Notification notification = getNotification();
		notification.markProcessing();
		notification.markFailed();
		List<Notification> notifications = List.of(notification);
		
		when(repository.findByStatusAndCompareToNextUpdateLimitWithLock(eq(NotificationStatus.FAILED), any(Instant.class), anyInt())).thenReturn(notifications);
		doThrow(RuntimeException.class).when(sender).send(anyString(), anyString());
		
		for (int i = 0; i < notification.getMaxAttempts(); i++) scheduler.processFailed();
		
		assertEquals(notification.getStatus(), NotificationStatus.DEAD);
		verify(repository, atLeast(1)).save(notification);
	}
	private Notification getNotification() {
		UUID id = UUID.randomUUID();
		Notification notification = new Notification(id, "example@mail.com", "text");
		return notification;
	}
}
