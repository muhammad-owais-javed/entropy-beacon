package com.entropybeacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication

@EnableScheduling
public class EntropyBeaconApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntropyBeaconApplication.class, args);
	}

}
