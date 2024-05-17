package com.alten.practica.modelo.entidad.dto.request;

import com.alten.practica.util.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTORequest {
	
	
	@NotNull(message = "El nombre de usuario no puede ser nulo")
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre de usuario debe tener entre 2 y 250 caracteres")
	private String username;

	private byte enabled;

	private Role role;


}
