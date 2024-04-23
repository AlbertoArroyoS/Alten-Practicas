package com.alten.practica.service;

import java.util.List;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;

/**
 * Interfaz que define los metodos de la clase ClienteCompraLibroService
 * 
 */
public interface IClienteCompraLibroService {

	// Metodo para guardar un autor
	public HrefEntityDTO save(ClienteCompraLibroDTORequest dto);

	// Metodo para buscar por id
	public ClienteCompraLibroDTO findById(int id);

	// Metodo para listar todas los autores
	public List<ClienteCompraLibroDTO> findAll();

	// Metodo para actualizar un autor
	public HrefEntityDTO update(ClienteCompraLibroDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);

}
