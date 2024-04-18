package com.alten.practica.dto.request;

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

	private double precio;

}
