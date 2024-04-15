package com.alten.practica;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.repository.ILibreriaRepository;

@SpringBootTest
class LibreriasBackendApplicationTests {
	
	
	@Autowired
	private ILibreriaRepository libreriaRepository;
	
	
	

	@Test
	void contextLoads() {
	
	
		
		
		List<Libreria> listaLibrerias = this.libreriaRepository.findByCiudadContainingOrderByNombreLibreriaAsc("Ciu");
		System.out.println("El tamaño de la lista " + listaLibrerias.size());
		
		List<Libreria> listaLibrerias2 = this.libreriaRepository.findByCiudadJPQL("Ciu");
		System.out.println("El tamaño de la lista " + listaLibrerias2.size());
		
		
		Libreria libreria = this.libreriaRepository.findByIdJPQL(1);
		System.out.println("La libreria es:  " + libreria.getNombreLibreria());
		
		boolean libreriaExiste = this.libreriaRepository.existsByIdSQL(9);
		System.out.println("La libreria existe:  " + libreriaExiste );
		

		
		
	}

}
