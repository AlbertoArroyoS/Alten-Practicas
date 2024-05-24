package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alten.practica.modelo.entidad.Usuario;
/*
 * Repositorio de JPA para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD y consultas adicionales sobre la entidad Usuario.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.role = 'ADMIN'")
	Page<Usuario> findAdmins(Pageable pageable);


	
	@Query("SELECT u FROM Usuario u WHERE u.role = 'USER'")
	Page<Usuario> findUsers(Pageable pageable);

	
}
