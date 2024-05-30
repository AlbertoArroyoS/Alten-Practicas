package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un autor de libros.
 *
 * Esta clase contiene información sobre un autor, incluyendo su identificador
 * único, nombre, apellidos y la lista de libros asociados.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_AUTORES, schema = LibreriaConstant.ESQUEMA_NOMBRE_ENCRTIPTADA)
public class Autor {

	@Id
	@Column(name = "id_autor")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;

	@OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST)
	private List<Libro> libros;

}
