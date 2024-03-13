package boot.spring.security;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    // JWT 字符串
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    // AuthenticationToken 接口需要的方法，返回凭证
    @Override
    public Object getCredentials() {
        return token;
    }

    // AuthenticationToken 接口需要的方法，返回主体（比如用户名）。你可以按需调整。
    @Override
    public Object getPrincipal() {
        return token;  // 或者根据token解析出的用户信息，取决于你的需求
    }

    // 获取token的公开方法
    public String getToken() {
        return token;
    }
}
