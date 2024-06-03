package com.alten.practica.modelo.entidad;

import java.util.List;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.service.encrypt.EncriptadorDesencriptadorAutomatico;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un cliente de la librería.
 *
 * Esta clase contiene información sobre un cliente, incluyendo su identificador
 * único, nombre, apellidos, correo electrónico, contraseña, nivel de permiso y
 * la lista de compras de libros asociadas.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_CLIENTE, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class Cliente {

	@Id
	@Column(name = "id_cliente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "nombre")
	private String nombre;
	
	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "apellidos")
	private String apellidos;
	
	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "email")
	private String email;
	
	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "password")
	private String password;
	
	@Convert(converter = EncriptadorDesencriptadorAutomatico.class)
	@Column(name = "nivel_permiso")
	private int nivelPermiso;

	@OneToMany(mappedBy = "cliente")
	private List<ClienteCompraLibro> listaCompras;

	@OneToOne(mappedBy = "cliente")
	private Usuario usuario;
}
