package com.example.javanotifications.outbox.infrastructure.persistence.entities;

import java.time.Instant;
import java.util.UUID;

import com.example.javanotifications.outbox.domain.OutboxEventStatus;

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
@Table(name = "outbox_events")
public class OutboxEventEntity {
	@Id
	private UUID id;
	@Enumerated(EnumType.STRING)
	private OutboxEventStatus status;
	private Instant createdAt;
	private Instant updatedAt;
	private Instant nextUpdate;
	private byte attemptCount;
}
