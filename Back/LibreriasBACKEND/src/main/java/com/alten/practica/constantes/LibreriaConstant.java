package com.alten.practica.constantes;

public class LibreriaConstant {

	//API version
	public static final String API_VERSION = "/v1";

	//contexto de la aplicacion
	public static final String RESOURCE_GENERIC = API_VERSION + "/app-libreria";	
	//Recursos, path del sistema libreria
	public static final String RESOURCE_LIBRERIAS = "/librerias";	
	public static final String RESOURCE_LIBRERIA = "/libreria";
	public static final String RESOURCE_GENERIC_ID = "/{id}";
	
	public static final String CLIENTE_FRONTEND = "*";
	
	private LibreriaConstant() {
		super();
	}
	
	//NOMBRES TABLAS
	public static final String TABLA_NOMBRE_AUTORES = "autores";
	public static final String TABLA_NOMBRE_LIBROS = "libros";
	public static final String TABLA_NOMBRE_LIBRERIAS = "librerias";
	public static final String TABLA_NOMBRE_LIBRERIA_LIBRO = "libreria_libro";
	public static final String TABLA_NOMBRE_USUARIOS = "usuarios";
	
	
	//ESQUEMA	
	public static final String ESQUEMA_NOMBRE = "dbo";
}
