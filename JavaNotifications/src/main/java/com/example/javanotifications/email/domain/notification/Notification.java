package com.example.javanotifications.email.domain.notification;

import java.time.Instant;
import java.util.UUID;

public class Notification {
	private final byte MAX_ATTEMPT = 4;
	
	private final String email;
	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;
	private byte attemptCount;
	private NotificationStatus status;

	public Notification(UUID id, String email) {
		this.id = id;
		this.email = email;
		this.createdAt = Instant.now();
		this.attemptCount = 0;
		this.status = NotificationStatus.NEW;
	}
	
	public void markProcessing() {
		this.status = NotificationStatus.PROCESSING;
		this.updatedAt = Instant.now();
	}
	public void markSent() {
		this.status = NotificationStatus.SENT;
		this.updatedAt = Instant.now();
	}
	public void markFailed() {
		this.attemptCount++;
		if (this.attemptCount >= this.MAX_ATTEMPT) {
			this.status = NotificationStatus.DEAD;
		}
		else this.status = NotificationStatus.FAILED;
		this.updatedAt = Instant.now();
	}
	public boolean isDead() {
		return this.attemptCount >= 4;
	}
	
	public String getEmail() { return this.email; }
	public Instant getCreatedAt() { return this.createdAt; }
	public Instant getUpdatedAt() { return this.updatedAt; }
	public UUID getId() { return this.id; }
	public NotificationStatus getStatus() { return this.status; }
}
