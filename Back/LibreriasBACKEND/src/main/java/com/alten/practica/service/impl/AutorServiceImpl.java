package com.alten.practica.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Autor;
import com.alten.practica.modelo.entidad.dto.AutorDTO;
import com.alten.practica.modelo.entidad.dto.request.AutorDTORequest;
import com.alten.practica.modelo.entidad.mapper.IAutorMapper;
import com.alten.practica.repository.IAutorRepository;
import com.alten.practica.service.IAutorService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz IAutorService
 * 
 * @see com.alten.practica.service.IAutorService
 */
@Transactional // Transacción para todos los métodos del servicio
@Service
public class AutorServiceImpl implements IAutorService {

	// inyectamos el repositorio del autor
	@Autowired
	IAutorRepository autorRepository;
	@Autowired
	IAutorMapper autorMapper;
	@Autowired
	LibreriaUtil libreriaUtil;

	// Metodo para convertir de entidad a dto. Ya no se necesita, realizar el mapeo
	// con MapStruct
	/*
	 * private AutorDTO convertirBeanADTO(Autor autor) { return AutorDTO.builder()
	 * .id(autor.getId()) .nombre(autor.getNombre() + " " +
	 * autor.getApellidos()).build(); }
	 */

	/**
	 * Guarda un nuevo autor en la base de datos.
	 * 
	 * @param dto Los datos del autor a guardar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         autor creado.
	 * @throws IllegalStateException Si ya existe un autor con el mismo nombre y
	 *                               apellidos en la base de datos.
	 */
	@Override
	public HrefEntityDTO save(AutorDTORequest dto) {

		// Comprobar si ya existe un autor con el mismo nombre y apellidos
		autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos()).ifPresent(a -> {
			throw new IllegalStateException(
					"Autor con el nombre '" + dto.getNombre() + "' y apellidos '" + dto.getApellidos() + "' ya existe");
		});

		Autor autor = this.autorRepository.save(this.autorMapper.toBean(dto));

		return libreriaUtil.createHrefFromResource(autor.getId(), LibreriaResource.AUTOR);

		/*
		 * Forma vieja Autor autor = new Autor(); autor.setNombre(dto.getNombre());
		 * autor.setApellidos(dto.getApellidos());
		 * autorMapper.toDTO(autorRepository.save(autor));
		 */

		// return autorMapper.toDTO(autor);

		// return autorMapper.toDTO(autorRepository.save(autor));
		// Autor autor = this.autorRepository.save(this.autorMapper.toBean(dto));
	}

	/**
	 * Guarda un nuevo autor utilizando una consulta SQL personalizada.
	 * 
	 * @param dto Los datos del autor a guardar.
	 * @return Un objeto {@code AutorDTO} que representa el autor guardado.
	 */
	@Override
	public AutorDTO saveAutorSQL(AutorDTORequest dto) {
		// convertir a dto con el metodo convertirEntidadADto
		return autorMapper.toDTO(this.autorRepository.nuevoAutorSQL(dto.getNombre(), dto.getApellidos()));

	}

	/**
	 * Busca un autor por su ID en la base de datos.
	 * 
	 * @param id El ID del autor a buscar.
	 * @return Un objeto {@code AutorDTO} que representa al autor encontrado.
	 * @throws EntityNotFoundException Si no se encuentra ningún autor con el ID
	 *                                 proporcionado.
	 */
	@Transactional(readOnly = true)
	@Override
	public AutorDTO findById(int id) {
		// Buscar el autor por su ID en el repositorio de autores
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));

		return autorMapper.toDTO(autor);
		/*
		 * // Verificar si el autor existe if (autor != null) { // Utilizar el método
		 * convertirEntidadADto para convertir el autor a un DTO AutorDTO autorDTO =
		 * autorMapper.toDTO(autor);
		 * 
		 * // Devolver el objeto AutorDTO return autorDTO; } else { // Si el autor no
		 * existe, devolver null o manejar el caso
		 * 
		 * return null; }
		 */
	}

	/**
	 * Obtiene una lista de todos los autores en la base de datos.
	 * 
	 * @return Una lista de objetos {@code AutorDTO} que representan a todos los
	 *         autores en la base de datos.
	 */
	@Transactional(readOnly = true)
	@Override
	public List<AutorDTO> findAll() {
		List<Autor> lista = this.autorRepository.findAll();
		return lista.stream().map(autor -> autorMapper.toDTO(autor)).collect(Collectors.toList());

		/*
		 * Forma vieja List<AutorDTO> listaDTO = new ArrayList<>(); for (Autor bean :
		 * lista) { AutorDTO autorDTO = new AutorDTO(); autorDTO.setId(bean.getId());
		 * autorDTO.setNombre(bean.getNombre() + " " + bean.getApellidos());
		 * listaDTO.add(autorDTO); } return listaDTO;
		 */

	}

	/**
	 * Actualiza los datos de un autor existente en la base de datos.
	 * 
	 * @param dto Los nuevos datos del autor.
	 * @param id  El ID del autor a actualizar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         autor actualizado.
	 * @throws IllegalStateException   Si ya existe un autor con los mismos nombre y
	 *                                 apellidos que los nuevos datos
	 *                                 proporcionados.
	 * @throws EntityNotFoundException Si no se encuentra ningún autor con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO update(AutorDTORequest dto, int id) {
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));
		autor.setId(id);

		// Comprobar si existen duplicados antes de actualizar
		List<Autor> autoresDuplicados = autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos())
				.stream().filter(a -> a.getId() != id) // Excluir el autor actual de la comprobación de duplicados
				.collect(Collectors.toList());

		if (!autoresDuplicados.isEmpty()) {
			throw new IllegalStateException(
					"Autor con el nombre '" + dto.getNombre() + "' y apellidos '" + dto.getApellidos() + "' ya existe");
		}

		// Actualizar datos del autor
		autor.setNombre(dto.getNombre());
		autor.setApellidos(dto.getApellidos());

		// Guardar el autor actualizado y devolver un enlace al recurso actualizado
		Autor savedAutor = autorRepository.save(autor);
		return libreriaUtil.createHrefFromResource(savedAutor.getId(), LibreriaResource.AUTOR);
	}

	/*
	 * FORMA ANTIGUA DE HACERLO public HrefEntityDTO update(AutorDTORequest dto, int
	 * id) { Autor autor = autorRepository.findById(id) .orElseThrow(() -> new
	 * EntityNotFoundException(String.format("El autor con id %s no existe", id)));
	 * autor.setId(id); autor.setNombre(dto.getNombre());
	 * autor.setApellidos(dto.getApellidos()); //return
	 * autorMapper.toDTO(autorRepository.save(autor));
	 * 
	 * autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos())
	 * .ifPresent(a -> { throw new IllegalStateException("Autor con el nombre '" +
	 * dto.getNombre() + "' y apellidos '"+ dto.getApellidos()+ "' ya existe"); });
	 * 
	 * return
	 * libreriaUtil.createHrefFromResource(this.autorRepository.save(autor).getId(),
	 * LibreriaResource.AUTOR); }
	 */

	/**
	 * Elimina un autor de la base de datos.
	 * 
	 * @param id El ID del autor a eliminar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         autor eliminado.
	 * @throws EntityNotFoundException Si no se encuentra ningún autor con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO delete(int id) {
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));
		this.autorRepository.delete(autor);
		return libreriaUtil.createHrefFromResource(autor.getId(), LibreriaResource.AUTOR);

	}

	/**
	 * Busca autores en la base de datos que coincidan con una palabra clave dada.
	 * 
	 * @param nombre La palabra clave para buscar autores.
	 * @return Una lista de objetos {@code AutorDTO} que representan a los autores
	 *         encontrados.
	 */
	@Override
	public List<AutorDTO> buscarKeyWordSQL(String nombre) {
		List<Autor> listaAutores = this.autorRepository.buscarKeyWordSQL(nombre);
		return listaAutores.stream().map((bean) -> autorMapper.toDTO(bean)).collect(Collectors.toList());

	}

	/**
	 * Obtiene el ID de un autor en la base de datos dado su nombre completo.
	 * 
	 * @param nombreCompleto El nombre completo del autor.
	 * @return El ID del autor encontrado, o -1 si no se encuentra ningún autor con
	 *         el nombre proporcionado.
	 */
	@Override
	public int obtenerIdAutor(String nombreCompleto) {
		// Buscar autores por nombre completo en la base de datos

		List<Autor> autor = autorRepository.buscarKeyWordSQL(nombreCompleto);

		// Verificar si la lista está vacía antes de intentar acceder al primer elemento
		if (autor.isEmpty()) {
			return -1;
		}

		// Obtener el ID del primer autor en la lista
		return autor.get(0).getId();

	}

	/**
	 * Busca un autor por su nombre y apellidos en la base de datos.
	 * 
	 * @param nombre    El nombre del autor.
	 * @param apellidos Los apellidos del autor.
	 * @return Un objeto {@code Optional<AutorDTO>} que representa al autor
	 *         encontrado, si existe.
	 * @throws IllegalStateException Si ya existe un autor con el mismo nombre y
	 *                               apellidos.
	 */
	@Transactional(readOnly = true) // para que no se haga un commit, solo lectura , de manera especifica ("En caso
									// de tener el general y el especifico, se toma el especifico,
	@Override
	public Optional<AutorDTO> findByName(String nombre, String apellidos) {
		Optional<Autor> autorOptional = autorRepository.findByNombreAndApellidos(nombre, apellidos);

		if (autorOptional.isPresent()) {
			throw new IllegalStateException(
					"Autor con el nombre '" + nombre + "' y apellidos '" + apellidos + "' ya existe");
		}

		return autorOptional.map(autorMapper::toDTO);
	}

}
