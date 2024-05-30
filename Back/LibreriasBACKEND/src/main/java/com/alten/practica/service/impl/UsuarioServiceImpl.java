package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.alten.practica.service.encrypt.DeterministicEncryptionService;
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
	
	@Autowired
	private DeterministicEncryptionService encryptionService;
	
	
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
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        // Descifrar datos sensibles antes de enviarlos al cliente
        usuario.setUsername(encryptionService.decrypt(usuario.getUsername()));
        usuario.setRole(encryptionService.decrypt(usuario.getRole()));
        if (usuario.getEnabled() != null) {
            usuario.setEnabled(encryptionService.decrypt(usuario.getEnabled()));
        }

        // Asumiendo que la entidad Usuario contiene relaciones directas o métodos para obtener los datos de Cliente y Librería
        if (usuario.getCliente() != null) {
            usuario.getCliente().setNombre(encryptionService.decrypt(usuario.getCliente().getNombre()));
            usuario.getCliente().setApellidos(encryptionService.decrypt(usuario.getCliente().getApellidos()));
            usuario.getCliente().setEmail(encryptionService.decrypt(usuario.getCliente().getEmail()));
        }

        if (usuario.getLibreria() != null) {
            usuario.getLibreria().setNombreLibreria(encryptionService.decrypt(usuario.getLibreria().getNombreLibreria()));
            usuario.getLibreria().setNombreDueno(encryptionService.decrypt(usuario.getLibreria().getNombreDueno()));
            usuario.getLibreria().setDireccion(encryptionService.decrypt(usuario.getLibreria().getDireccion()));
            usuario.getLibreria().setCiudad(encryptionService.decrypt(usuario.getLibreria().getCiudad()));
        }

        // Mapear la entidad Usuario al DTO UsuarioDTO
        return usuarioMapper.toDTO(usuario);
    }

	@Override
    public HrefEntityDTO update(UsuarioDTORequest dto, int id) {
		return null;
		
		/*
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        usuario.setUsername(encryptionService.encrypt(dto.getUsername()));
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        usuario.setRole(encryptionService.encrypt(dto.getRole()));
        usuario.setEnabled(dto.getEnabled());
        usuario = usuarioRepository.save(usuario);
        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);*/
    }

	@Override
    public HrefEntityDTO save(UsuarioDTORequest dto) {
		return null;
		
		/*
        Usuario usuario = Usuario.builder()
            .username(encryptionService.encrypt(dto.getUsername()))
            .password(passwordEncoder.encode(dto.getPassword()))
            .role(encryptionService.encrypt(dto.getRole()))
            .enabled(dto.getEnabled())
            .build();
        usuario = usuarioRepository.save(usuario);
        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);*/
    }

	@Override
    public Page<UsuarioDTO> findAllUser(Pageable pageable) {
        Page<Usuario> usuarioPage = usuarioRepository.findAll(pageable);
        return usuarioPage.map(usuario -> {
            usuario.setUsername(encryptionService.decrypt(usuario.getUsername()));
            return usuarioMapper.toDTO(usuario);
        });
    }

	@Override
	public Page<UsuarioAdminDTO> findAllAdmin(Pageable pageable) {
	    Page<Usuario> usuarioPage = usuarioRepository.findAdmins(pageable);
	    return usuarioPage.map(usuario -> {
	        // Descifrar el nombre de usuario y otros campos necesarios antes de mapear a DTO
	        usuario.setUsername(encryptionService.decrypt(usuario.getUsername()));
	        usuario.setRole(encryptionService.decrypt(usuario.getRole()));
	        // Si otros campos como 'enabled' también están cifrados, deben ser descifrados de manera similar
	        if (usuario.getEnabled() != null) {
	            usuario.setEnabled(encryptionService.decrypt(usuario.getEnabled()));
	        }
	        return usuarioAdminMapper.toDTO(usuario);
	    });
	}



}
