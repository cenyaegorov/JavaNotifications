package com.example.javanotifications.email.domain.notification;

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

	public Notification(UUID requestId, String email, String payload) {
		this.requestId = requestId;
		this.email = email;
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
		this.attemptCount = 0;
		this.status = NotificationStatus.NEW;
		this.payload = payload;
	}
	
	public void markProcessing() {
		if (this.status != NotificationStatus.NEW) {
			throw new IllegalStateException("cannot process notification in status " + this.status);
		}
		
		this.status = NotificationStatus.PROCESSING;
		this.updatedAt = Instant.now();
	}
	public void markSent() {
		this.status = NotificationStatus.SENT;
		this.updatedAt = Instant.now();
	}
	public void markFailed() {
		this.attemptCount++;
		if (this.attemptCount >= MAX_ATTEMPT) {
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
	
	public String getEmail() { return this.email; }
	public Instant getCreatedAt() { return this.createdAt; }
	public Instant getUpdatedAt() { return this.updatedAt; }
	public UUID getId() { return this.requestId; }
	public NotificationStatus getStatus() { return this.status; }
	public byte getAttemptCount() { return this.attemptCount; }
	public String getPayload() { return this.payload; }
}
