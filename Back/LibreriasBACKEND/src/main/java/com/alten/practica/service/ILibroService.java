package com.alten.practica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.request.LibroDTORequest;
import com.alten.practica.errorhandler.HrefEntityDTO;

public interface ILibroService {

	// Metodo para guardar un libro
	public HrefEntityDTO save(LibroDTORequest dto);

	// Metodo para guardar un libro Cargando Procedimiento SQL
	public int saveLibroSQL(LibroDTORequest dto);

	// Metodo para buscar por id
	public LibroDTO findById(int id);

	// Metodo para listar todas los libros
	public List<LibroDTO> findAll();

	// Metodo para actualizar un libro
	public int HrefEntityDTO(LibroDTORequest dto, int id);

	// Metodo para borrar un libro
	public int HrefEntityDTO(int id);

	// Metodo para buscar por key_word, por palabra clave
	public Page<LibroDTO> findByTitle(String title, Pageable pageable);

}
