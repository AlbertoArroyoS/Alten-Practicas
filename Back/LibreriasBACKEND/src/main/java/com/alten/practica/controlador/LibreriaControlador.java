package com.alten.practica.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.service.LibreriaService;

@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) //Permite que el FrontEnd se conecte a este controlador, de momento todos, luego se especificará
public class LibreriaControlador {
	
	private LibreriaService libreriaService;

	public LibreriaControlador(LibreriaService libreriaService) {
		super();
		this.libreriaService = libreriaService;
	}
	
	
	//@GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA)
	public List<LibreriaDTO> findAll () {
		return this.libreriaService.findAll();
	}
	
	//GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA + LibreriaConstant.RESOURCE_GENERIC_ID)
	public LibreriaDTO findById(@PathVariable("id") int id) {
		return this.libreriaService.findById(id);
	}
	
	@PostMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA)
	public int save(@RequestBody LibreriaDTORequest dto) {
		return this.libreriaService.save(dto);
	}
	
	@PutMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA + LibreriaConstant.RESOURCE_GENERIC_ID)
	public int update(@RequestBody LibreriaDTORequest dto, @PathVariable("id") int id) {
		return this.libreriaService.update(dto, id);
	}
	
	
	

}
