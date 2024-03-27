package boot.spring.controller;

import boot.spring.service.MailService;
import boot.spring.service.RedisService;
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
    private RedisService redisService;

    @GetMapping("/getcode")
    public String sendCode(@RequestParam String username, @RequestParam String email) {
        String code = generateCode();
        mailservice.sendVerificationCode(email, code);
        System.out.println(email+username);
        // The way to store the token
        redisService.saveVerificationCode(username, email, code);
        return "Verification code sent.";
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6 bits random number
        return String.valueOf(code);
    }
}
