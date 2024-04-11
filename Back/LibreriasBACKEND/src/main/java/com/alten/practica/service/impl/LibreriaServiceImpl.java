package com.alten.practica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.service.ILibreriaService;
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;




/**
 * Clase que implementa la interfaz LibreriaService
 * 
 * @see com.alten.practica.service.ILibreriaService
 * 
 * 
 */
@Transactional
@Service
public class LibreriaServiceImpl implements ILibreriaService{
	
	//inyeccion por constructor del repositorio de la libreria	
	@Autowired
	ILibreriaRepository libreriaRepository;
	


	@Override
	public int save(LibreriaDTORequest dto) {
		Libreria libreria = new Libreria();
		libreria.setNombreLibreria(dto.getNombre());
		return this.libreriaRepository.save(libreria).getId();
		
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
	public int update(LibreriaDTORequest dto, int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		bean.setId(id);
		bean.setNombreLibreria(dto.getNombre());
        return this.libreriaRepository.save(bean).getId();
	}


}
