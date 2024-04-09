package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.dto.LibroDTO;
import com.alten.practica.service.LibroService;

@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) 
public class LibroControlador {
	
	@Autowired
	private LibroService libroService;
	

	
	//@GetMapping para buscar por key_word
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public List<LibroDTO> buscarKeyWordSQL (@RequestParam String key_word) {
		return this.libroService.findByTitle(key_word);
	}

}
