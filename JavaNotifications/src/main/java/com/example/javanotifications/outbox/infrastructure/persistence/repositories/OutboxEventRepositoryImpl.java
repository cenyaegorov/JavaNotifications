package com.example.javanotifications.outbox.infrastructure.persistence.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.email.application.port.out.DomainEntityMapper;
import com.example.javanotifications.email.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

import jakarta.transaction.Transactional;

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

	@Override
	@Transactional
	public List<OutboxEvent> findByStatusWithLockAndLimit(OutboxEventStatus status, int limit) {
		List<OutboxEventEntity> entities = repository.findByStatusWithLockAndLimit(status, limit);
		
		return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public void saveAll(List<OutboxEvent> events) {
		repository.saveAll(events.stream().map(mapper::toEntity).collect(Collectors.toList()));
		
	}

}
