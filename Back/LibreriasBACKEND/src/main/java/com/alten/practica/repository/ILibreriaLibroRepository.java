package com.alten.practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.LibreriaLibro;

@Repository
public interface ILibreriaLibroRepository extends JpaRepository<LibreriaLibro, Integer>{

}
