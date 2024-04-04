package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.context.annotation.Bean;
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
		return this.libreriaRepository.save(libreria);
	}


	@Override
	public Libreria findById(int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		return bean;
	}

	@Override
	public List<Libreria> findAll() {
		return this.libreriaRepository.findAll();
	}

	@Override
	public Libreria update(Libreria libreria, int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		bean.setNombreLibreria(libreria.getNombreLibreria());
        return this.libreriaRepository.save(bean);
	}

}
