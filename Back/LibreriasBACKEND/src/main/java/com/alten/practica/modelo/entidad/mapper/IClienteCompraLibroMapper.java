package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;

/*
 * Interfaz que se encarga de realizar el mapeo de la entidad a DTO y viceversa
 * 
 * @Mapper: indica que es una interfaz de mapeo
 * @Builder: indica que se va a utilizar el patron builder para crear la entidad
 * @Mapping: indica el mapeo de los atributos de la entidad a los atributos del DTO
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface IClienteCompraLibroMapper {

	@Mapping(target = "id", source = "id")
	@Mapping(target = "idCliente", source = "cliente.id")
	@Mapping(target = "idLibro", source = "libro.id")
	@Mapping(target = "tituloLibro", source = "libro.titulo")
	@Mapping(target = "fechaCompra", source = "fechaCompra")
	@Mapping(target = "precio", source = "precio")
	ClienteCompraLibroDTO toDTO(ClienteCompraLibro clienteCompraLibro);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public ClienteCompraLibro toBean(ClienteCompraLibroDTORequest dto);

}
