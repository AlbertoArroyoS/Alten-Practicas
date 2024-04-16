package com.alten.practica.errorhandler;
//clase para gestionar de manera centralizada nuestros errores/excepciones

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alten.practica.constantes.LibreriaConstant;
/**
 * Controlador de excepciones global que maneja errores específicos para aplicaciones REST, extendiendo ResponseEntityExceptionHandler.
 */
@RestControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Maneja excepciones del tipo HttpStatusCodeException.
     * Genera un DTO de error basado en el tipo de error HTTP (4xx o 5xx) y construye la respuesta HTTP correspondiente.
     *
     * @param ex la excepción capturada de tipo HttpStatusCodeException
     * @return una respuesta ResponseEntity conteniendo detalles del error
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
     * Maneja EntityNotFoundException específicamente.
     * Configura un DTO de error para casos donde el recurso solicitado no se encuentra.
     *
     * @param ex la excepción capturada de tipo EntityNotFoundException
     * @return una respuesta ResponseEntity con el error específico de no encontrado
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
     * Crea un DTO de error para situaciones donde la solicitud contiene datos que no pueden ser procesados.
     *
     * @param ex la excepción capturada de tipo EntityUnprocessableException
     * @return una respuesta ResponseEntity indicando que la entidad no es procesable
     */
    @ExceptionHandler(EntityUnprocessableException.class)
    protected ResponseEntity<Object> handleConflict(EntityUnprocessableException ex) {
        ErrorDTO sysceError = new ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY,
                LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.UNPROCESSABLE_ENTITY);
        sysceError.setMessage(ex.getMessage());
        return buildResponseEntity(sysceError);
    }

    /**
     * Maneja errores generales del cliente capturados como EntityGenericClientException.
     * Configura un DTO de error con detalles del error y suberrores si están disponibles.
     *
     * @param ex la excepción capturada de tipo EntityGenericClientException
     * @return una respuesta ResponseEntity con los detalles del error del cliente
     */
    @ExceptionHandler(EntityGenericClientException.class)
    protected ResponseEntity<Object> handleGenericClientException(EntityGenericClientException ex) {
        ErrorDTO sysceError = new ErrorDTO(ex.getHttpStatus(), LibreriaConstant.PREFIX_CLIENT_ERROR);
        sysceError.setMessage(ex.getMessage());
        sysceError.setSubErrors(ex.getSubErros());
        return buildResponseEntity(sysceError);
    }

    /**
     * Maneja excepciones de autorización capturadas como EntityUnauthorizedException, cuando se necesita autorizacion.
     * Crea un DTO de error para casos de acceso no autorizado.
     *
     * @param ex la excepción capturada de tipo EntityUnauthorizedException
     * @return una respuesta ResponseEntity indicando acceso no autorizado
     */
    @ExceptionHandler(EntityUnauthorizedException.class)
    protected ResponseEntity<Object> handleEntityUnauthorized(EntityUnauthorizedException ex) {
        ErrorDTO sysceError = new ErrorDTO(HttpStatus.UNAUTHORIZED,
                LibreriaConstant.PREFIX_CLIENT_ERROR + LibreriaConstant.UNAUTHORIZED);
        sysceError.setMessage(ex.getMessage());
        return buildResponseEntity(sysceError);
    }

    /**
     * Maneja errores de servidor internos generalizados como EntityGenericServerException. 500s
     * Construye un DTO de error basado en el código de error proporcionado o usa un mensaje predeterminado si el código no está disponible.
     *
     * @param ex la excepción capturada de tipo EntityGenericServerException
     * @return una respuesta ResponseEntity con los detalles del error interno del servidor
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
     * Maneja excepciones no especificadas capturadas como Exception.
     * Proporciona una respuesta general para cualquier otro tipo de error no manejado específicamente.
     *
     * @param ex la excepción genérica capturada
     * @return una respuesta ResponseEntity con un mensaje de error genérico del servidor
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleError(Exception ex) {
        ErrorDTO sysceError = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR,
                LibreriaConstant.PREFIX_SERVER_ERROR + LibreriaConstant.INTERNAL_SERVER_ERROR);
        sysceError.setMessage("Error generico de servidor " + ex.getMessage());
        return buildResponseEntity(sysceError);
    }
    
    /**
     * Construye la entidad de respuesta basada en el DTO de error proporcionado.
     * Asigna el estado HTTP al cuerpo de la respuesta basándose en el error proporcionado.
     *
     * @param errorDTO el DTO que contiene información sobre el error
     * @return una ResponseEntity con el estado HTTP y el cuerpo del error configurados
     */
    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
    }
    

}
