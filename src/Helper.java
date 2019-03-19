/*
 * PadlockJ - a text encryption application written in Java that uses the AES-128 algorithm.
 * Copyright (C) 2017 UnexomWid

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.unexomwid.padlockj;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Contains helper fields and methods.
 */
public class Helper {

    /**
     * The SHA-256 object.
     */
    private static MessageDigest SHA256;
    /**
     * The HMAC SHA-256 object.
     */
    private static Mac HMACSHA256;

    /**
     * Represents a crypto action.
     */
    public enum CryptoAction {Encrypt, Decrypt}

    /**
     * Initializes all the ciphers that are used for hashing.
     */
    public static void initCiphers() {
        try {
            SHA256 = MessageDigest.getInstance("SHA-256");
            HMACSHA256 = Mac.getInstance("HmacSHA256");
        } catch(Exception ex){

        }
    }

    /**
     * Runs the AES algorithm on a byte array.
     *
     * @param action The crypto action (whether to encrypt or decrypt).
     * @param data The byte array to run the action on.
     * @param key The key to run the action with.
     *
     * @return The processed byte array.
     */
    public static byte[] RunAES(CryptoAction action, byte[] data, String key) {
        try {
            short blockSize = 128;
            short keySize = 128;

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] saltBytes = new byte[32];
            byte[] IV = new byte[32];
            byte[] keyBytes = key.getBytes("UTF-8");

            saltBytes = RunSHA256(keyBytes);
            String salt = new String(saltBytes, StandardCharsets.UTF_8);
            keyBytes = RunHMACSHA256(keyBytes, salt);
            IV = Arrays.copyOfRange(RunSHA256(saltBytes), 0, keySize / 8);
            keyBytes = Arrays.copyOfRange(RunSHA256(keyBytes), 0, keySize / 8);

            SecretKey keySpec = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
            IvParameterSpec IVSpec = new IvParameterSpec(IV);

            cipher.init(action == CryptoAction.Decrypt ? Cipher.DECRYPT_MODE : Cipher.ENCRYPT_MODE, keySpec, IVSpec);

            return cipher.doFinal(data);

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Runs the SHA-256 hash algorithm on a byte array.
     *
     * @param data The byte array to run the algorithm on.
     *
     * @return The raw hash of the byte array.
     */
    public static byte[] RunSHA256(byte[] data) {
        try {
            SHA256.update(data);
            return SHA256.digest();
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Runs the HMAC SHA-256 keyed hash algorithm on a byte array.
     *
     * @param data The byte array to run the algorithm on.
     * @param key The key to run the algorithm with.
     *
     * @return The raw hash of the byte array.
     */
    public static byte[] RunHMACSHA256(byte[] data, String key) {
        try {
            HMACSHA256.init(new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512"));
            return HMACSHA256.doFinal(data);
        } catch(Exception ex) {
            return null;
        }
    }
}
