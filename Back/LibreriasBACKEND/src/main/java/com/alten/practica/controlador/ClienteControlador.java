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
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;
import com.alten.practica.service.IClienteService;
import com.alten.practica.util.LibreriaUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
/*
 * Controlador REST para gestionar las operaciones CRUD sobre los clientes.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar clientes, así como para consultar clientes específicos.
 */
@Slf4j //para logs de lombok
@RestController
@RequestMapping(LibreriaConstant.RESOURCE_GENERIC)
public class ClienteControlador {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	LibreriaUtil libreriaUtil;


	/**
     * Lista todos los clientes almacenados en la base de datos.
     *
     * Este método se utiliza para recuperar todos los clientes almacenados en la base de datos.
     * Devuelve un {@link ResponseEntity} que contiene una lista de DTO de clientes y el estado HTTP
     * adecuado que representa el resultado de la operación (normalmente {@link HttpStatus#OK} si la operación es exitosa).
     *
     * @return un {@link ResponseEntity} que contiene una lista de DTO de clientes y el estado HTTP
     *         adecuado que representa el resultado de la operación.
     */
    @GetMapping(LibreriaConstant.RESOURCE_CLIENTES)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return new ResponseEntity<>(this.clienteService.findAll(), HttpStatus.OK); // 200 OK
    }

    /**
     * Busca un cliente por su ID.
     *
     * Este método se utiliza para recuperar un cliente específico de la base de datos utilizando su ID.
     * Devuelve un {@link ResponseEntity} que contiene un DTO del cliente encontrado y el estado HTTP
     * adecuado que representa el resultado de la operación (normalmente {@link HttpStatus#OK} si la operación es exitosa).
     *
     * @param id el ID del cliente a buscar.
     * @return un {@link ResponseEntity} que contiene un DTO del cliente encontrado y el estado HTTP
     *         adecuado que representa el resultado de la operación.
     */
    @GetMapping(LibreriaConstant.RESOURCE_CLIENTES + LibreriaConstant.RESOURCE_CLIENTE
            + LibreriaConstant.RESOURCE_GENERIC_ID)
    public ResponseEntity<ClienteDTO> findById(@PathVariable("id") int id) {
        return new ResponseEntity<ClienteDTO>(this.clienteService.findById(id), HttpStatus.OK);
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * Este método se utiliza para crear un nuevo cliente en la base de datos utilizando los datos proporcionados
     * en el DTO del cliente.
     * Después de la creación exitosa, devuelve un {@link ResponseEntity} que contiene un {@link HrefEntityDTO}
     * con detalles del cliente creado, la URL del recurso y el estado HTTP adecuado para representar
     * el resultado de la operación (normalmente {@link HttpStatus#CREATED} si la creación es exitosa).
     *
     * @param dto el DTO del cliente a crear.
     * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles del cliente creado,
     *         la URL del recurso y el estado HTTP adecuado que representa el resultado de la operación.
     */
    @PostMapping(LibreriaConstant.RESOURCE_CLIENTES + LibreriaConstant.RESOURCE_CLIENTE)
    public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody ClienteDTORequest dto) {
        
        return new ResponseEntity<HrefEntityDTO>(this.clienteService.save(dto), HttpStatus.CREATED);

    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * Este método se utiliza para actualizar la información de un cliente existente en la base de datos.
     * Para ello, se proporciona un DTO del cliente con la información actualizada, así como el ID del cliente
     * que se desea actualizar.
     * Después de la actualización exitosa, devuelve un {@link ResponseEntity} que contiene un {@link HrefEntityDTO}
     * con detalles del cliente actualizado, la URL del recurso y el estado HTTP adecuado para representar
     * el resultado de la operación (normalmente {@link HttpStatus#OK} si la actualización es exitosa).
     *
     * @param dto el DTO del cliente con la información actualizada.
     * @param id  el ID del cliente a actualizar.
     * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles del cliente actualizado,
     *         la URL del recurso y el estado HTTP adecuado que representa el resultado de la operación.
     */
    @PutMapping(LibreriaConstant.RESOURCE_CLIENTES + LibreriaConstant.RESOURCE_CLIENTE
            + LibreriaConstant.RESOURCE_GENERIC_ID)
    public ResponseEntity<HrefEntityDTO> update(@RequestBody ClienteDTORequest dto, @PathVariable("id") int id) {

        return new ResponseEntity<HrefEntityDTO>(this.clienteService.update(dto, id), HttpStatus.OK);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * Este método se utiliza para eliminar un cliente específico de la base de datos utilizando su ID.
     * Devuelve un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles del cliente eliminado,
     * la URL del recurso y el estado HTTP adecuado para representar el resultado de la operación
     * (normalmente {@link HttpStatus#OK} si la eliminación es exitosa).
     *
     * @param id el ID del cliente a eliminar.
     * @return un {@link ResponseEntity} que contiene un {@link HrefEntityDTO} con detalles del cliente eliminado,
     *         la URL del recurso y el estado HTTP adecuado que representa el resultado de la operación.
     */
    @DeleteMapping(LibreriaConstant.RESOURCE_CLIENTES + LibreriaConstant.RESOURCE_CLIENTE
            + LibreriaConstant.RESOURCE_GENERIC_ID)
    public ResponseEntity<HrefEntityDTO> delete(@PathVariable("id") int id) {
        
        
        return new ResponseEntity<HrefEntityDTO>(this.clienteService.delete(id), HttpStatus.OK);    

    }

}



