package com.example.javanotifications.outbox.infrastructure.persistence.entities;

import java.util.UUID;

import com.example.javanotifications.email.domain.outbox.OutboxEventStatus;

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
@Table(name = "outboxt_events")
public class OutboxEventEntity {
	@Id
	private UUID requestId;
	@Enumerated(EnumType.STRING)
	private OutboxEventStatus status;
}
