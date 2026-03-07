package com.example.javanotifications.email.application.port.out.repositories;

import com.example.javanotifications.outbox.domain.OutboxEvent;

public interface OutboxEventRepository {
	public void saveEvent(OutboxEvent event);
}
