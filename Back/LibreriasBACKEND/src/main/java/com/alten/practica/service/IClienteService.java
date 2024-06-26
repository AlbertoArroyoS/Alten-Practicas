package com.alten.practica.service;

import java.util.List;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;

/**
 * Interfaz que define los metodos de la clase ClienteService
 * 
 */
public interface IClienteService {

	// Metodo para guardar un autor
	public HrefEntityDTO save(ClienteDTORequest dto);

	// Metodo para buscar por id
	public ClienteDTO findById(int id);

	// Metodo para listar todas los autores
	public List<ClienteDTO> findAll();

	// Metodo para actualizar un autor
	public HrefEntityDTO update(ClienteDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);

}
