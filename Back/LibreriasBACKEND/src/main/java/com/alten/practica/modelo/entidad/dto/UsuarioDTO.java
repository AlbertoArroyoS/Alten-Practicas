package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import com.alten.practica.util.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable{
	

	private static final long serialVersionUID = 2749876341817710218L;

	private int idUsuario;

	private String username;

	private byte enabled;

	private Role role;

	private int idCliente;

	private String nombre;

	private String apellidos;
	
	private int idLibreria;

	private String nombreLibreria;

	private String nombreDueno;

	private String direccion;
	
	private String email;


}
