package com.alten.practica.service.encrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

    @Autowired
    private DeterministicEncryptionService encryptionService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute instanceof String || attribute instanceof Byte || attribute instanceof Integer || attribute instanceof Double || attribute instanceof Role || attribute instanceof Date) {
            if (attribute instanceof Date) {
                return encryptionService.encrypt(dateFormat.format((Date) attribute));
            }
            return encryptionService.encrypt(attribute.toString());
        }
        throw new IllegalArgumentException("Unsupported attribute type: " + attribute.getClass().getName());
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String decryptedData = encryptionService.decrypt(dbData);
        if (isByte(decryptedData)) {
            return Byte.valueOf(decryptedData);
        } else if (isInteger(decryptedData)) {
            return Integer.valueOf(decryptedData);
        } else if (isDouble(decryptedData)) {
            return Double.valueOf(decryptedData);
        } else if (isRole(decryptedData)) {
            return Role.valueOf(decryptedData);
        } else if (isDate(decryptedData)) {
            try {
                return dateFormat.parse(decryptedData);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date: " + decryptedData, e);
            }
        } else {
            return decryptedData;
        }
    }

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

    private boolean isDate(String data) {
        try {
            dateFormat.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

