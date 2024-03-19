package boot.spring.service.impl;

import boot.spring.service.RedisCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCodeServiceImpl implements RedisCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveVerificationCode(String username, String email, String code) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(username+email, code, 3, TimeUnit.MINUTES); // 设置验证码3分钟后过期
    }

    @Override
    public String getVerificationCode(String username, String email) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 使用username和email作为键来获取存储在Redis中的验证码
        String code = ops.get(username+email);
        return code; // 返回获取到的验证码，如果没有找到，将返回null
    }
}

