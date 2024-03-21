package boot.spring.config;

// Import necessary classes
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

/**
 * Configuration class for Apache Shiro security framework integration with Spring.
 */
@Configuration
public class ShiroConfig {
    // Autowire MyDataSource bean to be used for creating JdbcRealm
    @Autowired
    private MyDataSource dataSource;

    /**
     * Configures the JdbcRealm with the data source and enables permission lookup.
     * @return JdbcRealm - the configured realm for JDBC authentication.
     */
    @Bean
    public JdbcRealm setjdbcRealm() {
        JdbcRealm jdbcRealm = new JdbcRealm(dataSource.dataSource());
        jdbcRealm.setPermissionsLookupEnabled(true); // Enable permission lookup if needed
        // Additional custom queries configuration can be done here
        return jdbcRealm;
    }

    /**
     * Defines and returns a list of Realm instances to be used by Shiro.
     * Additional realms like JwtRealm can be added to the list.
     * @return List<Realm> - the list of configured realms.
     */
    @Bean
    public List<Realm> realms() {
        List<Realm> realms = new ArrayList<>();
        realms.add(setjdbcRealm()); // Add the configured JdbcRealm
        // Additional realms can be added here
        return realms;
    }

    /**
     * Configures and returns the DefaultWebSecurityManager.
     * Sets the ModularRealmAuthenticator with the configured realms.
     * @return DefaultWebSecurityManager - the security manager.
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(realms()); // Set the realms
        securityManager.setAuthenticator(authenticator); // Set the authenticator
        return securityManager;
    }

    /**
     * Configures and returns the ShiroFilterFactoryBean.
     * It defines custom filters and filter chain definitions for securing paths.
     * @return ShiroFilterFactoryBean - the Shiro filter factory bean.
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        System.out.println("Configuring Shiro filter factory bean.");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager()); // Set the security manager

        // Define custom filters if needed
        Map<String, Filter> filters = new LinkedHashMap<>();
        shiroFilter.setFilters(filters);

        // Define filter chain: specify pages that can be accessed publicly and which require authentication
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginvalidate", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/onlineusers", "authc");
        filterChainDefinitionMap.put("/upload", "authc");
        filterChainDefinitionMap.put("/webSocket/*", "authc");
        filterChainDefinitionMap.put("/currentuser", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilter;
    }
}
