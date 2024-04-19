package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibroDTO;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad Libro
 * 
 */
@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer> {

	// Busqueda de autores por nombre, usando un procedimiento almacenado, la
	// funcion fn_buscar_autor de la base de datos
	@Query(value = LibreriaConstant.SP_SEARCH_LIBRO, nativeQuery = true, countQuery = LibreriaConstant.SP_SEARCH_CONTAR_LIBROS)
	public Page<Libro> buscarKeyWordSQL(String nombre, Pageable pePageable);

	/*
	 * @Query(value = LibreriaConstant.SP_NUEVO_LIBRO, nativeQuery = true) public
	 * LibroDTO nuevoLibroSQL(String titulo, String nombreAutor, String
	 * apellidoAutor, String genero, int paginas,String editorial, String
	 * descripcion, double precio);
	 */

	@Query(value = "SELECT * FROM dbo.fn_guardar_libro(:titulo, :nombreAutor, :apellidosAutor, :genero, :paginas, :editorial, :descripcion, :precio)", nativeQuery = true)
	public LibroDTO guardarLibroSQL(@Param("titulo") String titulo, @Param("nombreAutor") String nombreAutor,
			@Param("apellidosAutor") String apellidosAutor, @Param("genero") String genero,
			@Param("paginas") int paginas, @Param("editorial") String editorial,
			@Param("descripcion") String descripcion, @Param("precio") double precio);

	@Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
	Optional<Libro> findByTitulo(@Param("titulo") String titulo);
}
