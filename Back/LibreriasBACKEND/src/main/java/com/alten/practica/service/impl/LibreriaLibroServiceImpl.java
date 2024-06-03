package com.alten.practica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.LibreriaLibro;
import com.alten.practica.modelo.entidad.Libro;
import com.alten.practica.modelo.entidad.dto.LibreriaLibroDTO;
import com.alten.practica.modelo.entidad.dto.request.LibreriaLibroDTORequest;
import com.alten.practica.modelo.entidad.mapper.ILibreriaLibroMapper;
import com.alten.practica.repository.ILibreriaLibroRepository;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.repository.ILibroRepository;
import com.alten.practica.service.ILibreriaLibroService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz LibreriaLibroService
 * 
 * @see com.alten.practica.service.ILibreriaLibroService
 * 
 */
@Transactional // Transacción para todos los métodos del servicio
@Service
public class LibreriaLibroServiceImpl implements ILibreriaLibroService {

	@Autowired
	ILibreriaLibroRepository libreriaLibroRepository;
	@Autowired
	ILibreriaLibroMapper libreriaLibroMapper;
	@Autowired
	LibreriaUtil libreriaUtil;
	@Autowired
	ILibreriaRepository libreriaRepository;
	@Autowired
	ILibroRepository libroRepository;

	/**
	 * Guarda una nueva relación entre una librería y un libro en la base de datos.
	 * 
	 * @param dto Los datos de la relación entre librería y libro a guardar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
	 *         la relación creada.
	 * @throws EntityNotFoundException Si no se encuentra ninguna librería o libro
	 *                                 con los IDs proporcionados en los datos.
	 */
	@Override
	public HrefEntityDTO save(LibreriaLibroDTORequest dto) {
		Libreria cli = libreriaRepository.findById(dto.getIdLibreria()).orElseThrow(() -> new EntityNotFoundException(
				String.format("La libreria con id %s no existe", dto.getIdLibreria())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));

		LibreriaLibro ccl = LibreriaLibro.builder().libreria(cli).libro(libro).cantidad(dto.getCantidad())
				.precio(dto.getPrecio()).edicion(dto.getEdicion()).fechaPublicacion(dto.getFechaPublicacion()).build();

		return libreriaUtil.createHrefFromResource(this.libreriaLibroRepository.save(ccl).getId(),
				LibreriaResource.LIBRERIALIBRO);
	}

	/**
	 * Busca una relación entre una librería y un libro por su ID en la base de
	 * datos.
	 * 
	 * @param id El ID de la relación entre librería y libro a buscar.
	 * @return Un objeto {@code LibreriaLibroDTO} que representa la relación
	 *         encontrada.
	 * @throws EntityNotFoundException Si no se encuentra ninguna relación con el ID
	 *                                 proporcionado.
	 */
	@Transactional(readOnly = true)
	@Override
	public LibreriaLibroDTO findById(int id) {
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));

		return libreriaLibroMapper.toDTO(cpl);
	}

	/**
	 * Obtiene una lista de todas las relaciones entre librerías y libros en la base
	 * de datos.
	 * 
	 * @return Una lista de objetos {@code LibreriaLibroDTO} que representan todas
	 *         las relaciones en la base de datos.
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<LibreriaLibroDTO> findAll(Pageable pageable) {
		Page<LibreriaLibro> lista = libreriaLibroRepository.findAll(pageable);
		return lista.map(libreriaLibroMapper::toDTO);
	}

	/**
	 * Actualiza los datos de una relación entre una librería y un libro existente
	 * en la base de datos.
	 * 
	 * @param dto Los nuevos datos de la relación entre librería y libro.
	 * @param id  El ID de la relación a actualizar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
	 *         la relación actualizada.
	 * @throws EntityNotFoundException Si no se encuentra ninguna librería o libro
	 *                                 con los IDs proporcionados en los datos, o si
	 *                                 no se encuentra ninguna relación con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO update(LibreriaLibroDTORequest dto, int id) {

		Libreria cli = libreriaRepository.findById(dto.getIdLibreria()).orElseThrow(() -> new EntityNotFoundException(
				String.format("La libreria con id %s no existe", dto.getIdLibreria())));
		Libro libro = libroRepository.findById(dto.getIdLibro()).orElseThrow(
				() -> new EntityNotFoundException(String.format("El libro con id %s no existe", dto.getIdLibro())));

		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));

		cpl.setLibreria(cli);
		cpl.setLibro(libro);
		cpl.setCantidad(dto.getCantidad());
		cpl.setPrecio(dto.getPrecio());
		cpl.setEdicion(dto.getEdicion());
		cpl.setFechaPublicacion(dto.getFechaPublicacion());

		return libreriaUtil.createHrefFromResource(this.libreriaLibroRepository.save(cpl).getId(),
				LibreriaResource.LIBRERIALIBRO);

	}

	/**
	 * Elimina una relación entre una librería y un libro de la base de datos.
	 * 
	 * @param id El ID de la relación a eliminar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso de
	 *         la relación eliminada.
	 * @throws EntityNotFoundException Si no se encuentra ninguna relación con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO delete(int id) {
		LibreriaLibro cpl = libreriaLibroRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("id %s no existe", id)));

		this.libreriaLibroRepository.delete(cpl);
		return libreriaUtil.createHrefFromResource(cpl.getId(), LibreriaResource.LIBRERIALIBRO);

	}
	
	/**
     * Disminuye la cantidad de un libro en una librería en 1.
     * 
     * @param idLibro    El ID del libro.
     * @param idLibreria El ID de la librería.
     * @throws EntityNotFoundException Si no se encuentra la relación entre la librería y el libro.
     */
	@Transactional
	@Override
	public void disminuirCantidadLibro(int idLibreriaLibro) {
	    LibreriaLibro libreriaLibro = libreriaLibroRepository.findById(idLibreriaLibro)
	            .orElseThrow(() -> new EntityNotFoundException("No se encontró la relación entre librería y libro"));

	    if (libreriaLibro.getCantidad() > 0) {
	        libreriaLibro.setCantidad(libreriaLibro.getCantidad() - 1);
	        libreriaLibroRepository.save(libreriaLibro);
	    } else {
	        throw new IllegalArgumentException("La cantidad del libro no puede ser menor a 0");
	    }
	}
    
    @Override
    @Transactional(readOnly = true)
    public Page<LibreriaLibroDTO> findByTituloContaining(String titulo, Pageable pageable) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("El título proporcionado está vacío o es nulo.");
            return Page.empty(pageable);
        }

        // Obtener todos los registros desde la base de datos
        List<LibreriaLibro> listaLibros = (List<LibreriaLibro>) libreriaLibroRepository.findAll();

        if (listaLibros.isEmpty()) {
            System.out.println("No se encontraron registros en la base de datos.");
            return Page.empty(pageable);
        }

        // Transformar el título de búsqueda a minúsculas y eliminar espacios
        String lowerCaseTitle = titulo.toLowerCase().replace(" ", "");

        // Desencriptar y buscar en la aplicación
        List<LibreriaLibro> filteredLibros = listaLibros.stream()
            .filter(libreriaLibro -> {
                String decryptedTitle = libreriaLibro.getLibro().getTitulo();
                String lowerCaseDecryptedTitle = decryptedTitle.toLowerCase().replace(" ", "");
                return lowerCaseDecryptedTitle.contains(lowerCaseTitle);
            })
            .collect(Collectors.toList());

        // Paginar la lista filtrada
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredLibros.size());
        List<LibreriaLibro> sublist = filteredLibros.subList(start, end);

        // Mapear a DTOs
        Page<LibreriaLibro> libroPage = new PageImpl<>(sublist, pageable, filteredLibros.size());
        return libroPage.map(libreriaLibro -> libreriaLibroMapper.toDTO(libreriaLibro));
    	
    	
        //Page<LibreriaLibro> lista = libreriaLibroRepository.findByTituloContainingNative(titulo, pageable);
        //return lista.map(libreriaLibroMapper::toDTO);
    }

	@Override
	public Page<LibreriaLibroDTO> findByLibraryId(int idLibreria, Pageable pageable) {
		Page<LibreriaLibro> libros = libreriaLibroRepository.findByLibraryId(idLibreria, pageable);
        return libros.map(libreriaLibroMapper::toDTO);
	}

	@Override
	public Page<LibreriaLibroDTO> findByLibraryNotId(int idLibreria, Pageable pageable) {
		Page<LibreriaLibro> libros = libreriaLibroRepository.findByLibraryIdNot(idLibreria, pageable);
        return libros.map(libreriaLibroMapper::toDTO);
	}

}
