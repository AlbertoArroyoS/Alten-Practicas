package com.alten.practica.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alten.practica.errorhandler.EntityNotFoundException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.UsuarioDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
import com.alten.practica.modelo.entidad.dto.request.UsuarioSimpleDTORequest;
import com.alten.practica.modelo.entidad.mapper.IUsuarioAdminMapper;
import com.alten.practica.modelo.entidad.mapper.IUsuarioMapper;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.repository.IUsuarioRepository;
import com.alten.practica.service.IAuthService;
import com.alten.practica.service.encriptacion.DeterministicEncryptionService;
import com.alten.practica.service.jwt.JwtService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;

import lombok.RequiredArgsConstructor;

/*
 * Implementación del servicio de autenticación.
 * Maneja la lógica para registrar y autenticar usuarios.
 * 
 * @see org.springframework.stereotype.Service
 * @see lombok.RequiredArgsConstructor
 * @see IAuthService
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	// Repositorio de clientes para acceder a los datos de cliente
	@Autowired
	private IClienteRepository clienteRepository;

	// Repositorio de librerías para acceder a los datos de librería
	@Autowired
	private ILibreriaRepository libreriaRepository;

	// Servicio para manejar la lógica de JWT
	// Servicio para manejar la lógica de JWT
	@Autowired
	private JwtService jwtService;

	// Servicio para manejar la lógica de encriptación
	@Autowired
	private DeterministicEncryptionService encryptionService;

	// Gestor de autenticación para autenticar usuarios
	private final AuthenticationManager authenticationManager;

	@Autowired
	LibreriaUtil libreriaUtil;

	@Autowired
	IUsuarioMapper usuarioMapper;

	@Autowired
	IUsuarioAdminMapper usuarioAdminMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * Autentica un usuario.
	 * 
	 * @param request Objeto LoginDTORequest que contiene las credenciales de inicio
	 * de sesión
	 * 
	 * @return AuthDTO que contiene el token de autenticación
	 */
	@Override
	public AuthDTO login(LoginDTORequest request) {
	    // Cifra el nombre de usuario para buscarlo en la base de datos
	    String encryptedUsername = encryptionService.encrypt(request.getUsername());
	    System.out.println("encryptedUsername: " + encryptedUsername);
	    
	    // Busca el usuario cifrado en la base de datos
	    Optional<Usuario> userOptional = usuarioRepository.findByUsername(encryptedUsername);
	    
	    if (!userOptional.isPresent()) {
	        throw new BadCredentialsException("Invalid username or password");
	    }

	    Usuario user = userOptional.get();

	    // Usa el authenticationManager para autenticar al usuario
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(encryptedUsername, request.getPassword()));

	    // Si la autenticación es exitosa, descifra los datos del usuario
	    user.setUsername(encryptionService.decrypt(user.getUsername()));
	    user.setRole(encryptionService.decrypt(user.getRole()));
	    user.setEnabled(encryptionService.decrypt(user.getEnabled()));

	    // Genera el token JWT
	    String token = jwtService.getToken(user);

	    // Devuelve el DTO de autenticación con el token
	    return AuthDTO.builder()
	            .token(token)
	            .idUsuario((long) user.getId())
	            .username(user.getUsername())
	            .role(user.getRole())
	            .build();
	}




	/*
	 * Registra un nuevo usuario.
	 * 
	 * @param request Objeto RegisterDTORequest que contiene los datos de registro
	 * del usuario
	 * 
	 * @return AuthDTO que contiene el token de autenticación
	 */
	@Override
    public HrefEntityDTO register(RegisterDTORequest request) {
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .build();
        // Assuming Cliente has encryption methods if needed
        cliente = clienteRepository.save(cliente);

        Libreria libreria = Libreria.builder()
                .nombreLibreria(request.getNombreLibreria())
                .nombreDueno(request.getNombreDueno())
                .direccion(request.getDireccion())
                .ciudad(request.getCiudad())
                .build();
        // Assuming Libreria has encryption methods if needed
        libreria = libreriaRepository.save(libreria);

        Usuario usuario = Usuario.builder()
                .username(encryptionService.encrypt(request.getUsername()))
                .password(passwordEncoder.encode(request.getPassword()))
                .cliente(cliente)
                .libreria(libreria)
                .role(encryptionService.encrypt("USER"))
                .enabled(encryptionService.encrypt("1"))
                .build();
        usuario = usuarioRepository.save(usuario);

        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
    }


	@Override
    public HrefEntityDTO registerAdmin(UsuarioDTORequest dto) {
        Usuario usuario = Usuario.builder()
                .username(encryptionService.encrypt(dto.getUsername()))
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(encryptionService.encrypt("ADMIN"))
                .enabled(encryptionService.encrypt("1"))
                .build();
        usuario = usuarioRepository.save(usuario);
        System.out.println("usuario: " + usuario.getUsername());

        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
    }

	@Override
    public HrefEntityDTO updateAdmin(UsuarioDTORequest dto, int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));
        usuario.setUsername(encryptionService.encrypt(dto.getUsername()));
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRole(encryptionService.encrypt("ADMIN"));
        usuario.setEnabled(encryptionService.encrypt("1"));
        usuario = usuarioRepository.save(usuario);

        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
    }

	@Override
    public HrefEntityDTO updateUser(UsuarioSimpleDTORequest request, int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario = usuarioRepository.save(usuario);

        return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
    }

	@Transactional(readOnly = true)
    @Override
    public UsuarioDTO findById(int id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setUsername(encryptionService.decrypt(usuario.getUsername()));
        usuario.setRole(encryptionService.decrypt(usuario.getRole()));
        usuario.setEnabled(encryptionService.decrypt(usuario.getEnabled()));
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        usuarios.forEach(usuario -> {
            usuario.setUsername(encryptionService.decrypt(usuario.getUsername()));
            usuario.setRole(encryptionService.decrypt(usuario.getRole()));
            usuario.setEnabled(encryptionService.decrypt(usuario.getEnabled()));
        });
        return usuarios.map(usuarioMapper::toDTO);
    }
}
