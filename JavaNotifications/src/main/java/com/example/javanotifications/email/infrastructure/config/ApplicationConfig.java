package com.example.javanotifications.email.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.email.application.usecase.SendEmailNotificationUseCase;
import com.example.javanotifications.outbox.dto.NotificationPayload;

@Configuration
public class ApplicationConfig {
	@Bean
	public SendEmailNotificationUseCase sendNotificationUsecase(NotificationRepository notificationRepository, OutboxEventRepository outboxRepository) {
		return new SendEmailNotificationUseCase(notificationRepository, outboxRepository);
	}
	

}
