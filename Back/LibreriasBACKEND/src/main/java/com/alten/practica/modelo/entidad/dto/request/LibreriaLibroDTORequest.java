package com.alten.practica.modelo.entidad.dto.request;

import java.util.Date;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "El id no puede estar vacío")
	private int idLibreria;

	@NotNull(message = "El id no puede ser nulo")
	@NotBlank(message = "El id no puede estar vacío")
	private int idLibro;

	private int cantidad;
	
	@DecimalMin(value = "0.01", message = "El precio debe ser al menos 0.01")
    private double precio;
	
	private int edicion;
	private Date fechaPublicacion;


}
