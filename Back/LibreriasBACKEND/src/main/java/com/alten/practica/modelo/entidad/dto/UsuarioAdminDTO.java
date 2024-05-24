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
public class UsuarioAdminDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -9117000623952262517L;
	
	private String username;

	private byte enabled;

	private Role role;
	
	

}
