package com.alten.practica.modelo.persistencia.interfaz;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Libreria;


public interface DaoLibreria{
	
	//CRUD
	//Añadir Libreria
	public int addLibreria(Libreria libreria);
	//Borrar Libreria
	public int deleteLibreria(int id);
	//Actualizar Libreria
	public int updateLibreria(Libreria libreria);
	//Obtener Libreria por id
	public Libreria getLibreria(int id);
	//Obtener todas las librerias en una lista
	public List<Libreria> getLibrerias();
	
}
