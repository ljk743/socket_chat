package boot.spring.service.impl;

// Import statements bring in other pieces of code that this class depends on.
import boot.spring.service.RedisService; // Import the RedisService interface.
import boot.spring.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired; // Import Spring's Autowired annotation for dependency injection.
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate; // Import the Spring Data Redis template for String operations.
import org.springframework.data.redis.core.ValueOperations; // Import the interface for Redis 'value' operations.
import org.springframework.stereotype.Service; // Import Spring's Service annotation to indicate this class is a service component.

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Service implementation of RedisService for managing verification codes using Redis.
 * This class defines methods to save and retrieve verification codes to/from Redis,
 * specifically associating these codes with a combination of username and email.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // Autowired dependency on the StringRedisTemplate to interact with Redis.

    /**
     * Saves a verification code in Redis associated with a specific username and email.
     * The code is set to automatically expire after 3 minutes.
     *
     * @param username the username with which the verification code is associated.
     * @param email the email address with which the verification code is associated.
     * @param code the verification code to be saved.
     */
    @Override
    public void saveVerificationCode(String username, String email, String code) {
        // Obtain the operations for working with String values in Redis.
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // Combine username and email to form a unique key, and save the code with an expiration time of 3 minutes.
        ops.set(username + email, code, 3, TimeUnit.MINUTES);
    }

    /**
     * Retrieves the verification code associated with a specific username and email from Redis.
     * If no code exists for the specified combination, returns null.
     *
     * @param username the username for which the verification code is to be retrieved.
     * @param email the email address for which the verification code is to be retrieved.
     * @return the verification code as a String, or null if no code exists for the given username and email.
     */
    @Override
    public String getVerificationCode(String username, String email) {
        // Obtain the operations for working with String values in Redis.
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // Use username+email as the key to retrieve the stored code.
        String code = ops.get(username + email);
        // Return the code, which may be null if it doesn't exist.
        return code;
    }

    /**
     * Saves a message associated with a specific key in Redis.
     * This method utilizes the `ListOperations` from `StringRedisTemplate` to perform list operations in Redis.
     * It adds the provided message value to the end of the list associated with the given key,
     * ensuring that messages are stored in the order they are received.
     *
     * @param key The key under which the message should be saved in Redis. This key acts as an identifier
     *            for the list to which the message will be added. Typically, this could represent a user ID,
     *            a chat room ID, or any other identifier that groups messages together.
     * @param value The message to be saved. The message is stored as a string and can be in any format
     *              (e.g., plain text, JSON, serialized object). The format should be consistent with how
     *              the `getMessage` method expects to retrieve and process these messages.
     */
    @Override
    public void saveMessage(String key, String value) {
        // Get the ListOperations bean from the StringRedisTemplate for performing operations on Redis lists.
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();

        // Use rightPush to add the value to the end of the list identified by the key.
        // This ensures that newer messages are added to the "right" end (tail) of the list,
        // maintaining the order of messages as they are received and saved.
        opsForList.rightPush(key, value);
    }

    /**
     * Retrieves the last nine messages associated with a specific key from Redis, decrypting each message.
     * This method uses `ListOperations` from `StringRedisTemplate` and fetches a range of elements from
     * the list, specifically the last nine elements, to provide a snapshot of the most recent messages.
     * It then decrypts each message before returning them.
     *
     * @param key The key for which to retrieve the associated messages from Redis. This is the same key
     *            that was used to save the messages via the `saveMessage` method.
     * @return A list of decrypted messages associated with the specified key, limited to the last nine
     *         messages for efficiency and relevance. The list could be shorter if fewer messages are available.
     * @throws Exception If an error occurs during the retrieval or decryption process. The method is designed
     *                   to propagate exceptions, including those from the decryption process, to allow the caller
     *                   to handle them appropriately, possibly indicating issues with data integrity or availability.
     */
    @Override
    public List<String> getMessage(String key) throws Exception {
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
        // Retrieve the current size of the list in Redis associated with the given key.
        long listSize = opsForList.size(key);

        // Calculate the start and end indexes to get the last nine elements of the list.
        long start = listSize - 9;
        long end = -1; // A special value indicating the end of the list.

        // Ensure the start index is not negative, which would happen if the list contains fewer than nine elements.
        // This adjustment ensures that we always start from the beginning of the list in such cases.
        start = Math.max(start, 0);

        // Retrieve the range of messages from Redis, from the calculated start index to the end of the list.
        List<String> recentElements = opsForList.range(key, start, end);

        // Decrypt each message before returning them.
        List<String> decryptedMessages = new ArrayList<>();
        for (String encryptedMessage : recentElements) {
            // Decrypt each message using AESUtil and add it to the list of decrypted messages.
            decryptedMessages.add(AESUtil.decrypt(encryptedMessage));
        }

        // Return the list of decrypted messages.
        return decryptedMessages;
    }

}