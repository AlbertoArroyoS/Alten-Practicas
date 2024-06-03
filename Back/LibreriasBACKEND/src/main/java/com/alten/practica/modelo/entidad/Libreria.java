package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.service.encrypt.EncriptadorDesencriptadorAutomatico;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una librería.
 *
 * Esta clase contiene información sobre una librería, incluyendo su
 * identificador único, nombre de la librería, nombre del dueño, dirección,
 * ciudad, nivel de permiso, correo electrónico y la lista de libros asociados a
 * la librería.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBRERIAS, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class Libreria {

	@Id
	@Column(name = "id_libreria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "nombre_libreria")
	private String nombreLibreria;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "nombre_dueno")
	private String nombreDueno;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "direccion")
	private String direccion;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "ciudad")
	private String ciudad;

	@OneToMany(mappedBy = "libreria")
	private List<LibreriaLibro> libreriaLibros;

	@OneToOne(mappedBy = "libreria")
	private Usuario usuario;

	@OneToMany(mappedBy = "libreria")
	private List<ClienteCompraLibro> listaComprasLibrerias;
}
