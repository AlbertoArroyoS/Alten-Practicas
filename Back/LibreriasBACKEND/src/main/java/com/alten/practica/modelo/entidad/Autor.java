package com.alten.practica.modelo.entidad;

import java.sql.Date;
import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_AUTORES, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class Autor {
	
    @Id
    @Column(name = "id_autor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    

    
    @OneToMany(mappedBy="autor", cascade = CascadeType.PERSIST)
    private List<Libro> libros;

	public Autor() {
		super();
	}
}
