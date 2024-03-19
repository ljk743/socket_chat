package boot.spring.service;

/**
 * The MailService interface defines the contract for a service capable of sending emails.
 * This service is specifically focused on operations related to sending verification codes
 * via email, which can be utilized in various scenarios such as user registration, password resets,
 * and two-factor authentication processes.
 */
public interface MailService {

    /**
     * Sends a verification code to the specified email address.
     * This method is designed to compose and send an email containing the provided
     * verification code to the recipient's email address. The implementation of this method
     * should ensure that the email delivery process adheres to the relevant email
     * sending standards and practices.
     *
     * @param to The email address of the recipient who is to receive the verification code.
     * @param code The verification code that is to be sent. This code is typically generated
     *             by the system as part of the verification process and is included in the
     *             email content sent to the user.
     */
    void sendVerificationCode(String to, String code);
}
