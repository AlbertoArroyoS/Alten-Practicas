package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaLibroMapper;
import com.alten.practica.repository.ILibreriaLibroRepository;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.ILibreriaLibroService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

@Transactional // Transacción para todos los métodos del servicio
@Service
public class LibreriaLibroServiceImpl implements ILibreriaLibroService{
	
	@Autowired
	ILibreriaLibroRepository libreriaLibroRepository;
	@Autowired
	ILibreriaLibroMapper libreriaLibroMapper;
	@Autowired
	LibreriaUtil libreriaUtil;
	@Autowired
	ILibreriaRepository libreriaRepository;
	@Autowired
	ILibroRepository libroRepository;
	
	@Override
	public HrefEntityDTO save(LibreriaLibroDTORequest dto) {
		Libreria cli = libreriaRepository.findById(dto.getIdLibreria()).orElseThrow(
				() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", dto.getIdLibreria())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));
		
		LibreriaLibro ccl = LibreriaLibro.builder()
				.libreria(cli)
				.libro(libro)
				.cantidad(dto.getCantidad())
				.precio(dto.getPrecio())
				.edicion(dto.getEdicion())
				.fechaPublicacion(dto.getFechaPublicacion())
				.build();
				
				
		
		return libreriaUtil.createHrefFromResource(this.libreriaLibroRepository.save(ccl).getId(), LibreriaResource.LIBRERIALIBRO);
	}
	@Transactional (readOnly = true)
	@Override
	public LibreriaLibroDTO findById(int id) {
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));
			
		return libreriaLibroMapper.toDTO(cpl);
	}
	@Transactional (readOnly = true)
	@Override
	public List<LibreriaLibroDTO> findAll() {
		List<LibreriaLibro> lista = libreriaLibroRepository.findAll();
		return lista.stream().map(libreriaLibroMapper::toDTO).toList();
	}

	@Override
	public HrefEntityDTO update(LibreriaLibroDTORequest dto, int id) {
		
		Libreria cli = libreriaRepository.findById(dto.getIdLibreria()).orElseThrow(
				() -> new EntityNotFoundException(String.format("La libreria con id %s no existe", dto.getIdLibreria())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));
		
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));
			
		cpl.setLibreria(cli);
		cpl.setLibro(libro);
		cpl.setCantidad(dto.getCantidad());
		cpl.setPrecio(dto.getPrecio());		
		cpl.setEdicion(dto.getEdicion());
		cpl.setFechaPublicacion(dto.getFechaPublicacion());
		
		return libreriaUtil.createHrefFromResource(this.libreriaLibroRepository.save(cpl).getId(), LibreriaResource.LIBRERIALIBRO);

	}

	@Override
	public HrefEntityDTO delete(int id) {
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));
			
		this.libreriaLibroRepository.delete(cpl);
		return libreriaUtil.createHrefFromResource(cpl.getId(), LibreriaResource.LIBRERIALIBRO);
		
	}

}
