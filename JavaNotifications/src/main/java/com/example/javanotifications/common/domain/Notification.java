package com.example.javanotifications.common.domain;

import java.time.Instant;
import java.util.UUID;

public class Notification {
	private static final byte MAX_ATTEMPT = 3;
	
	private final String email;
	private final UUID requestId;
	private final Instant createdAt;
	private Instant updatedAt;
	private byte attemptCount;
	private NotificationStatus status;
	private String payload;
	private Instant nextUpdate;

	public Notification(UUID requestId, String email, String payload) {
		this.requestId = requestId;
		this.email = email;
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
		this.attemptCount = 0;
		this.status = NotificationStatus.NEW;
		this.payload = payload;
	}
	
	public Notification(UUID requestId, String email, String payld, Instant createdAt, Instant updatedAt, byte attemptCount, NotificationStatus status, Instant nextUpdate) {
		this.requestId = requestId;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.attemptCount = attemptCount;
		this.status = status;
		this.payload = payload;
		this.nextUpdate = nextUpdate;
	}
	
	public void markProcessing() {
		this.nextUpdate = Instant.now();
		
		this.status = NotificationStatus.PROCESSING;
		this.updatedAt = Instant.now();
	}
	public void markSent() {
		this.status = NotificationStatus.SENT;
		this.updatedAt = Instant.now();
	}
	public void markFailed() {
		this.attemptCount++;
		this.nextUpdate.plusMillis(retryDelay());
		if (this.attemptCount > MAX_ATTEMPT) {
			this.status = NotificationStatus.DEAD;
		}
		else {
			this.status = NotificationStatus.FAILED;
		}
		this.updatedAt = Instant.now();
	}
	public boolean isDead() {
		return this.attemptCount >= MAX_ATTEMPT;
	}
	private long retryDelay() {
		return switch(this.attemptCount) {
		case 1 -> 30000;
		case 2 -> 60000;
		case 3 -> 90000;
		default -> 120000;
		};
	}
	
	public String getEmail() { return this.email; }
	public Instant getCreatedAt() { return this.createdAt; }
	public Instant getUpdatedAt() { return this.updatedAt; }
	public UUID getId() { return this.requestId; }
	public NotificationStatus getStatus() { return this.status; }
	public byte getAttemptCount() { return this.attemptCount; }
	public String getPayload() { return this.payload; }
	public Instant getNextUpdate() { return this.nextUpdate; }
	public byte getMaxAttempts() { return MAX_ATTEMPT; }
}
