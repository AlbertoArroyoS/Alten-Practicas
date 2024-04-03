package com.alten.practica.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

//***********ESTA CLASE ES DE PRUEBA***********
//Tabla de permisos para poder loguearse segun el rol


@Data
@ToString
@Entity
@Table(name = "authorities", schema = "practica")
public class Permiso {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "usermane")
	private String username;
	@Column(name = "authority")
	private String authority;
	
	public Permiso() {
		super();
	}

}
