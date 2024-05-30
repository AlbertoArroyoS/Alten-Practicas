package com.alten.practica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaMapper;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.service.ILibreriaService;
import com.alten.practica.service.encriptacion.EncryptionService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

import jakarta.persistence.EntityExistsException;

/**
 * Clase que implementa la interfaz LibreriaService
 * 
 * @see com.alten.practica.service.ILibreriaService
 * 
 */
@Transactional
@Service
public class LibreriaServiceImpl implements ILibreriaService {

    @Autowired
    ILibreriaRepository libreriaRepository;

    @Autowired
    ILibreriaMapper libreriaMapper;

    @Autowired
    LibreriaUtil libreriaUtil;

    @Autowired
    EncryptionService encryptionService;

    /**
     * Guarda una nueva librería en la base de datos.
     * 
     * @param dto Los datos de la librería a guardar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
     *         la librería creada.
     * @throws EntityExistsException Si ya existe una librería con el mismo nombre.
     */
    @Override
    public HrefEntityDTO save(LibreriaDTORequest dto) {

        libreriaRepository.findByNombreLibreria(dto.getNombreLibreria()).ifPresent(libreria -> {
            throw new EntityExistsException(String.format("La libreria %s ya existe", dto.getNombreLibreria()));
        });

        Libreria libreria = this.libreriaMapper.toBean(dto);
        libreria.encryptFields(encryptionService);
        libreria = this.libreriaRepository.save(libreria);

        return libreriaUtil.createHrefFromResource(libreria.getId(), LibreriaResource.LIBRERIA);
    }

    /**
     * Busca una librería por su ID en la base de datos.
     * 
     * @param id El ID de la librería a buscar.
     * @return Un objeto {@code LibreriaDTO} que representa la librería encontrada.
     * @throws EntityNotFoundException Si no se encuentra ninguna librería con el ID
     *                                 proporcionado.
     */
    @Transactional(readOnly = true)
    @Override
    public LibreriaDTO findById(int id) {

        Libreria libreria = libreriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", id)));

        libreria.decryptFields(encryptionService);
        return libreriaMapper.toDTO(libreria);
    }

    /**
     * Obtiene una lista de todas las librerías en la base de datos.
     * 
     * @return Una lista de objetos {@code LibreriaDTO} que representan todas las
     *         librerías en la base de datos.
     */
    @Transactional(readOnly = true)
    @Override
    public List<LibreriaDTO> findAll() {
        List<Libreria> lista = this.libreriaRepository.findAll();
        lista.forEach(libreria-> libreria.decryptFields(encryptionService));
        return lista.stream().map(libreria -> libreriaMapper.toDTO(libreria)).collect(Collectors.toList());
        
    }

    /**
     * Actualiza los datos de una librería existente en la base de datos.
     * 
     * @param dto Los nuevos datos de la librería.
     * @param id  El ID de la librería a actualizar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
     *         la librería actualizada.
     * @throws EntityNotFoundException Si no se encuentra ninguna librería con el ID
     *                                 proporcionado, o si ya existe una librería
     *                                 con el mismo nombre que la nueva.
     */
    @Override
    public HrefEntityDTO update(LibreriaDTORequest dto, int id) {
        // Encuentra la librería por id
        Libreria libreria = libreriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("La librería con id %s no existe", id)));
        libreria.setId(id);

        // Comprobar si existen duplicados antes de actualizar, excluyendo la librería actual
        List<Libreria> libreriasDuplicados = libreriaRepository.findByNombreLibreria(dto.getNombreLibreria()).stream()
                .filter(a -> a.getId() != id)
                .collect(Collectors.toList());

        if (!libreriasDuplicados.isEmpty()) {
            throw new IllegalStateException("La librería con el nombre '" + dto.getNombreLibreria() + "' ya existe");
        }

        // Actualiza los campos de la librería
        libreria.setNombreLibreria(dto.getNombreLibreria());
        libreria.setNombreDueno(dto.getNombreDueno());
        libreria.setDireccion(dto.getDireccion());
        libreria.setCiudad(dto.getCiudad());
        libreria.encryptFields(encryptionService);

        // Guarda los cambios y retorna la entidad
        Libreria libreriaActualizada = libreriaRepository.save(libreria);
        return libreriaUtil.createHrefFromResource(libreriaActualizada.getId(), LibreriaResource.LIBRERIA);
    }

    /**
     * Elimina una librería de la base de datos.
     * 
     * @param id El ID de la librería a eliminar.
     * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
     *         la librería eliminada.
     * @throws EntityNotFoundException Si no se encuentra ninguna librería con el ID
     *                                 proporcionado.
     */
    @Override
    public HrefEntityDTO delete(int id) {
        Libreria libreria = libreriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", id)));

        this.libreriaRepository.delete(libreria);

        return libreriaUtil.createHrefFromResource(libreria.getId(), LibreriaResource.LIBRERIA);
    }

}
