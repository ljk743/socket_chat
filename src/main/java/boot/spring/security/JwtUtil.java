package boot.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class JwtUtil {
    // 生成适用于HS256算法的安全密钥
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 创建JWT
    public static String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject) // 设置JWT的sub（主题）声明
                .signWith(key) // 指定签名时使用的密钥和算法
                .compact(); // 构建JWT并将其序列化为一个紧凑的、URL安全的字符串
    }

    // 从JWT中提取主题（subject）
    public static String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 验证签名的时候使用相同的密钥
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // 获取并返回JWT中的主题（subject）
    }
}
