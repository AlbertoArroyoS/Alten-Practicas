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
 * 
 * Controlador REST para gestionar las operaciones CRUD sobre las librerias.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar librerias, así como para consultar librerias específicas.
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
@Slf4j // para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class LibreriaController {

	@Autowired
	private ILibreriaService libreriaService;

	/**
	 * Lista todas las librerías almacenadas en la base de datos.
	 *
	 * Este método se utiliza para recuperar todas las librerías almacenadas en la
	 * base de datos. Devuelve un {@link ResponseEntity} que contiene una lista de
	 * DTO de librerías y el estado HTTP adecuado que representa el resultado de la
	 * operación (normalmente {@link HttpStatus#OK} si la operación es exitosa).
	 *
	 * @return un {@link ResponseEntity} que contiene una lista de DTO de librerías
	 *         y el estado HTTP adecuado que representa el resultado de la
	 *         operación.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS)
	public ResponseEntity<List<LibreriaDTO>> findAll() {
		return new ResponseEntity<>(this.libreriaService.findAll(), HttpStatus.OK); // 200 OK

	}

	/**
	 * Busca una librería por su ID.
	 *
	 * Este método se utiliza para recuperar una librería específica de la base de
	 * datos utilizando su ID. Devuelve un {@link ResponseEntity} que contiene un
	 * DTO de la librería encontrada y el estado HTTP adecuado que representa el
	 * resultado de la operación (normalmente {@link HttpStatus#OK} si la operación
	 * es exitosa).
	 *
	 * @param id el ID de la librería a buscar.
	 * @return un {@link ResponseEntity} que contiene un DTO de la librería
	 *         encontrada y el estado HTTP adecuado que representa el resultado de
	 *         la operación.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<LibreriaDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<>(this.libreriaService.findById(id), HttpStatus.OK); // 200 OK

	}

	/**
	 * Crea una nueva librería en la base de datos.
	 *
	 * Este método se utiliza para crear una nueva librería en la base de datos
	 * utilizando los datos proporcionados en el DTO de la librería. Después de la
	 * creación exitosa, devuelve un {@link ResponseEntity} que contiene un
	 * {@link HrefEntityDTO} con detalles de la librería creada, la URL del recurso
	 * y el estado HTTP adecuado para representar el resultado de la operación
	 * (normalmente {@link HttpStatus#CREATED} si la creación es exitosa).
	 *
	 * @param dto el DTO de la librería a crear.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles de la librería creada, la URL del recurso y el estado HTTP
	 *         adecuado que representa el resultado de la operación.
	 */
	@PostMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody LibreriaDTORequest dto) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaService.save(dto), HttpStatus.CREATED);

	}

	/**
	 * Actualiza la información de una librería existente.
	 *
	 * Este método se utiliza para actualizar la información de una librería
	 * existente en la base de datos. Para ello, se proporciona un DTO de la
	 * librería con la información actualizada, así como el ID de la librería que se
	 * desea actualizar. Después de la actualización exitosa, devuelve un
	 * {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles de
	 * la librería actualizada, la URL del recurso y el estado HTTP adecuado para
	 * representar el resultado de la operación (normalmente {@link HttpStatus#OK}
	 * si la actualización es exitosa).
	 *
	 * @param dto el DTO de la librería con la información actualizada.
	 * @param id  el ID de la librería a actualizar.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles de la librería actualizada, la URL del recurso y el estado
	 *         HTTP adecuado que representa el resultado de la operación.
	 */
	@PutMapping(LibreriaConstant.RESOURCE_LIBRERIAS + LibreriaConstant.RESOURCE_LIBRERIA
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody LibreriaDTORequest dto,
			@PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaService.update(dto, id), HttpStatus.OK);
	}

	/**
	 * Elimina una librería por su ID.
	 *
	 * Este método se utiliza para eliminar una librería específica de la base de
	 * datos utilizando su ID. Devuelve un {@link ResponseEntity} que contiene un
	 * {@link HrefEntityDTO} con detalles de la librería eliminada, la URL del
	 * recurso y el estado HTTP adecuado para representar el resultado de la
	 * operación (normalmente {@link HttpStatus#OK} si la eliminación es exitosa).
	 *
	 * @param id el ID de la librería a eliminar.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles de la librería eliminada, la URL del recurso y el estado
	 *         HTTP adecuado que representa el resultado de la operación.
	 */
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
