package boot.spring.service;

public interface RedisCodeService {
    void saveVerificationCode(String username,String email, String code);
    String getVerificationCode(String username,String email);
}
