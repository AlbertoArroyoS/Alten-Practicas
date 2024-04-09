package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.LibroDTO;
import com.alten.practica.service.LibroService;


/*
 * Todos los libros
 * http://localhost:8080/v1/app-libreria/libros
 * Obtener por id
 * http://localhost:8080/v1/app-libreria/libros/libro/1
 * Libro por nombre de libro a buscar
 * http://localhost:8080/v1/app-libreria/libros/libro?key_word=resplandor
 */


@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) 
public class LibroControlador {
	
	@Autowired
	private LibroService libroService;
	

	
	//@GetMapping para buscar por key_word un libro
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public List<LibroDTO> buscarKeyWordSQL (@RequestParam String key_word) {
		return this.libroService.findByTitle(key_word);
	}
	
	//@GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS)
	public List<LibroDTO> findAll () {
		return this.libroService.findAll();
	}
	
	//GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO + LibreriaConstant.RESOURCE_GENERIC_ID)
	public LibroDTO findById(@PathVariable("id") int id) {
		return this.libroService.findById(id);
	}

}
