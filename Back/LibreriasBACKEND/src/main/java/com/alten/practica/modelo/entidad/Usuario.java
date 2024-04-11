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
//Tabla de usuarios para poder loguearse

@Data
@ToString
@Entity
@Table(name = "users", schema = "dbo")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "usermane")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private byte enabled;
	
	public Usuario() {
		super();
	}


	

}
