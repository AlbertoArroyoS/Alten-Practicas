package com.alten.practica.constantes;

/*
 * Clase que contiene las constantes de la aplicación.
 */
public class LibreriaConstant {

	// =============================================================================================
	// CODIGO DE ERROR DEL CLIENTE Y SERVIDOR
	// =============================================================================================

	// Errores de cliente
	public static final String BAD_REQUEST = "400";
	public static final String UNAUTHORIZED = "401";
	public static final String FORBIDDEN = "403";
	public static final String NOT_FOUND = "404";
	public static final String METHOD_NOT_ALLOWED = "405";
	public static final String NOT_ACCEPTABLE = "406";
	public static final String CONFLICT = "409";
	public static final String UNPROCESSABLE_ENTITY = "422";
	public static final String EXPECTATION_FAILED = "417";
	public static final Float NR_VUELTO_DEFAULT = (float) 0;

	// Errores de servidor
	public static final String INTERNAL_SERVER_ERROR = "500";
	public static final String NOT_IMPLEMENTED = "501";
	public static final String BAD_GATEWAY = "503";
	public static final String SERVICE_UNAVAILABLE = "504";
	public static final String GATEWAY_TIMEOUT = "505";
	public static final String NOT_VALIDATED = "506";

	// prefijos identificar si es un error de cliente o de servidor
	public static final String PREFIX_SERVER_ERROR = "SRV";
	public static final String PREFIX_CLIENT_ERROR = "CLI";

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
	public static final String RESOURCE_CLIENTES = "/clientes";
	public static final String RESOURCE_CLIENTE = "/cliente";
	public static final String RESOURCE_LIBRERIA_LIBROS = "/libreria_libros";
	public static final String RESOURCE_LIBRERIA_LIBRO = "/libreria_libro";
	public static final String RESOURCE_CLIENTE_COMPRA_LIBROS = "/compras";
	public static final String RESOURCE_CLIENTE_COMPRA_LIBRO = "/compra";
	public static final String RESOURCE_GENERIC_ID = "/{id}";
	public static final String RESOURCE_AUTOR_ID = "/{authorId}";
	public static final String RESOURCE_LIBRERIA_ID = "/{idLibreria}";
	public static final String RESOURCE_USUARIOS = "/usuarios";
	public static final String RESOURCE_USUARIO = "/usuario";

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
	public static final String TABLA_NOMBRE_LIBRERIA_CON_LIBROS = "libreria_libro";
	public static final String TABLA_NOMBRE_CLIENTE_COMPRA_LIBRO = "cliente_compra_libro";
	public static final String TABLA_NOMBRE_CLIENTE = "clientes";

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
	public static final String SP_NUEVO_LIBRO = "select * from dbo.fn_guardar_libro(?1,?2,?3,?4,?5,?6,?7,?8)";
}
