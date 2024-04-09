package com.alten.practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alten.practica.modelo.entidad.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{
	

}
