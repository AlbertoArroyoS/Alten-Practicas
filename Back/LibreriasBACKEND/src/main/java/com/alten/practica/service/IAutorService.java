package com.alten.practica.service;

import java.util.List;
import java.util.Optional;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;

public interface IAutorService {

	// Metodo para guardar un autor
	public HrefEntityDTO save(AutorDTORequest dto);

	// Metodo para guardar un autor Cargando Procedimiento SQL
	public AutorDTO saveAutorSQL(AutorDTORequest dto);

	// Metodo para buscar por id
	public AutorDTO findById(int id);

	// Metodo para listar todas los autores
	public List<AutorDTO> findAll();

	// Metodo para actualizar un autor
	public HrefEntityDTO update(AutorDTORequest dto, int id);

	// Metodo para borrar un autor
	public HrefEntityDTO delete(int id);

	// Metodo para buscar por key_word, Cargando Procedimiento SQL, por palabra
	// clave
	public List<AutorDTO> buscarKeyWordSQL(String nombre);

	// Metodo para obtener id del autor
	public int obtenerIdAutor(String nombreCompleto);
	
	//Metodo para buscar por nombre
	public Optional<AutorDTO> findByName(String nombre, String apellidos);

}
