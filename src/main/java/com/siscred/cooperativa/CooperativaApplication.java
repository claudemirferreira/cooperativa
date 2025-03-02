package com.siscred.cooperativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CooperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativaApplication.class, args);
	}

}
