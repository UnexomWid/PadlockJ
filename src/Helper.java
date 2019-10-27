/**
 * PadlockJ (https://github.com/UnexomWid/PadlockJ)
 *
 * This project is licensed under the MIT license.
 * Copyright (c) 2017-2019 UnexomWid (https://uw.exom.dev)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
