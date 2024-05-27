package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.Libro;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad ClienteCompraLibro
 * 
 */
@Repository
public interface IClienteCompraLibroRepository extends JpaRepository<ClienteCompraLibro, Integer> {

	public Optional<Libro> findLibroById(int id);

	public Optional<Cliente> findClienteById(int id);
	
	
	// Método para buscar libros comprados por un cliente con paginación
	@Query("SELECT a FROM ClienteCompraLibro a WHERE a.cliente.id = :idCliente")
    Page<ClienteCompraLibro> findByIdCliente(@Param("idCliente") int idCliente, Pageable pageable);
	
	@Query("SELECT a FROM ClienteCompraLibro a WHERE a.libreria.id = :idLibreria")
    Page<ClienteCompraLibro> findByIdLibreria(@Param("idLibreria") int idCliente, Pageable pageable);

}
