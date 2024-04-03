package com.alten.practica.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import com.alten.practica.modelo.entidad.Libreria;
import com.alten.practica.modelo.persistencia.interfaz.DaoLibreria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class DaoLibreriaImplJPA implements DaoLibreria {
    
    private static EntityManagerFactory factoria;
    private EntityManager em;

    static {
        try {
            factoria = Persistence.createEntityManagerFactory("PracticasAlten");
        } catch (Exception e) {
            // Consider logging this exception
            throw new ExceptionInInitializerError(e);
        }
    }

    private boolean abrirConexion() {
        try {
            em = factoria.createEntityManager();
            return true;
        } catch (Exception e) {
            // Consider logging this exception
            return false;
        }
    }

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
            // Consider logging this exception
            return 0;
        } finally {
            cerrarConexion();
        }
        return 1;
    }

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
            // Consider logging this exception
            return 0;
        } finally {
            cerrarConexion();
        }
        return 1;
    }

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
