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
    public void saveVerificationCode(String email, String code) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(email, code, 3, TimeUnit.MINUTES); // 设置验证码3分钟后过期
    }
}

