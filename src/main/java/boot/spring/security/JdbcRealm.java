package boot.spring.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import javax.sql.DataSource;

// JdbcRealm class extends the JdbcRealm from Apache Shiro to provide JDBC-based authentication.
// It utilizes a DataSource to query the database for authentication purposes.
public class JdbcRealm extends org.apache.shiro.realm.jdbc.JdbcRealm {

    // Constructor that accepts a DataSource object.
    // This constructor sets the DataSource for this realm and configures the SQL query used for authentication.
    public JdbcRealm(DataSource dataSource) {
        super(); // Call to the parent class's constructor.
        this.setDataSource(dataSource); // Set the DataSource this realm will use to query for authentication data.
        // Set the SQL query to be used for fetching the hashed password of a user given the username.
        // This query is executed in the context of authentication attempts.
        this.setAuthenticationQuery("SELECT hashedpassword FROM spring_websocket.staff WHERE username = ?");
        // Configure other queries if needed. For example, you can set queries for authorization like roles and permissions.
    }

    // Override the doGetAuthenticationInfo method to add additional authentication logic if needed.
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Custom authentication logic can be added here.
        // By default, this method calls the parent class's doGetAuthenticationInfo method to perform the actual authentication logic
        // based on the configured SQL query.
        return super.doGetAuthenticationInfo(token);
    }
}
