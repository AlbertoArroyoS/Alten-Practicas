package com.alten.practica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	// traer los datos CORS que tenemos en el application-dev.yml , value de spring
	@Value("${cors.mapping}")
	private String MAPPING;

	@Value("${cors.allowed-origins}")
	private String[] ORIGINS;

	@Value("${cors.allowed-methods}")
	private String[] METHODS;

	@Value("${cors.allowed-headers}")
	private String[] HEADERS;

	// Añadir addCorsMappings de Source-> OverRide/Implement Methods,
	// addCorsMappings(CorsRegistry registry)
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping(MAPPING) //mapeo de la ruta
        .allowedOrigins(ORIGINS) //origenes permitidos
        .allowedMethods(METHODS) //metodos permitidos
        .allowedHeaders(HEADERS) //cabeceras permitidas
        .maxAge(3600) //tiempo maximo de espera
        .allowCredentials(false); //no permitir credenciales
	}

}
