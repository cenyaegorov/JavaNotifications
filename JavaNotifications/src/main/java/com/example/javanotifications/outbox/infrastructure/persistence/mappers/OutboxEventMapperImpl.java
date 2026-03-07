package com.example.javanotifications.outbox.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.javanotifications.email.application.port.out.DomainEntityMapper;
import com.example.javanotifications.email.domain.outbox.OutboxEvent;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

@Component
public class OutboxEventMapperImpl implements DomainEntityMapper<OutboxEvent, OutboxEventEntity> {

	@Override
	public OutboxEvent toDomain(OutboxEventEntity entity) {
		return new OutboxEvent(entity.getRequestId(), entity.getStatus());
	}

	@Override
	public OutboxEventEntity toEntity(OutboxEvent domain) {
		OutboxEventEntity entity = new OutboxEventEntity();
		entity.setRequestId(domain.getRequestId());
		entity.setStatus(domain.getStatus());
		return entity;
	}

}
