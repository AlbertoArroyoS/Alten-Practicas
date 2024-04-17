package com.alten.practica.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Capturar los datos de la peticion que vienen en el body
//atributos que nos envia el cliente
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTORequest {
	// LOS ATRIBUTOS NO SEAN NULOS. @NotNull

	// LOS ATRIBUTOS NO SEAN VACIOS. @NotEmpty

	// LOS ATRIBUTOS NOS SEAN NULAS NI BACIOS. @NotBlank

	// VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre debe tener entre 2 y 250 caracteres")
	private String nombre;

	@NotNull(message = "Los apellidos no pueden ser nulo")
	@NotBlank(message = "Los apellidos no pueden estar vacíos")
	@Size(min = 2, max = 250, message = "Los apellidos deben tener entre 2 y 250 caracteres")
	private String apellidos;

}
