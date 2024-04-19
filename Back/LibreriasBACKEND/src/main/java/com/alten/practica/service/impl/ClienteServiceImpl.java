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
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

@Transactional // Transacción para todos los métodos del servicio
@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IClienteMapper clienteMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	@Override
	public HrefEntityDTO save(ClienteDTORequest dto) {

		clienteRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos()).ifPresent(a -> {
			throw new IllegalStateException("Cliente con el nombre '" + dto.getNombre() + "' y apellidos '"
					+ dto.getApellidos() + "' ya existe");
		});

		Cliente cliente = this.clienteRepository.save(this.clienteMapper.toBean(dto));

		return libreriaUtil.createHrefFromResource(cliente.getId(), LibreriaResource.CLIENTE);

	}

	@Override
	public ClienteDTO findById(int id) {
		Cliente cpl = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

		return clienteMapper.toDTO(cpl);
	}

	@Override
	public List<ClienteDTO> findAll() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.stream().map(clienteMapper::toDTO).toList();
	}

	@Override
	public HrefEntityDTO update(ClienteDTORequest dto, int id) {
		clienteRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos()).ifPresent(a -> {
			throw new IllegalStateException("Cliente con el nombre '" + dto.getNombre() + "' y apellidos '"
					+ dto.getApellidos() + "' ya existe");
		});
		Cliente cpl = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

		cpl.setNombre(dto.getNombre());
		cpl.setApellidos(dto.getApellidos());
		cpl.setEmail(dto.getEmail());
		cpl.setPassword(dto.getPassword());
		cpl.setNivelPermiso(dto.getNivelPermiso());

		return libreriaUtil.createHrefFromResource(this.clienteRepository.save(cpl).getId(), LibreriaResource.CLIENTE);

	}

	@Override
	public HrefEntityDTO delete(int id) {
		Cliente cpl = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", id)));

		this.clienteRepository.delete(cpl);
		
		return libreriaUtil.createHrefFromResource(cpl.getId(), LibreriaResource.CLIENTE);
	}

}
