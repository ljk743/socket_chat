package boot.spring.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CspHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String cspPolicy = "default-src 'self' https://192.168.56.103;" +
                "font-src 'self' https://cdn.bootcss.com;" +
                "script-src 'self' https://cdn.bootcss.com 'unsafe-inline';" +
                "style-src 'self' https://cdn.bootcss.com 'unsafe-inline';" ;
        httpServletResponse.setHeader("Content-Security-Policy", cspPolicy);
        chain.doFilter(request, response);
    }
}

