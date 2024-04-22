package com.alten.practica.modelo.entidad.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibroDTO;

@Mapper (builder = @Builder(disableBuilder = true))
public interface ILibroMapper {
	
	// proceso de mappeo de la entidad a DTO, ya que los atributos del DTO no
	// coinciden con los de la entidad
	// source: atributo de la entidad, target: atributo del DTO, expression:
	// expresion para realizar el mapeo
	
	@Mapping(target = "id", source = "libro.id")
    @Mapping(target = "titulo", source = "libro.titulo")
    @Mapping(target = "nombreAutor", source = "libro.autor.nombre")
    @Mapping(target = "apellidosAutor", source = "libro.autor.apellidos")
    @Mapping(target = "genero", source = "libro.genero")
    @Mapping(target = "paginas", source = "libro.paginas")
    @Mapping(target = "editorial", source = "libro.editorial")
    @Mapping(target = "descripcion", source = "libro.descripcion")
    LibroDTO toDTO(Libro libro);

    // Similar mapeo inverso, si es necesario.
    @Mapping(target = "id", source = "libroDTO.id")
    @Mapping(target = "titulo", source = "libroDTO.titulo")
    // Asumiendo que puedes crear un objeto Autor a partir del nombre y apellidos del DTO.
    // Esto podría necesitar una lógica adicional para crear correctamente un objeto Autor.
    @Mapping(target = "autor.nombre", source = "libroDTO.nombreAutor")
    @Mapping(target = "autor.apellidos", source = "libroDTO.apellidosAutor")
    @Mapping(target = "genero", source = "libroDTO.genero")
    @Mapping(target = "paginas", source = "libroDTO.paginas")
    @Mapping(target = "editorial", source = "libroDTO.editorial")
    @Mapping(target = "descripcion", source = "libroDTO.descripcion")
    Libro toBean(LibroDTO libroDTO);

}
