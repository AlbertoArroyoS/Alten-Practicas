package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.service.ILibreriaLibroService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class LibreriaLibroControlador {
	
	@Autowired
	private ILibreriaLibroService libreriaLibroService;
	
	@Autowired
	LibreriaUtil libreriaUtil;


	// @GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS)
	public ResponseEntity<List<LibreriaLibroDTO>> findAll() {
		return new ResponseEntity<>(this.libreriaLibroService.findAll(), HttpStatus.OK); // 200 OK
	}

	// GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<LibreriaLibroDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<LibreriaLibroDTO>(this.libreriaLibroService.findById(id), HttpStatus.OK);
	}

	//
	@PostMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody LibreriaLibroDTORequest dto) {
		
		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.save(dto), HttpStatus.CREATED);

	}

	// Actualizar un libro
	@PutMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@RequestBody LibreriaLibroDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.update(dto, id), HttpStatus.OK);
	}

	// Eliminar un libro
	// Metodo para eliminar un autor
	@DeleteMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
		
		this.libreriaLibroService.delete(id);
		
		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.delete(id), HttpStatus.OK);	

	}

}
