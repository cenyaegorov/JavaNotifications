package com.example.javanotifications.outbox.infrastructure.kafka;

import java.time.Instant;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.javanotifications.common.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;
import com.example.javanotifications.outbox.dto.NotificationPayload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OutboxSheduler {
	private final OutboxEventRepository repository;
	private final KafkaTemplate<String, Object> template;
	
	public OutboxSheduler(OutboxEventRepository repository, KafkaTemplate<String, Object> template) {
		this.repository = repository;
		this.template = template;
		log.info("created");
	}

	@Scheduled(fixedDelay = 5000)
	public void publishEvents() {
		List<OutboxEvent> events = getEvents();
		log.info("publishEvents() scheduled");
		
		for (OutboxEvent event : events) {
			try {
			template.send("notifications", event.getPayload()).get();
			event.markProcessed();
			}
			catch (Exception e) {
				log.error("Failed to send event {}", event.getId(), e);
			}
			repository.saveEvent(event);
		}
	}
	
	@Scheduled(fixedDelay = 30000, initialDelay = 5000)
	public void retryEvents() {
		List<OutboxEvent> events = getUnsentEvents();
		log.info("retryEvents() scheduled");
		
		for (OutboxEvent event: events) {
			try {
				template.send("notifications", event.getPayload()).get();
				event.markProcessed();
			}
			catch (Exception e) {
				log.error("Failed to send event {}", event.getId(), e);
			}
			repository.saveEvent(event);
		}
	}
	
	@Transactional
	public List<OutboxEvent> getUnsentEvents() {
		List<OutboxEvent> events = repository.findByStatusByCompareNextUpdateWithLockAndLimit(OutboxEventStatus.PROCESSING, Instant.now(), 100);
		
		for (OutboxEvent event: events) {
			event.markProcessing();
		}
		
		return events;
	}
	
	@Transactional
	public List<OutboxEvent> getEvents() {
		List<OutboxEvent> events = repository.findByStatusWithLockAndLimit(OutboxEventStatus.NEW, 100);
		
		for (OutboxEvent event : events) {
			event.markProcessing();
		}
		
		repository.saveAll(events);
		return events;
	}
}
