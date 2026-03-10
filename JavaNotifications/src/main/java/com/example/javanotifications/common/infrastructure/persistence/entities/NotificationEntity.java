package com.example.javanotifications.common.infrastructure.persistence.entities;

import java.time.Instant;
import java.util.UUID;

import com.example.javanotifications.common.domain.NotificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "email_requests")
public class NotificationEntity {
	@Id
	private UUID requestId;
	private String email;
	private String payload;
	private Instant createdAt;
	private Instant updatedAt;
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;
	private byte attemptCount;
}
