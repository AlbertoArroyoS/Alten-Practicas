package com.alten.practica.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
	/*
	 * Metodo para actualizar un usuario
	 */
	
	@PutMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody UsuarioDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.usuarioService.update(dto, id), HttpStatus.OK);


	}
	/*
	 * Metodo para registrar un usuario
     */
	@PostMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody UsuarioDTORequest dto) {

		return new ResponseEntity<HrefEntityDTO>(this.usuarioService.save(dto), HttpStatus.CREATED);
	}
	
	/*
	 * Metodo para mostar los libros a la venta menos los del usuario logueado
     */
	@GetMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ "/user")
    public ResponseEntity<Page<UsuarioDTO>> findAllUser(@PageableDefault(size = 100, page = 0) Pageable pageable, Model model) {
		
        //return usuarioService.findAllUser(pageable);
		
		Page<UsuarioDTO> page = usuarioService
				.findAllUser(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		
		model.addAttribute("page", page);
		var totalPages = page.getTotalPages();
		var currentPage = page.getNumber();
		
		var start = Math.max(1, currentPage);
		var end = Math.min(currentPage + 5, totalPages);
		
		if (totalPages > 0) {
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = start; i <= end; i++) {
				pageNumbers.add(i);
			}
			
			model.addAttribute("pageNumbers", pageNumbers);
					
		}
		List<Integer> pageSizeOptions = Arrays.asList(10,20, 50, 100);
		model.addAttribute("pageSizeOptions", pageSizeOptions);
		
		return new ResponseEntity<>(page, HttpStatus.OK);
    }
	
	@GetMapping(LibreriaConstant.RESOURCE_USUARIOS + LibreriaConstant.RESOURCE_USUARIO
			+ "/admin")
    public ResponseEntity<Page<UsuarioAdminDTO>> findAllAdmin(@PageableDefault(size = 100, page = 0) Pageable pageable, Model model) {
		
        //return usuarioService.findAllAdmin(pageable);
        
        Page<UsuarioAdminDTO> page = usuarioService
				.findAllAdmin(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		
		model.addAttribute("page", page);
		var totalPages = page.getTotalPages();
		var currentPage = page.getNumber();
		
		var start = Math.max(1, currentPage);
		var end = Math.min(currentPage + 5, totalPages);
		
		if (totalPages > 0) {
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = start; i <= end; i++) {
				pageNumbers.add(i);
			}
			
			model.addAttribute("pageNumbers", pageNumbers);
					
		}
		List<Integer> pageSizeOptions = Arrays.asList(10,20, 50, 100);
		model.addAttribute("pageSizeOptions", pageSizeOptions);
		
		return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
