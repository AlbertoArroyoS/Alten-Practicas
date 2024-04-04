package com.alten.practica.test;

import java.util.List;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.entidad.Libro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class _01PruebasBBDD {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PracticasAlten");
	    EntityManager em = emf.createEntityManager();

	    // ... (código para dar de alta autores, editoriales y libros)

	    // Mostrar todos los libros con su editorial y autor
	    em.getTransaction().begin();
	    System.out.println("-----------LIBRERIAS---------------");
		 Query query = em.createQuery("Select l from Libreria l");
	        List<Libreria> librerias = query.getResultList();
		// TODO Auto-generated method stub
		for (Libreria l : librerias) {
			System.out.println(l);
			
		}
		System.out.println("-----------LIBROS---------------");
		 Query query2 = em.createQuery("Select l from Libro l");
	        List<Libro> libros = query.getResultList();
		// TODO Auto-generated method stub
		for (Libreria l : librerias) {
			System.out.println(l);
			
		}
		em.getTransaction().commit();
		em.close();
		emf.close();

	        
	}

}
