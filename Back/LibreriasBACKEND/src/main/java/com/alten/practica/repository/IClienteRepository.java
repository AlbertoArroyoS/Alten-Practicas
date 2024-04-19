package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//Metodo para buscar por nombre
	@Query("SELECT a FROM Cliente a WHERE a.nombre = :nombre AND a.apellidos = :apellidos")
	Optional<Cliente> findByNombreAndApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

}
