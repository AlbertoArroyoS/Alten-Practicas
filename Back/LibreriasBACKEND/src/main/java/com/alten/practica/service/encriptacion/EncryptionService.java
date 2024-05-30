package com.alten.practica.service.encriptacion;

import org.apache.tomcat.util.codec.binary.Base64;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private AES256TextEncryptor textEncryptor;

    public EncryptionService(@Value("${jasypt.encryptor.password}") String encryptionKey) {
        this.textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionKey);
    }

    public String encrypt(String data) {
        String encryptedData = textEncryptor.encrypt(data);
        // Encode in Base64 and then truncate
        String base64Encoded = Base64.encodeBase64String(encryptedData.getBytes());
        return truncateToLength(base64Encoded, 10);
    }

    public String decrypt(String encryptedData) {
        // Decode from Base64 before decrypting
        String base64Decoded = new String(Base64.decodeBase64(encryptedData));
        return textEncryptor.decrypt(base64Decoded);
    }

    public String encryptInt(int number) {
        return encrypt(Integer.toString(number));
    }

    public int decryptInt(String encryptedNumber) {
        return Integer.parseInt(decrypt(encryptedNumber));
    }

    public String encryptByte(byte number) {
        return encrypt(Byte.toString(number));
    }

    public byte decryptByte(String encryptedNumber) {
        return Byte.parseByte(decrypt(encryptedNumber));
    }

    private String truncateToLength(String data, int length) {
        if (data.length() > length) {
            return data.substring(0, length);
        }
        return data;
    }
}
