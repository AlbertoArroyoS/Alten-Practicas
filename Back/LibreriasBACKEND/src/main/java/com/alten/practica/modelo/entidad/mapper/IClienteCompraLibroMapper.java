package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.ClienteCompraLibro;
import com.alten.practica.modelo.entidad.dto.ClienteCompraLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteCompraLibroDTORequest;

@Mapper(builder = @Builder(disableBuilder = true))
public interface IClienteCompraLibroMapper {

	@Mapping(target = "id", source = "id")
	@Mapping(target = "idCliente", source = "cliente.id")
	@Mapping(target = "nombreCliente", source = "cliente.nombre")
	@Mapping(target = "apellidosCliente", source = "cliente.apellidos")
	@Mapping(target = "idLibro", source = "libro.id")
	@Mapping(target = "tituloLibro", source = "libro.titulo")
	@Mapping(target = "fechaCompra", source = "fechaCompra")
	@Mapping(target = "precio", source = "precio")
	ClienteCompraLibroDTO toDTO(ClienteCompraLibro clienteCompraLibro);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public ClienteCompraLibro toBean(ClienteCompraLibroDTORequest dto);

}
