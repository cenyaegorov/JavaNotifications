package com.example.javanotifications.outbox.domain;

import java.time.Instant;
import java.util.UUID;

public class OutboxEvent {
	private final UUID id;
	private OutboxEventStatus status;
	private Instant createdAt;
	private Instant updatedAt;
	
	public OutboxEvent(UUID id) {
		this.id = id;
		this.status = OutboxEventStatus.NEW;
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
	}
	
	public OutboxEvent(UUID id, OutboxEventStatus status, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void markProcessing() {
		this.status = OutboxEventStatus.PROCESSING;
		update();
	}
	public void markProcessed() {
		this.status = OutboxEventStatus.PROCESSED;
		update();
	}
	public void markFailed() {
		this.status = OutboxEventStatus.FAILED;
		update();
	}
	private void update() {
		this.updatedAt = Instant.now();
	}
	
	public UUID getId() { return this.id; }
	public OutboxEventStatus getStatus() { return this.status; }
	public Instant getCreatedAt() { return this.createdAt; }
	public Instant getUpdatedAt() { return this.updatedAt; }
}
