package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
@Mapper(builder = @Builder(disableBuilder = true))
public interface IUsuarioAdminMapper {
	
	UsuarioDTO toDTO(Usuario usuario);

	// proceso de mappeo de DTORequest que es lo que nos manda el cliente, lo
	// convertimos a entidad
	public Usuario toBean(UsuarioDTORequest dto);

}
