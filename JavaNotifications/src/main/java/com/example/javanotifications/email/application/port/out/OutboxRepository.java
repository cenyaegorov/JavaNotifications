package com.example.javanotifications.email.application.port.out;

import com.example.javanotifications.email.domain.outbox.OutboxEvent;

public interface OutboxRepository {
	public void saveEvent(OutboxEvent event);
}
