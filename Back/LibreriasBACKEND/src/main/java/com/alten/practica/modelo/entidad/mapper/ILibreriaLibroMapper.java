package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ILibreriaLibroMapper {

	@Mapping(target = "nombreLibreria", source = "libreria.nombreLibreria") // Asume que Libreria tiene un campo `nombre`
    @Mapping(target = "tituloLibro", source = "libro.titulo") // Asume que Libro tiene un campo `titulo`
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cantidad", source = "cantidad")
    @Mapping(target = "precio", source = "precio")
    LibreriaLibroDTO toDTO(LibreriaLibro libreriaLibro);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public LibreriaLibro toBean(LibreriaLibroDTORequest dto);

}
