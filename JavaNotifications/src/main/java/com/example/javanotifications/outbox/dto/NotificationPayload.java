package com.example.javanotifications.outbox.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.javanotifications.common.domain.NotificationStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationPayload extends OutboxPayload{
	private String email;
	private UUID requestId;
	private Instant createdAt;
	private Instant updatedAt;
	private byte attemptCount;
	private NotificationStatus status;
	private String payload;
}
