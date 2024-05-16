package com.alten.practica.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j // para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	LibreriaUtil libreriaUtil;
	
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<AutorDTO> findById(@PathVariable("id") int id) {

		return new ResponseEntity<AutorDTO>(this.usuarioService.findById(id), HttpStatus.OK);


	}
	
	@PutMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody AutorDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.usuarioService.update(dto, id), HttpStatus.OK);


	}

}
