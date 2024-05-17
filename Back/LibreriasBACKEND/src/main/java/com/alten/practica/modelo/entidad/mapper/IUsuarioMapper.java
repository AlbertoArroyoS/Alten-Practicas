package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;

@Mapper(builder = @Builder(disableBuilder = true))
public interface IUsuarioMapper {

	@Mapping(target = "idUsuario", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "idCliente", source = "cliente.id")
    @Mapping(target = "nombre", source = "cliente.nombre")
    @Mapping(target = "apellidos", source = "cliente.apellidos")
	@Mapping(target = "email", source = "cliente.email")
    @Mapping(target = "idLibreria", source = "libreria.id")
    @Mapping(target = "nombreLibreria", source = "libreria.nombreLibreria")
    @Mapping(target = "nombreDueno", source = "libreria.nombreDueno")
    @Mapping(target = "direccion", source = "libreria.direccion")
    UsuarioDTO toDTO(Usuario usuario);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public Usuario toBean(UsuarioDTORequest dto);

}
