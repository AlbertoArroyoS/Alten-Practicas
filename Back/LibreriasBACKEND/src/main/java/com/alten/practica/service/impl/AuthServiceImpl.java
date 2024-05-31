package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.alten.practica.service.encrypt.DeterministicEncryptionService;
import com.alten.practica.service.jwt.JwtService;
import com.alten.practica.util.LibreriaResource;
import com.alten.practica.util.LibreriaUtil;
import com.alten.practica.util.Role;

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

	// Repositorio de usuarios para acceder a los datos de usuario
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Repositorio de clientes para acceder a los datos de cliente
	@Autowired
	private IClienteRepository clienteRepository;

	// Repositorio de librerías para acceder a los datos de librería
	@Autowired
	private ILibreriaRepository libreriaRepository;

	// Servicio para manejar la lógica de JWT
	@Autowired
	private JwtService jwtService;

	// Gestor de autenticación para autenticar usuarios
	private final AuthenticationManager authenticationManager;

	@Autowired
	LibreriaUtil libreriaUtil;

	@Autowired
	IUsuarioMapper usuarioMapper;
	@Autowired
	IUsuarioAdminMapper usuarioAdminMapper;
	
	@Autowired
	private DeterministicEncryptionService encryptionService;

	// Codificador de contraseñas para codificar contraseñas de usuario
	public final PasswordEncoder passwordEncoder;

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
		
		String usuarioEncriptado = encryptionService.encrypt(request.getUsername());
		
		//System.out.println("usuarioEncriptado: " + usuarioEncriptado);
		
		
		// Autentica al usuario utilizando las credenciales proporcionadas
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usuarioEncriptado, request.getPassword()));

		// Busca los detalles del usuario en el repositorio
		Usuario user = usuarioRepository.findByUsername(usuarioEncriptado).orElseThrow();

		// Genera un token de autenticación para el usuario
		String token = jwtService.getToken(user);

		// Devuelve el DTO de autenticación con el token
		String usernameDesencriptado = encryptionService.decrypt(user.getUsername());
		// Devuelve el DTO de autenticación con el token y los datos del usuario
		return AuthDTO.builder().token(token).idUsuario((long) user.getId()).username(usernameDesencriptado)
				.role(user.getRole().name()).build();
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
		// Crear y guardar un nuevo Cliente
		Cliente cliente = Cliente.builder().nombre(request.getNombre()).apellidos(request.getApellidos())
				.email(request.getEmail()).build();
		cliente = clienteRepository.save(cliente);

		// Crear y guardar una nueva Librería
		Libreria libreria = Libreria.builder().nombreLibreria(request.getNombreLibreria())
				.nombreDueno(request.getNombreDueno()).direccion(request.getDireccion()).ciudad(request.getCiudad())
				.build();
		libreria = libreriaRepository.save(libreria);

		// Crear un nuevo Usuario y asignar Cliente y Librería
		Usuario usuario = Usuario.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).cliente(cliente).libreria(libreria)
				.role(Role.USER).enabled((byte) 1).build();

		// Guardar el Usuario en la base de datos
		usuario = usuarioRepository.save(usuario);

		// Generar un token de autenticación para el usuario
		//String token = jwtService.getToken(usuario);

		// Devolver el DTO de autenticación con el token
		//return AuthDTO.builder().token(token).build();
		
		return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
	}

	@Override
	public HrefEntityDTO registerAdmin(UsuarioDTORequest dto) {
		
		String usuarioEncriptado = encryptionService.encrypt(dto.getUsername());
		String passwordEncoded = passwordEncoder.encode(dto.getPassword());
		String roleEncriptado = encryptionService.encrypt("ADMIN");

		Usuario usuario = Usuario.builder().username(usuarioEncriptado)
				.password(passwordEncoded).role(Role.ADMIN).enabled((byte) 1).build();

		// Guardar el Usuario en la base de datos
		usuario = usuarioRepository.save(usuario);

		// Generar un token de autenticación para el usuario
		//String token = jwtService.getToken(usuario);

		//return UsuarioAdminDTO.builder().build();
		
		return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
	}

	@Override
	public HrefEntityDTO updateAdmin(UsuarioDTORequest dto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HrefEntityDTO updateUser(UsuarioSimpleDTORequest request, int id) {
		// Crear y guardar un nuevo Cliente	
		// Crear un nuevo Usuario y asignar Cliente y Librería
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("El usuario con id %s no existe", id)));

		// Actualizar los campos del usuario con los valores del DTO
		//usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		// Guardar el Usuario en la base de datos
		usuario = usuarioRepository.save(usuario);

		// Generar un token de autenticación para el usuario
		String token = jwtService.getToken(usuario);

		// Devolver el DTO de autenticación 
		//return UsuarioDTO.builder().build();
		
		return libreriaUtil.createHrefFromResource(usuario.getId(), LibreriaResource.USUARIO);
	}

	@Transactional(readOnly = true)
	@Override
	public UsuarioDTO findById(int id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow();
		return usuarioMapper.toDTO(usuario);
	}
	@Transactional(readOnly = true)
	@Override
	public Page<UsuarioDTO> findAll(Pageable pageable) {
		Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
		return usuarios.map(usuario ->usuarioMapper.toDTO(usuario));
	}

}