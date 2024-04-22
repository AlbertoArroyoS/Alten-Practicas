package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaLibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private int id;
	private int idLibro; // Identificador del libro   
    private String tituloLibro; // Título del libro
    private String nombreLibreria; // Nombre de la librería
    private int idLibreria;
    private int cantidad;
    private double precio;
    private int edicion;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaPublicacion;


}
