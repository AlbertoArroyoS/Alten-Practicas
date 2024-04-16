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
	private int id;
	private String titulo;
	private String nombreAutor;
	private String apellidosAutor;
	private String genero;
	private int paginas;
	private String editorial;
	private String descripcion;
    private double precio;
    
  
    /*
     * En LibroMapper para acceder a los datos del autor poner
     *  libroDTO.setNombreAutor( libro.getAutor().getNombre() );
        libroDTO.setApellidosAutor( libro.getAutor().getApellidos() );
        
        
        
        LibroDTO libroDTO = new LibroDTO();

        libroDTO.setId( libro.getId() );
        libroDTO.setTitulo( libro.getTitulo() );
        libroDTO.setNombreAutor( libro.getAutor().getNombre() );
        libroDTO.setApellidosAutor( libro.getAutor().getApellidos() );
        libroDTO.setGenero( libro.getGenero() );
        libroDTO.setPaginas( libro.getPaginas() );
        libroDTO.setEditorial( libro.getEditorial() );
        libroDTO.setDescripcion( libro.getDescripcion() );
        libroDTO.setPrecio( libro.getPrecio() );
     */
    
}
