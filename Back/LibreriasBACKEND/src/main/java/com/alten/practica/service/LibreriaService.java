package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.Libreria;

public interface LibreriaService {
	
	public Libreria save(Libreria libreria);
	
	public Libreria update (Libreria libreria, int id);
	
	public LibreriaDTO findById(int id);
	
	public List<LibreriaDTO> findAll();

}
