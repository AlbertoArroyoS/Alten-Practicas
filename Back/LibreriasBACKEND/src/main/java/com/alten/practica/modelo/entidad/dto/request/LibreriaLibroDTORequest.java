package com.alten.practica.modelo.entidad.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para capturar los datos de
 * una solicitud de libro en una librería. Información que enviará el cliente al
 * servidor.
 *
 * Esta clase contiene atributos que se envían en el cuerpo de una petición
 * HTTP, los cuales deben cumplir ciertas validaciones, como no ser nulos y
 * cumplir con un valor mínimo para el precio.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaLibroDTORequest {

	@NotNull(message = "El id no puede ser nulo")
	private int idLibreria;

	@NotNull(message = "El id no puede ser nulo")
	private int idLibro;

	private int cantidad;

	@DecimalMin(value = "0.01", message = "El precio debe ser al menos 0.01")
	private double precio;

	private int edicion;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date fechaPublicacion;

}
