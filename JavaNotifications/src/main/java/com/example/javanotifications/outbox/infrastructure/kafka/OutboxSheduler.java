package com.example.javanotifications.outbox.infrastructure.kafka;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.javanotifications.email.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;

import jakarta.transaction.Transactional;

@Component
public class OutboxSheduler {
	private final OutboxEventRepository repository;
	private final KafkaTemplate<String, String> template;
	
	public OutboxSheduler(OutboxEventRepository repository, KafkaTemplate<String, String> template) {
		this.repository = repository;
		this.template = template;
	}

	@Scheduled(fixedDelay = 5000)
	public void publishEvents() {
		List<OutboxEvent> events = getEvents();
		
		for (OutboxEvent event : events) {
			try {
			template.send("notifications", event.getId().toString());
			event.markProcessed();
			}
			catch (Exception e) {
				event.markFailed();
			}
			repository.saveEvent(event);
		}
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
