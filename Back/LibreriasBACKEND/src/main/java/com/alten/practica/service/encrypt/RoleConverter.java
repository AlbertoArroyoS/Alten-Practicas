package com.alten.practica.service.encrypt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alten.practica.util.Role;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Autowired
    private DeterministicEncryptionService encryptionService;

    @PostConstruct
    public void init() {
        //System.out.println("RoleConverter initialized with encryptionService: " + encryptionService);
    }

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        String encryptedRole = encryptionService.encrypt(role.name());
        //System.out.println("Encrypting role: " + role.name() + " -> " + encryptedRole);
        return encryptedRole;
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String decrypted = encryptionService.decrypt(dbData);
        //System.out.println("Decrypting role: " + dbData + " -> " + decrypted);
        return Role.valueOf(decrypted);
    }
}

