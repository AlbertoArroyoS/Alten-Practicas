package com.alten.practica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication 
public class LibreriasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriasBackendApplication.class, args);
	}
	
	//Configuracion de la documentacion de la API con Swagger
	@Bean
	public OpenAPI info() {
		return new OpenAPI()
				.info(new Info().title("Librerias API")
				.description("API para gestionar librerias")
				.contact(new io.swagger.v3.oas.models.info.Contact().email("albertoarroyo@hotmail.es"))
				.version("1.0"));
	}
}
