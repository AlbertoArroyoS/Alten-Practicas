package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;


public interface AutorService {
	
	public List<AutorDTO> buscarKeyWordSQL(String nombre);
	public AutorDTO nuevoAutorSQL(AutorDTORequest dto);
	//buscar por id
	public AutorDTO buscarPorId(int id);

}
