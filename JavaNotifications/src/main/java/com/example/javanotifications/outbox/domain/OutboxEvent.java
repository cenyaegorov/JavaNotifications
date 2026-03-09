package com.example.javanotifications.outbox.domain;

import java.time.Instant;
import java.util.UUID;

public class OutboxEvent {
	private static byte MAX_ATTEMPT = 3;
	
	private final UUID id;
	private OutboxEventStatus status;
	private Instant createdAt;
	private Instant updatedAt;
	private Instant nextUpdate;
	private byte attemptcount;
	
	public OutboxEvent(UUID id) {
		this.id = id;
		this.status = OutboxEventStatus.NEW;
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
		this.nextUpdate = Instant.now();
		this.attemptcount = 0;
	}
	
	public OutboxEvent(UUID id, OutboxEventStatus status, Instant createdAt, Instant updatedAt, Instant nextUpdate, byte attemptCount) {
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.nextUpdate = nextUpdate;
		this.attemptcount = attemptCount;
	}
	
	public void markProcessingWithoutAttempt() {
		this.status = OutboxEventStatus.PROCESSING;
		update();
	}
	
	public void markProcessing() {
		this.status = OutboxEventStatus.PROCESSING;
		this.attemptcount++;
		this.nextUpdate.plusMillis(retryDelay());
		if (this.attemptcount > MAX_ATTEMPT) {
			this.status = OutboxEventStatus.FAILED;
		}
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
	private long retryDelay() {
		return switch (this.attemptcount) {
		case 1 -> 30000;
		case 2 -> 60000;
		case 3 -> 90000;
		default -> 120000;
		};
	}
	
	public UUID getId() { return this.id; }
	public OutboxEventStatus getStatus() { return this.status; }
	public Instant getCreatedAt() { return this.createdAt; }
	public Instant getUpdatedAt() { return this.updatedAt; }
	public Instant getNextUpdate() { return this.nextUpdate; }
	public byte getAttemptCount() {return this.attemptcount; }
}
