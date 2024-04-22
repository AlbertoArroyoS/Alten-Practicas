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
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * Ruta para la configuración CORS.
     */
    @Value("${cors.mapping}")
    private String MAPPING;

    /**
     * Orígenes permitidos para las peticiones CORS.
     */
    @Value("${cors.allowed-origins}")
    private String[] ORIGINS;

    /**
     * Métodos HTTP permitidos para las peticiones CORS.
     */
    @Value("${cors.allowed-methods}")
    private String[] METHODS;

    /**
     * Cabeceras permitidas en las peticiones CORS.
     */
    @Value("${cors.allowed-headers}")
    private String[] HEADERS;

    /**
     * Configura los parámetros CORS para la aplicación.
     * 
     * Define las rutas, métodos, orígenes y cabeceras permitidas, así como
     * el tiempo máximo de duración de la caché para los resultados de la comprobación
     * previa de CORS y la política de credenciales.
     * 
     * @param registry el registro CORS donde se añadirán las configuraciones.
     */
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
