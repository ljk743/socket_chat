package boot.spring.config;

// Import necessary classes from the Spring framework and other libraries
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * Configures the data source, specifies the mapper package scan path, and the SessionFactory object.
 * The @Configuration annotation indicates that this class contains bean definitions for the application context.
 * The @MapperScan annotation specifies the package to scan for MyBatis mapper interfaces.
 */
@Configuration
@MapperScan(value = "boot.spring.mapper", sqlSessionFactoryRef = "localFactory")
public class MyDataSource {

    // Retrieve database connection properties from the application.properties file using @Value annotation
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Configures the data source bean using Alibaba's DruidDataSource for database connections.
     * The @Bean annotation indicates that this method produces a bean to be managed by the Spring container.
     * @return DataSource the configured data source
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }

    /**
     * Configures the SqlSessionFactory bean to manage database sessions.
     * It sets the mapper XML file locations and the MyBatis configuration file location.
     * @return SqlSessionFactory the configured SQL session factory
     * @throws Exception if any error occurs during the configuration
     */
    @Bean
    public SqlSessionFactory localFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        // Set the location of the mapper XML files
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        factoryBean.setMapperLocations(resources);
        // Set the location of the MyBatis configuration file
        Resource config = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        factoryBean.setConfigLocation(config);
        return factoryBean.getObject();
    }

    /**
     * Configures the DataSourceTransactionManager bean to manage transactions for the configured data source.
     * @return DataSourceTransactionManager the transaction manager for the data source
     */
    @Bean
    public DataSourceTransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * Configures the SqlSessionTemplate bean using the configured SqlSessionFactory.
     * The SqlSessionTemplate is the main interface to execute SQL operations.
     * @return SqlSessionTemplate the configured SQL session template
     * @throws Exception if any error occurs during the configuration
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(localFactory());
        return template;
    }
}
