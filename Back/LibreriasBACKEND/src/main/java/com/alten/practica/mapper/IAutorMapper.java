package com.alten.practica.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.modelo.entidad.Autor;

@Mapper (builder = @Builder(disableBuilder = true))
public interface IAutorMapper {
	
	//proceso de mappeo de la entidad a DTO, ya que los atributos del DTO no coinciden con los de la entidad
	//source: atributo de la entidad, target: atributo del DTO, expression: expresion para realizar el mapeo
	@Mapping(target = "nombre", expression = "java(autor.getNombre() + \" \" + autor.getApellidos())")
	public AutorDTO toDTO(Autor autor);

}
