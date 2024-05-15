package com.alten.practica.modelo.entidad.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTORequest {
	
	@NotNull(message = "El nombre de usuario no puede ser nulo")
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
    String username;
	
	@NotNull(message = "El password no puede ser nulo")
	@NotBlank(message = "El password no puede estar vacío")
    String password; 
}
