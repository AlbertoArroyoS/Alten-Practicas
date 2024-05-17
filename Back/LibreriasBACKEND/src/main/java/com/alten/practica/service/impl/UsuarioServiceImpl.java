package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
import com.alten.practica.modelo.entidad.mapper.IUsuarioAdminMapper;
import com.alten.practica.modelo.entidad.mapper.IUsuarioMapper;
import com.alten.practica.repository.IUsuarioRepository;
import com.alten.practica.service.IUsuarioService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;
/**
 * Clase que implementa la interfaz IAutorService
 * 
 * @see com.alten.practica.service.IAutorService
 */
@Transactional // Transacción para todos los métodos del servicio
@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	IUsuarioRepository usuarioRepository;
	@Autowired
	IUsuarioMapper usuarioMapper;
	@Autowired
	IUsuarioAdminMapper usuarioAdminMapper;
	@Autowired
	LibreriaUtil libreriaUtil;
	// Servicio para manejar la lógica de JWT
   // @Autowired
    //AuthServiceImpl authService;
    // Codificador de contraseñas para codificar contraseñas de usuario
    //PasswordEncoder passwordEncoder =authService.passwordEncoder;

	
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
	public UsuarioDTO findById(int id) {
		// Buscar el autor por su ID en el repositorio de autores
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El autor con id %s no existe", id)));

		return usuarioMapper.toDTO(usuario);
	}

	@Override
	public HrefEntityDTO update(UsuarioDTORequest dto, int id) {
	    // Buscar el usuario por su ID en el repositorio de usuarios
	    Usuario usuario = usuarioRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));

	    // Actualizar los campos del usuario con los valores del DTO
	    usuario.setUsername(dto.getUsername());
	    usuario.setEnabled(dto.getEnabled());
	    usuario.setRole(dto.getRole());
	    

	    // Guardar el usuario actualizado en el repositorio y crear un HrefEntityDTO
	    return libreriaUtil.createHrefFromResource(usuarioRepository.save(usuario).getId(), LibreriaResource.USUARIO);
	}

	@Override
	public HrefEntityDTO save(UsuarioDTORequest dto) {
		
		Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                //.password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .enabled((byte) 1)
                .build();

        // Guardar el Usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

		return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
	}


}
