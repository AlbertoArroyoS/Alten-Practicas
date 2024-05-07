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
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;
import com.alten.practica.service.IAutorService;

/*
 * Clase de test para el controlador de Autor
 * Se realizan pruebas unitarias para los métodos del controlador de Autor
 * @see com.alten.practica.controlador.AutorController
 */
@SpringBootTest
public class TestAutorController {

	@InjectMocks
	private AutorController autorController;

	@Mock
	private IAutorService autorService;

	@DisplayName("Test para el método buscarKeyWord del controlador de Autor")
	@Test
	public void testBuscarKeyWordSQL() {
		// Preparar los datos de prueba
		String keyword = "keyword";
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(1);
		autorDTO.setNombre("Nombre");

		List<AutorDTO> autorDTOList = Collections.singletonList(autorDTO);

		// Simular el comportamiento del servicio
		when(autorService.buscarKeyWordSQL(keyword)).thenReturn(autorDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<AutorDTO>> responseEntity = autorController.buscarKeyWordSQL(keyword);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(autorDTOList, responseEntity.getBody());
	}

	@DisplayName("Test para el método findById del controlador de Autor")
	@Test
	public void testFindById() {
		// Preparar los datos de prueba
		int id = 1;
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(id);
		autorDTO.setNombre("Nombre");

		// Simular el comportamiento del servicio
		when(autorService.findById(id)).thenReturn(autorDTO);

		// Ejecutar el método del controlador
		ResponseEntity<AutorDTO> responseEntity = autorController.findById(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(autorDTO, responseEntity.getBody());
	}
/*
	@DisplayName("Test para el método findAll del controlador de Autor")
	@Test
	public void testFindAll() {
		// Preparar los datos de prueba
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(1);
		autorDTO.setNombre("Nombre");
		List<AutorDTO> autorDTOList = Collections.singletonList(autorDTO);

		// Simular el comportamiento del servicio
		when(autorService.findAll()).thenReturn(autorDTOList);

		// Ejecutar el método del controlador
		ResponseEntity<List<AutorDTO>> responseEntity = autorController.findAll();

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(autorDTOList, responseEntity.getBody());
	}*/

	@DisplayName("Test para el método save del controlador de Autor")
	@Test
	public void testSave() {
		// Preparar los datos de prueba
		AutorDTORequest autorDTORequest = new AutorDTORequest();
		autorDTORequest.setNombre("Nombre");
		autorDTORequest.setApellidos("Apellidos");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(1);

		// Simular el comportamiento del servicio
		when(autorService.save(autorDTORequest)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = autorController.save(autorDTORequest);

		// Verificar el resultado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para el método delete del controlador de Autor")
	@Test
	public void testDelete() {
		// Preparar los datos de prueba
		int id = 1;
		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(autorService.delete(id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = autorController.delete(id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

	@DisplayName("Test para el método update del controlador de Autor")
	@Test
	public void testUpdate() {
		// Preparar los datos de prueba
		int id = 1;
		AutorDTORequest autorDTORequest = new AutorDTORequest();
		autorDTORequest.setNombre("Nombre");
		autorDTORequest.setApellidos("Apellidos");

		HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
		hrefEntityDTO.setId(id);

		// Simular el comportamiento del servicio
		when(autorService.update(autorDTORequest, id)).thenReturn(hrefEntityDTO);

		// Ejecutar el método del controlador
		ResponseEntity<HrefEntityDTO> responseEntity = autorController.update(autorDTORequest, id);

		// Verificar el resultado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(hrefEntityDTO, responseEntity.getBody());
	}

}
