package com.alten.practica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para la aplicación Spring MVC.
 * 
 * Esta clase configura las reglas CORS para las peticiones HTTP, permitiendo
 * el control sobre los recursos de diferentes orígenes. Los valores de configuración
 * son inyectados desde el archivo de propiedades `application-dev.yml`.
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
	

    @Value("${cors.mapping}")
    private String MAPPING;

    @Value("${cors.allowed-origins}")
    private String[] ORIGINS;


    @Value("${cors.allowed-methods}")
    private String[] METHODS;


    @Value("${cors.allowed-headers}")
    private String[] HEADERS;


    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(MAPPING)
            .allowedOrigins(ORIGINS)
            .allowedMethods(METHODS)
            .allowedHeaders(HEADERS)
            .maxAge(3600) // Define el tiempo máximo en segundos que los resultados de una solicitud de pre-vuelo pueden ser cachéados.
            .allowCredentials(false); // Configura si se deben exponer las credenciales al navegador.
    }
}
