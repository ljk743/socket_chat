package boot.spring.service;

public interface RedisCodeService {
    void saveVerificationCode(String email, String code);
}
