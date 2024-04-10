package com.alten.practica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.service.LibreriaService;
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;




/**
 * Clase que implementa la interfaz LibreriaService
 * 
 * @see com.alten.practica.service.LibreriaService
 * 
 * Ruta para acceder a una libreria por su id:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para listar todas las librerias:
 * http://localhost:8080/v1/app-libreria/librerias/libreria
 * Ruta para editar una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para crear una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria
 * 
 */
@Service
public class LibreriaServiceImpl implements LibreriaService{
	
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
