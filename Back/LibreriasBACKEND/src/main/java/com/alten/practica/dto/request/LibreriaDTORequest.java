package com.alten.practica.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO para usar en Spring para manejar solicitudes 
 * HTTP de creación o actualización de una librería, 
 * donde los datos del cuerpo de la solicitud deben validarse antes de ser utilizados.
 */
//Capturar los datos de la peticion que vienen en el body
//atributos que nos envia el cliente
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaDTORequest {
	
		// LOS ATRIBUTOS NO SEAN NULOS.
		@NotNull

		// LOS ATRIBUTOS NO SEAN VACIOS.
		@NotEmpty

		// LOS ATRIBUTOS NOS SEAN NULAS NI BACIOS.
		@NotBlank
		
		//VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
		@Size(min = 5,max = 100)
		private String nombreLibreria;
		private String nombreDueno;
		private String direccion;
		private String ciudad;

}
