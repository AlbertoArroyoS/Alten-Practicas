package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ILibreriaLibroMapper {

	
	  // Mapeos para DTO
    @Mapping(target = "nombreLibreria", source = "libreria.nombreLibreria")
    @Mapping(target = "tituloLibro", source = "libro.titulo")
    @Mapping(target = "idLibro", source = "libro.id")
    @Mapping(target = "idLibreria", source = "libreria.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cantidad", source = "cantidad")
    @Mapping(target = "precio", source = "precio")
    @Mapping(target = "edicion", source = "edicion")
    @Mapping(target = "fechaPublicacion", source = "fechaPublicacion")
    LibreriaLibroDTO toDTO(LibreriaLibro libreriaLibro);

    // Mapeo desde DTORequest a Entidad
    @Mapping(target = "libreria.id", source = "idLibreria")
    @Mapping(target = "libro.id", source = "idLibro")
    @Mapping(target = "cantidad", source = "cantidad")
    @Mapping(target = "precio", source = "precio")
    @Mapping(target = "edicion", source = "edicion")
    @Mapping(target = "fechaPublicacion", source = "fechaPublicacion")
    LibreriaLibro toBean(LibreriaLibroDTORequest dto);

}
