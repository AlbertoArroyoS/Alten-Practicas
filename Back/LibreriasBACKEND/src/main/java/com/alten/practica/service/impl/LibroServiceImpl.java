package com.alten.practica.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibroMapper;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.ILibroService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/*
 * Clase que implementa la interfaz LibroService
 * @see com.alten.practica.service.ILibroService
 */
@Transactional
@Service
public class LibroServiceImpl implements ILibroService {

	@Autowired
	ILibroRepository libroRepository;
	@Autowired
	IAutorRepository autorRepository;
	@Autowired
	ILibroMapper libroMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	// Método para convertir de entidad Libro a DTO LibroDTO. Ya no se necesita,
	// realizar el mapeo con MapStruct
	/*
	 * private LibroDTO convertirBeanADTO(Libro libro) { return
	 * LibroDTO.builder().titulo(libro.getTitulo()).nombreAutor(libro.getAutor().
	 * getNombre())
	 * .apellidosAutor(libro.getAutor().getApellidos()).genero(libro.getGenero()).
	 * paginas(libro.getPaginas())
	 * .editorial(libro.getEditorial()).descripcion(libro.getDescripcion()).precio(
	 * libro.getPrecio()).build(); }
	 */

	/**
	 * Guarda un nuevo libro en la base de datos.
	 * 
	 * @param dto Los datos del libro a guardar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         libro creado.
	 * @throws IllegalStateException Si ya existe un libro con el mismo título.
	 */
	@Override
	public HrefEntityDTO save(LibroDTORequest dto) {

		// Verificar si el libro ya existe
		Optional<Libro> libroExistente = libroRepository.findByTitulo(dto.getTitulo());
		if (libroExistente.isPresent()) {
			throw new IllegalStateException("Libro con el título '" + dto.getTitulo() + "' ya existe");
		}
		Libro libro = new Libro();

		// Verificar si el autor ya existe
		Optional<Autor> autorExistente = autorRepository.findByNombreAndApellidos(dto.getNombreAutor(),
				dto.getApellidosAutor());

		if (autorExistente.isPresent()) {
			// Si el autor ya existe, asegúrate de inicializar el autor en libro antes de
			// asignar el ID
			libro.setAutor(new Autor());
			libro.getAutor().setId(autorExistente.get().getId());
		} else {
			// Si el autor no existe, crear un nuevo autor y asignarlo a libro
			Autor nuevoAutor = new Autor();
			nuevoAutor.setNombre(dto.getNombreAutor());
			nuevoAutor.setApellidos(dto.getApellidosAutor());
			nuevoAutor = autorRepository.save(nuevoAutor); // Guardar el autor y obtener su ID

			libro.setAutor(nuevoAutor);
		}

		// Setear las propiedades del libro
		libro.setTitulo(dto.getTitulo());
		libro.setGenero(dto.getGenero());
		libro.setPaginas(dto.getPaginas());
		libro.setEditorial(dto.getEditorial());
		libro.setDescripcion(dto.getDescripcion());

		// Guardar el libro en la base de datos
		libro = libroRepository.save(libro);

		// Devolver el DTO adecuado
		return libreriaUtil.createHrefFromResource(libro.getId(), LibreriaResource.LIBRO);

		/*
		 * Forma vieja // Buscar el autor en la base de datos Autor autor =
		 * autorRepository.findByNombreAndApellidos(dto.getNombreAutor(),
		 * dto.getApellidosAutor());
		 * 
		 * // Verificar si el autor existe if (autor == null) { // Si el autor no
		 * existe, crear un nuevo autor autor = new Autor();
		 * autor.setNombre(dto.getNombreAutor());
		 * autor.setApellidos(dto.getApellidosAutor()); autor =
		 * autorRepository.save(autor); // Guardar el autor y obtener su ID }
		 */
		/*
		 * // Comprobar si el libro ya existe Libro libroExistente =
		 * libroRepository.findByTituloAndAutor(dto.getTitulo(), autor);
		 * 
		 * // Verificar si el libro ya existe if (libroExistente != null) { // Si el
		 * libro ya existe, puedes manejar el caso aquí, por ejemplo, lanzar una
		 * excepción o devolver null return null; // Devolver null indicando que el
		 * libro ya existe }
		 */

	}

	// **** usar save ****
	@Override
	public int saveLibroSQL(LibroDTORequest dto) {
		libroRepository.guardarLibroSQL(dto.getTitulo(), dto.getNombreAutor(), dto.getApellidosAutor(), dto.getGenero(),
				dto.getPaginas(), dto.getEditorial(), dto.getDescripcion());
		return 0;
	}

	/**
	 * Busca un libro por su ID en la base de datos.
	 * 
	 * @param id El ID del libro a buscar.
	 * @return Un objeto {@code LibroDTO} que representa el libro encontrado.
	 * @throws EntityNotFoundException Si no se encuentra ningún libro con el ID
	 *                                 proporcionado.
	 */
	@Transactional(readOnly = true)
	@Override
	public LibroDTO findById(int id) {

		Libro libro = libroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El libro con id %s no existe", id)));

		return libroMapper.toDTO(libro);

		/*
		 * LibroDTO libroDTO = new LibroDTO(); libroDTO.setTitulo(bean.getTitulo());
		 * libroDTO.setNombreAutor(bean.getAutor().getNombre());
		 * libroDTO.setApellidosAutor(bean.getAutor().getApellidos());
		 * libroDTO.setGenero(bean.getGenero()); libroDTO.setPaginas(bean.getPaginas());
		 * libroDTO.setEditorial(bean.getEditorial());
		 * libroDTO.setDescripcion(bean.getDescripcion());
		 * libroDTO.setPrecio(bean.getPrecio()); return libroDTO;
		 */
		/*
		 * Libro libro = this.libroRepository.findById(id).get();
		 * 
		 * if (libro != null) { // Utilizar el método convertirEntidadADto para
		 * convertir el libro a un DTO LibroDTO libroDTO = libroMapper.toDTO(libro); //
		 * Devolver el objeto LibroDTO return libroDTO; } else { // Si el libro no
		 * existe, devolver null o manejar el caso return null; }
		 */

	}

	/**
	 * Obtiene una lista de todos los libros en la base de datos.
	 * 
	 * @return Una lista de objetos {@code LibroDTO} que representan todos los
	 *         libros en la base de datos.
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<LibroDTO> findAll(Pageable pageable) {
		Page<Libro> lista = this.libroRepository.findAll(pageable);

		return lista.map(libro -> libroMapper.toDTO(libro));
		/*
		 * Forma vieja List<LibroDTO> listaDTO = new ArrayList<>(); for (Libro bean :
		 * lista) { LibroDTO libroDTO = new LibroDTO();
		 * libroDTO.setTitulo(bean.getTitulo());
		 * libroDTO.setNombreAutor(bean.getAutor().getNombre());
		 * libroDTO.setApellidosAutor(bean.getAutor().getApellidos());
		 * libroDTO.setGenero(bean.getGenero()); libroDTO.setPaginas(bean.getPaginas());
		 * libroDTO.setEditorial(bean.getEditorial());
		 * libroDTO.setDescripcion(bean.getDescripcion());
		 * libroDTO.setPrecio(bean.getPrecio()); listaDTO.add(libroDTO); } return
		 * listaDTO;
		 */

	}

	/**
	 * Actualiza los datos de un libro existente en la base de datos.
	 * 
	 * @param dto Los nuevos datos del libro.
	 * @param id  El ID del libro a actualizar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         libro actualizado.
	 * @throws EntityNotFoundException Si no se encuentra ningún libro con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO update(LibroDTORequest dto, int id) {
	    // Encuentra el libro por id
	    Libro libro = libroRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(String.format("El libro con id %s no existe", id)));

	    // Comprobar si existen duplicados antes de actualizar, excluyendo el libro actual
	    List<Libro> librosDuplicados = libroRepository.findByTitulo(dto.getTitulo()).stream()
	            .filter(a -> a.getId() != id)
	            .collect(Collectors.toList());

	    if (!librosDuplicados.isEmpty()) {
	        throw new IllegalStateException("El libro con el título '" + dto.getTitulo() + "' ya existe");
	    }

	    // Actualiza los campos del libro
	    libro.setTitulo(dto.getTitulo());
	    libro.setGenero(dto.getGenero());
	    libro.setPaginas(dto.getPaginas());
	    libro.setEditorial(dto.getEditorial());
	    libro.setDescripcion(dto.getDescripcion());

	    // Guarda los cambios y retorna la entidad
	    Libro libroActualizado = libroRepository.save(libro);
	    return libreriaUtil.createHrefFromResource(libroActualizado.getId(), LibreriaResource.LIBRO);
	}


	/**
	 * Elimina un libro de la base de datos.
	 * 
	 * @param id El ID del libro a eliminar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         libro eliminado.
	 * @throws EntityNotFoundException Si no se encuentra ningún libro con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO delete(int id) {
		Libro libro = libroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El libro con id %s no existe", id)));

		this.libroRepository.deleteById(libro.getId());
		return libreriaUtil.createHrefFromResource(libro.getId(), LibreriaResource.LIBRO);
	}

	/**
	 * Busca libros por título en la base de datos y devuelve una página de
	 * resultados.
	 * 
	 * @param title    El título del libro a buscar.
	 * @param pageable La información de paginación para la consulta.
	 * @return Una página de objetos {@code LibroDTO} que representan los libros
	 *         encontrados.
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<LibroDTO> findByTitle(String title, Pageable pageable) {
		Page<Libro> listaPages = this.libroRepository.buscarKeyWordSQL(title, pageable);
		return listaPages.map(libro -> libroMapper.toDTO(libro));

	}

	/**
	 * Obtiene el ID de un autor por su nombre completo.
	 * 
	 * @param nombreCompleto El nombre completo del autor.
	 * @return El ID del autor si existe, de lo contrario, devuelve -1.
	 */
	public int obtenerIdAutor(String nombreCompleto) {

		List<Autor> autor = autorRepository.buscarKeyWordSQL(nombreCompleto);

		// Verificar si la lista está vacía antes de intentar acceder al primer elemento
		if (autor.isEmpty()) {
			return -1;
		}

		// Obtener el ID del primer autor en la lista
		return autor.get(0).getId();

	}
	
	@Override
    public Page<LibroDTO> findByAuthorId(int authorId, Pageable pageable) {
        Page<Libro> libros = libroRepository.findByAutorId(authorId, pageable);
        return libros.map(libroMapper::toDTO);
    }

}
