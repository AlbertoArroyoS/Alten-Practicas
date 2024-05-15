package com.alten.practica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{
	
	
	@Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ILibreriaRepository libreriaRepository;

    @Autowired
    private JwtService jwtService; // Servicio para generar el token
    
    private final PasswordEncoder passwordEncoder;
	
	@Override
	public AuthDTO login(LoginDTORequest request) {
		//return AuthDTO.builder().token("token").build();
		return null;
	}
	
	@Override
	public AuthDTO register(RegisterDTORequest request) {
        // Crear y guardar un nuevo Cliente
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .build();
        cliente = clienteRepository.save(cliente);
        
        // Crear y guardar una nueva Libreria
        Libreria libreria = Libreria.builder()
                .nombreLibreria(request.getNombreLibreria())
                .nombreDueno(request.getNombreDueno())
                .direccion(request.getDireccion())
                .ciudad(request.getCiudad())
                .email(request.getEmail())
                .build();
        libreria = libreriaRepository.save(libreria);

        // Crear un nuevo Usuario y asignar Cliente y Libreria
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .cliente(cliente)
                .libreria(libreria)
                .role(Role.USER)
                .build();
        
        // Guardar el Usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Generar un token de autenticación para el usuario
        String token = jwtService.getToken(usuario);

        // Devolver el DTO de autenticación con el token
        return AuthDTO.builder()
                .token(token)
                .build();
    }
	
	

}
