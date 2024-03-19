package boot.spring.service;
public interface MailService {
    void sendVerificationCode(String to, String code);
}
