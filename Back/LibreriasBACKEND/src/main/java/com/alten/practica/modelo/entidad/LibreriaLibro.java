package com.alten.practica.modelo.entidad;

import jakarta.persistence.Column;
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
    @Column(name = "id_libreria_libro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_libreria", referencedColumnName = "id_libreria")
    private Libreria libreria;

    @ManyToOne
    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
    private Libro libro;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio")
    private double precio;

	public LibreriaLibro() {
		super();
	}
}

