package com.alten.practica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alten.practica.dto.AutorDTO;
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
	

}
