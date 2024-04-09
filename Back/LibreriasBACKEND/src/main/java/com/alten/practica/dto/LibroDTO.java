package com.alten.practica.dto;


import com.alten.practica.modelo.entidad.Autor;
import lombok.Builder;
import lombok.Data;


@Data
public class LibroDTO {

	private String titulo;
	private Autor autor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;
    private double precio;
    
}
