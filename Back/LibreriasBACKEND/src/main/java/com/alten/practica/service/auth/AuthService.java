package com.alten.practica.service.auth;

import org.springframework.stereotype.Service;

import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	public AuthDTO login(LoginDTORequest request) {
		//return AuthDTO.builder().token("token").build();
		return null;
	}

	public AuthDTO register(RegisterDTORequest request) {
		//return AuthDTO.builder().token("token").build();
		return null;
	}

}
