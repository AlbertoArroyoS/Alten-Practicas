package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.UsuarioAdminDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
import com.alten.practica.modelo.entidad.mapper.IUsuarioAdminMapper;
import com.alten.practica.modelo.entidad.mapper.IUsuarioMapper;
import com.alten.practica.repository.IUsuarioRepository;
import com.alten.practica.service.IUsuarioService;
import com.alten.practica.service.encriptacion.EncryptionService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

/**
 * Clase que implementa la interfaz IAutorService
 * 
 * @see com.alten.practica.service.IAutorService
 */
@Transactional // Transacción para todos los métodos del servicio
@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioRepository usuarioRepository;

	@Autowired
	IUsuarioMapper usuarioMapper;

	@Autowired
	IUsuarioAdminMapper usuarioAdminMapper;

	@Autowired
	LibreriaUtil libreriaUtil;

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	PasswordEncoder passwordEncoder;

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
        // Buscar el usuario por su ID en el repositorio de usuarios
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));
        usuario.setEncryptionService(encryptionService);
        usuario.decryptFields();
        return usuarioMapper.toDTO(usuario);
    }

	/**
	 * Actualiza un usuario existente en la base de datos.
	 * 
	 * @param dto Los nuevos datos del usuario.
	 * @param id  El ID del usuario a actualizar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         usuario actualizado.
	 * @throws EntityNotFoundException Si no se encuentra ningún usuario con el ID
	 *                                 proporcionado.
	 */
	@Override
	public HrefEntityDTO update(UsuarioDTORequest dto, int id) {
        // Buscar el usuario por su ID en el repositorio de usuarios
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));

        // Actualizar los campos del usuario con los valores del DTO
        usuarioMapper.updateUsuarioFromDto(dto, usuario);
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        usuario.setEncryptionService(encryptionService);
        usuario.encryptFields();

        // Guardar el usuario actualizado en el repositorio y crear un HrefEntityDTO
        return libreriaUtil.createHrefFromResource(usuarioRepository.save(usuario).getId(), LibreriaResource.USUARIO);
    }

	/**
	 * Guarda un nuevo usuario en la base de datos.
	 * 
	 * @param dto Los datos del usuario a guardar.
	 * @return Un objeto {@code HrefEntityDTO} que contiene un enlace al recurso del
	 *         usuario creado.
	 */
	@Override
	public HrefEntityDTO save(UsuarioDTORequest dto) {
        Usuario usuario = usuarioMapper.toBean(dto);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEncryptionService(encryptionService);
        usuario.encryptFields();

        // Guardar el Usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
    }

	/**
	 * Encuentra todos los usuarios con paginación.
	 * 
	 * @param pageable Objeto Pageable que contiene los parámetros de paginación.
	 * @return Page<UsuarioDTO> que contiene los datos de los usuarios encontrados.
	 */
	/**
	 * Encuentra todos los usuarios con paginación.
	 * 
	 * @param pageable Objeto Pageable que contiene los parámetros de paginación.
	 * @return Page<UsuarioDTO> que contiene los datos de los usuarios encontrados.
	 */
	@Transactional(readOnly = true)
	@Override
	 public Page<UsuarioDTO> findAllUser(Pageable pageable) {
        Page<Usuario> usuario = usuarioRepository.findUsers(pageable);
        usuario.forEach(u -> {
            u.setEncryptionService(encryptionService);
            u.decryptFields();
        });
        return usuario.map(usuarioMapper::toDTO);
    }

	/**
	 * Encuentra todos los administradores con paginación.
	 * 
	 * @param pageable Objeto Pageable que contiene los parámetros de paginación.
	 * @return Page<UsuarioAdminDTO> que contiene los datos de los administradores
	 *         encontrados.
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<UsuarioAdminDTO> findAllAdmin(Pageable pageable) {
        Page<Usuario> usuario = usuarioRepository.findAdmins(pageable);
        usuario.forEach(u -> {
            u.setEncryptionService(encryptionService);
            u.decryptFields();
        });
        return usuario.map(usuarioAdminMapper::toDTO);
    }
}
