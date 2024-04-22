package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la entidad Autor.
 * Información que se envia al cliente desde el servidor.
 * 
 * Esta clase contiene información simplificada de un autor, incluyendo su
 * identificador único y nombre.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO implements Serializable {

	private static final long serialVersionUID = 144219358491494626L;
	private int id;
	private String nombre;

}
