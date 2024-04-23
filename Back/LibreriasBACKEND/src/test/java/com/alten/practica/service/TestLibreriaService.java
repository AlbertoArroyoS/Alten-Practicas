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
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaMapper;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.service.impl.LibreriaServiceImpl;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz LibreriaService
 * 
 * @see com.alten.practica.service.ILibreriaService
 * 
 * 
 */
@SpringBootTest
public class TestLibreriaService {

    @Mock
    private ILibreriaRepository libreriaRepository;

    @Mock
    private ILibreriaMapper libreriaMapper;

    @Mock
    private LibreriaUtil libreriaUtil;

    @InjectMocks
    private LibreriaServiceImpl libreriaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        resetMocks();
    }

    private void resetMocks() {
        reset(libreriaRepository, libreriaMapper, libreriaUtil);
    }

    @DisplayName("Test para guardar una nueva librería")
    @Test
    public void testSave() {
        LibreriaDTORequest dtoRequest = new LibreriaDTORequest("NombreLibreria", "NombreDueño", "Dirección", "Ciudad", "correo@example.com");
        Libreria libreria = new Libreria();
        libreria.setId(1);
        when(libreriaRepository.findByNombreLibreria("NombreLibreria")).thenReturn(Optional.empty());
        when(libreriaMapper.toBean(dtoRequest)).thenReturn(libreria);
        when(libreriaRepository.save(any(Libreria.class))).thenReturn(libreria);

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIA)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libreriaService.save(dtoRequest);

        assertEquals(expectedHrefEntityDTO, result);
    }

    @DisplayName("Test para buscar una librería por su ID")
    @Test
    public void testFindById() {
        Libreria libreria = new Libreria();
        libreria.setId(1);
        when(libreriaRepository.findById(1)).thenReturn(Optional.of(libreria));

        LibreriaDTO expectedDTO = new LibreriaDTO();
        expectedDTO.setId(1);
        when(libreriaMapper.toDTO(libreria)).thenReturn(expectedDTO);

        LibreriaDTO result = libreriaService.findById(1);

        assertEquals(expectedDTO, result);
    }

    @DisplayName("Test para buscar todas las librerías")
    @Test
    public void testFindAll() {
        List<Libreria> lista = new ArrayList<>();
        Libreria libreria1 = new Libreria();
        libreria1.setId(1);
        lista.add(libreria1);
        Libreria libreria2 = new Libreria();
        libreria2.setId(2);
        lista.add(libreria2);

        LibreriaDTO expectedDTO1 = new LibreriaDTO();
        expectedDTO1.setId(1);
        LibreriaDTO expectedDTO2 = new LibreriaDTO();
        expectedDTO2.setId(2);
        when(libreriaMapper.toDTO(libreria1)).thenReturn(expectedDTO1);
        when(libreriaMapper.toDTO(libreria2)).thenReturn(expectedDTO2);

        when(libreriaRepository.findAll()).thenReturn(lista);

        List<LibreriaDTO> result = libreriaService.findAll();

        assertEquals(2, result.size());
        assertEquals(expectedDTO1, result.get(0));
        assertEquals(expectedDTO2, result.get(1));
    }

    @DisplayName("Test para actualizar una librería")
    @Test
    public void testUpdate() {
        LibreriaDTORequest dtoRequest = new LibreriaDTORequest("NombreLibreria", "NombreDueño", "Dirección", "Ciudad", "correo@example.com");
        Libreria libreria = new Libreria();
        libreria.setId(1);
        when(libreriaRepository.findById(1)).thenReturn(Optional.of(libreria));
        when(libreriaRepository.findByNombreLibreria("NombreLibreria")).thenReturn(Optional.empty());
        when(libreriaRepository.save(any(Libreria.class))).thenReturn(libreria);

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIA)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libreriaService.update(dtoRequest, 1);

        assertEquals(expectedHrefEntityDTO, result);
    }

    @DisplayName("Test para eliminar una librería por su ID")
    @Test
    public void testDelete() {
        Libreria libreria = new Libreria();
        libreria.setId(1);
        when(libreriaRepository.findById(1)).thenReturn(Optional.of(libreria));

        HrefEntityDTO expectedHrefEntityDTO = new HrefEntityDTO();
        when(libreriaUtil.createHrefFromResource(1, LibreriaResource.LIBRERIA)).thenReturn(expectedHrefEntityDTO);

        HrefEntityDTO result = libreriaService.delete(1);

        assertEquals(expectedHrefEntityDTO, result);
    }
}
