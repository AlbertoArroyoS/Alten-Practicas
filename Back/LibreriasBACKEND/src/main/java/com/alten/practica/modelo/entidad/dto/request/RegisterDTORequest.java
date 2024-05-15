package com.alten.practica.modelo.entidad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Data Transfer Object (DTO) para manejar las solicitudes de registro.
 * Contiene información del usuario y detalles de la librería.
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
public class RegisterDTORequest {
	
    private String username;

    private String password;

	private String nombre;

	private String apellidos;

	private String email;
	
	private String nombreLibreria;
	
	private String nombreDueno;
	
	private String direccion;
	
	private String ciudad;

}