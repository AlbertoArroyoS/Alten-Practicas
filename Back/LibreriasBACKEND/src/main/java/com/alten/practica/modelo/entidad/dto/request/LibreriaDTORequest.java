package com.alten.practica.modelo.entidad.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO para usar en Spring para manejar solicitudes HTTP
 * de creación o actualización de una librería, donde los datos del cuerpo de la
 * solicitud deben validarse antes de ser utilizados.
 */
//Capturar los datos de la peticion que vienen en el body
//atributos que nos envia el cliente
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaDTORequest {

	// LOS ATRIBUTOS NO SEAN NULOS. @NotNull

	// LOS ATRIBUTOS NO SEAN VACIOS. @NotEmpty

	// LOS ATRIBUTOS NOS SEAN NULAS NI BACIOS. @NotBlank

	// VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
	@NotNull(message = "El nombre de la libreria no puede ser nulo")
	@NotBlank(message = "El nombre de la libreria no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre de la libreria debe tener entre 2 y 250 caracteres")
	private String nombreLibreria;
	
	@NotNull(message = "El nombre del dueño no puede ser nulo")
	@NotBlank(message = "El nombre del dueño no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre del dueño debe tener entre 2 y 250 caracteres")
	private String nombreDueno;
	
	@NotNull(message = "La dirección no puede ser nula")
	@NotBlank(message = "La dirección no puede estar vacía")
	@Size(min = 2, max = 250, message = "La dirección debe tener entre 2 y 250 caracteres")
	private String direccion;
	
	@NotNull(message = "El nombre de la ciudad no puede ser nulo")
	@NotBlank(message = "El nombre de la ciudad no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre de la ciudad debe tener entre 2 y 250 caracteres")
	private String ciudad;

}
