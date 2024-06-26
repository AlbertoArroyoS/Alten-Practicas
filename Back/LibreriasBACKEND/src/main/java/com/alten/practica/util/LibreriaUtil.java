package com.alten.practica.util;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.alten.practica.constantes.LibreriaConstant;
import com.alten.practica.errorhandler.EntityGenericServerException;
import com.alten.practica.errorhandler.HrefEntityDTO;
import com.alten.practica.modelo.entidad.dto.PageableDTO;

/**
 * Clase de utilidad que proporciona métodos generales para la librería.
 * 
 * Esta clase contiene métodos que pueden ser utilizados en diferentes partes de
 * la aplicación para realizar tareas comunes, como la validación de datos, la
 * creación de objetos DTO y la generación de enlaces HREF para los recursos de
 * la librería.
 */
@Component
public class LibreriaUtil {

	/**
	 * Metodo que devuelve un objeto Pageable Recibe un objeto PageableDTO, con el
	 * orden, el campo, el numero de pagina y el tamaño de la pagina
	 * 
	 * order = 1 ASC, 0 DESC
	 * 
	 * @param pageableDTO, campoPorDefecto para ordenar por defecto por el campo
	 *                     pasado por parametro
	 * @return
	 */
	public Pageable getPageable(PageableDTO pageableDTO, String campoPorDefecto) {
		Optional<Integer> sortOrder = pageableDTO.getOrder();
		Optional<String> sortField = pageableDTO.getField();
		Integer pageNumber = pageableDTO.getPageNumber();
		Integer perPage = pageableDTO.getPageSize();

		Pageable pageable;
		if (sortField != null && sortField.isPresent() && !sortField.get().isEmpty()) {
			if (sortOrder.isPresent()) {
				Sort.Direction direction = sortOrder.get().equals(1) ? Sort.Direction.ASC : Sort.Direction.DESC;
				pageable = PageRequest.of(pageNumber, perPage, Sort.by(direction, sortField.get()));
			} else {
				pageable = PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, sortField.get()));
			}
		} else {
			// Por defecto se ordena por id de forma descendente
			pageable = PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.DESC, campoPorDefecto));
		}
		return pageable;
	}
	
	
	
	/**
	 * Crea un objeto {@link HrefEntityDTO} que representa un enlace HREF a un recurso específico basado en el identificador y el tipo de recurso proporcionados.
	 * Este método construye la URL utilizando constantes predefinidas basadas en el tipo de recurso, asegurando que el enlace esté correctamente formateado.
	 *
	 * @param id El identificador del recurso; este valor se añadirá al final de la URL del recurso.
	 * @param resource El tipo de recurso que se utilizará para determinar la parte base de la URL. Este parámetro debe ser uno de los valores definidos en {@link LibreriaResource}.
	 * @return Un objeto {@link HrefEntityDTO} que contiene tanto el identificador como el HREF construido.
	 * @throws EntityGenericServerException Si ocurre un error al generar el HREF, como un tipo de recurso no soportado.
	 * @throws IllegalArgumentException Si se proporciona un tipo de recurso no reconocido o no soportado, lo que no debería suceder si todos los casos están correctamente manejados en el switch.
	 * 
	 * Ejemplo de uso:
	 * {@code 
	 * HrefEntityDTO href = createHrefFromResource(5, LibreriaResource.AUTOR);
	 * System.out.println("Href: " + href.getHref()); // Imprime el HREF generado para el autor con ID 5.
	 * }
	 */
	public HrefEntityDTO createHrefFromResource(Object id, LibreriaResource resource)
	        throws EntityGenericServerException {
	    HrefEntityDTO hrefEntity = new HrefEntityDTO();
	    try {
	        StringBuilder builder = new StringBuilder();
	        String resourcePath;

	        // Utilizar un switch para seleccionar el recurso adecuado
	        switch (resource) {
	            case LIBRERIA:
	                resourcePath = LibreriaConstant.RESOURCE_LIBRERIAS;
	                break;
	            case AUTOR:
	                resourcePath = LibreriaConstant.RESOURCE_AUTORES;
	                break;
	            case LIBRO:
	                resourcePath = LibreriaConstant.RESOURCE_LIBROS;
	                break;
	            case CLIENTE:
	                resourcePath = LibreriaConstant.RESOURCE_CLIENTES;
	                break;
	            case CLIENTECOMPRALIBRO:
	                resourcePath = LibreriaConstant.RESOURCE_CLIENTE_COMPRA_LIBROS;
	                break;
	            case LIBRERIALIBRO:
	                resourcePath = LibreriaConstant.RESOURCE_LIBRERIA_LIBROS;
	                break;
	            case USUARIO:
	                resourcePath = LibreriaConstant.RESOURCE_USUARIOS;
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported resource type: " + resource);
	        }

	        // Construir el href
	        builder.append(resourcePath).append("/").append(id);
	        hrefEntity.setId(id);
	        hrefEntity.setHref(builder.toString());
	    } catch (Exception e) {
	        e.printStackTrace(); // Considerar usar un sistema de logging en lugar de printStackTrace en un entorno de producción
	        // Lanzar una excepción específica del servidor en caso de error durante la generación del href.
	        throw new EntityGenericServerException("Error generating href resource: " + e.getMessage(), e);
	    }
	    return hrefEntity;
	}
	
	
	/**
	 * Crea una referencia URL (Href) para un recurso específico de la librería usando su identificador.
	 * 
	 * Este método genera un URL completo para acceder a un recurso específico de una librería,
	 * como puede ser un autor, una librería o un libro, utilizando los identificadores definidos en
	 * las constantes de {@link LibreriaConstant}. El URL generado es útil para proporcionar accesos directos
	 * en interfaces de usuario o APIs donde se requiere referenciar recursos específicos.
	 * 
	 * @param id El identificador del recurso, como puede ser el ID de un libro, un autor o una librería.
	 * @param resource El tipo de recurso de la librería para el cual se está creando el href; debe ser uno de los valores definidos en {@link LibreriaResource}.
	 * @return Un objeto {@link HrefEntityDTO} que contiene el ID del recurso y el href construido.
	 * @throws EntityGenericServerException Si hay un error al acceder a las constantes o al construir el href, se lanza esta excepción indicando un problema en el servidor.
	 */
/*
	public HrefEntityDTO createHrefFromResource(Object id, LibreriaResource resource)
	        throws EntityGenericServerException {
	    HrefEntityDTO hrefEntity = new HrefEntityDTO();
	    try {
	        StringBuilder builder = new StringBuilder();
	        // Obtener el campo de la clase de constantes que corresponde al nombre del recurso.
	        Field field = LibreriaConstant.class.getDeclaredField("RESOURCE_" + resource + "ES");
	        Object valueResource = field.get(null);
	        builder.append(valueResource);
	        
	        // Concatenar el identificador específico del tipo de recurso.
	        field = LibreriaConstant.class.getDeclaredField("RESOURCE_" + resource + "ES_" + resource);
	        valueResource = field.get(null);
	        builder.append(valueResource).append("/").append(id);
	        
	        // Establecer el ID y href en el objeto DTO para su retorno.
	        hrefEntity.setId(id);
	        hrefEntity.setHref(builder.toString());
	    } catch (Exception e) {
	    	e.printStackTrace(); // Considerar usar un sistema de logging en lugar de printStackTrace en un entorno de producción
	        // Lanzar una excepción específica del servidor en caso de error durante la generación del href.
	        throw new EntityGenericServerException("Error generating href resource");
	    }
	    return hrefEntity;
	}*/

}

	





