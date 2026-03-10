package com.example.javanotifications.kafka.application;

import org.springframework.stereotype.Service;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.outbox.dto.NotificationPayload;

@Service
public class ProcessNotificationUseCase implements ProcessNotificationUseCasePort{
	private static NotificationRepository repository;
	
	public ProcessNotificationUseCase(NotificationRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean execute(NotificationPayload payload) {
		// TODO Auto-generated method stub
		return false;
	}

}
