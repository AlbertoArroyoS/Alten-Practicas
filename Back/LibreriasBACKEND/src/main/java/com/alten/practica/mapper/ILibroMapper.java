package com.alten.practica.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.dto.LibroDTO;
import com.alten.practica.modelo.entidad.Libro;

@Mapper (builder = @Builder(disableBuilder = true))
public interface ILibroMapper {
	
	// proceso de mappeo de la entidad a DTO, ya que los atributos del DTO no
	// coinciden con los de la entidad
	// source: atributo de la entidad, target: atributo del DTO, expression:
	// expresion para realizar el mapeo
	
	public LibroDTO toDTO(Libro libro);

}
