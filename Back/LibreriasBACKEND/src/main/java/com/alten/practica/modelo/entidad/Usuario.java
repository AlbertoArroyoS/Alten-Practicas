package com.alten.practica.modelo.entidad;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.service.encriptacion.EncryptionService;

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
@Table(name = "users", schema = LibreriaConstant.ESQUEMA_NOMBRE_ENCRTIPTADA, uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
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

    public void encryptFields(EncryptionService encryptionService) {
        this.username = encryptionService.encrypt(this.username);
        this.password = encryptionService.encrypt(this.password);
        this.role = encryptionService.encrypt(this.role);
        this.enabled = encryptionService.encryptByte(Byte.parseByte(this.enabled));
    }

    public void decryptFields(EncryptionService encryptionService) {
        this.username = encryptionService.decrypt(this.username);
        this.password = encryptionService.decrypt(this.password);
        this.role = encryptionService.decrypt(this.role);
        this.enabled = Byte.toString(encryptionService.decryptByte(this.enabled));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}