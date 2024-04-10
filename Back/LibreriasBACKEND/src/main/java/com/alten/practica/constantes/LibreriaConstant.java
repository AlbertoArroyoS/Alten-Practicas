package com.alten.practica.constantes;

public class LibreriaConstant {

	// =============================================================================================
	// API VERSION
	// =============================================================================================
	public static final String API_VERSION = "/v1";

	// =============================================================================================
	// CONTEXTO DE LA APLICACION
	// =============================================================================================
	public static final String RESOURCE_GENERIC = API_VERSION + "/app-libreria";	
	
	// =============================================================================================
	// PATH DEL SISTEMA LIBRERIA. RECURSOS
	// =============================================================================================
	public static final String RESOURCE_LIBRERIAS = "/librerias";
	public static final String RESOURCE_AUTORES = "/autores";
	public static final String RESOURCE_LIBROS = "/libros";
	public static final String RESOURCE_LIBRERIA = "/libreria";
	public static final String RESOURCE_AUTOR = "/autor";
	public static final String RESOURCE_LIBRO = "/libro";
	public static final String RESOURCE_GENERIC_ID = "/{id}";
	
	
	// =============================================================================================
	// PATH DEL SISTEMA LIBRERIA FRONT END
	// =============================================================================================
	public static final String CLIENTE_FRONTEND = "*";
	
	private LibreriaConstant() {
		super();
	}
	
	// =============================================================================================
	// NOMBRE DE LAS TABLAS
	// =============================================================================================
	public static final String TABLA_NOMBRE_AUTORES = "autores";
	public static final String TABLA_NOMBRE_LIBROS = "libros";
	public static final String TABLA_NOMBRE_LIBRERIAS = "librerias";
	public static final String TABLA_NOMBRE_LIBRERIA_LIBRO = "libreria_libro";
	public static final String TABLA_NOMBRE_USUARIOS = "usuarios";
	
	// =============================================================================================
	// NOMBRE DE LOS ESQUEMAS
	// =============================================================================================
	public static final String ESQUEMA_NOMBRE = "dbo";
	
	// =============================================================================================
	// NOMBRE DE LOS PROCEDIMIENTOS ALMACENADOS (FUNCIONES DE SQL SERVER)
	// =============================================================================================
	public static final String SP_SEARCH_AUTOR = "select * from dbo.fn_buscar_autor(?)";
	public static final String SP_SEARCH_CONTAR_AUTORES = "select count(*) from dbo.fn_buscar_autor(?)";
	public static final String SP_NUEVO_AUTOR = "select * from dbo.fn_guardar_autor(?1,?2);";
	public static final String SP_SEARCH_LIBRO = "select * from dbo.fn_buscar_nombre_libro(?)";
	public static final String SP_SEARCH_CONTAR_LIBROS = "select count(*) from dbo.fn_buscar_nombre_libro(?)";
	public static final String SP_NUEVO_LIBRO = "select * from dbo.fn_guardar_libro(?1,?2,?3,?4,?5,?6,?7);";
}
