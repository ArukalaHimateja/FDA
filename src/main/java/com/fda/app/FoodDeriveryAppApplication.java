package com.fda.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fda.app.property.FileStorageProperties;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fda.app" })
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableScheduling
public class FoodDeriveryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeriveryAppApplication.class, args);
	}

}
