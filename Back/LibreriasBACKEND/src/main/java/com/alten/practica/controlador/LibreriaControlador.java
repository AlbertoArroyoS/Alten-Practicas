package com.alten.practica.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.service.LibreriaService;

@RestController
@RequestMapping("/libreria")
@CrossOrigin("*") //Permite que el FrontEnd se conecte a este controlador, de momento todos, luego se especificará
public class LibreriaControlador {
	
	private LibreriaService libreriaService;

	public LibreriaControlador(LibreriaService libreriaService) {
		super();
		this.libreriaService = libreriaService;
	}
	
	
	//@GetMapping para listar todos las librerias de la base de datos
	@GetMapping("/findAll")
	public List<LibreriaDTO> findAll () {
		return this.libreriaService.findAll();
	}
	
	//GerMapping para listar 1 libreria por su id
	@GetMapping("/findById/{id}")
	public LibreriaDTO findById(@PathVariable("id") int id) {
		return this.libreriaService.findById(id);
	}
	
	
	

}
