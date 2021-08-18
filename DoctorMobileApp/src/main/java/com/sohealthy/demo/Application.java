package com.sohealthy.demo;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private static final long SLEEP_DURATION_IN_MS = 3000;

	@Autowired
	private StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Starting Doctor Mobile App");

			// WARNING - infinite loop for quick demo build
			while (true) {

				logger.debug("Send doctor chat msg");
				this.sendChatChatIdPatientId(
					new ChatMessageSchema(
						"2011-07-27T20:20:39+00:00",
						"patient-12345",
						"doctor-54321",
						"msg-12345",
						"Hi patient, how are you? :)"
					),
					"11111",
					"12345"
				);
				Thread.sleep(SLEEP_DURATION_IN_MS);
			}

		};
	}


	@Bean
	public Consumer<ChatMessageSchema> chatChatIdDoctorIdConsumer() {
		return data -> {
			// Add business logic here.	
			logger.info(data.toString());
		};
	}

	@Bean
	public Consumer<PatientAlertSchema> alertPatientPatientIdConsumer() {
		return data -> {
			// Add business logic here.	
			logger.info(data.toString());
		};
	}

	public void sendChatChatIdPatientId(
		ChatMessageSchema payload, String chatId, String patientId
		) {
		String topic = String.format("chat/%s/%s",
			chatId, patientId);
		streamBridge.send(topic, payload);
	}
}
