package com.example.javanotifications.email.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.javanotifications.kafka.application.ProcessNotificationUseCase;
import com.example.javanotifications.kafka.application.ProcessNotificationUseCasePort;
import com.example.javanotifications.outbox.dto.NotificationPayload;

@ExtendWith(MockitoExtension.class)
public class ProcessNotificationUseCaseTest {

	@Mock
	NotificationRepository repository;
	
	@Mock
	EmailSender sender;
	
	@InjectMocks
	ProcessNotificationUseCase usecase;
	
	@Test
	void shouldSendAndMarkAsSent() {
		UUID id = UUID.randomUUID();
		NotificationPayload payload = new NotificationPayload();
		payload.setRequestId(id);
		Notification notification = new Notification(id, "example@mail.com", "text");
		
		when(repository.findByIdAndStatus(id, NotificationStatus.NEW)).thenReturn(notification);
		
		usecase.execute(payload);
		
		assertEquals(notification.getStatus(), NotificationStatus.SENT);
		verify(repository, atLeast(1)).save(notification);;
	}
	@Test
	void shouldFailSendAndMarkAsFailed() {
		UUID id = UUID.randomUUID();
		NotificationPayload payload = new NotificationPayload();
		payload.setRequestId(id);
		Notification notification = new Notification(id, "example@mail.com", "text");
		
		when(repository.findByIdAndStatus(id, NotificationStatus.NEW)).thenReturn(notification);
		doThrow(RuntimeException.class).when(sender).send(anyString(), anyString());
		
		usecase.execute(payload);
		
		assertEquals(notification.getStatus(), NotificationStatus.FAILED);
		verify(repository, atLeast(1)).save(notification);
	}
}
