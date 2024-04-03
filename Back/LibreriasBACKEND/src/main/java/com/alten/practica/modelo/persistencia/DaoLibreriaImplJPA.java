package com.alten.practica.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.persistencia.interfaz.DaoLibreria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

/**
 * Esta clase implementa la interfaz DaoLibreria y proporciona una implementación JPA (Java Persistence API) 
 * para interactuar con la base de datos.
 */
public class DaoLibreriaImplJPA implements DaoLibreria {

    /**
     * EntityManagerFactory es una fábrica para EntityManager. Se crea una vez al inicio de la aplicación.
     */
    private static EntityManagerFactory factoria;

    /**
     * EntityManager es la interfaz principal en JPA. Se utiliza para crear consultas, 
     * recibir entidades y persistir entidades en la base de datos.
     */
    private EntityManager em;

    /**
     * Bloque estático para inicializar la factoría de EntityManager.
     */
    static {
        try {
            factoria = Persistence.createEntityManagerFactory("PracticasAlten");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Este método intenta abrir una conexión con la base de datos.
     * @return true si la conexión se abre correctamente, false si ocurre una excepción.
     */
    private boolean abrirConexion() {
        try {
            em = factoria.createEntityManager();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este método intenta cerrar la conexión con la base de datos.
     * @return true si la conexión se cierra correctamente, false si ocurre una excepción.
     */
    private boolean cerrarConexion() {
        try {
            if (em.isOpen()) {
                em.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este método agrega una nueva librería a la base de datos.
     * @param libreria la librería a agregar.
     * @return 1 si la librería se agrega correctamente, 0 si no se puede abrir la conexión o si ocurre una excepción.
     */
    @Override
    public int addLibreria(Libreria libreria) {
        if (!abrirConexion()) {
            return 0;
        }

        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(libreria);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return 0;
        } finally {
            cerrarConexion();
        }
        return 1;
    }

    /**
     * Este método elimina una librería de la base de datos.
     * @param id el id de la librería a eliminar.
     * @return 1 si la librería se elimina correctamente, 0 si no se puede abrir la conexión, la librería no existe o si ocurre una excepción.
     */
    @Override
    public int deleteLibreria(int id) {
        if (!abrirConexion()) {
            return 0;
        }

        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Libreria libreria = em.find(Libreria.class, id);
            if (libreria != null) {
                em.remove(libreria);
                et.commit();
            }
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return 0;
        } finally {
            cerrarConexion();
        }
        return 1;
    }

    /**
     * Este método actualiza una librería en la base de datos.
     * @param libreria la librería a actualizar.
     * @return 1 si la librería se actualiza correctamente, 0 si no se puede abrir la conexión o si ocurre una excepción.
     */
    @Override
    public int updateLibreria(Libreria libreria) {
        if (!abrirConexion()) {
            return 0;
        }

        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(libreria);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return 0;
        } finally {
            cerrarConexion();
        }
        return 1;
    }

    /**
     * Este método recupera una librería de la base de datos.
     * @param id el id de la librería a recuperar.
     * @return la librería si se encuentra, null si no se puede abrir la conexión, la librería no existe o si ocurre una excepción.
     */
    @Override
    public Libreria getLibreria(int id) {
        if (!abrirConexion()) {
            return null;
        }

        Libreria libreria = null;
        try {
            libreria = em.find(Libreria.class, id);
        } catch (Exception e) {
            // Consider logging this exception
        } finally {
            cerrarConexion();
        }
        return libreria;
    }

    /**
     * Este método recupera todas las librerías de la base de datos.
     * @return una lista de librerías, o null si no se puede abrir la conexión o si ocurre una excepción.
     */
    @Override
    public List<Libreria> getLibrerias() {
        if (!abrirConexion()) {
            return null;
        }

        List<Libreria> lista = new ArrayList<>();
        Query query = em.createQuery("SELECT l from Libreria l");
        try {
            lista = (List<Libreria>) query.getResultList();
        } catch (Exception e) {
            // Consider logging this exception
        } finally {
            cerrarConexion();
        }
        return lista;
    }
}