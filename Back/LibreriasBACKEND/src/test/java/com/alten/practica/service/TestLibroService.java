package com.alten.practica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibroMapper;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.impl.LibroServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase de test para el servicio de libros
 * 
 * @see LibroServiceImpl
 */
@SpringBootTest
public class TestLibroService {

    @Mock
    private ILibroRepository libroRepository;

    @Mock
    private IAutorRepository autorRepository;

    @Mock
    private ILibroMapper libroMapper;

    @Mock
    private LibreriaUtil libreriaUtil;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        resetMocks();
    }

    private void resetMocks() {
        reset(libroRepository, autorRepository, libroMapper, libreriaUtil);
    }

    @DisplayName("Test para guardar un nuevo libro")
    @Test
    public void testSave() {
        LibroDTORequest dtoRequest = new LibroDTORequest("Título", "NombreAutor", "ApellidosAutor", "Género", 200, "Editorial", "Descripción");

        when(libroRepository.findByTitulo("Título")).thenReturn(Optional.empty());
        when(autorRepository.findByNombreAndApellidos("NombreAutor", "ApellidosAutor")).thenReturn(Optional.empty());

        Libro libro = new Libro();
        libro.setId(1);
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRO)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libroService.save(dtoRequest);

        assertEquals(expectedHrefEntityDTO, result);
    }

    @DisplayName("Test para buscar un libro por su ID")
    @Test
    public void testFindById() {
        Libro libro = new Libro();
        libro.setId(1);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

        LibroDTO expectedDTO = new LibroDTO();
        expectedDTO.setId(1);
        when(libroMapper.toDTO(libro)).thenReturn(expectedDTO);

        LibroDTO result = libroService.findById(1);

        assertEquals(expectedDTO, result);
    }

    @DisplayName("Test para buscar todas los libros")
    @Test
    public void testFindAll() {
        List<Libro> lista = new ArrayList<>();
        Libro libro1 = new Libro();
        libro1.setId(1);
        lista.add(libro1);
        Libro libro2 = new Libro();
        libro2.setId(2);
        lista.add(libro2);

        LibroDTO expectedDTO1 = new LibroDTO();
        expectedDTO1.setId(1);
        LibroDTO expectedDTO2 = new LibroDTO();
        expectedDTO2.setId(2);
        when(libroMapper.toDTO(libro1)).thenReturn(expectedDTO1);
        when(libroMapper.toDTO(libro2)).thenReturn(expectedDTO2);

        when(libroRepository.findAll()).thenReturn(lista);

        List<LibroDTO> result = libroService.findAll();

        assertEquals(2, result.size());
        assertEquals(expectedDTO1, result.get(0));
        assertEquals(expectedDTO2, result.get(1));
    }

    @DisplayName("Test para actualizar un libro")
    @Test
    public void testUpdate() {
        LibroDTORequest dtoRequest = new LibroDTORequest("Título", "NombreAutor", "ApellidosAutor", "Género", 200, "Editorial", "Descripción");
        Libro libro = new Libro();
        libro.setId(1);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRO)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libroService.update(dtoRequest, 1);

        assertEquals(expectedHrefEntityDTO, result);
    }

    @DisplayName("Test para eliminar un libro por su ID")
    @Test
    public void testDelete() {
        Libro libro = new Libro();
        libro.setId(1);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRO)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libroService.delete(1);

        assertEquals(expectedHrefEntityDTO, result);
    }



    @DisplayName("Test para obtener ID del autor")
    @Test
    public void testObtenerIdAutor() {
        List<Autor> autores = new ArrayList<>();
        Autor autor = new Autor();
        autor.setId(1);
        autores.add(autor);
        when(autorRepository.buscarKeyWordSQL("NombreCompleto")).thenReturn(autores);

        int expectedId = 1;
        int result = libroService.obtenerIdAutor("NombreCompleto");

        assertEquals(expectedId, result);
    }
}
