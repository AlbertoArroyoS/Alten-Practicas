package com.alten.practica.modelo.entidad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}