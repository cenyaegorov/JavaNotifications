package com.example.javanotifications.email.domain.notification;

import java.util.UUID;

import lombok.Data;

@Data
public class Notification {
	private final String email;
	private UUID request_id;
	private final String created_at;
	private String updated_at;
	private byte attempt_count;
	private NotificationStatus status;

}
