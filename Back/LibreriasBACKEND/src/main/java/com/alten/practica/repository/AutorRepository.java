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
	
	//Busqueda de autores por nombre
	@Query(value = LibreriaConstant.SP_SEARCH_AUTOR, nativeQuery = true)
	public List<Autor> buscarAutor(String nombre);

}
