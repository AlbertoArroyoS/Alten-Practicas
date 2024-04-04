package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.LibreriaRepository;
import com.alten.practica.service.LibreriaService;

@Service
public class LibreriaServiceImpl implements LibreriaService{
	
	//inyeccion por constructor del repositorio de la libreria	
	
	LibreriaRepository libreriaRepository;
	
	public LibreriaServiceImpl(LibreriaRepository libreriaRepository) {
		this.libreriaRepository = libreriaRepository;
	}

	@Override
	public Libreria save(Libreria libreria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Libreria update(Libreria libreria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Libreria findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Libreria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
