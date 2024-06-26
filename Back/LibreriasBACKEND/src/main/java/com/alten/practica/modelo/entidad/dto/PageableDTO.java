package com.alten.practica.modelo.entidad.dto;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que implementa la interfaz Pageable para manejar la paginación de
 * resultados.
 *
 * Esta clase proporciona información sobre la paginación de resultados,
 * incluyendo el número de página, el tamaño de página, el orden opcional y el
 * campo opcional para ordenar los resultados.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO implements Pageable {

	private int page;
	private int size;
	private Optional<Integer> order;
	private Optional<String> field;

	public Optional<Integer> getOrder() {
		return order;
	}

	public Optional<String> getField() {
		return field;
	}

	@Override
	public int getPageNumber() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public long getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable withPage(int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

}
