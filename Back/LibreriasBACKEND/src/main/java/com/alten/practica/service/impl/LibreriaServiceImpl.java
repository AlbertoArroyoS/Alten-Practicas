package com.alten.practica.service.impl;



import java.util.List;
import java.util.stream.Collectors;

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
		libreria.setNombreLibreria(dto.getNombreLibreria());
		libreria.setNombreDueno(dto.getNombreDueno());
		libreria.setDireccion(dto.getDireccion());
		libreria.setCiudad(dto.getCiudad());
		return this.libreriaRepository.save(libreria).getId();
		
	}


	@Override
	public LibreriaDTO findById(int id) {
		/*
		Libreria bean = this.libreriaRepository.findById(id).get();
		LibreriaDTO libreriaDTO = new LibreriaDTO();
		libreriaDTO.setId(bean.getId());
		libreriaDTO.setNombre(bean.getNombreLibreria());
		return libreriaDTO;*/
		
		Libreria bean = this.libreriaRepository.findById(id).orElse(null); // Utiliza orElse(null) para evitar NullPointerException

	    if (bean != null) {
	        return convertirBeanADTO(bean); // Utiliza el método convertirBeanADTO para convertir el bean a DTO
	    } else {
	        return null; // Devuelve null si el bean no se encuentra en la base de datos
	    }
	}

	@Override
	public List<LibreriaDTO> findAll() {
		List<Libreria> lista = this.libreriaRepository.findAll();
		/*
		 * Sin funciones lambda y streams
		List<LibreriaDTO> listaDTO = new ArrayList<>();
		for (Libreria bean : lista) {
			LibreriaDTO libreriaDTO = new LibreriaDTO();
			libreriaDTO.setId(bean.getId());
			libreriaDTO.setNombre(bean.getNombreLibreria());
			listaDTO.add(libreriaDTO);
		}
		return listaDTO;*/
		
		/*Con funciones lambda y streams
		 * return lista.stream(
				.map(bean -> convertirBeanADTO(bean)
				.collect(Collectors.toList());*/
		//
		return lista.stream()
                .map(this::convertirBeanADTO) // Utiliza una referencia a método para convertir de bean a DTO
                .collect(Collectors.toList()); // Corrige la capitalización de "Collectors.toList()"
	}

	@Override
	public int update(LibreriaDTORequest dto, int id) {
		Libreria bean = this.libreriaRepository.findById(id).get();
		bean.setId(id);
		bean.setNombreLibreria(dto.getNombreLibreria());
		bean.setNombreDueno(dto.getNombreDueno());
		bean.setDireccion(dto.getDireccion());
		bean.setCiudad(dto.getCiudad());
        return this.libreriaRepository.save(bean).getId();
	}
	
	public LibreriaDTO convertirBeanADTO(Libreria bean) {
		return LibreriaDTO.builder()
				.id(bean.getId())
				.nombreLibreria(bean.getNombreLibreria())
				.nombreDueno(bean.getNombreDueno())
				.direccion(bean.getDireccion())
				.ciudad(bean.getCiudad())
				.build();
				
				
	}
	

}
