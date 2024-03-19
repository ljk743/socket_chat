package boot.spring.utils; // Defines the package name as boot.spring.utils.

import java.security.MessageDigest; // Imports the MessageDigest class for creating hash values.
import java.security.NoSuchAlgorithmException; // Imports the NoSuchAlgorithmException class for handling exceptions when a cryptographic algorithm is requested but is not available.
import java.security.SecureRandom; // Imports the SecureRandom class for generating cryptographically strong random values.

// Declares the public class HashTools for providing utility methods related to hashing.
public class HashTools {

    /**
     * Generates a random salt value.
     * @return A hexadecimal string representation of the salt.
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom(); // Creates an instance of SecureRandom for generating random numbers.
        byte[] salt = new byte[16]; // Declares a byte array of size 16 to hold the salt.
        random.nextBytes(salt); // Generates random bytes and places them into the byte array.
        return bytesToHex(salt); // Converts the byte array to a hexadecimal string and returns it.
    }

    /**
     * Converts a byte array to a hexadecimal string.
     * @param bytes The byte array to be converted.
     * @return The hexadecimal string representation of the byte array.
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(); // Creates a StringBuilder to efficiently build the hex string.
        for (byte b : bytes) { // Iterates over each byte in the byte array.
            String hex = Integer.toHexString(0xff & b); // Converts the byte to hex, masking the byte with 0xff.
            if (hex.length() == 1) hexString.append('0'); // Adds a leading zero if the hex string is only one character.
            hexString.append(hex); // Appends the hex string representation of the byte to the StringBuilder.
        }
        return hexString.toString(); // Converts the StringBuilder to a String and returns it.
    }

    /**
     * Performs SHA-256 hashing on the given data and salt.
     * @param data The data to be hashed.
     * @param salt The salt to be used in the hash.
     * @return The SHA-256 hashed string.
     */
    public static String hashWithSHA256(String data, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Creates a MessageDigest instance for the SHA-256 hashing algorithm.
            digest.update(salt.getBytes()); // Updates the digest using the salt.
            byte[] encodedhash = digest.digest(data.getBytes()); // Performs the hash computation on the data.
            return bytesToHex(encodedhash); // Converts the hashed byte array to a hexadecimal string and returns it.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e); // Throws a RuntimeException if the SHA-256 algorithm is not available.
        }
    }
}
