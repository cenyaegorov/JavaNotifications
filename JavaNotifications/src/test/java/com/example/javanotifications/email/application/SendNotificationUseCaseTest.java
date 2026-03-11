package com.example.javanotifications.email.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.usecase.SendNotificationUsecase;
import com.example.javanotifications.outbox.domain.OutboxEvent;

@ExtendWith(MockitoExtension.class)
public class SendNotificationUseCaseTest {

	@Mock
	NotificationRepository repository;
	@Mock
	OutboxEventRepository outboxRepository;
	
	@InjectMocks
	SendNotificationUsecase usecase;
	
	@Test
	void ShouldSaveNotificationAndOutboxEvent(){
		SendNotificationCommand command = new SendNotificationCommand(UUID.randomUUID(), "example@mail.com", "text");
		
		boolean resault = usecase.execute(command);
		
		assertTrue(resault);
		
		verify(repository).save(any(Notification.class));
		verify(outboxRepository).saveEvent(any(OutboxEvent.class));
	}
}
