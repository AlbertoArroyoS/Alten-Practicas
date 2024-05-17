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
	
	@NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

	private byte enabled;

	private Role role;


}
