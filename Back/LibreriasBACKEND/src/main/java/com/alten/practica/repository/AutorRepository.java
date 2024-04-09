package com.alten.practica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.modelo.entidad.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer>{
	
	//LLAMADA A PROCEDIMIENTOS ALMACENADOS
	
	//Busqueda de autores por nombre, usando un procedimiento almacenado, la funcion fn_buscar_autor de la base de datos
	@Query(value = LibreriaConstant.SP_SEARCH_AUTOR, nativeQuery = true , countQuery = LibreriaConstant.SP_SEARCH_CONTAR_AUTORES)
	public List<Autor> buscarKeyWordSQL(String nombre);
	
	@Query(value = LibreriaConstant.SP_NUEVO_AUTOR, nativeQuery = true)
	public Autor nuevoAutorSQL(String nombre, String apellidos);

}
