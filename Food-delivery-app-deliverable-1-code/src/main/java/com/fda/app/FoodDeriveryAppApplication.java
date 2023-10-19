package com.fda.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fda.app" })
@EnableScheduling
public class FoodDeriveryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeriveryAppApplication.class, args);
	}

}
