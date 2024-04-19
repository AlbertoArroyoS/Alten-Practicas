package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaLibroMapper;
import com.alten.practica.repository.ILibreriaLibroRepository;
import com.alten.practica.service.ILibreriaLibroService;
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
	
	@Override
	public HrefEntityDTO save(LibreriaLibroDTORequest dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibreriaLibroDTO findById(int id) {
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));
			
		return libreriaLibroMapper.toDTO(cpl);
	}

	@Override
	public List<LibreriaLibroDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO update(LibreriaLibroDTORequest dto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
