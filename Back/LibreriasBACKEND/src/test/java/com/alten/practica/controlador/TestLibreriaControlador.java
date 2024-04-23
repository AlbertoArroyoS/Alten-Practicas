package com.alten.practica.controlador;
/*
 * Clase de pruebas para el controlador REST de la librería.
 * Se encarga de realizar pruebas unitarias sobre los métodos del controlador.
 * @see com.alten.practica.servicio.ILibreriaService
 */

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
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;
import com.alten.practica.service.ILibreriaService;

@SpringBootTest
public class TestLibreriaControlador {

	@Autowired
	private LibreriaController libreriaController;

	@MockBean
	private ILibreriaService libreriaService;

	@DisplayName("Test para probar el método findAll()")
	@Test
	void testFindAll() {
		// Preparar los datos de prueba
		LibreriaDTO libreriaDTO = new LibreriaDTO();
		libreriaDTO.setId(1);
		libreriaDTO.setNombreLibreria("Nombre de prueba");

		List<LibreriaDTO> libreriaDTOList = Collections.singletonList(libreriaDTO);

		// Simular el comportamiento del servicio
		when(libreriaService.findAll()).thenReturn(libreriaDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<LibreriaDTO>> responseEntity = libreriaController.findAll();

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libreriaDTOList, responseEntity.getBody());
	}

	@DisplayName("Test para probar el método findById()")
	@Test
	void testFindById() {
		// Preparar los datos de prueba
		int id = 1;
		LibreriaDTO libreriaDTO = new LibreriaDTO();
		libreriaDTO.setId(id);
		libreriaDTO.setNombreLibreria("Nombre de prueba");

		// Simular el comportamiento del servicio
		when(libreriaService.findById(id)).thenReturn(libreriaDTO);

		// Ejecutar el método del controlador
		ResponseEntity<LibreriaDTO> responseEntity = libreriaController.findById(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libreriaDTO, responseEntity.getBody());
	}

	@DisplayName("Test para probar el método save()")
	@Test
	void testSave() {
		// Preparar los datos de prueba
		LibreriaDTORequest libreriaDTORequest = new LibreriaDTORequest();
		libreriaDTORequest.setNombreLibreria("Nombre de prueba");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(1);

		// Simular el comportamiento del servicio
		when(libreriaService.save(libreriaDTORequest)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaController.save(libreriaDTORequest);

		// Verificar el resultado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para probar el método update()")
	@Test
	void testUpdate() {
		// Preparar los datos de prueba
		int id = 1;
		LibreriaDTORequest libreriaDTORequest = new LibreriaDTORequest();
		libreriaDTORequest.setNombreLibreria("Nombre de prueba");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libreriaService.update(libreriaDTORequest, id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaController.update(libreriaDTORequest, id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para probar el método delete()")
	@Test
	void testDelete() {
		// Preparar los datos de prueba
		int id = 1;
		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libreriaService.delete(id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaController.delete(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

}
