package com.alten.practica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.modelo.entidad.Autor;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad Autor
 * 
 */
@Repository
public interface IAutorRepository extends JpaRepository<Autor, Integer> {

	// LLAMADA A PROCEDIMIENTOS ALMACENADOS

	// Busqueda de autores por nombre, usando un procedimiento almacenado, la
	// funcion fn_buscar_autor de la base de datos
	//@Query(value = LibreriaConstant.SP_SEARCH_AUTOR, nativeQuery = true, countQuery = LibreriaConstant.SP_SEARCH_CONTAR_AUTORES)
	//public List<Autor> buscarKeyWordSQL(String nombre);
	
	@Query(value = "SELECT * FROM dbo_encriptada.autores ta WHERE LOWER(CONCAT(ta.nombre, ' ', ta.apellidos)) LIKE %:keyword%", nativeQuery = true)
    List<Autor> buscarKeyWordSQL(@Param("keyword") String keyword);

	@Query(value = LibreriaConstant.SP_NUEVO_AUTOR, nativeQuery = true)
	public Autor nuevoAutorSQL(String nombre, String apellidos);

	// Metodo para buscar por nombre
	@Query("SELECT a FROM Autor a WHERE a.nombre = :nombre AND a.apellidos = :apellidos")
	Optional<Autor> findByNombreAndApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

}
