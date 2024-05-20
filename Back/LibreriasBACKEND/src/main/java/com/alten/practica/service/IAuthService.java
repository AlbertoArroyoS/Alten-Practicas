package com.alten.practica.service;

import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;

/*
 * Interfaz para el servicio de autenticación.
 * Define métodos para registrar y autenticar usuarios.
 */
public interface IAuthService {

	// Metodo para registrar un usuario
	public AuthDTO register(RegisterDTORequest request);

	// Metodo para loguear un usuario
	public AuthDTO login(LoginDTORequest request);

	public AuthDTO registerAdmin(UsuarioDTORequest dto);

}
