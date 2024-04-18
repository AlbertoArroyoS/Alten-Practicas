package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.PageableDTO;
import com.alten.practica.dto.request.LibroDTORequest;
import com.alten.practica.errorhandler.HrefEntityDTO;
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
	public ResponseEntity<List<LibroDTO>> findAll() {
		return new ResponseEntity<>(this.libroService.findAll(), HttpStatus.OK); // 200 OK
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
	public int update(@RequestBody LibroDTORequest dto, @PathVariable("id") int id) {
		return this.libroService.update(dto, id);
	}

	// Eliminar un libro
	// Metodo para eliminar un autor
	@DeleteMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public void delete(@PathVariable("id") int id) {
		this.libroService.delete(id);
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
