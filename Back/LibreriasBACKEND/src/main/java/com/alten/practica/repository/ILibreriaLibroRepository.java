package com.alten.practica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.LibreriaLibro;

/**
 * Interfaz que extiende de JpaRepository, para realizar operaciones con la base
 * de datos para la entidad LibreriaLibro
 * 
 */
@Repository
public interface ILibreriaLibroRepository extends JpaRepository<LibreriaLibro, Integer> {
	
    Optional<LibreriaLibro> findByLibroIdAndLibreriaId(int libroId, int libreriaId);

    @Query(value = "SELECT ll.* FROM libreria_libro ll JOIN libros l ON ll.id_libro = l.id_libro WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))", 
            countQuery = "SELECT count(ll.id_libreria_libro) FROM libreria_libro ll JOIN libros l ON ll.id_libro = l.id_libro WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))", 
            nativeQuery = true)
     Page<LibreriaLibro> findByTituloContainingNative(@Param("titulo") String titulo, Pageable pageable);
    
    
    @Query("SELECT l FROM LibreriaLibro l WHERE l.libreria.id = :libraryId")
    Page<LibreriaLibro> findByLibraryId(@Param("libraryId") int libraryId, Pageable pageable);
   
    
}
