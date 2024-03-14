package boot.spring.config;

import boot.spring.security.JdbcRealm;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private MyDataSource dataSource;


    @Bean
    public JdbcRealm setjdbcRealm() {
        JdbcRealm jdbcRealm = new JdbcRealm(dataSource.dataSource());
        jdbcRealm.setPermissionsLookupEnabled(true); // 如果需要权限查找
        // 可以在这里配置其他自定义查询
        return jdbcRealm;
    }

    @Bean
    public List<Realm> realms() {
        List<Realm> realms = new ArrayList<>();
        realms.add(setjdbcRealm());
        // 添加其他的 Realm，比如 JwtRealm
        return realms;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置认证器为 ModularRealmAuthenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(realms());
        securityManager.setAuthenticator(authenticator);

        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        System.out.println("Configuring Shiro filter factory bean.");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());

        // 添加自定义的过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        shiroFilter.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginvalidate", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/onlineusers", "authc");
        filterChainDefinitionMap.put("/webSocket/*", "authc");
        filterChainDefinitionMap.put("/currentuser", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilter;
    }
}
