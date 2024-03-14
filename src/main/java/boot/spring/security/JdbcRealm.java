package boot.spring.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import javax.sql.DataSource;

public class JdbcRealm extends org.apache.shiro.realm.jdbc.JdbcRealm {

    public JdbcRealm(DataSource dataSource) {
        super();
        this.setDataSource(dataSource);
        this.setAuthenticationQuery("SELECT hashedpassword FROM spring_websocket.staff WHERE username = ?");
        // Configure other queries if needed
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Here you can add additional authentication logic if needed
        return super.doGetAuthenticationInfo(token);
    }
}
