package com.example.javanotifications.outbox.infrastructure.persistence;

import com.example.javanotifications.email.application.port.out.repositories.OutboxRepository;
import com.example.javanotifications.email.domain.outbox.OutboxEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresOutboxRepository implements OutboxRepository {

	@Override
	public void saveEvent(OutboxEvent event) {
		log.info("event is saved: " + event.toString());
	}

}
