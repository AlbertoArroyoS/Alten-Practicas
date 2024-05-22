package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un DTO (Data Transfer Object) para la entidad
 * ClienteCompraLibro. Información que se envia al cliente desde el servidor.
 *
 * Esta clase contiene información simplificada de una compra de libro realizada
 * por un cliente, incluyendo su identificador único, el identificador del
 * cliente, nombre y apellidos del cliente, identificador del libro, título del
 * libro, fecha de compra y precio.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCompraLibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int idCliente;
	private int idLibreria;
	private String nombreCliente;
	private String apellidosCliente;
	private int idLibro;
	private String tituloLibro;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date fechaCompra;
	private double precio;

}
