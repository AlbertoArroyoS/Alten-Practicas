package com.alten.practica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Libreria;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad Libreria
 * 
 */
@Repository
public interface ILibreriaRepository extends JpaRepository<Libreria, Integer> {

	// PRIMERA FORMA- por nombre del metodo
	List<Libreria> findByCiudadContainingOrderByNombreLibreriaAsc(String ciudad);

	// findByDescriptionContainingOrderByIdAsc

	// SEGUNDA FORMA- por JPQL
	@Query("SELECT l FROM Libreria l WHERE l.ciudad LIKE CONCAT('%', :ciudad, '%')")
	public List<Libreria> findByCiudadJPQL(@Param("ciudad") String ciudad);

	@Query("SELECT l FROM Libreria l WHERE l.id = :id")
	public Libreria findByIdJPQL(@Param("id") int id);

	// Metodo para buscar por nombre
	@Query("SELECT l FROM Libreria l WHERE l.nombreLibreria = :nombreLibreria")
	Optional<Libreria> findByNombreLibreria(@Param("nombreLibreria") String nombre);

	// TERCERA FORMA - mediante instruccion SQL, ejemplo si existe en la base de
	// datos
	@Query(value = "select count(*)>0 from dbo.librerias l where l.id_libreria = ?;", nativeQuery = true)
	public boolean existsByIdSQL(int id);

}
