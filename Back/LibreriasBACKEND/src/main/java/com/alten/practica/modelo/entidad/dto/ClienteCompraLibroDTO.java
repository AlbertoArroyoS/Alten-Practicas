package com.alten.practica.modelo.entidad.dto;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private int idCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private int idLibro;
    private String tituloLibro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaCompra;
    private double precio;


}
