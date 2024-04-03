package com.alten.practica.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.persistencia.interfaz.DaoLibreria;

/**
 * Controlador que gestiona las operaciones CRUD para la entidad Libreria mediante
 * peticiones REST.
 *
 * <p>Este controlador proporciona métodos para dar de alta, dar de baja, modificar,
 * obtener por ID y listar todas las librerías.</p>
 *
 * @RestController Indica que esta clase es un controlador REST.
 * @RequestMapping Establece la raíz de la URL para todas las solicitudes mapeadas en este controlador.
 * @Autowired Inyecta una instancia de DaoLibreria en el controlador.
 *
 */
@RestController
public class ControladorLibreria {
	

	//Inyectamos un solo objeto DaoLibro, el que dimos de alta antes con @Component.
		@Autowired
		private DaoLibreria daoLibreria;
	
		
		
		//Dar de alta una libreria en la lista,usamos la anotación @PostMapping.
		//La URL será: http://localhost:8080/libros
		
		@PostMapping(path="librerias",consumes=MediaType.APPLICATION_JSON_VALUE,
				produces=MediaType.APPLICATION_JSON_VALUE)
		
		public ResponseEntity<Libreria>altaLibro(@RequestBody Libreria l) {
					

			int libroNuevo = daoLibreria.addLibreria(l);
			if(libroNuevo == 1) {
				System.out.println("altaLibro: objeto libro: " + l);
				return new ResponseEntity<Libreria>(l,HttpStatus.CREATED);//201 CREATED
			}else {
				System.out.println("No se ha dado de alta la libreria: " + l);
				return new ResponseEntity<Libreria>(HttpStatus.BAD_REQUEST);//404 NOT FOUND
			}
			

		}
		
		//Buscamos una libreria por ID para darlo de baja de la lista.Usamos la anotación @DeleteMapping.
		//Las busquedas por clave primaria van en el PATH de la URL.
		//La URL será: http://localhost:8080/librerias/id
		
		@DeleteMapping(path="librerias/{id}")
		public ResponseEntity<Libreria>bajaLibro(@PathVariable("id") int id) {
			System.out.println("Libro a dar de baja en la lista: " + id);
			int l = daoLibreria.deleteLibreria(id);
			if(l == 1) {
				return new ResponseEntity<Libreria>(HttpStatus.OK);//200 OK
			}else {
				return new ResponseEntity<Libreria>(HttpStatus.NOT_FOUND);//404 NOT FOUND
			}
		}
		
		//Modificar una libreria del listado. Usamos la anotación @PutMapping
		//"http://localhost:8080/librerias/ID" 		
		@PutMapping(path="librerias/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
		
		public ResponseEntity<Libreria>modificarLibreria(@PathVariable("id") int id,@RequestBody Libreria l) {	
			
	        
			l.setId(id);
			int pUpdate = daoLibreria.updateLibreria(l);
			if(pUpdate == 1) {
				return new ResponseEntity<Libreria>(HttpStatus.OK);//200 OK
			}else {
				return new ResponseEntity<Libreria>(HttpStatus.NOT_FOUND);//404 NOT FOUND
			}
	    }
		
		
		//Usamos el metodo GET para devolver una libreria por su identificador.
		//Las busquedas por clave primaria van en el PATH de la URL
		//URL: http://localhost:8080/librerias/id
		@GetMapping(path="librerias/{id}",produces = MediaType.APPLICATION_JSON_VALUE)			
		
		public ResponseEntity<Libreria>getLibreria(@PathVariable("id") int id) {				
			try {
				Libreria l = daoLibreria.getLibreria(id);
		        if (l != null) {
		            return new ResponseEntity<>(l, HttpStatus.OK); // 200 OK
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER ERROR
		    }
		}

	
		//Listar todos las librerias 
		//URL: http://localhost:8080/librerias
		@GetMapping(path = "librerias", produces = MediaType.APPLICATION_JSON_VALUE)
		
		public ResponseEntity<List<Libreria>> listarLibrerias() {			
		    System.out.println("Listando todos los libros");		    
		    List<Libreria> lista = daoLibreria.getLibrerias();	    
		    return new ResponseEntity<>(lista, HttpStatus.OK);
		}
}