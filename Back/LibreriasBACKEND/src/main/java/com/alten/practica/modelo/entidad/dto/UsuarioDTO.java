package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.util.Role;

public class UsuarioDTO implements Serializable{
	

	private static final long serialVersionUID = 2749876341817710218L;

	private int id;

	private String username;

	private byte enabled;

	private Role role;

	private Cliente cliente;

	private Libreria libreria;

}
