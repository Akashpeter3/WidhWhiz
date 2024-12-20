package com.app.eureka.server.eurekaserverapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverapplicationApplication.class, args);
	}

}
