package com.example.surveybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SurveybackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(SurveybackendApplication.class, args);

		//Initialize the database
		//initializeDatabase();
	}

	private static void initializeDatabase() {
		String initDatabase = "http://localhost:8080/jpa/surveys/init";

		RestTemplate restTemplate = new RestTemplate();

		try {
			restTemplate.getForObject(initDatabase, String.class);
		} catch (Exception e) {
			System.err.println("Failed to send db initialization request");
		}
	}

}
