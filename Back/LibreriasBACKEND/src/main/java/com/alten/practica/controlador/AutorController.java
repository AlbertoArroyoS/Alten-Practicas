package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.service.AutorService;

/*
 * Ruta para acceder a una libreria por su id:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para listar todas las librerias:
 * http://localhost:8080/v1/app-libreria/librerias/libreria
 * Ruta para editar una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para crear una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria
 */

@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) 
public class AutorController {
	
	//Inyectamos el servicio
	@Autowired
	private AutorService autorService;
	
	
	//@GetMapping para buscar por key_word
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public List<AutorDTO> buscarKeyWordSQL (@RequestParam String key_word) {
		return this.autorService.buscarKeyWordSQL(key_word);
	}
	
	
	//GetMapping para listar 1 autor por su id
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR + LibreriaConstant.RESOURCE_GENERIC_ID)
	public AutorDTO findById(@PathVariable("id") int id) {
		return this.autorService.buscarPorId(id);
	}
	
	
	//Metodo para crear un autor
	@PostMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public AutorDTO nuevoAutorSQL (@RequestBody AutorDTORequest dto) {
		return this.autorService.nuevoAutorSQL(dto);
	}
	
	
}
