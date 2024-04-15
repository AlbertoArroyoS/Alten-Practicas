package com.alten.practica.controlador;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.service.IAutorService;

import lombok.extern.slf4j.Slf4j;

/*
 * Ruta para acceder a un autor por su id:
 * http://localhost:8080/v1/app-libreria/autores/autor/1
 * Ruta para listar todas los autores:
 * http://localhost:8080/v1/app-libreria/autores
 * Ruta para editar un autor:
 * http://localhost:8080/v1/app-libreria/autores/autor/1
 * Ruta para crear una libreria:
 * http://localhost:8080/v1/app-libreria/autores/autor
 */
@Slf4j
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND)
public class AutorController {

	// Inyectamos el servicio
	@Autowired
	private IAutorService autorService;

	// @GetMapping para buscar por key_word
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public ResponseEntity<List<AutorDTO>> buscarKeyWordSQL(@RequestParam String key_word) {
		//return this.autorService.buscarKeyWordSQL(key_word);
		try {
			List<AutorDTO> lista = this.autorService.buscarKeyWordSQL(key_word);
			if (lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND); // 404 NOT FOUND
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK); // 200 OK
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		}

	}

	// GetMapping para listar 1 autor por su id
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<AutorDTO> findById(@PathVariable("id") int id) {
		try {
	        AutorDTO dto = this.autorService.findById(id);
	        if (dto != null) {
	        	return new ResponseEntity<AutorDTO>(dto, HttpStatus.OK); // 200 OK
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
	    }

	}

	// @GetMapping para listar todos los autores de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES)
	public ResponseEntity<List<AutorDTO>> findAll() {
		//return this.autorService.findAll();
		try {
			List<AutorDTO> lista = this.autorService.findAll();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(lista, HttpStatus.NOT_FOUND); // 404 NOT FOUND
			} else {
				return new ResponseEntity<>(lista, HttpStatus.OK); // 200 OK
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		}

	}

	// Metodo para crear un autor, otra opcion public AutorDTO nuevoAutorSQL
	// (@RequestBody AutorDTORequest dto) {
	@PostMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public ResponseEntity<AutorDTO> save(@RequestBody AutorDTORequest dto) {
		//return this.autorService.save(dto);
		//return new ResponseEntity<AutorDTO>(this.autorService.save(dto), HttpStatus.CREATED);//201 CREATED
		try {
			AutorDTO dtoAlta = this.autorService.save(dto);
			HttpStatus codigoRespuesta = null;
			if(dtoAlta != null) {
				codigoRespuesta = HttpStatus.CREATED; // 201 CREATED
			}else {
				codigoRespuesta = HttpStatus.BAD_REQUEST; // 400 BAD REQUEST
			}		
			ResponseEntity<AutorDTO> re = 
					new ResponseEntity<AutorDTO>(dtoAlta, codigoRespuesta);
			return re;
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		}

	
	}

	// Metodo para eliminar un autor
	@DeleteMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
		try {
			boolean borrado = autorService.delete(id);
			if (borrado) {
				return new ResponseEntity<Integer>(id, HttpStatus.OK); // 200 OK
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		}
		
	}

	// Metodo para actualizar un autor
	@PutMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<AutorDTO> update(@RequestBody AutorDTORequest dto, @PathVariable("id") int id) {
	//	return this.autorService.update(dto, id);			
		try {
			AutorDTO dtoModificado = this.autorService.update(dto, id);
			HttpStatus codigoRespuesta = null;
			if (dtoModificado != null) {
				codigoRespuesta = HttpStatus.OK; // 200 OK
			} else {
				codigoRespuesta = HttpStatus.BAD_REQUEST; // 400 BAD REQUEST
			}
			ResponseEntity<AutorDTO> re = new ResponseEntity<AutorDTO>(dtoModificado, codigoRespuesta);
			return re;
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		}

	}

}
