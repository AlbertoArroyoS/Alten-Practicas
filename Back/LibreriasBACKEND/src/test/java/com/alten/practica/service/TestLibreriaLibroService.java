package com.alten.practica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaLibroMapper;
import com.alten.practica.repository.ILibreriaLibroRepository;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.impl.LibreriaLibroServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz LibreriaLibroService
 * 
 * @see com.alten.practica.service.ILibreriaLibroService
 * 
 * 
 */
@SpringBootTest
public class TestLibreriaLibroService {

    @Mock
    ILibreriaLibroRepository libreriaLibroRepository;
    
    @Mock
    ILibreriaRepository libreriaRepository;
    
    @Mock
    ILibroRepository libroRepository;
    
    @Mock
    ILibreriaLibroMapper libreriaLibroMapper;
    
    @Mock
    LibreriaUtil libreriaUtil;
    
    @InjectMocks
    LibreriaLibroServiceImpl libreriaLibroService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        resetMocks();
    }

    private void resetMocks() {
        reset(libreriaLibroRepository, libreriaRepository, libroRepository, libreriaLibroMapper, libreriaUtil);
    }

    @Test
    @DisplayName("Test para guardar una nueva relación entre librería y libro")
    public void testSave() {
        // Datos de la relación a guardar
    	LibreriaLibroDTORequest dtoRequest = new LibreriaLibroDTORequest();
    	dtoRequest.setIdLibreria(1);
    	dtoRequest.setIdLibro(1);
    	dtoRequest.setCantidad(10);
    	dtoRequest.setPrecio(50.0);
    	dtoRequest.setEdicion(1);
    	Calendar calendar = Calendar.getInstance();
		java.util.Date utilDate = calendar.getTime();
		java.sql.Date fechaDeHoy = new java.sql.Date(utilDate.getTime());
    	dtoRequest.setFechaPublicacion(fechaDeHoy);

        // Simular que no existe ninguna relación con los mismos IDs de librería y libro
        when(libreriaRepository.findById(1)).thenReturn(Optional.of(new Libreria()));
        when(libroRepository.findById(1)).thenReturn(Optional.of(new Libro()));

        // Simular el guardado de la relación y su ID asignado
        LibreriaLibro libreriaLibro = new LibreriaLibro();
        libreriaLibro.setId(1);
        when(libreriaLibroRepository.save(any(LibreriaLibro.class))).thenReturn(libreriaLibro);

        // Simular la creación del HrefEntityDTO con el ID de la relación guardada
        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIALIBRO)).thenReturn(expectedHrefEntityDTO);

        // Ejecutar el método de guardar relación y obtener el resultado
        HrefEntityDTO result = libreriaLibroService.save(dtoRequest);

        // Verificar que el resultado coincida con el HrefEntityDTO esperado
        assertEquals(expectedHrefEntityDTO, result);
    }

    @Test
    @DisplayName("Test para encontrar una relación entre librería y libro por su ID")
    public void testFindById() {
        // Simular la relación a encontrar
        LibreriaLibro libreriaLibro = new LibreriaLibro();
        libreriaLibro.setId(1);
        when(libreriaLibroRepository.findById(1)).thenReturn(Optional.of(libreriaLibro));

        // Simular el mapeo de la relación a DTO
        LibreriaLibroDTO expectedDTO = new LibreriaLibroDTO();
        when(libreriaLibroMapper.toDTO(libreriaLibro)).thenReturn(expectedDTO);

        // Ejecutar el método de encontrar relación por ID y obtener el resultado
        LibreriaLibroDTO result = libreriaLibroService.findById(1);

        // Verificar que el resultado coincida con el DTO esperado
        assertEquals(expectedDTO, result);
    }

    @Test
    @DisplayName("Test para encontrar todas las relaciones entre librerías y libros")
    public void testFindAll() {
        // Simular la lista de relaciones a encontrar
        List<LibreriaLibro> lista = new ArrayList<>();
        LibreriaLibro libreriaLibro1 = new LibreriaLibro();
        LibreriaLibro libreriaLibro2 = new LibreriaLibro();
        lista.add(libreriaLibro1);
        lista.add(libreriaLibro2);
        when(libreriaLibroRepository.findAll()).thenReturn(lista);

        // Simular el mapeo de las relaciones a DTO
        LibreriaLibroDTO dto1 = new LibreriaLibroDTO();
        LibreriaLibroDTO dto2 = new LibreriaLibroDTO();
        when(libreriaLibroMapper.toDTO(libreriaLibro1)).thenReturn(dto1);
        when(libreriaLibroMapper.toDTO(libreriaLibro2)).thenReturn(dto2);

        // Ejecutar el método de encontrar todas las relaciones y obtener el resultado
        List<LibreriaLibroDTO> result = libreriaLibroService.findAll();

        // Verificar que la lista resultante tenga el mismo tamaño que la lista original
        assertEquals(lista.size(), result.size());
        // Verificar que los DTOs en la lista resultante coincidan con los esperados
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    @DisplayName("Test para actualizar una relación entre librería y libro")
    public void testUpdate() {
        // Datos de la relación a actualizar
    	LibreriaLibroDTORequest dtoRequest = new LibreriaLibroDTORequest();
    	dtoRequest.setIdLibreria(1);
    	dtoRequest.setIdLibro(1);
    	dtoRequest.setCantidad(10);
    	dtoRequest.setPrecio(50.0);
    	dtoRequest.setEdicion(1);
    	Calendar calendar = Calendar.getInstance();
		java.util.Date utilDate = calendar.getTime();
		java.sql.Date fechaDeHoy = new java.sql.Date(utilDate.getTime());
    	dtoRequest.setFechaPublicacion(fechaDeHoy);
    	
        // Simular la relación existente y su actualización
        LibreriaLibro libreriaLibro = new LibreriaLibro();
        libreriaLibro.setId(1);
        when(libreriaLibroRepository.findById(1)).thenReturn(Optional.of(libreriaLibro));

        // Simular que no existe ninguna relación con los mismos IDs de librería y libro
        when(libreriaRepository.findById(1)).thenReturn(Optional.of(new Libreria()));
        when(libroRepository.findById(1)).thenReturn(Optional.of(new Libro()));

        // Simular el guardado de la relación actualizada y su ID asignado
        when(libreriaLibroRepository.save(any(LibreriaLibro.class))).thenReturn(libreriaLibro);

        // Simular la creación del HrefEntityDTO con el ID de la relación actualizada
        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIALIBRO)).thenReturn(expectedHrefEntityDTO);

        // Ejecutar el método de actualizar relación y obtener el resultado
        HrefEntityDTO result = libreriaLibroService.update(dtoRequest, 1);

        // Verificar que el resultado coincida con el HrefEntityDTO esperado
        assertEquals(expectedHrefEntityDTO, result);
    }

    @Test
    @DisplayName("Test para eliminar una relación entre librería y libro")
    public void testDelete() {
        // Simular la relación a eliminar
        LibreriaLibro libreriaLibro = new LibreriaLibro();
        libreriaLibro.setId(1);
        when(libreriaLibroRepository.findById(1)).thenReturn(Optional.of(libreriaLibro));

        // Simular la creación del HrefEntityDTO con el ID de la relación eliminada
        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIALIBRO)).thenReturn(expectedHrefEntityDTO);

        // Ejecutar el método de eliminar relación y obtener el resultado
        HrefEntityDTO result = libreriaLibroService.delete(1);

        // Verificar que el ID del HrefEntityDTO coincida con el ID de la relación eliminada
        assertEquals(expectedHrefEntityDTO.getId(), result.getId());
    }

}

