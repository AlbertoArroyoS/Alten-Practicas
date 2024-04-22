package com.alten.practica.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/*
 * Clase que representa un objeto de transferencia de datos (DTO) para
 * representar un enlace a un recurso.
 * Devuelve el id del recurso y la URL del recurso.
 */	

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HrefEntityDTO {
    private Object id;
    private String href;
}