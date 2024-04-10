package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.dto.request.LibreriaDTORequest;
import com.alten.practica.dto.request.LibroDTORequest;
import com.alten.practica.service.LibroService;


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
 */


@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) 
public class LibroController {
	
	@Autowired
	private LibroService libroService;
	

	
	//@GetMapping para buscar por key_word un libro
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public List<LibroDTO> buscarKeyWordSQL (@RequestParam String key_word) {
		return this.libroService.findByTitle(key_word);
	}
	
	//@GetMapping para listar todos las librerias de la base de datos
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS)
	public List<LibroDTO> findAll () {
		return this.libroService.findAll();
	}
	
	//GetMapping para listar 1 libreria por su id
	@GetMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO + LibreriaConstant.RESOURCE_GENERIC_ID)
	public LibroDTO findById(@PathVariable("id") int id) {
		return this.libroService.findById(id);
	}
/*	
	//Incluir un libro
	@PostMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public LibroDTO nuevoLibroSQL (@RequestBody LibroDTORequest dto) {
		return this.libroService.save(dto);
	}
*/	
	@PostMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO)
	public ResponseEntity<String> guardarLibro(@RequestBody LibroDTO libroDTO) {
        libroService.guardarLibro(
            libroDTO.getTitulo(), 
            libroDTO.getNombreAutor(), 
            libroDTO.getApellidosAutor(), 
            libroDTO.getGenero(), 
            libroDTO.getPaginas(), 
            libroDTO.getEditorial(), 
            libroDTO.getDescripcion(), 
            libroDTO.getPrecio()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Libro guardado exitosamente");
    }
	
	@PutMapping(LibreriaConstant.RESOURCE_LIBROS + LibreriaConstant.RESOURCE_LIBRO + LibreriaConstant.RESOURCE_GENERIC_ID)
	public int update(@RequestBody LibroDTORequest dto, @PathVariable("id") int id) {
		return this.libroService.update(dto, id);
	}
	

}
