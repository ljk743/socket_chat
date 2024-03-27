package boot.spring.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    // Define a static key for AES encryption and decryption.
    // The key length must be 16 bytes (128 bits) to comply with AES-128 bit encryption standard.
    private static final String KEY = "HesPureAndSimple";

    /**
     * Encrypts a given string using AES encryption with a predefined key.
     *
     * @param content The string to be encrypted.
     * @return The encrypted string, encoded in Base64 to ensure it's represented in a safe format
     *         for transmission or storage in text form.
     * @throws Exception If an error occurs during the encryption process, such as an invalid key
     *         size or invalid parameters for the encryption algorithm.
     */
    public static String encrypt(String content) throws Exception {
        // Initialize the Cipher for AES encryption using ECB mode and PKCS5 padding.
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // Create a secret key spec for AES based on the byte array of the key.
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        // Initialize the cipher in encryption mode with the secret key.
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // Perform the encryption, converting the string content to a byte array and then encrypting it.
        byte[] encryptedBytes = cipher.doFinal(content.getBytes());
        // Encode the encrypted byte array into a Base64 string to make it safe for storage or transmission.
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a given string (previously encrypted with AES encryption) using the same predefined key.
     *
     * @param encryptedContent The encrypted string, encoded in Base64, to be decrypted.
     * @return The original plaintext string after decryption.
     * @throws Exception If an error occurs during the decryption process, such as invalid Base64 input,
     *         invalid key size, or invalid parameters for the decryption algorithm.
     */
    public static String decrypt(String encryptedContent) throws Exception {
        // Initialize the Cipher for AES decryption using ECB mode and PKCS5 padding.
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // Create a secret key spec for AES, similar to the encryption process.
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        // Initialize the cipher in decryption mode with the secret key.
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        // Decode the encrypted content from Base64 to get the encrypted byte array.
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        // Convert the decrypted byte array back into a string and return it.
        return new String(decryptedBytes);
    }

}
