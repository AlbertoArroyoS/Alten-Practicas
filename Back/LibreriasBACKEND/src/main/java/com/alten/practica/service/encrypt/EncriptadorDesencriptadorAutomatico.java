package com.alten.practica.service.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alten.practica.util.Role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
/*
 * Convertidor de atributos de entidad para cifrado y descifrado automático.
 */
@Component
@Converter(autoApply = true)
public class EncriptadorDesencriptadorAutomatico implements AttributeConverter<Object, String> {

    // Inyección de la dependencia del servicio de cifrado
    @Autowired
    private DeterministicEncryptionService encryptionService;

    // Convierte el atributo a una columna de base de datos cifrada
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        // Verifica si el atributo es de un tipo soportado antes de cifrar
        if (attribute instanceof String || attribute instanceof Byte || attribute instanceof Integer || attribute instanceof Double || attribute instanceof Role) {
            return encryptionService.encrypt(attribute.toString());
        }
        // Lanza una excepción si el tipo del atributo no es soportado
        throw new IllegalArgumentException("Unsupported attribute type: " + attribute.getClass().getName());
    }

    // Convierte la columna de la base de datos desencriptada al atributo de entidad
    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String decryptedData = encryptionService.decrypt(dbData);
        // Determina el tipo de datos del valor desencriptado y lo convierte en consecuencia
        if (isByte(decryptedData)) {
            return Byte.valueOf(decryptedData);
        } else if (isInteger(decryptedData)) {
            return Integer.valueOf(decryptedData);
        } else if (isDouble(decryptedData)) {
            return Double.valueOf(decryptedData);
        } else if (isRole(decryptedData)) {
            return Role.valueOf(decryptedData);
        } else {
            return decryptedData;
        }
    }

    // Métodos privados para verificar el tipo de datos del valor desencriptado
    private boolean isByte(String data) {
        try {
            Byte.valueOf(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String data) {
        try {
            Integer.valueOf(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String data) {
        try {
            Double.valueOf(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isRole(String data) {
        try {
            Role.valueOf(data);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

