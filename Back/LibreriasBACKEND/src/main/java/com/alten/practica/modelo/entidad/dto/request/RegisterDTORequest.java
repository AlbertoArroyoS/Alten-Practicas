package com.alten.practica.modelo.entidad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTORequest {
	
    String username;
    String password;
    String firstname;
    String lastname;
    String country; 
}