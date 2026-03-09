package com.example.javanotifications.email.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.application.port.out.repositories.OutboxEventRepository;
import com.example.javanotifications.email.application.usecase.SendNotificationUsecase;

@Configuration
public class ApplicationConfig {
	@Bean
	public SendNotificationUsecase sendNotificationUsecase(NotificationRepository notificationRepository, OutboxEventRepository outboxRepository) {
		return new SendNotificationUsecase(notificationRepository, outboxRepository);
	}
	
	@Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.19.73.195:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        

        
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaProducerFactory<>(config);
    }
	
	@Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
