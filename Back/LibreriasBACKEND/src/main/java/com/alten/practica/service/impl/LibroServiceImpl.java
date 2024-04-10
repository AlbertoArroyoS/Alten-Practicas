package com.alten.practica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.practica.dto.AutorDTO;
import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.request.AutorDTORequest;
import com.alten.practica.dto.request.LibroDTORequest;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.LibroService;

@Service
public class LibroServiceImpl implements LibroService{
	
	@Autowired
	ILibroRepository libroRepository;
	
	@Override
	public LibroDTO guardarLibro(String titulo, String nombreAutor, String apellidosAutor, String genero, int paginas, String editorial, String descripcion, double precio) {
        return libroRepository.guardarLibroSQL(titulo, nombreAutor, apellidosAutor, genero, paginas, editorial, descripcion, precio);
    }
	
	/*
	@Override
	public LibroDTO save(LibroDTORequest dto) {
		LibroDTO libroDTO = LibroDTO.builder()
			.titulo(dto.getTitulo())
            .nombreAutor(dto.getNombreAutor()) // Utiliza un método para convertir el autor
            .apellidoAutor(dto.getApellidoAutor()) // Utiliza un método para convertir el autor
            .genero(dto.getGenero())
            .paginas(dto.getPaginas())
            .editorial(dto.getEditorial())
            .descripcion(dto.getDescripcion())
            .precio(dto.getPrecio())
            .build();
		
        return (this.libroRepository.nuevoLibroSQL(libroDTO.getTitulo(), libroDTO.getNombreAutor(), libroDTO.getApellidoAutor(),libroDTO.getGenero(),libroDTO.getPaginas(), libroDTO.getEditorial(), libroDTO.getDescripcion(), libroDTO.getPrecio()));
	    
	    
	    //(String titulo,int idAutor,String genero,int paginas,String editorial,String descripcion,double precio)
	    //return libroRepository.nuevoLibroSQL(dto.getTitulo(), dto.getNombreAutor(), dto.getApellidoAutor(),dto.getGenero(),dto.getPaginas(), dto.getEditorial(), dto.getDescripcion(), dto.getPrecio());
	}*/

	@Override
	public int update(LibroDTORequest dto, int id) {
		Libro libro = this.libroRepository.findById(id).get();
		libro.setTitulo(dto.getTitulo());
		libro.setGenero(dto.getGenero());
		libro.setPaginas(dto.getPaginas());
		libro.setEditorial(dto.getEditorial());
		libro.setDescripcion(dto.getDescripcion());
		libro.setPrecio(dto.getPrecio());
		return this.libroRepository.save(libro).getId();
	}

	@Override
	public LibroDTO findById(int id) {
		Libro bean = this.libroRepository.findById(id).get();
		LibroDTO libroDTO = new LibroDTO();
			libroDTO.setTitulo(bean.getTitulo());
			libroDTO.setNombreAutor(bean.getAutor().getNombre());
			libroDTO.setApellidosAutor(bean.getAutor().getApellidos());
			libroDTO.setGenero(bean.getGenero());
			libroDTO.setPaginas(bean.getPaginas());
			libroDTO.setEditorial(bean.getEditorial());
			libroDTO.setDescripcion(bean.getDescripcion());
			libroDTO.setPrecio(bean.getPrecio());			
		return libroDTO;
	
	}

	@Override
	public List<LibroDTO> findAll() {
		List<Libro> lista = this.libroRepository.findAll();
		List<LibroDTO> listaDTO = new ArrayList<>();
		for (Libro bean : lista) {
			LibroDTO libroDTO = new LibroDTO();
			libroDTO.setTitulo(bean.getTitulo());
			libroDTO.setNombreAutor(bean.getAutor().getNombre());
			libroDTO.setApellidosAutor(bean.getAutor().getApellidos());
			libroDTO.setGenero(bean.getGenero());
			libroDTO.setPaginas(bean.getPaginas());
			libroDTO.setEditorial(bean.getEditorial());
			libroDTO.setDescripcion(bean.getDescripcion());
			libroDTO.setPrecio(bean.getPrecio());			
			listaDTO.add(libroDTO);
		}
		return listaDTO;
		
	}

	@Override
	public int delete(int id) {
		this.libroRepository.deleteById(id);
		return id;
	}

	@Override
	public List<LibroDTO> findByTitle(String title) {
		List<Libro> lista = this.libroRepository.buscarKeyWordSQL(title);
		List<LibroDTO> listaDTO = new ArrayList<>();
		for (Libro bean : lista) {
			LibroDTO libroDTO = new LibroDTO();
			libroDTO.setTitulo(bean.getTitulo());
			libroDTO.setNombreAutor(bean.getAutor().getNombre());
			libroDTO.setApellidosAutor(bean.getAutor().getApellidos());
			libroDTO.setGenero(bean.getGenero());
			libroDTO.setPaginas(bean.getPaginas());
			libroDTO.setEditorial(bean.getEditorial());
			libroDTO.setDescripcion(bean.getDescripcion());
			libroDTO.setPrecio(bean.getPrecio());	
			listaDTO.add(libroDTO);
		}
		return listaDTO;
		
	}
	
	// Método para convertir de entidad Libro a DTO LibroDTO
	private LibroDTO convertirEntidadADto(Libro libro) {
	    return LibroDTO.builder()
	            .titulo(libro.getTitulo())
	            .nombreAutor(libro.getAutor().getNombre()) // Utiliza un método para convertir el autor
	            .apellidosAutor(libro.getAutor().getApellidos()) // Utiliza un método para convertir el autor
	            .genero(libro.getGenero())
	            .paginas(libro.getPaginas())
	            .editorial(libro.getEditorial())
	            .descripcion(libro.getDescripcion())
	            .precio(libro.getPrecio())
	            .build();
	}

	@Override
	public LibroDTO save(LibroDTORequest dto) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
