package com.alten.practica.modelo.entidad.dto.request;

import jakarta.validation.constraints.Email;
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
public class ClienteDTORequest {


	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre debe tener entre 2 y 250 caracteres")
	private String nombre;

	@NotNull(message = "Los apellidos no pueden ser nulo")
	@NotBlank(message = "Los apellidos no pueden estar vacíos")
	@Size(min = 2, max = 250, message = "Los apellidos deben tener entre 2 y 250 caracteres")
	private String apellidos;
	
	@NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
	private String email;
	
	private String password;

	private int nivelPermiso;

}
