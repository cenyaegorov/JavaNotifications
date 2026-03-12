package com.example.javanotifications;

import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.javanotifications.email.application.dto.SendNotificationCommand;

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class JavaNotificationsApplication implements ApplicationContextAware{
	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(JavaNotificationsApplication.class, args);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		
	}

}
