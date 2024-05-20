package com.alten.practica.controlador.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.practica.modelo.entidad.dto.AuthDTO;
import com.alten.practica.modelo.entidad.dto.request.LoginDTORequest;
import com.alten.practica.modelo.entidad.dto.request.RegisterDTORequest;
import com.alten.practica.modelo.entidad.dto.request.UsuarioDTORequest;
import com.alten.practica.service.impl.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

/*
 * Controlador REST para la gestión de autenticación y registro de usuarios.
 * Proporciona endpoints para iniciar sesión y registrarse.
 * 
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see lombok.RequiredArgsConstructor
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // Servicio de autenticación que maneja la lógica de login y registro
    private final AuthServiceImpl authService;
    

    /*
     * Endpoint para iniciar sesión.
     * 
     * @param request Objeto LoginDTORequest que contiene las credenciales de inicio de sesión
     * @return ResponseEntity con AuthDTO que contiene el token de autenticación
     */
    @PostMapping(value = "login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginDTORequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /*
     * Endpoint para registrar un nuevo usuario.
     * 
     * @param request Objeto RegisterDTORequest que contiene los datos de registro del usuario
     * @return ResponseEntity con AuthDTO que contiene el token de autenticación
     */
    @PostMapping(value = "register")
    public ResponseEntity<AuthDTO> register(@RequestBody RegisterDTORequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping(value = "register/admins")
    public ResponseEntity<AuthDTO> registerAdmin(@RequestBody UsuarioDTORequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }
}
