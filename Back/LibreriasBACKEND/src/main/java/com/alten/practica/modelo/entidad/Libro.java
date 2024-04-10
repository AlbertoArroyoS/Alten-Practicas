package com.alten.practica.modelo.entidad;

import java.util.List;


import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBROS, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class Libro {
	
	@Id
	@Column(name = "id_libro")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "titulo")
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name = "id_autor" , referencedColumnName = "id_autor")	
	private Autor autor;
	
	@Column(name = "genero")
	private String genero;
	@Column(name = "paginas")
	private int paginas;
	@Column(name = "editorial")
	private String editorial;
	@Column(name = "descripcion")
	private String descripcion;
    @Column(name = "precio")
    private double precio;
    
    @OneToMany(mappedBy = "libro")
    private List<LibreriaLibro> libreriaLibros;

	public Libro(int id, String titulo, Autor autor, String genero, int paginas, String editorial, String descripcion,
			double precio, List<LibreriaLibro> libreriaLibros) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.paginas = paginas;
		this.editorial = editorial;
		this.descripcion = descripcion;
		this.precio = precio;
		this.libreriaLibros = libreriaLibros;
	}

	public Libro() {
		super();
	}


    
}
