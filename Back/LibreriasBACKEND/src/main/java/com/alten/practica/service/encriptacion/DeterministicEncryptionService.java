package com.alten.practica.service.encriptacion;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio que proporciona cifrado y descifrado determinista utilizando el algoritmo AES.
 * El cifrado determinista garantiza que el mismo texto sin formato siempre se cifre de la misma manera
 * utilizando la misma clave. Esto es útil para campos que requieren búsquedas o comparaciones cifradas.
 */
@Service
public class DeterministicEncryptionService {

    // Clave secreta utilizada para el cifrado y descifrado
    private SecretKey secretKey = null;

    /**
     * Constructor que inicializa el servicio con una clave de cifrado proporcionada.
     *
     * @param encryptionKey Clave de cifrado en formato Base64, proporcionada a través del archivo de configuración.
     * @throws NoSuchAlgorithmException Si el algoritmo de generación de claves no está disponible.
     */
    public DeterministicEncryptionService(@Value("${encryption.key}") String encryptionKey) throws NoSuchAlgorithmException {
        this.secretKey = generateKey(encryptionKey);
    }

    /**
     * Genera una clave secreta a partir de una cadena codificada en Base64.
     *
     * @param key Clave en formato Base64.
     * @return SecretKey La clave secreta generada.
     * @throws NoSuchAlgorithmException Si el algoritmo de generación de claves no está disponible.
     */
    private SecretKey generateKey(String key) throws NoSuchAlgorithmException {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    /**
     * Cifra el texto proporcionado utilizando el algoritmo AES en modo ECB con padding PKCS5.
     *
     * @param data El texto sin formato que se desea cifrar.
     * @return String El texto cifrado, codificado en Base64.
     */
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.toString());
        }
    }

    /**
     * Descifra el texto cifrado proporcionado utilizando el algoritmo AES en modo ECB con padding PKCS5.
     *
     * @param encryptedData El texto cifrado, codificado en Base64.
     * @return String El texto sin formato descifrado.
     */
    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.toString());
        }
    }

    /**
     * Método estático para generar una nueva clave AES y mostrarla en formato Base64.
     *
     * @throws NoSuchAlgorithmException Si el algoritmo de generación de claves no está disponible.
     */
    public static void generarBase64() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Puedes usar 128 o 192 bits también
        SecretKey secretKey = keyGen.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Base64 Encoded Key: " + base64Key);
    }
}
