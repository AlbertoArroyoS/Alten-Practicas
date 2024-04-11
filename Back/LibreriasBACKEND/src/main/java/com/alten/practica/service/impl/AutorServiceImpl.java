package com.alten.practica.service.impl;

import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.repository.IAutorRepository;

import com.alten.practica.service.IAutorService;

@Transactional
@Service
public class AutorServiceImpl implements IAutorService{
	
	//inyectamos el repositorio del autor
	@Autowired
	IAutorRepository autorRepository;
	
	
	//Metodo para convertir de entidad a dto	
	private AutorDTO convertirBeanADTO(Autor autor) {
		return AutorDTO.builder()
				.id(autor.getId())
				.nombre(autor.getNombre() + " " + autor.getApellidos())
				.build();
	}

	
	@Override
	public List<AutorDTO> buscarKeyWordSQL(String nombre) {
		List<Autor> listaAutores = this.autorRepository.buscarKeyWordSQL(nombre);
		return listaAutores.stream()
				.map((bean) -> convertirBeanADTO(bean))
				.collect(Collectors.toList());

	}



	@Override
	public AutorDTO nuevoAutorSQL(AutorDTORequest dto) {		
		//convertir a dto con el metodo convertirEntidadADto
		return convertirBeanADTO(this.autorRepository.nuevoAutorSQL(dto.getNombre(), dto.getApellidos()));

	}



	@Override
	public AutorDTO findById(int id) {
	    // Buscar el autor por su ID en el repositorio de autores
	    Autor autor = autorRepository.findById(id).orElse(null);

	    // Verificar si el autor existe
	    if (autor != null) {
	        // Utilizar el método convertirEntidadADto para convertir el autor a un DTO
	        AutorDTO autorDTO = convertirBeanADTO(autor);
	        
	        // Devolver el objeto AutorDTO
	        return autorDTO;
	    } else {
	        // Si el autor no existe, devolver null o manejar el caso 
	        return null;
	    }
	}


	@Override
	public int obtenerIdAutor(String nombreCompleto) {
		/*
	    // Buscar el autor por su nombre y apellidos en el repositorio de autores
	    Autor autor = autorRepository.findByNombreAndApellidos(nombre, apellidos);
	    
	    // Verificar si el autor existe
	    if (autor != null) {
	        // Si existe, devolver su ID
	        return autor.getId();
	    } else {
	        // Si el autor no existe, devolver -1 o manejar el caso según tus necesidades
	        return -1;
	    }*/
		
		List<Autor> autor = autorRepository.buscarKeyWordSQL(nombreCompleto);

		// Verificar si la lista está vacía antes de intentar acceder al primer elemento
		if (autor.isEmpty()) {
		    return -1;
		}

		// Obtener el ID del primer autor en la lista
		return autor.get(0).getId();
	}


	@Override
	public List<AutorDTO> findAll() {
		List<Autor> lista = this.autorRepository.findAll();
		/*
		List<AutorDTO> listaDTO = new ArrayList<>();
		for (Autor bean : lista) {
			AutorDTO autorDTO = new AutorDTO();
			autorDTO.setId(bean.getId());
			autorDTO.setNombre(bean.getNombre() + " " + bean.getApellidos());
			listaDTO.add(autorDTO);
		}
		return listaDTO;*/
		return lista.stream()
                .map(this::convertirBeanADTO) // Utiliza una referencia a método para convertir de bean a DTO
                .collect(Collectors.toList()); 
	}


	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}


	


	

}
