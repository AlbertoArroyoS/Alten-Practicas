package com.alten.practica.modelo.entidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data Transfer Object (DTO) para manejar la respuesta de autenticación.
 * Contiene el token de autenticación.
 * 
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {

	private String token;
	private Long idUsuario;
	private String username;
}