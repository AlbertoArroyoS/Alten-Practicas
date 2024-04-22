package com.alten.practica.modelo.entidad.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class LibreriaLibroDTORequest {


	@NotNull(message = "El id no puede ser nulo")
	private int idLibreria;

	@NotNull(message = "El id no puede ser nulo")
	private int idLibro;

	private int cantidad;
	
	@DecimalMin(value = "0.01", message = "El precio debe ser al menos 0.01")
    private double precio;
	
	private int edicion;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date fechaPublicacion;


}
