package com.example.Assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/pets").allowedOrigins("*");
				registry.addMapping("/pets/{id}").allowedOrigins("*");
				registry.addMapping("/pets/price/{minPrice}").allowedOrigins("*");
				registry.addMapping("/adoptions").allowedOrigins("*");
				registry.addMapping("/adoptions/{petId}").allowedOrigins("*");
				registry.addMapping("/adoptions/{id}").allowedOrigins("*");
				registry.addMapping("/adoptions/statistics").allowedOrigins("*");
				registry.addMapping("/adoptionCustomer").allowedOrigins("*");
				registry.addMapping("/adoptionCustomer/{adoptionId}/{customerId}").allowedOrigins("*");
				registry.addMapping("/adoptionCustomer/{id}").allowedOrigins("*");
				registry.addMapping("/customers").allowedOrigins("*");
				registry.addMapping("/customers/{id}").allowedOrigins("*");

			}
		};
	}

}
