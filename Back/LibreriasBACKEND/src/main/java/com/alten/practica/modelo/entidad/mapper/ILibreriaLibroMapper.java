package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;

@Mapper (builder = @Builder(disableBuilder = true))
public interface ILibreriaLibroMapper {
	
public AutorDTO toDTO(Autor autor);
	
	//proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo convertimos a entidad
	public Autor toBean(AutorDTORequest dto);

}
