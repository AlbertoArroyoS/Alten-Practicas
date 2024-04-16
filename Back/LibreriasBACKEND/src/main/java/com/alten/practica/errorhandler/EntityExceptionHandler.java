package com.alten.practica.errorhandler;
//clase para gestionar de manera centralizada nuestros errores/excepciones

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alten.practica.constantes.LibreriaConstant;

@RestControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HttpStatusCodeException.class)//lanzamos esta clase cuando se produce una excepcion de tipo HttpStatusCodeException
	protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
		ErrorDTO sysceError = null;
		if (ex.getStatusCode().is4xxClientError()) {
			sysceError = new ErrorDTO((HttpStatus) ex.getStatusCode(), LibreriaConstant.PREFIX_CLIENT_ERROR);
		} else if (ex.getStatusCode().is5xxServerError()) {
			sysceError = new ErrorDTO((HttpStatus) ex.getStatusCode(), LibreriaConstant.PREFIX_SERVER_ERROR);
		}
		sysceError.setMessage(ex.getStatusText());
		return buildResponseEntity(sysceError);
	}
	
	//Metodo cuando se genere un not found exception
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ErrorDTO sysceError = new ErrorDTO(HttpStatus.NOT_FOUND,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.NOT_FOUND);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	

	//metodo para construir la respuesta
	private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
		return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
	}
}