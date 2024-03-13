package boot.spring.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class JwtRealm extends AuthenticatingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken; // JwtToken 是你自定义的一个 AuthenticationToken 类
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String username = JwtUtil.getSubject(jwtToken.getToken());

        // 这里可以添加获取用户信息的代码，比如通过 username 查询数据库
        // 如果用户不存在或 token 无效，应抛出 AuthenticationException

        return new SimpleAuthenticationInfo(username, jwtToken.getCredentials(), getName());
    }
}
