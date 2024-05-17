package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alten.practica.modelo.entidad.Cliente;
import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.repository.IClienteRepository;
import com.alten.practica.repository.ILibreriaRepository;
import com.alten.practica.repository.IUsuarioRepository;
import com.alten.practica.service.IAuthService;
import com.alten.practica.service.jwt.JwtService;
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

    // Codificador de contraseñas para codificar contraseñas de usuario
    public final PasswordEncoder passwordEncoder;

    /*
     * Autentica un usuario.
     * 
     * @param request Objeto LoginDTORequest que contiene las credenciales de inicio de sesión
     * @return AuthDTO que contiene el token de autenticación
     */
    @Override
    public AuthDTO login(LoginDTORequest request) {
        // Autentica al usuario utilizando las credenciales proporcionadas
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Busca los detalles del usuario en el repositorio
        Usuario user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();

        // Genera un token de autenticación para el usuario
        String token = jwtService.getToken(user);

        // Devuelve el DTO de autenticación con el token
     // Devuelve el DTO de autenticación con el token y los datos del usuario
        return AuthDTO.builder()
            .token(token)
            .idUsuario((long) user.getId())
            .username(user.getUsername())
            .build();
    }

    /*
     * Registra un nuevo usuario.
     * 
     * @param request Objeto RegisterDTORequest que contiene los datos de registro del usuario
     * @return AuthDTO que contiene el token de autenticación
     */
    @Override
    public AuthDTO register(RegisterDTORequest request) {
        // Crear y guardar un nuevo Cliente
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .build();
        cliente = clienteRepository.save(cliente);

        // Crear y guardar una nueva Librería
        Libreria libreria = Libreria.builder()
                .nombreLibreria(request.getNombreLibreria())
                .nombreDueno(request.getNombreDueno())
                .direccion(request.getDireccion())
                .ciudad(request.getCiudad())
                .email(request.getEmail())
                .build();
        libreria = libreriaRepository.save(libreria);

        // Crear un nuevo Usuario y asignar Cliente y Librería
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .cliente(cliente)
                .libreria(libreria)
                .role(Role.USER)
                .enabled((byte) 1)
                .build();

        // Guardar el Usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Generar un token de autenticación para el usuario
        String token = jwtService.getToken(usuario);

        // Devolver el DTO de autenticación con el token
        return AuthDTO.builder().token(token).build();
    }
}