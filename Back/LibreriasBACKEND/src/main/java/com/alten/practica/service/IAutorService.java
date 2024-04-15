package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;

public interface IAutorService {

	// Metodo para guardar un autor
	public AutorDTO save(AutorDTORequest dto);

	// Metodo para guardar un autor Cargando Procedimiento SQL
	public AutorDTO saveAutorSQL(AutorDTORequest dto);

	// Metodo para buscar por id
	public AutorDTO findById(int id);

	// Metodo para listar todas los autores
	public List<AutorDTO> findAll();

	// Metodo para actualizar un autor
	public int update(AutorDTORequest dto, int id);

	// Metodo para borrar un autor
	public void delete(int id);

	// Metodo para buscar por key_word, Cargando Procedimiento SQL, por palabra
	// clave
	public List<AutorDTO> buscarKeyWordSQL(String nombre);

	// Metodo para obtener id del autor
	public int obtenerIdAutor(String nombreCompleto);

}
