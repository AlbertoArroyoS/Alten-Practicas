package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 144219358491494626L;
	private int id;
	private String nombre;
	

}
