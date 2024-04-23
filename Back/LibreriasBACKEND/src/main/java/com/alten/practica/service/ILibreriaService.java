package com.alten.practica.service;

import java.util.List;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;

/**
 * Interfaz que define los metodos de la clase LibreriaService
 * 
 */
public interface ILibreriaService {

	// Metodo para guardar una libreria
	public HrefEntityDTO save(LibreriaDTORequest dto);

	// Metodo para buscar por id
	public LibreriaDTO findById(int id);

	// Metodo para listar todas las librerias
	public List<LibreriaDTO> findAll();

	// Metodo para actualizar una libreria
	public HrefEntityDTO update(LibreriaDTORequest dto, int id);

	// Metodo para borrar una libreria
	public HrefEntityDTO delete(int id);

}
