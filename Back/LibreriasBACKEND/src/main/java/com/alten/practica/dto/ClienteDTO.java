package com.alten.practica.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private int id;

	private String nombre;

	private String apellidos;

	private String email;

	private String password;

	private int nivelPermiso;

}
