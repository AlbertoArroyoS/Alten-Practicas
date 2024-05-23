package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la relación entre una
 * librería y un libro. Información que se envia al cliente desde el servidor.
 *
 * Esta clase contiene información simplificada sobre la relación entre una
 * librería y un libro, incluyendo su identificador único, identificador del
 * libro, título del libro, nombre de la librería, identificador de la librería,
 * cantidad disponible en la librería, precio, edición y fecha de publicación.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaLibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int idLibro; // Identificador del libro
	private String tituloLibro; // Título del libro
	private String nombreLibreria; // Nombre de la librería
	private int idLibreria;
	private int cantidad;
	private double precio;
	private int edicion;
	private String nombreAutor;
	private String apellidosAutor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date fechaPublicacion;

}
