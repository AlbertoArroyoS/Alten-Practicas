package com.alten.practica.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class Libreria {
	
	@Id
	@Column(name = "id_libreria")
	private int id;
	private String nombreLibreria;
	private String nombreDueno;
	private String direccion;
	private String ciudad;
	
	public Libreria() {
		super();
	}
	
	

}
