package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la entidad Libreria.
 * Información que se envia al cliente desde el servidor.
 *
 * Esta clase contiene información simplificada de una librería, incluyendo su
 * identificador único, nombre de la librería, nombre del dueño, dirección,
 * ciudad y correo electrónico.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219294407338278479L;
	private int id;
	private String nombreLibreria;
	private String nombreDueno;
	private String direccion;
	private String ciudad;
	private String email;

}
