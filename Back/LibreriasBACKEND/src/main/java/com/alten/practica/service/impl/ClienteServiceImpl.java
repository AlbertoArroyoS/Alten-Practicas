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
import com.alten.practica.util.LibreriaUtil;

@Transactional // Transacción para todos los métodos del servicio
@Service
public class ClienteServiceImpl implements IClienteService{
	
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IClienteMapper clienteMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	@Override
	public HrefEntityDTO save(ClienteDTORequest dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO findById(int id) {
		Cliente cpl = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));
			
		return clienteMapper.toDTO(cpl);
	}

	@Override
	public List<ClienteDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO update(ClienteDTORequest dto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
