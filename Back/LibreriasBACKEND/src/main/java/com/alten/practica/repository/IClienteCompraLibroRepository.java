package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.Libro;

@Repository
public interface IClienteCompraLibroRepository extends JpaRepository<ClienteCompraLibro, Integer>{
	
	
	public Optional<Libro> findLibroById(int id);
	public Optional<Cliente> findClienteById(int id);

}
