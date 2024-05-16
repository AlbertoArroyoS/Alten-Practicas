package com.alten.practica.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;
import com.alten.practica.service.IClienteService;

/*
 * Clase de test para el controlador de Cliente
 * Se realizan pruebas unitarias para los métodos del controlador de Cliente
 * @see com.alten.practica.controlador.ClienteControlador
 */
@SpringBootTest
class TestClienteControlador {

	@Autowired
	private ClienteController clienteControlador;

	@MockBean
	private IClienteService clienteService;

	@DisplayName("Test para el método findAll")
	@Test
	void testFindAll() {
		// Preparar los datos de prueba
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(1);
		clienteDTO.setNombre("Nombre de prueba");

		List<ClienteDTO> clienteDTOList = Collections.singletonList(clienteDTO);

		// Simular el comportamiento del servicio
		when(clienteService.findAll()).thenReturn(clienteDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<ClienteDTO>> responseEntity = clienteControlador.findAll();

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(clienteDTOList, responseEntity.getBody());
	}

	@DisplayName("Test para el método findById")
	@Test
	void testFindById() {
		// Preparar los datos de prueba
		int id = 1;
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(id);
		clienteDTO.setNombre("Nombre de prueba");

		// Simular el comportamiento del servicio
		when(clienteService.findById(id)).thenReturn(clienteDTO);

		// Ejecutar el método del controlador
		ResponseEntity<ClienteDTO> responseEntity = clienteControlador.findById(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(clienteDTO, responseEntity.getBody());
	}

	@DisplayName("Test para el método save")
	@Test
	void testSave() {
		// Preparar los datos de prueba
		ClienteDTORequest clienteDTORequest = new ClienteDTORequest();
		clienteDTORequest.setNombre("Nombre de prueba");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(1);

		// Simular el comportamiento del servicio
		when(clienteService.save(clienteDTORequest)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = clienteControlador.save(clienteDTORequest);

		// Verificar el resultado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para el método update")
	@Test
	void testUpdate() {
		// Preparar los datos de prueba
		int id = 1;
		ClienteDTORequest clienteDTORequest = new ClienteDTORequest();
		clienteDTORequest.setNombre("Nombre de prueba");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(clienteService.update(clienteDTORequest, id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = clienteControlador.update(clienteDTORequest, id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para el método delete")
	@Test
	void testDelete() {
		// Preparar los datos de prueba
		int id = 1;
		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(clienteService.delete(id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = clienteControlador.delete(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}
}
