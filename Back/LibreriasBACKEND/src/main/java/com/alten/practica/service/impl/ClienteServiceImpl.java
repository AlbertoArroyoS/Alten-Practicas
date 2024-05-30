package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;
import com.alten.practica.modelo.entidad.mapper.IClienteMapper;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.service.IClienteService;
import com.alten.practica.service.encriptacion.DeterministicEncryptionService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz ClienteService
 * 
 * @see com.alten.practica.service.IClienteService
 * 
 */
@Transactional // Transacción para todos los métodos del servicio
@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    IClienteRepository clienteRepository;

    @Autowired
    IClienteMapper clienteMapper;

    @Autowired
    LibreriaUtil libreriaUtil;

    @Autowired
    DeterministicEncryptionService encryptionService;

    /**
     * Guarda un nuevo cliente en la base de datos.
     * 
     * @param dto Los datos del cliente a guardar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
     *         cliente creado.
     * @throws IllegalStateException Si ya existe un cliente con el mismo nombre y
     *                               apellidos proporcionados en los datos.
     */
    @Override
    public HrefEntityDTO save(ClienteDTORequest dto) {
    	clienteRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos()).ifPresent(a -> {
            throw new IllegalStateException("Cliente con el nombre '" + dto.getNombre() + "' y apellidos '"
                    + dto.getApellidos() + "' ya existe");
        });

        Cliente cliente = this.clienteMapper.toBean(dto);
        cliente.setEncryptionService(encryptionService);
        cliente.encryptFields();
        cliente = this.clienteRepository.save(cliente);

        return libreriaUtil.createHrefFromResource(cliente.getId(), LibreriaResource.CLIENTE);
    }

    /**
     * Busca un cliente por su ID en la base de datos.
     * 
     * @param id El ID del cliente a buscar.
     * @return Un objeto {@code ClienteDTO} que representa el cliente encontrado.
     * @throws EntityNotFoundException Si no se encuentra ningún cliente con el ID
     *                                 proporcionado.
     */
    @Transactional(readOnly = true)
    @Override
    public ClienteDTO findById(int id) {
        Cliente cpl = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

        cpl.setEncryptionService(encryptionService);
        cpl.decryptFields();
        return clienteMapper.toDTO(cpl);
    }

    /**
     * Obtiene una lista de todos los clientes en la base de datos.
     * 
     * @return Una lista de objetos {@code ClienteDTO} que representan todos los
     *         clientes en la base de datos.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(cliente -> {
            cliente.setEncryptionService(encryptionService);
            cliente.decryptFields();
        });
        return clientes.stream().map(clienteMapper::toDTO).toList();
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     * 
     * @param dto Los nuevos datos del cliente.
     * @param id  El ID del cliente a actualizar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
     *         cliente actualizado.
     * @throws IllegalStateException   Si ya existe un cliente con el mismo nombre y
     *                                 apellidos proporcionados en los datos.
     * @throws EntityNotFoundException Si no se encuentra ningún cliente con el ID
     *                                 proporcionado.
     */
    @Override
    public HrefEntityDTO update(ClienteDTORequest dto, int id) {
        // Encuentra el cliente por id
        Cliente cpl = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

        // Verifica si ya existe otro cliente con el mismo nombre y apellidos, excluyendo el cliente actual
        clienteRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos()).ifPresent(a -> {
            if (a.getId() != id) {
                throw new IllegalStateException("Cliente con el nombre '" + dto.getNombre() + "' y apellidos '"
                        + dto.getApellidos() + "' ya existe");
            }
        });

        // Actualiza los campos del cliente
        cpl.setNombre(dto.getNombre());
        cpl.setApellidos(dto.getApellidos());
        cpl.setEmail(dto.getEmail());
        cpl.setEncryptionService(encryptionService);
        cpl.encryptFields();

        // Guarda los cambios y retorna la entidad
        return libreriaUtil.createHrefFromResource(this.clienteRepository.save(cpl).getId(), LibreriaResource.CLIENTE);
    }

    /**
     * Elimina un cliente de la base de datos.
     * 
     * @param id El ID del cliente a eliminar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
     *         cliente eliminado.
     * @throws EntityNotFoundException Si no se encuentra ningún cliente con el ID
     *                                 proporcionado.
     */
    @Override
    public HrefEntityDTO delete(int id) {
        Cliente cpl = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

        this.clienteRepository.delete(cpl);

        return libreriaUtil.createHrefFromResource(cpl.getId(), LibreriaResource.CLIENTE);
    }

}

