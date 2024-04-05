package com.alten.practica.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que representa un DTO para usar en Spring para manejar solicitudes 
 * HTTP de creación o actualización de una librería, 
 * donde los datos del cuerpo de la solicitud deben validarse antes de ser utilizados.
 */

@Data
public class LibreriaDTORequest {
	
	// LOS ATRIBUTOS NO SEAN NULOS.
		@NotNull

		// LOS ATRIBUTOS NO SEAN BACIOS.
		@NotEmpty

		// LOS ATRIBUTOS NOS SEAN NULAS NI BACIOS.
		@NotBlank
		
		//VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
		@Size(min = 5,max = 100)
		private String nombre;

}
