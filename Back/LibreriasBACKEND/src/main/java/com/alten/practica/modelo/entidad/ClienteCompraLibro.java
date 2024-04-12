package com.alten.practica.modelo.entidad;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "cliente_compra_libro", schema = "dbo")
public class ClienteCompraLibro {
	
	 @Id
	    @Column(name = "id_compra")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @ManyToOne
	    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	    private Cliente cliente;

	    @ManyToOne
	    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
	    private Libro libro;
	    
	    @DateTimeFormat(pattern = "dd-MM-yyyy")
	    private Date fechaCompra;
	    
	    @Column(name = "precio")
	    private double precio;
	    
	  

}
