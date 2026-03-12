package com.example.javanotifications.email.api.dto;

import java.util.UUID;

public record SendNotificationResponse(
		UUID id,
		String status
		) {

}
