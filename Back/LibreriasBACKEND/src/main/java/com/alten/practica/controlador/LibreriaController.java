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
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;
import com.alten.practica.service.ILibreriaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
/*
 * Ruta para acceder a una libreria por su id:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para listar todas las librerias:
 * http://localhost:8080/v1/app-libreria/librerias/
 * Ruta para editar una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria/1
 * Ruta para crear una libreria:
 * http://localhost:8080/v1/app-libreria/librerias/libreria
 */
//@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) // Permite que el FrontEnd se conecte a este controlador, de momento	
@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)									
public class LibreriaController {

	@Autowired
	private ILibreriaService libreriaService;

	// @GetMapping para listar todos las librerias de la base de datos +
	// LibreriaConstant.RESOURCE_LIBRERIA
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS)
	public ResponseEntity<List<LibreriaDTO>> findAll() {
		return new ResponseEntity<>(this.libreriaService.findAll(), HttpStatus.OK); // 200 OK
		
	}

	// GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<LibreriaDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<>(this.libreriaService.findById(id), HttpStatus.OK); // 200 OK

	}

	@PostMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody LibreriaDTORequest dto) {
		
		return new ResponseEntity<HrefEntityDTO>(this.libreriaService.save(dto), HttpStatus.CREATED);

	}

	@PutMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody LibreriaDTORequest dto, @PathVariable("id") int id) {
		
		return new ResponseEntity<HrefEntityDTO>(this.libreriaService.update(dto, id), HttpStatus.OK);
	}

	@DeleteMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
				
		return new ResponseEntity<HrefEntityDTO>(this.libreriaService.delete(id), HttpStatus.OK);
		/*
		 * if (deletedId != -1) { return
		 * ResponseEntity.ok("Libreria eliminada con éxito"); // Si la eliminación fue
		 * exitosa, devuelves un ResponseEntity con estado 200 (OK) } else { return
		 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libreria no encontrada");
		 * // Si no se encontró la libreria, devuelves un ResponseEntity con estado 404
		 * (NOT FOUND) }
		 */
	}

}
