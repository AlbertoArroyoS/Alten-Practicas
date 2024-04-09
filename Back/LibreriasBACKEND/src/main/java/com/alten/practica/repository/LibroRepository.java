package com.alten.practica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{
	
	//Busqueda de autores por nombre, usando un procedimiento almacenado, la funcion fn_buscar_autor de la base de datos
		@Query(value = LibreriaConstant.SP_SEARCH_LIBRO, nativeQuery = true , countQuery = LibreriaConstant.SP_SEARCH_CONTAR_LIBROS)
		public List<Libro> buscarKeyWordSQL(String nombre);
}
