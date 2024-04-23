package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;

/*
 * Interfaz que se encarga de realizar el mapeo de la entidad a DTO y viceversa
 * 
 * @Mapper: indica que es una interfaz de mapeo
 * @Builder: indica que se va a utilizar el patron builder para crear la entidad
 * @Mapping: indica el mapeo de los atributos de la entidad a los atributos del DTO
 */
@Mapper (builder = @Builder(disableBuilder = true))
public interface IAutorMapper {
	
	//proceso de mappeo de la entidad a DTO, ya que los atributos del DTO no coinciden con los de la entidad
	//source: atributo de la entidad, target: atributo del DTO, expression: expresion para realizar el mapeo
	@Mapping(target = "nombre", expression = "java(autor.getNombre() + \" \" + autor.getApellidos())")
	public AutorDTO toDTO(Autor autor);
	
	//proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo convertimos a entidad
	public Autor toBean(AutorDTORequest dto);

}
