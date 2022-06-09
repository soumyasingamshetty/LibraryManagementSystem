package com.LibraryManagementService.LibraryAPIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LibraryApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiGatewayApplication.class, args);
	}

}
