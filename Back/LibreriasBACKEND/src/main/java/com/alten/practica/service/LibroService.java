package com.alten.practica.service;

import java.util.List;

import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.request.LibroDTORequest;

public interface LibroService {
	
	public LibroDTO save(LibroDTORequest dto);
	public int update (LibroDTORequest dto, int id);
	public LibroDTO findById(int id);
	public List<LibroDTO> findAll();
	public int delete(int id);
	public List<LibroDTO> findByTitle(String title);
	LibroDTO guardarLibro(String titulo, String nombreAutor, String apellidosAutor, String genero, int paginas,
			String editorial, String descripcion, double precio);

}
