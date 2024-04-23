package com.alten.practica.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;
import com.alten.practica.service.IClienteCompraLibroService;

/**
 * Clase de prueba para el controlador de ClienteCompraLibro.
 * @see ClienteCompraLibroControlador
 */
@SpringBootTest
public class TestClienteCompraLibroController {
	
	@Autowired
    private ClienteCompraLibroControlador clienteCompraLibroControlador;

    @MockBean
    private IClienteCompraLibroService clienteCompraLibroService;

    @Test
    void testFindAll() {
        // Preparar los datos de prueba
        ClienteCompraLibroDTO clienteCompraLibroDTO = new ClienteCompraLibroDTO();
        clienteCompraLibroDTO.setId(1);
        clienteCompraLibroDTO.setTituloLibro("Libro de prueba");

        List<ClienteCompraLibroDTO> clienteCompraLibroDTOList = Collections.singletonList(clienteCompraLibroDTO);

        // Simular el comportamiento del servicio
        when(clienteCompraLibroService.findAll()).thenReturn(clienteCompraLibroDTOList);

        // Ejecutar el método del controlador
        ResponseEntity<List<ClienteCompraLibroDTO>> responseEntity = clienteCompraLibroControlador.findAll();

        // Verificar el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clienteCompraLibroDTOList, responseEntity.getBody());
    }

    @Test
    void testFindById() {
        // Preparar los datos de prueba
        int id = 1;
        ClienteCompraLibroDTO clienteCompraLibroDTO = new ClienteCompraLibroDTO();
        clienteCompraLibroDTO.setId(id);
        clienteCompraLibroDTO.setTituloLibro("Libro de prueba");

        // Simular el comportamiento del servicio
        when(clienteCompraLibroService.findById(id)).thenReturn(clienteCompraLibroDTO);

        // Ejecutar el método del controlador
        ResponseEntity<ClienteCompraLibroDTO> responseEntity = clienteCompraLibroControlador.findById(id);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clienteCompraLibroDTO, responseEntity.getBody());
    }

    @Test
    void testSave() {
        // Preparar los datos de prueba
        ClienteCompraLibroDTORequest clienteCompraLibroDTORequest = new ClienteCompraLibroDTORequest();
        clienteCompraLibroDTORequest.setIdCliente(1);

        HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
        hrefEntityDTO.setId(1);

        // Simular el comportamiento del servicio
        when(clienteCompraLibroService.save(clienteCompraLibroDTORequest)).thenReturn(hrefEntityDTO);

        // Ejecutar el método del controlador
        ResponseEntity<HrefEntityDTO> responseEntity = clienteCompraLibroControlador.save(clienteCompraLibroDTORequest);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(hrefEntityDTO, responseEntity.getBody());
    }

    @Test
    void testUpdate() {
        // Preparar los datos de prueba
        int id = 1;
        ClienteCompraLibroDTORequest clienteCompraLibroDTORequest = new ClienteCompraLibroDTORequest();
        clienteCompraLibroDTORequest.setIdCliente(1);

        HrefEntityDTO hrefEntityDTO = new HrefEntityDTO();
        hrefEntityDTO.setId(id);

        // Simular el comportamiento del servicio
        when(clienteCompraLibroService.update(clienteCompraLibroDTORequest, id)).thenReturn(hrefEntityDTO);

        // Ejecutar el método del controlador
        ResponseEntity<HrefEntityDTO> responseEntity = clienteCompraLibroControlador.update(clienteCompraLibroDTORequest, id);

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
        when(clienteCompraLibroService.delete(id)).thenReturn(hrefEntityDTO);

        // Ejecutar el método del controlador
        ResponseEntity<HrefEntityDTO> responseEntity = clienteCompraLibroControlador.delete(id);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(hrefEntityDTO, responseEntity.getBody());
    }

}
