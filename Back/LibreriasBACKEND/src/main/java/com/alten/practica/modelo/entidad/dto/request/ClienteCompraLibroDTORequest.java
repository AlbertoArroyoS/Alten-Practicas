package com.alten.practica.modelo.entidad.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCompraLibroDTORequest {
	
	@NotNull(message = "El id no puede ser nulo")
	private int idCliente;
	
	@NotNull(message = "El id no puede ser nulo")
	private int idLibro;

	private Date fechaCompra;
	
	@DecimalMin(value = "0.01", message = "El precio debe ser al menos 0.01")
	private double precio;

}
