package com.alten.practica.modelo.entidad.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Capturar los datos de la peticion que vienen en el body
//atributos que nos envia el cliente
/**
 * Clase que representa un DTO (Data Transfer Object) para capturar los datos de
 * una solicitud de libro. Información que enviará el cliente al servidor.
 *
 * Esta clase contiene atributos que se envían en el cuerpo de una petición
 * HTTP, los cuales deben cumplir ciertas validaciones, como no ser nulos, no
 * estar vacíos y cumplir con una longitud mínima y máxima.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTORequest {

	// VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
	@NotNull(message = "El titulo no puede ser nulo")
	@NotBlank(message = "El titulo no puede estar vacío")
	@Size(min = 2, max = 250, message = "El titulo debe tener entre 2 y 250 caracteres")
	private String titulo;

	@NotNull(message = "El nombre del autor no puede ser nulo")
	@NotBlank(message = "El nombre del autor no puede estar vacío")
	@Size(min = 2, max = 250, message = "El nombre del autor debe tener entre 2 y 250 caracteres")
	private String nombreAutor;

	@NotNull(message = "El apellido del autor no puede ser nulo")
	@NotBlank(message = "El apellido del autor no puede estar vacío")
	@Size(min = 2, max = 250, message = "El apellido del autor debe tener entre 2 y 250 caracteres")
	private String apellidosAutor;

	private String genero;

	private int paginas;

	private String editorial;

	private String descripcion;

}
