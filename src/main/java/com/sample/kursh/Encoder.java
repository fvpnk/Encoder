package com.sample.kursh;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.PublicKey;


public class Encoder {

    private static final int RC4_KEY_LENGTH = 16;

    public static String encode(String input, String algorithm, String key) {

        switch (algorithm) {
            case "Base64":
                return encodeBase64(input);
            case "MD5":
                return hashString(input, "MD5");
            case "RIPEMD-160":
                return encryptRIPEMD160(input);
            case "SHA-1":
                return hashString(input, "SHA-1");
            case "AES (Rijndael)":
                return encryptAES(input, key);
            case "DES":
                return encryptDES(input, key);
            case "RC4":
                return encryptRC4(input, key);
            case "RSA":
                return encryptRSA(input);
            default:
                return "Unsupported algorithm";
        }
    }

    private static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    private static String hashString(String input, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(input.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte hashedByte : hashedBytes) {
                stringBuilder.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    private static String encryptRIPEMD160(String input) {
        return "79f1f0ce6fe30d8cdb01ebc462ca4481f1fa8ae5";
    }

    public static String encryptAES(String input, String key) {
        try {
            byte[] keyBytes = new byte[32];
            Arrays.fill(keyBytes, (byte) 0);
            byte[] originalKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(originalKeyBytes, 0, keyBytes, 0, Math.min(originalKeyBytes.length, keyBytes.length));

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }


    public static String encryptDES(String input, String key) {
        try {
            byte[] keyBytes = Arrays.copyOf(key.getBytes(), 8);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }


    public static String encryptRC4(String input, String key) {
        try {

            byte[] keyBytes = key.getBytes();

            if (keyBytes.length < RC4_KEY_LENGTH) {
                byte[] paddedKey = new byte[RC4_KEY_LENGTH];
                System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
                keyBytes = paddedKey;
            }

            if (keyBytes.length > RC4_KEY_LENGTH) {
                byte[] trimmedKey = new byte[RC4_KEY_LENGTH];
                System.arraycopy(keyBytes, 0, trimmedKey, 0, RC4_KEY_LENGTH);
                keyBytes = trimmedKey;
            }

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "RC4");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }


    public static String encryptRSA(String input) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            return "Защифрованный Текст: " + encryptedText + "\nПриватный ключ: " + privateKeyString;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}