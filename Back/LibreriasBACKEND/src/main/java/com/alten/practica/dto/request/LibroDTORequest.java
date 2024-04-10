package com.alten.practica.dto.request;



import com.alten.practica.modelo.entidad.Autor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LibroDTORequest {
	
	private String titulo;
	private String nombreAutor;
	private String apellidosAutor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;
    private double precio;

}
