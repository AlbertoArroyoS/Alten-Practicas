package com.alten.practica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;
import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.mapper.ILibreriaMapper;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.service.ILibreriaService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

import jakarta.persistence.EntityExistsException;

/**
 * Clase que implementa la interfaz LibreriaService
 * 
 * @see com.alten.practica.service.ILibreriaService
 * 
 * 
 */
@Transactional
@Service
public class LibreriaServiceImpl implements ILibreriaService {

	// inyeccion por constructor del repositorio de la libreria
	@Autowired
	ILibreriaRepository libreriaRepository;
	
	@Autowired
	ILibreriaMapper libreriaMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	// Metodo para convertir de entidad a dto. Ya no se necesita, realizar el mapeo con MapStruct

	/*
	public LibreriaDTO convertirBeanADTO(Libreria bean) {
		return LibreriaDTO.builder().id(bean.getId()).nombreLibreria(bean.getNombreLibreria())
				.nombreDueno(bean.getNombreDueno()).direccion(bean.getDireccion()).ciudad(bean.getCiudad()).build();

	}*/

	@Override
	public HrefEntityDTO save(LibreriaDTORequest dto) {
		
		libreriaRepository.findByNombreLibreria(dto.getNombreLibreria())
                .ifPresent(libreria -> {
                    throw new EntityExistsException(String.format("La libreria %s ya existe", dto.getNombreLibreria()));
                });
		
		Libreria libreria = this.libreriaRepository.save(this.libreriaMapper.toBean(dto));
		
		return libreriaUtil.createHrefFromResource(libreria.getId(), LibreriaResource.LIBRERIA);

		
		/*
		 * Forma vieja
		Libreria libreria = new Libreria();
		libreria.setNombreLibreria(dto.getNombreLibreria());
		libreria.setNombreDueno(dto.getNombreDueno());
		libreria.setDireccion(dto.getDireccion());
		libreria.setCiudad(dto.getCiudad());
		return this.libreriaRepository.save(libreria).getId();*/

	}
	@Transactional (readOnly = true)
	@Override
	public LibreriaDTO findById(int id) {
		/*
		 * Libreria bean = this.libreriaRepository.findById(id).get(); LibreriaDTO
		 * libreriaDTO = new LibreriaDTO(); libreriaDTO.setId(bean.getId());
		 * libreriaDTO.setNombre(bean.getNombreLibreria()); return libreriaDTO;
		 */
		/*
		Libreria bean = this.libreriaRepository.findById(id).orElse(null); // Utiliza orElse(null) para evitar
																			// NullPointerException

		if (bean != null) {
			return libreriaMapper.toDTO(bean); // Utiliza el método convertirBeanADTO para convertir el bean a DTO
		} else {
			return null; // Devuelve null si el bean no se encuentra en la base de datos
		}*/
		
		Libreria libreria = libreriaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", id)));
			
		return libreriaMapper.toDTO(libreria);
	}
	@Transactional (readOnly = true)
	@Override
	public List<LibreriaDTO> findAll() {
		List<Libreria> lista = this.libreriaRepository.findAll();
		/*
		 * Sin funciones lambda y streams List<LibreriaDTO> listaDTO = new
		 * ArrayList<>(); for (Libreria bean : lista) { LibreriaDTO libreriaDTO = new
		 * LibreriaDTO(); libreriaDTO.setId(bean.getId());
		 * libreriaDTO.setNombre(bean.getNombreLibreria()); listaDTO.add(libreriaDTO); }
		 * return listaDTO;
		 */

		/*
		 * Con funciones lambda y streams return lista.stream( .map(bean ->
		 * convertirBeanADTO(bean) .collect(Collectors.toList());
		 */
		//
		return lista.stream()
	            .map(libreria -> libreriaMapper.toDTO(libreria))
	            .collect(Collectors.toList());

	}

	@Override
	public HrefEntityDTO update(LibreriaDTORequest dto, int id) {
		
		Libreria libreria = libreriaRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));
	    libreria.setId(id);
		
	 // Comprobar si existen duplicados antes de actualizar
	    List<Libreria> libreriasDuplicados = libreriaRepository.findByNombreLibreria(dto.getNombreLibreria())
	            .stream()
	            .filter(a -> a.getId() != id) // Excluir el autor actual de la comprobación de duplicados
	            .collect(Collectors.toList());

	    if (!libreriasDuplicados.isEmpty()) {
	        throw new IllegalStateException("La libreria con el nombre '" + dto.getNombreLibreria() +  "' ya existe");
	    }
	    
	    libreria.setNombreLibreria(dto.getNombreLibreria());
	    libreria.setNombreDueno(dto.getNombreDueno());
	    libreria.setDireccion(dto.getDireccion());
	    libreria.setCiudad(dto.getCiudad());
	    
	    Libreria libreriaActualizada = libreriaRepository.save(libreria);
	    return libreriaUtil.createHrefFromResource(libreriaActualizada.getId(), LibreriaResource.LIBRERIA);
		
		/*
		 * Forma vieja
		Libreria bean = this.libreriaRepository.findById(id).get();
		bean.setId(id);
		bean.setNombreLibreria(dto.getNombreLibreria());
		bean.setNombreDueno(dto.getNombreDueno());
		bean.setDireccion(dto.getDireccion());
		bean.setCiudad(dto.getCiudad());
		return this.libreriaRepository.save(bean).getId();*/
	}

	@Override
	public HrefEntityDTO delete(int id) {
		
		Libreria libreria = libreriaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", id)));
		
		this.libreriaRepository.delete(libreria);
		
		return libreriaUtil.createHrefFromResource(libreria.getId(), LibreriaResource.LIBRERIA);
		
		

	}

}
