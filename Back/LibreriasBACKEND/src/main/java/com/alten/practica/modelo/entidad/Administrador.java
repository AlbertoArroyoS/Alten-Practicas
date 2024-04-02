package com.alten.practica.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "administradores", schema = "practica")
public class Administrador{
	
	@Id
	@Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre")
	@JoinColumn(name = "nombre")
	private String nombre;
	
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "rol")
	private String rol;
	@Column(name = "nivel_permiso")
	private int nivelPermiso;
	
	public Administrador() {
		super();
	}

	
	

}
