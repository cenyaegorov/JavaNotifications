package com.example.javanotifications.outbox.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.javanotifications.email.application.port.out.DomainEntityMapper;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

@Component
public class OutboxEventMapperImpl implements DomainEntityMapper<OutboxEvent, OutboxEventEntity> {

	@Override
	public OutboxEvent toDomain(OutboxEventEntity entity) {
		return new OutboxEvent(entity.getId(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt(), entity.getNextUpdate(), entity.getAttemptCount(), entity.getPayload());
	}

	@Override
	public OutboxEventEntity toEntity(OutboxEvent domain) {
		OutboxEventEntity entity = new OutboxEventEntity();
		entity.setId(domain.getId());
		entity.setStatus(domain.getStatus());
		entity.setCreatedAt(domain.getCreatedAt());
		entity.setUpdatedAt(domain.getUpdatedAt());
		entity.setNextUpdate(domain.getNextUpdate());
		entity.setAttemptCount(domain.getAttemptCount());
		entity.setPayload(domain.getPayload());
		return entity;
	}

}
