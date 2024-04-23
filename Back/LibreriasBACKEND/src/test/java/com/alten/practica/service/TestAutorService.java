package com.alten.practica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;
import com.alten.practica.modelo.entidad.mapper.IAutorMapper;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.service.impl.AutorServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

public class TestAutorService {

	@Mock
	private IAutorRepository autorRepository;

	@Mock
	private IAutorMapper autorMapper;

	@Mock
	private LibreriaUtil libreriaUtil;

	@InjectMocks
	private AutorServiceImpl autorService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// Aquí puedes realizar cualquier reinicio necesario de las variables antes de
		// cada prueba
		// Por ejemplo, restablecer las simulaciones de Mockito
		resetMocks();
	}

	private void resetMocks() {
		// Reiniciar simulaciones de Mockito
		// Esto puede incluir restablecer cualquier comportamiento simulado o eliminar
		// cualquier interacción previa
		// Aquí tienes un ejemplo de cómo podrías reiniciar las simulaciones:
		reset(autorRepository, autorMapper, libreriaUtil);
	}

	@DisplayName("Test de guardar un autor")
	@Test
	public void testSave() {
		AutorDTORequest dtoRequest = new AutorDTORequest("Alberto", "Arroyo");
		Autor autor = new Autor();
		autor.setId(1);
		when(autorRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.empty());
		when(autorMapper.toBean(dtoRequest)).thenReturn(autor);
		when(autorRepository.save(autor)).thenReturn(autor);

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.AUTOR)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = autorService.save(dtoRequest);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@DisplayName("Test para buscar por id un autor")
	@Test
	public void testFindById() {
		Autor autor = new Autor();
		autor.setId(1);
		autor.setNombre("Alberto");
		autor.setApellidos("Arroyo");
		when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

		AutorDTO expectedAutorDTO = new AutorDTO();
		expectedAutorDTO.setId(1);
		expectedAutorDTO.setNombre("Alberto Arroyo");
		when(autorMapper.toDTO(autor)).thenReturn(expectedAutorDTO);

		AutorDTO result = autorService.findById(1);

		assertEquals(expectedAutorDTO, result);
	}

	@DisplayName("Test para listar todos los autores")
	@Test
	public void testFindAll() {
		List<Autor> lista = new ArrayList<>();
		Autor autor1 = new Autor();
		autor1.setId(1);
		autor1.setNombre("Alberto");
		autor1.setApellidos("Arroyo");
		Autor autor2 = new Autor();
		autor2.setId(2);
		autor2.setNombre("Juan");
		autor2.setApellidos("González");
		lista.add(autor1);
		lista.add(autor2);

		when(autorRepository.findAll()).thenReturn(lista);

		List<AutorDTO> expectedDTOList = lista.stream().map(autor -> autorMapper.toDTO(autor))
				.collect(Collectors.toList());

		List<AutorDTO> result = autorService.findAll();

		assertEquals(expectedDTOList, result);
	}

	@DisplayName("Test para actualizar un autor")
	@Test
	public void testUpdate() {
		AutorDTORequest dtoRequest = new AutorDTORequest("John", "Doe");
		Autor autor = new Autor();
		autor.setNombre("Alberto");
		autor.setApellidos("Arroyo");

		when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
		when(autorRepository.findByNombreAndApellidos("Alberto", "Arroyo")).thenReturn(Optional.ofNullable(null));
		when(autorRepository.save(autor)).thenReturn(autor);

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.AUTOR)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = autorService.update(dtoRequest, 1);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@DisplayName("Test para borrar un autor")
	@Test
	public void testDelete() {
		Autor autor1 = new Autor();
		autor1.setId(1);
		autor1.setNombre("Alberto");
		autor1.setApellidos("Arroyo");

		when(autorRepository.findById(1)).thenReturn(Optional.of(autor1));

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.AUTOR)).thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = autorService.delete(1);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@DisplayName("Test para buscar un autor cargando procedimiento SQL de busqueda")
	@Test
	public void testBuscarKeyWordSQL() {
		String nombre = "Alberto";
		List<Autor> listaAutores = new ArrayList<>();
		Autor autor1 = new Autor();
		autor1.setId(1);
		autor1.setNombre("Alberto");
		autor1.setApellidos("Arroyo");
		Autor autor2 = new Autor();
		autor2.setId(2);
		autor2.setNombre("Juan");
		autor2.setApellidos("González");
		listaAutores.add(autor1);
		listaAutores.add(autor2);

		when(autorRepository.buscarKeyWordSQL(nombre)).thenReturn(listaAutores);

		List<AutorDTO> expectedDTOList = listaAutores.stream().map(autor -> autorMapper.toDTO(autor))
				.collect(Collectors.toList());

		List<AutorDTO> result = autorService.buscarKeyWordSQL(nombre);

		assertEquals(expectedDTOList, result);
	}

	@DisplayName("Test para obtener id del autor")
	@Test
	public void testObtenerIdAutor() {
		String nombreCompleto = "Alberto Arroyo";
		List<Autor> autores = new ArrayList<>();
		Autor autor1 = new Autor();
		autor1.setId(1);
		autor1.setNombre("Alberto");
		autor1.setApellidos("Arroyo");
		autores.add(autor1);

		when(autorRepository.buscarKeyWordSQL(nombreCompleto)).thenReturn(autores);

		int expectedId = 1;
		int result = autorService.obtenerIdAutor(nombreCompleto);

		assertEquals(expectedId, result);
	}

	@DisplayName("Test para buscar por nombre")
	@Test
	public void testFindByName() {
	    String nombre = "Alberto";
	    String apellidos = "Arroyo";

	    // Simular un Optional vacío, ya que no se encontró ningún autor con los nombres y apellidos dados
	    when(autorRepository.findByNombreAndApellidos(nombre, apellidos)).thenReturn(Optional.empty());

	    // Ahora, puedes ejecutar el método que estás probando sin preocuparte por lanzar una IllegalStateException
	    Optional<AutorDTO> result = autorService.findByName(nombre, apellidos);

	    // Verificar que el Optional devuelto esté vacío
	    assertFalse(result.isPresent());
	}

}
