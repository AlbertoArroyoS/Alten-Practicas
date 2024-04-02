package com.alten.practica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class LibreriasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriasBackendApplication.class, args);
	}

}
