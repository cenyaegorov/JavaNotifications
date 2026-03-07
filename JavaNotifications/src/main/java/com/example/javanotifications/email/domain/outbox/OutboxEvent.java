package com.example.javanotifications.email.domain.outbox;

import java.util.UUID;

public class OutboxEvent {
	private UUID requestId;
	private OutboxEventStatus status;
	
	public OutboxEvent(UUID requestId) {
		this.requestId = requestId;
		this.status = OutboxEventStatus.NEW;
	}
	
	public void markPublished() {
		this.status = OutboxEventStatus.PUBLISHED;
	}
	public void markFailed() {
		this.status = OutboxEventStatus.FAILED;
	}
	
	public UUID getRequestId() { return this.requestId; }
	public OutboxEventStatus getStatus() { return this.status; }
}
