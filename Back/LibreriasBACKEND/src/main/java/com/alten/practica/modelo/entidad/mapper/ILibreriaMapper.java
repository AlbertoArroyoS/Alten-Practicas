package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.dto.LibreriaDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaDTORequest;

/*
 * Interfaz que se encarga de realizar el mapeo de la entidad a DTO y viceversa
 * 
 * @Mapper: indica que es una interfaz de mapeo
 * @Builder: indica que se va a utilizar el patron builder para crear la entidad
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface ILibreriaMapper {

	// Metodo para realizar el mapeo de las entidades a los DTO
	public LibreriaDTO toDTO(Libreria libreria);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public Libreria toBean(LibreriaDTORequest dto);

}
