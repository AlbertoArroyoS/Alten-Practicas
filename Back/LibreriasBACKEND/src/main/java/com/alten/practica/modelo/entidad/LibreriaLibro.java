package com.alten.practica.modelo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "libreria_libro", schema = "dbo")
public class LibreriaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "libreria_id")
    private Libreria libreria;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    
    private int cantidad;
    
    private double precio;

	public LibreriaLibro() {
		super();
	}
}

