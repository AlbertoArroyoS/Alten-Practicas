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
import com.alten.practica.modelo.entidad.dto.LibroDTO;
import com.alten.practica.modelo.entidad.dto.PageableDTO;
import com.alten.practica.modelo.entidad.dto.request.LibroDTORequest;
import com.alten.practica.service.ILibroService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/*
 * Todos los libros
 * http://localhost:8080/v1/app-libreria/libros
 * Obtener por id
 * http://localhost:8080/v1/app-libreria/libros/libro/1
 * Libro por nombre de libro a buscar
 * http://localhost:8080/v1/app-libreria/libros/libro?key_word=resplandor
 * Ruta para editar una libro:
 * http://localhost:8080/v1/app-libreria/libros/libro/1
 * Ruta para crear una libro:
 * http://localhost:8080/v1/app-libreria/libros/libro
 * Ruta para buscar por key_word un libro:
 * http://localhost:8080/v1/app-libreria/libros/libro?key_word=a&page=0&size=4
 */

//@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) // Para permitir peticiones desde el frontend, quien puede hacer peticiones a este controlador
@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class LibroController {

	@Autowired
	private ILibroService libroService;
	
	@Autowired
	LibreriaUtil libreriaUtil;
/*
	private LibroDTORequest convertirLibroDTORequest(LibroDTO libroDTO) {
		return LibroDTORequest.builder().titulo(libroDTO.getTitulo()).nombreAutor(libroDTO.getNombreAutor())
				.apellidosAutor(libroDTO.getApellidosAutor()).genero(libroDTO.getGenero())
				.paginas(libroDTO.getPaginas()).editorial(libroDTO.getEditorial())
				.descripcion(libroDTO.getDescripcion()).precio(libroDTO.getPrecio()).build();
	}*/

	// @GetMapping para buscar por key_word un libro
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public Page<LibroDTO> buscarKeyWordSQL(@RequestParam String key_word, PageableDTO pageable) {
		//
		log.info("LibroController -> {} " + pageable.toString());
		return this.libroService.findByTitle(key_word, this.libreriaUtil.getPageable(pageable,"id_libro"));
	}

	// @GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS)
	public ResponseEntity<Page<LibroDTO>> findAll(@PageableDefault(size = 100, page = 0) Pageable pageable, Model model) {
		//return new ResponseEntity<>(this.libroService.findAll(), HttpStatus.OK); // 200 OK
		Page<LibroDTO> page = libroService
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

	// GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<LibroDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<LibroDTO>(this.libroService.findById(id), HttpStatus.OK);
	}

	/*
	 * //Incluir un libro
	 * 
	 * @PostMapping(LibreriaConstant.RESOURCE_LIBROS +
	 * LibreriaConstant.RESOURCE_LIBRO) public LibroDTO nuevoLibroSQL (@RequestBody
	 * LibroDTORequest dto) { return this.libroService.save(dto); }
	 */
	@PostMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody LibroDTORequest dto) {
		
		return new ResponseEntity<HrefEntityDTO>(this.libroService.save(dto), HttpStatus.CREATED);

		
		/*
		 * 
		 * Forma vieja
	    int resultado = libroService.save(dto);
	    if (resultado != -1) {
	        return ResponseEntity.status(HttpStatus.CREATED).body("Libro guardado exitosamente");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al guardar el libro");
	    }*/
	}


	@PutMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@RequestBody LibroDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.libroService.update(dto, id), HttpStatus.OK);
	}

	// Eliminar un libro
	// Metodo para eliminar un autor
	@DeleteMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
			
		return new ResponseEntity<HrefEntityDTO>(this.libroService.delete(id), HttpStatus.OK);	
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
