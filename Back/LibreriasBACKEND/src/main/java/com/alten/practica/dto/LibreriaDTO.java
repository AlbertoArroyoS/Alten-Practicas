package com.alten.practica.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * Clase que representa un DTO para usar en Spring una entidad de datos que se 
 * utiliza para transferir información relacionada con una librería. 
*/
@Data
public class LibreriaDTO implements Serializable{
	
	private int id;
	private String nombre;

}
