package com.alten.practica.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Clase que representa un DTO para usar en Spring una entidad de datos que se 
 * utiliza para transferir información relacionada con una librería. 
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1219294407338278479L;
	private int id;
	private String nombre;

}
