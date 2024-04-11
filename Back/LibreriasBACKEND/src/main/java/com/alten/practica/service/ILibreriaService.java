package com.alten.practica.service;

import java.util.List;


import com.alten.practica.dto.LibreriaDTO;
import com.alten.practica.dto.request.LibreriaDTORequest;

public interface ILibreriaService {
	
	public int save(LibreriaDTORequest dto);
	
	public int update (LibreriaDTORequest dto, int id);
	
	public LibreriaDTO findById(int id);
	
	public List<LibreriaDTO> findAll();
	

}
