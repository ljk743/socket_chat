package boot.spring.service;

/**
 * The RedisCodeService interface defines operations for managing verification codes in Redis.
 * This service interface specifies methods for saving a verification code for a given username and email,
 * as well as retrieving a verification code for a given username and email combination.
 */
public interface RedisCodeService {

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
}
