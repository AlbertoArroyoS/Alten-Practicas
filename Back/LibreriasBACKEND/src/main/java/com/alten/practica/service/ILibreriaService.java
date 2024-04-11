package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;

public interface ILibreriaService {

	// Metodo para guardar una libreria
	public int save(LibreriaDTORequest dto);

	// Metodo para buscar por id
	public LibreriaDTO findById(int id);

	// Metodo para listar todas las librerias
	public List<LibreriaDTO> findAll();
	
	// Metodo para actualizar una libreria
	public int update(LibreriaDTORequest dto, int id);

	// Metodo para borrar una libreria
	public void delete(int id);

}
