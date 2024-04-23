package com.alten.practica.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibroDTORequest;
import com.alten.practica.service.ILibroService;
import com.alten.practica.util.LibreriaUtil;

/*
 * Test para el controlador de libros
 * 
 * @see com.alten.practica.controlador.LibroController
 */
@SpringBootTest
public class TestLibroController {
	@InjectMocks
	private LibroController libroController;

	@Mock
	private ILibroService libroService;

	@Mock
	private LibreriaUtil libreriaUtil;

	@DisplayName("Test find all")
	@Test
	void testFindAll() {
		// Preparar los datos de prueba
		List<LibroDTO> libroDTOList = Collections.singletonList(new LibroDTO());

		// Simular el comportamiento del servicio
		when(libroService.findAll()).thenReturn(libroDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<LibroDTO>> responseEntity = libroController.findAll();

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libroDTOList, responseEntity.getBody());
	}

	@DisplayName("Test find by id")
	@Test
	void testFindById() {
		// Preparar los datos de prueba
		int id = 1;
		LibroDTO libroDTO = new LibroDTO();

		// Simular el comportamiento del servicio
		when(libroService.findById(id)).thenReturn(libroDTO);

		// Ejecutar el método del controlador
		ResponseEntity<LibroDTO> responseEntity = libroController.findById(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libroDTO, responseEntity.getBody());
	}

	@DisplayName("Test save")
	@Test
	void testSave() {
		// Preparar los datos de prueba
		LibroDTORequest libroDTORequest = new LibroDTORequest();

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(1);

		// Simular el comportamiento del servicio
		when(libroService.save(libroDTORequest)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libroController.save(libroDTORequest);

		// Verificar el resultado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test update")
	@Test
	void testUpdate() {
		// Preparar los datos de prueba
		int id = 1;
		LibroDTORequest libroDTORequest = new LibroDTORequest();

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libroService.update(libroDTORequest, id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libroController.update(libroDTORequest, id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test delete")
	@Test
	void testDelete() {
		// Preparar los datos de prueba
		int id = 1;
		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libroService.delete(id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libroController.delete(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

}
