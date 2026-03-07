package com.example.javanotifications.outbox.infrastructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID>{

}
