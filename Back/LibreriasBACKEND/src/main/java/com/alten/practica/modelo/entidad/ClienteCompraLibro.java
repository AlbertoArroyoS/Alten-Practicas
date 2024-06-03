package com.alten.practica.modelo.entidad;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.service.encrypt.EncriptadorDesencriptadorAutomatico;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una compra de libro realizada por un cliente. Producto
 * de la relación entre la tabla cliente y la tabla libro.
 *
 * Esta clase contiene información sobre una compra de libro, incluyendo su
 * identificador único, el cliente que realizó la compra, el libro comprado, la
 * fecha de compra y el precio.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_CLIENTE_COMPRA_LIBRO, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class ClienteCompraLibro {

	@Id
	@Column(name = "id_compra")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_libreria", referencedColumnName = "id_libreria")
	private Libreria libreria;

	@ManyToOne
	@JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
	private Libro libro;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fechaCompra;

	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "precio")
	private double precio;

}
