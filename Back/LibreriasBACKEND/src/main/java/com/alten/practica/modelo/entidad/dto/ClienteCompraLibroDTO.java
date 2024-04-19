package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import java.sql.Date;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.Libro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCompraLibroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private Cliente cliente;

	private Libro libro;

	private Date fechaCompra;

	private double precio;

}
