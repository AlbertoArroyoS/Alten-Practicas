package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.LibreriaLibro;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad LibreriaLibro
 * 
 */
@Repository
public interface ILibreriaLibroRepository extends JpaRepository<LibreriaLibro, Integer> {
	
    Optional<LibreriaLibro> findByLibroIdAndLibreriaId(int libroId, int libreriaId);

	
		
}
