package com.example.javanotifications.outbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.javanotifications.common.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;
import com.example.javanotifications.outbox.dto.NotificationPayload;
import com.example.javanotifications.outbox.infrastructure.kafka.OutboxScheduler;

@ExtendWith(MockitoExtension.class)
public class OutboxSchedulerTest {

	@Mock
	OutboxEventRepository repository;
	@Mock
	KafkaTemplate<String, Object> template;
	
	@InjectMocks
	OutboxScheduler scheduler;
	
	@Test
	void shouldPublishEventsAndMarkAsProcessed() {
		OutboxEvent event = getEvent();
		List<OutboxEvent> events = List.of(event);
		
		when(repository.findByStatusWithLockAndLimit(eq(OutboxEventStatus.NEW), eq(100))).thenReturn(events);
		when(template.send(any(String.class), any())).thenReturn(CompletableFuture.completedFuture(null));
		
		scheduler.publishEvents();
		
		assertEquals(event.getStatus(), OutboxEventStatus.PROCESSED);
		verify(template, times(1)).send(eq("notifications"), eq(event.getPayload()));
		verify(repository, atLeast(1)).saveEvent(event);
	}
	@Test
	void shouldFailPublishEventsAndMarkOnlyAsProcessing() {
		OutboxEvent event = getEvent();
		List<OutboxEvent> events = List.of(event);
		
		when(repository.findByStatusWithLockAndLimit(eq(OutboxEventStatus.NEW), eq(100))).thenReturn(events);
		when(template.send(any(String.class), any())).thenThrow(RuntimeException.class);
		//doThrow(Exception.class).when(template).send(any(String.class), any());
		
		scheduler.publishEvents();
		
		assertEquals(event.getStatus(), OutboxEventStatus.PROCESSING);
		verify(repository, atLeast(1)).saveEvent(event);
	}
	@Test
	void shouldRetryEventsAndMarkAsProcessed() {
		OutboxEvent event = getEvent();
		List<OutboxEvent> events = List.of(event);
		event.markProcessing();
		
		when(repository.findByStatusByCompareNextUpdateWithLockAndLimit(eq(OutboxEventStatus.PROCESSING), any(), eq(100))).thenReturn(events);
		when(template.send(any(String.class), any())).thenReturn(CompletableFuture.completedFuture(null));
		
		scheduler.retryEvents();
		
		assertEquals(event.getStatus(), OutboxEventStatus.PROCESSED);
		verify(template, times(1)).send(eq("notifications"), eq(event.getPayload()));
		verify(repository).saveEvent(event);
	}
	@Test
	void shouldFailRetryEventsAndMarkOnlyAsProcessing() {
		OutboxEvent event = getEvent();
		List<OutboxEvent> events = List.of(event);
		event.markProcessing();
		
		when(repository.findByStatusByCompareNextUpdateWithLockAndLimit(eq(OutboxEventStatus.PROCESSING), any(), eq(100))).thenReturn(events);
		when(template.send(any(String.class), any())).thenThrow(RuntimeException.class);
		
		scheduler.retryEvents();
		
		assertEquals(event.getStatus(), OutboxEventStatus.PROCESSING);
		verify(template, times(1)).send(eq("notifications"), eq(event.getPayload()));
		verify(repository).saveEvent(event);
	}
	@Test
	void shouldFailRetryEventMaxAttemptsAndDead() {
		OutboxEvent event = getEvent();
		List<OutboxEvent> events = List.of(event);
		event.markProcessing();
		
		when(repository.findByStatusByCompareNextUpdateWithLockAndLimit(eq(OutboxEventStatus.PROCESSING), any(), eq(100))).thenReturn(events);
		when(template.send(any(String.class), any())).thenThrow(RuntimeException.class);
		
		for (int i = 0; i < event.getMaxAttempt(); i++) scheduler.retryEvents();
		
		assertEquals(event.getStatus(), OutboxEventStatus.FAILED);
	}
	private OutboxEvent getEvent() {
		UUID id = UUID.randomUUID();
		NotificationPayload payload = new NotificationPayload();
		payload.setRequestId(id);
		OutboxEvent event = new OutboxEvent(id, payload);
		return event;
	}
}
