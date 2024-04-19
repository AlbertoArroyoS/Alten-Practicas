package com.alten.practica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.IClienteCompraLibroMapper;
import com.alten.practica.repository.IClienteCompraLibroRepository;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.IClienteCompraLibroService;
import com.alten.practica.util.LibreriaResource;
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
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	ILibroRepository libroRepository;
	

	@Override
	public HrefEntityDTO save(ClienteCompraLibroDTORequest dto) {
		Cliente cli = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", dto.getIdCliente())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));
		
		ClienteCompraLibro ccl = ClienteCompraLibro.builder()
				.cliente(cli)
				.libro(libro)
				.fechaCompra(dto.getFechaCompra()).build();
		
		return libreriaUtil.createHrefFromResource(this.clienteCompraLibroRepository.save(ccl).getId(), LibreriaResource.CLIENTECOMPRALIBRO);
		
	}
	@Transactional (readOnly = true)
	@Override
	public ClienteCompraLibroDTO findById(int id) {
		
		ClienteCompraLibro cpl = clienteCompraLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La compra con id %s no existe", id)));
			
		return clienteCompraLibrosMapper.toDTO(cpl);
	}
	@Transactional (readOnly = true)
	@Override
	public List<ClienteCompraLibroDTO> findAll() {
		List<ClienteCompraLibro> lista = clienteCompraLibroRepository.findAll();
		
		return lista.stream().map(clienteCompraLibrosMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public HrefEntityDTO update(ClienteCompraLibroDTORequest dto, int id) {
		Cliente cli = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El cliente con id %s no existe", dto.getIdCliente())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));
		
		ClienteCompraLibro cpl = clienteCompraLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La compra con id %s no existe", id)));
			
		cpl.setCliente(cli);
		cpl.setLibro(libro);
		cpl.setFechaCompra(dto.getFechaCompra());
		

		return libreriaUtil.createHrefFromResource(this.clienteCompraLibroRepository.save(cpl).getId(), LibreriaResource.CLIENTECOMPRALIBRO);
		
	}

	@Override
	public HrefEntityDTO delete(int id) {

		ClienteCompraLibro cpl = clienteCompraLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("La compra con id %s no existe", id)));
			
		this.clienteCompraLibroRepository.delete(cpl);
		
		return libreriaUtil.createHrefFromResource(cpl.getId(), LibreriaResource.CLIENTECOMPRALIBRO);
		
	}
	
	

}
