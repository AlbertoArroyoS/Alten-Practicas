package com.alten.practica.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Clase que representa un error de validación.
 *
 * Esta clase proporciona información detallada sobre un error de validación,
 * incluyendo el objeto afectado, el campo específico, el valor rechazado y
 * el mensaje de error asociado.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError extends SubError{
	
	//objeto
	private String object;
	//campo
	private String field;
	//valor de cada campo
	private Object rejectedValue;
	//mensaje
	private String message;

	public ValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
