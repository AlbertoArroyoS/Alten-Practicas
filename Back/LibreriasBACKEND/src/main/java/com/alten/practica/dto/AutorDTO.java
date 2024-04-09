package com.alten.practica.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AutorDTO implements Serializable{
	
	private int id;
	private String nombre;

}
