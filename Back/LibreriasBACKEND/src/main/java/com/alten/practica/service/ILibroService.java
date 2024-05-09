package com.alten.practica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibroDTORequest;

/**
 * Interfaz que define los metodos de la clase LibroService
 * 
 */
public interface ILibroService {

	// Metodo para guardar un libro
	public HrefEntityDTO save(LibroDTORequest dto);

	// Metodo para guardar un libro Cargando Procedimiento SQL
	public int saveLibroSQL(LibroDTORequest dto);

	// Metodo para buscar por id
	public LibroDTO findById(int id);

	// Metodo para listar todas los libros
	public Page<LibroDTO> findAll(Pageable pageable);

	// Metodo para actualizar un libro
	public HrefEntityDTO update(LibroDTORequest dto, int id);

	// Metodo para borrar un libro
	public HrefEntityDTO delete(int id);

	// Metodo para buscar por key_word, por palabra clave
	public Page<LibroDTO> findByTitle(String title, Pageable pageable);

}
