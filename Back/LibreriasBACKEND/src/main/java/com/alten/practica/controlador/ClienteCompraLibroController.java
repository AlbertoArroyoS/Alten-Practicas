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
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.service.IClienteCompraLibroService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
/**
 * Controlador REST para gestionar las operaciones CRUD sobre las compras de libros por parte de los clientes.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar compras, así como para consultar compras específicas.
 */
@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class ClienteCompraLibroController {
	
	@Autowired
	private IClienteCompraLibroService clienteLibroService;
	
	@Autowired
	LibreriaUtil libreriaUtil;



	/**
	 * Lista todas las compras de libros registradas en la base de datos.
	 *
	 * Este método recupera y retorna todas las compras de libros disponibles en la base de datos.
	 *
	 * @return un {@link ResponseEntity<List<ClienteCompraLibroDTO>>} que contiene la lista de todas las compras
	 *         de libros. El estado HTTP de la respuesta es 200 (OK) si la operación es exitosa.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS)
	public ResponseEntity<Page<ClienteCompraLibroDTO>> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable, Model model) {
		//return new ResponseEntity<>(this.clienteLibroService.findAll(), HttpStatus.OK); // 200 OK
		
		Page<ClienteCompraLibroDTO> page = clienteLibroService
				.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		
		model.addAttribute("page", page);
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        int start = Math.max(1, currentPage + 1);
        int end = Math.min(currentPage + 5, totalPages);

        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }

            model.addAttribute("pageNumbers", pageNumbers);
        }

        List<Integer> pageSizeOptions = Arrays.asList(10, 20, 50, 100);
        model.addAttribute("pageSizeOptions", pageSizeOptions);

        return new ResponseEntity<>(page, HttpStatus.OK);
	}

	/**
	 * Obtiene una compra específica de libro por su ID.
	 *
	 * Este método busca en la base de datos y retorna los detalles de una compra de libro específica
	 * utilizando el ID proporcionado. Si la compra se encuentra, se devuelve en el cuerpo de la respuesta
	 * HTTP con un estado 200 (OK). Si la compra no se encuentra, se devuelve un estado HTTP 404 (Not Found)
	 * para indicar que el recurso solicitado no está disponible.
	 *
	 * @param id el ID de la compra de libro a obtener. Este debe ser un identificador válido de una compra existente.
	 * @return un {@link ResponseEntity<ClienteCompraLibroDTO>} que contiene la compra de libro si se encuentra,
	 *         o un estado HTTP 404 si no se encuentra. 
	 */
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<ClienteCompraLibroDTO> findById(@PathVariable("id") int id) {
		return new ResponseEntity<ClienteCompraLibroDTO>(this.clienteLibroService.findById(id), HttpStatus.OK);
	}

	/**
	 * Crea una nueva compra de libro en la base de datos.
	 *
	 * Este método acepta un DTO que contiene los datos necesarios para registrar una nueva compra de libro. Si la
	 * creación es exitosa, devuelve un {@link ResponseEntity} que incluye un {@link HrefEntityDTO}. Este objeto
	 * {@link HrefEntityDTO} contiene el ID del recurso creado y un enlace (href) para acceder directamente a él.
	 * El estado HTTP 201 es retornado para indicar que la creación fue exitosa y que un nuevo recurso fue creado
	 * en el servidor.
	 *
	 * @param dto el DTO con los datos de la nueva compra. Debe incluir al menos los detalles esenciales del libro
	 *            y la información del cliente.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} con el estado HTTP 201, indicando la creación exitosa del
	 *         recurso. El cuerpo del {@link ResponseEntity} incluye un {@link HrefEntityDTO} con el ID del nuevo
	 *         recurso y el enlace correspondiente.
	 */
	@PostMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody ClienteCompraLibroDTORequest dto) {
		
		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.save(dto), HttpStatus.CREATED);

	}

	/**
	 * Actualiza una compra de libro existente.
	 *
	 * Este método procesa la actualización de los detalles de una compra de libro utilizando los datos proporcionados
	 * en el DTO. Si la actualización es exitosa, devuelve un {@link ResponseEntity} que contiene un {@link HrefEntityDTO}
	 * con el ID del objeto actualizado, la URL del recurso, y el estado HTTP adecuado para representar el resultado
	 * de la operación (usualmente {@link HttpStatus#OK}).
	 *
	 * @param dto el DTO con la información actualizada de la compra. No debe ser nulo y debe contener al menos un campo
	 *            válido para actualizar.
	 * @param id el ID de la compra a actualizar. Debe corresponder a una compra existente.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} que incluye detalles del objeto actualizado, la URL del recurso,
	 *         y el estado HTTP de la operación. Proporciona una confirmación visual de la actualización para los clientes de la API.
	 */
	@PutMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@RequestBody ClienteCompraLibroDTORequest dto, @PathVariable("id") int id) {

		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.update(dto, id), HttpStatus.OK);
	}

	/**
	 * Elimina una compra de libro por su ID.
	 *
	 * Este método gestiona la eliminación de una compra de libro específica en la
	 * base de datos. Al eliminar exitosamente, devuelve un {@link ResponseEntity}
	 * que contiene un {@link HrefEntityDTO} con el ID del objeto eliminado, la URL
	 * para acceder al recurso, y el estado HTTP adecuado para representar el
	 * resultado de la operación (normalmente {@link HttpStatus#OK} si es exitosa).
	 *
	 * @param id el ID de la compra de libro a eliminar.
	 * @return un {@link ResponseEntity<HrefEntityDTO>} que incluye detalles del
	 *         objeto eliminado, la URL del recurso y el estado HTTP de la
	 *         operación, lo cual facilita la verificación del resultado de la
	 *         llamada API por parte de los clientes.
	 */
	@DeleteMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+ LibreriaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
		
		
		return new ResponseEntity<HrefEntityDTO>(this.clienteLibroService.delete(id), HttpStatus.OK);	

	}
	/*
	 * Obtiene una lista de todas las compras de libros de un cliente en la base de
	 * datos.
	 */
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS + LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBRO
			+LibreriaConstant.RESOURCE_CLIENTE + LibreriaConstant.RESOURCE_GENERIC_ID )
	public ResponseEntity<Page<ClienteCompraLibroDTO>> findByIdCliente(
            @PathVariable("id") int id, 
            @PageableDefault(size = 10, page = 0) Pageable pageable, 
            Model model) {

        Page<ClienteCompraLibroDTO> page = clienteLibroService.findByCliente(id, pageable);

        model.addAttribute("page", page);
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        int start = Math.max(1, currentPage + 1);
        int end = Math.min(currentPage + 5, totalPages);

        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }

            model.addAttribute("pageNumbers", pageNumbers);
        }

        List<Integer> pageSizeOptions = Arrays.asList(10, 20, 50, 100);
        model.addAttribute("pageSizeOptions", pageSizeOptions);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
	@GetMapping(LibreriaConstant.RESOURCE_CLIENTE_VENDE_LIBROS + LibreriaConstant.RESOURCE_LIBRERIA
			 + LibreriaConstant.RESOURCE_LIBRERIA_ID )
	public ResponseEntity<Page<ClienteCompraLibroDTO>> findByIdLibreria(
            @PathVariable("idLibreria") int id, 
            @PageableDefault(size = 10, page = 0) Pageable pageable, 
            Model model) {

        Page<ClienteCompraLibroDTO> page = clienteLibroService.findByLibreria(id, pageable);

        model.addAttribute("page", page);
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        int start = Math.max(1, currentPage + 1);
        int end = Math.min(currentPage + 5, totalPages);

        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }

            model.addAttribute("pageNumbers", pageNumbers);
        }

        List<Integer> pageSizeOptions = Arrays.asList(10, 20, 50, 100);
        model.addAttribute("pageSizeOptions", pageSizeOptions);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
