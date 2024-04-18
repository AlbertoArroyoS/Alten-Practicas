package com.alten.practica.dto;

import java.io.Serializable;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.Libro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibreriaLibroDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	

	    private int id;

	    private Libreria libreria;

	    private Libro libro;

	    private int cantidad;

	    private double precio;

}
