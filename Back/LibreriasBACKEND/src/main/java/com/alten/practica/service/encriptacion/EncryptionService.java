package com.alten.practica.service.encriptacion;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alten.practica.util.Role;

@Service
public class EncryptionService {

    private AES256TextEncryptor textEncryptor;

    public EncryptionService(@Value("${jasypt.encryptor.password}") String encryptionKey) {
        this.textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionKey);
    }

    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encryptedData) {
        return textEncryptor.decrypt(encryptedData);
    }

    public String encryptRole(Role role) {
        return textEncryptor.encrypt(role.name());
    }

    public Role decryptRole(String encryptedRole) {
        return Role.valueOf(textEncryptor.decrypt(encryptedRole));
    }

    public String encryptInt(int number) {
        return textEncryptor.encrypt(Integer.toString(number));
    }

    public int decryptInt(String encryptedNumber) {
        return Integer.parseInt(textEncryptor.decrypt(encryptedNumber));
    }

    public String encryptByte(byte number) {
        return textEncryptor.encrypt(Byte.toString(number));
    }

    public byte decryptByte(String encryptedNumber) {
        return Byte.parseByte(textEncryptor.decrypt(encryptedNumber));
    }
}
