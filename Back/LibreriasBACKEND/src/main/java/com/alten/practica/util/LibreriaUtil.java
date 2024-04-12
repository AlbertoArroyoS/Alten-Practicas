package com.alten.practica.util;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.alten.practica.dto.PageableDTO;

//Clase donde se encuentran todos los metodos generales
//como validar un email, un password, un nombre, un apellido, etc
//Se puede usar en cualquier parte del proyecto

@Component
public class LibreriaUtil {
	

	/**
	 * Metodo que devuelve un objeto Pageable Recibe un objeto PageableDTO, con el
	 * orden, el campo, el numero de pagina y el tamaño de la pagina
	 * 
	 * order = 1 ASC, 0 DESC
	 * 
	 * @param pageableDTO
	 * @return
	 */
	public Pageable getPageable(PageableDTO pageableDTO) {
		Optional<Integer> sortOrder = pageableDTO.getOrder();
		Optional<String> sortField = pageableDTO.getField();
		Integer pageNumber = pageableDTO.getPageNumber();
		Integer perPage = pageableDTO.getPageSize();

		Pageable pageable;
		if (sortOrder.isPresent() && sortField.isPresent()) {
			Sort.Direction direction = sortOrder.get().equals(1) ? Sort.Direction.ASC : Sort.Direction.DESC;
			pageable = PageRequest.of(pageNumber, perPage, Sort.by(direction, sortField.get()));
		} else {
			// Por defecto se ordena por id de forma descendente
			pageable = PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, "id"));
		}
		return pageable;
	}

}
