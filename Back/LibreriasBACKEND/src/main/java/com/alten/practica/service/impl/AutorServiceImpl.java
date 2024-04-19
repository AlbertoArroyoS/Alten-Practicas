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

	// Metodo para convertir de entidad a dto. Ya no se necesita, realizar el mapeo con MapStruct
	/*
	private AutorDTO convertirBeanADTO(Autor autor) {
		return AutorDTO.builder()
				.id(autor.getId())
				.nombre(autor.getNombre() + " " + autor.getApellidos()).build();
	}*/

	@Override
	public HrefEntityDTO  save(AutorDTORequest dto) {
		/*
		Autor autor = new Autor();
		autor.setNombre(dto.getNombre());
		autor.setApellidos(dto.getApellidos());
		autorMapper.toDTO(autorRepository.save(autor));
		*/
		
	
		//return autorMapper.toDTO(autor);
		
		
		//return autorMapper.toDTO(autorRepository.save(autor));
		//Autor autor = this.autorRepository.save(this.autorMapper.toBean(dto));
		
		
		autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos())
			.ifPresent(a -> {
	             throw new IllegalStateException("Autor con el nombre '" + dto.getNombre() + "' y apellidos '"+ dto.getApellidos()+ "' ya existe");
	         });
		
		
		Autor autor = this.autorRepository.save(this.autorMapper.toBean(dto));

		return libreriaUtil.createHrefFromResource(autor.getId(), LibreriaResource.AUTOR);
	}

	@Override
	public AutorDTO saveAutorSQL(AutorDTORequest dto) {
		// convertir a dto con el metodo convertirEntidadADto
		return autorMapper.toDTO(this.autorRepository.nuevoAutorSQL(dto.getNombre(), dto.getApellidos()));

	}
	@Transactional (readOnly = true)
	@Override
	public AutorDTO findById(int id) {
		// Buscar el autor por su ID en el repositorio de autores
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));
			
		return autorMapper.toDTO(autor);
		/*				
		// Verificar si el autor existe
		if (autor != null) {
			// Utilizar el método convertirEntidadADto para convertir el autor a un DTO
			AutorDTO autorDTO = autorMapper.toDTO(autor);

			// Devolver el objeto AutorDTO
			return autorDTO;
		} else {
			// Si el autor no existe, devolver null o manejar el caso
			
			return null;
		}*/
	}
	@Transactional (readOnly = true)
	@Override
	public List<AutorDTO> findAll() {
		List<Autor> lista = this.autorRepository.findAll();
		/*
		 * List<AutorDTO> listaDTO = new ArrayList<>(); for (Autor bean : lista) {
		 * AutorDTO autorDTO = new AutorDTO(); autorDTO.setId(bean.getId());
		 * autorDTO.setNombre(bean.getNombre() + " " + bean.getApellidos());
		 * listaDTO.add(autorDTO); } return listaDTO;
		 */
		return lista.stream()
	            .map(autor -> autorMapper.toDTO(autor))
	            .collect(Collectors.toList());
	}
	//@Transactional
	@Override
	public HrefEntityDTO update(AutorDTORequest dto, int id) {
	    Autor autor = autorRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));
	    autor.setId(id);

	    // Comprobar si existen duplicados antes de actualizar
	    List<Autor> autoresDuplicados = autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos())
	            .stream()
	            .filter(a -> a.getId() != id) // Excluir el autor actual de la comprobación de duplicados
	            .collect(Collectors.toList());

	    if (!autoresDuplicados.isEmpty()) {
	        throw new IllegalStateException("Autor con el nombre '" + dto.getNombre() + "' y apellidos '" + dto.getApellidos() + "' ya existe");
	    }

	    // Actualizar datos del autor
	    autor.setNombre(dto.getNombre());
	    autor.setApellidos(dto.getApellidos());

	    // Guardar el autor actualizado y devolver un enlace al recurso actualizado
	    Autor savedAutor = autorRepository.save(autor);
	    return libreriaUtil.createHrefFromResource(savedAutor.getId(), LibreriaResource.AUTOR);
	}

	/*
	 * FORMA ANTIGUA DE HACERLO
	public HrefEntityDTO update(AutorDTORequest dto, int id) {
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));		autor.setId(id);
		autor.setNombre(dto.getNombre());
		autor.setApellidos(dto.getApellidos());
		//return autorMapper.toDTO(autorRepository.save(autor));
		
		autorRepository.findByNombreAndApellidos(dto.getNombre(), dto.getApellidos())
		.ifPresent(a -> {
             throw new IllegalStateException("Autor con el nombre '" + dto.getNombre() + "' y apellidos '"+ dto.getApellidos()+ "' ya existe");
         });
	
		return libreriaUtil.createHrefFromResource(this.autorRepository.save(autor).getId(), LibreriaResource.AUTOR);
	} */

	@Override
	public HrefEntityDTO delete(int id) {
		Autor autor = autorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));	
		this.autorRepository.delete(autor);
		return libreriaUtil.createHrefFromResource(autor.getId(), LibreriaResource.AUTOR);

	}

	@Override
	public List<AutorDTO> buscarKeyWordSQL(String nombre) {
		List<Autor> listaAutores = this.autorRepository.buscarKeyWordSQL(nombre);
		return listaAutores.stream().map((bean) -> autorMapper.toDTO(bean)).collect(Collectors.toList());

	}

	@Override
	public int obtenerIdAutor(String nombreCompleto) {
		/*
		 * // Buscar el autor por su nombre y apellidos en el repositorio de autores
		 * Autor autor = autorRepository.findByNombreAndApellidos(nombre, apellidos);
		 * 
		 * // Verificar si el autor existe if (autor != null) { // Si existe, devolver
		 * su ID return autor.getId(); } else { // Si el autor no existe, devolver -1 o
		 * manejar el caso según tus necesidades return -1; }
		 */

		List<Autor> autor = autorRepository.buscarKeyWordSQL(nombreCompleto);

		// Verificar si la lista está vacía antes de intentar acceder al primer elemento
		if (autor.isEmpty()) {
			return -1;
		}

		// Obtener el ID del primer autor en la lista
		return autor.get(0).getId();
	}
	@Transactional (readOnly = true) // para que no se haga un commit, solo lectura , de manera especifica ("En caso de tener el general y el especifico, se toma el especifico, 
	@Override
	public Optional<AutorDTO> findByName(String nombre, String apellidos) {
	    Optional<Autor> autorOptional = autorRepository.findByNombreAndApellidos(nombre, apellidos);
	    
	    if (autorOptional.isPresent()) {
	        throw new IllegalStateException("Autor con el nombre '" + nombre + "' y apellidos '" + apellidos + "' ya existe");
	    }

	    return autorOptional.map(autorMapper::toDTO);
	}





}
