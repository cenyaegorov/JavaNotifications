package com.example.javanotifications.outbox.infrastructure.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.email.application.port.out.DomainEntityMapper;
import com.example.javanotifications.email.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.email.domain.outbox.OutboxEvent;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

@Repository
public class OutboxEventRepositoryImpl implements OutboxEventRepository {
	private final JpaOutboxEventRepository repository;
	private final DomainEntityMapper<OutboxEvent, OutboxEventEntity> mapper;
	
	public OutboxEventRepositoryImpl(JpaOutboxEventRepository repository, DomainEntityMapper<OutboxEvent, OutboxEventEntity> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public void saveEvent(OutboxEvent event) {
		repository.save(mapper.toEntity(event));
	}

}
