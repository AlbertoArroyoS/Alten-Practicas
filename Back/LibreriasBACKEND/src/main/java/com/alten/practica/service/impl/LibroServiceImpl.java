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
	@Autowired
	IAutorRepository autorRepository;
	
	
	@Override
	public LibroDTO save(LibroDTORequest dto) {
	    Libro libro = new Libro();
	       
	    String nombreAutor = dto.getAutor().getNombre();
	    String apellidoAutor = dto.getAutor().getApellidos();

	    //(String titulo,int idAutor,String genero,int paginas,String editorial,String descripcion,double precio)
	    return libroRepository.nuevoLibroSQL(dto.getTitulo(), nombreAutor, apellidoAutor,dto.getGenero(),dto.getPaginas(), dto.getEditorial(), dto.getDescripcion(), dto.getPrecio() );
	}

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
			libroDTO.setIdAutor(bean.getAutor().getId());
			libroDTO.setAutorNombre(bean.getAutor().getNombre());
			libroDTO.setAutorApellido(bean.getAutor().getApellidos());
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
			libroDTO.setIdAutor(bean.getAutor().getId());
			libroDTO.setAutorNombre(bean.getAutor().getNombre());
			libroDTO.setAutorApellido(bean.getAutor().getApellidos());
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
			libroDTO.setIdAutor(bean.getAutor().getId());
			libroDTO.setAutorNombre(bean.getAutor().getNombre());
			libroDTO.setAutorApellido(bean.getAutor().getApellidos());
			libroDTO.setGenero(bean.getGenero());
			libroDTO.setPaginas(bean.getPaginas());
			libroDTO.setEditorial(bean.getEditorial());
			libroDTO.setDescripcion(bean.getDescripcion());
			libroDTO.setPrecio(bean.getPrecio());	
			listaDTO.add(libroDTO);
		}
		return listaDTO;
		
	}
	

}
