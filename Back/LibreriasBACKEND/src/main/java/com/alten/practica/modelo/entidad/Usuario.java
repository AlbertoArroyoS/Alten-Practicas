package com.alten.practica.modelo.entidad;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad de la base de datos que representa a un usuario.
 * Implementa la interfaz UserDetails para integrarse con Spring Security.
 * 
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see javax.persistence.Entity
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = LibreriaConstant.ESQUEMA_NOMBRE, uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled")
	private String enabled;

	@Column(name = "role")
	private String role;

	@OneToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name = "id_libreria", referencedColumnName = "id_libreria")
	private Libreria libreria;

	// Devolver una lista con el rol del usuario
	/*
	 * Método que devuelve una lista con los roles del usuario.
	 * 
	 * @return Lista de autoridades del usuario
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority((this.role)));
	}

	/*
	 * Indica si la cuenta del usuario ha expirado.
	 * 
	 * @return true siempre, ya que no se maneja la expiración de cuentas
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * Indica si la cuenta del usuario está bloqueada.
	 * 
	 * @return true siempre, ya que no se maneja el bloqueo de cuentas
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * Indica si las credenciales del usuario han expirado.
	 * 
	 * @return true siempre, ya que no se maneja la expiración de credenciales
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * Indica si el usuario está habilitado.
	 * 
	 * @return true si el usuario está habilitado
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
