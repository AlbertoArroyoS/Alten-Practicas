package com.alten.practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Libreria;

@Repository
public interface ILibreriaRepository extends JpaRepository<Libreria, Integer>{
	
	

}
