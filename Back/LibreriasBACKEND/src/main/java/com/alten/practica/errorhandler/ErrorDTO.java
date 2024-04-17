package com.alten.practica.errorhandler;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Clase para enviarle la respuesta al cliente

@Getter
@Setter
@ToString
public class ErrorDTO {
	
	private String code;
	private HttpStatusCode httpStatus;
	private Date timestamp;
	private String message;
	private String debugMessage;
	private List<SubError> subErrors;
	
	private ErrorDTO() {
		timestamp = new Date();
	}


	public ErrorDTO(HttpStatusCode httpStatusCode, String code) {
		this();
		this.code = code;
		this.httpStatus = httpStatusCode;
	}
	
	
	public ErrorDTO(HttpStatusCode httpStatus, String code, Throwable e) {
		this();
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = "Unexpected error";
		this.debugMessage = e.getLocalizedMessage();
	}

	public ErrorDTO(HttpStatusCode httpStatus, String code, String message, Throwable e) {
		this();
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
		this.debugMessage = e.getLocalizedMessage();
	}

}
