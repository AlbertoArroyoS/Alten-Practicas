package com.alten.practica.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.Usuario;
import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.repository.IUsuarioRepository;
import com.alten.practica.service.jwt.JwtService;
import com.alten.practica.util.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	
	@Autowired
	private final IUsuarioRepository usuarioRepository;
	
	private final JwtService jwtService;
	
	public AuthDTO login(LoginDTORequest request) {
		//return AuthDTO.builder().token("token").build();
		return null;
	}

	public AuthDTO register(RegisterDTORequest request) {
		//return AuthDTO.builder().token("token").build();
		
		Libreria libreria = Libreria.builder()
				.nombreLibreria(request.getNombreLibreria()).build();
		
		
		Usuario usuario = Usuario.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.nombre(request.getNombre())
				.apellidos(request.getApellidos())
				.email(request.getEmail())
				.libreria(libreria)
				.role(Role.USER)
				.build();
		
		usuarioRepository.save(usuario);
		
		
		return AuthDTO.builder()
				.token(jwtService.getToken(usuario))
	            .build();
	}
	
	

}
