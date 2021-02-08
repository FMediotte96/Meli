package com.mercadolibre.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TopSecretAPIApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(TopSecretAPIApplication.class, args);
	}

}
