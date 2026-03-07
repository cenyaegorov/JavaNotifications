package com.example.javanotifications.outbox.domain;

import java.util.UUID;

public class OutboxEvent {
	private final UUID id;
	private OutboxEventStatus status;
	
	public OutboxEvent(UUID id) {
		this.id = id;
		this.status = OutboxEventStatus.NEW;
	}
	
	public OutboxEvent(UUID id, OutboxEventStatus status) {
		this.id = id;
		this.status = status;
	}
	
	public void markPublished() {
		this.status = OutboxEventStatus.PUBLISHED;
	}
	public void markFailed() {
		this.status = OutboxEventStatus.FAILED;
	}
	
	public UUID getId() { return this.id; }
	public OutboxEventStatus getStatus() { return this.status; }
}
