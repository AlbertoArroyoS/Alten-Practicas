package com.alten.practica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alten.practica.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

/*
 * Clase de configuración de la aplicación que define beans para la gestión de autenticación y usuarios.
 * 
 * @see org.springframework.context.annotation.Configuration
 */
@Configuration
@RequiredArgsConstructor
public class AplicationConfig {

    // Repositorio de usuarios para acceder a los datos de usuario
    private final IUsuarioRepository userRepository;

    /*
     * Define el bean AuthenticationManager que gestiona la autenticación.
     * 
     * @param config Configuración de autenticación
     * @return El AuthenticationManager configurado
     * @throws Exception En caso de errores de configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     * Define el bean AuthenticationProvider que gestiona la autenticación basada en DAO.
     * 
     * @return El AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /*
     * Define el bean PasswordEncoder que se utiliza para codificar las contraseñas.
     * 
     * @return El PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Define el bean UserDetailsService que se utiliza para cargar los detalles del usuario.
     * 
     * @return El UserDetailsService configurado
     */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}