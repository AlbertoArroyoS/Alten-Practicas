package com.alten.practica.dto.request;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

//Capturar los datos de la peticion que vienen en el body

@Builder
@Data
public class AutorDTORequest implements Serializable	{
	
	//atributos que nos envia el cliente
	private String nombre;
	private String apellidos;
}
