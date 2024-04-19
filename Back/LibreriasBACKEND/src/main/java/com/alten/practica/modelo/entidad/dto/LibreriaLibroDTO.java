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
public class LibreriaLibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private int id;
    private String nombreLibreria; // Nombre de la librería
    private String tituloLibro; // Título del libro
    private int cantidad;
    private double precio;


}
