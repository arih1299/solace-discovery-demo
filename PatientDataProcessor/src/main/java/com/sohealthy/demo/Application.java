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
	private static final long SLEEP_DURATION_IN_MS = 5000;

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

				logger.debug("Send patient alert");
				this.sendAlertPatientPatientId(
					new PatientAlertSchema(
						"2011-07-27T20:20:39+00:00",
						"Patient is having an emergency .......",
						"patient-12345",
						"msg-12345"
					),
					"12345"
				);
				Thread.sleep(SLEEP_DURATION_IN_MS);
			}

		};
	}

	@Bean
	public Consumer<PatientHeartRateSchema> patientDataRawPatientIdHrConsumer() {
		return data -> {
			// Add business logic here.	
			logger.info(data.toString());
		};
	}

	@Bean
	public Consumer<PatientBloodPressureSchema> patientDataRawPatientIdBpConsumer() {
		return data -> {
			// Add business logic here.	
			logger.info(data.toString());
		};
	}

	public void sendAlertPatientPatientId(
		PatientAlertSchema payload, String patientId
		) {
		String topic = String.format("alert/patient/%s",
			patientId);
		streamBridge.send(topic, payload);
	}
}
