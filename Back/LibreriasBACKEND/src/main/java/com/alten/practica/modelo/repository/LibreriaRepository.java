package com.alten.practica.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Libreria;

@Repository
public interface LibreriaRepository extends JpaRepository<Libreria, Integer> {

}
