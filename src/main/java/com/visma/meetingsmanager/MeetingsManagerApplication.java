package com.visma.meetingsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeetingsManagerApplication {

	public static final String BASE_URL = "/api";

	public static void main(String[] args) {
		SpringApplication.run(MeetingsManagerApplication.class, args);
	}

}
