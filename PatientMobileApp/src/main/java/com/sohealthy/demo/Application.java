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
	private static final long SLEEP_DURATION_IN_MS = 1000;

	@Autowired
	private StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public Consumer<ChatMessageSchema> chatChatIdPatientIdConsumer() {
		return data -> {
			// Add business logic here.	
			logger.info(data.toString());
		};
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Starting Patient Mobile App");

			// WARNING - infinite loop for quick demo build
			while (true) {
				logger.debug("Send patient HR data");
				this.sendPatientDataRawPatientIdHr(
					new PatientHeartRateSchema(
						"2011-07-27T20:20:39+00:00",
						"patient-12345",
						"msg-12345",
						111
					),
					"12345"
				);
				Thread.sleep(SLEEP_DURATION_IN_MS);

				logger.debug("Send patient BP data");
				this.sendPatientDataRawPatientIdBp(
					new PatientBloodPressureSchema(
						"2011-07-27T20:20:39+00:00",
						99,
						"patient-12345",
						"msg-12345"
					),
					"12345"
				);
				Thread.sleep(SLEEP_DURATION_IN_MS);

				logger.debug("Send patient chat msg");
				this.sendChatChatIdDoctorId(
					new ChatMessageSchema(
						"2011-07-27T20:20:39+00:00",
						"doctor-54321",
						"patient-12345",
						"msg-12345",
						"Hi doctor, I'm a bit sick :("
					),
					"11111",
					"54321"
				);
				Thread.sleep(SLEEP_DURATION_IN_MS);
			}

		};
	}



	public void sendChatChatIdDoctorId(
		ChatMessageSchema payload, String chatId, String doctorId
		) {
		String topic = String.format("chat/%s/%s",
			chatId, doctorId);
		streamBridge.send(topic, payload);
	}
	public void sendPatientDataRawPatientIdHr(
		PatientHeartRateSchema payload, String patientId
		) {
		String topic = String.format("patient/data/raw/%s/hr",
			patientId);
		streamBridge.send(topic, payload);
	}
	public void sendPatientDataRawPatientIdBp(
		PatientBloodPressureSchema payload, String patientId
		) {
		String topic = String.format("patient/data/raw/%s/bp",
			patientId);
		streamBridge.send(topic, payload);
	}
}
