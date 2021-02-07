package com.mercadolibre.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mercadolibre.api.swagger.SwaggerConfig;

@SpringBootApplication
public class TopSecretAPIApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(TopSecretAPIApplication.class, args);
	}

}
