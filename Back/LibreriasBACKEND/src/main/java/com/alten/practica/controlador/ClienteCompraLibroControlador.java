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
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.service.IClienteCompraLibroService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class ClienteCompraLibroControlador {
	
	@Autowired
	private IClienteCompraLibroService clienteLibroService;
	
	@Autowired
	LibreriaUtil libreriaUtil;



	// @GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS)
	public ResponseEntity<List<ClienteCompraLibroDTO>> findAll() {
		return new ResponseEntity<>(this.clienteLibroService.findAll(), HttpStatus.OK); // 200 OK
	}

	// GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<ClienteCompraLibroDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<ClienteCompraLibroDTO>(this.clienteLibroService.findById(id), HttpStatus.OK);
	}

	//
	@PostMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody ClienteCompraLibroDTORequest dto) {
		
		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.save(dto), HttpStatus.CREATED);

	}

	// Actualizar un libro
	@PutMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@RequestBody ClienteCompraLibroDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.update(dto, id), HttpStatus.OK);
	}

	// Eliminar un libro
	// Metodo para eliminar un autor
	@DeleteMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
		
		this.clienteLibroService.delete(id);
		
		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.delete(id), HttpStatus.OK);	

	}

}
