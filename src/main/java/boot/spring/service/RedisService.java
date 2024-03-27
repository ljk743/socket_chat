package boot.spring.service;

import java.util.List;

/**
 * The RedisService interface defines operations for managing verification codes in Redis.
 * This service interface specifies methods for saving a verification code for a given username and email,
 * as well as retrieving a verification code for a given username and email combination.
 */
public interface RedisService {

    /**
     * Saves a verification code associated with a specific username and email address.
     * This method is responsible for storing the code in a way that can later be retrieved
     * using the same username and email combination.
     *
     * @param username the username with which the verification code is associated.
     * @param email the email address with which the verification code is associated.
     * @param code the verification code to be saved.
     */
    void saveVerificationCode(String username, String email, String code);

    /**
     * Retrieves the verification code associated with a specific username and email address.
     * This method looks up the code previously saved for the given username and email.
     * If no code is found for the specified combination, it may return null or indicate
     * the absence in another manner, depending on the implementation.
     *
     * @param username the username for which the verification code is to be retrieved.
     * @param email the email address for which the verification code is to be retrieved.
     * @return the verification code as a String, or null if no code exists for the given username and email.
     */
    String getVerificationCode(String username, String email);

    /**
     * Saves a message associated with a specific key.
     * This method should ensure the message is stored in a manner that allows it
     * to be retrieved later via the same key. Depending on the implementation, if the key
     * already exists, this method could overwrite the existing value or append the new message
     * to a list of messages under the same key.
     *
     * @param key The key under which the message should be saved. This key acts as an identifier
     *            for retrieving the stored message(s) later. It is typically a string representing
     *            a unique attribute of the message, such as a user ID or message topic.
     * @param value The message to be saved. This could be a plain text message, encrypted text,
     *              or even a serialized object, depending on the use case and storage mechanism.
     */
    void saveMessage(String key, String value);

    /**
     * Retrieves a list of messages associated with a specific key.
     * This method is used to fetch all messages stored under the given key. The exact behavior
     * and the order of the returned messages may depend on the underlying storage mechanism
     * and the implementation details. For instance, a FIFO (First In, First Out) order could be
     * maintained in a queue-based implementation.
     *
     * @param key The key for which to retrieve the associated messages. This is the same key
     *            that was used to save the messages via the {@link #saveMessage(String, String)} method.
     * @return A list of messages associated with the specified key. The list could be empty if
     *         no messages are associated with the key or if the key does not exist.
     * @throws Exception If an error occurs during the retrieval process. The nature of the exception
     *                   can vary depending on the underlying storage mechanism and the implementation
     *                   details. It could be a communication error with the database, a timeout, or
     *                   any other issue that prevents the successful retrieval of the messages.
     */
    List<String> getMessage(String key) throws Exception;
}
