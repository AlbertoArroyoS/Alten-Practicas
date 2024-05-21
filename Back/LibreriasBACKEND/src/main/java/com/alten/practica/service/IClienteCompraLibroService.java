package com.alten.practica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	// Metodo para listar todas las compras de libros
	public Page<ClienteCompraLibroDTO> findAll(Pageable pageable);

	// Metodo para actualizar un autor
	public HrefEntityDTO update(ClienteCompraLibroDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);
	
	//Metodo para listar las compras de un cliente
	public Page<ClienteCompraLibroDTO> findByCliente(int idCliente, Pageable pageable);

}
