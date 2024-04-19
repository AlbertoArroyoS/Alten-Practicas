package com.alten.practica.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.IClienteCompraLibroMapper;
import com.alten.practica.repository.IClienteCompraLibroRepository;
import com.alten.practica.service.IClienteCompraLibroService;
import com.alten.practica.util.LibreriaUtil;

@Transactional // Transacción para todos los métodos del servicio
@Service
public class ClienteCompraLibroServiceImpl implements IClienteCompraLibroService{
	
	@Autowired
	IClienteCompraLibroRepository clienteCompraLibroRepository;
	@Autowired
	IClienteCompraLibroMapper clienteCompraLibrosMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	@Override
	public HrefEntityDTO save(ClienteCompraLibroDTORequest dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteCompraLibroDTO findById(int id) {
		
		ClienteCompraLibro cpl = clienteCompraLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La compra con id %s no existe", id)));
			
		return clienteCompraLibrosMapper.toDTO(cpl);
	}

	@Override
	public List<ClienteCompraLibroDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO update(ClienteCompraLibroDTORequest dto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
