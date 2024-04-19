package com.alten.practica.service;

import java.util.List;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;

public interface ILibreriaLibroService {

	// Metodo para guardar un autor
	public HrefEntityDTO save(LibreriaLibroDTORequest dto);

	// Metodo para buscar por id
	public LibreriaLibroDTO findById(int id);

	// Metodo para listar todas los autores
	public List<LibreriaLibroDTO> findAll();

	// Metodo para actualizar un autor
	public HrefEntityDTO update(LibreriaLibroDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);

}
