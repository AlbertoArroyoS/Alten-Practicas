package com.alten.practica.service;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;

import jakarta.validation.Valid;

/**
 * Interfaz que define los metodos de la clase UsuarioService
 * 
 */
public interface IUsuarioService {

	// Metodo para buscar por id
	public UsuarioDTO findById(int id);

	// Metodo para actualizar un usuario
	public HrefEntityDTO update(UsuarioDTORequest dto, int id);

	// Metodo para guardar un autor
	public HrefEntityDTO save(@Valid UsuarioDTORequest dto);

}
