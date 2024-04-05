package com.alten.practica.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.LibreriaRepository;
import com.alten.practica.service.LibreriaService;
import com.alten.practica.dto.LibreriaDTO;

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
	public LibreriaDTO findById(int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		LibreriaDTO libreriaDTO = new LibreriaDTO();
		libreriaDTO.setId(bean.getId());
		libreriaDTO.setNombre(bean.getNombreLibreria());
		return libreriaDTO;
	}

	@Override
	public List<LibreriaDTO> findAll() {
		List<Libreria> lista = this.libreriaRepository.findAll();
		List<LibreriaDTO> listaDTO = new ArrayList<>();
		for (Libreria bean : lista) {
			LibreriaDTO libreriaDTO = new LibreriaDTO();
			libreriaDTO.setId(bean.getId());
			libreriaDTO.setNombre(bean.getNombreLibreria());
			listaDTO.add(libreriaDTO);
		}
		return listaDTO;
	}

	@Override
	public Libreria update(Libreria libreria, int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		bean.setNombreLibreria(libreria.getNombreLibreria());
        return this.libreriaRepository.save(bean);
	}

}
