package com.alten.practica.util;
/**
 * Representa los recursos disponibles en una librería.
 * 
 * Esta enumeración define los tipos de recursos que pueden ser gestionados
 * dentro de una librería. Los valores definidos incluyen:
 * 
 * - AUTOR: Representa un autor de libros.
 * - LIBRERIA: Representa una librería física o virtual donde se venden libros.
 * - LIBRO: Representa un libro individual.
 * - CLIENTE: Representa un cliente que realiza compras en la librería.
 * - CLIENTECOMPRALIBRO: Representa una compra específica realizada por un cliente.
 * - LIBRERIALIBRO: Representa la asociación entre una librería y un libro, incluyendo la cantidad disponible y el precio.
 * 
 * Cada uno de estos valores puede ser utilizado para representar y controlar
 * los diferentes tipos de entidades en un sistema de manejo de una librería.
 */
public enum LibreriaResource {
	
	AUTOR, LIBRERIA, LIBRO, CLIENTE, CLIENTECOMPRALIBRO, LIBRERIALIBRO, USUARIO;

}
