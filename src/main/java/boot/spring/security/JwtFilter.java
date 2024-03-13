package boot.spring.security;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    // 在请求被处理之前调用
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 仅当请求需要JWT认证时才处理
        boolean isJwtSubmit = isLoginAttempt(httpServletRequest, httpServletResponse);
        if (isJwtSubmit) {
            String token = httpServletRequest.getHeader("Authorization").substring(7);
            JwtToken jwtToken = new JwtToken(token);
            try {
                getSubject(request, response).login(jwtToken);
                return true; // 认证成功，继续处理请求
            } catch (Exception e) {
                // 认证失败，返回 401 错误
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        // 对于不需要JWT认证的请求（如标记为anon的路径），允许它们继续
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null && authorization.startsWith("Bearer ");
    }

}
