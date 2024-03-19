package boot.spring.service.impl;

// Importing classes from the Spring framework and project-defined interfaces.
import boot.spring.service.MailService; // Interface for mail service operations.
import org.springframework.beans.factory.annotation.Autowired; // Spring annotation for automatic dependency injection.
import org.springframework.mail.SimpleMailMessage; // Spring's simple e-mail message class.
import org.springframework.mail.javamail.JavaMailSender; // Interface for sending e-mail messages.
import org.springframework.stereotype.Service; // Marks this class as a Spring service component.
import org.springframework.transaction.annotation.Isolation; // Enum for transaction isolation levels.
import org.springframework.transaction.annotation.Propagation; // Enum for transaction propagation behaviors.
import org.springframework.transaction.annotation.Transactional; // Configuration for transactional behavior of a class or method.

/**
 * Defines transactional behavior for all methods within this service class.
 * Propagation.REQUIRED indicates that a transaction is required for each method execution.
 * If there's an existing transaction, it will be used; otherwise, a new one will be started.
 * Isolation.DEFAULT uses the default isolation level of the underlying datastore.
 * timeout=5 sets the timeout for this transaction to 5 seconds.
 */
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, timeout=5)
@Service("mailservice") // Marks this class as a Spring service with a specific identifier "mailservice".
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender; // Injects an instance of JavaMailSender to send email messages.

    /**
     * Implements the sendVerificationCode method from the MailService interface.
     * This method configures and sends an email with a verification code.
     *
     * @param to The recipient's email address.
     * @param code The verification code to be sent.
     */
    @Override
    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage(); // Creates a new email message.
        message.setFrom("wxandrew.co@gmail.com"); // Sets the sender's email address.
        message.setTo(to); // Sets the recipient's email address.
        message.setSubject("Your Verification Code"); // Sets the subject of the email.
        message.setText("Your verification code is: " + code); // Sets the body text of the email, including the verification code.
        mailSender.send(message); // Sends the email message.
    }
}
