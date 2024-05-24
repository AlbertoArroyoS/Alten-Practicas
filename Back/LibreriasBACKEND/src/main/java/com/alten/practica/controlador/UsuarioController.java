package com.alten.practica.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioAdminDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
import com.alten.practica.service.IUsuarioService;
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
	
	@GetMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") int id) {

		return new ResponseEntity<UsuarioDTO>(this.usuarioService.findById(id), HttpStatus.OK);


	}
	
	@PutMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody UsuarioDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.usuarioService.update(dto, id), HttpStatus.OK);


	}
	
	@PostMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody UsuarioDTORequest dto) {

		return new ResponseEntity<HrefEntityDTO>(this.usuarioService.save(dto), HttpStatus.CREATED);
	}
	
	/*
	 * Metodo para mostar los libros a la venta menos los del usuario logueado
     */
	@GetMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ "/user")
    public Page<UsuarioDTO> findAllUser(Pageable pageable) {
		
        return usuarioService.findAllUser(pageable);
    }
	
	@GetMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ "/admin")
    public Page<UsuarioAdminDTO> findAllAdmin(Pageable pageable) {
		
        return usuarioService.findAllAdmin(pageable);
    }

}
