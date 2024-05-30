package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la entidad Cliente.
 * Información que se envia al cliente desde el servidor.
 *
 * Esta clase contiene información simplificada de un cliente, incluyendo su
 * identificador único, nombre, apellidos, correo electrónico, contraseña y
 * nivel de permiso.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String nombre;

	private String apellidos;

	private String email;


}
