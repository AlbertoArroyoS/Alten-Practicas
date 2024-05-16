package com.alten.practica.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.service.ILibreriaLibroService;

/**
 * Pruebas unitarias para el controlador de libros de librería.
 * 
 * @see LibreriaLibroController
 */
@SpringBootTest
class TestLibreriaLibroControlador {

	@InjectMocks
	private LibreriaLibroController libreriaLibroController;

	@Mock
	private ILibreriaLibroService libreriaLibroService;
/*
	@Test
	void testFindAll() {
		// Preparar los datos de prueba
		LibreriaLibroDTO libroDTO = new LibreriaLibroDTO();
		libroDTO.setId(1);
		libroDTO.setNombreLibreria("Nombre de prueba");

		List<LibreriaLibroDTO> libroDTOList = Collections.singletonList(libroDTO);

		// Simular el comportamiento del servicio
		when(libreriaLibroService.findAll()).thenReturn(libroDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<LibreriaLibroDTO>> responseEntity = libreriaLibroController.findAll();

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libroDTOList, responseEntity.getBody());
	}*/

	@Test
	void testFindById() {
		// Preparar los datos de prueba
		int id = 1;
		LibreriaLibroDTO libroDTO = new LibreriaLibroDTO();
		libroDTO.setId(id);
		libroDTO.setNombreLibreria("Nombre de prueba");

		// Simular el comportamiento del servicio
		when(libreriaLibroService.findById(id)).thenReturn(libroDTO);

		// Ejecutar el método del controlador
		ResponseEntity<LibreriaLibroDTO> responseEntity = libreriaLibroController.findById(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(libroDTO, responseEntity.getBody());
	}

	@Test
	void testSave() {
		// Preparar los datos de prueba
		LibreriaLibroDTORequest libroDTORequest = new LibreriaLibroDTORequest();
		libroDTORequest.setIdLibreria(1);

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(1);

		// Simular el comportamiento del servicio
		when(libreriaLibroService.save(libroDTORequest)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaLibroController.save(libroDTORequest);

		// Verificar el resultado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@Test
	void testUpdate() {
		// Preparar los datos de prueba
		int id = 1;
		LibreriaLibroDTORequest libroDTORequest = new LibreriaLibroDTORequest();
		libroDTORequest.setIdLibreria(1);

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libreriaLibroService.update(libroDTORequest, id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaLibroController.update(libroDTORequest, id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@Test
	void testDelete() {
		// Preparar los datos de prueba
		int id = 1;
		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(libreriaLibroService.delete(id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = libreriaLibroController.delete(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}
}
