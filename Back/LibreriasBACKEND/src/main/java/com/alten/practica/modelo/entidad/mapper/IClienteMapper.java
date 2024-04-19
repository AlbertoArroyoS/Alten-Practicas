package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.dto.ClienteDTO;
import com.alten.practica.modelo.entidad.dto.request.ClienteDTORequest;

@Mapper(builder = @Builder(disableBuilder = true))
public interface IClienteMapper {

	public ClienteDTO toDTO(Cliente cliente);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public Cliente toBean(ClienteDTORequest dto);

}
