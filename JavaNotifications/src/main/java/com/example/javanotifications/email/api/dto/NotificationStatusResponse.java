package com.example.javanotifications.email.api.dto;

import java.util.UUID;

public record NotificationStatusResponse(
		UUID id,
		String status,
		byte attemptCount
		) {

}
