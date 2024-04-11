package com.alten.practica.dto.request;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//Capturar los datos de la peticion que vienen en el body
//atributos que nos envia el cliente
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTORequest {
		// LOS ATRIBUTOS NO SEAN NULOS.
		@NotNull

		// LOS ATRIBUTOS NO SEAN VACIOS.
		@NotEmpty

		// LOS ATRIBUTOS NOS SEAN NULAS NI BACIOS.
		@NotBlank
		
		//VALIDA LA LONGITUD MINIMA Y MAXIMA DE UNA CADENA
		@Size(min = 5,max = 100)
		private String titulo;
		private String nombreAutor;
		private String apellidosAutor;
		private String genero;
		private int paginas;
		private String editorial;
		private String descripcion;
	    private double precio;

}
