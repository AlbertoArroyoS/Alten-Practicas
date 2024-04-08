package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBRERIAS, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class Libreria {
	
	@Id
	@Column(name = "id_libreria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre_libreria")
	private String nombreLibreria;
	@Column(name = "nombre_dueno")
	private String nombreDueno;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "nivel_permiso")
	private int nivelPermiso;
	
	@OneToMany(mappedBy = "libreria")
	private List<LibreriaLibro> libreriaLibros;

	
	public Libreria() {
		super();
	}
	
	

}
