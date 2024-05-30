package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.service.encriptacion.EncryptionService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una librería.
 *
 * Esta clase contiene información sobre una librería, incluyendo su
 * identificador único, nombre de la librería, nombre del dueño, dirección,
 * ciudad, nivel de permiso, correo electrónico y la lista de libros asociados a
 * la librería.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBRERIAS, schema = LibreriaConstant.ESQUEMA_NOMBRE_ENCRTIPTADA)
public class Libreria {

	@Id
	@Column(name = "id_libreria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre_libreria")
	private String nombreLibreria;
	@Column(name = "nombre_dueno")
	private String nombreDueno;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "ciudad")
	private String ciudad;
	
	@OneToMany(mappedBy = "libreria")
	private List<LibreriaLibro> libreriaLibros;
	
	@OneToOne(mappedBy = "libreria")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "libreria")
	private List<ClienteCompraLibro> listaComprasLibrerias;

    @Transient
    private static EncryptionService encryptionService;


    @PrePersist
    @PreUpdate
    public void encryptFields(EncryptionService encryptionService) {
        this.nombreLibreria = encryptionService.encrypt(this.nombreLibreria);
        this.nombreDueno = encryptionService.encrypt(this.nombreDueno);
        this.direccion = encryptionService.encrypt(this.direccion);
        this.ciudad = encryptionService.encrypt(this.ciudad);
    }

    @PostLoad
    public void decryptFields(EncryptionService encryptionService) {
        this.nombreLibreria = encryptionService.decrypt(this.nombreLibreria);
        this.nombreDueno = encryptionService.decrypt(this.nombreDueno);
        this.direccion = encryptionService.decrypt(this.direccion);
        this.ciudad = encryptionService.decrypt(this.ciudad);
    }
}