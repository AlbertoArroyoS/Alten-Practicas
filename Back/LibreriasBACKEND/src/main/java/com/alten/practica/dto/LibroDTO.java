package com.alten.practica.dto;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titulo;
	private String nombreAutor;
	private String apellidosAutor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;
    private double precio;
    
  
    
}
