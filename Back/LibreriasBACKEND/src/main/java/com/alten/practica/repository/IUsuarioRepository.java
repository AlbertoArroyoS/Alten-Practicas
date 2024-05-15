package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alten.practica.modelo.entidad.Usuario;
/*
 * Repositorio de JPA para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD y consultas adicionales sobre la entidad Usuario.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByUsername(String username);
}
