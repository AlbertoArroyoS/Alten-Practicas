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
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.service.ILibreriaLibroService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/*
 * Controlador REST para gestionar las operaciones CRUD sobre los libros de las librerías.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar libros, así como para consultar libros específicos.
 */
@Slf4j // para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class LibreriaLibroController {

	@Autowired
	private ILibreriaLibroService libreriaLibroService;

	@Autowired
	LibreriaUtil libreriaUtil;

	/**
	 * Lista todos los libros de librería almacenados en la base de datos.
	 *
	 * Este método se utiliza para recuperar todos los libros de librería
	 * almacenados en la base de datos. Devuelve un {@link ResponseEntity} que
	 * contiene una lista de DTO de libros de librería y el estado HTTP adecuado que
	 * representa el resultado de la operación (normalmente {@link HttpStatus#OK} si
	 * la operación es exitosa).
	 *
	 * @return un {@link ResponseEntity} que contiene una lista de DTO de libros de
	 *         librería y el estado HTTP adecuado que representa el resultado de la
	 *         operación.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS)
	public ResponseEntity<Page<LibreriaLibroDTO>> findAll(@PageableDefault(size = 100, page = 0) Pageable pageable, Model model) {
		//return new ResponseEntity<>(this.libreriaLibroService.findAll(), HttpStatus.OK); // 200 OK
		
		Page<LibreriaLibroDTO> page = libreriaLibroService
				.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		
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

	/**
	 * Busca un libro de librería por su ID.
	 *
	 * Este método se utiliza para recuperar un libro de librería específico de la
	 * base de datos utilizando su ID. Devuelve un {@link ResponseEntity} que
	 * contiene un DTO del libro de librería encontrado y el estado HTTP adecuado
	 * que representa el resultado de la operación (normalmente
	 * {@link HttpStatus#OK} si la operación es exitosa).
	 *
	 * @param id el ID del libro de librería a buscar.
	 * @return un {@link ResponseEntity} que contiene un DTO del libro de librería
	 *         encontrado y el estado HTTP adecuado que representa el resultado de
	 *         la operación.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<LibreriaLibroDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<LibreriaLibroDTO>(this.libreriaLibroService.findById(id), HttpStatus.OK);
	}

	/**
	 * Crea un nuevo libro de librería en la base de datos.
	 *
	 * Este método se utiliza para crear un nuevo libro de librería en la base de
	 * datos utilizando los datos proporcionados en el DTO del libro de librería.
	 * Después de la creación exitosa, devuelve un {@link ResponseEntity} que
	 * contiene un {@link HrefEntityDTO} con detalles del libro de librería creado,
	 * la URL del recurso y el estado HTTP adecuado para representar el resultado de
	 * la operación (normalmente {@link HttpStatus#CREATED} si la creación es
	 * exitosa).
	 *
	 * @param dto el DTO del libro de librería a crear.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles del libro de librería creado, la URL del recurso y el estado
	 *         HTTP adecuado que representa el resultado de la operación.
	 */
	@PostMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody LibreriaLibroDTORequest dto) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.save(dto), HttpStatus.CREATED);

	}

	/**
	 * Actualiza la información de un libro de librería existente.
	 *
	 * Este método se utiliza para actualizar la información de un libro de librería
	 * existente en la base de datos. Para ello, se proporciona un DTO del libro de
	 * librería con la información actualizada, así como el ID del libro de librería
	 * que se desea actualizar. Después de la actualización exitosa, devuelve un
	 * {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles del
	 * libro de librería actualizado, la URL del recurso y el estado HTTP adecuado
	 * para representar el resultado de la operación (normalmente
	 * {@link HttpStatus#OK} si la actualización es exitosa).
	 *
	 * @param dto el DTO del libro de librería con la información actualizada.
	 * @param id  el ID del libro de librería a actualizar.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles del libro de librería actualizado, la URL del recurso y el
	 *         estado HTTP adecuado que representa el resultado de la operación.
	 */
	@PutMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@RequestBody LibreriaLibroDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.update(dto, id), HttpStatus.OK);
	}

	/**
	 * Elimina un libro de librería por su ID.
	 *
	 * Este método se utiliza para eliminar un libro de librería específico de la
	 * base de datos utilizando su ID. Devuelve un {@link ResponseEntity} que
	 * contiene un {@link HrefEntityDTO} con detalles del libro de librería
	 * eliminado, la URL del recurso y el estado HTTP adecuado para representar el
	 * resultado de la operación (normalmente {@link HttpStatus#OK} si la
	 * eliminación es exitosa).
	 *
	 * @param id el ID del libro de librería a eliminar.
	 * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con
	 *         detalles del libro de librería eliminado, la URL del recurso y el
	 *         estado HTTP adecuado que representa el resultado de la operación.
	 */
	@DeleteMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.libreriaLibroService.delete(id), HttpStatus.OK);

	}
	@GetMapping(LibreriaConstant.RESOURCE_LIBRERIA_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA_LIBRO)
    public ResponseEntity<Page<LibreriaLibroDTO>> searchBooksByTitle(@RequestParam String titulo, Pageable pageable) {
        Page<LibreriaLibroDTO> libros = libreriaLibroService.findByTituloContaining(titulo, pageable);
        return ResponseEntity.ok(libros);
    }

}
