package com.alten.practica.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;
import com.alten.practica.modelo.entidad.Libreria;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ILibreriaMapper {

	// Metodo para realizar el mapeo de las entidades a los DTO
	public LibreriaDTO toDTO(Libreria libreria);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public Libreria toBean(LibreriaDTORequest dto);

}
