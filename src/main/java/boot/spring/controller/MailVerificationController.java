package boot.spring.controller;

import boot.spring.service.MailService;
import boot.spring.service.RedisCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class MailVerificationController {

    @Autowired
    private MailService mailservice;
    @Autowired
    private RedisCodeService redisCodeService;

    @GetMapping("/getcode")
    public String sendCode(@RequestParam String email) {
        String code = generateCode();
        System.out.println(email);
        mailservice.sendVerificationCode(email, code);
        // 存储验证码逻辑
        redisCodeService.saveVerificationCode(email, code);
        return "Verification code sent.";
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 生成六位随机数
        return String.valueOf(code);
    }
}
