package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la entidad Libro.
 * Información que se envia al cliente desde el servidor.
 *
 * Esta clase contiene información simplificada de un libro, incluyendo su
 * identificador único, título, nombre y apellidos del autor, género, número de
 * páginas, editorial y descripción.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String titulo;
	private String nombreAutor;
	private String apellidosAutor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;

}
