package com.alten.practica.errorhandler;
//clase para gestionar de manera centralizada nuestros errores/excepciones

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alten.practica.constantes.LibreriaConstant;

import jakarta.annotation.Nullable;

/**
 * Controlador de excepciones global que maneja errores específicos para
 * aplicaciones REST, extendiendo ResponseEntityExceptionHandler.
 */
@RestControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Construye la entidad de respuesta basada en el DTO de error proporcionado.
	 * Asigna el estado HTTP al cuerpo de la respuesta basándose en el error
	 * proporcionado.
	 *
	 * @param errorDTO el DTO que contiene información sobre el error.
	 * @return una ResponseEntity con el estado HTTP y el cuerpo del error
	 *         configurados.
	 */
	private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
		return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
	}

	/**
	 * Maneja excepciones del tipo HttpStatusCodeException. Genera un DTO de error
	 * basado en el tipo de error HTTP (4xx o 5xx) y construye la respuesta HTTP
	 * correspondiente.
	 *
	 * @param ex la excepción capturada de tipo HttpStatusCodeException.
	 * @return una respuesta ResponseEntity conteniendo detalles del error.
	 */
	@ExceptionHandler(HttpStatusCodeException.class)
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

	/**
	 * Maneja específicamente EntityNotFoundException. Configura un DTO de error
	 * para casos donde el recurso solicitado no se encuentra.
	 *
	 * @param ex la excepción capturada de tipo EntityNotFoundException.
	 * @return una respuesta ResponseEntity con el error específico de no
	 *         encontrado.
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ErrorDTO sysceError = new ErrorDTO(HttpStatus.NOT_FOUND,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.NOT_FOUND);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	/**
	 * Maneja EntityUnprocessableException para errores de entidad no procesable.
	 * Crea un DTO de error para situaciones donde la solicitud contiene datos que
	 * no pueden ser procesados.
	 *
	 * @param ex la excepción capturada de tipo EntityUnprocessableException.
	 * @return una respuesta ResponseEntity indicando que la entidad no es
	 *         procesable.
	 */
	@ExceptionHandler(EntityUnprocessableException.class)
	protected ResponseEntity<Object> handleConflict(EntityUnprocessableException ex) {
		ErrorDTO sysceError = new ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.UNPROCESSABLE_ENTITY);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	/**
	 * Maneja errores generales del cliente capturados como
	 * EntityGenericClientException. Configura un DTO de error con detalles del
	 * error y suberrores si están disponibles.
	 *
	 * @param ex la excepción capturada de tipo EntityGenericClientException.
	 * @return una respuesta ResponseEntity con los detalles del error del cliente.
	 */
	@ExceptionHandler(EntityGenericClientException.class)
	protected ResponseEntity<Object> handleGenericClientException(EntityGenericClientException ex) {
		ErrorDTO sysceError = new ErrorDTO(ex.getHttpStatus(), LibreriaConstant.PREFIX_CLIENT_ERROR);
		sysceError.setMessage(ex.getMessage());
		sysceError.setSubErrors(ex.getSubErros());
		return buildResponseEntity(sysceError);
	}

	/**
	 * Maneja excepciones de autorización capturadas como
	 * EntityUnauthorizedException. Crea un DTO de error para casos de acceso no
	 * autorizado.
	 *
	 * @param ex la excepción capturada de tipo EntityUnauthorizedException.
	 * @return una respuesta ResponseEntity indicando acceso no autorizado.
	 */
	@ExceptionHandler(EntityUnauthorizedException.class)
	protected ResponseEntity<Object> handleEntityUnauthorized(EntityUnauthorizedException ex) {
		ErrorDTO sysceError = new ErrorDTO(HttpStatus.UNAUTHORIZED,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.UNAUTHORIZED);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	/**
	 * Maneja errores de servidor internos generalizados como
	 * EntityGenericServerException. Construye un DTO de error basado en el código
	 * de error proporcionado o usa un mensaje predeterminado si el código no está
	 * disponible.
	 *
	 * @param ex la excepción capturada de tipo EntityGenericServerException.
	 * @return una respuesta ResponseEntity con los detalles del error interno del
	 *         servidor.
	 */
	@ExceptionHandler(EntityGenericServerException.class)
	protected ResponseEntity<Object> handleGenericServerException(EntityGenericServerException ex) {
		ErrorDTO sysceError = null;
		if (ex.getCode() != null) {
			sysceError = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode());
		} else {
			sysceError = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR,
					LibreriaConstant.PREFIX_SERVER_ERROR + LibreriaConstant.INTERNAL_SERVER_ERROR);
		}
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	/**
	 * Maneja excepciones no especificadas capturadas como Exception. Proporciona
	 * una respuesta general para cualquier otro tipo de error no manejado
	 * específicamente.
	 *
	 * @param ex la excepción genérica capturada.
	 * @return una respuesta ResponseEntity con un mensaje de error genérico del
	 *         servidor.
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleError(Exception ex) {
		ErrorDTO sysceError = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR,
				LibreriaConstant.PREFIX_SERVER_ERROR + LibreriaConstant.INTERNAL_SERVER_ERROR);
		sysceError.setMessage("Error generico de servidor " + ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	// *********************************************************************************************************************************
	// Métodos sobreescritos de ResponseEntityExceptionHandler

	/**
	 * Maneja las excepciones {@link HttpMessageNotReadableException} cuando una
	 * solicitud HTTP contiene un cuerpo JSON malformado.
	 * 
	 * @param ex      La excepción de mensaje HTTP no legible.
	 * @param headers Los encabezados HTTP de la solicitud.
	 * @param status  El estado HTTP a usar para la respuesta (generalmente
	 *                BAD_REQUEST).
	 * @param request La información de la solicitud web.
	 * @return Una {@link ResponseEntity} que encapsula un {@link ErrorDTO} con
	 *         detalles del error.
	 */
	@Nullable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ErrorDTO(HttpStatus.BAD_REQUEST,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.BAD_REQUEST, error, ex));
	}

	/**
	 * Maneja las excepciones {@link MissingServletRequestParameterException} cuando
	 * falta un parámetro de solicitud requerido.
	 *
	 * @param ex      La excepción por falta de parámetro en la solicitud.
	 * @param headers Los encabezados HTTP de la solicitud.
	 * @param status  El estado HTTP a usar para la respuesta (generalmente
	 *                BAD_REQUEST).
	 * @param request La información de la solicitud web.
	 * @return Una {@link ResponseEntity} que encapsula un {@link ErrorDTO} con
	 *         detalles del error.
	 */
	@Nullable
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		return buildResponseEntity(new ErrorDTO(HttpStatus.BAD_REQUEST,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.BAD_REQUEST, error, ex));
	}

	/**
	 * Maneja las excepciones {@link MethodArgumentNotValidException} cuando un
	 * argumento de método anotado con @Valid falla la validación.
	 *
	 * @param ex      La excepción de argumento de método no válido.
	 * @param headers Los encabezados HTTP de la solicitud.
	 * @param status  El estado HTTP a usar para la respuesta (generalmente
	 *                BAD_REQUEST).
	 * @param request La información de la solicitud web.
	 * @return Una {@link ResponseEntity} que encapsula un {@link ErrorDTO} con
	 *         detalles del error y errores específicos de validación.
	 */
	@Nullable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST,
				LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.BAD_REQUEST);
		errorDTO.setMessage(ex.getBindingResult().getFieldError().toString());
		errorDTO.setSubErrors(fillValidationErrorsFrom(ex));
		return buildResponseEntity(errorDTO);
	}

	/**
	 * Extrae y crea una lista de {@link SubError} a partir de los errores de
	 * validación en una excepción {@link MethodArgumentNotValidException}.
	 * 
	 * @param argumentNotValid La excepción que contiene los resultados de
	 *                         validación.
	 * @return Una lista de {@link SubError} con detalles de cada error específico
	 *         de validación.
	 */
	protected List<SubError> fillValidationErrorsFrom(MethodArgumentNotValidException argumentNotValid) {
		List<SubError> subErrorCollection = new ArrayList<>();
		argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
			SubError sysceSubError = new ValidationError(objError.getObjectName(), objError.getField(),
					objError.getRejectedValue(), objError.getDefaultMessage());
			subErrorCollection.add(sysceSubError);
		});
		return subErrorCollection;
	}

}
