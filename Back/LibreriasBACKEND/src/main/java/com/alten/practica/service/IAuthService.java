package com.alten.practica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;

/*
 * Interfaz para el servicio de autenticación.
 * Define métodos para registrar y autenticar usuarios.
 */
public interface IAuthService {

	// Metodo para registrar un usuario
	public HrefEntityDTO register(RegisterDTORequest request);

	// Metodo para loguear un usuario
	public AuthDTO login(LoginDTORequest request);

	public HrefEntityDTO registerAdmin(UsuarioDTORequest dto);
	
	public HrefEntityDTO updateAdmin(UsuarioDTORequest dto, int id);
	
	public HrefEntityDTO updateUser(RegisterDTORequest request, int id);
	
	public UsuarioDTO findById(int id);
	
	public Page<UsuarioDTO> findAll(Pageable pageable);

	

}
