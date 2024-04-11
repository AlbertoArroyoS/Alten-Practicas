package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;


public interface IAutorService {
	
	public List<AutorDTO> buscarKeyWordSQL(String nombre);
	public AutorDTO nuevoAutorSQL(AutorDTORequest dto);
	//buscar por id
	public AutorDTO buscarPorId(int id);
	//obtener id del autor
	public int obtenerIdAutor(String nombreCompleto);

}
