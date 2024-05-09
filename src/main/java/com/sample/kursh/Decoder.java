package com.sample.kursh;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Arrays;
import javax.crypto.Cipher;

public class Decoder {

    public static String decode(String input, String algorithm, String key, String privateKey) {
        switch (algorithm) {
            case "Base64":
                return decodeBase64(input);
            case "AES (Rijndael)":
                return decryptAES(input, key);
            case "DES":
                return decryptDES(input, key);
            case "RC4":
                return decryptRC4(input, key);
            case "RSA":
                return decryptRSA(input, privateKey);
            default:
                return "Unsupported algorithm";
        }
    }

    private static String decodeBase64(String input) {
        return new String(Base64.getDecoder().decode(input));
    }

    private static String decryptAES(String input, String key) {
        try {
            byte[] keyBytes = padKey(key, 32);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    private static String decryptDES(String input, String key) {
        try {
            byte[] keyBytes = padKey(key, 8);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    private static String decryptRC4(String input, String key) {
        try {
            byte[] keyBytes = padKey(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "RC4");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }


    private static byte[] padKey(String key, int length) {
        byte[] keyBytes = key.getBytes();
        if (keyBytes.length < length) {
            byte[] paddedKey = new byte[length];
            Arrays.fill(paddedKey, (byte) 0);
            System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
            keyBytes = paddedKey;
        } else if (keyBytes.length > length) {
            keyBytes = Arrays.copyOf(keyBytes, length);
        }
        return keyBytes;
    }
    public static String decryptRSA(String input, String privateKeyString) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}

