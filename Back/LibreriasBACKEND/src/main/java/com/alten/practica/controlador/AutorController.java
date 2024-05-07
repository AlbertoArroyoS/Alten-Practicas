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
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.PageableDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;
import com.alten.practica.service.IAutorService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones CRUD relacionadas con autores.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar autores, así
 * como para buscar autores por palabras clave.
 *
 * @see AutorService
 * 
 *      Ruta para acceder a un autor por su id:
 *      http://localhost:8080/v1/app-libreria/autores/autor/1 Ruta para listar
 *      todas los autores: http://localhost:8080/v1/app-libreria/autores Ruta
 *      para editar un autor:
 *      http://localhost:8080/v1/app-libreria/autores/autor/1 Ruta para crear
 *      una libreria: http://localhost:8080/v1/app-libreria/autores/autor
 */

//@CrossOrigin(LibreriaConstant.CLIENTE_FRONTEND) // Para permitir peticiones desde el frontend, quien puede hacer peticiones a este controlador
@Slf4j // para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class AutorController {

	// Inyectamos el servicio
	@Autowired
	private IAutorService autorService;
	
	@Autowired
	LibreriaUtil libreriaUtil;

	/**
	 * Busca autores utilizando una palabra clave. Este método procesa solicitudes
	 * GET para buscar autores cuyos nombres u otros atributos contengan la palabra
	 * clave especificada. Devuelve una lista de autores que coinciden con la
	 * palabra clave, o una lista vacía si no hay coincidencias.
	 *
	 * @param key_word La palabra clave utilizada para la búsqueda de autores. No
	 *                 debe ser null ni vacía.
	 * @return ResponseEntity con una lista de AutorDTO que coincide con la palabra
	 *         clave, o un estado HTTP 404 si no se encuentra ninguna coincidencia.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public ResponseEntity<List<AutorDTO>> buscarKeyWordSQL(@RequestParam String key_word) {
		// return this.autorService.buscarKeyWordSQL(key_word);
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

	/**
	 * Obtiene un autor por su ID.
	 *
	 * Este método procesa solicitudes GET para buscar y recuperar un autor
	 * específico utilizando el ID proporcionado. Si el autor se encuentra, se
	 * devuelve con un estado HTTP 200 (OK). Si el autor no se encuentra, se
	 * devuelve un estado HTTP 404 (Not Found) para indicar que no existe un recurso
	 * con el ID especificado.
	 *
	 * @param id el ID del autor a buscar. Este debe ser un identificador válido y
	 *           existente.
	 * @return ResponseEntity que contiene el autor encontrado o un estado HTTP 404
	 *         si el autor no se encuentra.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<AutorDTO> findById(@PathVariable("id") int id) {

		return new ResponseEntity<AutorDTO>(this.autorService.findById(id), HttpStatus.OK);
		/*
		 * try { AutorDTO dto = this.autorService.findById(id); if (dto != null) {
		 * return new ResponseEntity<AutorDTO>(dto, HttpStatus.OK); // 200 OK } else {
		 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND } } catch
		 * (Exception e) { return new
		 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER
		 * ERROR }
		 */

	}

	/**
	 * Lista todos los autores disponibles en la base de datos.
	 *
	 * Este método recupera una lista de todos los autores registrados en la base de
	 * datos.
	 *
	 * @return ResponseEntity que contiene una lista de AutorDTO. Siempre devuelve
	 *         un estado HTTP 200 (OK),
	 */
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES)
	public Page<AutorDTO> findAll(PageableDTO pageable) {
		return this.autorService.findAll(this.libreriaUtil.getPageable(pageable,"id_autor"));	}
	
	
	/*
	@GetMapping(LibreriaConstant.RESOURCE_AUTORES)
    public ResponseEntity<Page<AutorDTO>> findAll(PageableDTO pageable) {
        Page<AutorDTO> autoresPage = autorService.findAll(pageable);
        return ResponseEntity.ok().body(autoresPage);
    }*/


	/**
	 * Crea un nuevo autor en la base de datos.
	 *
	 * Este método se utiliza para crear un nuevo autor en la base de datos
	 * utilizando los datos proporcionados en el DTO del autor. Después de la
	 * creación exitosa, devuelve un {@link ResponseEntity} que contiene un
	 * {@link HrefEntityDTO}, el cual contiene un enlace al recurso creado.
	 *
	 * @param dto el DTO del autor a crear. Contiene la información necesaria para
	 *            crear un nuevo autor.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} que incluye detalles del
	 *         objeto , la URL del recurso y el estado HTTP de la operación
	 *         (normalmente {@link HttpStatus#CREATED} si la creación es exitosa).
	 */
	@PostMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody AutorDTORequest dto) {

		return new ResponseEntity<HrefEntityDTO>(this.autorService.save(dto), HttpStatus.CREATED);

		// Forma antigua de hacerlo, antes de implementar HrefEntityDTO y paquete
		// errorhandler
		// return new ResponseEntity<AutorDTO>(this.autorService.save(dto),
		// HttpStatus.CREATED);//201 CREATED
		/*
		 * try { AutorDTO dtoAlta = this.autorService.save(dto); HttpStatus
		 * codigoRespuesta = null; if(dtoAlta != null) { codigoRespuesta =
		 * HttpStatus.CREATED; // 201 CREATED }else { codigoRespuesta =
		 * HttpStatus.BAD_REQUEST; // 400 BAD REQUEST } ResponseEntity<AutorDTO> re =
		 * new ResponseEntity<AutorDTO>(dtoAlta, codigoRespuesta); return re; } catch
		 * (Exception e) { return new
		 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER
		 * ERROR }
		 */
		// log.info("crce controller save -> {} "+dto.toString());

	}

	/**
	 * Elimina un autor por su ID.
	 *
	 * Este método se utiliza para eliminar un autor específico en la base de datos
	 * utilizando su ID. Después de la eliminación exitosa, devuelve un
	 * {@link ResponseEntity} indicando el resultado de la operación.
	 *
	 * @param id el ID del autor a eliminar. Es el identificador único del autor en
	 *           la base de datos.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} que incluye detalles del
	 *         objeto eliminado, la URL del recurso y el estado HTTP de la
	 *         operación, lo cual facilita la verificación del resultado de la
	 *         llamada API por parte de los clientes.
	 */
	@DeleteMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.autorService.delete(id), HttpStatus.OK);

		/*
		 * Forma antigua de hacerlo, antes de implementar HrefEntityDTO y paquete
		 * errorhandler try { boolean borrado = autorService.delete(id); if (borrado) {
		 * return new ResponseEntity<Integer>(id, HttpStatus.OK); // 200 OK } else {
		 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND } } catch
		 * (Exception e) { return new
		 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER
		 * ERROR }
		 */

	}

	/**
	 * Actualiza la información de un autor existente.
	 *
	 * Este método se utiliza para actualizar la información de un autor existente
	 * en la base de datos. Para ello, se proporciona un DTO del autor con la
	 * información actualizada, así como el ID del autor que se desea actualizar.
	 * Después de la actualización exitosa, devuelve un {@link ResponseEntity} con
	 * el DTO actualizado.
	 *
	 * @param dto el DTO del autor con la información actualizada. Contiene los
	 *            datos actualizados del autor.
	 * @param id  el ID del autor a actualizar. Es el identificador único del autor
	 *            en la base de datos.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} que incluye detalles del
	 *         objeto actualizado, la URL del recurso y el estado HTTP de la
	 *         operación, lo cual facilita la verificación del resultado de la
	 *         llamada API por parte de los clientes.
	 */
	@PutMapping(LibreriaConstant.RESOURCE_AUTORES + LibreriaConstant.RESOURCE_AUTOR
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody AutorDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.autorService.update(dto, id), HttpStatus.OK);
		/*
		 * Forma antigua de hacerlo, antes de implementar HrefEntityDTO y paquete
		 * errorhandler try { AutorDTO dtoModificado = this.autorService.update(dto,
		 * id); HttpStatus codigoRespuesta = null; if (dtoModificado != null) {
		 * codigoRespuesta = HttpStatus.OK; // 200 OK } else { codigoRespuesta =
		 * HttpStatus.BAD_REQUEST; // 400 BAD REQUEST } ResponseEntity<AutorDTO> re =
		 * new ResponseEntity<AutorDTO>(dtoModificado, codigoRespuesta); return re; }
		 * catch (Exception e) { return new
		 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 INTERNAL SERVER
		 * ERROR }
		 */

	}

}
