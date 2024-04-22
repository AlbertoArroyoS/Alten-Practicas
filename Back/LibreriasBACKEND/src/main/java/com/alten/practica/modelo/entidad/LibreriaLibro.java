package com.alten.practica.modelo.entidad;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = LibreriaConstant.TABLA_NOMBRE_LIBRERIA_CON_LIBROS, schema = LibreriaConstant.ESQUEMA_NOMBRE)
public class LibreriaLibro {

    @Id
    @Column(name = "id_libreria_libro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_libreria", referencedColumnName = "id_libreria")
    private Libreria libreria;

    @ManyToOne
    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
    private Libro libro;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio")
    private double precio;
    
    @Column(name = "edicion")
    private int edicion;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;
    


}

