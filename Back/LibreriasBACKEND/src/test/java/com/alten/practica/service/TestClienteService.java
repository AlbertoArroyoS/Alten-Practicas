package com.alten.practica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;
import com.alten.practica.modelo.entidad.mapper.IClienteMapper;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.service.impl.ClienteServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase para probar los métodos de la clase ClienteService
 * 
 * @see com.alten.practica.service.impl.ClienteServiceImpl
 * 
 */
@SpringBootTest
class TestClienteService {

	@Mock
	private IClienteRepository clienteRepository;

	@Mock
	private IClienteMapper clienteMapper;

	@Mock
	private LibreriaUtil libreriaUtil;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		resetMocks();
	}

	private void resetMocks() {
		reset(clienteRepository, clienteMapper, libreriaUtil);
	}

	@Test
	@DisplayName("Test para guardar un nuevo cliente")
	public void testSave() {

		ClienteDTORequest dtoRequest = new ClienteDTORequest("Alberto", "Arroyo", "aas@example.com", "password", 1);
		Cliente cliente = new Cliente();
		cliente.setId(1);

		when(clienteRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.empty());
		when(clienteMapper.toBean(dtoRequest)).thenReturn(cliente);
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTE)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = clienteService.save(dtoRequest);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@Test
	@DisplayName("Test para guardar un cliente con nombre y apellidos ya existentes")
	public void testSaveExistingClient() {
		ClienteDTORequest dtoRequest = new ClienteDTORequest("Alberto", "Arroyo", "aas@example.com", "password", 1);

		when(clienteRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.of(new Cliente()));

		assertThrows(IllegalStateException.class, () -> clienteService.save(dtoRequest));
	}

	@Test
	@DisplayName("Test para buscar un cliente por ID")
	public void testFindById() {
		Cliente cliente = new Cliente();
		cliente.setId(1);

		when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

		ClienteDTO expectedDTO = new ClienteDTO();
		expectedDTO.setId(1);
		when(clienteMapper.toDTO(cliente)).thenReturn(expectedDTO);

		ClienteDTO result = clienteService.findById(1);

		assertEquals(expectedDTO, result);
	}

	@Test
	@DisplayName("Test para buscar un cliente por ID que no existe")
	public void testFindByIdNotFound() {
		when(clienteRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> clienteService.findById(1));
	}

	@Test
	@DisplayName("Test para listar todos los clientes")
	public void testFindAll() {
		List<Cliente> clientes = List.of(new Cliente(), new Cliente());

		when(clienteRepository.findAll()).thenReturn(clientes);

		List<ClienteDTO> expectedDTOList = List.of(new ClienteDTO(), new ClienteDTO());
		when(clienteMapper.toDTO(any())).thenReturn(new ClienteDTO());

		List<ClienteDTO> result = clienteService.findAll();

		assertEquals(expectedDTOList, result);
	}

	@Test
	@DisplayName("Test para actualizar un cliente")
	public void testUpdate() {
		ClienteDTORequest dtoRequest = new ClienteDTORequest("Alberto", "Arroyo", "aas@example.com", "password", 1);

		when(clienteRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.empty());
		when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));

		Cliente cliente = new Cliente();
		cliente.setId(1);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTE)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = clienteService.update(dtoRequest, 1);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@Test
	@DisplayName("Test para actualizar un cliente que no existe")
	public void testUpdateNotFound() {
		ClienteDTORequest dtoRequest = new ClienteDTORequest("Alberto", "Arroyo", "aas@example.com", "password", 1);

		when(clienteRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.empty());
		when(clienteRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> clienteService.update(dtoRequest, 1));
	}

	@Test
	@DisplayName("Test para actualizar un cliente con nombre y apellidos ya existentes")
	public void testUpdateExistingClient() {
		ClienteDTORequest dtoRequest = new ClienteDTORequest("Alberto", "Arroyo", "aas@example.com", "password", 1);

		when(clienteRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.of(new Cliente()));

		assertThrows(IllegalStateException.class, () -> clienteService.update(dtoRequest, 1));
	}

	@Test
	@DisplayName("Test para eliminar un cliente")
	public void testDelete() {
		Cliente cliente = new Cliente();
		cliente.setId(1);

		when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTE)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = clienteService.delete(1);

		assertEquals(expectedHrefEntityDTO, result);
		verify(clienteRepository, times(1)).delete(cliente);
	}

	@Test
	@DisplayName("Test para eliminar un cliente que no existe")
	public void testDeleteNotFound() {
		when(clienteRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> clienteService.delete(1));
	}
}