package com.alten.practica.service;

import java.util.List;

import com.alten.practica.modelo.entidad.Libreria;

public interface LibreriaService {
	
	public Libreria save(Libreria libreria);
	
	public Libreria update (Libreria libreria, int id);
	
	public Libreria findById(int id);
	
	public List<Libreria> findAll();

}
