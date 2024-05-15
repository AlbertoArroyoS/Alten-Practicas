package com.alten.practica.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alten.practica.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
/*
 * Componente de Spring que actúa como filtro de autenticación JWT.
 * Este filtro se ejecuta una vez por cada solicitud HTTP.
 * 
 * @see JwtService
 * @see UserDetailsService
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Servicio para manejar la lógica de JWT
    private final JwtService jwtService;

    // Servicio para cargar detalles del usuario
    private final UserDetailsService userDetailsService;

    // Método que se ejecuta una vez por cada solicitud para filtrar el JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene el token JWT de la solicitud HTTP
        final String token = getTokenFromRequest(request);
        final String username;

        // Si no hay token, continúa con el siguiente filtro en la cadena
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtiene el nombre de usuario desde el token
        username = jwtService.getUsernameFromToken(token);

        // Verifica que el nombre de usuario no sea nulo y que no haya autenticación previa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario usando el UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valida el token con los detalles del usuario
            if (jwtService.isTokenValid(token, userDetails)) {
                // Crea un token de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities());

                // Establece detalles adicionales para la autenticación
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

    // Método para obtener el token JWT de la cabecera de autorización de la solicitud
    private String getTokenFromRequest(HttpServletRequest request) {
        // Obtiene la cabecera de autorización
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verifica si la cabecera tiene texto y comienza con "Bearer "
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // Retorna el token sin el prefijo "Bearer "
            return authHeader.substring(7);
        }
        return null;
    }
}