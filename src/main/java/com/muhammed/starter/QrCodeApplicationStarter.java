package com.muhammed.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.muhammed"})
@EntityScan(basePackages = {"com.muhammed"})
@EnableJpaRepositories(basePackages = {"com.muhammed"})
public class QrCodeApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeApplicationStarter.class, args);
	}

}
