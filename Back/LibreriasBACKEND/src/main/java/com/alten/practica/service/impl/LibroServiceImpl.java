package com.alten.practica.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.dto.LibroDTO;
import com.alten.practica.dto.request.LibroDTORequest;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.ILibroService;

@Transactional
@Service
public class LibroServiceImpl implements ILibroService {

	@Autowired
	ILibroRepository libroRepository;
	@Autowired
	IAutorRepository autorRepository;

	// Método para convertir de entidad Libro a DTO LibroDTO
	private LibroDTO convertirBeanADTO(Libro libro) {
		return LibroDTO.builder().titulo(libro.getTitulo()).nombreAutor(libro.getAutor().getNombre())
				.apellidosAutor(libro.getAutor().getApellidos()).genero(libro.getGenero()).paginas(libro.getPaginas())
				.editorial(libro.getEditorial()).descripcion(libro.getDescripcion()).precio(libro.getPrecio()).build();
	}

	@Override
	public int save(LibroDTORequest dto) {
		// Buscar el autor en la base de datos
		Autor autor = autorRepository.findByNombreAndApellidos(dto.getNombreAutor(), dto.getApellidosAutor());

		// Verificar si el autor existe
		if (autor == null) {
			// Si el autor no existe, crear un nuevo autor
			autor = new Autor();
			autor.setNombre(dto.getNombreAutor());
			autor.setApellidos(dto.getApellidosAutor());
			autor = autorRepository.save(autor); // Guardar el autor y obtener su ID
		}
		
		/*
		 * // Comprobar si el libro ya existe Libro libroExistente =
		 * libroRepository.findByTituloAndAutor(dto.getTitulo(), autor);
		 * 
		 * // Verificar si el libro ya existe if (libroExistente != null) { // Si el
		 * libro ya existe, puedes manejar el caso aquí, por ejemplo, lanzar una
		 * excepción o devolver null return null; // Devolver null indicando que el
		 * libro ya existe }
		 */
		// Crear un nuevo libro
		Libro libro = new Libro();
		libro.setTitulo(dto.getTitulo());
		libro.setAutor(autor);
		libro.setGenero(dto.getGenero());
		libro.setPaginas(dto.getPaginas());
		libro.setEditorial(dto.getEditorial());
		libro.setDescripcion(dto.getDescripcion());
		libro.setPrecio(dto.getPrecio());

		// Guardar el libro en la base de datos
		libro = libroRepository.save(libro);

		// Crear y devolver el objeto LibroDTO
		return libro.getId();
	}
	//**** no funciona correctamente, usar save ****
	@Override
	public int saveLibroSQL(LibroDTORequest dto) {
		libroRepository.guardarLibroSQL(dto.getTitulo(), dto.getNombreAutor(), dto.getApellidosAutor(),
				dto.getGenero(), dto.getPaginas(), dto.getEditorial(), dto.getDescripcion(), dto.getPrecio());
		return 0;
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

}
