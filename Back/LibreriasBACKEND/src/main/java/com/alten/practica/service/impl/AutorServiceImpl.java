package com.alten.practica.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.repository.AutorRepository;

import com.alten.practica.service.AutorService;


@Service
public class AutorServiceImpl implements AutorService{
	
	//inyectamos el repositorio del autor
	final AutorRepository autorRepository;
	
	public AutorServiceImpl(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}


	
	//Metodo para convertir de entidad a dto	
	private AutorDTO convertirEntidadADto(Autor autor) {
		return AutorDTO.builder()
				.id(autor.getId())
				.nombre(autor.getNombre() + " " + autor.getApellidos())
				.build();
	}


	@Override
	public List<AutorDTO> buscarKeyWordSQL(String nombre) {
		List<Autor> listaAutores = this.autorRepository.buscarKeyWordSQL(nombre);
		return listaAutores.stream()
				.map((bean) -> convertirEntidadADto(bean))
				.collect(Collectors.toList());

	}



	@Override
	public AutorDTO nuevoAutorSQL(AutorDTORequest dto) {		
		//convertir a dto con el metodo convertirEntidadADto
		return convertirEntidadADto(this.autorRepository.nuevoAutorSQL(dto.getNombre(), dto.getApellidos()));

	}



	@Override
	public AutorDTO buscarPorId(int id) {
		// Buscar el autor por su ID en el repositorio de autores
	    Autor autor = autorRepository.findById(id).orElse(null);
	    
	    // Verificar si el autor existe
	    if (autor != null) {
	        // Si existe, crear un objeto AutorDTO y copiar los datos del autor a él
	        AutorDTO autorDTO = new AutorDTO(autor.getId(), autor.getNombre() + " " + autor.getApellidos());
	        autorDTO.setId(autor.getId());
	        autorDTO.setNombre(autor.getNombre()+ " " + autor.getApellidos());
	        
	        // Devolver el objeto AutorDTO
	        return autorDTO;
	    } else {
	        // Si el autor no existe, devolver null o manejar el caso según tus necesidades
	        return null;
	    }
	}





	

}
