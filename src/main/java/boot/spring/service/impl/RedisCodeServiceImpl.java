package boot.spring.service.impl;

// Import statements bring in other pieces of code that this class depends on.
import boot.spring.service.RedisCodeService; // Import the RedisCodeService interface.
import org.springframework.beans.factory.annotation.Autowired; // Import Spring's Autowired annotation for dependency injection.
import org.springframework.data.redis.core.StringRedisTemplate; // Import the Spring Data Redis template for String operations.
import org.springframework.data.redis.core.ValueOperations; // Import the interface for Redis 'value' operations.
import org.springframework.stereotype.Service; // Import Spring's Service annotation to indicate this class is a service component.

import java.util.concurrent.TimeUnit; // Import the TimeUnit enum for specifying time units in operations.

/**
 * Service implementation of RedisCodeService for managing verification codes using Redis.
 * This class defines methods to save and retrieve verification codes to/from Redis,
 * specifically associating these codes with a combination of username and email.
 */
@Service
public class RedisCodeServiceImpl implements RedisCodeService {

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
}