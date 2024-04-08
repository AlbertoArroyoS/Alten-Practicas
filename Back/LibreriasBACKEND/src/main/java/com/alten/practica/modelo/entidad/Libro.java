package com.alten.practica.modelo.entidad;

import java.util.List;

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
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "libros", schema = "dbo")
public class Libro {
	
	@Id
	@Column(name = "id_libro")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "titulo")
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	
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


	public Libro() {
		super();
	}

}
