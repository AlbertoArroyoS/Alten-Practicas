package com.alten.practica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@ComponentScan(basePackages = "com.alten.practica.modelo.persistencia.daoLibreriaImplJPA")
public class LibreriasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriasBackendApplication.class, args);
	}

}
