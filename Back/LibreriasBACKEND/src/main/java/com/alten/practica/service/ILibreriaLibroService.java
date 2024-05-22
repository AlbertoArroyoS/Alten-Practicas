package com.alten.practica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;

/**
 * Interfaz que define los metodos de la clase LibreriaLibroService
 * 
 */
public interface ILibreriaLibroService {

	// Metodo para guardar un autor
	public HrefEntityDTO save(LibreriaLibroDTORequest dto);

	// Metodo para buscar por id
	public LibreriaLibroDTO findById(int id);

	// Metodo para listar todas los autores
	public Page<LibreriaLibroDTO> findAll(Pageable pageable);

	// Metodo para actualizar un autor
	public HrefEntityDTO update(LibreriaLibroDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);

	void disminuirCantidadLibro(int idLibreriaLibro);

	Page<LibreriaLibroDTO> findByTituloContaining(String titulo, Pageable pageable);

}
