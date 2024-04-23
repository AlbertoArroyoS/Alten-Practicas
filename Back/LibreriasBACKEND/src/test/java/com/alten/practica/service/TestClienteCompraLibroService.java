package com.alten.practica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.IClienteCompraLibroMapper;
import com.alten.practica.repository.IClienteCompraLibroRepository;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.impl.ClienteCompraLibroServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

public class TestClienteCompraLibroService {
	@Mock
	private IClienteCompraLibroRepository clienteCompraLibroRepository;

	@Mock
	private IClienteCompraLibroMapper clienteCompraLibrosMapper;

	@Mock
	private LibreriaUtil libreriaUtil;

	@Mock
	private IClienteRepository clienteRepository;

	@Mock
	private ILibroRepository libroRepository;

	@InjectMocks
	private ClienteCompraLibroServiceImpl clienteCompraLibroService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		resetMocks();
	}

	private void resetMocks() {
		reset(clienteCompraLibroRepository, clienteCompraLibrosMapper, libreriaUtil, clienteRepository,
				libroRepository);
	}

	@DisplayName("Test para guardar una compra de libro")
	@Test
	public void testSave() {
		ClienteCompraLibroDTORequest dtoRequest = new ClienteCompraLibroDTORequest();
        dtoRequest.setIdCliente(1);
        dtoRequest.setIdLibro(1);       
        Calendar calendar = Calendar.getInstance();
        java.util.Date utilDate = calendar.getTime();
        java.sql.Date fechaDeHoy = new java.sql.Date(utilDate.getTime());
        dtoRequest.setFechaCompra(fechaDeHoy);
        dtoRequest.setPrecio(25.50);
		Cliente cliente = new Cliente();
        cliente.setId(1);
        Libro libro = new Libro();
        libro.setId(1);

		when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
		when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

		ClienteCompraLibro clienteCompraLibro = new ClienteCompraLibro();
		clienteCompraLibro.setId(1);
		when(clienteCompraLibroRepository.save(any(ClienteCompraLibro.class))).thenReturn(clienteCompraLibro);

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTECOMPRALIBRO))
				.thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = clienteCompraLibroService.save(dtoRequest);

		assertEquals(expectedHrefEntityDTO, result);
	}

	@DisplayName("Test para buscar una compra de libro por su ID")
	@Test
	public void testFindById() {
		ClienteCompraLibro clienteCompraLibro = new ClienteCompraLibro();
		clienteCompraLibro.setId(1);

		when(clienteCompraLibroRepository.findById(1)).thenReturn(Optional.of(clienteCompraLibro));

		ClienteCompraLibroDTO expectedDTO = new ClienteCompraLibroDTO();
		expectedDTO.setId(1);
		when(clienteCompraLibrosMapper.toDTO(clienteCompraLibro)).thenReturn(expectedDTO);

		ClienteCompraLibroDTO result = clienteCompraLibroService.findById(1);

		assertEquals(expectedDTO, result);
	}

	@DisplayName("Test para listar todas las compras de libros")
	@Test
	public void testFindAll() {
		List<ClienteCompraLibro> lista = new ArrayList<>();
		ClienteCompraLibro clienteCompraLibro1 = new ClienteCompraLibro();
		clienteCompraLibro1.setId(1);
		ClienteCompraLibro clienteCompraLibro2 = new ClienteCompraLibro();
		clienteCompraLibro2.setId(2);
		lista.add(clienteCompraLibro1);
		lista.add(clienteCompraLibro2);

		when(clienteCompraLibroRepository.findAll()).thenReturn(lista);

		List<ClienteCompraLibroDTO> expectedDTOList = lista.stream().map(clienteCompraLibrosMapper::toDTO)
				.collect(Collectors.toList());

		List<ClienteCompraLibroDTO> result = clienteCompraLibroService.findAll();

		assertEquals(expectedDTOList, result);
	}

	@DisplayName("Test para actualizar una compra de libro")
	@Test
	public void testUpdate() {
	    // Crear el DTORequest con los datos necesarios
	    ClienteCompraLibroDTORequest dtoRequest = new ClienteCompraLibroDTORequest();
	    dtoRequest.setIdCliente(1);
	    dtoRequest.setIdLibro(1);
	    Calendar calendar = Calendar.getInstance();
	    java.util.Date utilDate = calendar.getTime();
	    java.sql.Date fechaDeHoy = new java.sql.Date(utilDate.getTime());
	    dtoRequest.setFechaCompra(fechaDeHoy);
	    dtoRequest.setPrecio(25.50);
	    
	    // Simular los objetos Cliente y Libro
	    Cliente cliente = new Cliente();
	    cliente.setId(1);
	    cliente.setNombre("Juan");
	    cliente.setApellidos("Pérez");
	    Libro libro = new Libro();
	    libro.setId(1);
	    libro.setTitulo("El Quijote");

	 // Configurar el comportamiento simulado para el método findById() del repositorio clienteRepository
	    when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
	    
	    // Configurar el comportamiento simulado para el método findById() del repositorio libroRepository
	    when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

	    // Configurar el comportamiento simulado para el método findById() del repositorio clienteCompraLibroRepository
	    ClienteCompraLibro clienteCompraLibro = new ClienteCompraLibro();
	    clienteCompraLibro.setId(1);
	    when(clienteCompraLibroRepository.findById(1)).thenReturn(Optional.of(clienteCompraLibro));

	    // Configurar el comportamiento simulado para el método save() del repositorio clienteCompraLibroRepository
	    when(clienteCompraLibroRepository.save(any(ClienteCompraLibro.class))).thenReturn(clienteCompraLibro);

	    // Configurar el comportamiento simulado para el método createHrefFromResource() de libreriaUtil
	    HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
	    when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTECOMPRALIBRO)).thenReturn(expectedHrefEntityDTO);

	    // Ejecutar el método que se está probando
	    HrefEntityDTO result = clienteCompraLibroService.update(dtoRequest, 1);

	    // Verificar que el resultado sea el esperado
	    assertEquals(expectedHrefEntityDTO, result);
	}



	@DisplayName("Test para eliminar una compra de libro")
	@Test
	public void testDelete() {
		ClienteCompraLibro clienteCompraLibro = new ClienteCompraLibro();
		clienteCompraLibro.setId(1);

		when(clienteCompraLibroRepository.findById(1)).thenReturn(Optional.of(clienteCompraLibro));

		HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
		when(libreriaUtil.createHrefFromResource(1, LibreriaResource.CLIENTECOMPRALIBRO))
				.thenReturn(expectedHrefEntityDTO);

		HrefEntityDTO result = clienteCompraLibroService.delete(1);

		assertEquals(expectedHrefEntityDTO, result);
	}
}
