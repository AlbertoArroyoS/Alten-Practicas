package com.alten.practica.dto;


import com.alten.practica.modelo.entidad.Autor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LibroDTO {

	private String titulo;
	private String nombreAutor;
	private String apellidosAutor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;
    private double precio;
    
    
    public LibroDTO() {
		super();
	}
    
    
	public LibroDTO(String titulo, String nombreAutor, String apellidoAutor, String genero, int paginas,
			String editorial, String descripcion, double precio) {
		super();
		this.titulo = titulo;
		this.nombreAutor = nombreAutor;
		this.apellidosAutor = apellidoAutor;
		this.genero = genero;
		this.paginas = paginas;
		this.editorial = editorial;
		this.descripcion = descripcion;
		this.precio = precio;
	}

    
    
}
