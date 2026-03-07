package com.example.javanotifications.email.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.javanotifications.email.infrastructure.persistence.entities.NotificationEntity;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, UUID>{

}
