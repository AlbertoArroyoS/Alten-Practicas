package com.alten.practica.modelo.entidad.dto.request;

import com.alten.practica.util.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTORequest {
	
	

	private String username;

	private byte enabled;

	private Role role;


}
