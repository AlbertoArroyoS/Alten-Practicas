package com.alten.practica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alten.practica.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/*
 * Clase de configuración de seguridad que establece las reglas de seguridad
 * y la configuración del filtro de autenticación JWT.
 * 
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro de autenticación JWT
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Proveedor de autenticación personalizado
    private final AuthenticationProvider authProvider;

    /*
     * Define la cadena de filtros de seguridad y las reglas de configuración de seguridad.
     * 
     * @param http Objeto HttpSecurity utilizado para construir la configuración de seguridad
     * @return La cadena de filtros de seguridad configurada
     * @throws Exception En caso de errores de configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilita la protección CSRF
                .csrf(csrf -> csrf.disable())
                
                // Configura las reglas de autorización de solicitudes
                .authorizeHttpRequests(authRequest -> 
                	authRequest
                	//Permitir solicitures get y options
                	.requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                    // Permite todas las solicitudes a las rutas que comienzan con "/auth/"
                    .requestMatchers("/auth/**").permitAll()
                    // Requiere autenticación para cualquier otra solicitud
                    .anyRequest().authenticated())
                
                // Configura la gestión de sesiones como STATELESS
                .sessionManagement(sessionManager -> sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Establece el proveedor de autenticación personalizado
                .authenticationProvider(authProvider)
                
                // Añade el filtro de autenticación JWT antes del filtro de autenticación por usuario y contraseña
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                
                // Construye la cadena de filtros de seguridad
                .build();
    }
}
