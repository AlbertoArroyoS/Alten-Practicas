package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un libro.
 *
 * Esta clase contiene información sobre un libro, incluyendo su identificador
 * único, título, autor, género, número de páginas, editorial, descripción y las
 * listas de librerías y clientes que lo tienen asociado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBROS, schema = LibreriaConstant.ESQUEMA_NOMBRE_ENCRTIPTADA)
public class Libro {

	@Id
	@Column(name = "id_libro")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "titulo")
	private String titulo;

	@ManyToOne
	@JoinColumn(name = "id_autor", referencedColumnName = "id_autor")
	private Autor autor;

	@Column(name = "genero")
	private String genero;
	@Column(name = "paginas")
	private int paginas;
	@Column(name = "editorial")
	private String editorial;
	@Column(name = "descripcion")
	private String descripcion;

	@OneToMany(mappedBy = "libro")
	private List<LibreriaLibro> libreriaLibros;

	@OneToMany(mappedBy = "libro")
	private List<ClienteCompraLibro> listaClientes;

}
